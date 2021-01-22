/**
 * Project Name:learning-spring
 * File Name:JsoupUtil.java
 * Package Name:me.kany.project.learning.spring.utils
 * Date:2021年01月21日 15:55
 * Copyright (c) 2021, Kai.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * ClassName:JsoupUtil<br/>
 * Function: JsoupUtil<br/>
 * Date:2021年01月21日 15:55<br/>
 *
 * @author Kai.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
public class JsoupUtil {
    /**
     * 默认使用relaxed()
     * 允许的标签: a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul。结果不包含标签rel=nofollow ，如果需要可以手动添加。
     */
    private Whitelist whiteList;


    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    private Document.OutputSettings outputSettings;

    private JsoupUtil() {
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @param whiteList 白名单标签
     * @param pretty    是否格式化
     * @return HtmlFilter
     */
    public static JsoupUtil create(Whitelist whiteList, boolean pretty) {
        JsoupUtil filter = new JsoupUtil();
        if (whiteList == null) {
            filter.whiteList = Whitelist.relaxed();
        }
        filter.outputSettings = new Document.OutputSettings().prettyPrint(pretty);
        return filter;
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @return HtmlFilter
     */
    public static JsoupUtil create() {
        return create(null, false);
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @param whiteList 白名单标签
     * @return HtmlFilter
     */
    public static JsoupUtil create(Whitelist whiteList) {
        return create(whiteList, false);
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @param excludeTags 例外的特定标签
     * @param includeTags 需要过滤的特定标签
     * @param pretty      是否格式化
     * @return HtmlFilter
     */
    public static JsoupUtil create(List<String> excludeTags, List<String> includeTags, boolean pretty) {
        JsoupUtil filter = create(null, pretty);
        //要过滤的标签
        if (includeTags != null && !includeTags.isEmpty()) {
            String[] tags = (String[]) includeTags.toArray(new String[0]);
            filter.whiteList.removeTags(tags);
        }
        //例外标签
        if (excludeTags != null && !excludeTags.isEmpty()) {
            String[] tags = (String[]) excludeTags.toArray(new String[0]);
            filter.whiteList.addTags(tags);
        }
        return filter;
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @param excludeTags 例外的特定标签
     * @param includeTags 需要过滤的特定标签
     * @return HtmlFilter
     */
    public static JsoupUtil create(List<String> excludeTags, List<String> includeTags) {
        return create(includeTags, excludeTags, false);
    }

    /**
     * @param content 需要过滤内容
     * @return 过滤后的String
     */
    public String clean(String content) {
        return Jsoup.clean(content, "", this.whiteList, this.outputSettings);

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String text = "{\"additional_info\": \"<a href=\\\"http://www.baidu.com/a\\\" onclick=\\\"alert(1);\\\">sss</a><script>alert(0);</script>sss这个是我输入的中文\"}";
        System.out.println(JsoupUtil.create().clean(text));
    }

}