package com.llegoati.llegoati.infrastructure.concrete.utils;

/**
 * Created by Yansel on 8/2/2017.
 */

public class GenericUtils {
    public static String brief(String wholeText, int count) {
        String end = " ...";
        if (wholeText.length() < count)
            return wholeText;
        else {
            if (wholeText.charAt(count - 1) != ' ') {
                int idx = (wholeText.substring(0, count - 1)).lastIndexOf(' ');
                return wholeText.substring(0, idx).concat(end);
            } else
                return wholeText.substring(0, count - 1).concat(end);
        }

    }
}
