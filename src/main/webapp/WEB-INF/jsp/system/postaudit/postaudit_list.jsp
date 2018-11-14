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
                <form action="postAudit/list.do" method="post" name="Form" id="Form">
                        <input type="hidden" name="pro" id="pro" value="${pd.pro}"/>
                    <table>
                        <tr>
                            <td style="vertical-align: top;"><select
                                    class="chzn-select" name="SHENCHA" id="SHENCHA"
                                    data-placeholder="请选择是否审查过" title="是否审查过"
                                    style="vertical-align: top; width: 170px;">
                                <option value="">请选择是否审查过</option>
                                <option value="1"
                                        <c:if test="${pd.SHENCHA=='1'}">selected</c:if>>是</option>
                                <option value="0"
                                        <c:if test="${pd.SHENCHA=='0'}">selected</c:if>>否</option>
                            </select></td>
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
                        <c:if test="${pd.pro eq '0'}">
                            <tr>
                                <th class="center" width="70px;"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
                                <th class="center" width="70px;">序号</th>
                                <th class="center" width="200px;">标题</th>
                                <th class="center" width="500px;">内容简述</th>
                                <th class="center" width="120px;">发布人</th>
                                <th class="center" width="250px;">发布时间</th>
                                <th class="center" width="150px;">研究院</th>
                                <th class="center" width="120px;">是否审核</th>
                                <th class="center" width="80px;">操作</th>
                            </tr>
                        </c:if>
                        <c:if test="${pd.pro eq '1'}">
                            <tr>
                                <th class="center" width="70px;"><label><input type="checkbox"
                                                                 id="zcheckbox2" /><span class="lbl"></span></label></th>
                                <th class="center" width="70px;">序号</th>
                                <th class="center">内容简述</th>
                                <th class="center" width="120px;">提问人</th>
                                <th class="center" width="250px;">提问时间</th>
                                <th class="center" width="120px;">是否审核</th>
                                <th class="center" width="80px;">操作</th>
                            </tr>
                        </c:if>

                        </thead>
                        <tbody>
                        <!-- 研究院遍历 -->
                        <c:if test="${pd.pro eq '0'}">
                            <c:choose>
                                <c:when test="${not empty varList}">
                                    <c:if test="${QX.cha == 1 }">
                                        <c:forEach items="${varList}" var="var" varStatus="vs">
                                            <tr style="height: 50px;">
                                                <td class='center' style="width: 30px;"><label><input
                                                        type='checkbox' name='ids' value="${var.RESEARCH_ID}" /><span
                                                        class="lbl"></span></label></td>
                                                <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                <td class="center" <c:if test="${var.dateF eq '1'}">style="background-image: url('http://s-425807918.vicp.io:40794/NJK/uploadFiles/uploadImgs/new1.png');background-repeat:no-repeat; " </c:if>>
                                                        ${var.SUBJECT}
                                                </td>
                                                <td class="center" style="<c:if test="${var.MESSAGE eq '无详细文字描述 ......'}">color: mediumvioletred;</c:if><c:if test="${var.MESSAGE != '无详细文字描述 ......'}">color: green;</c:if>" >
                                                        ${var.MESSAGE}
                                                </td>
                                                <td class="center">
                                                        ${var.NAME}
                                                </td>
                                                <td class="center">
                                                        ${var.DATE}
                                                </td>
                                                <td class="center">
                                                        ${var.PRODUCT_NAME}
                                                </td>
                                                <td class="center" <c:if test="${var.SHENCHA eq '0'}">style="color: mediumvioletred" onclick="edit(${var.RESEARCH_ID},'0')" </c:if><c:if test="${var.SHENCHA eq '1'}">style="color: green;" </c:if>>
                                                    <c:if test="${var.SHENCHA eq '0'}">
                                                        未审核
                                                    </c:if>
                                                    <c:if test="${var.SHENCHA eq '1'}">
                                                        已审核
                                                    </c:if>
                                                </td>
                                                <td style="width: 30px;" class="center">
                                                <div class='hidden-phone visible-desktop btn-group'>
                                                    <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                        <span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
                                                    </c:if>
                                                    <div class="inline position-relative">
                                                        <button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
                                                        <ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                                            <c:if test="${QX.del == 1 }">
                                                                <li><a style="cursor:pointer;" title="删除" onclick="del('${var.RESEARCH_ID}','0');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
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
                        </c:if>


                        <!-- 提问遍历-->
                        <c:if test="${pd.pro eq '1'}">
                            <c:choose>
                                <c:when test="${not empty varList}">
                                    <c:if test="${QX.cha == 1 }">
                                        <c:forEach items="${varList}" var="var" varStatus="vs">
                                            <tr  style="height: 50px;">
                                                <td class='center' style="width: 30px;"><label><input
                                                        type='checkbox' name='ids' value="${var.TIWEN_ID}" /><span
                                                        class="lbl"></span></label></td>
                                                <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                <td class="center" style="<c:if test="${var.MESSAGE eq '无详细文字描述 ......'}">color: mediumvioletred;</c:if><c:if test="${var.MESSAGE != '无详细文字描述 ......'}">color: green;</c:if>" >
                                                        ${var.MESSAGE}
                                                </td>
                                                <td class="center">
                                                        ${var.NAME}
                                                </td>
                                                <td class="center">
                                                        ${var.DATE}
                                                </td>
                                                <td class="center" <c:if test="${var.SHENCHA eq '0'}">style="color: mediumvioletred;" onclick="edit('${var.TIWEN_ID}','1')" </c:if><c:if test="${var.SHENCHA eq '1'}">style="color: green;" </c:if> >
                                                    <c:if test="${var.SHENCHA eq '0'}">
                                                        未审核
                                                    </c:if>
                                                    <c:if test="${var.SHENCHA eq '1'}">
                                                        已审核
                                                    </c:if>
                                                </td>
                                                <td style="width: 30px;" class="center">
                                                <div class='hidden-phone visible-desktop btn-group'>
                                                    <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                        <span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
                                                    </c:if>
                                                    <div class="inline position-relative">
                                                        <button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
                                                        <ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                                            <c:if test="${QX.del == 1 }">
                                                                <li><a style="cursor:pointer;" title="删除" onclick="del('${var.TIWEN_ID}','1');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
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
                        </c:if>

                        </tbody>
                    </table>

                    <div class="page-header position-relative">
                        <table style="width: 100%;">
                            <tr>
                                <td style="vertical-align: top;">
                                    <a class="btn btn-small btn-success" href="postAudit/list.do?pro=0">研究院</a>
                                    <a class="btn btn-small btn-success" href="postAudit/list.do?pro=1">提问</a>
                                    <a class="btn btn-small btn-success" ONCLICK="makeAll('确定要修改选中的数据吗?',${pd.pro})">批量修改</a>
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


    //显示滚动图片
    function research(id){
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="显示商品研究院";
        diag.URL = '<%=basePath%>product/rshow.do?PRODUCT_ID='+id;
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

    //研究院删除
    function del(Id,pro){
        bootbox.confirm("确定要删除吗?", function(result) {
            if(result) {
                top.jzts();
                var url = "<%=basePath%>postAudit/delete.do?id="+Id+"&pro="+pro+"&tm="+new Date().getTime();
                $.get(url,function(data){
                    nextPage("${page.currentPage}");
                });
            }
        });
    }

    //研究院审核
    function edit(Id,pro){
        bootbox.confirm("确定审核完成吗?", function(result) {
            if(result) {
                top.jzts();
                var url = "<%=basePath%>postAudit/edit?id="+Id+"&pro="+pro+"&tm="+new Date().getTime();
                $.get(url,function(data){
                    nextPage("${page.currentPage}");
                });
            }
        });
    }
    //提问
    function edit1(Id,pro){
        bootbox.confirm("确定审核完成吗?", function(result) {
            if(result) {
                top.jzts();
                var url = "<%=basePath%>postAudit/edit?id="+Id+"&pro="+pro+"&tm="+new Date().getTime();
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
    function makeAll(msg,pro){
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
                    if(msg == '确定要修改选中的数据吗?'){
                        top.jzts();
                        $.ajax({
                            type: "POST",
                            url: '<%=basePath%>postAudit/updateAll.do?tm='+new Date().getTime(),
                            data: {DATA_IDS:str,pro:pro},
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

