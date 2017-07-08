package com.distributed.core.util;

import com.distributed.core.exception.ErrorMsgException;
import com.william.base.util.EncryptUtil;

/**
 * Created by Administrator on 2017/7/8.
 */
public class TokenUtils {

    public static final String CHARSET = "UTF-8";
    public static final String ENCRYPT_KEY = "NorthFish";

    public static String createKey(String uid) throws Exception {
        byte[] bytes = EncryptUtil.encryptAES(uid, ENCRYPT_KEY);
        return new String(bytes, CHARSET);
    }

    public static String parseKeyToUid(String key) throws Exception {
        byte[] bytes = EncryptUtil.decryptAES(key.getBytes(), ENCRYPT_KEY);
        return new String(bytes, CHARSET);
    }

    public static String createSid(String sessionId, String key) throws Exception {
        String sid = sessionId + "__" + key;
        byte[] bytes = EncryptUtil.encryptAES(sid, ENCRYPT_KEY);
        return new String(bytes, CHARSET);
    }

    public static String parseSidToKey(String sid) throws Exception {
        byte[] bytes = EncryptUtil.decryptAES(sid.getBytes(), ENCRYPT_KEY);
        sid = new String(bytes, CHARSET);
        String[] split = sid.split("__");
        if (split.length != 2)
            throw new ErrorMsgException("SID错误，解析出错：" + sid);
        return split[1];
    }

    public static String parseSidToUid(String sid) throws Exception {
        return parseKeyToUid(parseSidToKey(sid));
    }

}
