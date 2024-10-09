package com.yt.furns.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class DataUtils {
    public static <T> T copyParamToBean(Map value, T bean) {
        try {
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

    // 将数字型字符串转为整数，如果过不能转的话就返回一个默认值
    public static int parseInt(String val, int defaultValue) {

        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
//            System.out.println(val + "form is wrong");
        }

        return defaultValue;
    }
}
