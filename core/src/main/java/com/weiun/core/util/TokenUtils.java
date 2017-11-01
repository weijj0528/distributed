package com.weiun.core.util;

import com.weiun.base.exception.ErrorMsgException;
import com.weiun.base.util.EncryptUtil;

/**
 * Created by Administrator on 2017/7/8.
 */
public class TokenUtils {

    public static final String CHARSET = "UTF-8";
    public static final String ENCRYPT_KEY = "NorthFish";

    public static String createKey(String uid) throws Exception {
        return EncryptUtil.encryptAES(uid, ENCRYPT_KEY);
    }

    public static String parseKeyToUid(String key) throws Exception {
        return EncryptUtil.decryptAES(key, ENCRYPT_KEY);
    }

    public static String createSid(String sessionId, String key) throws Exception {
        String sid = sessionId + "__" + key;
        return sid;
    }

    public static String parseSidToKey(String sid) throws Exception {
        String[] split = sid.split("__");
        if (split.length != 2) {
            throw new ErrorMsgException("SID错误，解析出错：" + sid);
        }
        return split[1];
    }

    public static String parseSidToUid(String sid) throws Exception {
        return parseKeyToUid(parseSidToKey(sid));
    }

    public static void main(String[] args) throws Exception {
        String key = createKey("1");
        System.out.println("key:" + key);
        String uid = parseKeyToUid(key);
        System.out.println("uid:" + uid);
        String sid = createSid("asdfasgasgwehs", key);
        System.out.println("sid:" + sid);
        String k = parseSidToKey(sid);
        System.out.println("k:" + k);
        String u = parseSidToUid(sid);
        System.out.println("u:" + u);
    }

}
