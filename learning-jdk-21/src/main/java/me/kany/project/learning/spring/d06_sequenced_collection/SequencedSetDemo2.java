/**
 * Project Name:learning-spring
 * File Name:SequencedSetDemo2.java
 * Package Name:me.kany.project.learning.spring.d06_sequenced_collection
 * Date:2025-06-26 17:07
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d06_sequenced_collection;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.SequencedSet;
import java.util.TreeSet;

/**
 * ClassName: SequencedSetDemo2<br/>
 * Function: 序列Set<br/>
 * Date: 2025-06-26 17:07<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class SequencedSetDemo2 {
    public static void main(String[] args) {
        SequencedSet<String> set = new LinkedHashSet<>(Arrays.asList("张歆艺", "杨幂", "杨紫", "Black.Jack", "杨幂"));
        System.out.println(set);
        System.out.println(set.getFirst());
        System.out.println(set.getLast());

        SequencedSet<String> reversed = set.reversed();
        System.out.println(reversed);

        SequencedSet<String> treeSet = new TreeSet<>();
        treeSet.add("王源");
        treeSet.add("易烊千玺");
        treeSet.add("王俊凯");
        treeSet.add("Black.Jack");
        System.out.println(treeSet);
        System.out.println(treeSet.getFirst());
        System.out.println(treeSet.getLast());
        SequencedSet<String> sequencedSet = treeSet.reversed();
        System.out.println(sequencedSet);

        // HashSet 本身是无序的，没有实现 SequencedSet 接口。因此无法调用上诉方法
        //        SequencedSet<String> hashSet = new HashSet<>();
    }
}
