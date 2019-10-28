/**
 * Project Name:ethereum-parent
 * File Name:IdCardUtilsTest.java
 * Package Name:me.kany.project.learning.spring.utils.idcard
 * Date:2019年07月30日 17:29
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.idcard;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName:IdCardUtilsTest<br/>
 * Function: 身份证验证的测试类<br/>
 * Date:2019年07月30日 17:29<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class IdCardUtilsTest {

    @Test
    public void validHKIdCard() {
        List<String> IdCards = Arrays.asList("W1948981", "Z1363124", "C2948867", "X3560045", "Z3108148", "B7655261", "N1194494", "R3631930", "A9223293", "U7637974", "O6456619", "R0373922", "B2214313", "N7482289", "X7819779", "N8090031", "R370444A", "R3344710", "X8111125", "Z3109845");
        IdCards.stream().forEach(IdCard -> {
            log.info("Check IdCard {} is {}", IdCard, IdCardUtils.validHKIdCard(IdCard));
        });

    }
}
