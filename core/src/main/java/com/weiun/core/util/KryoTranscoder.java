package com.weiun.core.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 基于Kryo的序列化工具
 *
 * @author
 */
public class KryoTranscoder {

    private static ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            return new Kryo();
        }
    };


    /**
     * 序列化
     *
     * @param value
     * @return
     * @author weiun
     */
    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        try {
            kryos.get().writeObject(output, value);
            output.flush();
            rv = bos.toByteArray();
        } catch (Exception e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            try {
                bos.close();
                output.close();
            } catch (Exception e) {
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
     * @author weiun
     */
    public static <T> T deserialize(byte[] in, Class<T> clazz) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(in);
        Input input = new Input(bis);
        try {
            if (in != null) {
                return kryos.get().readObject(input, clazz);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                bis.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param in
     * @return
     * @throws Exception
     * @author weiun
     */
    public static Object deserialize(byte[] in) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(in);
        Input input = new Input(bis);
        try {
            if (in != null) {
                return kryos.get().readClassAndObject(input);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                bis.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
