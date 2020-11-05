package com.yuanxiatech.xgj.funeral.base.utils;

public class HtmlUtil {

    public static String removeHtml(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }
        return str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
//        String newStr = StringEscapeUtils.unescapeHtml4(str);
//        return StringEscapeUtils.unescapeHtml4(newStr.replaceAll("<[^br][^>]*>", "").replaceAll("<br[^>]*>", "<br/>&nbsp;&nbsp;&nbsp;&nbsp;"));
    }
}
