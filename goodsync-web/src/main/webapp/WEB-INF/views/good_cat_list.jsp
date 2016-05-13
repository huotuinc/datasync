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
    <title>分类列表</title>
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
        var ajaxUrl = "<c:url value="/batchSetUserPrice" />";
        var batchSetHandler = {
            showDialog: function () {
                J.ShowDialog("bulkDiscount_dialog", "批量设置", function () {
                    var self = this;
                    var cateIdList = "";
                    $("input[name='chkCateId']:checked").each(function () {
                        cateIdList += $(this).val() + ",";
                    });
                    var evalStr = $.trim($("#evalStr").val());
                    if (evalStr.length == 0) {
                        $.jBox.tip("请输入计算公式");
                        return;
                    }
                    var requestData = {
                        evalStr: evalStr,
                        cateIdList: cateIdList.substring(0, cateIdList.length - 1)
                    };
                    $.jBox.tip("正在设置...", "loading");
                    J.GetJsonRespons(ajaxUrl, requestData, function (json) {
                        if (json.resultCode == 200) {
                            $.jBox.tip("设置成功", "success");
                        } else {
                            $.jBox.tip("设置失败" + json.desc, "error");
                        }
                        $(self).dialog("close");
                    }, function () {
                    }, "post");
                }, function () {
                    $(this).dialog("close");
                })
            }
        }
    </script>
</head>
<body style="background-color:#e4e7ea">
<div id="bulkDiscount_dialog" style="padding:20px;display: none;">

    <p>请输入公式：<input type="text" id="evalStr">

    <p>a是成本价,b是市场价,c是销售价,批量设置以后将会批量更新商品的会员价</p>
</div>
<div class="contentpanel">
    <div class="block">
        <div class="h">
            <p style="line-height:35px; padding-left:10px;">
                <i class="fa  fa-file-o"></i>分类列表
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
                                        <a href="javascript:batchSetHandler.showDialog()">批量设置会员价</a>
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
                                <th align="center" rowspan="1" colspan="1">分类名称</th>
                            </tr>
                            </thead>
                            <!---表头---->
                            <tbody>
                            <c:forEach var="goodCat" items="${goodCatList}">
                                <tr height="28px" class="odd">
                                    <td align="center">
                                        <input type="checkbox" name="chkCateId" value="${goodCat.catId}"/>
                                    </td>
                                    <td align="left">
                                            ${goodCat.space()}${goodCat.catName}
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