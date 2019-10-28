/**
 * Project Name:ethereum-parent
 * File Name:RestoreWalletFileTest.java
 * Package Name:me.kany.project.learning.spring.ethereum.ust
 * Date:2019年06月12日 16:55
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.ust;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.ethereum.Constants;
import me.kany.project.learning.spring.ethereum.utils.WalletFileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.web3j.crypto.*;

import static me.kany.project.learning.spring.ethereum.utils.SecureRandomUtils.secureRandom;

/**
 * ClassName:RestoreWalletFileTest<br/>
 * Function: 根据PrivateKey还原钱包<br/>
 * Date:2019年06月12日 16:55<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class RestoreWalletFileTest {
    /**
     * 钱包认证对象
     */
    private Credentials credentials = null;
    /**
     * 配置钱包的公私钥
     */
    private String publicKey;
    private String privateKey;

    @BeforeTest
    private void initPrivateKey() {
        //解锁账户
        credentials = WalletUtils.loadBip39Credentials(Constants.WALLET_PASSWORD, Constants.WALLET_MNEMONIC);
        String address = credentials.getAddress();
        publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);
        privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        //助记词
        log.debug("钱包的助记词为:{}", Constants.WALLET_MNEMONIC);
        //钱包地址
        log.debug("钱包的地址为:{}", address);
        //公钥16进制字符串表示
        log.debug("钱包的公钥为:{}", publicKey);
        //私钥16进制字符串表示
        log.debug("钱包的私钥为:{}", privateKey);
    }

    /**
     * restoreWalletFileFromMnemonic: 通过助记词还原成WalletFile文件<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 17:16
     */
    @Test
    public void restoreWalletFileFromMnemonic() {
        //通过助记词直接还原钱包对象
        WalletFile walletFile = WalletFileUtils.mnemonicToWalletFile(Constants.WALLET_MNEMONIC, Constants.WALLET_PASSWORD);
        log.debug("助记词还原：");
        log.debug("还原钱包的JSON文件内容：{}", JSONObject.toJSONString(walletFile));
        log.debug("还原钱包的JSON文件钱包文件名：{}", JSONObject.toJSONString(WalletFileUtils.getWalletFileName(walletFile)));
        //通过钱包文件构建助记词信息
        Bip39Wallet bip39Wallet = WalletFileUtils.getBip39Wallet(walletFile);
        log.debug("重新生成的助记词为：{}", bip39Wallet.getMnemonic());
        log.debug("通过助记词重新生成WalletFile钱包：{}", JSONObject.toJSONString(WalletFileUtils.bip39WalletToWalletFile(bip39Wallet, Constants.WALLET_PASSWORD)));
    }

    /**
     * restoreWalletFileFromPrivateKey: 通过私钥还原成JSON的WalletFile钱包文件<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 17:15
     */
    @Test
    public void restoreWalletFileFromPrivateKey() {
        log.debug("私钥还原：");
        //通过私钥构建钱包对象
        WalletFile walletFile = WalletFileUtils.generateWalletFileByPriavte(Constants.ACCOUNT_PAIVTE_KEY, Constants.WALLET_PASSWORD);
        log.debug("通过私钥重新生成WalletFile钱包：{}", JSONObject.toJSONString(walletFile));
        //通过钱包文件构建助记词信息
        Bip39Wallet bip39Wallet = WalletFileUtils.getBip39Wallet(walletFile);
        log.debug("重新生成的助记词为：{}", bip39Wallet.getMnemonic());
        log.debug("通过助记词重新生成WalletFile钱包：{}", JSONObject.toJSONString(WalletFileUtils.bip39WalletToWalletFile(bip39Wallet, Constants.WALLET_PASSWORD)));
    }

    /**
     * restoreWalletFileFromPrivateKeyWithAddress: 通过私钥还原成JSON的WalletFile钱包文件，并指定地址<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 17:15
     */
    @Test
    public void restoreWalletFileFromPrivateKeyWithAddress() {
        log.debug("私钥还原：");
        //通过私钥构建钱包对象
        WalletFile walletFile = WalletFileUtils.generateWalletFileByPriavte(Constants.ACCOUNT_PAIVTE_KEY, Constants.ACCOUNT_ADDRESS, Constants.WALLET_PASSWORD);
        log.debug("通过私钥重新生成WalletFile钱包：{}", JSONObject.toJSONString(walletFile));
        log.debug("通过私钥重新生成WalletFile钱包文件名：{}", JSONObject.toJSONString(WalletFileUtils.getWalletFileName(walletFile)));
        //通过钱包文件构建助记词信息
        Bip39Wallet bip39Wallet = WalletFileUtils.getBip39Wallet(walletFile);
        log.debug("重新生成的助记词为：{}", bip39Wallet.getMnemonic());
        log.debug("通过助记词重新生成WalletFile钱包：{}", JSONObject.toJSONString(WalletFileUtils.bip39WalletToWalletFile(bip39Wallet, Constants.WALLET_PASSWORD)));
    }

    /**
     * createMnemonicWalletFile: 生成新的助记词钱包，以及怎么使用WalletFile钱包还原到原始的助记词<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 19:50
     */
    @Test
    public void createMnemonicWalletFile() {
        /**
         * <pre>
         *     1、生成助记词的钱包只需要WalletFile的文件名称，例如："UTC--2019-06-12T10-02-17.623000000Z--0a76c5f077e40789c7ec5c996c1d26062352800c.json"
         *     2、虽然我们能还原出来Key信息，但是无法还原回去助记词信息，因为使用Sha-256的处理
         * </pre>
         */
        byte[] initialEntropy = new byte[16];
        secureRandom().nextBytes(initialEntropy);
        String mnemonicNew = MnemonicUtils.generateMnemonic(initialEntropy);
        Bip39Wallet bip39Wallet = new Bip39Wallet(Constants.WALLET_FILE_NAME, mnemonicNew);
        log.debug("生成助记词钱包的文件名称：{}", Constants.WALLET_FILE_NAME);
        //使用最新的助记词处理
        WalletFile walletFile = WalletFileUtils.mnemonicToWalletFile(bip39Wallet.getMnemonic(), Constants.WALLET_PASSWORD);
        log.error("使用新的助记词还原：");
        log.debug("新的助记词：{}", mnemonicNew);
        log.debug("WalletFile钱包的地址：{}", walletFile.getAddress());
        log.debug("还原钱包的JSON文件内容：{}", JSONObject.toJSONString(walletFile));
        //使用原始的助记词处理
        walletFile = WalletFileUtils.mnemonicToWalletFile(Constants.WALLET_MNEMONIC, Constants.WALLET_PASSWORD);
        log.error("使用原始助记词还原：");
        log.debug("原始助记词：{}", Constants.WALLET_MNEMONIC);
        log.debug("WalletFile钱包的地址：{}", walletFile.getAddress());
        log.debug("还原钱包的JSON文件内容：{}", JSONObject.toJSONString(walletFile));
    }
}
