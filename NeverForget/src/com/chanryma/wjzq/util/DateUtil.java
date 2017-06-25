package com.chanryma.wjzq.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.mysql.jdbc.StringUtils;

public class DateUtil {
    private static SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    /**getCurrentTime 获得当前时间.
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        String result = "";
        result = sf1.format(new Date());
        return result;

    }

    /**获得当前时间.
     * @return yyyy-MM-dd
     */
    public static String getCurrentDate() {
        String result = "";
        result = sf2.format(new Date());
        return result;
    }

    /**获得当前时间.
     * @return yyyy-MM-dd
     */
    public static String getDate(String dateStr) {
        String result = "";
        if (!StringUtils.isEmptyOrWhitespaceOnly(dateStr)) {
            try {
                Date date = sf2.parse(dateStr);
                result = sf2.format(date);
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 获得当前时间前几天
     * @return
     */

    public static String getDatePre(int i) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, i);
        String format = sf1.format(calendar.getTime());
        return format;
    }

    public static String fromLong(long ltime) {
        Date date = new Date(ltime);
        return sf1.format(date);
    }

    /**获得当前时间.
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTime(Date date) {
        String result = "";
        if (null != date) {
            result = sf1.format(date);
        }

        return result;
    }

    /**
     * @param dateString yyyy-MM-dd HH:mm:ss.
     */
    public static String getDateTime(String dateString) {
        String result = "";
        if (!StringUtils.isEmptyOrWhitespaceOnly(dateString)) {
            try {
                Date date = sf1.parse(dateString);
                result = sf1.format(date);
            } catch (ParseException e) {
            }
        }

        return result;
    }

    /**
     * @return yyyy-MM-dd.
     */
    public static String getDateStr(Date date) {
        String result = "";
        if (null != date) {
            result = sf2.format(date);
        }

        return result;
    }

    public static Date getDate2(String dateStr) {
        Date date = null;

        if (!StringUtils.isEmptyOrWhitespaceOnly(dateStr)) {
            try {
                date = sf2.parse(dateStr);
            } catch (Exception e) {
            }
        }

        return date;
    }

    public static Date getBeforeDate(String dateStr, int days) {
        Date date = getDate2(dateStr);

        return getBeforeDate(date, days);
    }

    public static Date getBeforeDate(Date date, int days) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);

        return calendar.getTime();
    }
}