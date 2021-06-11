/**
 * Project Name:learning-spring
 * File Name:RsaUtilsTest.java
 * Package Name:me.kany.project.learning.spring.utils.rsa.utils
 * Date:2021年06月08日 9:27
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.rsa.utils;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;

/**
 * ClassName:RsaUtilsTest<br/>
 * Function: RsaUtils测试类<br/>
 * Date:2021年06月08日 9:27<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@Slf4j
public class RsaUtilsTest {

    private String publicKey = null;
    private String privateKey = null;
    private final static String source = "我是一个被加密的小鱼仔.";
    private String encrypt = null;

    @BeforeTest
    public void generateKeyPair() {
        try {
            RsaUtils.RsaKeyPair keyPair = RsaUtils.generateKeyPair();
            publicKey = keyPair.getPublicKey();
            privateKey = keyPair.getPrivateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);
        try {
            System.out.println("私钥PKCS#1：" + RsaUtils.formatPkcs8ToPkcs1(privateKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEncrypt() {
        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);
        try {
            System.out.println("私钥PKCS#1：" + RsaUtils.formatPkcs8ToPkcs1(privateKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
//            String publicKeyStr = RsaUtils.formatPkcs1ToPkcs8(publicKey);
            encrypt = RsaUtils.encryptByPublicKey(publicKey, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("加密：" + encrypt);

        try {
//            String privateKeyStr = RsaUtils.formatPkcs1ToPkcs8(privateKey);
            System.out.println("解密：" + RsaUtils.decryptByPrivateKey(privateKey, encrypt));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}