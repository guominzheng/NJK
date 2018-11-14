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
		<!-- 上传图片样式包 -->
		<link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
		<link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
		<!-- Ueditor编辑器JS -->
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	
	
	//保存
	function save(){
		if($("#SUBJECT").val()==""){
			$("#SUBJECT").tips({
				side:3,
	            msg:'请输入标题',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SUBJECT").focus();
			return false;
		}		
/*  		if($("#editor").val()==""){
 			alert($("#editor").val())
			$("#editor").tips({
				side:3,
	            msg:'请输入详情',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#editor").focus();
			return false;
		}  */
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
	</head>
<body>
	<form action="catenst/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="CATNEST_ID" id="CATNEST_ID" value="${pd.CATNEST_ID}"/>
		<input type="hidden" name="ORDE_BY" id="ORDE_BY" value="${pd.ORDE_BY}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">标题:</td>
				<td><input style="width:300PX" type="text" name="SUBJECT" id="SUBJECT" value="${pd.SUBJECT}" maxlength="300" placeholder="这里输入规格" title="规格"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">点击量:</td>
				<td><input style="width:300PX" type="text" name="CLICK" id="CLICK" value="${pd.CLICK}" maxlength="300" placeholder="这里输入点击量" title="点击量"/></td>
			</tr>
			<tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">置顶:</td>
                <%-- <td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="32" placeholder="这里输入状态" title="状态"/></td> --%>
                <td>
                <select  name="ZSTATUS" id="ZSTATUS" >
                        <!-- <option value=""></option> -->
                            <option value="0" <c:if test="${pd.ZSTATUS=='0'}">selected</c:if>>否</option>
                            <option value="1" <c:if test="${pd.ZSTATUS=='1'}">selected</c:if>>是</option>
                </select>
                </td>
            </tr>
            <tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">内容:</td>
				<td>
					<!--- 输入框 --->
					<script type="text/plain" id="editor" style="width:900PX;height:300px;">${pd.MESSAGE}</script>
					<!--- 输入框 --->
				</td>
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
		//实例化Ueditor编辑器
		var ue = UE.getEditor('editor');
		</script>
				<!-- 图片上传 -->
    	<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
    	</script>
</body>
</html>

