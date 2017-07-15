package com.william.base.util;

import org.apache.commons.lang3.RandomUtils;

import java.util.regex.Pattern;

/**
 * Created by vijay on 2017/4/13.
 */
public class StrUtils {

    private static Pattern emailP = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");

    private static Pattern phoneP = Pattern.compile("^1[3456789]\\d{9}$");

    private static String[] Numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static boolean isEmail(String email) {
        return emailP.matcher(email).matches();
    }

    public static boolean isPhone(String email) {
        return phoneP.matcher(email).matches();
    }

    public static String randomNumberStr(int length) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * 10);
            str.append(Numbers[index]);
        }
        return str.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.err.println(randomNumberStr(4));
        }
    }

}
