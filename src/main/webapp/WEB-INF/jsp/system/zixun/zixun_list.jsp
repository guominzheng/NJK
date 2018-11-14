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
            <form action="zixun/list.do" method="post" name="Form" id="Form">
            <!-- 检索  -->
        
        
            <table id="table_report" class="table table-striped table-bordered table-hover">
                
                <thead>
                    <tr>
                        <th class="center">
                        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                        </th>
                        <th class="center">序号</th>
                        <th class="center">咨询电话</th>                     
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
                                <td class='center'>
                                            <input type="text" style="width:100px;height:10px;" name="ZIXUN" id="ZIXUN${vs.index+1}" value="${var.ZIXUN}"  />
                                            <a style="cursor:pointer;" title="快速修改" onclick="editp('${var.REMARK_ID}','${var.ZIXUN}','${vs.index+1}');" class="tooltip-success" data-rel="tooltip" data-placement="left">
                                            <span class="green"><i class="icon-edit"></i></span></a>
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
        function editp(Id,price,n){
            var PRICE = $("#ZIXUN"+n).val();
            var re = /^(([1-9][0-9]*\.[0-9][0-9]*)|([0]\.[0-9][0-9]*)|([1-9][0-9]*)|([0]{1}))$/; 
            if(re.test(PRICE)){
                if(price!=PRICE){
                    $.ajax({
                        type: "POST",
                        url: '<%=basePath%>zixun/goEditp.do?REMARK_ID='+Id+'&ZIXUN='+PRICE,
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
        
        //导出excel
        function toExcel(){
            window.location.href='<%=basePath%>pro_img/excel.do';
        }
        </script>
        
    </body>
</html>


