package com.wangjunneil.schedule.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by wangjun on 8/2/16.
 */
public final class DateTimeUtil {
    private DateTimeUtil() {}

    public static String nowDateString(String format) {
        return dateFormat(new Date(), format);
    }

    public static String preDateString(String format, int days) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        now = calendar.getTime();
        return dateFormat(now, format);
    }

    public static String dateFormat(Date date, String format) {
        if (!StringUtil.isEmpty(date)) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } else {
            return null;
        }
    }

    public static String dateSetTimeZone(Date date, String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, -8);
        Date dt = cal.getTime();
        return df.format(dt);
    }

    public static Date getExpireDate(long time, int expireIn) {
        Date date = new Date(time + expireIn * 1000);
        return date;
    }

    public static Date getExpireDate2(long time, long expireIn) {
        Date date = new Date(time + expireIn * 1000);
        return date;
    }

    public static Date getExpireDate(long time) {
        Date date = new Date();
        date.setTime(time * 1000);
        return date;
    }

    public static Date parseDateString(String dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date formatDateString(String dateTime, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
        return calendar.getTime();
    }

    public static Date addDayNow(int day) {
        return addDay(now(), day);
    }

    public static Date addHour(Date date, int hour) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, hour);
        return ca.getTime();
    }

    public static Date now() {
        return new Date();
    }

}
