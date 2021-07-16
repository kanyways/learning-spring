/**
 * Project Name:learning-spring
 * File Name:IpfsUtils.java
 * Package Name:me.kany.project.learning.spring.utils.ipfs
 * Date:2021年06月09日 17:31
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.ipfs;

import com.alibaba.fastjson.JSONObject;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * ClassName:IpfsUtils<br/>
 * Function: IPFS的工具类<br/>
 * Date:2021年06月09日 17:31<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@Component
public class IpfsUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 节点地址
     */
    @Value("${ipfs.node}")
    private String node;

    /**
     * uploadFile : 上传文件<br/>
     *
     * @param filePath 指的是文件的上传路径+文件名，如D:/1.png
     * @return
     * @throws IOException
     * @author Jason.Wang
     * @date 2020/10/13 16:50
     */
    public String uploadFile(String filePath) throws IOException {
        //创建IPFS的服务
        IPFS ipfs = new IPFS(node);
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(filePath));
        MerkleNode addResult = ipfs.add(file).get(0);
        logger.debug("upload file {} file info: {}", filePath, JSONObject.toJSONString(addResult));
        return addResult.hash.toString();
    }

    /**
     * uploadFile : 上传文件<br/>
     *
     * @param file 文件对象
     * @return
     * @throws IOException
     * @author Jason.Wang
     * @date 2020/10/13 16:50
     */
    public String uploadFile(File file) throws IOException {
        //创建IPFS的服务
        IPFS ipfs = new IPFS(node);
        NamedStreamable.FileWrapper uploadFile = new NamedStreamable.FileWrapper(file);
        MerkleNode addResult = ipfs.add(uploadFile).get(0);
        logger.debug("upload file {} file info: {}", file.getName(), JSONObject.toJSONString(addResult));
        return addResult.hash.toString();
    }

    /**
     * uploadFile : 使用InputStream上传<br/>
     *
     * @param inputStream 文件流
     * @return
     * @throws IOException
     * @author Jason.Wang
     * @date 2020/10/13 16:55
     */
    public String uploadFile(InputStream inputStream) throws IOException {
        //创建IPFS的服务
        IPFS ipfs = new IPFS(node);
        NamedStreamable.InputStreamWrapper input = new NamedStreamable.InputStreamWrapper(inputStream);
        MerkleNode addResult = ipfs.add(input).get(0);
        logger.debug("upload InputStream file info: {}", JSONObject.toJSONString(addResult));
        return addResult.hash.toString();
    }

    /**
     * uploadFile : 使用字节数组上传文件<br/>
     *
     * @param data byte[]
     * @return
     * @throws IOException
     * @author Jason.Wang
     * @date 2020/10/13 17:37
     */
    public String uploadFile(byte[] data) throws IOException {
        //创建IPFS的服务
        IPFS ipfs = new IPFS(node);
        NamedStreamable.ByteArrayWrapper input = new NamedStreamable.ByteArrayWrapper(data);
        MerkleNode addResult = ipfs.add(input).get(0);
        logger.debug("upload byte[] file info: {}", JSONObject.toJSONString(addResult));
        return addResult.hash.toString();
    }

    /**
     * downloadFile : 下载文件<br/>
     *
     * @param fileHash 文件在IPFS上保存Hash值
     * @param filePath 保存文件路径
     * @throws IOException
     * @author Jason.Wang
     * @date 2020/10/13 16:51
     */
    public void downloadFile(String fileHash, String filePath) throws IOException {
        //创建IPFS的服务
        IPFS ipfs = new IPFS(node);
        Multihash filePointer = Multihash.fromBase58(fileHash);
        logger.debug("get file from hash {},file info: {}", fileHash, JSONObject.toJSONString(filePointer));
        byte[] data = ipfs.cat(filePointer);
        if (null != data) {
            File file = new File(filePath);
            //判断文件是否存在，存在则删除
            if (file.exists()) {
                file.delete();
            }
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(data, 0, data.length);
            }
        }
    }

    /**
     * downloadFile : 下载文件使用流的形式返回<br/>
     *
     * @param fileHash
     * @return
     * @throws IOException
     * @author Jason.Wang
     * @date 2020/10/13 17:01
     */
    public InputStream downloadFile(String fileHash) throws IOException {
        //创建IPFS的服务
        IPFS ipfs = new IPFS(node);
        Multihash filePointer = Multihash.fromBase58(fileHash);
        logger.debug("get file from hash {}", fileHash);
        return ipfs.catStream(filePointer);
    }

    /**
     * getFile : 将文件流改变成文件<br/>
     *
     * @param inputStream
     * @param fileName
     * @throws IOException
     * @author Jason.Wang
     * @date 2020/10/13 17:20
     */
    public static void getFile(InputStream inputStream, String fileName) throws IOException {
        try (
                BufferedInputStream in = new BufferedInputStream(inputStream);
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
        ) {
            int len = -1;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
        }
    }

    /**
     * toByteArray : 将文件输入流转换成Byte数组<br/>
     *
     * @param input InputStream
     * @return
     * @throws IOException
     * @author Jason.Wang
     * @date 2020/10/13 17:57
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }
}