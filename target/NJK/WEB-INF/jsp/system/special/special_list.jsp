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

                    <!-- 检索  -->
                    <form action="special/list.do" method="post" name="Form" id="Form">
                    <input type="hidden" name="FID" id="FID" value="${pd.FID}"/>
                        <!-- 检索  -->


                        <table id="table_report"
                            class="table table-striped table-bordered table-hover">

                            <thead>
                                <tr>
                                    <th class="center"><label><input type="checkbox"
                                            id="zcheckbox" /><span class="lbl"></span></label></th>
                                    <th class="center">序号</th>
                                    <th class="center">标题</th>
                                    <th class="center">提示</th>
                                    <th class="center">点赞量</th>
                                    <th class="center">回复数</th>
                                    <th class="center">排序</th>
                                   	<th class="center">次数</th>
                                    <th class="center">操作</th>
                                </tr>
                            </thead>

                            <tbody>

                                <!-- 开始循环 -->
                                <c:choose>
                                    <c:when test="${not empty varList}">
                                            <c:forEach items="${varList}" var="var" varStatus="vs">
                                                <tr>
                                                    <td class='center' style="width: 30px;"><label><input
                                                            type='checkbox' name='ids' value="${var.TID}" />
                                                            <span
                                                            class="lbl"></span></label></td>
                                                    <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                    <td class='center'><a
                                                        href="javascript:show('${var.TID}')">${var.SUBJECT}</a>
                                                    </td>
                                                    <td class='center'>${var.KEYWORD1}</td>
                                                    <td class='center'>${var.VIEWS}</td>
                                                    <td class='center'>${var.HUIFU}</td>
                                                    <td class='center'>${var.ORDE_BY}</td>
                                                    <td class='center'>${var.SHUZI}</td>
                                    <td style="width: 30px;" class="center">
                                    <div class='hidden-phone visible-desktop btn-group'>
                                        <div class="inline position-relative">
                                        <button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
                                        <ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                            <li><a style="cursor:pointer;" title="编辑" onclick="edit('${var.TID}');" class="tooltip-success" data-rel="tooltip" title="" data-placement="left"><span class="green"><i class="icon-edit"></i></span></a></li>
                                            <li><a style="cursor:pointer;" title="删除" onclick="del('${var.TID}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
                                        </ul>
                                        </div>
                                    </div>
                                </td>
                                                </tr>

                                            </c:forEach>
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
                                            <a class="btn btn-small btn-success" onclick="add('${pd.FID}');">新增</a>
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
        

        
        //检索
        function search(){
            top.jzts();
            $("#Form").submit();
        }
        //新增
        function add(id){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="新增";
             diag.URL = '<%=basePath%>special/goAdd.do?FID='+id;
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
                    var url = "<%=basePath%>special/delete.do?TID="+Id+"&tm="+new Date().getTime();
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
             diag.URL = '<%=basePath%>special/goEdit.do?TID='+Id;
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
                                url: '<%=basePath%>special/deleteAll.do?tm='+new Date().getTime(),
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
        
<%--        //导出excel
        function toExcel(){
            window.location.href='<%=basePath%>product/excel.do';
        } --%>
        </script>

</body>
</html>

