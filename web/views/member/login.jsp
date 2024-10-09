<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>韩顺平教育-家居网购</title>
    <%--    <base href="http://localhost:8080/furns/">--%>
    <base href="<%=request.getContextPath() + "/"%>">
    <meta username="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css"/>
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function () {
            // 对用户名输入框绑定一个失去焦点的响应
            $("#username").blur(function (){
                // 获取输入的用户名
                var username = this.value;
                // 发出ajax请求
                $.getJSON(
                    "memberServlet",
                    "action=isExistUsername&username=" + username,
                    function (data){
                        if(data.isExist){
                            $("span.errorMsg").text("用户名已存在!")
                        } else {
                            $("span.errorMsg").text("用户名可用!")
                        }
                    }
                )
            })

            // 模拟一个点击事件选中注册
            if("${requestScope.active}" == "register"){
                $("#register_tab")[0].click();
            }

            // 绑定会员注册按钮，对输入的信息进行验证
            $("#sub-btn").click(function () {
                // 获取输入的用户名、密码、邮箱进行验证
                var usernameVal = $("#username").val()
                // 编写正则表达式进行验证
                var usernamePattern = /^\w{6,10}$/
                if (!usernamePattern.test(usernameVal)) {
                    // 使用前端提供的span来报错
                    $("span[class='errorMsg']").text("用户名格式不对，需要6-10的字符");
                    return false;
                }
                // 完成密码校验
                var passwordVal = $("#password").val();
                if (!usernamePattern.test(passwordVal)) {
                    // 使用前端提供的span来报错
                    $("span[class='errorMsg']").text("密码格式不对，需要6-10 的字符");
                    return false;
                }
                // 输入的两次密码相同
                var repwdVal = $("#repwd").val();
                if (repwdVal != passwordVal) {
                    $("span[class='errorMsg']").text("两次输入的密码不相等");
                    return false;
                }
                // 验证邮箱
                var emailVal = $("#email").val();
                var emailPattern = /^[\w-]+@([a-zA-Z]+\.)+[a-zA-Z]+$/
                if (!emailPattern.test(emailVal)) {
                    $("span[class='errorMsg']").text("邮箱格式不对");
                    return false;
                }

                // 验证码不能为空前端校验
                var codeText2 = $("#code2").val();
                codeText2 = $.trim(codeText2); // 去掉两端空格
                if (codeText2 == null || codeText2 == "") {
                    $("span[class='errorMsg']").text("验证码不能为空");
                    return false;
                }

                $("span[class='errorMsg']").text("验证通过");
                return true;
            })

            // 对验证码图片将那些处理，点击刷新验证码
            $("#codeImg1").click(function () {
                // 在url没有发生变化的时候，图片是不会发出请求的，也就不会刷新，所以这里在后面加上一个没有的参数
                // 时间来改变url
                this.src = "<%=request.getContextPath()%>/kaptchaServlet?d=" + new Date();
            })
            $("#codeImg2").click(function () {
                // 在url没有发生变化的时候，图片是不会发出请求的，也就不会刷新，所以这里在后面加上一个没有的参数
                // 时间来改变url
                this.src = "<%=request.getContextPath()%>/kaptchaServlet?d=" + new Date();
            })

            // 验证码不能为空前端校验
            $("#log-btn").click(function () {
                // 验证码不能为空前端校验
                var codeText1 = $("#code1").val();
                codeText1 = $.trim(codeText1); // 去掉两端空格
                if (codeText1 == null || codeText1 == "") {
                    $("span[class='errorMsg']").text("验证码不能为空");
                    return false;
                }
            })
        })
    </script>
</head>

<body>
<!-- Header Area start  -->
<div class="header section">
    <!-- Header Top Message Start -->
    <!-- Header Top  End -->
    <!-- Header Bottom  Start -->
    <div class="header-bottom d-none d-lg-block">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.html"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->

            </div>
        </div>
    </div>
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.html"><img width="280px" src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->
            </div>
        </div>
    </div>
    <!-- Main Menu Start -->
    <div style="width: 100%;height: 50px;background-color: black"></div>
    <!-- Main Menu End -->
