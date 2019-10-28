/**
 * Project Name:learning-spring
 * File Name:WalletFileUtilsTest.java
 * Package Name:me.kany.project.learning.spring.ethereum.ust
 * Date:2019年10月28日 14:42
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.ust;

import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.ethereum.Constants;
import me.kany.project.learning.spring.ethereum.utils.WalletFileUtils;
import org.junit.Test;

/**
 * ClassName:WalletFileUtilsTest<br/>
 * Function: WalletFile测试类<br/>
 * Date:2019年10月28日 14:42<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class WalletFileUtilsTest {

    /**
     * createWalletFile: CreateWalletFile<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/10/28 17:27
     */
    @Test
    public void createWalletFile() {
        log.debug(WalletFileUtils.getWalletString(Constants.WALLET_PASSWORD));
    }
}
