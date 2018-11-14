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
        
        <meta name="description" content="overview & stats"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link href="static/css/bootstrap.min.css" rel="stylesheet" />
        <link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="static/css/font-awesome.min.css" />
        <link rel="stylesheet" href="static/css/ace.min.css" />
        <link rel="stylesheet" href="static/css/ace-responsive.min.css" />
        <link rel="stylesheet" href="static/css/ace-skins.min.css" />
        
        <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
        <!--提示框-->
        <script type="text/javascript" src="static/js/jquery.tips.js"></script>
        
        <!-- 上传图片样式包 -->
        <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
        <link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
</head>

<script type="text/javascript">
    $(top.hangge()); 
    //保存
    
    function save(){

        
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
    
    onload=function jiancha(){
    if($("#PROVINCE").val() !=""){  
        $("#shi").show();
        $("#qu").show();
    }
} 
</script>


<body>

    <form  action="customer/${msg }.do" name="Form" id="Form" method="post" >
        <input type="hidden" name="CUSTOMER_ID" id="CUSTOMER_ID" value="${pd.CUSTOMER_ID}"/>
        <div id="zhongxin">
        <table>    
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">客户姓名:</td>
                <td><input type="text" name="CUSTOMER_NAME" id="CUSTOMER_NAME" value="${pd.CUSTOMER_NAME }" maxlength="32" placeholder="这里输入客户姓名" title="客户姓名"/></td>
            </tr>   
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">电话:</td>
                <td><input type="text" name="PHONE" id="PHONE" value="${pd.PHONE }" maxlength="32" placeholder="这里输入电话" title="电话"/></td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">备用电话:</td>
                <td><input type="text" name="BPHONE" id="BPHONE" value="${pd.BPHONE }" maxlength="32" placeholder="这里输入备用电话" title="备用电话"/></td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">当地作物:</td>
                <td><input type="text" name="CROP" id="CROP" value="${pd.CROP }" maxlength="32" placeholder="这里输入电话" title="电话"/></td>
            </tr>
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
                <td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS }"  maxlength="32" placeholder="这里输入详细地址" title="详细地址"/></td>
            </tr>  
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">备注:</td>
                <td><textarea cols="30" rows="3" align="center" name="BEIZHU" id="BEIZHU">${pd.BEIZHU}</textarea></td>
            </tr> 
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">微信:</td>
                <td><input type="text" name="WEIXIN" id="WEIXIN" value="${pd.WEIXIN }" maxlength="32" placeholder="这里输入微信" title="微信"/></td>
            </tr> 
             <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">QQ:</td>
                <td><input type="text" name="QQ" id="QQ" value="${pd.QQ }" maxlength="32" placeholder="这里输入QQ" title="QQ"/></td>
            </tr> 
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">登记人:</td>
                <td><input type="text" name="REGISTER_NAME" id="REGISTER_NAME" value="${pd.REGISTER_NAME }" maxlength="32" placeholder="这里输入登记人" title="登记人"/></td>
            </tr> 
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                    <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
        </table>
        </div>
        <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
    </form>
</body>
<script type="text/javascript">
    var msg = '${msg}';
/*  if(msg == 'no'){
        $("#BIANMA").attr("readonly",true);  //设置编码不能修改
    }
 */
</script>
</html>