/**
 * Project Name:learning-spring
 * File Name:CodeGeneratorTest.java
 * Package Name:me.kany.project.learning.spring.mybatis.plus.utils
 * Date:2019年10月18日 16:34
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.mybatis.plus.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * ClassName:CodeGeneratorTest<br/>
 * Function: CodeGenerator的测试类<br/>
 * Date:2019年10月18日 16:34<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class CodeGeneratorTest {

    private String author = "Jason.Wang";
    private String packageName = "me.kany.project.learning.spring";
    private String tableName = "college_infos";

    /**
     * generateOneTable: 测试仅创建一张表的案例<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/10/18 16:37
     */
    @Test
    public void generateOneTable() {
        CodeGenerator.generate(author, packageName, tableName);
    }

    /**
     * generateAllTable: 创建所有表案例<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/10/18 16:37
     */
    @Test
    public void generateAllTable() {
        CodeGenerator.generate(author, packageName);
    }
}
