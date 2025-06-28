/**
 * Project Name:learning-spring
 * File Name:SequencedCollectionDemo1.java
 * Package Name:me.kany.project.learning.spring.d06_sequenced_collection
 * Date:2025-06-26 16:54
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d06_sequenced_collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SequencedCollection;

/**
 * ClassName: SequencedCollectionDemo1<br/>
 * Function: 序列集合<br/>
 * Date: 2025-06-26 16:54<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class SequencedCollectionDemo1 {
    public static void main(String[] args) {
        System.out.println("序列集合");
        SequencedCollection<Integer> list = new ArrayList<>(
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        System.out.println(list.getFirst());
        System.out.println(list.getLast());
        list.addFirst(0);
        System.out.println(list.getFirst());
        list.addLast(11);
        System.out.println(list.getLast());
        System.out.println(list.removeFirst());
        System.out.println(list.getFirst());
        System.out.println(list.removeLast());
        System.out.println(list.getLast());
        SequencedCollection<Integer> reversedList = list.reversed();
        System.out.println(reversedList);
    }
}
