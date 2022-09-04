package com.yogcn.iot.common.core.utils;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author Peter.Feng
 */
public class DateTimeUtils {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String CRON_DATE_FORMAT = "0 mm HH dd MM ? yyyy";

    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);

    /**
     * 工具类不允许创建实例
     */
    private DateTimeUtils() {
    }


    /**
     * 按照yyyy-MM-dd的格式将日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String convertDateToString(Date date) {
        return convertDateToString(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 按照yyyy-MM-dd的格式将日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String convertDateToString(LocalDateTime date) {
        return convertDateToString(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式将日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String convertTimeToString(Date date) {
        return convertTimeToString(date, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式将日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String convertTimeToString(LocalDateTime date) {
        return convertTimeToString(date, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 按照指定格式，将日期转换为字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String convertDateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        FastDateFormat fdf = FastDateFormat.getInstance(pattern);
        return fdf.format(date);
    }

    /**
     * 按照指定格式，将日期转换为字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String convertDateToString(LocalDateTime date, String pattern) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter fdf = DateTimeFormatter.ofPattern(pattern);
        return fdf.format(date);
    }


    /**
     * 按照指定格式，将日期转换为字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String convertTimeToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        FastDateFormat fdf = FastDateFormat.getInstance(pattern);
        return fdf.format(date);
    }

    /**
     * 按照指定格式，将日期转换为字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String convertTimeToString(LocalDateTime date, String pattern) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter fdf = DateTimeFormatter.ofPattern(pattern);
        return fdf.format(date);
    }

    public static LocalDateTime convertStringToLocalDateTime(String date) {

        return convertStringToLocalDateTime(date, DEFAULT_DATE_TIME_FORMAT);

    }

    public static LocalDateTime convertStringTimeToLocalDateTime(String date) {
        return convertStringToLocalDateTime(date, DEFAULT_DATE_TIME_FORMAT);
    }

    public static LocalDateTime convertStringToLocalDateTime(String date, String pattern) {

        DateTimeFormatter fdf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime ldt = LocalDateTime.parse(date, fdf);
        return ldt;
    }

    public static Date convertStringToDate(String date, String pattern) {
        FastDateFormat fdf = FastDateFormat.getInstance(pattern);
        try {
            return fdf.parse(date);
        } catch (ParseException e) {
            logger.error("转换字符串为日期出错", e);
        }
        return null;
    }

    public static String convertDateToCronString(String date) {
        Date target = convertStringToDate(date, "yyyy-MM-dd HH:mm");
        return convertTimeToString(target, CRON_DATE_FORMAT);
    }
}
