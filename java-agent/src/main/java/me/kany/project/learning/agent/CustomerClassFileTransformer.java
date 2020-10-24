/**
 * Project Name:learning-agent
 * File Name:CustomerClassFileTransformer.java
 * Package Name:me.kany.project.learning.agent
 * Date:2020-10-24 3:25 下午
 * Copyright (c) 2020, jason All Rights Reserved.
 */
package me.kany.project.learning.agent;

import me.kany.project.learning.agent.model.CustomerClassVisitor;
import me.kany.project.learning.agent.model.CustomerFieldVisitor;
import org.objectweb.asm.ClassReader;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * ClassName: CustomerClassFileTransformer<br/>
 * Function: CustomerClassFileTransformer<br/>
 * Date: 2020-10-24 3:25 下午<br/>
 *
 * @author jason
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
public class CustomerClassFileTransformer implements ClassFileTransformer {

    /**
     * transform : 自定义的实现类型
     *
     * @param loader
     * @param className
     * @param classBeingRedefined
     * @param protectionDomain
     * @param classfileBuffer
     * @return
     * @throws IllegalClassFormatException
     * @author jason
     * @date 2020/10/24 3:36 下午
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (null == className || "".equals(className)) {
            return classfileBuffer;
        }
        ClassReader classReader = new ClassReader(classfileBuffer);
        CustomerClassVisitor classVisitor = new CustomerClassVisitor();
        classReader.accept(classVisitor, 0);
        System.out.println("check clazz: " + className);
        for (CustomerFieldVisitor fieldVisitor : classVisitor.fields) {
            System.out.println("find " + fieldVisitor.descriptor + ", " + fieldVisitor.value);
        }
        return classfileBuffer;
    }
}
