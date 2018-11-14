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
            <form action="weixinzhifu/list.do" method="post" name="Form" id="Form">
            <!-- 检索  -->
        
        
            <table id="table_report" class="table table-striped table-bordered table-hover">
                
                <thead>
                    <tr>
                        <th class="center">
                        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                        </th>
                        <th class="center">序号</th>
                        <th class="center">微信支付</th>
                        <th class="center">团购</th>                 
                    </tr>
                </thead>
                                        
                <tbody>
                    
                <!-- 开始循环 -->   
                <c:choose>
                    <c:when test="${not empty varList}">
                        <c:forEach items="${varList}" var="var" varStatus="vs">
                            <tr>
                                <td class='center' style="width: 30px;">
                                    <label><input type='checkbox' name='ids' value="${var.WEIXINZHIFU}" /><span class="lbl"></span></label>
                                </td>
                                <td class='center' style="width: 30px;">${vs.index+1}</td>
   								<td id="checkRadio" class='center'>
                                                    <label style="float:left;padding-left: 12px;"><input name="form-field-radio1" id="form-field-radio1" onclick="setType('0','${var.WEIXINZHIFU}');" <c:if test="${var.WEIXIN == '0' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">开</span></label>
                                                    <label style="float:left;padding-left: 5px;"><input name="form-field-radio1" id="form-field-radio1" onclick="setType('1','${var.WEIXINZHIFU}');" <c:if test="${var.WEIXIN == '1' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">关</span></label>         
                                 </td>
                                 
                                 <td id="checkRadio" class='center'>
                                                    <label style="float:left;padding-left: 12px;"><input name="form-field-radio2" id="form-field-radio2" onclick="setType1('0','${var.WEIXINZHIFU}');" <c:if test="${var.WSTATUS == '0' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">开</span></label>
                                                    <label style="float:left;padding-left: 5px;"><input name="form-field-radio2" id="form-field-radio2" onclick="setType1('1','${var.WEIXINZHIFU}');" <c:if test="${var.WSTATUS == '1' }">checked="checked"</c:if> type="radio" value="icon-edit"><span class="lbl">关</span></label>         
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
        


        
        
        //修改单价
       	      //查询订单商品详情
        function setType(WEIXIN,WEIXINZHIFU){
                        top.jzts();
                        var url = "<%=basePath%>weixinzhifu/edit.do?WEIXINZHIFU="+WEIXINZHIFU+"&WEIXIN="+WEIXIN;
                        $.get(url,function(data){
                            nextPage("${page.currentPage}");
                        });
        }
        
        function setType1(WSTATUS,WEIXINZHIFU){
            top.jzts();
            var url = "<%=basePath%>weixinzhifu/edit1.do?WEIXINZHIFU="+WEIXINZHIFU+"&WSTATUS="+WSTATUS;
            $.get(url,function(data){
                nextPage("${page.currentPage}");
            });
		}
        
        </script>
        
        <script type="text/javascript">
        
        //导出excel
        function toExcel(){
            window.location.href='<%=basePath%>pro_img/excel.do';
        }
        </script>
        
    </body>
</html>


                              