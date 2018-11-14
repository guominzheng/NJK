// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 2018/11/13 13:51:43
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Source File Name:   DelHTMLTag.java
package com.fh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelHTMLTag {
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    private static final String regEx_html = "<[^>]+>";
    private static final String regEx_space = "\\s*|\t|\r|\n";

    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile("<script[^>]*?>[\\s\\S]*?<\\/script>", 2);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        Pattern p_style = Pattern.compile("<style[^>]*?>[\\s\\S]*?<\\/style>", 2);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        Pattern p_html = Pattern.compile("<[^>]+>", 2);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        Pattern p_space = Pattern.compile("\\s*|\t|\r|\n", 2);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");
        return htmlStr.trim();
    }

    public static String getTextFromHtml(String htmlStr) {
        htmlStr = DelHTMLTag.delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll(" ", "");
        return htmlStr;
    }

    public static String getText(String htmlStr) {
        Pattern p_html = Pattern.compile("<[^>]+>", 2);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        return htmlStr;
    }
}
