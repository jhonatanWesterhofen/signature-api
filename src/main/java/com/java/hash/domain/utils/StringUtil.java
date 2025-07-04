package com.java.hash.domain.utils;

import java.text.MessageFormat;

/**
 *
 * @author Jhonatan
 */
public class StringUtil {

    public static String stringPatternFormat(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }
}