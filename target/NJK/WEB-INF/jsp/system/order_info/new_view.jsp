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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-datepicker.min.js"></script>
<!-- 日期框 -->
<script type="text/javascript">
    $(function(){
        $("#bac").hide();
    });
</script>
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
                <div>
                    <table>
                        <tr>
                            <td><input class="span10 date-picker" name="BEGIN"
                                       id="BEGIN" value="" type="text"
                                       data-date-format="yyyy-mm-dd" readonly="readonly"
                                       style="width: 150px;" placeholder="开始日期"/></td>
                            <td><input class="span10 date-picker" name="END"
                                       id="END" value="" type="text"
                                       data-date-format="yyyy-mm-dd" readonly="readonly"
                                       style="width: 150px;" placeholder="结束日期"/></td>
                            <td><input name="miaoshu" id="miaoshu" value="不添加为搜索当天，只添加开始则查询所填写的某一天" type="text"
                                       readonly="readonly"
                                       style="width: 400px;"/></td>
                            <td style="vertical-align: top;">
                                <button
                                        class="btn btn-mini btn-light" onclick="zhou();" title="检索">
                                    <i id="nav-search-icon" class="icon-search"></i>
                                </button>
                            </td>
                              <%--  <c:if test="">--%>
                                 <%--<td style="vertical-align: top;">
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                             <input class="btn btn-small btn-success" onclick="bac()" value="返回" id="bac" style="width: 50px;"/>
                                 </td>--%>
                              <%--  </c:if>--%>
                            <br>
                        </tr>
                    </table>
                </div>
                <div id="main" style="width: 1500px;height:400px;"></div>
                <hr style="border-bottom:#00a0c6 solid 1px"/>
                <table>
                    <tr>
                        <td style="vertical-align: top;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="btn btn-small btn-success" onclick="sdemo()">上8天单量金额查询</a>
                        </td>
                        <td style="vertical-align: top;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="btn btn-small btn-success" onclick="ssdemo()">上四周单量金额查询</a>
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
                            date: arr[j].TIAN,
                            value: arr[j].MONEY,
                            src: "${pageContext.request.contextPath}/demo/demo1.do"
                        })
                    }
                    var tian = [];
                    for (var i = arr.length - 1; i >= 0; i--) {
                        tian.push({
                            value: arr[i].TIAN
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
                    /**
                     * 所有view上的按钮
                     */
                    //======================= start ==============================
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
                                                rotate: 0,
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
                                            data: tian,
                                            "axisLabel": {
                                                rotate: 0,
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
                                }
                            }
                        });
                    });

                    //======================= end ==============================


                    /**
                     * 按时间查询某个阶段的单量
                     */
                    //====================== start ====================
                    function zhou() {
                        var BEGIN = $("#BEGIN").val();
                        var END = $("#END").val();
                        //开始和结束都为空时
                        if (BEGIN == null || BEGIN == '') {
                            //获取当前时间
                            var nowDate = new Date();
                            var year = nowDate.getFullYear();
                            var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1)
                                : nowDate.getMonth() + 1;
                            var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate
                                .getDate();
                            var dateStr = year + "-" + month + "-" + day;
                            //end

                            var data = dateStr;
                            $.ajax({
                                url: "<%= basePath%>demo/demo1.do?pro=t",
                                data: "date=" + data,
                                type: "POST",
                                dataType: "json",
                                success: function (e) {
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
                                }
                            });
                            //开始不为空则结束为空时
                        } else if ((BEGIN != null || BEGIN != '') && END == '') {
                            $.ajax({
                                url: "<%= basePath%>demo/demo1.do?pro=t",
                                data: "date=" + BEGIN,
                                type: "POST",
                                dataType: "json",
                                success: function (e) {
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
                                    $("#BEGIN").val("");
                                }
                            });
                            //开始和结束都不为空时
                        } else if (BEGIN !== '' && END != '') {

                            $.ajax({
                                url: "<%= basePath%>demo/sddemo.do",
                                data: "BEGIN=" + BEGIN + "&END=" + END,
                                type: "POST",
                                dataType: "json",
                                success: function (e) {
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
                                                rotate: 90,
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
                                    $("#BEGIN").val("");
                                    $("#END").val("");
                                   /* $("#bac").show();*/
                                }
                            });
                        }
                    }

                    //====================== 按时间查询end ====================
                    function bac(){
                        var BEGIN = $("#BEGIN").val();
                        var END = $("#END").val();
                        $.ajax({
                            url: "<%= basePath%>demo/sddemo.do",
                            data: "BEGIN=" + BEGIN + "&END=" + END,
                            type: "POST",
                            dataType: "json",
                            success: function (e) {
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
                                            rotate: 90,
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
                                /*$("#BEGIN").val("");
                                $("#END").val("");
                                $("#bac").hide();*/
                            }
                        });
                    }

                    /**
                     * 搜索前8天的单量数据
                     */
                    //====================== start ====================
                    function sdemo() {
                        $.ajax({
                            url: "<%= basePath%>demo/sdemo.do",
                            data: "",
                            type: "POST",
                            dataType: "json",
                            success: function (e) {
                                var message = e.message;
                                $("#message").html(message);
                                var dat = e.list;
                                var arr2 = [];
                                for (var i = dat.length - 1; i >= 0; i--) {
                                    arr2.push({
                                        date: dat[i].TIAN,
                                        value: dat[i].MONEY,
                                        src: "${pageContext.request.contextPath}/demo/demo1.do"
                                    })
                                }
                                var tian = [];
                                for (var i = dat.length - 1; i >= 0; i--) {
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
                            }
                        });
                    }

                    //====================== 8天end ====================

                    /**
                     * 搜索上四周的单量数据
                     */
                    //====================== start ====================
                    function ssdemo() {
                        $.ajax({
                            url: "<%= basePath%>demo/ademo2.do?",
                            data: "",
                            type: "POST",
                            dataType: "json",
                            success: function (e) {
                                var arr = e.list;
                                var message = e.message;
                                $("#message").html(message);
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
                                    ]
                                };
                                myChart.setOption(option);
                            }
                        });
                    }

                    //===========================end======================
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
    function windowprint() {

        var f = document.getElementById("printdiv");

        var mStr;
        mStr = window.document.body.innerHTML;
        f.style.display = "";

        window.document.body.innerHTML = printdiv.innerHTML;
        window.print();

        f.style.display = "none";
        window.document.body.innerHTML = mStr;
    }

    $(top.hangge());

    //检索
    function search() {
        top.jzts();
        $("#Form").submit();
    }
</script>
<script type="text/javascript">
    $(function () {
        //下拉框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect: true});

        //日期框
        $('.date-picker').datepicker();

        //复选框
        $('table th input:checkbox').on('click', function () {
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                .each(function () {
                    this.checked = that.checked;
                    $(this).closest('tr').toggleClass('selected');
                });
        });
    });

    //导出excel
    function toExcel() {
        window.location.href = '<%=basePath%>order_pro/excel.do';
    }
</script>
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

