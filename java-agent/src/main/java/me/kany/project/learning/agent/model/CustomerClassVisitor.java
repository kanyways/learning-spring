/**
 * Project Name:learning-agent
 * File Name:CustomerClassVisitor.java
 * Package Name:me.kany.project.learning.agent.model
 * Date:2020-10-24 3:41 下午
 * Copyright (c) 2020, jason All Rights Reserved.
 */
package me.kany.project.learning.agent.model;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

/**
 * ClassName: CustomerClassVisitor<br/>
 * Function: 自定义的ClassVisitor<br/>
 * Date: 2020-10-24 3:41 下午<br/>
 *
 * @author jason
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
public class CustomerClassVisitor extends ClassVisitor {

    public List<CustomerFieldVisitor> fields;

    /**
     * CustomerClassVisitor : 自定义的实现方法
     *
     * @author jason
     * @date 2020/10/24 3:45 下午
     */
    public CustomerClassVisitor() {
        this(Opcodes.ASM7);
        if (getClass() != CustomerClassVisitor.class) {
            throw new IllegalStateException();
        }
    }

    public CustomerClassVisitor(int api) {
        super(api);
    }

    /**
     * visitField : 重写该方法
     *
     * @param access
     * @param name
     * @param descriptor
     * @param signature
     * @param value
     * @return
     * @author jason
     * @date 2020/10/24 4:17 下午
     */
    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        CustomerFieldVisitor fieldVisitor = new CustomerFieldVisitor(access, name, descriptor, signature, value);
        this.fields.add(fieldVisitor);
        return fieldVisitor;
    }

    /**
     * visitMethod : 重写该方法
     *
     * @param access
     * @param name
     * @param descriptor
     * @param signature
     * @param exceptions
     * @return
     * @author jason
     * @date 2020/10/24 4:17 下午
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }


}
