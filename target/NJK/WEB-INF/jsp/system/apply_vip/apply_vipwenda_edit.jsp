<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8" />
    <title></title>
    <meta name="description" content="overview & stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="static/css/bootstrap.min.css" rel="stylesheet" />
    <link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="static/css/font-awesome.min.css" />
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/css/chosen.css" />

    <link rel="stylesheet" href="static/css/ace.min.css" />
    <link rel="stylesheet" href="static/css/ace-responsive.min.css" />
    <link rel="stylesheet" href="static/css/ace-skins.min.css" />

    <link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>

    <script type="text/javascript">


        //保存
        function save(){
            if($("#VIP").val()==""){
                $("#VIP").tips({
                    side:3,
                    msg:'请选择状态',
                    bg:'#AE81FF',
                    time:2
                });
                $("#VIP").focus();
                return false;
            }
            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }

    </script>
</head>
<body>
<form action="apply_vip_wenda/${msg }.do" name="Form" id="Form" method="post">
    <input type="hidden" name="APPLYVIP_ID_WENDA" id="APPLYVIP_ID_WENDA" value="${pd.APPLYVIP_ID_WENDA}"/>
    <input type="hidden" name="USER_ID" id="USER_ID" value="${pd.USER_ID}"/>
    <input type="hidden" name="PRODUCT_ID" id="PRODUCT_ID" value="${pd.PRODUCT_ID}"/>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">状态:</td>
                <%-- <td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="32" placeholder="这里输入状态" title="状态"/></td> --%>
                <td>
                    <select  name="VIP" id="VIP" >
                        <!-- <option value=""></option> -->
                        <option value="1" <c:if test="${pd.VIP=='1'}">selected</c:if>>同意</option>
                        <option value="2" <c:if test="${pd.VIP=='2'}">selected</c:if>>审核中</option>
                        <option value="4" <c:if test="${pd.VIP=='4'}">selected</c:if>>拒绝</option>
                        <%-- <option value="2" <c:if test="${pd.STATUS=='2'}">selected</c:if>>已发货</option>
                        <option value="3" <c:if test="${pd.STATUS=='3'}">selected</c:if>>已完成</option> --%>
                    </select>
                </td>

            <tr>
                <c:if test="">
                </c:if>
                <td style="width:70px;text-align: right;padding-top: 13px;">短信内容:</td>
                <td><input style="width:300PX;height:50px;" type="text" name="NEIRONG" id="NEIRONG" maxlength="300" placeholder="这里输入短信" title="短信"/></td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                    <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
        </table>
    </div>
</form>


<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript">
    $(top.hangge());
    $(function() {

        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect:true});

        //日期框
        $('.date-picker').datepicker();

    });

</script>
</body>
</html>