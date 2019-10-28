/**
 * Project Name:Ethereum
 * File Name:GethClient.java
 * Package Name:me.kany.project.learning.spring.ethereum.client
 * Date:2018年06月14日 8:47 PM
 * Copyright (c) 2018, Jason All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.client;

import me.kany.project.learning.spring.ethereum.Constants;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;

import java.io.Serializable;

/**
 * ClassName:GethClient<br/>
 * Function: GethClient客户端<br/>
 * Date:2018年06月14日 8:47 PM<br/>
 *
 * @author Jason
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@SuppressWarnings("serial")
public class GethClient implements Serializable {
    private volatile static Geth geth;

    public static Geth getClient() {
        if (null == geth) {
            synchronized (Web3jClient.class) {
                if (null == geth) {
                    geth = Geth.build(new HttpService(Constants.ETHEREUM_NODE_ENDPOINT));
                }
            }
        }
        return geth;
    }
}