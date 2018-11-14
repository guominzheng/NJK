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
            <form action="tiwen/list.do" method="post" name="Form" id="Form">
        
        
            <table id="table_report" class="table table-striped table-bordered table-hover">
                
                <thead>
                    <tr>
                        <th class="center">
                        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                        </th>
                        <th class="center">序号</th>
                        <th class="center">用户</th>
                        <th class="center">内容</th>
                        <th class="center">评论</th>
                        <th class="center">回复</th>
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
                                    <label><input type='checkbox' name='ids' value="${var.TIWEN_ID}" /><span class="lbl"></span></label>
                                </td>
                                <td class='center' style="width: 30px;">${vs.index+1}</td>
                                <td class='center'>${var.NAME}</td>
                                <td class='center'>${var.MESSAGE}</td>
                                 <td class='center'><a class="btn btn-small btn-success"
                                     onclick="show1('${var.TIWEN_ID}');">评论</a></td>
                                 <td class='center'><a class="btn btn-small btn-success"
                                     onclick="show2('${var.TIWEN_ID}');">回复</a></td>
                                <td style="width: 30px;" class="center">
                                    <div class='hidden-phone visible-desktop btn-group'>
                                        <div class="inline position-relative">
                                        <button class="btn btn-mini btn-info" data-toggle="dropdown"><i class="icon-cog icon-only"></i></button>
                                        <ul class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                            <li><a style="cursor:pointer;" title="删除" onclick="del('${var.TIWEN_ID}');" class="tooltip-error" data-rel="tooltip" title="" data-placement="left"><span class="red"><i class="icon-trash"></i></span> </a></li>
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
        function show1(id){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="显示评论";
             diag.URL = '<%=basePath%>tiwen/show1.do?TIWEN_ID='+id;
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
          
        //显示商品规格
        function show2(id){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="回复";
             diag.URL = '<%=basePath%>tiwen/show2.do?TIWEN_ID='+id;
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
        
        
        //删除
        function del(Id){
            bootbox.confirm("确定要删除吗?", function(result) {
                if(result) {
                    top.jzts();
                    var url = "<%=basePath%>tiwen/delete.do?TIWEN_ID="+Id+"&tm="+new Date().getTime();
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