</div>
<!-- Header Area End  -->
<!-- login area start -->
<div class="login-register-area pt-70px pb-100px">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 col-md-12 ml-auto mr-auto">
                <div class="login-register-wrapper">
                    <div class="login-register-tab-list nav">
                        <a class="active" data-bs-toggle="tab" href="#lg1">
                            <h4>会员登录</h4>
                        </a>
                        <a id="register_tab" data-bs-toggle="tab" href="#lg2">
                            <h4>会员注册</h4>
                        </a>
                    </div>
                    <div class="tab-content">
                        <div id="lg1" class="tab-pane active">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <span class="errorMsg"
                                          style="float: right; font-weight: bold; font-size: 20pt; margin-left: 10px;"></span>
                                    <form action="memberServlet" method="post">
                                        ${requestScope.msg}
                                        <input type="hidden" name="action" value="login">
                                        <input type="text" name="username" value="${requestScope.username}"
                                               placeholder="Username"/>
                                        <input type="password" name="password" placeholder="Password"/>
                                        <!-- 加上验证码 -->
                                        <input type="text" id="code1" name="code1" style="width: 50%"
                                               placeholder="验证码"/>　　<img id="codeImg1" src="kaptchaServlet"
                                                                            style="width: 120px;height: 50px">
                                        <div class="button-box">
                                            <div class="login-toggle-btn">
                                                <input type="checkbox"/>
                                                <a class="flote-none" href="javascript:void(0)">Remember me</a>
                                                <a href="#">Forgot Password?</a>
                                            </div>
                                            <button id="log-btn" type="submit"><span>Login</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div id="lg2" class="tab-pane">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <span class="errorMsg"
                                          style="float: right; font-weight: bold; font-size: 20pt; margin-left: 10px;"></span>
                                    <form action="memberServlet" method="post">
                                        ${requestScope.msg2}
                                        <input type="hidden" name="action" value="register">
                                        <input type="text" id="username" name="username" value="${requestScope.username}" placeholder="Username"/>
                                        <input type="password" id="password" name="password" placeholder="输入密码"/>
                                        <input type="password" id="repwd" name="repassword" placeholder="确认密码"/>
                                        <input name="email" id="email" placeholder="电子邮件" value="${requestScope.email}" type="email"/>
                                        <input type="text" id="code2" name="code2" style="width: 50%"
                                               placeholder="验证码"/>　　<img id="codeImg2" src="kaptchaServlet"
                                                                            style="width: 120px;height: 50px">
                                        <div class="button-box">
                                            <button type="submit" id="sub-btn"><span>会员注册</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- login area end -->

<!-- Footer Area Start -->
<div class="footer-area">
    <div class="footer-container">
        <div class="footer-top">
            <div class="container">
                <div class="row">
                    <!-- Start single blog -->
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-sm-6 col-lg-3 mb-md-30px mb-lm-30px" data-aos="fade-up"
                         data-aos-delay="400">
                        <div class="single-wedge">
                            <h4 class="footer-herading">信息</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="about.html">关于我们</a></li>
                                        <li class="li"><a class="single-link" href="#">交货信息</a></li>
                                        <li class="li"><a class="single-link" href="privacy-policy.html">隐私与政策</a>
                                        </li>
                                        <li class="li"><a class="single-link" href="#">条款和条件</a></li>
                                        <li class="li"><a class="single-link" href="#">制造</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-2 col-sm-6 mb-lm-30px" data-aos="fade-up" data-aos-delay="600">
                        <div class="single-wedge">
                            <h4 class="footer-herading">我的账号</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="my-account.html">我的账号</a>
                                        </li>
                                        <li class="li"><a class="single-link" href="cart.html">我的购物车</a></li>
                                        <li class="li"><a class="single-link" href="login.jsp">登录</a></li>
                                        <li class="li"><a class="single-link" href="wishlist.html">感兴趣的</a></li>
                                        <li class="li"><a class="single-link" href="checkout.html">结账</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-3" data-aos="fade-up" data-aos-delay="800">

                    </div>
                    <!-- End single blog -->
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <div class="row flex-sm-row-reverse">
                    <div class="col-md-6 text-right">
                        <div class="payment-link">
                            <img src="#" alt="">
                        </div>
                    </div>
                    <div class="col-md-6 text-left">
                        <p class="copy-text">Copyright &copy; 2021 韩顺平教育~</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer Area End -->
<script src="assets/js/vendor/vendor.min.js"></script>
<script src="assets/js/plugins/plugins.min.js"></script>
<!-- Main Js -->
<script src="assets/js/main.js"></script>
</body>
</html>