<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<script src="${pageContext.request.contextPath}/plugins/view/echarts.common.min.js" type="text/javascript"
        charset="utf-8"></script>
<head>
    <base href="<%=basePath%>">
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/admin/top.jsp" %>
</head>
<body>
<div id="zhongxin">
    <div class="container-fluid" id="main-container">
        <div id="page-content" class="clearfix">
            <div class="row-fluid">
                <input type="hidden" id="list" value="${list}"/>
                <div id="message" align="center"
                     style="color:white;font-size:20px;height:50px;background-color:#00a0c6;border: #1b6aaa solid 1px;border-radius:3%;">
                    ${message}
                </div>
                <hr>
                <div id="main" style="width: 1500px;height:400px;"></div>
                <hr style="border-bottom:#00a0c6 solid 1px"/>
                <table>
                    <tr>
                        <td style="vertical-align: top;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="btn btn-small btn-success" onclick="rollback()">返回</a>
                        </td>
                    </tr>
                </table>
                <script type="text/javascript">
                    var myChart = echarts.init(document.getElementById('main'));
                    var str = $("#list").val();
                    var arr = eval('(' + str + ')');
                    var arr2 = [];
                    for (var j = arr.length - 1; j >= 0; j--) {
                        arr2.push({
                            date: arr[j].SHIJIAN,
                            value: arr[j].MONEY,
                            src: "${pageContext.request.contextPath}/demo/zdemo1.do"
                        })
                    }
                    var tian = [];
                    for (var i = arr.length - 1; i >= 0; i--) {
                        tian.push({
                            value: arr[i].SHIJIAN
                        })
                    }
                    var option = {
                        xAxis: {
                            type: 'category',
                            data: tian,
                            "axisLabel": {
                                interval: 0
                            }
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [{
                            data: arr2,
                            type: 'line'
                        }
                            /*, {
                                data: [{name: "1234", value: 1120, src: "http://baidu.com"},{name: "val", value: 500}, {name: "val", value: 200}, {name: "val", value: 250}, {name: "val", value: 300}, {name: "val", value: 400}, {name: "val", value: 580}],
                                type: 'line'
                            }*/
                        ]
                    };
                    myChart.setOption(option);

                    function rollback() {
                        myChart.setOption(option);
                    }

                    myChart.on('click', function (params) {
                        // 控制台打印数据的名称
                        console.log(params.data.date);
                        console.log(params.data.src);
                        var u = params.data.src;
                        var data = params.data.date;
                        $.ajax({
                            url: u + "?pro=t",
                            data: "date=" + data,
                            type: "POST",
                            dataType: "json",
                            success: function (e) {
                                if (e.flag != null) {
                                    var message = e.message;
                                    $("#message").html(message);
                                    var dat = e.list;
                                    var arr2 = [];
                                    for (var i = 0; i < dat.length; i++) {
                                        arr2.push({
                                            date: dat[i].TIAN,
                                            value: dat[i].cunt,
                                            src: "${pageContext.request.contextPath}/demo/demo1.do"
                                        })
                                    }
                                    var tian = [];
                                    for (var i = 0; i < dat.length; i++) {
                                        tian.push({
                                            value: dat[i].TIAN
                                        })
                                    }
                                    var option = {
                                        xAxis: {
                                            type: 'category',
                                            data: tian,
                                            "axisLabel": {
                                                interval: 0
                                            }
                                        },
                                        yAxis: {
                                            type: 'value'
                                        },
                                        series: [{
                                            data: arr2,
                                            type: 'line'
                                        }
                                        ]
                                    };
                                    myChart.setOption(option);
                                } else {
                                    var message = e.message;
                                    $("#message").html(message);
                                    var dat = e.data;
                                    var arr2 = [];
                                    for (var i = 0; i < dat.length; i++) {
                                        arr2.push({
                                            value: dat[i].cunt
                                        })
                                    }
                                    var tian = [];
                                    for (var i = 0; i < dat.length; i++) {
                                        tian.push({
                                            value: dat[i].SHI
                                        })
                                    }
                                    var option = {
                                        xAxis: {
                                            type: 'category',
                                            data: tian
                                        },
                                        yAxis: {
                                            type: 'value'
                                        },
                                        series: [{
                                            data: arr2,
                                            type: 'line'
                                        }
                                        ]
                                    };
                                    myChart.setOption(option);
                                }
                            }
                        });
                    });
                </script>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.7.2.js"></script>
<!-- 下拉框 -->
<!-- 日期框 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootbox.min.js"></script>
<!-- 确认窗口 -->
<!-- 引入 -->
<!--提示框-->
<script type="text/javascript">
    $(top.hangge());
</script>

<script type="text/javascript">
    function serchFocus() {
        $("#nav-search-input").val("");
    }
</script>
</body>
</html>

