/**
 * Project Name:Ethereum
 * File Name:Constants.java
 * Package Name:me.kany.project.learning.spring.ethereum
 * Date:2018年06月13日 3:28 PM
 * Copyright (c) 2018, Jason All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum;

import java.math.BigInteger;

/**
 * ClassName:Constants<br/>
 * Function: 常用的配置文件，因为测试所以不写到配置文件中<br/>
 * Date:2018年06月13日 3:28 PM<br/>
 *
 * @author Jason
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class Constants {
    /**
     * 当前区块链服务的地址
     */
    public final static String ETHEREUM_NODE_ENDPOINT = "https://ropsten.infura.io";


    /**
     * 区块链主要地址
     */
    public final static String ETHEREUM_MAIN_NODE_ENDPOINT = "https://mainnet.infura.io";

    /**
     * Rinkeby的测试地址
     */
    public final static String ETHEREUM_TEST_RINKEBY_NODE_ENDPOINT = "https://rinkeby.infura.io";

    /**
     * Kovan的测试地址
     */
    public final static String ETHEREUM_TEST_KOVAN_NODE_ENDPOINT = "https://kovan.infura.io";
    /**
     * Ropsten的测试地址
     */
    public final static String ETHEREUM_TEST_ROPSTEN_NODE_ENDPOINT = "https://ropsten.infura.io";
    /**
     * 当前合约的创建者的地址
     */
    public final static String ETHEREUM_OWER_ADDRESS = "0x180ea203c540ef97a01f93a4e8bf404b0efaa8c9";
    /**
     * 22 GWei as of august '17. check http://ethgasstation.info/ or similar
     */
    public static BigInteger GAS_PRICE_DEFAULT = BigInteger.valueOf(22_000_000_000L);

    /**
     * 300'000 gas. check https://ethereum.stackexchange.com/questions/5845/how-are-ethereum-transaction-costs-calculated
     */
    public static BigInteger GAS_LIMIT_DEFAULT = BigInteger.valueOf(300_000L);
    /**
     * Wallet File Name
     */
    public static String WALLET_FILE_NAME = "UTC--2019-10-28T10-02-20.115000000Z--0x8602a4800901848129e7a862fa53882dc5316871.json";
    /**
     * Test Wallet File Content
     */
    public static String WALLET_FILE = "{\"address\":\"0x8602a4800901848129e7a862fa53882dc5316871\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"cipherparams\":{\"iv\":\"75d2251d3174b6d6e4efff7bc5018f7f\"},\"ciphertext\":\"95206b600f33f1ccb963b630350e54ff55b6cae9ddfd40673a0c69d018abb3a6\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":4096,\"p\":6,\"r\":8,\"salt\":\"76438bd41bbcb752962bb08e72b1a858af48ec7b47df8a643950aead6d3dec93\"},\"mac\":\"07b4e2b2be3f0abc42e6550d9d030f6aab565ebc664909d5de79d0ce85c1ea40\"},\"id\":\"c8938625-8f74-4307-b03b-faf144bce78c\",\"version\":3}";
    /**
     * Test Wallet File Password
     */
    public static String WALLET_PASSWORD = "12345678";
    /**
     * Test Wallet File Mnemonic
     */
    public static String WALLET_MNEMONIC = "noodle utility kidney limb enough fiber force collect champion rifle edit logic";
    /**
     * Test Contract Address
     */
    public static String USTAR_CONTRACT_ADDRESS = "0xbb342fe62fd0853fb41cca8847a3d7e603f1f382";
    /**
     * Test Account Address
     */
    public static String ACCOUNT_ADDRESS = "0x8602a4800901848129E7a862fa53882DC5316871";
    /**
     * Test Account Private
     */
    public static String ACCOUNT_PAIVTE_KEY = "a3c8defd31b3bfacabc118bf5d7ecad4fe8689a4e13ef0023c37aa4313a12a8a";
    /**
     * Test TX Hash For Query
     */
    public static String TEST_TX_HASH = "0x05c765a484a6846d6be6008478bfa572283559f96a600b6c9b84a5e80cb3e608";
}