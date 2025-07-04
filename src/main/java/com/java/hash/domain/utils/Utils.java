package com.java.hash.domain.utils;

public class Utils {

    public static <T> boolean isNull(T value) {
        return value == null ? true : false;
    }

    public static <T> boolean isNotNull(T value) {
        return value == null ? false : true;
    }
}