<%@ page import="com.fh.util.PhoneAddress" %>
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
					<form action="appuser/list.do" method="post" name="Form" id="Form">
						<table>
							<tr>
								<td><span class="input-icon"> <input
										autocomplete="off" id="nav-search-input" type="text"
										name="KEYWORD" value="${pd.KEYWORD}" placeholder="这里输入关键词" />
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>
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
                                <td style="vertical-align: top;"><select
									class="chzn-select" name="VIP" id="VIP"
									data-placeholder="请选择VIP"
									style="vertical-align: top; width: 120px;">
										<option value=""></option>
										<option value="">全部</option>
										<option value="0"
											<c:if test="${pd.VIP=='0'}">selected</c:if>>不是VIP</option>
										<option value="1"
											<c:if test="${pd.VIP=='1'}">selected</c:if>>VIP</option>
										<option value="2"
											<c:if test="${pd.VIP=='2'}">selected</c:if>>审核中</option>
										<option value="3"
											<c:if test="${pd.VIP=='3'}">selected</c:if>>超级超级VIP</option>
										<option value="4"
											<c:if test="${pd.VIP=='4'}">selected</c:if>>拒绝</option>
										<option value="5"
											<c:if test="${pd.VIP=='5'}">selected</c:if>>超级VIP</option>
										<%-- <option value="7" <c:if test="${pd.STATUS=='7'}">selected</c:if>>已取消</option> --%>
								</select></td>
                                <td style="vertical-align:top;"> 
                        <select name="PROVINCE" id="PROVINCE" onchange="jz(this.value);" style="vertical-align:top;width: 120px;"  class="chzn-select" data-placeholder="请选择省">
                            <option value="">请选择省</option>
                                <c:forEach items="${provinces}" var="var" varStatus="vs">
                                        <option value="${var.ID}" <c:if test="${var.ID == pd.PROVINCE }">selected</c:if>>${var.NAME}</option>
                                </c:forEach>
                        </select>
                        </td>
                         <td style="vertical-align:top;"> 
                        <select name="CITY" id="CITY" onchange="jz2(this.value);" style="vertical-align:top;width: 120px;">
                            <option value="">请选择市</option>
                                <c:forEach items="${dizhi}" var="var" varStatus="vs">
                                        <option value="${var.ID}" <c:if test="${var.ID == pd.CITY }">selected</c:if>>${var.NAME}</option>
                                </c:forEach>
                        </select>
                        </td>
                         <td style="vertical-align:top;"> 
                        <select name="DISTRICT" id="DISTRICT" onchange="jz3();" style="vertical-align:top;width: 120px;">
                            <option value="">请选择县(县)</option>
                                <c:forEach items="${dizhi1}" var="var" varStatus="vs">
                                        <option value="${var.ID}" <c:if test="${var.ID == pd.DISTRICT }">selected</c:if>>${var.NAME}</option>
                                </c:forEach>
                        </select>
                        </td>
								<td style="vertical-align: top;"><button
										class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button></td>
									<td style="vertical-align: top;"><a
										class="btn btn-mini btn-light" onclick="toExcel('${pd.KEYWORD}','${pd.EXCLU_ID}','${pd.PROVINCE}','${pd.CITY}','${pd.DISTRICT}');"
										title="导出到EXCEL"><i id="nav-search-icon"
											class="icon-download-alt"></i></a></td>
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
									<th class="center">用户名</th>
									<th class="center">客服</th>
									<th class="center">手机隶属</th>
									<th class="center">名称</th>
									<th class="center">是否为vip</th>
									<th class="center">客户姓名</th>
									<th class="center">电话</th>
									<th class="center">地址</th>
									<th class="center">当地作物</th>
									<th class="center">订单</th>
									<th class="center">商品权限</th>
									<th class="center">直播权限</th>
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty varList}">
										<%-- <c:if test="${QX.cha == 1 }"> --%>
											<c:forEach items="${varList}" var="var" varStatus="vs">
												<tr>
													<td class='center' style="width: 30px;"><label><input
															type='checkbox' name='ids' value="${var.USER_ID}" /><span
															class="lbl"></span></label></td>
													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td class='center'><a href="javascript:findOrder('${var.USERNAME}')">${var.USERNAME}</a></td>
													<td class='center'>${var.ENAME}</td>
													<td class='center'><c:if test="${empty var.PHONEADDRESS}">无法显示或无信息</c:if><c:if test="${not empty var.PHONEADDRESS}">${var.PHONEADDRESS}</c:if></td>
													<td class='center'>${var.NAME}</td>
													<td class='center'><c:if test="${var.VIP == '1' }">
															<span class="label label-important arrowed-in">是VIP</span>
													</c:if>
													<c:if test="${var.VIP == '2' }">
                                                           <span class="label label-success arrowed">审核中</span>
                                                    </c:if>
													 <c:if test="${var.VIP == '0' }">
															<span class="label label-success arrowed">不是VIP</span>
													</c:if>
													<c:if test="${var.VIP == '3' }">
                                                            <span class="label label-success arrowed">超级超级VIP</span>
                                                    </c:if>
                                                    <c:if test="${var.VIP == '5' }">
                                                            <span class="label label-success arrowed">超级VIP</span>
                                                    </c:if>
													</td>
													<td class='center'>${var.CUSTOMER_NAME}</td>
													<td class='center'>${var.PHONE}</td>
													<td class='center'>${var.P_NAME}${var.C_NAME}${var.D_NAME}</td>
													<td class='center'>${var.CROP}</td>
													<td class='center'><a
                                                        class="btn btn-small btn-success"
                                                      onclick="show('${var.USER_ID}');">订单</a></td>
                                                    <td class='center'><a
                                                        class="btn btn-small btn-success"
                                                      onclick="QuanXian('${var.USER_ID}');">商品权限</a></td>
                                                    <td class='center'><a
                                                        class="btn btn-small btn-success"
                                                      onclick="ZQuanXian('${var.USER_ID}');">直播权限</a></td>
                                <td style="width: 30px;" class="center">
                                    <div class='hidden-phone visible-desktop btn-group'>
                                        <div class="inline position-relative">
                                        <button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
                                        <ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                            <li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.USER_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
                                        </ul>
                                        </div>
                                    </div>
                                </td>
												</tr>

											</c:forEach>
										<%-- </c:if> --%>
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
								<td style="vertical-align:top;">
                                <a class="btn btn-small btn-success" onclick="SendVIP();">VIP发短息</a>
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
		  //修改
        function edit(Id){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="编辑";
             diag.URL = '<%=basePath%>appuser/goEdit.do?USER_ID='+Id;
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
		  
        //显示滚动图片
        function show(id){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="显示用户订单";
             diag.URL = '<%=basePath%>appuser/show.do?USER_ID='+id;
             diag.Width = 1200;
             diag.Height = 1000;
             diag.CancelEvent = function(){ //关闭事件
                 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                     if('${page.currentPage}' == '0'){
                         top.jzts();
                         setTimeout("self.location=self.location",100);
                     }else{
                         nextPage("${page.currentPage}");
                     }
                }
                diag.close();
             };
             diag.show();
        }
        
        //显示滚动图片
        function QuanXian(id){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="显示商品权限";
             diag.URL = '<%=basePath%>p_quanxian/list.do?USER_ID='+id;
             diag.Width = 1000;
             diag.Height = 1000;
             diag.CancelEvent = function(){ //关闭事件
                 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                     if('${page.currentPage}' == '0'){
                         top.jzts();
                         setTimeout("self.location=self.location",100);
                     }else{
                         nextPage("${page.currentPage}");
                     }
                }
                diag.close();
             };
             diag.show();
        }
		
        
        //显示滚动图片
        function ZQuanXian(id){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="显示直播权限";
             diag.URL = '<%=basePath%>z_quanXian/list.do?USER_ID='+id;
             diag.Width = 1000;
             diag.Height = 1000;
             diag.CancelEvent = function(){ //关闭事件
                 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                     if('${page.currentPage}' == '0'){
                         top.jzts();
                         setTimeout("self.location=self.location",100);
                     }else{
                         nextPage("${page.currentPage}");
                     }
                }
                diag.close();
             };
             diag.show();
        }
        
        //新增
        function Send(){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="发送短信";
             diag.URL = '<%=basePath%>appuser/Send.do';
             diag.Width = 450;
             diag.Height = 355;
             diag.CancelEvent = function(){ //关闭事件
                 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                     if('${page.currentPage}' == '0'){
                         top.jzts();
                         setTimeout("self.location=self.location",100);
                     }else{
                         nextPage("${page.currentPage}");
                     }
                }
                diag.close();
             };
             diag.show();
        }


        //查询客户的订单详情
        function findOrder(USERNAME){
            top.jzts();
            var diag = new top.Dialog();
            diag.Drag=true;
            diag.Title ="查询订单商品详情";
            diag.URL = '<%=basePath%>order_info/findUserOrder?USERNAME='+USERNAME;
            diag.Width = 1200;
            diag.Height = 800;
            diag.CancelEvent = function(){ //关闭事件
                if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                    if('${page.currentPage}' == '0'){
                        top.jzts();
                        setTimeout("self.location=self.location",100);
                    }else{
                        nextPage("${page.currentPage}");
                    }
                }
                diag.close();
            };
            diag.show();
        }
		
	      //新增
        function SendVIP(){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="给VIP发送短信";
             diag.URL = '<%=basePath%>appuser/SendVIP.do';
             diag.Width = 450;
             diag.Height = 355;
             diag.CancelEvent = function(){ //关闭事件
                 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                     if('${page.currentPage}' == '0'){
                         top.jzts();
                         setTimeout("self.location=self.location",100);
                     }else{
                         nextPage("${page.currentPage}");
                     }
                }
                diag.close();
             };
             diag.show();
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
		
	      function jz(PROVINCE){
	             $("#CITY").val("");//清空
	                var url = "<%=basePath%>user/selectCity.do?ID="+PROVINCE;;
	                $.get(url,function(dizhi){
	                    if (dizhi == "[]") {
	                     $("#CITY").empty();
	                     $("<option value=''>请选择市</option>").appendTo($("#CITY"));
	                 } else {
	                     $("#CITY").empty();
	                     $("<option value=''>请选择市</option>").appendTo($("#CITY"));
	                     $.each(eval(dizhi), function (i, item) {
	                     $("<option value='" + item.ID + "'>" + item.NAME + "</option>").appendTo($("#CITY"));
	                     });
	                 }
	                });
	        }
	      
	       function jz2(CITY){
               $("#DISTRICT").val("");//清空
                  var url = "<%=basePath%>user/selectCity1.do?ID="+CITY;;
                  $.get(url,function(dizhi1){
                      if (dizhi1 == "[]") {
                       $("#DISTRICT").empty();
                       $("<option value=''>请选择县(区)</option>").appendTo($("#DISTRICT"));
                   } else {
                       $("#DISTRICT").empty();
                       $("<option value=''>请选择县(区)</option>").appendTo($("#DISTRICT"));
                       $.each(eval(dizhi1), function (i, item) {
                       $("<option value='" + item.ID + "'>" + item.NAME + "</option>").appendTo($("#DISTRICT"));
                       });
                   }
                  });
          }
		
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>product/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage("${page.currentPage}");
									 });
								},
						    	error:function(){
						    		nextPage("${page.currentPage}");
						    	}
							});
						}
					}
				}
			});
		}
		
		//导出excel
		function toExcel(KEYWORD,EXCLU_ID,PROVINCE,CITY,DISTRICT){
			window.location.href='<%=basePath%>appuser/excel.do?KEYWORD='+KEYWORD+'&EXCLU_ID='+EXCLU_ID+'&PROVINCE='+PROVINCE+'&CITY='+CITY+'&DISTRICT='+DISTRICT;
			
		}
		</script>

</body>
</html>

 

  