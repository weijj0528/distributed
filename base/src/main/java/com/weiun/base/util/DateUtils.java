package com.weiun.base.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by vijay on 2017/4/13.
 */
public class DateUtils {
    /**
     * 最大日期
     */
    private final static int[] maxDay = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * 输入的年月日是否为正确日期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean isRealDate(int year, int month, int day) {

        if (year < 0 || month < 1 || day < 1)
            return false;

        if (month > 12 || day > 31)
            return false;

        if (day > maxDay[month - 1])
            return false;

        if (month == 2 && day > 28) {

            if (year % 4 != 0 || year % 100 == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 根据指定日期 会员续费天数 如果指定日期小于今天 就以今日为起点
     *
     * @param specified 会员日期
     * @param number    购买数量 续费数量
     * @param day       每次多少天 续费单位
     * @return
     * @throws Exception
     * @author Johnson.Jia
     */
    public static long userPeriod(long specified, int number, int day) {
        long today = System.currentTimeMillis();
        if (specified < today) {
            specified = today;
        } else {
            specified += 1000;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(specified);
        cal.add(Calendar.DAY_OF_MONTH, day * number - 1); // 增加多少天
        cal.set(Calendar.HOUR_OF_DAY, 23); // 设置为当日23点
        cal.set(Calendar.MINUTE, 59); // 59分
        cal.set(Calendar.SECOND, 59); // 59秒
        cal.set(Calendar.MILLISECOND, 0); // 0毫秒
        return cal.getTimeInMillis();
    }

    /**
     * 格式化    当前时间   yyyy-MM-dd HH:mm:ss
     *
     * @return
     * @author Johnson.Jia
     * @date 2016年11月18日 下午7:37:57
     */
    public static String getDateToString() {
        return getDateToString(null, System.currentTimeMillis());
    }

    /**
     * 格式化  时间 yyyy-MM-dd HH:mm:ss
     *
     * @param millis
     * @return
     * @author Johnson.Jia
     * @date 2016年11月18日 下午7:37:57
     */
    public static String getDateToString(long millis) {
        return getDateToString(null, millis);
    }

    /**
     * 将时间戳转换为格式化的字符串
     *
     * @param format 默认 yyyy-MM-dd HH:mm:ss
     * @param millis
     * @return
     */
    public static String getDateToString(String format, long millis) {
        Date date = new Date(millis);
        if (StringUtils.isBlank(format))
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);

    }

    /**
     * 返回指定日期格式字符串的时间戳
     *
     * @param format eg: yyyy-MM-dd HH:mm
     * @param time
     * @return
     */
    public static long parseDateString(String format, String time) {

        try {

            return new SimpleDateFormat(format).parse(time).getTime();

        } catch (Exception e) {

            return 0;

        }

    }

    /**
     * 判断时间戳是否为今天
     *
     * @param millis
     * @return
     * @throws Exception
     */
    public static boolean isToday(long millis) throws Exception {
        long oneday = 24 * 60 * 60 * 1000l;
        long utc = 8 * 60 * 60 * 1000l;
        return (System.currentTimeMillis() + utc) / oneday - (millis + utc) / oneday == 0;
    }

    /**
     * 获取指定日期时间戳
     *
     * @param dateFormat
     * @param dateString
     * @return
     */
    public static long getTimeStamp(String dateFormat, String dateString) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            Date date = (Date) df.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 获取 当前时间 增加相应月数的时间戳
     *
     * @param month
     * @return
     * @author Johnson.Jia
     * @date 2015年11月20日 下午8:27:32
     */
    public static long getDateTimeByMonth(long millis, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        cal.add(Calendar.MONTH, month); // 增加自然月
        return cal.getTimeInMillis();
    }

    /**
     * 返回n天后的日期零点时间戳
     *
     * @param days 可以为任何整数，负数表示前N天，正数表示后N天
     * @return
     * @author Johnson.Jia
     */
    public static long getFutureInMillis(int days) {
        return getFutureInMillis(-1, days);
    }

    /**
     * 返回 指定日期 n天后的日期零点时间戳
     *
     * @param millis 大于 0 有效
     * @param days   可以为任何整数，负数表示前N天，正数表示后N天
     * @return
     * @author Johnson.Jia
     */
    public static long getFutureInMillis(long millis, int days) {
        Calendar cal = Calendar.getInstance();
        if (millis > 0) {
            cal.setTimeInMillis(millis);
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + days); // 当前日期增加天数
        cal.set(Calendar.HOUR_OF_DAY, 0); // 设置为当日0点
        cal.set(Calendar.MINUTE, 0); // 0分
        cal.set(Calendar.SECOND, 0); // 0秒
        cal.set(Calendar.MILLISECOND, 0); // 0毫秒
        return cal.getTimeInMillis();
    }

    /**
     * 获取当前 日期 周一初始 时间
     *
     * @param millis
     * @return
     * @author Johnson.Jia
     * @date 2016年4月5日 上午10:47:32
     */
    public static long getWeekInitTime(long millis) {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        if (millis > 0) {
            cal.setTimeInMillis(millis);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0); // 设置为当日0点
        cal.set(Calendar.MINUTE, 0); // 0分
        cal.set(Calendar.SECOND, 0); // 0秒
        cal.set(Calendar.MILLISECOND, 0); // 0毫秒
        return cal.getTimeInMillis();
    }

    /**
     * 获取指定日期内的 周一 至 周末的时间
     *
     * @param millis 小于等于 0 采用当前时间
     * @return
     * @author Johnson.Jia
     */
    public static Map<Integer, Integer> getWeekdays(long millis) {
        Map<Integer, Integer> result = new LinkedHashMap<Integer, Integer>();
        Calendar calendar = Calendar.getInstance();
        if (millis > 0) {
            calendar.setTimeInMillis(millis);
        }
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        for (int i = 1; i < 8; i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            result.put(i,
                    Integer.valueOf(dateFormat.format(calendar.getTime())));
            calendar.add(Calendar.DATE, 1);
        }
        return result;
    }

    /**
     * 根据生日 计算年龄
     *
     * @param birthday 2016-05-66 yyyy-MM-dd
     * @return
     * @author Johnson.Jia
     */
    public static Integer getAge(String birthday) {
        long time = System.currentTimeMillis();
        long day = (time - getTimeStamp("yyyy-MM-dd", birthday))
                / (24 * 60 * 60 * 1000) + 1;
        String year = new DecimalFormat("#").format(day / 365f);
        return Integer.valueOf(year);
    }

    /**
     * 获取当指定日期开始时间
     *
     * @return
     */
    public static Timestamp getFirstDayOfDay(Calendar current) {
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.clear(Calendar.MINUTE);
        current.clear(Calendar.SECOND);
        return Timestamp.valueOf(formatDate(current));
    }

    /****
     *获取当前 日期 季度
     * @param millis
     * @return
     */
    public static int getQuarter(long millis) {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        if (millis > 0) {
            cal.setTimeInMillis(millis);
        }
        int quarter = 0;
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        if (currentMonth >= 1 && currentMonth <= 3) {
            quarter = 1;
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            quarter = 2;
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            quarter = 3;
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            quarter = 4;
        }
        return quarter;
    }

    public static int getCurrentMonth() {
        Calendar current = Calendar.getInstance();
        int i = current.get(Calendar.MONTH);
        return i + 1;
    }

    public static int getCurrentYear() {
        Calendar current = Calendar.getInstance();
        int i = current.get(Calendar.YEAR);
        return i;
    }

    /**
     * 获取当指定日期开始时间
     *
     * @return
     */
    public static Timestamp getEndDayOfDay(Calendar current) {
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.clear(Calendar.MINUTE);
        current.clear(Calendar.SECOND);
        current.add(Calendar.DATE, 1);
        current.add(Calendar.SECOND, -1);
        return Timestamp.valueOf(formatDate(current));
    }

    /**
     * 获取当指定日期所在周的第一天时间
     *
     * @return
     */
    public static Timestamp getFirstDayOfWeek(Calendar current) {
        // 中国默认周一为第一天
        current.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.clear(Calendar.MINUTE);
        current.clear(Calendar.SECOND);
        return Timestamp.valueOf(formatDate(current));
    }

    /**
     * 获取指定日期所在周的结束时间
     *
     * @return
     */
    public static Timestamp getEndDayOfWeek(Calendar current) {
        // 中国默认周一为第一天
        current.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        current.add(Calendar.DATE, 7);
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.clear(Calendar.MINUTE);
        current.clear(Calendar.SECOND);
        current.add(Calendar.SECOND,-1);
        return Timestamp.valueOf(formatDate(current));
    }

    /**
     * 获取指定日期所在月的第一天时间
     *
     * @return
     */
    public static Timestamp getFirstDayOfMonth(Calendar current) {
        current.add(Calendar.MONTH, 0);
        current.set(Calendar.DAY_OF_MONTH, 1);
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.clear(Calendar.MINUTE);
        current.clear(Calendar.SECOND);
        return Timestamp.valueOf(formatDate(current));
    }

    /**
     * 获取指定日期所在月的结束时间
     *
     * @return
     */
    public static Timestamp getEndDayOfMonth(Calendar current) {
        current.add(Calendar.MONTH, 1);
        current.set(Calendar.DAY_OF_MONTH, 1);
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.clear(Calendar.MINUTE);
        current.clear(Calendar.SECOND);
        current.add(Calendar.SECOND,-1);
        return Timestamp.valueOf(formatDate(current));
    }

    /**
     * 获取指定日期所在季度的第一天时间
     *
     * @return
     */
    public static Timestamp getFirstDayOfQuarter(Calendar current) {
        int quarter = getQuarter(current.getTimeInMillis());
        switch (quarter) {
            case 1:
                return getFirstDayOfYear(current);
            case 2:
                current.set(Calendar.MONTH, 3); // 4月开始时间
                return getFirstDayOfMonth(current);
            case 3:
                current.set(Calendar.MONTH, 6); // 7月开始时间
                return getFirstDayOfMonth(current);
            case 4:
                current.set(Calendar.MONTH, 9); // 10月开始时间
                return getFirstDayOfMonth(current);
        }
        return null;
    }

    /**
     * 获取指定日期所在季度的结束时间
     *
     * @return
     */
    public static Timestamp getEndDayOfQuarter(Calendar current) {
        int quarter = getQuarter(current.getTimeInMillis());
        switch (quarter) {
            case 1:
                current.set(Calendar.MONTH, 2); // 3月结束时间
                return getEndDayOfMonth(current);
            case 2:
                current.set(Calendar.MONTH, 5); // 6月结束时间
                return getEndDayOfMonth(current);
            case 3:
                current.set(Calendar.MONTH, 8); // 9月结束时间
                return getEndDayOfMonth(current);
            case 4:
                return getEndDayOfYear(current);
        }
        return null;
    }



    /**
     * 获取指定日期所在年的第一天
     *
     * @return
     */
    public static Timestamp getFirstDayOfYear(Calendar current) {
        int currentYear = current.get(Calendar.YEAR);
        current.clear();
        current.set(Calendar.YEAR, currentYear);
        return Timestamp.valueOf(formatDate(current));
    }

    /**
     * 获取指定日期所在年的结束时间
     *
     * @return
     */
    public static Timestamp getEndDayOfYear(Calendar current) {
        int currentYear = current.get(Calendar.YEAR);
        current.clear();
        current.set(Calendar.YEAR, currentYear + 1);
        current.add(Calendar.SECOND,-1);
        return Timestamp.valueOf(formatDate(current));
    }



    /**
     * 格式化时间
     *
     * @return String 时间字符串
     */
    public static String formatDate(Calendar calendar) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = f.format(calendar.getTime());
        return sDate;
    }


    // TEST
    public static void main(String args[]) throws Exception {
        System.out.println(System.currentTimeMillis());
        System.out.println(parseDateString("yyyy-MM-dd HH:mm:ss",
                "2016-09-16 00:00:00"));
        System.out.println(getDateToString("yyyy-MM-dd HH:mm:ss",
                1472116187303l));
    }

}
