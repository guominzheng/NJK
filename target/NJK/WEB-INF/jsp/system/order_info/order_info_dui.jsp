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
	<div id="zhongxin">
		
				<div class="row-fluid">

					<div class="space-6"></div>
					<div class="row-fluid">
<%-- 						<%
							String strXML = "";

							strXML += "<graph caption='对比表' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
							strXML += "<set name='1' value='462' color='AFD8F8'/>";
							strXML += "<set name='2' value='857' color='F6BD0F'/>";
							strXML += "<set name='3' value='671' color='8BBA00'/>";
							strXML += "<set name='4' value='494' color='FF8E46'/>";
							strXML += "<set name='5' value='761' color='008E8E'/>";
							strXML += "<set name='6' value='960' color='D64646'/>";
							strXML += "<set name='7' value='629' color='8E468E'/>";
							strXML += "<set name='8' value='622' color='588526'/>";
							strXML += "<set name='9' value='376' color='B3AA00'/>";
							strXML += "<set name='10' value='494' color='008ED6'/>";
							strXML += "<set name='11' value='761' color='9D080D'/>";
							strXML += "<set name='12' value='960' color='A186BE'/>";
							strXML += "</graph>";
							//Create the chart - Column 3D Chart with data from strXML variable using dataXML method
						%> --%>
						<%
							String strXML = "";

							strXML += "<graph caption='对比表' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
							strXML += "<set name='1' value='462' color='AFD8F8'/>";
							strXML += "<set name='2' value='857' color='F6BD0F'/>";
							strXML += "<set name='3' value='671' color='8BBA00'/>";
							strXML += "<set name='4' value='494' color='FF8E46'/>";
							strXML += "<set name='5' value='761' color='008E8E'/>";
							strXML += "<set name='6' value='960' color='D64646'/>";
							strXML += "<set name='7' value='629' color='8E468E'/>";
							strXML += "<set name='8' value='622' color='588526'/>";
							strXML += "<set name='9' value='376' color='B3AA00'/>";
							strXML += "<set name='10' value='494' color='008ED6'/>";
							strXML += "<set name='11' value='761' color='9D080D'/>";
							strXML += "<set name='12' value='960' color='A186BE'/>";
							strXML += "</graph>";
							//Create the chart - Column 3D Chart with data from strXML variable using dataXML method
						%>
					<%--	<!-- 柱状图 -->
						<div class="center">
							<div style="float:left;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Area2D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${pd.strXML }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
							<div style="float:right;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Column3D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${pd.strXML }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</div>




						<div class="center">
							<div style="float:left;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Bar2D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${pd.strXML }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
							<div style="float:right;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Column2D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${pd.strXML }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</div>







						<div class="center">
							<div style="float:left;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF"
													value="static/FusionCharts/Doughnut2D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${pd.strXML }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
							<div style="float:right;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF"
													value="static/FusionCharts/Doughnut3D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${pd.strXML }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</div>







						<div class="center">
							<div style="float:left;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/SSGrid.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${pd.strXML }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
							<div style="float:right;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Pie3D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${pd.strXML }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</div>






						<div class="center">
							<div style="float:left;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Pie2D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${pd.strXML }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="500" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>--%>
							<div style="float:right;">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Line.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="${pd.strXML }" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="1500" />
												<jsp:param name="chartHeight" value="400" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</div>












					</div>
				</div> 
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

