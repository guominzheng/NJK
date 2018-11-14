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
					<form action="post/list.do" method="post" name="Form" id="Form">
						<table>
							<tr>
								<td><span class="input-icon"> <input
										autocomplete="off" id="nav-search-input" type="text"
										name="KEYWORD" value="${pd.KEYWORD}" placeholder="这里输入关键词" />
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>
								<td style="vertical-align:top;"> 
                                    <select class="chzn-select" name="TSTATUS" id="TSTATUS" data-placeholder="请选择是否置顶" style="vertical-align:top;width: 130px;">
                                    <option value=""></option>
                                    <option value="">全部</option>
                                    <option value="0" <c:if test="${pd.TSTATUS=='0'}">selected</c:if>>否</option>
                                    <option value="1" <c:if test="${pd.TSTATUS=='1'}">selected</c:if>>是</option>
                                    </select>
                                </td>
                    <td style="vertical-align:top;">
                                <select
                                    class="chzn-select" name="FID1" id="FID1"
                                    data-placeholder="请选择专题" title="专题"
                                    style="vertical-align: top; width: 145px;">
                                        <option value="">请选择专题</option>
                                        <c:forEach items="${Slist}" var="var" varStatus="vs">
                                        <option value="${var.FID}" <c:if test="${var.FID == pd.FID1 }">selected</c:if>>${var.SPECIAL}</option>
                                        </c:forEach>
                                </select>
                    </td>
                    <td style="vertical-align:top;">
                                <select
                                    class="chzn-select" name="PRODUCT_ID1" id="PRODUCT_ID1"
                                    data-placeholder="请选择商品" title="商品"
                                    style="vertical-align: top; width: 145px;">
                                        <option value="">请选择商品</option>
                                        <c:forEach items="${Plist}" var="var" varStatus="vs">
                                        <option value="${var.PRODUCT_ID}" <c:if test="${var.PRODUCT_ID == pd.PRODUCT_ID1 }">selected</c:if>>${var.PRODUCT_NAME}</option>
                                        </c:forEach>
                                </select>
                    </td>
                     <td style="vertical-align:top;">
                                <select
                                    class="chzn-select" name="ACTIVITY_ID1" id="ACTIVITY_ID1"
                                    data-placeholder="请选择直播" title="直播"
                                    style="vertical-align: top; width: 145px;">
                                        <option value="">请选择直播</option>
                                        <c:forEach items="${Alist}" var="var" varStatus="vs">
                                        <option value="${var.ACTIVITY_ID}" <c:if test="${var.ACTIVITY_ID == pd.ACTIVITY_ID1 }">selected</c:if>>${var.TITLE}</option>
                                        </c:forEach>
                                </select>
                    </td>
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
									<th class="center">标题</th>
									<th class="center">所属专题</th>
									<th class="center">所属商品</th>
									<th class="center">所属直播</th>
									<th class="center">作者</th>
									<th class="center">点赞数</th>
									<th class="center">回复人数</th>
									<th class="center">点击数</th>
									<th class="center">发表时间</th>
									<th class="center">置顶</th>
									<th class="center">精华帖子</th>
									<th class="center">每日一读</th>
									<th class="center">发布</th>
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
															type='checkbox' name='ids' value="${var.TID}" /><span
															class="lbl"></span></label></td>
													<td class='center' style="width: 30px;">${vs.index+1}</td>
													<td class='center'><a
														href="javascript:show('${var.TID}')">${var.SUBJECT}</a>
													</td>
                                                      <td class="center">
                                                        <c:if test="${not empty var.SPECIAL}">
                                                        ${var.SPECIAL}
                                                    </c:if>
                                                     <c:if test="${empty var.SPECIAL}">
                                                        <span style="color: red;">没有所属专题</span>
                                                    </c:if>
                                                    </td>
                                                    <td class="center">
                                                        <c:if test="${not empty var.PRODUCT_NAME}">
                                                        ${var.PRODUCT_NAME}
                                                    </c:if>
                                                     <c:if test="${empty var.PRODUCT_NAME}">
                                                        <span style="color: red;">没有所属商品</span>
                                                    </c:if>
                                                    </td>
                                                     <td class="center">
                                                        <c:if test="${not empty var.TITLE}">
                                                        ${var.TITLE}
                                                    </c:if>
                                                     <c:if test="${empty var.TITLE}">
                                                        <span style="color: red;">没有所属直播</span>
                                                    </c:if>
                                                    </td>
													<td class='center'><span
														class="label label-success arrowed">${var.NAME}</span></td>
													<td class='center'>${var.VIEWS}</td>
													<td class='center'>${var.HUIFU}</td>
													<td class='center'>${var.SHUZI}</td>
													<td class='center'>${var.DATE}</td>
													<td id="checkRadio" class='center'>
                                                    <label style="float:left;padding-left: 12px;"><input name="form-field-radio${vs.index+1}" id="form-field-radio${vs.index+1}" onclick="setType('0','${var.TID}');" <c:if test="${var.TSTATUS == '0' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">否</span></label>
                                                    <label style="float:left;padding-left: 5px;"><input name="form-field-radio${vs.index+1}" id="form-field-radio${vs.index+1}" onclick="setType('1','${var.TID}');" <c:if test="${var.TSTATUS == '1' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">是</span></label>         
                                                    </td>
                                                    <td id="checkRadio" class='center'>
                                                    <label style="float:left;padding-left: 12px;"><input name="form-field-radio1${vs.index+1}" id="form-field-radio1${vs.index+1}" onclick="setType1('0','${var.TID}');" <c:if test="${var.ESTATUS == '0' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">否</span></label>
                                                    <label style="float:left;padding-left: 5px;"><input name="form-field-radio1${vs.index+1}" id="form-field-radio1${vs.index+1}" onclick="setType1('1','${var.TID}');" <c:if test="${var.ESTATUS == '1' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">是</span></label>         
                                                    </td>
                                                    <td id="checkRadio" class='center'>
                                                    <label style="float:left;padding-left: 12px;"><input name="form-field-radio2${vs.index+1}" id="form-field-radio2${vs.index+1}" onclick="setType2('0','${var.TID}');" <c:if test="${var.MSTATUS == '0' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">否</span></label>
                                                    <label style="float:left;padding-left: 5px;"><input name="form-field-radio2${vs.index+1}" id="form-field-radio2${vs.index+1}" onclick="setType2('1','${var.TID}');" <c:if test="${var.MSTATUS == '1' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">是</span></label>         
                                                    </td>
                                                     <td class='center'><a
														class="btn btn-small btn-success"
														onclick="fabu('${var.TID}');">发布</a></td>
													<td style="width: 30px;" class="center">
														<div class='hidden-phone visible-desktop btn-group'>

															<c:if test="${QX.edit != 1 && QX.del != 1 }">
																<span
																	class="label label-large label-grey arrowed-in-right arrowed-in"><i
																	class="icon-lock" title="无权限"></i></span>
															</c:if>
															<div class="inline position-relative">
																<button class="btn btn-mini btn-info"
																	data-toggle="dropdown">
																	<i class="icon-cog icon-only"></i>
																</button>
																<ul
																	class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
																	<c:if test="${QX.edit == 1 }">
																		<li><a style="cursor: pointer;" title="编辑"
																			onclick="edit('${var.TID}');"
																			class="tooltip-success" data-rel="tooltip" title=""
																			data-placement="left"><span class="green"><i
																					class="icon-edit"></i></span></a></li>
																	</c:if>
																	<c:if test="${QX.del == 1 }">
																		<li><a style="cursor: pointer;" title="删除"
																			onclick="del('${var.TID}');"
																			class="tooltip-error" data-rel="tooltip" title=""
																			data-placement="left"><span class="red"><i
																					class="icon-trash"></i></span> </a></li>
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
											<a class="btn btn-small btn-success" onclick="add();">新增</a>
										</c:if> 
										<%-- <c:if test="${QX.del == 1 }">
											<a class="btn btn-small btn-danger"
												onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
												class='icon-trash'></i></a>
										</c:if> <!-- <a class="btn btn-small btn-success" onclick="Reset();">重置</a> --> --%>
									</td>
									<td style="vertical-align: top;">
									<c:if test="${QX.edit == 1 }">
                                       <select class="chzn-select" name="FID" id="FID" data-placeholder="请选择专题" title="专题" style="vertical-align: top; width: 145px;">
                                        <option value="0">请选择专题</option>
                                        <c:forEach items="${Slist}" var="var" varStatus="vs">
                                        <option value="${var.FID}">${var.SPECIAL}</option>
                                        </c:forEach>
                                        </select>
                                        <a class="btn btn-small btn-success" onclick="saveSpecial('确定要添加到选中的专题吗?');">选择专题</a>
                                    </c:if>
                                    </td>
                                    <td style="vertical-align: top;">
                                    <c:if test="${QX.edit == 1 }">
                                       <select class="chzn-select" name="PRODUCT_ID" id="PRODUCT_ID" data-placeholder="请选择商品" title="商品" style="vertical-align: top; width: 145px;">
                                        <option value="">请选择商品</option>
                                        <c:forEach items="${Plist}" var="var" varStatus="vs">
                                        <option value="${var.PRODUCT_ID}">${var.PRODUCT_NAME}</option>
                                        </c:forEach>
                                        </select>
                                        <a class="btn btn-small btn-success" onclick="saveProduct('确定要添加到选中的商品吗?');">选择商品</a>
                                    </c:if>
                                    </td>
                                    <td style="vertical-align: top;">
                                    <c:if test="${QX.edit == 1 }">
                                       <select class="chzn-select" name="ACTIVITY_ID" id="ACTIVITY_ID" data-placeholder="请选择直播" title="直播" style="vertical-align: top; width: 145px;">
                                        <option value="">请选择直播</option>
                                        <c:forEach items="${Alist}" var="var" varStatus="vs">
                                        <option value="${var.ACTIVITY_ID}">${var.TITLE}</option>
                                        </c:forEach>
                                        </select>
                                        <a class="btn btn-small btn-success" onclick="saveActivity('确定要添加到选中的直播吗?');">选择直播</a>
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
        function setType(TSTATUS,TID){
                        top.jzts();
                        var url = "<%=basePath%>post/editTop.do?TID="+TID+"&TSTATUS="+TSTATUS;
                        $.get(url,function(data){
                            nextPage("${page.currentPage}");
                        });
        }
	      
	      //是否为精华
        function setType1(ESTATUS,TID){
            top.jzts();
            var url = "<%=basePath%>post/editEstatus.do?TID="+TID+"&ESTATUS="+ESTATUS;
            $.get(url,function(data){
                nextPage("${page.currentPage}");
            });
           }
	      
        //是否每日一读
        function setType2(MSTATUS,TID){
            top.jzts();
            var url = "<%=basePath%>post/editMstatus.do?TID="+TID+"&MSTATUS="+MSTATUS;
            $.get(url,function(data){
                nextPage("${page.currentPage}");
            });
           }
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>post/goAdd.do';
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
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>post/delete.do?TID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage("${page.currentPage}");
					});
				}
			});
		}
		
		//查询订单商品详情
		function show(ID){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="查询帖子回复详情";
			 diag.URL = '<%=basePath%>post/show.do?TID='+ID;
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
		
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>post/goEdit.do?TID='+Id;
			 diag.Width = 1000;
			 diag.Height = 1000;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage("${page.currentPage}");
				}
				diag.close();
			 };
			 diag.show();
		}
        //删除
        function fabu(Id){
            bootbox.confirm("确定要发布吗?", function(result) {
                if(result) {
                    top.jzts();
                    var url = "<%=basePath%>post_special/fabu.do?TID="+Id+"&tm="+new Date().getTime();
                    $.get(url,function(data){
                        nextPage("${page.currentPage}");
                    });
                }
            });
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
								url: '<%=basePath%>post/deleteAll.do?tm='+new Date().getTime(),
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
		
	      //批量操作
        function saveSpecial(msg){
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
                        if(msg == '确定要添加到选中的专题吗?'){
                            top.jzts();
                            $.ajax({
                                type: "POST",
                                url: '<%=basePath%>post/saveSpecial.do?tm='+new Date().getTime(),
                                data: {DATA_IDS:str,FID:$("#FID").val()},
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
	      
	      
        
<%--         //批量操作
        function saveEssence(msg){
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
                        if(msg == '确定要把选中的帖子为精品吗?'){
                            top.jzts();
                            $.ajax({
                                type: "POST",
                                url: '<%=basePath%>post/saveEssence.do?tm='+new Date().getTime(),
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
        } --%>
        
      //批量操作
        function saveActivity(msg){
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
                        if(msg == '确定要添加到选中的直播吗?'){
                            top.jzts();
                            $.ajax({
                                type: "POST",
                                url: '<%=basePath%>post/saveActivity.do?tm='+new Date().getTime(),
                                data: {DATA_IDS:str,ACTIVITY_ID:$("#ACTIVITY_ID").val()},
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
	      
	      
        //批量操作
        function saveProduct(msg){
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
                        if(msg == '确定要添加到选中的商品吗?'){
                            top.jzts();
                            $.ajax({
                                type: "POST",
                                url: '<%=basePath%>post/saveProduct.do?tm='+new Date().getTime(),
                                data: {DATA_IDS:str,PRODUCT_ID:$("#PRODUCT_ID").val()},
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
        
        
        //批量操作
        function saveSpecial(msg){
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
                        if(msg == '确定要添加到选中的专题吗?'){
                            top.jzts();
                            $.ajax({
                                type: "POST",
                                url: '<%=basePath%>post/saveSpecial.do?tm='+new Date().getTime(),
                                data: {DATA_IDS:str,FID:$("#FID").val()},
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
		
		
	<%-- 		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>product/excel.do';
		} --%>
		
	</script>

</body>
</html>

