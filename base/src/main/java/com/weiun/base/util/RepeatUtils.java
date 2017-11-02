package com.weiun.base.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weiun on 2017/10/26.
 * 防重复操作工具类
 */
public class RepeatUtils {


    private static Map<String, Long> map = null;


    private static void initMap() {
        if (map == null) {
            map = new HashMap<String, Long>();
        }
    }

    public static String getKey() {
        // 获取调用该方法的类名与方法名，做为检查 KEY
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        String key = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
        return key;
    }

    public static boolean check() {
        String key = getKey();
        return check(key);
    }

    public static boolean check(String key) {
        return check(key, 0);
    }

    public static boolean check(String key, int ms) {
        initMap();
        // 获取上次记录时间，为空则返回 false
        boolean result = true;
        Long aLong = map.get(key);
        Calendar now = Calendar.getInstance();
        if (aLong == null) {
            result = false;
        } else {
            // 判断时间差，如果在设定时间内则返回true,否则返回false
            long timeInMillis = now.getTimeInMillis();
            // 默认 1S
            if (ms == 0) {
                ms = 1000;
            }
            if (timeInMillis - aLong > ms) {
                result = false;
            } else {
                result = true;
            }
        }
        // 返回false则记录本次时间
        if (!result) {
            map.put(key, now.getTimeInMillis());
        }
        return result;
    }

    public static void clear() {
        if (map != null && !map.isEmpty()) {
            map.clear();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        check();
        check();
        check();
        check();
        check();
        check();
        check();
        Thread.sleep(1000L);
        check();
    }

}
