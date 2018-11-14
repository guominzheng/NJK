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
			<form action="remark/list.do" method="post" name="Form" id="Form">
			<input type="hidden" name="PRODUCT_ID" id="PRODUCT_ID" value="${pd.PRODUCT_ID}"/>
			<input type="hidden" name="REMARK_ID" id="REMARK_ID" value="${pd.REMARK_ID}"/>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center">序号</th>
						<th class="center">规格</th>
						<th class="center">价格</th>
						<th class="center">库存</th>
						<th class="center">最小购买量</th>
						<th class="center">图片</th>
						<th class="center">套餐图片</th>
						<th class="center">排序</th>
						<th class="center">价格</th>
						<th class="center">说明</th>
						<th class="center">备注</th>
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
									<label><input type='checkbox' name='ids' value="${var.REMARK_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td class='center'>${var.REMARK}</td>
<%-- 								<td class='center'>${var.PRICE}</td> --%>
								<td class='center'>
											<input type="text" style="width: 40px;height:10px;" name="PRICE" id="PRICE${vs.index+1}" value="${var.PRICE}"  />
											<a style="cursor:pointer;" title="快速修改" onclick="editp('${var.REMARK_ID}','${var.PRICE}','${vs.index+1}');" class="tooltip-success" data-rel="tooltip" data-placement="left">
											<span class="green"><i class="icon-edit"></i></span></a>
								</td>
							<td class='center'>
								<input type="text" style="width: 40px;height:10px;" name="KUCUN" id="KUCUN${vs.index+1}" value="${var.KUCUN}"  />
								<a style="cursor:pointer;" title="快速修改" onclick="editK('${var.REMARK_ID}','${var.KUCUN}','${vs.index+1}');" class="tooltip-success" data-rel="tooltip" data-placement="left">
									<span class="green"><i class="icon-edit"></i></span></a>
							</td>
								<td class='center'>${var.NUM}</td>
								<td class='center'>
                                     <a href="${var.IMG}" title="" target="_blank"><img src="${var.IMG}" alt="" width="80"></a>
                                </td>
                                <td class='center'>
                                     <a href="${var.TIMG}" title="" target="_blank"><img src="${var.TIMG}" alt="" width="80"></a>
                                </td>
								<td class='center'>${var.ORDE_BY}</td>
								<td class='center'><a class="btn btn-small btn-success"
                                     onclick="show('${var.REMARK_ID}');">价格</a></td>
                                <td class='center'>${var.EXPLAIN1}</td>
                                <td class='center'>${var.SHUMING}</td>
								<td style="width: 30px;" class="center">
									<div class='hidden-phone visible-desktop btn-group'>
										<div class="inline position-relative">
										<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
										<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
											<li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.REMARK_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
											<li><a style="cursor:pointer;" title="删除" onclick="del('${var.REMARK_ID}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
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
					<a class="btn btn-small btn-success" onclick="add('${pd.PRODUCT_ID}');">新增</a>
					<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>
				</td>
				<td style="vertical-align:top;">
					 <div id="zhongxin2" class="center" style="display:none;background: #39f;color:#fff;border-radius:3px;width:60%;">修改成功</div>
					 <div id="zhongxin3" class="center" style="display:none;background: #39f;color:#fff;border-radius:3px;width:60%;">无变化</div>
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
             diag.URL = '<%=basePath%>price/list.do?REMARK_ID='+id;
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
			 diag.URL = '<%=basePath%>remark/goAdd.do?PRODUCT_ID='+id;
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
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>remark/delete.do?REMARK_ID="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>remark/goEdit.do?REMARK_ID='+Id;
			 diag.Width = 600;
			 diag.Height = 700;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage("${page.currentPage}");
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
						url: '<%=basePath%>remark/goEditp.do?REMARK_ID='+Id+'&PRICE='+PRICE,
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
        function editK(Id,kucun,n){
            var KUCUN = $("#KUCUN"+n).val();
            var re = /^(([1-9][0-9]*\.[0-9][0-9]*)|([0]\.[0-9][0-9]*)|([1-9][0-9]*)|([0]{1}))$/;
            if(re.test(KUCUN)){
                if(kucun!=KUCUN){
                    $.ajax({
                        type: "POST",
                        url: '<%=basePath%>remark/goEditK?REMARK_ID='+Id+'&KUCUN='+KUCUN,
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
                alert("输入有误！");
            }

        }
		
		//修改单价
		function editpp(Id,price,n){
			var PRICE = $("#PRICE"+n).val();
			var re = /^(([1-9][0-9]*\.[0-9][0-9]*)|([0]\.[0-9][0-9]*)|([1-9][0-9]*)|([0]{1}))$/; 
			if(re.test(PRICE)){
				if(price!=PRICE){
					$.ajax({
						type: "POST",
						url: '<%=basePath%>remark/goEditp.do?REMARK_ID='+Id+'&PRICE='+PRICE,
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
								url: '<%=basePath%>remark/deleteAll.do?tm='+new Date().getTime(),
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
		function toExcel(){
			window.location.href='<%=basePath%>pro_img/excel.do';
		}
		</script>
		
	</body>
</html>


