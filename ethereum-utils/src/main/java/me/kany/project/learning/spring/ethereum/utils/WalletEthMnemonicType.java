/**
 * Project Name:Ethereum
 * File Name:WalletEthMnemonicType.java
 * Package Name:me.kany.project.learning.spring.ethereum.utils
 * Date:2018年08月23日 09:43
 * Copyright (c) 2018, Jason All Rights Reserved.
 */
package me.kany.project.learning.spring.ethereum.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * ClassName:WalletEthMnemonicType<br/>
 * Function: 以太坊助记词类型<br/>
 * Date:2018年08月23日 09:43<br/>
 *
 * @author Jason
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public enum WalletEthMnemonicType {
    //因为这个对象是枚举所以需要在这里加上一个“;”来处理
    ;

    @Setter
    @Getter
    int type;
    @Setter
    @Getter
    String desc;
    @Setter
    @Getter
    String value;
}