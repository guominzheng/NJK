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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	</head>
<body>
	<div id="zhongxin" >
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<thead>
					<tr>
						<!-- <th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th> -->
						<th class="center">序号</th>
						<th class="center">客户账号</th>
						<th class="center">客户昵称</th>
						<th class="center">订单号</th>
						<th class="center">订购商品</th>
						<th class="center">收货电话</th>
						<th class="center">收货地址</th>
						<th class="center">处理客服</th>
					</tr>
				</thead>
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
								<!-- 序号-->
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<!-- 客户账号-->
									<td class="center">${var.USERNAME}</td>
								<!-- 客户昵称-->
								<td class="center">${var.NAME}</td>
								<!-- 订单号-->
								<td class="center">${var.ORDER_NUMBER}</td>
								<!-- 订购商品-->
								<td class="center">${var.PRO_NAME}</td>
								<!-- 数量-->
								<td class="center">${var.PHONE}</td>
								<!-- 收货电话-->
								<td class="center">${var.ADDRESS}</td>
								<!-- 办理客服-->
								<td class="center">${var.EXCLUNAME}</td>
							</tr >
						
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
		</table>
		
	<%-- 		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<c:if test="${QX.add == 1 }">
					<a class="btn btn-small btn-success" onclick="add('${pd.PRO_INFO_ID}');">新增</a>
					</c:if>
					<c:if test="${QX.del == 1 }">
					<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>
					</c:if>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		 --%>
		</div>

		
		<!-- 返回顶部  -->
	<!-- 	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a> -->
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		function windowprint() {

		    var f = document.getElementById("printdiv");

		    var mStr;
            mStr = window.document.body.innerHTML ;
		    f.style.display = "";

		    window.document.body.innerHTML = printdiv.innerHTML;
		    window.print();

		    f.style.display = "none";
		    window.document.body.innerHTML = mStr;
		}
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>order_pro/excel.do';
		}
		</script>
		
	</body>
</html>

