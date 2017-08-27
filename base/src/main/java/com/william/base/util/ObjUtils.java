package com.william.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 对像工具类
 * Created by Administrator on 2017/8/6.
 */
public class ObjUtils {

    /**
     * Null attr to default.
     * 对像空属性转默认值
     *
     * @param object the object
     */
    public static void nullAttrToDefault(Object object) throws Exception {
        Class<?> aClass = object.getClass();
        Field[] field = aClass.getDeclaredFields();        //获取实体类的所有属性，返回Field数组
        for (int j = 0; j < field.length; j++) {     //遍历所有属性
            String name = field[j].getName();    //获取属性的名字
            name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
            Method getMethod = aClass.getMethod("get" + name);
            Method setMethod = null;
            Object value = getMethod.invoke(object);
            if (value == null) {
                String type = field[j].getGenericType().toString();    //获取属性的类型
                Object defaultValue = null;
                //如果type是类类型，则前面包含"class "，后面跟类名
                if ("class java.lang.String".equals(type)) {
                    defaultValue = "";
                    setMethod = aClass.getMethod("set" + name, String.class);
                }
                if ("class java.lang.Integer".equals(type)) {
                    defaultValue = 0;
                    setMethod = aClass.getMethod("set" + name, Integer.class);
                }
                if ("class java.lang.Short".equals(type)) {
                    defaultValue = (short) 0;
                    setMethod = aClass.getMethod("set" + name, Short.class);
                }
                if ("class java.lang.Long".equals(type)) {
                    defaultValue = 0l;
                    setMethod = aClass.getMethod("set" + name, Long.class);
                }
                if ("class java.lang.Float".equals(type)) {
                    defaultValue = 0.0f;
                    setMethod = aClass.getMethod("set" + name, Float.class);
                }
                if ("class java.lang.Double".equals(type)) {
                    defaultValue = 0.0d;
                    setMethod = aClass.getMethod("set" + name, Double.class);
                }
                if ("class java.lang.Boolean".equals(type)) {
                    defaultValue = false;
                    setMethod = aClass.getMethod("set" + name, Boolean.class);
                }
                if ("class java.util.Date".equals(type)) {
                    defaultValue = new Date();
                    setMethod = aClass.getMethod("set" + name, Date.class);
                }
                if (defaultValue != null && setMethod != null)
                    setMethod.invoke(object, defaultValue);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Stu stu = new Stu();
        System.out.println("str-->" + stu.getStr());
        System.out.println("inte-->" + stu.getInte());
        System.out.println("sho-->" + stu.getSho());
        System.out.println("lon-->" + stu.getLon());
        System.out.println("flo-->" + stu.getFlo());
        System.out.println("dou-->" + stu.getDou());
        System.out.println("dat-->" + stu.getDat());
        nullAttrToDefault(stu);
        System.out.println("str-->" + stu.getStr());
        System.out.println("inte-->" + stu.getInte());
        System.out.println("sho-->" + stu.getSho());
        System.out.println("lon-->" + stu.getLon());
        System.out.println("flo-->" + stu.getFlo());
        System.out.println("dou-->" + stu.getDou());
        System.out.println("dat-->" + stu.getDat());
    }

    public static class Stu {
        private String str;
        private Integer inte;
        private Short sho;
        private Long lon;
        private Float flo;
        private Double dou;
        private Date dat;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public Integer getInte() {
            return inte;
        }

        public void setInte(Integer inte) {
            this.inte = inte;
        }

        public Short getSho() {
            return sho;
        }

        public void setSho(Short sho) {
            this.sho = sho;
        }

        public Long getLon() {
            return lon;
        }

        public void setLon(Long lon) {
            this.lon = lon;
        }

        public Float getFlo() {
            return flo;
        }

        public void setFlo(Float flo) {
            this.flo = flo;
        }

        public Double getDou() {
            return dou;
        }

        public void setDou(Double dou) {
            this.dou = dou;
        }

        public Date getDat() {
            return dat;
        }

        public void setDat(Date dat) {
            this.dat = dat;
        }
    }

}
