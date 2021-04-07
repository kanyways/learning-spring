/**
 * Project Name:learning-spring
 * File Name:Md2Test.java
 * Package Name:me.kany.project.learning.spring.md2
 * Date:2020年10月21日 13:59
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.md2;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * ClassName:Md2Test<br/>
 * Function: MD2加密<br/>
 * Date:2020年10月21日 13:59<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@Slf4j
public class Md2Test {

    @Test
    public void md2Test() {
        String src = "49";
//        Digest digest = new MD5Digest();
//        digest.update(src.getBytes(), 0, src.getBytes().length);
//        byte[] md5Bytes = new byte[digest.getDigestSize()];
//        digest.doFinal(md5Bytes, 0);
//        System.out.println("BC MD5 : " + org.bouncycastle.util.encoders.Hex.toHexString(md5Bytes));

//        Digest digest = new MD2Digest();
//        digest.update(src.getBytes(), 0, src.getBytes().length);
//        byte[] md2Bytes = new byte[digest.getDigestSize()];
//        digest.doFinal(md2Bytes, 0);
//        System.out.println("BC MD2 : " + org.bouncycastle.util.encoders.Hex.toHexString(md2Bytes));


        for (int i = 0; i < 100000000; i++) {
            src = DigestUtils.md2Hex(src.getBytes(Charset.forName("UTF-8")));
        }
        log.debug(src);
    }
}