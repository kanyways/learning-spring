/**
 * Project Name:ethereum-parent
 * File Name:TransactionTest.java
 * Package Name:me.kany.project.learning.spring.ethereum.ust
 * Date:2019年06月28日 15:44
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.ust;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.ethereum.Constants;
import me.kany.project.learning.spring.ethereum.client.Web3jClient;
import org.junit.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;

/**
 * ClassName:TransactionTest<br/>
 * Function: 交易的测试类<br/>
 * Date:2019年06月28日 15:44<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class TransactionTest {

    static Web3j web3j = Web3jClient.getClient();

    /**
     * queryTransactionHash: 查询交易的信息<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/28 15:50
     */
    @Test
    public void queryTransactionHash() {
        try {
            EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(Constants.TEST_TX_HASH).send();
            log.debug(JSONObject.toJSONString(ethGetTransactionReceipt));
            TransactionReceipt transactionReceipt = ethGetTransactionReceipt.getResult();
            log.debug(JSONObject.toJSONString(transactionReceipt));
            log.debug(transactionReceipt.getStatus());
            log.debug(transactionReceipt.isStatusOK() + "");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
