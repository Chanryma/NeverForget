package com.chanryma.wjzq.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static <T> boolean isEmty(List<T> list) {
        return (list == null) || (list.size() == 0);
    }

    public static <T> List<T> getSafeList(List<T> list) {
        if (list == null) {
            list = new ArrayList<T>();
        }

        return list;
    }
}
