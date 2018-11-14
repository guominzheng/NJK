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
    
    //根据省查市  
    function selectCity(pId){
        if($("#PROVINCE").val()!=""){
            $("#shi").show();
            $("#CITY").val("");//清空
            var url = "<%=basePath%>user/selectCity.do?ID="+pId;
            $.get(url,function(dizhi){
                if (dizhi == "[]") {
                 $("#CITY").empty();
                 $("<option value=''>请选择所在市</option>").appendTo($("#CITY"));
             } else {
                 $("#CITY").empty();
                 $("<option value=''>请选择所在市</option>").appendTo($("#CITY"));
                 $.each(eval(dizhi), function (i, item) {
                 $("<option value='" + item.ID + "'>" + item.NAME + "</option>").appendTo($("#CITY"));
                 });
             }
            });
        }
    }
    //根据市查区  
    function selectDistrict(pId){
        if($("#PROVINCE").val()!=""){
            $("#qu").show();
            $("#DISTRICT").val("");//清空
            var url = "<%=basePath%>user/selectCity.do?ID="+pId;
            $.get(url,function(dizhi){
                
                if (dizhi == "[]") {
                 $("#DISTRICT").empty();
                 $("<option value=''>请选择所在区(县)</option>").appendTo($("#DISTRICT"));
             } else {
                 $("#DISTRICT").empty();
                 $("<option value=''>请选择所在区(县)</option>").appendTo($("#DISTRICT"));
                 $.each(eval(dizhi), function (i, item) {
                 $("<option value='" + item.ID + "'>" + item.NAME + "</option>").appendTo($("#DISTRICT"));
                 });
             }
            });
        }
    }
    
</script>
    </head>
<body>
    <form action="appuser/${msg }.do" name="Form" id="Form" method="post">
        <input type="hidden" name="USER_ID" id="USER_ID" value="${pd.USER_ID}"/>
        
        <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <c:if test="${pd.GUANLI=='admin'||pd.GUANLI=='13298316296'}">
            	<tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">状态:</td>
                <%-- <td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="32" placeholder="这里输入状态" title="状态"/></td> --%>
                <td>
                <select  name="VIP" id="VIP" >
                         <!-- <option value=""></option> -->
                          	<option value="0" <c:if test="${pd.VIP=='0'}">selected</c:if>>不是VIP</option>
                            <option value="1" <c:if test="${pd.VIP=='1'}">selected</c:if>>VIP</option>
                            <option value="2" <c:if test="${pd.VIP=='2'}">selected</c:if>>审核中</option>
                            <option value="3" <c:if test="${pd.VIP=='3'}">selected</c:if>>超级超级VIP</option>
                            <option value="4" <c:if test="${pd.VIP=='4'}">selected</c:if>>拒绝</option>
                            <option value="5" <c:if test="${pd.VIP=='5'}">selected</c:if>>超级VIP</option>
                            <%-- <option value="2" <c:if test="${pd.STATUS=='2'}">selected</c:if>>已发货</option>
                            <option value="3" <c:if test="${pd.STATUS=='3'}">selected</c:if>>已完成</option> --%>
                            
                </select>
                </td>
            </tr>
            </c:if>
            
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">客户姓名:</td>
                <td><input type="text" name="CUSTOMER_NAME" id="CUSTOMER_NAME" value="${pd.CUSTOMER_NAME }"  maxlength="32" placeholder="这里输入客户姓名" title="详细地址"/></td>
            </tr>  
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">当地作物:</td>
                <td><input type="text" name="CROP" id="CROP" value="${pd.CROP}"  maxlength="32" placeholder="这里输入当地作物" title="当地作物"/></td>
            </tr>  
             <c:if test="${pd.EXCLU_ID=='5d18274e708c472ca679622f2c964ce0'||pd.GUANLI=='admin'||pd.GUANLI=='18137336250'||pd.GUANLI=='13298316296'}">
              <tr>
                                   <td style="width:70px;text-align: right;padding-top: 13px;">客服:</td>
                                                     <td style="vertical-align:top;">
                                <select
                                    class="chzn-select" name="EXCLU_ID" id="EXCLU_ID"
                                    data-placeholder="请选择客服" title="客服"
                                    style="vertical-align: top; width: 145px;">
                                        <option value="">请选择客服</option>
                                        <c:forEach items="${varList1}" var="var" varStatus="vs">
                                        <option value="${var.EXCLU_ID}" <c:if test="${var.EXCLU_ID == pd.EXCLU_ID }">selected</c:if>>${var.NAME}</option>
                                        </c:forEach>
                                </select>
                                </td>
              </tr>
            </c:if>
               <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">所在省:</td>
                <td>
                    <select name="PROVINCE" id="PROVINCE" data-placeholder="请选择所在省" style="vertical-align:top;" onchange="selectCity(this.value)">
                    <option value="">请选择所在省</option>
                    <c:forEach items="${provinces}" var="p">
                        <option value="${p.ID }" <c:if test="${p.ID  == pd.PROVINCE }">selected</c:if>>${p.NAME }</option>
                    </c:forEach>
                    </select>
                    </td>
            </tr>
             <tr class="info" id="shi" style="display:none">
                <td style="width:70px;text-align: right;padding-top: 13px;">所在市:</td>
                <td>
                 <select  name="CITY" id="CITY" data-placeholder="请选择所在市" style="vertical-align:top;" onchange="selectDistrict(this.value)">
                    <option value="">请选择所在市</option>
                    <option value="${pd.CITY }" selected="selected">${pd.C_NAME }</option>  
                </select> 
                </td>
            </tr>
                        <tr class="info" id="qu" style="display:none">
                <td style="width:70px;text-align: right;padding-top: 13px;">所在区(县):</td>
                <td>
                <select  name="DISTRICT" id="DISTRICT" data-placeholder="请选择所在区(县)" style="vertical-align:top;">
                    <option value="">请选择所在区(县)</option>
                    <option value="${pd.DISTRICT }" selected="selected">${pd.D_NAME }</option>
                </select>
                </td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">详细地址:</td>
                <td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}"  maxlength="32" placeholder="这里输入详细地址" title="详细地址"/></td>
            </tr>  
            
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                    <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
        </table>
        </div>
        
        <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
        
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