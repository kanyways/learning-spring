package me.kany.project.learning.spring.annotation;

/**
 * @author Jason.Wang
 */

public enum SpacesType {
    /**
     * 仅替换前后空格、前后换行符
     */
    SIMPLE,
    /**
     * 替换所有的换行符、空格
     */
    ALL_WHITESPACES,
    /**
     * 除了文字中的换行符，其余的换行符、前后空格全部替换
     */
    EXCEPT_LINE_BREAK,
    /**
     * 除了文字中的空格，其余的换行符、前后空格全部替换
     */
    EXCEPT_LINE_SPACES;
}
