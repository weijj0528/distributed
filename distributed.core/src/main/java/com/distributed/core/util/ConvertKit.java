package com.distributed.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Throwables;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2016/10/21
 * Time: 10:13
 * Description: 对像转换工具类
 */
public class ConvertKit {

    /**
     * 类型转换，将普通对像解析为其他已知类型实例，字段相同匹配
     *
     * @param o
     * @param clazz
     * @return
     */
    public static <X> X convert(Object o, Class<X> clazz) {
        String json = JSON.toJSONString(o);
        return JSON.parseObject(json, clazz);
    }

    /**
     * Page info page info.
     * 封装分页对像
     *
     * @param <E>      the type parameter
     * @param list     the list
     * @param page     the page
     * @param pageSize the page size
     * @param total    the total
     * @return the page info
     */
    public static <E> PageInfo pageInfo(List<E> list, int page, int pageSize, long total) {
        if (list == null)
            list = new ArrayList<E>();
        Page<E> vOfferPage = new Page<E>(page, pageSize);
        for (E e : list) {
            vOfferPage.add(e);
        }
        vOfferPage.setTotal(total);
        return new PageInfo<E>(vOfferPage);
    }
}
