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
                    <form action="order_info/list.do" method="post" name="Form"
                        id="Form">
                        <table>
                            
                        </table>
                        <!-- 检索  -->


<!--                        <table id="table_report"
                            class="table table-striped table-bordered table-hover"> -->
                        <table
                            class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr style="background-color:rgb(220,220,220); height:30px; width:100%;">
                                    <th class="center"><label><input type="checkbox"
                                            id="zcheckbox" /><span class="lbl"></span></label></th>
                                    <th class="center">序号</th>
                                    <th class="center">订单号</th>
                                    <th class="center">下单时间</th>
                                    <th class="center">收货人</th>
                                    <th class="center">收货人电话</th>
                                    <th class="center">收货地址</th>
                                    <th class="center">支付方式</th>
                                    <th class="center">支付金额</th>
                                    <th class="center">总金额</th>
                                    <th class="center">支付时间</th>
                                    <th class="center">完成时间</th>
                                    <th class="center">备注</th>
                                    <th class="center">状态</th>
                                </tr>
                            </thead>

                            <tbody>

                                <!-- 开始循环 -->
                                <c:choose>
                                    <c:when test="${not empty varList}">
                                            <c:forEach items="${varList}" var="var" varStatus="vs">
                                                    <tr>
                                                    <td class='center' style="width: 30px;"><label><input
                                                            type='checkbox' name='ids' value="${var.ORDER_INFO_ID}" /><span
                                                            class="lbl"></span></label></td>
                                                        <!-- 序号-->
                                                    <td class='center'>${vs.index+1}</td>
                                                        <!-- 订单号-->
                                                    <td  class="center"><a
                                                        href="javascript:goods('${var.ORDER_INFO_ID}','${var.ORDER_NUMBER}')">${var.ORDER_NUMBER}</a>
                                                    </td>
                                                        <!-- 序号创建时间-->
                                                    <td  class="center">${var.ORDER_DATE}</td>
                                                        <!-- 收获客户姓名-->
                                                    <td  class="center">${var.NAME}</td>
                                                        <!-- 收获客户电话-->
                                                    <td class="center">${var.PHONE}</td>
                                                        <!-- 收获客户地址-->
                                                    <td class="center">${var.ADDRESS}</td>
                                                        <!-- -->
                                                    <td  class="center"><c:if
                                                            test="${var.PAY_METHOD == 'alipay' }">
                                                            <span class="label label-important arrowed-in">支付宝支付</span>
                                                        </c:if> <c:if test="${var.PAY_METHOD == 'wx' }">
                                                            <span class="label label-success arrowed">微信支付</span>
                                                        </c:if> <c:if test="${var.PAY_METHOD == 'xx' }">
                                                            <span class="label label-success arrowed">线下支付</span>
                                                        </c:if></td>
                                                    <td class="center">${var.PAY_MONEY}</td>
                                                    <td  class="center">${var.MONEY}</td>
                                                    <td  class="center">${var.PAY_DATE}</td>
                                                    <td  class="center">${var.END_DATE}</td>
                                                    <td  class="center">${var.BEIZHU}</td>
                                                    <td  class="center">
                                                     <c:if test="${var.STATUS == '0' }"><span class="label label-important arrowed-in">未付款</span></c:if>
                                            <c:if test="${var.STATUS == '1' }"><span class="label label-important arrowed-in">已付款</span></c:if>
                                            <c:if test="${var.STATUS == '2' }"><span class="label label-success arrowed">已发货</span></c:if>
                                            <c:if test="${var.STATUS == '3' }"><span class="label label-success arrowed">已完成</span></c:if>
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
        
        function shuaxin(){
            //location=location;
            location.assign(location);
        }
        
         //查询订单商品详情
        function goods(ID,ORDER_NUMBER){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="查询订单商品详情";
             diag.URL = '<%=basePath%>order_pro/show.do?ORDER_INFO_ID='+ID+'&ORDER_NUMBER='+ORDER_NUMBER;
             diag.Width = 800;
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
        
        
        
        //导出excel
        function toExcel(method,sta){
            window.location.href='<%=basePath%>order_info/excel.do?PAY_METHOD='+method+'&STATUS='+sta;
        }
        </script>

</body>
</html>

