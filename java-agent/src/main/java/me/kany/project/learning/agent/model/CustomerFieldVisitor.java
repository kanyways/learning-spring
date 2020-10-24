/**
 * Project Name:learning-agent
 * File Name:CustomerFieldVisitor.java
 * Package Name:me.kany.project.learning.agent.model
 * Date:2020-10-24 4:18 下午
 * Copyright (c) 2020, jason All Rights Reserved.
 */
package me.kany.project.learning.agent.model;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

/**
 * ClassName: CustomerFieldVisitor<br/>
 * Function: 自定义FieldVisitor<br/>
 * Date: 2020-10-24 4:18 下午<br/>
 *
 * @author jason
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
public class CustomerFieldVisitor extends FieldVisitor {

    public int access;
    public String name;
    public String descriptor;
    public String signature;
    public Object value;

    public CustomerFieldVisitor(int access, String name, String descriptor, String signature, Object value) {
        this(Opcodes.ASM7, access, name, descriptor, signature, value);
        if (getClass() != CustomerFieldVisitor.class) {
            throw new IllegalStateException();
        }
    }

    public CustomerFieldVisitor(int api, int access, String name, String descriptor, String signature, Object value) {
        super(api);
        this.access = access;
        this.name = name;
        this.descriptor = descriptor;
        this.signature = signature;
        this.value = value;
    }

    public CustomerFieldVisitor(int api, FieldVisitor fieldVisitor) {
        super(api, fieldVisitor);
    }
}
