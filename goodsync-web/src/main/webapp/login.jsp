<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>伙伴+|商品导入</title>

    <script type="text/javascript" src="<c:url value="/resources/scripts/jquery-1.7.2.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/jquery.utils.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/jBox/jquery.jBox-2.3.min.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/scripts/jBox/Skins/Green/jbox.css"/>">
    <script type="text/javascript">
        var ajaxUrl = "<c:url value="/login" />";
        $(function () {
            $("#loginBtn").click(function () {
                var account = $.trim($("#account").val());
                var password = $.trim($("#password").val());
                var requestData = {
                    account: account,
                    password: password
                };
                J.GetJsonRespons(ajaxUrl, requestData, function (json) {
                    if (json == 1) {
                        $.jBox.tip("登录成功，正在跳转..", "loading");
                        window.location.href = "<c:url value="/index" />";
                    } else {
                        $.jBox.tip("登录失败，请检查用户名密码");
                    }
                }, function () {
                }, J.PostMethod);
            })
        });
    </script>
</head>
<body style="zoom: 1; background-color: #292929;" class=" move">
<div style="width: 100%; padding-top: 20px; background-color: #e8ecef;">
    <header class="header">
        <nav class="headerNav">
            <a target="_blank" href="http://www.huobanplus.com/">首页</a>
            &nbsp;<a target="_blank" href="http://www.huobanplus.com/index2.html">产品说明</a>
            @* &nbsp;<a target="_blank" href="http://bbs.huobanmall.com">商家社区</a>*@
        </nav>
    </header>
</div>
<section class="main" style="background: url(<c:url value="/resources/images/muban.jpg"/>) center center;">
    <!--登录框-->
    <div>
        <img src="<c:url value="/resources/images/huobanshangchenglogo.png" />" width="15%">

        <form style="height: 335px;">
            <div class="loginFormIpt showPlaceholder" style="text-align: center; margin: 0 auto;">
                <input class="formIpt" tabindex="1" title="请输入帐号" id="account" type="text" maxlength="50"
                       autocomplete="off" placeholder="帐号">
            </div>

            <div class="loginFormIpt showPlaceholder" style="text-align: center; margin: 0 auto;">

                <input class="formIpt" tabindex="2" title="请输入密码" id="password" type="password" placeholder="密码">
            </div>
            <div style="text-align: center; margin: 0 auto; width: 389px; padding-top: 10px;">

                <p style="width: 50%;margin:0 auto">
                    <input id="loginBtn" type="button" class="buttons3" value="登&nbsp;&nbsp;录">
                </p>


            </div>


            <div style="width: 7%; position: fixed; top: 60px; margin-left: 88%;">
                <img src="<c:url value="/resources/images/or.jpg" />" width="100%">
            </div>
        </form>
    </div>


</section>
<footer id="footer" class="footer">
    <div class="footer-inner" id="footerInner">


        <nav class="footerNav">

            <span class="copyright"></span>
        </nav>
    </div>
</footer>
</body>
</html>
