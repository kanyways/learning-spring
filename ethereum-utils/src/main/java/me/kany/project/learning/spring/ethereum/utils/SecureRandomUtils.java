/**
 * Project Name:Ethereum
 * File Name:SecureRandomUtils.java
 * Package Name:me.kany.project.learning.spring.ethereum.utils
 * Date:2018年07月07日 21:58
 * Copyright (c) 2018, Jason All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.utils;

import org.web3j.crypto.LinuxSecureRandom;

import java.security.SecureRandom;

/**
 * ClassName:SecureRandomUtils<br/>
 * Function: <br/>
 * Date:2018年07月07日 21:58<br/>
 *
 * @author Jason
 * @see
 * @since JDK1.8
 */
public class SecureRandomUtils {
    private static final SecureRandom SECURE_RANDOM;

    static {
        if (isAndroidRuntime()) {
            new LinuxSecureRandom();
        }
        SECURE_RANDOM = new SecureRandom();
    }

    public static SecureRandom secureRandom() {
        return SECURE_RANDOM;
    }

    // Taken from BitcoinJ implementation
    // https://github.com/bitcoinj/bitcoinj/blob/3cb1f6c6c589f84fe6e1fb56bf26d94cccc85429/core/src/main/java/org/bitcoinj/core/Utils.java#L573
    private static int isAndroid = -1;

    static boolean isAndroidRuntime() {
        if (isAndroid == -1) {
            final String runtime = System.getProperty("java.runtime.name");
            isAndroid = (runtime != null && runtime.equals("Android Runtime")) ? 1 : 0;
        }
        return isAndroid == 1;
    }

    private SecureRandomUtils() {
    }
}