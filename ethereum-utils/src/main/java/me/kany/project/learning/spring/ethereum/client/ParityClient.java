/**
 * Project Name:Ethereum
 * File Name:ParityClient.java
 * Package Name:me.kany.project.learning.spring.ethereum.client
 * Date:2018年06月13日 3:35 PM
 * Copyright (c) 2018, Jason All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.client;

import me.kany.project.learning.spring.ethereum.Constants;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;

import java.io.Serializable;

/**
 * ClassName:ParityClient<br/>
 * Function: ParityClient模块的客户端实现<br/>
 * Date:2018年06月13日 3:35 PM<br/>
 *
 * @author Jason
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@SuppressWarnings("serial")
public class ParityClient implements Serializable {

    private static class ClientHolder {
        private static final Parity parity = Parity.build(new HttpService(Constants.ETHEREUM_NODE_ENDPOINT));
    }

    public static final Parity getClient() {
        return ClientHolder.parity;
    }
}