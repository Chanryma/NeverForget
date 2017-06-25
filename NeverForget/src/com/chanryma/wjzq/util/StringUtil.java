package com.chanryma.wjzq.util;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return (str == null) || (str.isEmpty());
    }

    public static boolean isBlank(String str) {
        return isEmpty(str) || isEmpty(str.trim());
    }
}