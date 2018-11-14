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
<div id="zhongxin">
    <div class="container-fluid" id="main-container">


        <div id="page-content" class="clearfix">

            <div class="row-fluid">

                <div class="row-fluid">

                    <%--<!-- 检索  -->
                    <form action="product/rshow.do" method="post" name="Form" id="Form">
                        <input type="hidden" name="PRODUCT_ID" id="PRODUCT_ID" value="${pd.PRODUCT_ID}"/>
                        <table>
                            <tr>
                                <td><span class="input-icon"> <input
                                        autocomplete="off" id="nav-search-input" type="text"
                                        name="KEYWORD" value="${pd.KEYWORD}" placeholder="这里输入关键词" />
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>
                                <td style="vertical-align: top;"><button
                                        class="btn btn-mini btn-light" onclick="search();" title="检索">
                                    <i id="nav-search-icon1" class="icon-search"></i>
                                </button></td>
                            </tr>
                        </table>
                        <!-- 检索  -->--%>


                        <table id="table_report"
                               class="table table-striped table-bordered table-hover">

                            <thead>
                            <tr>
                                <th class="center"><label><input type="checkbox"
                                                                 id="zcheckbox" /><span class="lbl"></span></label></th>
                                <th class="center">序号</th>
                                <th class="center">评论人</th>
                                <th class="center">评论内容</th>
                                <th class="center">评论时间</th>
                                <th class="center">@</th>
                                <th class="center">回复</th>
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
                                                        type='checkbox' name='ids' value="${var.COMMENT_PRO_ID}" /><span
                                                        class="lbl"></span></label></td>
                                                <!-- 序号-->
                                                <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                <!-- 评论人-->
                                                <td class='center'><span
                                                        class="label label-success arrowed">${var.NAME}</span></td>
                                                <!-- 评论时间-->
                                                <td class='center'>${var.MESSAGE}</td>
                                                <!-- @-->
                                                <td class='center'>${var.DATE}</td>
                                                <!--回复角色 -->
                                                <td class='center'>${var.PNAME}</td>
                                                <!-- 回复-->
                                                <td class='center'><a
                                                        class="btn btn-small btn-success"
                                                        onclick="saveR('${var.COMMENT_PRO_ID}','${pd.PRODUCT_ID}');">回复</a></td>

                                                    <%--

                                                                                                    <td class='center'>
                                                                                                        <c:if test="${var.STATUS == '0' }">
                                                                                                            <span class="label label-important arrowed-in">否</span>
                                                                                                        </c:if> <c:if test="${var.STATUS == '1' }">
                                                                                                        <span class="label label-success arrowed">是</span>
                                                                                                    </c:if></td>

                                                                                                    <td class='center'><a
                                                                                                            class="btn btn-small btn-success"
                                                                                                            onclick="lookComment('${var.RESEARCH_ID}');">查看评论</a></td>
                                                                                                    <td style="width: 30px;" class="center">
                                                                                                        <div class='hidden-phone visible-desktop btn-group'>
                                                                                                            <div class="inline position-relative">
                                                                                                                <button class="btn btn-mini btn-info"
                                                                                                                        data-toggle="dropdown">
                                                                                                                    <i class="icon-cog icon-only"></i>
                                                                                                                </button>--%><%--  <ul
                                                                    class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                                                <c:if test="${QX.del == 1 }">
                                                                    <li><a style="cursor: pointer;" title="删除"
                                                                           onclick="del('${var.RESEARCH_ID}');"
                                                                           class="tooltip-error" data-rel="tooltip" title=""
                                                                           data-placement="left"><span class="red"><i
                                                                            class="icon-trash">删除</i></span></a></li>
                                                                </c:if>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </td>--%>
                                                <td style="width: 30px;" class="center">
                                                    <div class='hidden-phone visible-desktop btn-group'>

                                                        <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                            <span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
                                                        </c:if>
                                                        <div class="inline position-relative">
                                                            <button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
                                                            <ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                                                <c:if test="${QX.del == 1 }">
                                                                    <li><a style="cursor:pointer;" title="删除" onclick="del('${var.COMMENT_PRO_ID}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
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
                                        <a class="btn btn-small btn-success" onclick="saveUR('${pd.PRODUCT_ID}');">回复</a>
                                    </c:if>
                                        <c:if test="${QX.del == 1 }">
                                            <a class="btn btn-small btn-danger"
                                               onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
                                                    class='icon-trash'></i></a>
                                        </c:if> <%-- <a class="btn btn-small btn-success" onclick="Reset();">重置</a>--%>
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
</div>
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



    //修改是否为精
    function setType(TSTATUS,RESEARCH_ID){
        top.jzts();
        var url = "<%=basePath%>product/editTstatus.do?RESEARCH_ID="+RESEARCH_ID+"&TSTATUS="+TSTATUS;
        $.get(url,function(data){
            nextPage("${page.currentPage}");
        });
    }



    //删除评论
    function del(Id) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>product/comRsdelete.do?COMMENT_RESEARCH_ID="+Id+"&tm="+new Date().getTime();
                $.get(url, function (data) {
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
        diag.URL = '<%=basePath%>product/goREdit.do?RESEARCH_ID='+Id;
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
    //批量删除
    makeAll
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
                            url: '<%=basePath%>product/comRsdeleteAll.do?tm=' + new Date().getTime(),
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
    //新增
    function add(id){
        var url=location.href;
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="新增";
        diag.URL = '<%=basePath%>comment_pro/goRAdd.do?PRODUCT_ID='+id;
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

    //添加评论回复
    function saveR(id,proid){
        var url=location.href;
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="新增";
        diag.URL = '<%=basePath%>comment_pro/gosaveCompro.do?COMMENT_PRO_ID='+id+'&PRODUCT_ID='+proid;
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
    //添加评论回复
    function saveUR(id){
        var url=location.href;
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="添加主评论";
        diag.URL = '<%=basePath%>comment_pro/goSaveUR.do?PRODUCT_ID='+id;
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

    <%-- 		//导出excel
        function toExcel(){
            window.location.href='<%=basePath%>product/excel.do';
        } --%>

</script>

</body>
</html>

