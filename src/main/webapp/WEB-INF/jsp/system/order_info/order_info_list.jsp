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
<head>
    <base href="<%=basePath%>">
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/admin/top.jsp" %>
</head>
<body>

<div class="container-fluid" id="main-container">


    <div id="page-content" class="clearfix">

        <div class="row-fluid">

            <div class="row-fluid">

                <!-- 检索  -->
                <form action="order_info/list.do" method="post" name="Form"
                      id="Form">
                    <table>
                        <tr>
                            <td><span class="input-icon"> <input
                                    autocomplete="off" id="nav-search-input" type="text"
                                    name="KEYWORD" value="${pd.KEYWORD}" placeholder="关键词"/>
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>
                            <td><span class="input-icon"> <input
                                    autocomplete="off" id="nav-search-input" type="text"
                                    name="JIAN" value="${pd.JIAN}" placeholder="优惠金额"/>
										<i id="nav-search-icon" class="icon-search"></i>
								</span></td>
                            <td><input class="span10 date-picker" name="lastLoginStart"
                                       id="lastLoginStart" value="${pd.lastLoginStart}" type="text"
                                       data-date-format="yyyy-mm-dd" readonly="readonly"
                                       style="width: 88px;" placeholder="开始日期"/></td>
                            <td><input class="span10 date-picker" name="lastLoginEnd"
                                       id="lastLoginEnd" value="${pd.lastLoginEnd}" type="text"
                                       data-date-format="yyyy-mm-dd" readonly="readonly"
                                       style="width: 88px;" placeholder="结束日期"/></td>
                            <td style="vertical-align: top;"><select
                                    class="chzn-select" name="EXCLU_ID" id="EXCLU_ID"
                                    data-placeholder="请选择客服人员"
                                    style="vertical-align: top; width: 130px;">
                                <option value=""></option>
                                <option value="">全部</option>
                                <c:forEach items="${eclist}" var="ec">
                                    <option value="${ec.EXCLU_ID}">${ec.NAME}</option>
                                </c:forEach>
                            </select></td>
                            <td style="vertical-align: top;"><select
                                    class="chzn-select" name="PAY_METHOD" id="PAY_METHOD"
                                    data-placeholder="请选择支付方式"
                                    style="vertical-align: top; width: 130px;">
                                <option value=""></option>
                                <option value="">全部</option>
                                <option value="alipay"
                                        <c:if test="${pd.PAY_METHOD=='alipay'}">selected</c:if>>支付宝支付
                                </option>
                                <option value="wx"
                                        <c:if test="${pd.PAY_METHOD=='wx'}">selected</c:if>>微信支付
                                </option>
                                <option value="ye"
                                        <c:if test="${pd.PAY_METHOD=='xx'}">selected</c:if>>线下支付
                                </option>
                            </select></td>
                            <td style="vertical-align: top;"><select
                                    class="chzn-select" name="STATUS" id="STATUS"
                                    data-placeholder="请选择状态"
                                    style="vertical-align: top; width: 120px;">
                                <option value=""></option>
                                <option value="">全部</option>
                                <option value="0"
                                        <c:if test="${pd.STATUS=='0'}">selected</c:if>>未付款
                                </option>
                                <option value="1"
                                        <c:if test="${pd.STATUS=='1'}">selected</c:if>>已付款
                                </option>
                                <option value="2"
                                        <c:if test="${pd.STATUS=='2'}">selected</c:if>>已发货
                                </option>
                                <option value="3"
                                        <c:if test="${pd.STATUS=='3'}">selected</c:if>>已完成
                                </option>
                                <%-- <option value="7" <c:if test="${pd.STATUS=='7'}">selected</c:if>>已取消</option> --%>
                            </select></td>
                            <td style="vertical-align: top;"><select
                                    class="chzn-select" name="STATUS" id="STATUS"
                                    data-placeholder="请选择状态"
                                    style="vertical-align: top; width: 120px;">
                                <option value=""></option>
                                <option value="">全部</option>
                                <option value="0"
                                        <c:if test="${pd.STATUS=='0'}">selected</c:if>>未付款
                                </option>
                                <option value="1"
                                        <c:if test="${pd.STATUS=='1'}">selected</c:if>>已付款
                                </option>
                                <option value="2"
                                        <c:if test="${pd.STATUS=='2'}">selected</c:if>>已发货
                                </option>
                                <option value="3"
                                        <c:if test="${pd.STATUS=='3'}">selected</c:if>>已完成
                                </option>
                                <%-- <option value="7" <c:if test="${pd.STATUS=='7'}">selected</c:if>>已取消</option> --%>
                            </select></td>
                            <td style="vertical-align: top;"><select
                                    class="chzn-select" name="CSTATUS" id="CSTATUS"
                                    data-placeholder="请选择处理或未处理"
                                    style="vertical-align: top; width: 120px;">
                                <option value=""></option>
                                <option value="">全部</option>
                                <option value="0"
                                        <c:if test="${pd.CSTATUS=='0'}">selected</c:if>>未处理
                                </option>
                                <option value="1"
                                        <c:if test="${pd.CSTATUS=='1'}">selected</c:if>>已处理
                                </option>
                                <%-- <option value="7" <c:if test="${pd.STATUS=='7'}">selected</c:if>>已取消</option> --%>
                            </select></td>
                            <td style="vertical-align: top;"><select
                                    class="chzn-select" name="PROVINCE" id="PROVINCE"
                                    data-placeholder="请选择省" title="省"
                                    style="vertical-align: top; width: 145px;" onchange="jz();">
                                <option value="">请选择省</option>
                                <c:forEach items="${list_a}" var="var" varStatus="vs">
                                    <option value="${var.NAME}"
                                            <c:if test="${var.NAME == pd.PROVINCE }">selected</c:if>>${var.NAME}</option>
                                </c:forEach>
                            </select></td>
                            <%-- 				<td style="vertical-align:top;">
                            <select
                                class="chzn-select" name="CITY" id="CITY"
                                data-placeholder="请选择市" title="市"
                                style="vertical-align: top; width: 145px;">
                                    <option value="">请选择市</option>
                                    <c:forEach items="${varList1}" var="var" varStatus="vs">
                                    <option value="${var.NAME}" <c:if test="${var.NAME == pd.CITY }">selected</c:if>>${var.NAME}</option>
                                    </c:forEach>
                            </select>
                </td> --%>
                            <td style="vertical-align: top;">
                                <button
                                        class="btn btn-mini btn-light" onclick="search();" title="检索">
                                    <i id="nav-search-icon" class="icon-search"></i>
                                </button>
                            </td>
                            <c:if test="${QX.cha == 1 }">
                                <td style="vertical-align: top;"><a
                                        class="btn btn-mini btn-light"
                                        onclick="toExcel('${pd.PAY_METHOD}','${pd.STATUS}');"
                                        title="导出到EXCEL"><i id="nav-search-icon"
                                                            class="icon-download-alt"></i></a></td>
                            </c:if>
                        </tr>
                    </table>
                    <!-- 检索  -->


                    <!-- 						<table id="table_report"
                                                class="table table-striped table-bordered table-hover"> -->
                    <table
                            class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr style="background-color:rgb(220,220,220); height:30px; width:100%;">
                            <th class="center"><label><input type="checkbox"
                                                             id="zcheckbox"/><span class="lbl"></span></label></th>
                            <th class="center">序号</th>
                            <th class="center">订单号</th>
                            <th class="center">用户账号</th>
                            <th class="center">用户姓名</th>
                            <th class="center">下单时间</th>
                            <%--<th class="center">收货人</th>
                                <th class="center">收货人电话</th>
                                <th class="center">收货地址</th>
                                <th class="center">支付方式</th>
                                <th class="center">支付金额</th>
                                <th class="center">总金额</th>--%>
                            <th class="center">支付时间</th>
                            <th class="center">处理时间</th>
                            <%--<th class="center">备注</th>--%>
                            <th class="center">负责人</th>
                            <th class="center">支付方式</th>
                            <th class="center">状态</th>
                            <th class="center">退款</th>
                            <th class="center">处理</th>
                            <th class="center">操作</th>
                        </tr>
                        </thead>

                        <tbody>

                        <!-- 开始循环 -->
                        <c:choose>
                            <c:when test="${not empty varList}">
                                <c:if test="${QX.cha == 1 }">
                                    <c:forEach items="${varList}" var="var" varStatus="vs">
                                        <c:if test="${var.STATUS!=11}">
                                            <c:if test="${var.CSTATUS==0 }">
                                                <tr>
                                                    <!-- 序号-->
                                                    <td class='center' style="width: 30px;"><label><input
                                                            type='checkbox' name='ids'
                                                            value="${var.ORDER_INFO_ID}"/><span
                                                            class="lbl"></span></label></td>
                                                    <td class='center'
                                                        style="width: 30px; background-color:#AAAAAA;">${vs.index+1}</td>
                                                    <!-- 订单号-->

                                                    <td style="background-color:#AAAAAA;" class="center"><a
                                                            href="javascript:goods('${var.ORDER_INFO_ID}','${var.ORDER_NUMBER}')">${var.ORDER_NUMBER}</a>
                                                    </td>
                                                    <!-- 用户名-->
                                                    <td style="background-color:#AAAAAA;" class="center"><c:if
                                                            test="${not empty var.USNAME}">
                                                        <a href="javascript:findOrder('${var.ORDER_INFO_ID}','${var.USERNAME}')"> ${var.USERNAME}</a>
                                                    </c:if> <c:if test="${empty var.USNAME}">
                                                        <span style="color: red;">用户已注销</span>
                                                    </c:if></td>

                                                    <td style="background-color:#AAAAAA;" class="center"><c:if
                                                            test="${not empty var.NAME}">
                                                        ${var.NAME}
                                                    </c:if> <c:if test="${empty var.NAME}">
                                                        <span style="color: red;">用户已注销</span>
                                                    </c:if></td>
                                                    <!-- 下单时间-->
                                                    <td style="background-color:#AAAAAA;"
                                                        class="center">${var.ORDER_DATE}</td>
                                                        <%-- <td style="background-color:#AAAAAA;" class="center">${var.NAME}</td>--%>
                                                        <%--<td style="background-color:#AAAAAA;" class="center">${var.PHONE}</td>--%>
                                                        <%--<td style="background-color:#AAAAAA;" class="center">${var.CITY} ${var.ADDRESS}</td>--%>
                                                        <%--<td style="background-color:#AAAAAA;" class="center"><c:if
                                                                test="${var.PAY_METHOD == 'alipay' }">
                                                                <span class="label label-important arrowed-in">支付宝支付</span>
                                                            </c:if> <c:if test="${var.PAY_METHOD == 'wx' }">
                                                                <span class="label label-success arrowed">微信支付</span>
                                                            </c:if> <c:if test="${var.PAY_METHOD == 'xx' }">
                                                                <span class="label label-success arrowed">线下支付</span>
                                                            </c:if></td>--%>
                                                        <%--<td style="background-color:#AAAAAA;" class="center">${var.PAY_MONEY}</td>
                                                        <td style="background-color:#AAAAAA;" class="center">${var.MONEY}</td>--%>
                                                    <!-- 购买时间-->
                                                    <td style="background-color:#AAAAAA;"
                                                        class="center">${var.PAY_DATE}</td>
                                                    <!-- 处理结束时间-->
                                                    <td style="background-color:#AAAAAA;"
                                                        class="center">${var.END_DATE}</td>
                                                    <!-- 办理人员信息-->
                                                    <td style="background-color:#AAAAAA;"
                                                        class="center">${var.EXCLUNAME}</td>
                                                        <%--<td style="background-color:#AAAAAA;" class="center">${var.BEIZHU}</td>--%>
                                                    <td style="background-color:#AAAAAA;" class="center"><c:if
                                                            test="${var.PAY_METHOD == 'alipay' }">
                                                        <span class="label label-important arrowed-in">支付宝支付</span>
                                                    </c:if> <c:if test="${var.PAY_METHOD == 'wx' }">
                                                        <span class="label label-success arrowed">微信支付</span>
                                                    </c:if> <c:if test="${var.PAY_METHOD == 'xx' }">
                                                        <span class="label label-success arrowed">线下支付</span>
                                                    </c:if></td>
                                                    <td style="background-color:#AAAAAA;" class="center">
                                                        <c:if test="${var.STATUS == '0' }"><span
                                                                class="label label-important arrowed-in">未付款</span></c:if>
                                                        <c:if test="${var.STATUS == '1' }"><span
                                                                class="label label-important arrowed-in">已付款</span></c:if>
                                                        <c:if test="${var.STATUS == '2' }"><span
                                                                class="label label-success arrowed">已发货</span></c:if>
                                                        <c:if test="${var.STATUS == '3' }"><span
                                                                class="label label-success arrowed">已完成</span></c:if>
                                                        <c:if test="${var.STATUS == '7' }"><span
                                                                class="label label-success arrowed">退款成功</span></c:if>
                                                        <c:if test="${var.STATUS == '8' }"><span
                                                                class="label label-success arrowed">退款失败</span></c:if>
                                                        <c:if test="${var.STATUS == '10' }"><span
                                                                class="label label-important arrowed-in">未付款</span></c:if>
                                                    </td>

                                                    <td class="center" style="cursor:hand;background-color:#AAAAAA;"
                                                        onclick="cedit('${var.ORDER_INFO_ID}',${var.CSTATUS})">
                                                        <c:if test="${var.CSTATUS == '0' }">
                                                            <span style="cursor:hand"
                                                                  class="label label-important arrowed-in">未处理</span>
                                                        </c:if> <c:if test="${var.CSTATUS == '1' }">
                                                        <span style="cursor:hand" class="label label-success arrowed">已处理</span>
                                                    </c:if> <%-- <c:if test="${var.STATUS == '4' }"><span class="label label-important arrowed-in">已取消</span></c:if> --%>
                                                    </td>
                                                    <td style="background-color:#AAAAAA;" class="center">
                                                        <c:if test="${pd.GUANLI=='admin'}">
                                                            <a class="btn btn-small btn-success"
                                                               onclick="tuikuan('${var.ORDER_INFO_ID}');">退款</a>
                                                        </c:if>
                                                    </td>

                                                    <td style="width: 30px;" class="center">
                                                        <div class='hidden-phone visible-desktop btn-group'>
                                                            <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                                <span
                                                                        class="label label-large label-grey arrowed-in-right arrowed-in"><i
                                                                        class="icon-lock" title="无权限"></i></span>
                                                            </c:if>
                                                            <div class="inline position-relative">
                                                                <button class="btn btn-mini btn-info"
                                                                        data-toggle="dropdown">
                                                                    <i class="icon-cog icon-only"></i>
                                                                </button>
                                                                <ul
                                                                        class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                                                    <c:if test="${QX.edit == 1 }">
                                                                        <li><a style="cursor: pointer;" title="编辑"
                                                                               onclick="edit('${var.ORDER_INFO_ID}');"
                                                                               class="tooltip-success"
                                                                               data-rel="tooltip" title=""
                                                                               data-placement="left"><span
                                                                                class="green"><i
                                                                                class="icon-edit"></i></span></a></li>
                                                                    </c:if>
                                                                    <c:if test="${QX.del == 1 }">
                                                                        <li><a style="cursor: pointer;" title="删除"
                                                                               onclick="del('${var.ORDER_INFO_ID}');"
                                                                               class="tooltip-error" data-rel="tooltip"
                                                                               title=""
                                                                               data-placement="left"><span
                                                                                class="red"><i
                                                                                class="icon-trash"></i></span> </a></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:if>


                                            <c:if test="${var.CSTATUS==1 }">
                                                <tr>
                                                    <td class='center' style="width: 30px;"><label><input
                                                            type='checkbox' name='ids'
                                                            value="${var.ORDER_INFO_ID}"/><span
                                                            class="lbl"></span></label></td>
                                                    <td class='center' style="width: 30px;">${vs.index+1}</td>
                                                    <!-- 订单号-->
                                                    <td class="center"><a
                                                            href="javascript:goods('${var.ORDER_INFO_ID}','${var.ORDER_NUMBER}')">${var.ORDER_NUMBER}</a>
                                                    </td>
                                                    <!-- 用户账号-->
                                                    <td class="center"><c:if
                                                            test="${not empty var.USNAME}">
                                                        <a href="javascript:findOrder('${var.ORDER_INFO_ID}','${var.USERNAME}')"> ${var.USNAME}</a>
                                                    </c:if> <c:if test="${empty var.USNAME}">
                                                        <span style="color: red;">用户已注销</span>
                                                    </c:if></td>
                                                    <td class="center"><c:if
                                                            test="${not empty var.NAME}">
                                                        ${var.NAME}
                                                    </c:if> <c:if test="${empty var.NAME}">
                                                        <span style="color: red;">用户已注销</span>
                                                    </c:if></td>
                                                    <!-- 订单创建时间-->
                                                    <td class="center">${var.ORDER_DATE}</td>

                                                        <%--<td class="center">${var.PHONE}</td>
                                                        <td class="center">${var.CITY} ${var.ADDRESS}</td>--%>
                                                        <%--<td class="center"><c:if
                                                                test="${var.PAY_METHOD == 'alipay' }">
                                                                <span class="label label-important arrowed-in">支付宝支付</span>
                                                            </c:if> <c:if test="${var.PAY_METHOD == 'wx' }">
                                                                <span class="label label-success arrowed">微信支付</span>
                                                            </c:if> <c:if test="${var.PAY_METHOD == 'xx' }">
                                                                <span class="label label-success arrowed">线下支付</span>
                                                            </c:if></td>--%>
                                                        <%--<td class="center">${var.NAME}</td>
                                                    <td class="center">${var.PAY_MONEY}</td>
                                                    <td class="center">${var.MONEY}</td>--%>
                                                    <!-- 支付时间-->
                                                    <td class="center">${var.PAY_DATE}</td>
                                                    <!-- 处理时间-->
                                                    <td class="center">${var.END_DATE}</td>
                                                    <!-- 客服姓名-->
                                                    <td class="center">${var.EXCLUNAME}</td>
                                                    <!-- 订单备注信息-->
                                                        <%--<td class="center">${var.BEIZHU}</td>--%>
                                                    <td class="center"><c:if
                                                            test="${var.PAY_METHOD == 'alipay' }">
                                                        <span class="label label-important arrowed-in">支付宝支付</span>
                                                    </c:if> <c:if test="${var.PAY_METHOD == 'wx' }">
                                                        <span class="label label-success arrowed">微信支付</span>
                                                    </c:if> <c:if test="${var.PAY_METHOD == 'xx' }">
                                                        <span class="label label-success arrowed">线下支付</span>
                                                    </c:if></td>
                                                    <td class="center">
                                                        <c:if test="${var.STATUS == '0' }"><span
                                                                class="label label-important arrowed-in">未付款</span></c:if>
                                                        <c:if test="${var.STATUS == '1' }"><span
                                                                class="label label-important arrowed-in">已付款</span></c:if>
                                                        <c:if test="${var.STATUS == '2' }"><span
                                                                class="label label-success arrowed">已发货</span></c:if>
                                                        <c:if test="${var.STATUS == '3' }"><span
                                                                class="label label-success arrowed">已完成</span></c:if>
                                                        <c:if test="${var.STATUS == '7' }"><span
                                                                class="label label-success arrowed">退款成功</span></c:if>
                                                        <c:if test="${var.STATUS == '8' }"><span
                                                                class="label label-success arrowed">退款失败</span></c:if>
                                                        <c:if test="${var.STATUS == '10' }"><span
                                                                class="label label-important arrowed-in">未付款</span></c:if>
                                                    </td>
                                                    <td class="center">
                                                        <c:if test="${pd.GUANLI=='admin'}">
                                                            <a class="btn btn-small btn-success"
                                                               onclick="tuikuan('${var.ORDER_INFO_ID}');">退款</a>
                                                        </c:if>
                                                    </td>
                                                    <td class="center" style="cursor:hand"
                                                        onclick="cedit('${var.ORDER_INFO_ID}',${var.CSTATUS})">
                                                        <c:if test="${var.CSTATUS == '0' }">
                                                            <span style="cursor:hand"
                                                                  class="label label-important arrowed-in">未处理</span>
                                                        </c:if> <c:if test="${var.CSTATUS == '1' }">
                                                        <span style="cursor:hand" class="label label-success arrowed">已处理</span>
                                                    </c:if> <%-- <c:if test="${var.STATUS == '4' }"><span class="label label-important arrowed-in">已取消</span></c:if> --%>
                                                    </td>
                                                    <td style="width: 30px;" class="center">
                                                        <div class='hidden-phone visible-desktop btn-group'>
                                                            <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                                <span
                                                                        class="label label-large label-grey arrowed-in-right arrowed-in"><i
                                                                        class="icon-lock" title="无权限"></i></span>
                                                            </c:if>
                                                            <div class="inline position-relative">
                                                                <button class="btn btn-mini btn-info"
                                                                        data-toggle="dropdown">
                                                                    <i class="icon-cog icon-only"></i>
                                                                </button>
                                                                <ul
                                                                        class="dropdown-menu dropdown-icon-only dropdown-light pull-right dropdown-caret dropdown-close">
                                                                    <c:if test="${QX.edit == 1 }">
                                                                        <li><a style="cursor: pointer;" title="编辑"
                                                                               onclick="edit('${var.ORDER_INFO_ID}');"
                                                                               class="tooltip-success"
                                                                               data-rel="tooltip" title=""
                                                                               data-placement="left"><span
                                                                                class="green"><i
                                                                                class="icon-edit"></i></span></a></li>
                                                                    </c:if>
                                                                    <c:if test="${QX.del == 1 }">
                                                                        <li><a style="cursor: pointer;" title="删除"
                                                                               onclick="del('${var.ORDER_INFO_ID}');"
                                                                               class="tooltip-error" data-rel="tooltip"
                                                                               title=""
                                                                               data-placement="left"><span
                                                                                class="red"><i
                                                                                class="icon-trash"></i></span> </a></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:if>
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
                                <td style="vertical-align: top;">
                                    <!-- <a class="btn btn-small btn-success" onclick="statistics();">统计进货</a> -->
                                    <c:if test="${QX.del == 1 }">
                                        <a class="btn btn-small btn-danger"
                                           onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除"><i
                                                class='icon-trash'></i></a>
                                    </c:if> <a class="btn btn-small btn-success" onclick="shuaxin();">刷新</a>
                                    <a class="btn btn-small btn-success" onclick="xiaoyi();">月效益对比图</a>
                                    <a class="btn btn-small btn-success" onclick="xiaoyi2();">周效益对比图</a>
                                    <%--<a class="btn btn-small btn-success" onclick="xiaoyi1();">天效益对比图</a>--%>
                                    <%--<a class="btn btn-small btn-success" onclick="xiaoyi3();">时效益对比图</a>--%>
                                    <a class="btn btn-small btn-success" onclick="xiaoyi4();">周天时综合查询</a>
                                </td>
                                <td style="vertical-align: top;">金额： <span
                                        style="color: red;"> <fmt:formatNumber
                                        value="${pd.sum}" pattern="##.##"
                                        minFractionDigits="2"></fmt:formatNumber></span>元
                                </td>
                                <td style="vertical-align: top;">
                                    <div class="pagination"
                                         style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div>
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
    function search() {
        top.jzts();
        $("#Form").submit();
    }

    function shuaxin() {
        //location=location;
        location.assign(location);
    }

    //查询订单商品详情
    function goods(ID, ORDER_NUMBER) {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "查询订单商品详情";
        diag.URL = '<%=basePath%>order_pro/show.do?ORDER_INFO_ID=' + ID + '&ORDER_NUMBER=' + ORDER_NUMBER;
        diag.Width = 1200;
        diag.Height = 800;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                if ('${page.currentPage}' == '0') {
                    top.jzts();
                    setTimeout("self.location=self.location", 100);
                } else {
                    nextPage("${page.currentPage}");
                }
            }
            diag.close();
        };
        diag.show();
    }

    //查询客户的订单详情
    function findOrder(ID, USERNAME) {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "查询订单商品详情";
        diag.URL = '<%=basePath%>order_info/findUserOrder?ORDER_INFO_ID=' + ID + '&USERNAME=' + USERNAME;
        diag.Width = 1200;
        diag.Height = 800;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                if ('${page.currentPage}' == '0') {
                    top.jzts();
                    setTimeout("self.location=self.location", 100);
                } else {
                    nextPage("${page.currentPage}");
                }
            }
            diag.close();
        };
        diag.show();
    }

    //查询订单商品详情
    function xiaoyi() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "订单效益对比";
        diag.URL = '<%=basePath%>order_info/xiaoyi.do';
        diag.Width = 1500;
        diag.Height = 1000;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                if ('${page.currentPage}' == '0') {
                    top.jzts();
                    setTimeout("self.location=self.location", 100);
                } else {
                    nextPage("${page.currentPage}");
                }
            }
            diag.close();
        };
        diag.show();
    }

    //查询订单商品详情
    function xiaoyi1() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "订单效益对比";
        diag.URL = '<%=basePath%>order_info/xiaoyi1.do';
        diag.Width = 1500;
        diag.Height = 1000;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                if ('${page.currentPage}' == '0') {
                    top.jzts();
                    setTimeout("self.location=self.location", 100);
                } else {
                    nextPage("${page.currentPage}");
                }
            }
            diag.close();
        };
        diag.show();
    }

    //查询订单商品详情
    function xiaoyi2() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "订单效益对比";
        diag.URL = '<%=basePath%>demo/demo2.do';
        diag.Width = 1700;
        diag.Height = 1000;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                if ('${page.currentPage}' == '0') {
                    top.jzts();
                    setTimeout("self.location=self.location", 100);
                } else {
                    nextPage("${page.currentPage}");
                }
            }
            diag.close();
        };
        diag.show();
    }

    //查询订单商品详情
    function xiaoyi3() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "订单效益对比";
        diag.URL = '<%=basePath%>order_info/xiaoyi3.do';
        diag.Width = 1500;
        diag.Height = 1000;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                if ('${page.currentPage}' == '0') {
                    top.jzts();
                    setTimeout("self.location=self.location", 100);
                } else {
                    nextPage("${page.currentPage}");
                }
            }
            diag.close();
        };
        diag.show();
    }

    function xiaoyi4() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "订单效益对比";
        diag.URL = '<%=basePath%>demo/demo.do';
        diag.Width = 1500;
        diag.Height = 1000;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                if ('${page.currentPage}' == '0') {
                    top.jzts();
                    setTimeout("self.location=self.location", 100);
                } else {
                    nextPage("${page.currentPage}");
                }
            }
            diag.close();
        };
        diag.show();
    }

    //删除
    function del(Id) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>order_info/delete.do?ORDER_INFO_ID=" + Id + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    nextPage("${page.currentPage}");
                });
            }
        });
    }

    <%--
            function jz(){
                 $("#CITY").val("");//清空
                    var url = "<%=basePath%>sys_city/findCity.do?PROVINCE="+$("#PROVINCE").val()+"&tm="+new Date().getTime();
                    $.get(url,function(varList1){
                        if (varList1 == "[]") {
                        $("#CITY").empty();
                        $("<option value=''>请选择分类1</option>").appendTo($("#CITY"));
                    } else {
                        $("#CITY").empty();
                        $("<option value=''>请选择分类2</option>").appendTo($("#CITY"));
                        $.each(eval(varList1), function (i, item) {
                        $("<option value='" + item.ID + "'>" + item.NAME + "</option>").appendTo($("#CITY"));
                        });
                    }
                    });
            } --%>

    //删除
    function cedit(Id, CSTATUS) {
        if (CSTATUS != 1) {
            bootbox.confirm("确定处理过了?", function (result) {
                if (result) {
                    top.jzts();
                    var url = "<%=basePath%>order_info/cedit.do?ORDER_INFO_ID=" + Id + "&tm=" + new Date().getTime();
                    $.get(url, function (data) {
                        nextPage("${page.currentPage}");
                    });
                }
            });
        }
    }

    //删除
    function tuikuan(Id) {
        bootbox.confirm("确定要退款吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>order_info/tuikuan.do?ORDER_INFO_ID=" + Id;
                $.get(url, function (data) {
                    nextPage("${page.currentPage}");
                });
            }
        });
    }

    //修改
    function edit(Id) {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "编辑";
        diag.URL = '<%=basePath%>order_info/goEdit.do?ORDER_INFO_ID=' + Id;
        diag.Width = 300;
        diag.Height = 190;
        diag.CancelEvent = function () { //关闭事件
            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                nextPage("${page.currentPage}");
            }
            diag.close();
        };
        diag.show();
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


    //批量操作
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
                            url: '<%=basePath%>order_info/deleteAll.do?tm=' + new Date().getTime(),
                            data: {DATA_IDS: str},
                            dataType: 'json',
                            //beforeSend: validateData,
                            cache: false,
                            success: function (data) {
                                $.each(data.list, function (i, list) {
                                    nextPage("${page.currentPage}");
                                });
                            }
                        });
                    }
                }
            }
        });
    }

    //导出excel
    function toExcel(method, sta) {
        window.location.href = '<%=basePath%>order_info/excel.do?PAY_METHOD=' + method + '&STATUS=' + sta;
    }
</script>

</body>
</html>

