<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

	<!-- jsp文件头和头部 -->
	<%@ include file="top.jsp"%>
	<style type="text/css">
	.commitopacity{position:absolute; width:100%; height:100px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
	</style>
	<style type="text/css">
		.modal-container {position: fixed;top: 0;left: 0;right: 0;bottom: 0;background: rgba(0, 0, 0, .7);visibility: hidden;opacity: 0;transition: all .5s ease-in-out;display: flex; /*align-items: center;*/justify-content: center;z-index: 9999;}  .modal-container.active {visibility: visible;opacity: 1;}
		.modal-box {width: 400px;position: absolute;overflow: hidden;top: 50%;left: 50%;margin: -200px 0 0 -200px;background: #FFFFFF;border-radius: 20px;transform-origin: center center; /*cursor: pointer;*/transform: scale(.5);opacity: 0;transition: all .5s ease-in-out;}
		.modal-container.active .modal-box {opacity: 1;transform: scale(1);}
		.modal-text{margin: 0 auto;margin-top: 30px;margin-bottom: 30px;max-width: 80%;line-height: 30px;font-size: 20px;text-align:center;}
		.modal-btns {width: 100%;cursor: pointer;background: #FFFFFF;text-align: center;border-top: 1px solid #1a1917;font-size: 20px;height: 50px;line-height: 50px;}
	</style>
	<!-- 即时通讯 -->
	<script type="text/javascript">var wimadress="${pd.WIMIP}:${pd.WIMPORT}";</script>
	<script type="text/javascript">var oladress="${pd.OLIP}:${pd.OLPORT}";</script>
	<link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/ext4/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="plugins/websocketInstantMsg/css/websocket.css" />
	<script type="text/javascript" src="plugins/websocketInstantMsg/ext4/ext-all-debug.js"></script>
	<script type="text/javascript" src="plugins/websocketInstantMsg/websocket.js"></script>
	<!-- 即时通讯 -->
	<script type="text/javascript">
        $(function () {
            var websocket = null;
            //判断当前浏览器是否支持WebSocket
            if ('WebSocket' in window) {
                websocket = new WebSocket("wss://m.nongjike.cn/NJK/websocket/socketServer.do?liveId=17");
            }
            //连接发生错误的回调方法
            websocket.onerror = function () {
            };

            //连接成功建立的回调方法
            websocket.onopen = function (event) {
            }

            //接收到消息的回调方法
            websocket.onmessage = function (event) {
                setMessageInnerHTML(event.data);
            }

            //连接关闭的回调方法
            websocket.onclose = function () {
            }
            //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
            window.onbeforeunload = function () {
                websocket.close();
            }

            //将消息显示在网页上
            function setMessageInnerHTML(innerHTML) {
                showModal(innerHTML, "确定");
            }

            //本地接受方法
            function commessage() {
                websocket.onmessage();
            }

			//关闭连接
            function closeWebSocket() {
                websocket.close();
            }

			//发送消息
            function send() {
                var messages = document.getElementById('text').value;
                websocket.send(message);
            }
          function showModal(text,btntext){
                console.log("提醒内容为："+text);
			  	var username = $("#username").val();
                $(".modal-text").html(text);
                $(".modal-btns").html(btntext);
                $(".modal-container").addClass("active");
            }

            $(".modal-btns").on("click",function(){
                $(".modal-container").removeClass("active");
            })
        });
	</script>
	<script type="text/javascript" src="static/js/defualtSocket.js"></script>
</head>
<body>
<div class="modal-container">
	<div class="modal-box">
		<div class="modal-content">
			<div class="modal-text">
			</div>
			<div class="modal-btns" >
			</div>
		</div>
	</div>
</div>
	<!-- 页面顶部¨ -->
	<%@ include file="head.jsp"%>
	<div id="websocket_button"></div>
	<div class="container-fluid" id="main-container">
		<a href="#" id="menu-toggler"><span></span></a>
		<!-- menu toggler -->

		<!-- 左侧菜单 -->
		<%@ include file="left.jsp"%>

		<div id="main-content" class="clearfix">
			<input type="hidden" id="username" name="username" value="${sessionUser.NAME}"/>
			<input type="hidden" id="phone" name="phone" value="${sessionUser.PHONE}"/>
			<div id="jzts" style="display:none; width:100%; position:fixed; z-index:99999999;">
			<div class="commitopacity" id="bkbgjz"></div>
			<div style="padding-left: 70%;padding-top: 1px;">
				<div style="float: left;margin-top: 3px;"><img src="static/images/loadingi.gif" /> </div>
				<div style="margin-top: 5px;"><h4 class="lighter block red">&nbsp;加载中 ...</h4></div>
			</div>
			</div>

			<div>
				<iframe name="mainFrame" id="mainFrame" frameborder="0" src="tab.do" style="margin:0 auto;width:100%;height:100%;"></iframe>
			</div>
            <DIV id="flt" name="flt" style="right:100px; POSITION:absolute; top:30px; z-index:99;">
            <a href="javascript:note()" title="" target="_blank"><img src="http://localhost:8888/NJK/uploadFiles/uploadImgs/JiShiBen.gif" ></a>
            </div>
			<!-- 换肤 -->
			<div id="ace-settings-container">
				<div class="btn btn-app btn-mini btn-warning" id="ace-settings-btn">
					<i class="icon-cog"></i> 
				</div>
				<div id="ace-settings-box">
					<div>
						<div class="pull-left">
							<select id="skin-colorpicker" class="hidden">
								<option data-class="default" value="#438EB9"
									<c:if test="${user.SKIN =='default' }">selected</c:if>>#438EB9</option>
								<option data-class="skin-1" value="#222A2D"
									<c:if test="${user.SKIN =='skin-1' }">selected</c:if>>#222A2D</option>
								<option data-class="skin-2" value="#C6487E"
									<c:if test="${user.SKIN =='skin-2' }">selected</c:if>>#C6487E</option>
								<option data-class="skin-3" value="#D0D0D0"
									<c:if test="${user.SKIN =='skin-3' }">selected</c:if>>#D0D0D0</option>
							</select>
						</div>
						<span>&nbsp; 选择皮肤</span>
					</div>
<!-- 					<div>
						<label><input type='checkbox' name='menusf' id="menusf"
							onclick="menusf();" /><span class="lbl" style="padding-top: 5px;">菜单缩放</span></label>
					</div> -->
				</div>
			</div>
			<!--/#ace-settings-container-->

		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	<!-- basic scripts -->
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
		
		<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="static/js/myjs/menusf.js"></script>
		
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/index.js"></script>
		<script>
        //查询笔记
        function note(ID,ORDER_NUMBER){
             top.jzts();
             var diag = new top.Dialog();
             diag.Drag=true;
             diag.Title ="查询记事本";
             diag.URL = '<%=basePath%>note/note.do';
             diag.Width = 1000;
             diag.Height = 1000;
             diag.CancelEvent = function(){ //关闭事件
                 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                	 //location.assign(location);
                	 document.execCommand('Refresh');
                }
                diag.close();
             };
             diag.show();
        }
</script>
</body>
</html>
