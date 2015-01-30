package com.makenv.sheets.util;

/**
 * Created by alei on 2015/1/27.
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().isEmpty()) {
            return true;
        }
        return false;
    }
}
