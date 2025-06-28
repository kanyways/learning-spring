/**
 * Project Name:learning-spring
 * File Name:SequencedMapDemo3.java
 * Package Name:me.kany.project.learning.spring.d06_sequenced_collection
 * Date:2025-06-26 17:07
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d06_sequenced_collection;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SequencedMap;

/**
 * ClassName: SequencedMapDemo3<br/>
 * Function: 序列Map<br/>
 * Date: 2025-06-26 17:07<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class SequencedMapDemo3 {
    public static void main(String[] args) {
        SequencedMap<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            // 注意 string_template 这个是模板语法，类似字符串拼接，但是比字符串拼接更安全，可以防止SQL注入，目前还是预览的哈。
            map.put(STR."key\{i}", i);
        }
        for (String key : map.keySet()) {
            System.out.println(STR."\{key} : \{map.get(key)}");
        }

        Map.Entry<String, Integer> entry = map.firstEntry();
        if (entry != null) {
            System.out.println(STR."\{entry.getKey()} : \{entry.getValue()}");
        }

        map.pollLastEntry();
        System.out.println(map);
    }
}
