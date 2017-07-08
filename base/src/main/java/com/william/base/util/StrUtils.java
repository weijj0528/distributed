package com.william.base.util;

import java.util.regex.Pattern;

/**
 * Created by vijay on 2017/4/13.
 */
public class StrUtils {

    private static Pattern emailP = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");

    private static Pattern phoneP = Pattern.compile("^1[3456789]\\d{9}$");

    public static boolean isEmail(String email) {
        return emailP.matcher(email).matches();
    }

    public static boolean isPhone(String email) {
        return phoneP.matcher(email).matches();
    }

}
