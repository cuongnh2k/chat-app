package com.vn.chat.common.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * yyyy: year
     * mm: month
     * dd: day
     * HH: hour
     * MM: minutes
     * ss: second
     * yyyy/mm/dd
     * yyyymmddHHMMss
     * */
    public static String format(String format){
        Calendar time = Calendar.getInstance();
        Integer year = time.get(Calendar.YEAR);
        Integer month = time.get(Calendar.MONTH) + 1;
        Integer day = time.get(Calendar.DAY_OF_MONTH);
        Integer hour = time.get(Calendar.HOUR_OF_DAY);
        Integer minute = time.get(Calendar.MINUTE);
        Integer second = time.get(Calendar.SECOND);
        return format.replaceAll("yyyy", year.toString())
                .replaceAll("mm", month.toString())
                .replaceAll("dd", day.toString())
                .replaceAll("HH", hour.toString())
                .replaceAll("MM", minute.toString())
                .replaceAll("ss", second.toString());
    }

    public static String format(Date date, String format){
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        Integer year = time.get(Calendar.YEAR);
        Integer month = time.get(Calendar.MONTH) + 1;
        Integer day = time.get(Calendar.DAY_OF_MONTH);
        Integer hour = time.get(Calendar.HOUR_OF_DAY);
        Integer minute = time.get(Calendar.MINUTE);
        Integer second = time.get(Calendar.SECOND);
        return format.replaceAll("yyyy", year.toString())
                .replaceAll("mm", month.toString())
                .replaceAll("dd", day.toString())
                .replaceAll("HH", hour.toString())
                .replaceAll("MM", minute.toString())
                .replaceAll("ss", second.toString());
    }
}
