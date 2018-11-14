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
	<base href="//hitman.net.cn/NJK/">
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%>
</head>
<body>

<div class="container-fluid" id="main-container">

	<div id="page-content" class="clearfix">

		<div class="row-fluid">

			<div class="row-fluid">

				<!-- 检索  -->
				<form action="product/list.do" method="post" name="Form" id="Form">
					<table>
						<tr>
							<td><span class="input-icon"> <input
									autocomplete="off" id="nav-search-input" type="text"
									name="KEYWORD" value="${pd.KEYWORD}" placeholder="这里输入关键词" />
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>
							<td style="vertical-align: top;"><select
									class="chzn-select" name="BSTATUS" id="BSTATUS"
									data-placeholder="请选择是否包邮" title="是否包邮"
									style="vertical-align: top; width: 145px;">
								<option value="">请选择是否包邮</option>
								<option value="0"
										<c:if test="${pd.BSTATUS=='0'}">selected</c:if>>是</option>
								<option value="1"
										<c:if test="${pd.BSTATUS=='1'}">selected</c:if>>否</option>
							</select></td>
							<td style="vertical-align: top;"><select
									class="chzn-select" name="STATUS" id="STATUS"
									data-placeholder="请选择是否正在售卖" title="是否正在售卖"
									style="vertical-align: top; width: 145px;">
								<option value="">请选择是否正在售卖</option>
								<option value="0"
										<c:if test="${pd.STATUS=='0'}">selected</c:if>>是</option>
								<option value="1"
										<c:if test="${pd.STATUS=='1'}">selected</c:if>>否</option>
							</select></td>
							<td style="vertical-align:top;">
								<select
										class="chzn-select" name="GYS" id="GYS"
										data-placeholder="请选择供应商" title="供应商"
										style="vertical-align: top; width: 145px;">
									<option value="">请选择供应商</option>
									<c:forEach items="${varList1}" var="var" varStatus="vs">
										<option value="${var.GYS}" <c:if test="${var.GYS == pd.GYS }">selected</c:if>>${var.NAME}</option>
									</c:forEach>
								</select>
							</td>
							<td style="vertical-align:top;">
								<select
										class="chzn-select" name=PRODUCT_TYPE_ID id="PRODUCT_TYPE_ID"
										data-placeholder="请选择商品分类" title="商品分类"
										style="vertical-align: top; width: 145px;">
									<option value="">请选择商品分类</option>
									<c:forEach items="${varList2}" var="var" varStatus="vs">
										<option value="${var.PRODUCT_TYPE_ID}" <c:if test="${var.PRODUCT_TYPE_ID == pd.PRODUCT_TYPE_ID }">selected</c:if>>${var.TYPE_NAME}</option>
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
							<th class="center">商品名称</th>
							<th class="center">分类名称</th>
							<th class="center">查询客户</th>
							<th class="center">备注</th>
							<th class="center">封面图</th>
							<th class="center">是否包邮</th>
							<th class="center">是否上架</th>
							<th class="center">是否为套餐</th>
							<!-- <th class="center">最小购买量</th> -->
							<th class="center">库存</th>
							<th class="center">供应商</th>
							<th class="center">成分</th>
							<th class="center">负责人姓名</th>
							<th class="center">负责人电话</th>
							<th class="center">滚动图片</th>
							<th class="center">规格</th>
							<th class="center">是否有活动</th>
							<th class="center">排序</th>
							<th class="center">发货时间</th>
							<%--<th class="center">评论</th>--%>
							<th class="center">研究院</th>
							<th class="center">操作</th>
						</tr>
						</thead>

						<!-- 开始循环 -->
						<c:choose>
							<c:when test="${not empty varList}">
								<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center' style="width: 30px;"><label><input
													type='checkbox' name='ids' value="${var.PRODUCT_ID}" /><span
													class="lbl"></span></label></td>
											<!-- 序号-->
											<td class='center' style="width: 30px;">${vs.index+1}</td>
											<!-- 商品名称-->
											<td class='center'><c:if test="${var.TAOCAN == '1' }">
												<a href="javascript:ttshow('${var.PRODUCT_ID}')"><span
														class="label label-success arrowed">${var.PRODUCT_NAME}</span></a>
											</c:if>
												<c:if test="${var.TAOCAN == '0' }">
															<span
																	class="label label-success arrowed">${var.PRODUCT_NAME}</span>
												</c:if></td>
											<!-- 商品分类-->
											<td class='center'>${var.TYPE_NAME}</td>
											<td class='center'><a class="btn btn-small btn-success" onclick="pushow('${var.PRODUCT_ID}')">查询</a></td>
											<!-- 描述信息-->
											<td class='center'>${var.REMARKS}</td>
											<!-- 封面图片-->
											<td class='center'>
												<a href="${var.IMG}" title="" target="_blank"><img src="${var.IMG}" alt="" width="80"></a>
											</td>
											<!-- 包邮-->
											<td class='center'>
												<c:if test="${var.BSTATUS == '0' }">
													<span class="label label-important arrowed-in">是</span>
												</c:if> <c:if test="${var.BSTATUS == '1' }">
												<span class="label label-success arrowed">否</span>
											</c:if></td>
											<!-- 是否上架-->
											<td class='center'><c:if test="${var.STATUS == '0' }">
												<span class="label label-important arrowed-in">是</span>
											</c:if> <c:if test="${var.STATUS == '1' }">
												<span class="label label-success arrowed">否</span>
											</c:if></td>
											<!-- 是否为套餐-->
											<td class='center'>
												<c:if test="${var.TAOCAN == '0' }">
													<span class="label label-important arrowed-in">否</span>
												</c:if> <c:if test="${var.TAOCAN == '1' }">
												<span class="label label-success arrowed">是</span>
											</c:if></td>
												<%-- <td class='center'>${var.MIN}</td> --%>
											<!-- 库存-->
											<td class='center'>${var.KUCUN}</td>
											<!-- 供应商-->
											<td class='center'>${var.NAME}</td>
											<!-- 成分-->
											<td class='center'>${var.COMPONENT}</td>
											<!-- 负责人-->
											<td class='center'>${var.PNAME}</td>
											<!-- 负责人电话-->
											<td class='center'>${var.PHONE}</td>
											<!-- 滚动图片-->
											<td class='center'><a
													class="btn btn-small btn-success"
													onclick="show('${var.PRODUCT_ID}');">查看</a></td>
											<!-- 规格-->
											<td class='center'><a
													class="btn btn-small btn-success"
													onclick="rshow('${var.PRODUCT_ID}');">规格</a></td>
											<td class="'center'">
												<c:if test="${var.HSTATUS == 1 }">
													有活动
												</c:if>
												<c:if test="${var.HSTATUS == 0 }">
													无活动
												</c:if>
											</td>
											<!-- 排序-->
											<td class='center'>${var.ORDE_BY}</td>
											<!-- 发货时间-->
											<td class='center'>${var.DATE}</td>
											<!-- 评论-->
												<%--<td class='center'><a
                                                    class="btn btn-small btn-success"
                                                    onclick="comment('${var.PRODUCT_ID}','${var.PRODUCT_NAME}');">评论</a></td>--%>
											<!-- 研究院-->
											<td class='center'><c:if test="${var.TAOCAN == '0' }"><a class="btn btn-small btn-success" onclick="research('${var.PRODUCT_ID}');">研究院</a></c:if>
												<c:if test="${var.TAOCAN == '1' }"><a class="btn btn-small btn-success" onclick="comment('${var.PRODUCT_ID}');">评论</a></c:if></td>
											<td style="width: 30px;" class="center">
												<div class='hidden-phone visible-desktop btn-group'>

													<c:if test="${QX.edit != 1 && QX.del != 1 }">
														<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
													</c:if>
													<div class="inline position-relative">
														<button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
														<ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
															<c:if test="${QX.edit == 1 }">
																<li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.PRODUCT_ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
															</c:if>
															<c:if test="${QX.del == 1 }">
																<li><a style="cursor:pointer;" title="删除" onclick="del('${var.PRODUCT_ID}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
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
								</c:if> <c:if test="${QX.del == 1 }">
									<a class="btn btn-small btn-danger"
									   onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
											class='icon-trash'></i></a>
								</c:if> <!-- <a class="btn btn-small btn-success" onclick="Reset();">重置</a> -->
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
    //新增
    function add(){
        var url=location.href;
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="新增";
        diag.URL = '//hitman.net.cn/NJK/product/goAdd.do';
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
    function show(id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="显示滚动图片";
        diag.URL = '//hitman.net.cn/NJK/pro_images/show.do?PRODUCT_ID='+id;
        diag.Width = 500;
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

    //显示滚动图片
    function ttshow(id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="显示套餐商品";
        diag.URL = '//hitman.net.cn/NJK/product/tshow.do?PRODUCT_ID='+id;
        diag.Width = 1200;
        diag.Height = 1200;
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

    //查看套餐中的评论
    function comment(id,name){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="显示套餐中的评论";
        diag.URL = '//hitman.net.cn/NJK/comment_pro/show.do?PRODUCT_ID='+id+'&PRODUCT_NAME='+name;
        diag.Width = 900;
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
    function research(id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="显示商品研究院";
        diag.URL = '//hitman.net.cn/NJK/product/rshow.do?PRODUCT_ID='+id;
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

    //显示商品规格
    function rshow(id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="显示商品规格";
        diag.URL = '//hitman.net.cn/NJK/remark/list.do?PRODUCT_ID='+id;
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

    //显示商品规格
    function tshow(id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="显示套餐商品";
        diag.URL = '//hitman.net.cn/NJK/remark/list.do?PRODUCT_ID='+id;
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
    //显示商品绑定用户list
    function pushow(id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="显示套餐商品";
        diag.URL = '//hitman.net.cn/NJK/p_quanxian/uplist?PRODUCT_ID='+id;
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
                var url = "//hitman.net.cn/NJK/product/delete.do?PRODUCT_ID="+Id+"&tm="+new Date().getTime();
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
        diag.URL = '//hitman.net.cn/NJK/product/goEdit.do?PRODUCT_ID='+Id;
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
                            url: '//hitman.net.cn/NJK/product/deleteAll.do?tm='+new Date().getTime(),
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

    <%-- 		//导出excel
            function toExcel(){
                window.location.href='//hitman.net.cn/NJK/product/excel.do';
            } --%>
</script>

</body>
</html>

