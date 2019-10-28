/**
 * Project Name:Ethereum
 * File Name:Web3jClient.java
 * Package Name:me.kany.project.learning.spring.ethereum.client
 * Date:2018年06月13日 3:26 PM
 * Copyright (c) 2018, Jason All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.client;

import me.kany.project.learning.spring.ethereum.Constants;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.Serializable;

/**
 * ClassName:Web3jClient<br/>
 * Function: Web3JClient<br/>
 * Date:2018年06月13日 3:26 PM<br/>
 *
 * @author Jason
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@SuppressWarnings("serial")
public class Web3jClient implements Serializable {
    private volatile static Web3j web3j;

    public static Web3j getClient() {
        if (null == web3j) {
            synchronized (Web3jClient.class) {
                if (null == web3j) {
                    web3j = Web3j.build(new HttpService(Constants.ETHEREUM_NODE_ENDPOINT));
                }
            }
        }
        return web3j;
    }
}