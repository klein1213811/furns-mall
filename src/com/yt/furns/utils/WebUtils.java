package com.yt.furns.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
    public static final String FURN_IMG_DIRECTORY = "assets/images/product-image";
    /** 判断一个请求是不是一个Ajax请求
     * @param request Htt请求
     * @return boolean
     **/
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
