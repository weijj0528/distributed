package com.weiun.base.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by vijay on 2017/4/13.
 */
public class StrUtils {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Set<String> ids = new HashSet<String>();
        for (int i = 0; i < 10000000; i++) {
            String mid = idToMid(String.valueOf(i));
            ids.add(mid);
        }
        System.out.println(ids.size());
    }

    /**
     * The constant emailP.
     */
    public static final Pattern emailP = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");

    /**
     * The constant phoneP.
     */
    public static final Pattern phoneP = Pattern.compile("^1[3456789]\\d{9}$");

    /**
     * The constant Numbers.
     */
    public static final String[] Numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * Is email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isEmail(String email) {
        return emailP.matcher(email).matches();
    }

    /**
     * Is phone boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isPhone(String email) {
        return phoneP.matcher(email).matches();
    }

    /**
     * Random number str string.
     *
     * @param length the length
     * @return the string
     */
    public static String randomNumberStr(int length) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * 10);
            str.append(Numbers[index]);
        }
        return str.toString();
    }


    private static String str62keys = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    /**
     * Mid to id string.
     *
     * @param str62 the str 62
     * @return the string
     */
    public static String midToId(String str62) {
        String id = "";
        // 从最后往前以4字节为一组读取字符
        for (int i = str62.length() - 4; i > -4; i = i - 4) {
            int offset = i < 0 ? 0 : i;
            int len = i < 0 ? str62.length() % 4 : 4;
            long encode = encode62ToInt(left(str62, offset, len));
            String str = String.valueOf(encode);
            if (offset > 0) {
                // 若不是第一组，则不足7位补0
                str = leftPad(str, 7, '0');
            }
            id = str + id;
        }
        return id;
    }


    /**
     * Id to mid string.
     *
     * @param mid the mid
     * @return the string
     */
    public static String idToMid(String mid) {
        String result = "";
        for (int i = mid.length() - 7; i > -7; i -= 7) {
            int offset1 = (i < 0) ? 0 : i;
            int offset2 = i + 7;
            String num = intToEnode62(left(mid, offset1, offset2 - offset1));
            result = num + result;
        }
        return result;
    }


    private static String intToEnode62(String mid) {
        long int_mid = Long.parseLong(mid);
        String result = "";
        do {
            long a = int_mid % 62;
            result = str62keys.charAt((int) a) + result;
            int_mid = (int_mid - a) / 62;
        } while (int_mid > 0);

        return leftPad(result, 4, '0');
    }


    private static long encode62ToInt(String str62) {
        long i10 = 0;

        for (int i = 0; i < str62.length(); i++) {
            double n = str62.length() - i - 1;
            i10 += str62keys.indexOf(str62.charAt(i)) * Math.pow(62, n);
        }
        String temp = leftPad(String.valueOf(i10), 7, '0');
        try {
            i10 = Long.parseLong(temp);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            return i10;
        }
    }


    /**
     * Left pad string.
     * 补左边
     *
     * @param s       the s
     * @param size    the size
     * @param padChar the pad char
     * @return the string
     */
    public static String leftPad(String s, int size, char padChar) {
        int length = s.length();
        if (length == 0) {
            return s;
        }
        int pads = size - length;
        if (pads <= 0) {
            return s;
        }
        return padding(pads, padChar).concat(s);
    }


    private static String padding(int repeat, char padChar) {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

    /**
     * 左起截取字符串
     *
     * @param s     the s
     * @param begin the begin
     * @param len   the len
     * @return string
     */
    public static String left(String s, int begin, int len) {
        int length = length(s);
        if (length <= len) {
            return s;
        }
        return s.substring(begin, begin > 0 ? begin + len : len);
    }

    /**
     * 长度
     *
     * @param s the s
     * @return int
     */
    public static int length(String s) {
        return s != null ? s.length() : 0;
    }

}
