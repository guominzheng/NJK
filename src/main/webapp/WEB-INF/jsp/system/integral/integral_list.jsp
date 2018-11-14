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
		<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="integral/list.do" method="post" name="Form" id="Form">
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center">序号</th>
						<th class="center">名称</th>
						<th class="center">价格</th>
						<th class="center">封面图</th>
						<th class="center">规则1</th>
						<th class="center">规则2</th>
						<th class="center">规则3</th>
						<th class="center">运费价格</th>
						<th class="center">详情</th>
						<th class="center">操作</th>
						
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
							                          
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.INTEGRAL_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td class='center'>${var.NAME}</td>
								<td class='center'>${var.MONEY}</td>
								<td class='center'>
                                     <a href="${var.IMG}" title="" target="_blank"><img src="${var.IMG}" alt="" width="80"></a>
                                </td>
                                <td class='center'>${var.GUIZE1}</td>
                                <td class='center'>${var.GUIZE2}</td>
                                <td class='center'>${var.GUIZE3}</td>
                                <td class='center'>${var.YUNFEI}</td>
                                <td class='center'><a class="btn btn-small btn-success" onclick="show('${var.INTEGRAL_ID}');">详情</a></td>
								<td style="width: 30px;" class="center">
									<div class='hidden-phone visible-desktop btn-group'>
										<div class="inline position-relative">
										<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
											<li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.INTEGRAL_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
											<li><a style="cursor:pointer;" title="删除" onclick="del('${var.INTEGRAL_ID}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
										</ul>
										</div>
									</div>
								</td>
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
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<a class="btn btn-small btn-success" onclick="add();">新增</a>
				</td>
				<td style="vertical-align:top;">
					 <div id="zhongxin2" class="center" style="display:none;background: #39f;color:#fff;border-radius:3px;width:60%;">修改单价成功</div>
					 <div id="zhongxin3" class="center" style="display:none;background: #39f;color:#fff;border-radius:3px;width:60%;">单价无变化</div>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
	</div>
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
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
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		
	      //显示商品规格
        function show(id){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="显示商品规格";
             diag.URL = '<%=basePath%>integral/show.do?INTEGRAL_ID='+id;
             diag.Width = 1000;
             diag.Height = 700;
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
		function add(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>integral/goAdd.do';
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
		
		//修改单价
		function editp(Id,price,n){
			var PRICE = $("#PRICE"+n).val();
			var re = /^(([1-9][0-9]*\.[0-9][0-9]*)|([0]\.[0-9][0-9]*)|([1-9][0-9]*)|([0]{1}))$/; 
			if(re.test(PRICE)){
				if(price!=PRICE){
					$.ajax({
						type: "POST",
						url: '<%=basePath%>integral/goEditp.do?INTEGRAL_ID='+Id+'&MONEY='+PRICE,
						success:function(data){
							if(data=="success"){
								$("#zhongxin2").show();
								setTimeout("nextPage(${page.currentPage})",500);
								
							}
						}
					});
				}else{
					$("#zhongxin3").show();
					setTimeout("$('#zhongxin3').hide()",1000);
				}
			}else{
				alert("输入价格有误！");
			}
			
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>integral/delete.do?INTEGRAL_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage("${page.currentPage}");
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
			 diag.URL = '<%=basePath%>integral/goEdit.do?INTEGRAL_ID='+Id;
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
		</script>
		
	</body>
</html>


