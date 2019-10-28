/**
 * Project Name:Ethereum
 * File Name:WalletFileUtils.java
 * Package Name:me.kany.project.learning.spring.ethereum.utils
 * Date:2018年07月07日 23:33
 * Copyright (c) 2018, Jason All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.utils;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.crypto.generators.SCrypt;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;
import static me.kany.project.learning.spring.ethereum.utils.SecureRandomUtils.secureRandom;
import static org.web3j.crypto.Hash.sha256;

/**
 * ClassName:WalletFileUtils<br/>
 * Function: 钱包工具类<br/>
 * Date:2018年07月07日 23:33<br/>
 *
 * @author Jason
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class WalletFileUtils {


    private static final int N_LIGHT = 1 << 12;
    private static final int P_LIGHT = 6;

    private static final int R = 8;
    private static final int DKLEN = 32;

    private static final int CURRENT_VERSION = 3;

    private static final String CIPHER = "aes-128-ctr";
    static final String SCRYPT = "scrypt";

    /**
     * getWalletFile : 获取JSON格式的KeyStore文件
     *
     * @param password
     * @return
     * @author Jason
     * @date 2018/7/7 23:42
     */
    public static WalletFile getWalletFile(String password) {
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            return Wallet.createLight(password, ecKeyPair);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getBip39Wallet : 生成助记词的钱包对象
     *
     * @param password
     * @return
     * @author Jason
     * @date 2018/7/7 23:38
     */
    public static Bip39Wallet getBip39Wallet(String password) {
        try {
            byte[] initialEntropy = new byte[16];
            secureRandom().nextBytes(initialEntropy);
            String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
            byte[] seed = MnemonicUtils.generateSeed(mnemonic, password);
            ECKeyPair ecKeyPair = ECKeyPair.create(sha256(seed));
            WalletFile walletFile = Wallet.createLight(password, ecKeyPair);
            return new Bip39Wallet(getWalletFileName(walletFile), mnemonic);
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getBip39Wallet : 生成助记词的钱包，只是为了获取WalletFile的名称，不用再去生成一个WalletFile文件而已，该助记词不能和传递的WalletFile钱包关联，切记
     *
     * @param walletFile
     * @return
     * @author Jason
     * @date 2018/7/7 23:51
     */
    public static Bip39Wallet getBip39Wallet(WalletFile walletFile) {
        if (null == walletFile) {
            return null;
        }
        byte[] initialEntropy = new byte[16];
        secureRandom().nextBytes(initialEntropy);
        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
        return new Bip39Wallet(getWalletFileName(walletFile), mnemonic);
    }

    /**
     * mnemonicToWalletFile : 将助记词的钱包转换成WalletFile，但是转化需要密码
     *
     * @param mnemonic
     * @param password
     * @return
     * @author Jason
     * @date 2018/7/7 23:51
     */
    public static WalletFile mnemonicToWalletFile(String mnemonic, String password) {
        if (null == mnemonic) {
            return null;
        }
        try {
            byte[] seed = MnemonicUtils.generateSeed(mnemonic, password);
            ECKeyPair ecKeyPair = ECKeyPair.create(sha256(seed));
            return Wallet.createLight(password, ecKeyPair);
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getWalletString : 获取JSON格式的KeyStore字符串
     *
     * @param password
     * @return
     * @author Jason
     * @date 2018/7/7 23:46
     */
    public static String getWalletString(String password) {
        WalletFile walletFile = getWalletFile(password);
        if (null == walletFile) {
            return null;
        }
        return JSONObject.toJSONString(walletFile);
    }

    /**
     * getMnemonic : 生成助记词的钱包，12个单词的助记词
     *
     * @param password
     * @return
     * @author Jason
     * @date 2018/7/7 23:37
     */
    public static String getMnemonic(String password) {
        Bip39Wallet bip39Wallet = getBip39Wallet(password);
        if (null == bip39Wallet) {
            return null;
        }
        return bip39Wallet.getMnemonic();
    }

    /**
     * getBip39WalletByWalletFileStr : 生成助记词的钱包，只是为了获取WalletFile的名称，不用再去生成一个WalletFile文件而已，该助记词不能和传递的WalletFile钱包关联，切记
     *
     * @param walletFileStr
     * @return
     * @author Jason
     * @date 2018/7/7 23:51
     */
    public static Bip39Wallet getBip39WalletByWalletFileStr(String walletFileStr) {
        if (null == walletFileStr) {
            return null;
        }
        WalletFile walletFile = JSONObject.parseObject(walletFileStr, WalletFile.class);
        return getBip39Wallet(walletFile);
    }

    /**
     * bip39WalletToWalletFile : 将助记词的钱包转换成WalletFile，但是转化需要密码
     *
     * @param bip39Wallet
     * @param password
     * @return
     * @author Jason
     * @date 2018/7/7 23:51
     */
    public static WalletFile bip39WalletToWalletFile(Bip39Wallet bip39Wallet, String password) {
        if (null == bip39Wallet) {
            return null;
        }
        return mnemonicToWalletFile(bip39Wallet.getMnemonic(), password);

    }

    /**
     * generateWalletFileByPriavte : 根据当前的明文私钥和地址并重新设置密码
     *
     * @param privateKey 0x开头的私钥地址
     * @param password   当前想要设置的新密码
     * @return
     * @author Jason
     * @date 2018/7/8 00:11
     */
    public static WalletFile generateWalletFileByPriavte(String privateKey, String password) {
        String address = Numeric.prependHexPrefix(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        return generateWalletFileByPriavte(privateKey, address, password);
    }

    /**
     * generateWalletFileByPriavte : 根据当前的明文私钥和地址并重新设置密码
     *
     * @param privateKey 0x开头的私钥地址
     * @param address    这个不能有0x开头的地址
     * @param password   当前想要设置的新密码
     * @return
     * @author Jason
     * @date 2018/7/8 00:11
     */
    public static WalletFile generateWalletFileByPriavte(String privateKey, String address, String password) {
        try {
            int n = N_LIGHT;
            int p = P_LIGHT;
            byte[] salt = generateRandomBytes(32);
            byte[] derivedKey = generateDerivedScryptKey(password.getBytes(UTF_8), salt, n, R, p, DKLEN);
            byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 16);
            byte[] iv = generateRandomBytes(16);
            byte[] privateKeyBytes = Numeric.toBytesPadded(Numeric.toBigInt(privateKey), 32);
            byte[] cipherText = performCipherOperation(Cipher.ENCRYPT_MODE, iv, encryptKey, privateKeyBytes);
            byte[] mac = generateMac(derivedKey, cipherText);
            return createWalletFile(address, cipherText, iv, salt, mac, n, p);
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * createWalletFile :
     *
     * @param address    这个地址的格式的不带上0x开头的，并且一定要小写
     * @param cipherText
     * @param iv
     * @param salt
     * @param mac
     * @param n
     * @param p
     * @return
     * @author Jason
     * @date 2018/7/8 00:13
     */
    private static WalletFile createWalletFile(String address, byte[] cipherText, byte[] iv, byte[] salt, byte[] mac, int n, int p) {
        WalletFile walletFile = new WalletFile();
        walletFile.setAddress(address.toLowerCase());
        WalletFile.Crypto crypto = new WalletFile.Crypto();
        crypto.setCipher(CIPHER);
        crypto.setCiphertext(Numeric.toHexStringNoPrefix(cipherText));
        walletFile.setCrypto(crypto);
        WalletFile.CipherParams cipherParams = new WalletFile.CipherParams();
        cipherParams.setIv(Numeric.toHexStringNoPrefix(iv));
        crypto.setCipherparams(cipherParams);
        crypto.setKdf(SCRYPT);
        WalletFile.ScryptKdfParams kdfParams = new WalletFile.ScryptKdfParams();
        kdfParams.setDklen(DKLEN);
        kdfParams.setN(n);
        kdfParams.setP(p);
        kdfParams.setR(R);
        kdfParams.setSalt(Numeric.toHexStringNoPrefix(salt));
        crypto.setKdfparams(kdfParams);
        crypto.setMac(Numeric.toHexStringNoPrefix(mac));
        walletFile.setCrypto(crypto);
        walletFile.setId(UUID.randomUUID().toString());
        walletFile.setVersion(CURRENT_VERSION);
        return walletFile;
    }

    /**
     * getWalletFileName : 存储KeyStore的文件名称
     *
     * @param walletFile
     * @return
     * @author Jason
     * @date 2018/7/8 00:08
     */
    public static String getWalletFileName(WalletFile walletFile) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("'UTC--'yyyy-MM-dd'T'HH-mm-ss.nVV'--'");
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        return now.format(format) + walletFile.getAddress() + ".json";
    }

    /**
     * performCipherOperation: 格式化加密参数的数据<br/>
     *
     * @param mode
     * @param iv
     * @param encryptKey
     * @param text
     * @return
     * @throws CipherException
     * @author Jason.Wang
     * @createTime 2019/6/12 19:39
     */
    private static byte[] performCipherOperation(int mode, byte[] iv, byte[] encryptKey, byte[] text) throws CipherException {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey, "AES");
            cipher.init(mode, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(text);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new CipherException("Error performing cipher operation", e);
        }
    }

    /**
     * generateMac: 生成Mac信息<br/>
     *
     * @param derivedKey
     * @param cipherText
     * @return
     * @author Jason.Wang
     * @createTime 2019/6/12 19:38
     */
    private static byte[] generateMac(byte[] derivedKey, byte[] cipherText) {
        byte[] result = new byte[16 + cipherText.length];
        System.arraycopy(derivedKey, 16, result, 0, 16);
        System.arraycopy(cipherText, 0, result, 16, cipherText.length);
        return Hash.sha3(result);
    }

    /**
     * generateDerivedScryptKey: 生成derivedKey数据<br/>
     *
     * @param password
     * @param salt
     * @param n
     * @param r
     * @param p
     * @param dkLen
     * @return
     * @author Jason.Wang
     * @createTime 2019/6/12 19:37
     */
    private static byte[] generateDerivedScryptKey(byte[] password, byte[] salt, int n, int r, int p, int dkLen) {
        return SCrypt.generate(password, salt, n, r, p, dkLen);
    }

    /**
     * generateRandomBytes: 生成指定大小的随机byte数组<br/>
     *
     * @param size
     * @return
     * @author Jason.Wang
     * @createTime 2019/6/12 19:37
     */
    private static byte[] generateRandomBytes(int size) {
        byte[] bytes = new byte[size];
        secureRandom().nextBytes(bytes);
        return bytes;
    }
}