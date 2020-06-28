/**
 * Project Name:learning-spring
 * File Name:AliyunOssServiceImpl.java
 * Package Name:me.kany.project.learning.spring.service.impl
 * Date:2020年06月28日 12:12
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.log4j.Log4j2;
import me.kany.project.learning.spring.config.OssConfig;
import me.kany.project.learning.spring.module.OssCallbackParam;
import me.kany.project.learning.spring.module.OssCallbackResult;
import me.kany.project.learning.spring.module.OssPolicyResult;
import me.kany.project.learning.spring.service.IOssService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:AliyunOssServiceImpl<br/>
 * Function: 阿里云的OSS实现类<br/>
 * Date:2020年06月28日 12:12<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Log4j2
@Service
public class AliyunOssServiceImpl implements IOssService {

    @Autowired
    OssConfig ossConfig;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * getAliyunOSS : 获取子帐号授权信息 <br/>
     *
     * @return
     * @author Jason
     * @date 2020-04-14 23:50
     */
    @Override
    public Map<String, String> getStsOSS() {
        String key = "oss:token";
        Map<String, String> map = new HashMap<>();
        if (stringRedisTemplate.hasKey(key)) {
            String ossToken = stringRedisTemplate.opsForValue().get(key);
            map = JSONObject.parseObject(ossToken, HashMap.class);
            return map;
        }
        DefaultAcsClient client = null;
        try {
            //构造default profile（参数留空，无需添加Region ID）
            IClientProfile profile = DefaultProfile.getProfile("", ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
            //用profile构造client
            client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysEndpoint(ossConfig.getSysEndpoint());
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(ossConfig.getRoleArn());
            request.setRoleSessionName(ossConfig.getRoleSessionName());
            // Optional
            request.setPolicy(ossConfig.getPolicy());
            final AssumeRoleResponse response = client.getAcsResponse(request);
            map.put("expiration", response.getCredentials().getExpiration());
            map.put("accessKeyId", response.getCredentials().getAccessKeyId());
            map.put("accessKeySecret", response.getCredentials().getAccessKeySecret());
            map.put("securityToken", response.getCredentials().getSecurityToken());
            map.put("requestId", response.getRequestId());

            //访问的 类型
            map.put("type", "aliyun");
            //访问的外网节点
            map.put("endpoint", String.format("https://%s", ossConfig.getEndpoint()));
            //存储桶名称
            map.put("bucketName", ossConfig.getBucketName());
            //对外访问的网站
            map.put("webSite", String.format("https://%s.%s", ossConfig.getBucketName(), ossConfig.getEndpoint()));
            stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(map));
            stringRedisTemplate.expire(key, 50L, TimeUnit.MINUTES);
            return map;
        } catch (ClientException ce) {
        } finally {
            //一定要加上，不然你的电脑会让你怀疑人生。
            if (null != client) {
                /**
                 * 这个地方一定要注意，使用client.shutdown(),是不成的，即使使用单利模式创建DefaultAcsClient一样是有问题的，一直会提示连接要关闭
                 */
                client.shutdown();
                IOUtils.closeQuietly(client.getHttpClient());
                client.setHttpClient(null);
                client = null;
            }
        }
        return null;
    }

    /**
     * getPolicy: 获取当前的OSS签名<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2020/6/28 15:04
     */
    @Override
    public OssPolicyResult getPolicy() {
        OssPolicyResult result = new OssPolicyResult();
        Map<String, String> map = getStsOSS();
        if (null == map) {
            log.error("签名生成失败");
        }
        // 存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dir = ossConfig.getPrefix() + sdf.format(new Date());
        // 签名有效期
        long expireEndTime = System.currentTimeMillis() + ossConfig.getExpire() * 1000;
        Date expiration = new Date(expireEndTime);
        // 文件大小
        long maxSize = ossConfig.getMaxSize() * 1024 * 1024;
        // 回调
        OssCallbackParam callback = new OssCallbackParam();
        callback.setCallbackUrl(ossConfig.getCallback());
        callback.setCallbackBody("filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
        callback.setCallbackBodyType("application/x-www-form-urlencoded");
        // 提交节点
        String action = map.get("webSite");
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(map.get("webSite"), new DefaultCredentialProvider(map.get("accessKeyId"), map.get("accessKeySecret"), map.get("securityToken")), null);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String policy = BinaryUtil.toBase64String(binaryData);
            String signature = ossClient.calculatePostSignature(postPolicy);
            String callbackData = BinaryUtil.toBase64String(JSONObject.toJSONString(callback).getBytes("utf-8"));
            // 返回结果
            result.setAccessKeyId(ossClient.getCredentialsProvider().getCredentials().getAccessKeyId());
            result.setPolicy(policy);
            result.setSignature(signature);
            result.setDir(dir);
            result.setCallback(callbackData);
            result.setHost(action);
        } catch (Exception e) {
            log.error("签名生成失败", e);
        } finally {
            if (null != ossClient) {
                ossClient.shutdown();
            }
        }
        return result;

    }

    /**
     * getCallback: OSS上传之后的回调<br/>
     *
     * @param request
     * @return
     * @author Jason.Wang
     * @createTime 2020/6/28 15:29
     */
    @Override
    public OssCallbackResult getCallback(HttpServletRequest request) {
        OssCallbackResult result = new OssCallbackResult();
        String filename = request.getParameter("filename");
        filename = "http://".concat(ossConfig.getBucketName()).concat(".").concat(ossConfig.getEndpoint()).concat("/").concat(filename);
        result.setFilename(filename);
        result.setSize(request.getParameter("size"));
        result.setMimeType(request.getParameter("mimeType"));
        result.setWidth(request.getParameter("width"));
        result.setHeight(request.getParameter("height"));
        return result;
    }
}
