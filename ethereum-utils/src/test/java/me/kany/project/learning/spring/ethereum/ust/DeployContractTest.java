/**
 * Project Name:ethereum-parent
 * File Name:DeployContractTest.java
 * Package Name:me.kany.project.learning.spring.ethereum.ust
 * Date:2019年06月06日 17:30
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.ust;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.ethereum.Constants;
import me.kany.project.learning.spring.ethereum.client.Web3jClient;
import me.kany.project.learning.spring.ethereum.lib.Ustar;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;

/**
 * ClassName:DeployContractTest<br/>
 * Function: 部署合约<br/>
 * Date:2019年06月06日 17:30<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class DeployContractTest {

    static Web3j web3j = Web3jClient.getClient();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Credentials credentials = null;

    /**
     * initCredentials: 初始化好认证信息<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 16:10
     */
    @BeforeTest
    public void initCredentials() {
        EthGetBalance ethGetBalance = null;
        try {
            web3j = Web3j.build(new HttpService(Constants.ETHEREUM_TEST_ROPSTEN_NODE_ENDPOINT));
            ethGetBalance = web3j.ethGetBalance(Constants.ACCOUNT_ADDRESS, DefaultBlockParameterName.LATEST).send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("当前帐号 {} 的余额为：{}", Constants.ACCOUNT_ADDRESS, ethGetBalance.getBalance());
        WalletFile walletFile = null;

        try {
            //根据JSON对象还原钱包对象
            walletFile = objectMapper.readValue(Constants.WALLET_FILE, WalletFile.class);
            //创建认证信息
            credentials = Credentials.create(Wallet.decrypt(Constants.WALLET_PASSWORD, walletFile));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    /**
     * deployContract: 部署当前的合约<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 14:42
     */
    @Test
    public void deployContract() {
        //获取合约信息
        Ustar ustar = Ustar.load(Constants.USTAR_CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());
        if (null == ustar) {
            log.debug("Current contract not exits,deploy now.");
            try {
                ustar = Ustar.deploy(web3j, credentials, new DefaultGasProvider()).send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug("Current contract address:{}", ustar.getContractAddress());
    }

    /**
     * transferOwnership: 调用合约的转让功能<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/7/11 17:17
     */
    @Test
    public void transferOwnership() {
        TransactionManager transactionManager = null;
        try {
            byte chainId = Byte.parseByte(web3j.netVersion().send().getNetVersion());
            log.debug("chainId:{}", chainId);
            transactionManager = new RawTransactionManager(web3j, credentials, chainId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Ustar ustar = Ustar.load(Constants.USTAR_CONTRACT_ADDRESS, web3j, transactionManager, new DefaultGasProvider());
    }

    /**
     * saveDataByContract: 通过合约保存数据到链上<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 16:13
     */
    @Test
    public void saveDataByContract() {
        TransactionManager transactionManager = null;
        /**
         * web3j提供了一个交易管理器TransactionManager来控制你连接到以太坊客户端的方式。默认机制使用web3j的RawTransactionManager，它与以太坊钱包文件一起工作，在提交到网络之前离线地签署交易。
         */
        try {
            byte chainId = Byte.parseByte(web3j.netVersion().send().getNetVersion());
            log.debug("chainId:{}", chainId);
            transactionManager = new RawTransactionManager(web3j, credentials, chainId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Ustar ustar = Ustar.load(Constants.USTAR_CONTRACT_ADDRESS, web3j, transactionManager, new DefaultGasProvider());
        TransactionReceipt transactionReceipt = null;
        try {
            transactionReceipt = ustar.newCommodity(BigInteger.valueOf(1L), BigInteger.valueOf(15225986823L), "7b685b8793e8fbd855f7c9552f78751a").sendAsync().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("交易Hash:{}", transactionReceipt.getTransactionHash());
        log.debug("交易区块高度：{}", transactionReceipt.getBlockNumber());
        log.debug("区块Hash:{}", transactionReceipt.getBlockHash());
        log.debug("完整的交易信息：{}", JSONObject.toJSONString(transactionReceipt));
    }

    /**
     * queryDataByContract: 通过合约获取当前连上的信息<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 16:16
     */
    @Test
    public void queryDataByContract() {
        /**
         * ReadonlyTransactionManager只能用来进行查询交易
         */
        TransactionManager transactionManager = new ReadonlyTransactionManager(web3j, Constants.ETHEREUM_OWER_ADDRESS);
        Ustar ustar = Ustar.load(Constants.USTAR_CONTRACT_ADDRESS, web3j, transactionManager, new DefaultGasProvider());
        Tuple3<BigInteger, BigInteger, String> result = null;
        try {
            result = ustar.getCommodityInfoById(BigInteger.valueOf(4L)).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug(JSONObject.toJSONString(result));
    }
}
