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
                    <form action="jifen_shopController/countList.do" method="post" name="Form" id="Form">
                        <!-- 检索  -->
                        <table>
                            <tr>
                                <td><span class="input-icon"> <input
                                        autocomplete="off" id="nav-search-input" type="text" onfocus="serchFocus()"
                                        name="NAME" value="${pd.NAME}" placeholder="这里输入关键词" width="200px"/>
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>
                                <td style="vertical-align: top;">
                                    <button
                                            class="btn btn-mini btn-light" onclick="search();" title="检索">
                                        <i id="nav-search-icon" class="icon-search"></i>
                                    </button>
                                </td>
                            </tr>
                        </table>

                        <table id="table_report" class="table table-striped table-bordered table-hover">

                            <thead>
                            <tr>
                                <th class="center">
                                    <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                                </th>
                                <th class="center">序号</th>
                                <th class="center">商品名称</th>
                                <th class="center">商品图片</th>
                                <th class="center">兑换积分</th>
                                <th class="center">兑换人</th>
                                <th class="center">是否发货</th>
                                <th class="center">快递单号</th>
                                <th class="center">快递公司</th>
                                <th class="center">创建时间</th>
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
                                            <label><input type='checkbox' name='ids' value="${var.ID}" /><span class="lbl"></span></label>
                                        </td>
                                        <td class='center' style="width: 30px;">${vs.index+1}</td>
                                        <td class='center'>${var.JIFENSHOP_NAME}</td>
                                        <td class='center'>
                                            <a href="${var.JIFENSHOP_IMG}" title="" target="_blank"><img src="${var.JIFENSHOP_IMG}" alt="" width="80"></a>
                                        </td>
                                        <td class='center'>${var.NUM}</td>
                                        <td class='center'>${var.NAME}</td>
                                        <td class='center'><c:if test="${var.STATUSH eq '1'}">是</c:if><c:if test="${var.STATUSH eq '0'}">否</c:if></td>
                                        <td class='center'>${var.ORDERNUM}</td>
                                        <td class='center'>${var.PRISE}</td>
                                        <td class='center'>${var.create_date}</td>
                                        <td style="width: 30px;" class="center">
                                            <div class='hidden-phone visible-desktop btn-group'>
                                                <div class="inline position-relative">
                                                    <button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
                                                    <ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                                        <li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.ID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
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
                                   <%-- <td style="vertical-align:top;">
                                        <a class="btn btn-small btn-success" onclick="add();">新增</a>
                                        <c:if test="${QX.del == 1 }">
                                            <a class="btn btn-small btn-danger"
                                               onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
                                                    class='icon-trash'></i></a>
                                        </c:if>
                                    </td>--%>
                                    <td style="vertical-align:top;">
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
        diag.URL = '<%=basePath%>jifen_shopController/goAdd.do';
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
                    url: '<%=basePath%>jifen_shopController/goEditp.do?INTEGRAL_ID='+Id+'&MONEY='+PRICE,
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
                var url = "<%=basePath%>jifen_shopController/delete.do?ID="+Id+"&tm="+new Date().getTime();
                $.get(url,function(data){
                    nextPage("${page.currentPage}");
                });
            }
        });
    }

    //批量操作
    function makeAll(msg) {
        bootbox.confirm(msg, function (result) {
            if (result) {
                var str = '';
                for (var i = 0; i < document.getElementsByName('ids').length; i++) {
                    if (document.getElementsByName('ids')[i].checked) {
                        if (str == '') str += document.getElementsByName('ids')[i].value;
                        else str += ',' + document.getElementsByName('ids')[i].value;
                    }
                }
                if (str == '') {
                    bootbox.dialog("您没有选择任何内容!",
                        [
                            {
                                "label": "关闭",
                                "class": "btn-small btn-success",
                                "callback": function () {
                                    //Example.show("great success");
                                }
                            }
                        ]
                    );

                    $("#zcheckbox").tips({
                        side: 3,
                        msg: '点这里全选',
                        bg: '#AE81FF',
                        time: 8
                    });

                    return;
                } else {
                    if (msg == '确定要删除选中的数据吗?') {
                        top.jzts();
                        $.ajax({
                            type: "POST",
                            url: '<%=basePath%>jifen_shopController/deleteAll.do?tm=' + new Date().getTime(),
                            data: {DATA_IDS: str},
                            dataType: 'json',
                            //beforeSend: validateData,
                            cache: false,
                            success: function (data) {
                                $.each(data.list, function (i, list) {
                                    nextPage("${page.currentPage}");
                                });
                            },
                            error: function () {
                                nextPage("${page.currentPage}");
                            }
                        });
                    }
                }
            }
        });
    }

    //修改
    function edit(Id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="编辑";
        diag.URL = '<%=basePath%>jifen_shopController/goEditCount.do?ID='+Id;
        diag.Width = 400;
        diag.Height = 400;
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


