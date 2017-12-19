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
        Kryo kryo = kryos.get();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);
        kryo.writeClassAndObject(output, value);
        output.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 反序列化
     *
     * @param in
     * @return
     * @throws Exception
     * @author weiun
     */
    public static <T> T deserialize(byte[] in) throws Exception {
        Kryo kryo = kryos.get();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(in);
        Input input = new Input(byteArrayInputStream);
        input.close();
        return (T) kryo.readClassAndObject(input);
    }

}
