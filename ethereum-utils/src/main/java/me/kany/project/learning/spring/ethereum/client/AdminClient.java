/**
 * Project Name:Ethereum
 * File Name:AdminClient.java
 * Package Name:me.kany.project.learning.spring.ethereum.client
 * Date:2018年06月14日 12:07 AM
 * Copyright (c) 2018, Jason All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.client;

import me.kany.project.learning.spring.ethereum.Constants;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

import java.io.Serializable;

/**
 * ClassName:AdminClient<br/>
 * Function: AdminClient实体类<br/>
 * Date:2018年06月14日 12:07 AM<br/>
 *
 * @author Jason
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@SuppressWarnings("serial")
public class AdminClient implements Serializable {
    private volatile static Admin admin;

    public static Admin getClient() {
        if (null == admin) {
            synchronized (Web3jClient.class) {
                if (null == admin) {
                    admin = Admin.build(new HttpService(Constants.ETHEREUM_NODE_ENDPOINT));
                }
            }
        }
        return admin;
    }
}