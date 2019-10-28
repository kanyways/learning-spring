/**
 * Project Name:ethereum-parent
 * File Name:MonitorEthereumTest.java
 * Package Name:me.kany.project.learning.spring.ethereum.ust
 * Date:2019年06月12日 16:24
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.ust;

import com.alibaba.fastjson.JSONObject;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.ethereum.client.Web3jClient;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.web3j.protocol.Web3j;

/**
 * ClassName:MonitorEthereumTest<br/>
 * Function: 监听区块交易的测试类<br/>
 * Date:2019年06月12日 16:24<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see <pre>https://docs.web3j.io/filters.html</pre>
 * @since JDK1.8
 */
@Slf4j
public class MonitorEthereumTest {
    private Web3j web3j = null;


    /**
     * initWeb3j: 初始化web3j的连接方法，注意监听的矿一定要有权限<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 16:54
     */
    @BeforeTest
    public void initWeb3j() {
        web3j = Web3jClient.getClient();
    }

    /**
     * listenerEthereum: 开始监听<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 16:54
     */
    @Test
    public void listenerEthereum() {
        blockListener(web3j);
        transactionListener(web3j);
        pendingTransactionListener(web3j);
    }

    /**
     * blockListener: 监听区块信息<br/>
     *
     * @param web3j
     * @author Jason.Wang
     * @createTime 2019/6/12 16:53
     */
    private void blockListener(Web3j web3j) {
        Disposable disposable = web3j.blockFlowable(false).subscribe(block -> {
            log.debug("new block come in");
            log.debug("block number：" + block.getBlock().getNumber());
            log.debug(JSONObject.toJSONString(block));
        });
        if (disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * transactionListener: 监听交易信息<br/>
     *
     * @param web3j
     * @author Jason.Wang
     * @createTime 2019/6/12 16:53
     */
    private void transactionListener(Web3j web3j) {
        Disposable disposable = web3j.transactionFlowable().subscribe(transaction -> {
            log.debug("transaction come in");
            log.debug("transaction txHash：" + transaction.getHash());
            log.debug(JSONObject.toJSONString(transaction));
        });
        if (disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * pendingTransactionListener: 获取正在执行中的交易<br/>
     *
     * @param web3j
     * @author Jason.Wang
     * @createTime 2019/6/13 0:13
     */
    private void pendingTransactionListener(Web3j web3j) {
        Disposable disposable = web3j.pendingTransactionFlowable().subscribe(transaction -> {
            log.debug("pending transaction come in");
            log.debug("pending transaction txHash：" + transaction.getHash());
            log.debug(JSONObject.toJSONString(transaction));
        });
        if (disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
