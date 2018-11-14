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
                    <form action="ogin_record/list.do" method="post" name="Form" id="Form">
                        <!-- 检索  -->


                        <table id="table_report"
                            class="table table-striped table-bordered table-hover">

                            <thead>
                                <tr>
                                    <th class="center"><label><input type="checkbox"
                                            id="zcheckbox" /><span class="lbl"></span></label></th>
                                    <th class="center">序号</th>
                                    <th class="center">时间</th>
                                    <th class="center">登录数量</th>
                                  	<th class="center">登录次数</th>
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
                                                            type='checkbox' name='ids' value="${var.PRODUCT_ID}" /><span
                                                            class="lbl"></span></label></td>
                                                    <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                     <td class="center">
                                                         <a href="javascript:show('${var.DATE}')">${var.DATE}</a>
                                                        </td>
                                                    <td class='center'>${var.cont1}</td>
                                                    <td class='center'><a
                                                        class="btn btn-small btn-success"
                                                        onclick="renshu('${var.DATE}');">查询登录人数</a></td>
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
                                     <td style="vertical-align:top;">
                                      <a class="btn btn-small btn-success" onclick="xiaoyi();">用户登录月对比图</a>
                                       <a class="btn btn-small btn-success" onclick="xiaoyi2();">用户登录周对比图</a>
                                      <a class="btn btn-small btn-success" onclick="xiaoyi1();">用户登录天对比图</a>
                                     </td>
                                    <td style="vertical-align: top;">
                                    <div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
                                    </td>
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
        
        
      //查询订单商品详情
        function xiaoyi(){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="用户登录对比";
             diag.URL = '<%=basePath%>ogin_record/xiaoyi.do';
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
        
        //查询订单商品详情
        function xiaoyi1(){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="用户登录对比";
             diag.URL = '<%=basePath%>ogin_record/xiaoyi1.do';
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
        
        //查询订单商品详情
        function xiaoyi2(){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="用户登录对比";
             diag.URL = '<%=basePath%>ogin_record/xiaoyi2.do';
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
        
        
        //查询订单商品详情
        function show(DATE){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="查询用户登录";
             diag.URL = '<%=basePath%>ogin_record/show.do?DATE='+DATE;
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
        
      //查询订单商品详情
        function renshu(DATE){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="查询用户登录";
             diag.URL = '<%=basePath%>ogin_record/renshu.do?DATE='+DATE;
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

        </script>

</body>
</html>
