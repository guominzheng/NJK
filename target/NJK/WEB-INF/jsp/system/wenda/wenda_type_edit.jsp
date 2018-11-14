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
	//删除图片
	function delP(IMG,TID){
		if(confirm("确定要删除图片？")){
			var url = "post/deltp.do?IMG="+IMG+"&TID="+TID+"&guid="+new Date().getTime();
			$.get(url,function(data){
					document.location.reload();
				if(data=="success"){
					$("#uploadName").val("");
					$("#uploader").show();
					$("#delP").hide();
				}
			}); 
		}
	}
</script>
	</head>
<body>
	<form action="wenda/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="WENDA_TYPE_ID" id="WENDA_TYPE_ID" value="${pd.WENDA_TYPE_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:120px;text-align: right;padding-top: 13px;">问答类型名称:</td>
				<td><input type="text" name="WENDA_TYPE_NAME" id="WENDA_TYPE_NAME" value="${pd.WENDA_TYPE_NAME}" maxlength="32" placeholder="这里输入类型名称" title="类型名称"/></td>
			</tr>
			<tr>
				<td style="width:120px;text-align: right;padding-top: 13px;">问答类型排序:</td>
				<td><input style="width:300PX" type="number" name="ORDE_BY" id="ORDE_BY" value="${pd.ORDE_BY}" maxlength="300" placeholder="这里输入排序" title="排序"/></td>
			</tr>
			<tr>
				<td style="width:120px;text-align: right;padding-top: 13px;">开放:</td>
				<td>
					 <label style="float:left;padding-left: 12px;"><input name="STATUS" id="form-field-radio0"  <c:if test="${pd.STATUS == '0' }">checked="checked"</c:if> type="radio" value="0"><span class="lbl">否</span></label>
                     <label style="float:left;padding-left: 5px;"><input name="STATUS" id="form-field-radio0" <c:if test="${pd.STATUS == '1' }">checked="checked"</c:if> type="radio" value="1"><span class="lbl">是</span></label>         
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
</body>
</html>

