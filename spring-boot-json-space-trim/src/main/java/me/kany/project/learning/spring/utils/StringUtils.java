package me.kany.project.learning.spring.utils;

import me.kany.project.learning.spring.annotation.SpacesType;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Jason.Wang
 * @date 2022/3/31 14:05
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static final Pattern PATTERN_WHITESPACES = Pattern.compile("\\s+");
    private static final Pattern PATTERN_WHITESPACES_WITH_LINE_BREAK = Pattern.compile("\\s*\\n\\s*");
    private static final Pattern PATTERN_WHITESPACES_EXCEPT_LINE_BREAK = Pattern.compile("[\\s&&[^\\n]]+");

    /**
     * trimSpaces: 处理空格信息<br/>
     *
     * @param text 文字
     * @param type 类型
     * @return
     * @author Jason.Wang
     * @date 2022/3/31 4:05
     */
    public static String trimSpaces(String text, SpacesType type) {
        if (isNotBlank(text)) {
            text = trim(text);
            switch (type) {
                case ALL_WHITESPACES:
                    return PATTERN_WHITESPACES.matcher(text).replaceAll("");
                case EXCEPT_LINE_BREAK:
                    return PATTERN_WHITESPACES_EXCEPT_LINE_BREAK.matcher(
                        PATTERN_WHITESPACES_WITH_LINE_BREAK.matcher(text).replaceAll("\n")).replaceAll("");
                case EXCEPT_LINE_SPACES:
                    return PATTERN_WHITESPACES_EXCEPT_LINE_BREAK.matcher(
                        PATTERN_WHITESPACES_WITH_LINE_BREAK.matcher(text).replaceAll("")).replaceAll(" ");
                case SIMPLE:
                    return text;
                default:
                    // not possible
                    throw new AssertionError();
            }
        }
        return text;
    }
}
