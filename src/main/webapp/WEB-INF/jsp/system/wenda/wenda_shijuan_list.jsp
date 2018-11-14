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
	<div id="zhongxin">

		<div id="page-content" class="clearfix">

			<div class="row-fluid">

				<div class="row-fluid">

					<!-- 检索  -->
					<form action="wenda/shijuan_list.do" method="post" name="Form" id="Form">
						<input type="hidden" name="WENDA_TYPE_ID" id="WENDA_TYPE_ID" value="${pd.WENDA_TYPE_ID}"/>
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
									<th class="center">序号</th>
									<th class="center">问答试卷名称</th>
									<th class="center">开放</th>
									<th class="center">次数</th>
									<th class="center">置顶</th>
									<th class="center">是否为新</th>
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varList}">
										<c:if test="${QX.cha == 1 }">
											<c:forEach items="${varList}" var="var" varStatus="vs">
												<tr>
													<td class='center' style="width: 30px;"><label><input
															type='checkbox' name='ids' value="${var.WENDA_SHIJUAN_ID}" /><span
															class="lbl"></span></label></td>
													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td class='center'><a href="javascript:show('${var.WENDA_SHIJUAN_ID}','${pd.WENDA_TYPE_ID}')">${var.WENDA_SHIJUAN_NAME}</a></td>
													<td class='center'>${var.counts}</td>
													<td class="center">
                                                    	<c:if test="${var.STATUS == '0' }"><span class="label label-important arrowed-in">未开放</span></c:if>
                                            			<c:if test="${var.STATUS == '1' }"><span class="label label-success arrowed">已开放</span></c:if>
                                                    </td>
                                                    
                                                    <td id="checkRadio" class='center'>
                                                    <label style="float:left;padding-left: 5px;"><input name="form-field-radio${vs.index+1}" id="form-field-radio${vs.index+1}" onclick="zhiding('1','${var.WENDA_SHIJUAN_ID}');" <c:if test="${var.ZHIDING == '1' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">是</span></label>         
                                                    </td>
                                                    													<td id="checkRadio" class='center'>
                                                    <label style="float:left;padding-left: 12px;"><input name="form-field-radio1${vs.index+1}" id="form-field-radio1${vs.index+1}" onclick="setType('0','${var.WENDA_SHIJUAN_ID}');" <c:if test="${var.XIN == '0' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">否</span></label>
                                                    <label style="float:left;padding-left: 5px;"><input name="form-field-radio1${vs.index+1}" id="form-field-radio1${vs.index+1}" onclick="setType('1','${var.WENDA_SHIJUAN_ID}');" <c:if test="${var.XIN == '1' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">是</span></label>         
                                                    </td>
									<td style="width: 30px;" class="center">
									<div class='hidden-phone visible-desktop btn-group'>
									
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
										</c:if>
										<div class="inline position-relative">
										<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
											<c:if test="${QX.edit == 1 }">
											<li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.WENDA_SHIJUAN_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
											</c:if>
											<c:if test="${QX.del == 1 }">
											<li><a style="cursor:pointer;" title="删除" onclick="del('${var.WENDA_SHIJUAN_ID}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
											</c:if>
										</ul>
										</div>
									</div>
								</td>
												</tr>

											</c:forEach>
										</c:if>
										<c:if test="${QX.cha == 0 }">
											<tr>
												<td colspan="100" class="center">您无权查看</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>


							</tbody>
						</table>

						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;"><c:if
											test="${QX.add == 1 }">
											<a class="btn btn-small btn-success" onclick="add('${pd.WENDA_TYPE_ID}');">新增</a>
										</c:if>
									</td>
									<td style="vertical-align: top;"><div class="pagination"
											style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
					</form>
				</div>




				<!-- PAGE CONTENT ENDS HERE -->
			</div>
			<!--/row-->

		</div>
		<!--/#page-content-->
		</div>
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
		
		  //查询订单商品详情
        function setType(XIN,WENDA_SHIJUAN_ID){
                        top.jzts();
                        var url = "<%=basePath%>wenda/editXin.do?WENDA_SHIJUAN_ID="+WENDA_SHIJUAN_ID+"&XIN="+XIN;
                        $.get(url,function(data){
                            nextPage("${page.currentPage}");
                        });
        }
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		 //查询订单商品详情
	       function show(WENDA_SHIJUAN_ID,WENDA_TYPE_ID){
	            top.jzts();
	            var diag = new top.Dialog();
	            diag.Drag=true;
	            diag.Title ="查询问题详情";
	            diag.URL = '<%=basePath%>wenda/wenda_list.do?WENDA_SHIJUAN_ID='+WENDA_SHIJUAN_ID+'&WENDA_TYPE_ID='+WENDA_TYPE_ID;
	            diag.Width = 1000;
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
		
		//新增
		function add(WENDA_TYPE_ID){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>wenda/goShiJuanAdd.do?WENDA_TYPE_ID='+WENDA_TYPE_ID;
			 diag.Width = 600;
			 diag.Height = 450;
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
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>wenda/ShiJuan_delete.do?WENDA_SHIJUAN_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						$("#Form").submit();
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>wenda/goShiJuanEdit.do?WENDA_SHIJUAN_ID='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage("${page.currentPage}");
				}
				diag.close();
			 };
			 diag.show();
		}
		
		  //查询订单商品详情
        function zhiding(ZHIDING,WENDA_SHIJUAN_ID){
                        top.jzts();
                        var url = "<%=basePath%>wenda/zhiding.do?WENDA_SHIJUAN_ID="+WENDA_SHIJUAN_ID+"&ZHIDING="+ZHIDING;
                        $.get(url,function(data){
                            nextPage("${page.currentPage}");
                        });
        }
		</script>
</body>
</html>

