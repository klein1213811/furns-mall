package com.yt.furns.web;

import com.yt.furns.javaBean.Furn;
import com.yt.furns.javaBean.Page;
import com.yt.furns.service.impl.FurnService;
import com.yt.furns.service.impl.FurnServiceImpl;
import com.yt.furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/customerFurnServlet"})
public class CustomerFurnServlet extends BasicServlet{
    private FurnService furnService = new FurnServiceImpl();
    /** 一个分页请求的接口
     * @param req
     * @param resp 
     **/
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        // 调用service方法，获取page对象
        Page<Furn> page = furnService.page(pageNo, pageSize);
        // 将page放入request域
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/customer/index.jsp").forward(req, resp);
    }

    /** 一个分页请求的接口
     * @param req
     * @param resp
     **/
    protected void pageByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        // 如果输入了name但是没有值，那么就表示显示所有的items
        // 如果没有输入name，表示接收到的是null，就不会显示了，所以这里做一个处理，保证在没有name值的情况下，name依然是""
        String name = req.getParameter("name");
        if (name == null){
            name = "";
        }

        // 调用service方法，获取page对象
        Page<Furn> page = furnService.pageByName(pageNo, pageSize, name);

        //
        StringBuilder url = new StringBuilder("customerFurnServlet?action=pageByName");
        if (!name.isEmpty()){
            url.append("&name=").append(name);
        }
        page.setUrl(url.toString());
        // 将page放入request域
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/customer/index.jsp").forward(req, resp);
    }
}
