package com.alipay.altershield.common.util;

import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author yuefan, wyf
 * DateUtils.java Date processing related tools
 */
public class DateUtil {

    /**
     * Constants
     **/
    public static final String DATE_JFP_STR    = "yyyyMM";
    public static final String DATE_JFP_STR_YMD    = "yyyyMMdd";
    public static final String DATE_FULL_STR   = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_MINUTE_STR = "yyyy-MM-dd HH:mm";
    public static final String DATE_SMALL_STR  = "yyyy-MM-dd";
    public static final String DATE_KEY_STR    = "yyMMddHHmmss";

    /**
     * Extract string date using preset format
     * @param strDate date string
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, DATE_FULL_STR);
    }

    /**
     * Extract string date using user format
     * @param strDate date string
     * @param pattern date format
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Compare times with now
     * @param date1 compare date
     * @return
     */
    public static int compareDateWithNow(Date date1) {
        Date date2 = new Date();
        int rnum = date1.compareTo(date2);
        return rnum;
    }

    /**
     * Compare times with now (timestamp)
     * @param date1 compare timestamp
     * @return
     */
    public static int compareDateWithNow(long date1) {
        long date2 = dateToUnixTimestamp();
        if (date1 > date2) {
            return 1;
        } else if (date1 < date2) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Compare two times (timestamp comparison, return boolean)
     * If the current time is after the entered parameter time, false is returned.
     * If the current time is before the parameter time, it returns true
     * @param date
     * @return
     */
    public static boolean compareDateWithNowBoolean(long date) {
        try {
            return date > dateToUnixTimestamp();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Comparison of two times (string comparison, format yyyy-MM-dd HH:mm:ss)
     * @param date1
     * @return
     */
    public static int compareDateWithNow(String date1) {
        String nowTime = getNowTime();
        int num = nowTime.compareTo(date1);
        return num;
    }

    /**
     * Comparison of two times (string comparison, format yyyy-MM-dd)
     * @param date
     * @return
     */
    public static int compareDateWithNowYMD(String date) {
        String nowTime = getNowTime(DATE_SMALL_STR);
        int num = nowTime.compareTo(date);
        return num;
    }

    /**
     * Comparison of two times (string comparison, format HH:mm:ss)
     * @param date1
     * @return
     */
    public static int compareDateWithNowString(String date1) {
        String nowTime = getNowTime("HH:mm:ss");
        int num = nowTime.compareTo(date1);
        return num;
    }

    /**
     * Get the current system time
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
        return df.format(new Date());
    }

    /**
     * Get the current system time
     * @return
     */
    public static String getNowTime(String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        return df.format(new Date());
    }

    /**
     * Get current date
     * @return
     */
    public static Date getNowDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * Get current month
     * @return
     */
    public static String getJFPTime() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
        return df.format(new Date());
    }

    /**
     * Convert the specified date to a Unix timestamp
     * @param date StringDate to be converted: yyyy-MM-dd HH:mm:ss
     * @return long timestamp
     */
    public static long dateToUnixTimestamp(String date) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
        } catch (ParseException e) {
            AlterShieldLoggerManager.log("error", Loggers.DEFAULT, e, "DateUtil.dateToUnixTimestamp");

        }
        return timestamp;
    }

