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
		if($("#STATUS").val()==""){
			$("#STATUS").tips({
				side:3,
	            msg:'请输入状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STATUS").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="order_info/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ORDER_INFO_ID" id="ORDER_INFO_ID" value="${pd.ORDER_INFO_ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">订单号:</td>
				<td><input type="text" name="ORDER_NUMBER" id="ORDER_NUMBER" value="${pd.ORDER_NUMBER}" maxlength="32" placeholder="这里输入订单号" title="订单号"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">用户ID:</td>
				<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="32" placeholder="这里输入用户ID" title="用户ID"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">下单时间:</td>
				<td><input type="text" name="ORDER_DATE" id="ORDER_DATE" value="${pd.ORDER_DATE}" maxlength="32" placeholder="这里输入下单时间" title="下单时间"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">配送时间:</td>
				<td><input type="text" name="DISPATCHING_TIME" id="DISPATCHING_TIME" value="${pd.DISPATCHING_TIME}" maxlength="32" placeholder="这里输入配送时间" title="配送时间"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">收货人:</td>
				<td><input type="text" name="CONSIGNEE" id="CONSIGNEE" value="${pd.NAME}" maxlength="32" placeholder="这里输入收货人" title="收货人"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">收货人电话:</td>
				<td><input type="text" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="32" placeholder="这里输入收货人电话" title="收货人电话"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">省:</td>
				<td><input type="text" name="PROVINCE" id="PROVINCE" value="${pd.PROVINCE}" maxlength="32" placeholder="这里输入收货地址" title="收货地址"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">市:</td>
				<td><input type="text" name="CITY" id="CITY" value="${pd.CITY}" maxlength="32" placeholder="这里输入收货地址" title="收货地址"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">县(区):</td>
				<td><input type="text" name="DISTRICT" id="DISTRICT" value="${pd.DISTRICT}" maxlength="32" placeholder="这里输入收货地址" title="收货地址"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">收货地址:</td>
				<td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="32" placeholder="这里输入收货地址" title="收货地址"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">订单完成时间:</td>
				<td><input type="text" name="SEND_DATE" id="SEND_DATE" value="${pd.SEND_DATE}" maxlength="32" placeholder="这里输入订单完成时间" title="订单完成时间"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">支付金额:</td>
				<td><input type="text" name="PAY_MONEY" id="PAY_MONEY" value="${pd.PAY_MONEY}" maxlength="32" placeholder="这里输入支付金额" title="支付金额"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">支付方式:</td>
				<td><input type="text" name="PAY_METHOD" id="PAY_METHOD" value="${pd.PAY_METHOD}" maxlength="32" placeholder="这里输入支付方式" title="支付方式"/></td>
			</tr>
			<tr style="display: none">
				<td style="width:70px;text-align: right;padding-top: 13px;">支付时间:</td>
				<td><input type="text" name="PAY_TIME" id="PAY_TIME" value="${pd.PAY_TIME}" maxlength="32" placeholder="这里输入支付时间" title="支付时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">状态:</td>
				<%-- <td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="32" placeholder="这里输入状态" title="状态"/></td> --%>
				<td>
				<select  name="STATUS" id="STATUS" >
						<!-- <option value=""></option> -->
							<option value="0" <c:if test="${pd.STATUS=='0'}">selected</c:if>>未付款</option>
							<option value="1" <c:if test="${pd.STATUS=='1'}">selected</c:if>>已付款</option>
						    <option value="2" <c:if test="${pd.STATUS=='2'}">selected</c:if>>已发货</option>
							<option value="3" <c:if test="${pd.STATUS=='3'}">selected</c:if>>已完成</option> 
				</select>
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
		
		</script>
</body>
</html>