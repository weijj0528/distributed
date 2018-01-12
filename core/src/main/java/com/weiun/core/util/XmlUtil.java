package com.weiun.core.util;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by weiun on 2018/1/12.
 */
public class XmlUtil {

    public static JSONObject toJson(String xmlStr) {
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element rootElement = document.getRootElement();
            JSONObject jsonObject = new JSONObject();
            parseNote(rootElement, jsonObject);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //遍历当前节点下的所有节点
    private static void parseNote(Element element, JSONObject jsonObject) {
        //首先获取当前节点的所有属性节点
        List<Attribute> list = element.attributes();
        //遍历属性节点
        for (Attribute attribute : list) {
            jsonObject.put(attribute.getName(), attribute.getValue());
        }
        //同时迭代当前节点下面的所有子节点
        //使用递归
        Iterator<Element> iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            if (e.elements().isEmpty()) {
                jsonObject.put(e.getName(), e.getTextTrim());
            } else {
                JSONObject note = new JSONObject();
                parseNote(e, note);
                jsonObject.put(e.getName(), note);
            }
        }
    }

}
