/**
 * Project Name:learning-spring
 * File Name:RsaUtils.java
 * Package Name:me.kany.project.learning.spring.utils.rsa.utils
 * Date:2021年06月08日 9:20
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.rsa.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import javax.crypto.Cipher;
import java.io.StringWriter;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * ClassName:RsaUtils<br/>
 * Function: RSA工具类<br/>
 * Date:2021年06月08日 9:20<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
public class RsaUtils {

    /**
     * decryptByPublicKey: 公钥解密<br/>
     *
     * @param publicKeyText
     * @param text
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @date 2021/6/8 9:26
     */
    public static String decryptByPublicKey(String publicKeyText, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * decryptBytesByPublicKey: 公钥解密<br/>
     *
     * @param publicKeyText
     * @param text
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @date 2021/6/8 9:26
     */
    public static byte[] decryptBytesByPublicKey(String publicKeyText, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(Base64.decodeBase64(text));
    }

    /**
     * encryptByPrivateKey: 私钥加密<br/>
     *
     * @param privateKeyText
     * @param text
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @date 2021/6/8 9:26
     */
    public static String encryptByPrivateKey(String privateKeyText, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * encryptByPrivateKey: 私钥加密<br/>
     *
     * @param privateKeyText
     * @param text
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @date 2021/6/8 9:26
     */
    public static String encryptByPrivateKey(String privateKeyText, byte[] text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(text);
        return Base64.encodeBase64String(result);
    }

    /**
     * decryptByPrivateKey: 私钥解密<br/>
     *
     * @param privateKeyText
     * @param text
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @date 2021/6/8 9:25
     */
    public static String decryptByPrivateKey(String privateKeyText, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * decryptByPrivateKey: 私钥解密<br/>
     *
     * @param privateKeyText
     * @param text
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @date 2021/6/8 9:25
     */
    public static byte[] decryptByPrivateKey(String privateKeyText, byte[] text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(text);
    }

    /**
     * decryptBytesByPrivateKey: 私钥解密<br/>
     *
     * @param privateKeyText
     * @param text
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @date 2021/6/8 9:25
     */
    public static byte[] decryptBytesByPrivateKey(String privateKeyText, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(Base64.decodeBase64(text));
    }

    /**
     * encryptByPublicKey: 公钥加密<br/>
     *
     * @param publicKeyText
     * @param text
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @date 2021/6/8 9:25
     */
    public static String encryptByPublicKey(String publicKeyText, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * encryptByPublicKey: 公钥加密<br/>
     *
     * @param publicKeyText
     * @param text
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @date 2021/6/8 9:24
     */
    public static String encryptByPublicKey(String publicKeyText, byte[] text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text);
        return Base64.encodeBase64String(result);
    }

    /**
     * generateKeyPair: 构建RSA密钥对<br/>
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @author Jason.Wang
     * @date 2021/6/8 9:24
     */
    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        java.security.interfaces.RSAPublicKey rsaPublicKey = (java.security.interfaces.RSAPublicKey) keyPair.getPublic();
        java.security.interfaces.RSAPrivateKey rsaPrivateKey = (java.security.interfaces.RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }

    //format PKCS#8 to PKCS#1
    public static String formatPkcs8ToPkcs1(String rawKey) throws Exception {
        String result = null;
        //extract valid key content
        String validKey = rawKey;// pem文件多行合并为一行
        if (StringUtils.isNotBlank(validKey)) {
            //将BASE64编码的私钥字符串进行解码
            byte[] encodeByte = Base64.decodeBase64(validKey);
            //==========
            //pkcs8Bytes contains PKCS#8 DER-encoded key as a byte[]
            PrivateKeyInfo pki = PrivateKeyInfo.getInstance(encodeByte);
            RSAPrivateKey pkcs1Key = RSAPrivateKey.getInstance(pki.parsePrivateKey());
            byte[] pkcs1Bytes = pkcs1Key.getEncoded();//etc.
            //==========
            String type = "RSA PRIVATE KEY";
            result = format2PemString(type, pkcs1Bytes);
        }
        return result;
    }

    //format PKCS#1 to PKCS#8
    public static String formatPkcs1ToPkcs8(String rawKey) throws Exception {
        String result = null;
        //extract valid key content
        String validKey = rawKey;
        if (StringUtils.isNotBlank(validKey)) {
            //将BASE64编码的私钥字符串进行解码
            byte[] encodeByte = Base64.decodeBase64(validKey);
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.pkcs8ShroudedKeyBag);    //PKCSObjectIdentifiers.pkcs8ShroudedKeyBag
            ASN1Object asn1Object = ASN1ObjectIdentifier.fromByteArray(encodeByte);
            PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(algorithmIdentifier, asn1Object);
            byte[] pkcs8Bytes = privateKeyInfo.getEncoded();
            String type = "PRIVATE KEY";
            result = Base64.encodeBase64String(pkcs8Bytes); // 直接一行字符串输出
        }
        return result;
    }

    // Write to pem file
    // 字符串换行显示
    private static String format2PemString(String type, byte[] privateKeyPKCS1) throws Exception {
        PemObject pemObject = new PemObject(type, privateKeyPKCS1);
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.close();
        return stringWriter.toString();
    }


    /**
     * RSA密钥对对象
     */
    @Getter
    @AllArgsConstructor
    public static class RsaKeyPair {
        private final String publicKey;
        private final String privateKey;
    }
}