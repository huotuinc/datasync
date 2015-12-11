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
        var huobanAjax = "<c:url value="/bulkDiscount" />"
        var ws;
        var isOpened = false;
        $(function () {
            $("#checkAll").change(function () {
                var $chkGoodId = $("input[name='chkGoodId']");
                if ($(this).attr("checked")) {
                    $chkGoodId.attr("checked", "checked");
                } else {
                    $chkGoodId.removeAttr("checked");
                }
            });

        });

        var discountHandler = {
            discount: function () {
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

                J.ShowDialog("bulkDiscount_dialog", "批量打折", function () {
                    var self = this;
                    var discount = $.trim($("#discount").val());
                    if (discount.length == 0) {
                        $.jBox.tip("请输入打折数");
                        return;
                    }
                    $.jBox.tip("正在处理，请稍候...", "loading");
                    J.GetJsonRespons(huobanAjax, {
                        goodListStr: goodList,
                        discount: discount
                    }, function (json) {
                        if (json.resultCode == 200) {
                            $.jBox.tip("操作成功", "success");
                            $(self).dialog("close");
                        } else {
                            $.jBox.tip("操作失败", "error");
                        }
                    }, function () {
                    }, J.PostMethod);
                }, function () {
                    $(this).dialog("close");
                })
            }
        };
    </script>
</head>
<body style="background-color:#e4e7ea">
<div id="bulkDiscount_dialog" style="padding:20px;display: none;">
    <p>打折：<input type="text" id="discount"/></p>

    <p>如打8折则输入0.8</p>
</div>
<div class="contentpanel">
    <div class="block">
        <div class="h">
            <p style="line-height:35px; padding-left:10px;">
                <i class="fa  fa-file-o"></i>商品导出
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
                                        <a href="javascript:discountHandler.discount()">批量打折</a>
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