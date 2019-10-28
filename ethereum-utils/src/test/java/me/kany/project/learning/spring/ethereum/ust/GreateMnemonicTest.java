/**
 * Project Name:ethereum-parent
 * File Name:GreateMnemonicTest.java
 * Package Name:me.kany.project.learning.spring.ethereum.ust
 * Date:2019年06月12日 14:13
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.ust;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.ethereum.Constants;
import me.kany.project.learning.spring.ethereum.utils.WalletFileUtils;
import org.junit.Test;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ClassName:GreateMnemonicTest<br/>
 * Function: 助记词钱包的生成，测试类<br/>
 * Date:2019年06月12日 14:13<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class GreateMnemonicTest {

    /**
     * 用这个对象来直接写入文件
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * createMnemonicWalletFile: 创建助记词钱包<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/6/12 14:44
     */
    @Test
    public void createMnemonicWalletFile() {
        String mnemonic = WalletFileUtils.getMnemonic(Constants.WALLET_PASSWORD);
        WalletFile walletFile = WalletFileUtils.mnemonicToWalletFile(Constants.WALLET_MNEMONIC, Constants.WALLET_PASSWORD);
        //keyStore文件名
        log.debug("钱包文件名：{}", WalletFileUtils.getWalletFileName(walletFile));
        //钱包文件内容
        log.debug("钱包内容：{}", JSONObject.toJSONString(walletFile));
        //12个单词的助记词
        log.debug("钱包助记词：{}", mnemonic);
        try {
            File destination = new File("./", WalletFileUtils.getWalletFileName(walletFile));
            objectMapper.writeValue(destination, walletFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
