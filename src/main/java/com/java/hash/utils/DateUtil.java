package com.java.hash.utils;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

public class DateUtil {

    public static String toIso8601String(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return DatatypeConverter.printDateTime(cal);
    }
}