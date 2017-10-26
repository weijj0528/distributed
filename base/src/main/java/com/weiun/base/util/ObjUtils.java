package com.weiun.base.util;

import com.alibaba.fastjson.JSONArray;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

/**
 * 对像工具类
 * Created by Administrator on 2017/8/6.
 */
public class ObjUtils {

    /**
     * Tests if a Class represents an array or Collection.
     */
    public static boolean isArray( Class clazz ) {
        return clazz != null
                && (clazz.isArray() || Collection.class.isAssignableFrom( clazz ) || (JSONArray.class.isAssignableFrom( clazz )));
    }

    /**
     * Tests if obj is an array or Collection.
     */
    public static boolean isArray( Object obj ) {
        if( (obj != null && obj.getClass()
                .isArray()) || (obj instanceof Collection) || (obj instanceof JSONArray) ){
            return true;
        }
        return false;
    }

    /**
     * Tests if Class represents a Boolean or primitive boolean
     */
    public static boolean isBoolean( Class clazz ) {
        return clazz != null
                && (Boolean.TYPE.isAssignableFrom( clazz ) || Boolean.class.isAssignableFrom( clazz ));
    }

    /**
     * Tests if obj is a Boolean or primitive boolean
     */
    public static boolean isBoolean( Object obj ) {
        if( (obj instanceof Boolean) || (obj != null && obj.getClass() == Boolean.TYPE) ){
            return true;
        }
        return false;
    }

    /**
     * Tests if Class represents a primitive double or wrapper.<br>
     */
    public static boolean isDouble( Class clazz ) {
        return clazz != null
                && (Double.TYPE.isAssignableFrom( clazz ) || Double.class.isAssignableFrom( clazz ));
    }


    /**
     * Returns trus if str represents a valid Java identifier.
     */
    public static boolean isJavaIdentifier( String str ) {
        if( str.length() == 0 || !Character.isJavaIdentifierStart( str.charAt( 0 ) ) ){
            return false;
        }
        for( int i = 1; i < str.length(); i++ ){
            if( !Character.isJavaIdentifierPart( str.charAt( i ) ) ){
                return false;
            }
        }
        return true;
    }


    /**
     * Tests if Class represents a primitive number or wrapper.<br>
     */
    public static boolean isNumber( Class clazz ) {
        return clazz != null
                && (Byte.TYPE.isAssignableFrom( clazz ) || Short.TYPE.isAssignableFrom( clazz )
                || Integer.TYPE.isAssignableFrom( clazz ) || Long.TYPE.isAssignableFrom( clazz )
                || Float.TYPE.isAssignableFrom( clazz ) || Double.TYPE.isAssignableFrom( clazz ) || Number.class.isAssignableFrom( clazz ));
    }

    /**
     * Tests if obj is a primitive number or wrapper.<br>
     */
    public static boolean isNumber( Object obj ) {
        if( (obj != null && obj.getClass() == Byte.TYPE)
                || (obj != null && obj.getClass() == Short.TYPE)
                || (obj != null && obj.getClass() == Integer.TYPE)
                || (obj != null && obj.getClass() == Long.TYPE)
                || (obj != null && obj.getClass() == Float.TYPE)
                || (obj != null && obj.getClass() == Double.TYPE) ){
            return true;
        }

        return obj instanceof Number;
    }

    /**
     * Tests if Class represents a String or a char
     */
    public static boolean isString( Class clazz ) {
        return clazz != null
                && (String.class.isAssignableFrom( clazz ) || (Character.TYPE.isAssignableFrom( clazz ) || Character.class.isAssignableFrom( clazz )));
    }

    /**
     * Tests if obj is a String or a char
     */
    public static boolean isString( Object obj ) {
        if( (obj instanceof String)
                || (obj instanceof Character)
                || (obj != null && (obj.getClass() == Character.TYPE || String.class.isAssignableFrom( obj.getClass() ))) ){
            return true;
        }
        return false;
    }

    /**
     * Tests if the String possibly represents a valid JSON String.<br>
     * Valid JSON strings are:
     * <ul>
     * <li>"null"</li>
     * <li>starts with "[" and ends with "]"</li>
     * <li>starts with "{" and ends with "}"</li>
     * </ul>
     */
    public static boolean mayBeJSON( String string ) {
        return string != null
                && ("null".equals( string )
                || (string.startsWith( "[" ) && string.endsWith( "]" )) || (string.startsWith( "{" ) && string.endsWith( "}" )));
    }

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