    /**
     * Convert the specified date to a Unix timestamp
     * @param date String Date to be converted: yyyy-MM-dd
     * @return long timestamp
     */
    public static long dateToUnixTimestamp(String date, String dateFormat) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
        } catch (ParseException e) {
            AlterShieldLoggerManager.log("error", Loggers.DEFAULT, e, "DateUtil.dateToUnixTimestamp");
        }
        return timestamp;
    }

    /**
     * Convert current date to Unix timestamp
     * @return long timestamp
     */
    public static long dateToUnixTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * Convert Unix timestamp to date
     * @param timestamp long timestamp
     * @return String - Date string
     */
    public static String unixTimestampToDateStr(long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }

    /**
     * Convert Unix timestamp to date
     * @param timestamp long timestamp
     * @return String - Date string
     */
    public static String unixTimestampToDateStr(long timestamp, String dateFormat) {
        SimpleDateFormat sd = new SimpleDateFormat(dateFormat);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }

    /**
     * Convert Unix timestamp to date
     * @param timestamp long timestamp
     * @return Date
     */
    public static Date unixTimestampToDate(long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return new Date(timestamp);
    }

    /**
     * Get the Date one week ago
     *
     * @return
     */
    public static Date getOneWeekBeforeDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        Date beforeDate = calendar.getTime();
        return beforeDate;
    }

    /**
     * Get the Date one day ago
     *
     * @return
     */
    public static Date getOneDayBeforeDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date beforeDate = calendar.getTime();
        return beforeDate;
    }

    /**
     * Get the Date a few days ago
     *
     * @param days
     * @return
     */
    public static Date getDaysBeforeDate(Integer days) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) - days);
        return now.getTime();
    }

    /**
     * Get Date a few minutes ago
     *
     * @param minutes
     * @return
     */
    public static Date getMinutesBeforeDate(Integer minutes) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - minutes);
        return now.getTime();
    }

    /**
     * Get the Date a few days later
     *
     * @param days
     * @return
     */
    public static Date getDaysAfterDate(Integer days) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
        return now.getTime();
    }

    /**
     * Get the Date a few seconds later
     *
     * @param seconds
     * @return
     */
    public static Date getSecondsAfterDate(Integer seconds) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.SECOND, now.get(Calendar.SECOND) + seconds);
        return now.getTime();
    }

    /**
     * Get Date a few minutes later
     *
     * @param minutes
     * @return
     */
    public static Date getMinutesAfterDate(Integer minutes) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minutes);
        return now.getTime();
    }

    /**
     * Get the timestamp a few seconds later
     *
     * @param seconds
     * @return
     */
    public static long getSecondsAfterMillis(Integer seconds) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.SECOND, now.get(Calendar.SECOND) + seconds);
        return now.getTimeInMillis();
    }

    /**
     * Get the timestamp a few minutes later
     *
     * @param minutes
     * @return
     */
    public static long getMinutesAfterMillis(Integer minutes) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minutes);
        return now.getTimeInMillis();
    }

    /**
     * Get the Date a few days after the specified date
     *
     * @param days
     * @return
     */
    public static Date getBaseDaysAfterDate(Date baseDate,Integer days) {
        Calendar now = Calendar.getInstance();
        now.setTime(baseDate);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
        return now.getTime();
    }

    /**
     * Get current time hour
     *
     * @return
     */
    public static Integer getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Get current time minutes
     *
     * @return
     */
    public static Integer getCurrentMinute() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MINUTE);
    }

    /**
     * Get current time seconds
     *
     * @return
     */
    public static Integer getCurrentMillisecond() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MILLISECOND);
    }

    /**
     * Get the difference in milliseconds between two dates
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static Long getDiffFromTwoDates(Date fromDate, Date toDate) {
        return toDate.getTime() - fromDate.getTime();
    }

    /**
     * Get the difference in days between two dates
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static String getDiffDaysFromTwoDates(Date fromDate, Date toDate) {
        return (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24) + "天";
    }

    /**
     * Get the number of days between two dates
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static Long getDiffDaysFromTwoDate(Date fromDate, Date toDate) {
        return (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * Format date time output string
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * Format date time output string
     * @param date
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * Get the start date of a specified number of days
     * @param dayIndex Date offset, such as yesterday, pass in -1
     * @return
     */
    public static Date getStartDateOfDay(int dayIndex) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, dayIndex);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    /**
     * Get the end date of a specified number of days
     * @param dayIndex Date offset, such as yesterday, pass in -1
     * @return
     */
    public static Date getEndDateOfDay(int dayIndex) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, dayIndex);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);

        return c.getTime();
    }

    /**
     * Get the end date of a specified number of days without milliseconds
     * @param dayIndex Date offset, such as yesterday, pass in -1
     * @return
     */
    public static Date getEndDateOfDayNotMilliSecond(int dayIndex) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, dayIndex);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    /**
     * Get the date n seconds later
     *
     * @param seconds number of seconds to delay
     * @return Date
     */
    public static Date getSecondsAfterNow(int seconds) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, seconds);
        return c.getTime();
    }
}
