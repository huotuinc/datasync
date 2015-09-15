<%--
  Created by IntelliJ IDEA.
  User: liual
  Date: 2015-09-06
  Time: 7:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">

    <link href="<c:url value="/resources/css/houtaikk.css"/>" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/common.css"/>">
    <script type="text/javascript" src="<c:url value="/resources/scripts/jquery-1.7.2.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/jquery.utils.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/scripts/jBox/jquery.jBox-2.3.min.js" />"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/scripts/jBox/Skins/Green/jbox.css"/>">
    <script type="text/javascript" src="<c:url value="/resources/scripts/jqueryui/jquery-ui-1.8.20.min.js" />"></script>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/scripts/jqueryui/jquery-ui-1.10.3.custom.min.css"/>">
    <script type="text/javascript" src="<c:url value="/resources/scripts/sockjs-0.3.4.min.js" />"></script>
    <title>商品列表</title>
    <style type="text/css">
        .box {
            height: 300px;
            overflow-x: hidden;
            overflow-y: inherit;
            margin: 10px;
            border: 1px solid #ddd;
            width: 600px;
        }

        .box p {
            font-size: 14px;
            line-height: 30px;
            margin: 10px;
        }

        .size {
            margin: 0 auto;
            width: 640px;
        }
    </style>
    <script type="text/javascript">
        var type = 0;
        var huobanAjax = "<c:url value="/huobanmall/" />"
        var ws;
        $(function () {
            $("#checkAll").change(function () {
                var $chkGoodId = $("input[name='chkGoodId']");
                if ($(this).attr("checked")) {
                    $chkGoodId.attr("checked", "checked");
                } else {
                    $chkGoodId.removeAttr("checked");
                }
            });
            connectSocket();
        });

        function connectSocket() {
            ws = new SockJS("<c:url value="/sync/message" />");
            ws.onopen = function () {
                console.log("Info: connection opened");
            }
            ws.onmessage = function (event) {
                console.log("message:" + event.data);
                $("#msgBench").append('<p>' + event.data + '</p>');
            }
        }

        var exportHandler = {
            export: function () {
                var goodList = "";
                if ($("#checkAll").attr("checked")) {
                    goodList = "all";
                } else {
                    var $chkGoodId = $("input[name='chkGoodId']:checked");
                    $chkGoodId.each(function (o, item) {
                        if (o == $chkGoodId.length - 1) {
                            goodList += $(item).val();
                        } else {
                            goodList += $(item).val() + ",";
                        }
                    })
                }
                if (goodList.length == 0) {
                    $.jBox.tip("您还未选择任何商品");
                    return;
                }
                $("#hbGoodList").val(goodList);
                J.ShowDialogButton("export_dialog", "选择导出平台", {});
            },
            exportToHuoban: function () {
                var goodList = $("#hbGoodList").val();
                J.ShowDialog("export_huoban", "伙伴商城授权登录", function () {
                    var account = $.trim($("#account").val());
                    var password = $.trim($("#password").val());
                    var requestData = {
                        account: account,
                        password: password
                    }
                    J.GetJsonRespons(huobanAjax + "authority", requestData, function (json) {
                        if (json.resultCode == 200) {
                            $.jBox.tip(json.desc, "success");
                            setTimeout(function () {
                                jBox.confirm('确定要信息同步到指定商户？将会更新覆盖已导出过的商品信息', '提示', function (v, h, f) {
                                    if (v == "ok") {
                                        $("#msgBench").html("");
                                        J.ShowDialogButton("msgBench_dialog", "导出进度", {
                                            "关闭": function () {
                                                $(this).dialog('close');
                                            }
                                        });

                                        J.GetJsonRespons(huobanAjax + "export", {goodList: goodList}, function (json) {
                                            if (json.resultCode == 200) {
                                                $.jBox.tip(json.desc, "success");
                                                $("#msgBench").append("<p>" + json.desc + "</p>");
                                            }
                                        }, function () {
                                        }, J.PostMethod);
                                    }
                                    return true;
                                });
                            }, 400);
                        } else {
                            $.jBox.tip(json.desc);
                        }
                    }, function () {
                    }, J.PostMethod);
                }, function () {
                    $(this).dialog('close');
                })
            }
        }
    </script>
</head>
<body style="background-color:#e4e7ea">
<div id="export_dialog" style="padding:20px;display: none;">
    <input type="hidden" id="hbGoodList"/>

    <div class="fg-button clearfix">
        <a href="javascript:exportHandler.exportToHuoban();">导出到伙伴商城</a>
    </div>

    <div class="fg-button clearfix" style="margin-top: 57px;">
        <a href="javascript:type=1;">导出到淘宝（敬请期待）</a>
    </div>
</div>
<div id="export_huoban" style="padding:20px;display: none;">
    <p>账户名：<input type="text" id="account"/></p>

    <p>账户密码：<input type="password" id="password"/></p>
</div>
<div id="msgBench_dialog" style="padding: 20px;display:none;">
    <div class="box" id="msgBench">
    </div>
</div>
<div class="contentpanel">
    <div class="block">
        <div class="h">
            <p style="line-height:35px; padding-left:10px;">
                <i class="fa  fa-file-o"></i>代理商等级管理
            </p>
        </div>
        <div class="cnt-wp">
            <div class="cnt form">
                <form>
                    <!--提示--->
                    <p style="clear:both"></p>

                    <div class="day">
                        <!--时间-->
                        <table width="100%">
                            <tbody>
                            <tr>
                                <td width="55%">
                                </td>

                                <td align="right">


                                    <div class="fg-button clearfix" style="float:right">
                                        <a href="javascript:exportHandler.export()">导出</a>
                                    </div>

                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <!---表格--->
                    <div class="dataTables_wrapper">
                        <table width="100%" class="table_appss tablept5" id="table_apps">
                            <thead>
                            <tr class="sdkbar" style="font-weight:bold;">
                                <th align="center" rowspan="1" colspan="1">
                                    <input type="checkbox" id="checkAll"/>
                                </th>
                                <th align="center" rowspan="1" colspan="1">商品名称</th>
                            </tr>
                            </thead>
                            <!---表头---->
                            <tbody>
                            <c:forEach var="good" items="${goodsList}">
                                <tr height="28px" class="odd">
                                    <td align="center">
                                        <input type="checkbox" name="chkGoodId" value="${good.goodsId}"/>
                                    </td>
                                    <td align="center">
                                            ${good.name}
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <!---内容---->
                        </table>
                        <p style=" clear:both"></p>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
