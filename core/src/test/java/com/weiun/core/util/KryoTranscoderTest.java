package com.weiun.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weiun.base.util.Transcoder;

/**
 * Created by weiun on 2017/12/19.
 */
public class KryoTranscoderTest {

    @org.junit.Test
    public void serialize() throws Exception {
        // 反序列化对比 均衡 占用字节 Kryo 优胜
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "韦爵爷");
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Transcoder.serialize(jsonObject);
        }
        long t2 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            KryoTranscoder.serialize(jsonObject);
        }
        long t3 = System.currentTimeMillis();
        System.out.println("Transcoder:" + (t2 - t1));
        System.out.println("KryoTranscoder:" + (t3 - t2));
    }

    @org.junit.Test
    public void deserialize() throws Exception {
        // 反序列化对比 Kryo 优胜
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "韦爵爷");
        byte[] serialize = Transcoder.serialize(jsonObject);
        System.out.println("serialize:" + serialize.length);
        long t1 = System.currentTimeMillis();
        Object deserialize = null;
        for (int i = 0; i < 100000; i++) {
            deserialize = Transcoder.deserialize(serialize);
        }
        System.out.println(JSON.toJSONString(deserialize));
        long t2 = System.currentTimeMillis();
        byte[] kryo = KryoTranscoder.serialize(jsonObject);
        System.out.println("kryo:" + kryo.length);
        for (int i = 0; i < 100000; i++) {
            jsonObject = KryoTranscoder.deserialize(kryo);
        }
        System.out.println(JSON.toJSONString(jsonObject));
        long t3 = System.currentTimeMillis();
        System.out.println("Transcoder:" + (t2 - t1));
        System.out.println("KryoTranscoder:" + (t3 - t2));
    }

    @org.junit.Test
    public void xml() throws Exception {
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  \n" +
                "<CONFIG>  \n" +
                "    <VALUE>  \n" +
                "        <!-- mysql连接设置 -->  \n" +
                "        <server>127.0.0.1</server>  \n" +
                "        <dbname>users</dbname>  \n" +
                "        <user>root</user>  \n" +
                "        <pass>pass</pass>  \n" +
                "        <port>3306</port>  \n" +
                "    </VALUE>  \n" +
                "</CONFIG> ";
        JSONObject jsonObject = XmlUtil.toJson(s);
        System.out.println(jsonObject.toJSONString());
    }

}