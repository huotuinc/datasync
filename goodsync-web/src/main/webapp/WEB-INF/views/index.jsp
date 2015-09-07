<%--
  Created by IntelliJ IDEA.
  User: liual
  Date: 2015-09-06
  Time: 4:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10"/>
    <title></title>
    <link type="text/css" rel="stylesheet" href="/resources/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/index.css"/>
    <style type="text/css">
        .gonggao:hover {
            cursor: pointer;
        }
    </style>
</head>
<body style="background-image: url(/resources/images/d7.jpg); background-color: #111; background-repeat: no-repeat; background-size: cover">
<div style="position: relative; width: 1000px; margin: 0 auto">
    <div class="logo">
        <img src="images/logo-2013-l.png" style="width: 240px;"/>
    </div>
</div>

<div class="content">
    <div class="renwuxin">
        <ul class="renwu">
            <li class="ren" style="width: 48%; margin-right: 20px">
                <a href="<c:url value="/export"/>" title="">

                    <p>
                        <font style="font-size: 35px; font-family: 'Microsoft YaHei'; font-weight: normal">
                            商品导出
                        </font>
                    </p>
                </a>
            </li>
            <li class="rens" style="width: 48%;">
                <a href="#" title="">

                    <p>
                        <font style="font-size: 35px; font-family: 'Microsoft YaHei'; font-weight: normal">
                            商品导入（尽情期待）
                        </font>
                    </p>
                </a>
            </li>
        </ul>
    </div>
    <div style="clear: both;"></div>
</div>
<p style="height: 20px;"></p>

<p style="height: 150px;"></p>

<div style="text-align: center">
    <p class="copyright">版权所有：杭州火图科技有限公司 Copyright 2014, 浙ICP备13027761</p>
</div>
<p style="height: 70px;"></p>
</body>
</html>
