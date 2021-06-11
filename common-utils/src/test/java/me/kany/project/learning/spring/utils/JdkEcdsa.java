package me.kany.project.learning.spring.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.NullCipher;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JdkEcdsa {
    public static final String EC = "EC";
    public static final String NONEwithECDSA = "NONEwithECDSA";
    public static final String RIPEMD160withECDSA = "RIPEMD160withECDSA";
    public static final String SHA1withECDSA = "SHA1withECDSA";
    public static final String SHA224withECDSA = "SHA224withECDSA";
    public static final String SHA256withECDSA = "SHA256withECDSA";
    public static final String SHA384withECDSA = "SHA384withECDSA";
    public static final String SHA512withECDSA = "SHA512withECDSA";
    private static String src = "imooc security ecdsa";

    public static KeyPair initKey(int keySize, byte[] seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(JdkEcdsa.EC);
        // 初始化随机产生器
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed);
        keygen.initialize(keySize, secureRandom);
        KeyPair keys = keygen.genKeyPair();
        return keys;
    }

    public static KeyPair initKey(int keySize) throws Exception {
        return initKey(keySize, new SecureRandom().generateSeed(8));
    }

    public static byte[] sign(String signAlgorithm, PrivateKey privateKey, byte[] data) throws Exception {
        // 2.执行签名[私钥签名]
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public static byte[] sign(String signAlgorithm, byte[] privateKeyByte, byte[] data) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(EC);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        return sign(signAlgorithm, privateKey, data);
    }

    public static boolean verify(String signAlgorithm, byte[] publKeyBytes, byte[] data, byte[] sign) throws Exception {
        // 3.验证签名[公钥验签]
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(EC);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        return verify(signAlgorithm, publicKey, data, sign);
    }

    public static boolean verify(String signAlgorithm, PublicKey publicKey, byte[] data, byte[] sign) throws Exception {
        // 3.验证签名[公钥验签]
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }

    /**
     * @param publicKeyBase64
     * @return
     */
    public static PublicKey getPublicKey(String publicKeyBase64) {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64));
        PublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC"); //ECC 可根据需求更改
            publicKey = keyFactory.generatePublic(pubKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * @param privateKeyBase64
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKeyBase64) {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64));
        PrivateKey privateKey = null;
        try {
            //ECC 可根据需求更改
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            privateKey = keyFactory.generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 生成公钥，私钥
     *
     * @return Map
     */
    public static Map<String, String> generateKeyPair() {
        HashMap<String, String> rtn = new HashMap<>();
        try {
            KeyPair keyPair = initKey(256);

            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

            rtn.put("publicKey", publicKey);
            rtn.put("privateKey", privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }


    public static void main(String[] args) throws Exception {

        KeyPair keyPair = initKey(256);

        Map<String, String> map = generateKeyPair();
        String s = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String s2 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

//        PublicKey publicKey = getPublicKey("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEFtulkENJ/1m/ynfP3HAxBk9znfXSjgUjmKir7rgQbXr3Hfk5PMWrU1M+QcIhO95wbaS1nTcHSkR35qp4WYesyw==");
//        PrivateKey privateKey = getPrivateKey("MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCD2AyBv9NP7f9XvLJfZGkXn6ZfsgSYGIQJcQpD2pcJjYA==");

//        PublicKey publicKey = getPublicKey("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEg1+tgLnspVtwT7kmKjPg3K6O0xKaGEE9tPiu/vGCHlfPwnh3GLLG8UhboQWtWRrEBjGPJ1U6z/mGVatR8jgaLQ==");
//        PrivateKey privateKey = getPrivateKey("MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgqnZ6WKj8p0vCoANBnR7HimPVx70tPj/QphucHLN/NmehRANCAASDX62AueylW3BPuSYqM+Dcro7TEpoYQT20+K7+8YIeV8/CeHcYssbxSFuhBa1ZGsQGMY8nVTrP+YZVq1HyOBot");

//        System.out.println("测试公：" + Hex.encodeHexString(keyPair.getPublic().getEncoded()));
//        System.out.println("测试私：" + Hex.encodeHexString(privateKey.getEncoded()));

        System.out.println("测试公：" + s);
        System.out.println("测试私：" + s2);

        PublicKey publicKey = getPublicKey(map.get("publicKey"));
        PrivateKey privateKey = getPrivateKey(map.get("privateKey"));
        //mac这样做好像没有意义吧，随便给个数字啊，应该要解析
        String source = "Happyjava not only java";
        byte[] signs = new byte[0];
        try {
            //MEUCIHjefZiVujBAOzIx01EunJvq75e/CRM/nU/fchMm54uXAiEA7r28tHzKIdk7Mtj9ehOHnrZwMH3etTkFdBHurZ4qAI8=
            // MEUCIAI4IO4qC6gaODnxaD70qDT6z3ILwC344deWSenc/XazAiEA8ERKHY+CaIbKMRdH13tga9pGJxPjE3qzLqtKVAwPcPY=
            signs = JdkEcdsa.sign(JdkEcdsa.SHA256withECDSA, privateKey, source.getBytes());

            System.out.println("jdk ecdsa sign : " + Hex.encodeHexString(signs));

            String encode = Base64.getEncoder().encodeToString(signs);
            //System.out.println("mac数据:" +encode);
            byte[] decode = Base64.getDecoder().decode(encode);
            boolean verify = JdkEcdsa.verify(JdkEcdsa.SHA256withECDSA, publicKey, source.getBytes(), decode);
            System.out.println("验签:" + verify);
//            jdkECDSA();

            String ddddd = JdkEcdsa.encrypt(source, s);
            System.out.println(ddddd);
            System.out.println(JdkEcdsa.decrypt(ddddd, s2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void jdkECDSA() {
        try {
            //1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
            //2.执行签名
            System.out.println("测试公：" + Base64.getEncoder().encodeToString(ecPublicKey.getEncoded()));

            System.out.println("测试私：" + Base64.getEncoder().encodeToString(ecPrivateKey.getEncoded()));
            //System.out.println("测试"+Base64.getEncoder().encode(ecPrivateKey.getEncoded()));
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance(SHA1withECDSA);
            signature.initSign(privateKey);
            signature.update(src.getBytes());
            byte[] result = signature.sign();
            System.out.println("jdk ecdsa sign : " + Hex.encodeHexString(result));

            //3.验证签名
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(ecPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            signature = Signature.getInstance(SHA1withECDSA);
            signature.initVerify(publicKey);
            signature.update(src.getBytes());
            boolean bool = signature.verify(result);
            System.out.println("jdk ecdsa verify : " + bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String str, String publicKey) throws Exception {
        ECPublicKey pubKey = (ECPublicKey) getPublicKey(publicKey);
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(pubKey.getW(), pubKey.getParams());

        //RSA加密
        Cipher cipher = new NullCipher();
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey, ecPublicKeySpec.getParams());
        String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}