<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>
</head>
<body>

	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">

			<div class="row-fluid">

				<div class="row-fluid">

					<!-- 检索  -->
					<form action="wenda/wenda_list.do" method="post" name="Form" id="Form">
					<input type="hidden" name="WENDA_TYPE_ID" id="WENDA_TYPE_ID" value="${pd.WENDA_TYPE_ID}"/>
					<input type="hidden" name="WENDA_SHIJUAN_ID" id="WENDA_SHIJUAN_ID" value="${pd.WENDA_SHIJUAN_ID}"/>
						<table>
							<tr>
								<td><span class="input-icon"> <input
										autocomplete="off" id="nav-search-input" type="text"
										name="KEYWORD" value="${pd.KEYWORD}" placeholder="这里输入关键词" />
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>
								<td style="vertical-align: top;"><button
										class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button></td>
							</tr>
						</table>
						<!-- 检索  -->


						<table id="table_report"
							class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<th class="center"><label><input type="checkbox"
											id="zcheckbox" /><span class="lbl"></span></label></th>
									<th class="center">类型</th>
								</tr>
							</thead>

							<tbody>
												<tr>
													<td class='center' style="width: 30px;">1</td>
													<td class='center'><a href="javascript:show('0')">总分排行榜</a></td>
												</tr>
												<tr>
													<td class='center' style="width: 30px;">2</td>
													<td class='center'><a href="javascript:show('1')">次数排行榜</a></td>
												</tr>
												<tr>
													<td class='center' style="width: 30px;">3</td>
													<td class='center'><a href="javascript:show('2')">晒成绩</a></td>
												</tr>

							</tbody>
						</table>
						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;">
										<c:if test="${QX.add == 1 }">
											<a class="btn btn-small btn-success" onclick="add('${pd.WENDA_SHIJUAN_ID}','${pd.WENDA_TYPE_ID}');">新增</a>
										</c:if> 
									</td>
									<td style="vertical-align: top;"><div class="pagination"
											style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
<div id="zhongxin"></div>



				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
		</div>
	<!--/.fluid-container#main-container-->

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>

	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>

	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
	<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		  
    	//查询订单商品详情
		function show(ID){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查询问题答案";
			 diag.URL = '<%=basePath%>wenda/fenxiang_list.do?STATUS='+ID;
			 diag.Width = 1200;
			 diag.Height = 1000;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 $("#Form").submit();
					 }else{
						 nextPage("${page.currentPage}");
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		</script>
		
		
		 	
</body>
</html>