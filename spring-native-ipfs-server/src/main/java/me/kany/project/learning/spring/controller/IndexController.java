/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2021年06月09日 17:32
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.CharStreams;
import me.kany.project.learning.spring.utils.ipfs.IpfsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * ClassName:IndexController<br/>
 * Function: 首页控制器<br/>
 * Date:2021年06月09日 17:32<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@RestController
@RequestMapping("/api/v0")
public class IndexController {

    @Autowired
    private IpfsUtils ipfsUtils;

    /**
     * add: 上传文件<br/>
     *
     * @param file
     * @return
     * @author Jason.Wang
     * @date 2021/6/9 17:48
     */
    @PostMapping("add")
    public String add(@RequestParam("arg") MultipartFile file) {
        String fileHash = null;
        try {
            fileHash = ipfsUtils.uploadFile(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileHash;
    }

    /**
     * get: 获取文件，并转换成String输出<br/>
     *
     * @param hash
     * @return
     * @author Jason.Wang
     * @date 2021/6/10 14:37
     */
    @PostMapping("files/get")
    public Object get(@RequestParam("ipfshash") String hash) {
        try {
            return CharStreams.toString(new InputStreamReader(ipfsUtils.downloadFile(hash), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}