package com.william.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化工具
 *
 * @author
 */
public class Transcoder {

    /**
     * 序列化
     *
     * @param value
     * @return
     * @author Johnson.Jia
     */
    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            // os.writeObject(null);
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            try {
                os.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rv;
    }

    /**
     * 反序列化
     *
     * @param in
     * @return
     * @throws Exception
     * @author Johnson.Jia
     */
    public static Object deserialize(byte[] in) throws Exception {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                if (is != null) {
                    rv = is.readObject();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } catch (ClassNotFoundException e) {
            throw e;
        } finally {
            try {
                if (in != null) {
                    is.close();
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rv;
    }
}
