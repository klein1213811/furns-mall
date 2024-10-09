package com.yt.furns.web;

import com.mysql.cj.util.DataTypeUtil;
import com.yt.furns.javaBean.Furn;
import com.yt.furns.javaBean.Page;
import com.yt.furns.service.impl.FurnService;
import com.yt.furns.service.impl.FurnServiceImpl;
import com.yt.furns.utils.DataUtils;
import com.yt.furns.utils.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {"/manage/furnServlet"}) // 这里写manage的原因是为了以后使用过滤器
public class FurnServlet extends BasicServlet{
    FurnService furnService = new FurnServiceImpl();
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println(111); 如果使用了BasicServlet就必须要有action，在测试阶段可以再url上加上?action=list
        List<Furn> furns = furnService.queryAllFurn();
        // 将拿到的集合放入到req域中
        req.setAttribute("furns", furns);
        req.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(req, resp);
    }

    /** 添加家居到表中
     * @param req
     * @param resp
     **/
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取添加家居信息
        String name = req.getParameter("name");
        String maker = req.getParameter("maker");
        String price = req.getParameter("price");
        String sales = req.getParameter("sales");
        String stock = req.getParameter("stock");

        // 后端对输入的数据格式进行校验
        Furn furn = null;
        try {
            furn = new Furn(null, name, maker, new BigDecimal(price), Integer.parseInt(sales), Integer.parseInt(stock), "assets/images/product-image/default.jpg");
        } catch (NumberFormatException e) {
            req.setAttribute("msg", "输入的格式不对");
            req.getRequestDispatcher("/views/manage/furn_add.jsp").forward(req, resp);
            return;
        }

//        // 使用BeanUtils添加数据
//        Furn furn = null;
//        try {
//            // 前提时表单的数据的名字和javaBean属性名保持一致才能封装
//            BeanUtils.populate(furn, req.getParameterMap());
//        } catch (Exception e) {
//            req.setAttribute("msg", "输入的格式不对");
//            req.getRequestDispatcher("/views/manage/furn_add.jsp").forward(req, resp);
//            return;
//        }
//
        furnService.addFurn(furn);

        // 请求转发到家居显示页面 这里不能使用请求转发，要使用重定向，不然当用户刷新页面时会重复提交表单
//        req.getRequestDispatcher("/manage/furnServlet?action=list").forward(req, resp);
//        resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list"); // 这里的地址是让浏览器解析的，所以这里的地址要要是完整地址
        resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }
    /** 根据Id删除DB中的家居
     * @param req
     * @param resp
     **/
    protected void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        furnService.deleteFurnById(id);
//        resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
        resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    /** 请求家居的回显
     * @param req
     * @param resp
     **/
    protected void showFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int i = DataUtils.parseInt(req.getParameter("id"), 0);
        Furn furn = furnService.queryFurnById(i);
        req.setAttribute("furn", furn); // 将furn放入
        req.getRequestDispatcher("/views/manage/furn_update.jsp").forward(req, resp);
    }


//    /** 将在浏览器进行修改后的表单更新到数据库（修改前）
//     * @param req
//     * @param resp
//     **/
//    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // 将提交修改的家居信息封装为Furn对象
//        Furn furn = DataUtils.copyParamToBean(req.getParameterMap(), new Furn());
//        furnService.updateFurn(furn);
////        resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list"); // 重定向到显示页面
//        // 这里考虑分页并带上pageNo
//        resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo")); // 重定向到显示页面
//    }

    /** 将在浏览器进行修改后的表单更新到数据库
     * @param req
     * @param resp
     **/
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //将提交修改的家居信息，封装成Furn对象

        //如果你的表单是enctype="multipart/form-data", req.getParameter("id") 得不到id
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到对应furn对象[从db中获取]
        Furn furn = furnService.queryFurnById(id);
        //todo 做一个判断 furn为空就不处理

        //1. 判断是不是文件表单(enctype="multipart/form-data")
        if (ServletFileUpload.isMultipartContent(req)) {
            //2. 创建 DiskFileItemFactory 对象, 用于构建一个解析上传数据的工具对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            //3. 创建一个解析上传数据的工具对象

            ServletFileUpload servletFileUpload =
                    new ServletFileUpload(diskFileItemFactory);
            //解决接收到文件名是中文乱码问题
            servletFileUpload.setHeaderEncoding("utf-8");

            //4. 关键的地方, servletFileUpload 对象可以把表单提交的数据text / 文件
            //   将其封装到 FileItem 文件项中
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                //遍历，并分别处理=> 自然思路
                for (FileItem fileItem : list) {
                    //判断是不是一个文件=> 你是OOP程序员
                    if (fileItem.isFormField()) {//如果是true就是文本 input text(普通的表单字段)

                        if ("name".equals(fileItem.getFieldName())) {//家居名
                            furn.setName(fileItem.getString("utf-8"));
                        } else if ("maker".equals(fileItem.getFieldName())) {//制造商
                            furn.setMaker(fileItem.getString("utf-8"));
                        } else if ("price".equals(fileItem.getFieldName())) {//价格
                            furn.setPrice(new BigDecimal(fileItem.getString()));
                        } else if ("sales".equals(fileItem.getFieldName())) {//销量
                            furn.setSales(Integer.parseInt(fileItem.getString()));
                        } else if ("stock".equals(fileItem.getFieldName())) {//库存
                            furn.setStock(Integer.parseInt(fileItem.getString()));
                        }

                    } else {//是一个文件

                        //获取上传的文件的名字
                        String name = fileItem.getName();

                        //如果用户没有选择新的图片, name = ""
                        if (!"".equals(name)) {
                            //1.指定一个目录 , 就是我们网站工作目录下
                            String filePath = "/" + WebUtils.FURN_IMG_DIRECTORY;
                            //2. 获取到完整目录 [io/servlet基础]
                            String fileRealPath =
                                    req.getServletContext().getRealPath(filePath);

                            //3. 创建这个上传的目录=> 创建目录?=> Java基础
                            File fileRealPathDirectory = new File(fileRealPath);
                            if (!fileRealPathDirectory.exists()) {//不存在，就创建
                                fileRealPathDirectory.mkdirs();//创建
                            }

                            //4. 将文件拷贝到fileRealPathDirectory目录
                            //   构建一个上传文件的完整路径 ：目录+文件名
                            //   对上传的文件名进行处理, 前面增加一个前缀，保证是唯一即可, 不错
                            name = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + name;
                            String fileFullPath = fileRealPathDirectory + "/" + name;
                            fileItem.write(new File(fileFullPath)); //保存

                            fileItem.getOutputStream().close();//关闭流

                            //更新家居的图片路径
                            furn.setImgPath(WebUtils.FURN_IMG_DIRECTORY + "/" + name);
                            //todo 删除原来旧的不用的图片
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是文件表单...");
        }

        //更新furn对象->DB
        furnService.updateFurn(furn);
        System.out.println("success!");
        //可以请求转发到更新成功的页面..
        req.getRequestDispatcher("/views/manage/update_ok.jsp").forward(req, resp);
    }

    /** 处理分页请求显示
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
        req.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(req, resp);
    }
}
