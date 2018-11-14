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
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		
		<!-- 上传图片插件 -->
		<link href="plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="plugins/uploadify/swfobject.js"></script>
		<script type="text/javascript" src="plugins/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
		<!-- 上传图片插件 -->
		<script type="text/javascript">
		var jsessionid = "<%=session.getId()%>";  //勿删，uploadify兼容火狐用到
		</script>
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="static/js/myjs/sys.js"></script>	
		
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!-- 上传图片样式包 -->
		<link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css" />
		<link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css" />
		
	</head>
<body>

<div id="zhongxin">
 <div class="span6">
	<div class="tabbable">
           <!--  <ul class="nav nav-tabs" id="myTab">
              <li class="active"><a data-toggle="tab" href="#home"><i class="green icon-home bigger-110"></i> 配置 NO1</a></li>
              <li><a data-toggle="tab" href="#profile"><i class="green icon-cog bigger-110"></i>配置 NO2</a></li>
              <li><a data-toggle="tab" href="#profile3"><i class="green icon-cog bigger-110"></i>配置 NO3</a></li>
            </ul> -->
            <div class="tab-content">
			  <div id="home" class="tab-pane in active">
				<form action="head/saveSys.do" name="Form" id="Form" method="post">
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="width:70px;text-align: right;padding-top: 13px;">系统名称:</td>
						<td><input type="text" name="YSYNAME" id="YSYNAME" value="${pd.YSYNAME }" placeholder="这里输入系统名称" style="width:90%" title="系统名称"/></td>
					
						<td style="width:70px;text-align: right;padding-top: 13px;">每页条数:</td>
						<td><input type="number" name="COUNTPAGE" id="COUNTPAGE" value="${pd.COUNTPAGE }" placeholder="这里输入每页条数" style="width:90%" title="每页条数"/></td>
					</tr>
				</table>
				
<%-- 				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="text-align: center;" colspan="100">
							抽奖设置
						</td>
					</tr>
					<tr>
						<td style="width:70px;text-align: right;padding-top: 13px;">抽奖所需的积分:</td>
						<td><input type="text" name="JIFEN" id="JIFEN" value="${pd.JIFEN }" placeholder="这里输入" style="width:15%" title="抽奖所需的积分"/>
						&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*请填写整数</span></td>
					</tr>	
					<tr>
						<td style="width:70px;text-align: right;padding-top: 13px;">中大奖所需的积分:</td>
						<td><input type="text" name="PRIZE" id="PRIZE" value="${pd.PRIZE }" placeholder="这里输入" style="width:15%" title="抽奖所需的积分"/>
						&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*请填写整数</span></td>
					</tr>	
					<tr>	
						<td style="width:70px;text-align: right;padding-top: 13px;">抽奖图片:
					<input type="hidden" id="DRAW_IMG" name="DRAW_IMG" value="${pd.DRAW_IMG}"/></td>
					<td>
						<c:if test="${(empty pd.DRAW_IMG) || (pd.DRAW_IMG eq '')}">
							<div id="uploader" style="width: 150px;height: 200px;">
							    <!--用来存放item-->
							    <div id="fileList" class="uploader-list"></div>
							    <div id="filePicker">选择抽奖图片</div>
							</div>
						</c:if>
						<c:if test="${(not empty pd.DRAW_IMG) && (pd.DRAW_IMG ne '')}">
							<div id="delP">
								<input type="button"  class="btn btn-mini btn-danger"  value="删除图片" onclick="delP('${pd.DRAW_IMG}');" /><br>
								<a href="${pd.DRAW_IMG}" target="_blank"><img src="${pd.DRAW_IMG}" width="200"/></a>
							</div>
						</c:if>
					</td>
					</tr>
				</table> --%>
				
		
				<table class="center" style="width:100%" >
					<tr>
						<td style="text-align: center;" colspan="100">
							<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
							<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						</td>
					</tr>
				</table>
				</form>
			  </div>
			  <div id="profile" class="tab-pane">
			  	<form action="head/saveSys2.do" name="Form2" id="Form2" method="post">
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="text-align: center;" colspan="100">
							文字水印配置
							<label style="float:left;padding-left: 15px;"><input name="fcheckbox" class="ace-checkbox-2" type="checkbox" id="check1" onclick="openThis1();" /><span class="lbl">开启</span></label>
						</td>
					</tr>
					<tr>
						<td style="width:50px;text-align: right;padding-top: 12px;">内容:</td>
						<td><input type="text" name="fcontent" id="fcontent" value="${pd.fcontent }"  style="width:90%" title="水印文字内容"/></td>
						<td style="width:50px;text-align: right;padding-top: 12px;">字号:</td>
						<td><input type="number" name="fontSize" id="fontSize" value="${pd.fontSize }"  style="width:90%" title="字号"/></td>
					</tr>
					<tr>
						<td style="width:50px;text-align: right;padding-top: 12px;">X坐标:</td>
						<td><input type="number" name="fontX" id="fontX" value="${pd.fontX }"  style="width:90%" title="X坐标"/></td>
						<td style="width:50px;text-align: right;padding-top: 12px;">Y坐标:</td>
						<td><input type="number" name="fontY" id="fontY" value="${pd.fontY }"  style="width:90%" title="Y坐标"/></td>
					</tr>
				</table>
				
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="text-align: center;" colspan="100">
							图片水印配置
							<label style="float:left;padding-left: 15px;"><input name="fcheckbox" class="ace-checkbox-2" type="checkbox" id="check2" onclick="openThis2();" /><span class="lbl">开启</span></label>
						</td>
					</tr>
					<tr>
						<td style="width:50px;text-align: right;padding-top: 12px;">X坐标:</td>
						<td><input type="number" name="imgX" id="imgX" value="${pd.imgX }" style="width:90%" title="X坐标"/></td>
						<td style="width:50px;text-align: right;padding-top: 12px;">Y坐标:</td>
						<td><input type="number" name="imgY" id="imgY" value="${pd.imgY }"  style="width:90%" title="Y坐标"/></td>
					</tr>
					<tr>
						<td style="width:50px;text-align: right;padding-top: 12px;">水印:</td>
						<td colspan="10">
						<div style="float:left;"><img src="<%=basePath%>uploadFiles/uploadImgs/${pd.imgUrl}"  width="100"/></div>
						<div style="float:right;"><input type="file" name="TP_URL" id="uploadify1" keepDefaultStyle = "true"/></div>
						</td>
					</tr>
				</table>
				
				<table class="center" style="width:100%" >
					<tr>
						<td style="text-align: center;" colspan="100">
							<a class="btn btn-mini btn-primary" onclick="save2();">保存</a>
							<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						</td>
					</tr>
				</table>
				<input type="hidden" name="isCheck1" id="isCheck1" value="${pd.isCheck1 }"/>
				<input type="hidden" name="isCheck2" id="isCheck2" value="${pd.isCheck2 }"/>
				<input type="hidden" name="imgUrl" id="imgUrl" value="${pd.imgUrl }"/>
				<input type="hidden" value="no" id="hasTp1" />
				</form>
			  </div>
			  
			  
			  <div id="profile3" class="tab-pane">
			  	<form action="head/saveSys3.do" name="Form3" id="Form3" method="post">
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="text-align: center;" colspan="100">
							微信接口配置
						</td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 12px;">URL(服务器地址):</td>
						<td><input type="text" name="WXURL" id="WXURL" value="<%=basePath%>weixin/index " disabled="disabled"  style="width:90%" title="URL(服务器地址)必须是域名，ip地址验证通不过"/></td>
					</tr>
					<tr>
						<td style="width:120px;text-align: right;padding-top: 12px;">Token(令牌):</td>
						<td><input type="text" name="Token" id="Token" value="${pd.Token }"  style="width:90%" title="URL(服务器地址)"/></td>
					</tr>
				</table>
				
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="text-align: center;" colspan="100">
							即时聊天服务器配置
						</td>
					</tr>
					<tr>
						<td style="width:50px;text-align: right;padding-top: 13px;">地址:</td>
						<td><input type="text" name="WIMIP" id="WIMIP" value="${pd.WIMIP }" placeholder="请输入服务器地址" style="width:90%" title="服务器地址"/></td>
					
						<td style="width:50px;text-align: right;padding-top: 13px;">端口:</td>
						<td><input type="number" name="WIMPORT" id="WIMPORT" value="${pd.WIMPORT }" placeholder="端口" style="width:90%" title="端口"/></td>
					</tr>
				</table>
				
				<table id="table_report" class="table table-striped table-bordered table-hover">
					<tr>
						<td style="text-align: center;" colspan="100">
							在线管理服务器配置
						</td>
					</tr>
					<tr>
						<td style="width:50px;text-align: right;padding-top: 13px;">地址:</td>
						<td><input type="text" name="OLIP" id="OLIP" value="${pd.OLIP }" placeholder="请输入服务器地址" style="width:90%" title="服务器地址"/></td>
					
						<td style="width:50px;text-align: right;padding-top: 13px;">端口:</td>
						<td><input type="number" name="OLPORT" id="OLPORT" value="${pd.OLPORT }" placeholder="端口" style="width:90%" title="端口"/></td>
					</tr>
				</table>
				
				<table class="center" style="width:100%" >
					<tr>
						<td style="text-align: center;" colspan="100">
							<a class="btn btn-mini btn-primary" onclick="save3();">保存</a>
							<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						</td>
					</tr>
				</table>
				</form>
			  </div>
			  
			  
            </div>
	</div>
 </div><!--/span-->



</div>
		
<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			if("${pd.isCheck1 }" == "yes"){
				$("#check1").attr("checked",true);
			}else{
				$("#check1").attr("checked",false);
			}
			if("${pd.isCheck2 }" == "yes"){
				$("#check2").attr("checked",true);
			}else{
				$("#check2").attr("checked",false);
			}
		});
		//删除展示图
		function delP(DRAW_IMG){
			if(confirm("确定要删除展示图？")){
				var url = "<%=basePath%>head/deltp.do?DRAW_IMG="+DRAW_IMG+"&guid="+new Date().getTime();
				$.get(url,function(data){
						document.location.reload();
					if(data=="success"){
						$("#DRAW_IMG").val("");
						$("#uploader").show();
						$("#delP").hide();
					}
				}); 
			}
		}
		</script>
			<!-- 图片上传 -->
    	<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
		<script type="text/javascript" >
    	// 图片上传
    	jQuery(function() {
    	    var $ = jQuery,
    	        $list = $('#fileList'),
    	        // 优化retina, 在retina下这个值是2
    	        ratio = window.devicePixelRatio || 1,

    	        // 缩略图大小
    	        thumbnailWidth = 100 * ratio,
    	        thumbnailHeight = 100 * ratio,

    	        // Web Uploader实例
    	        uploader;

    	    // 初始化Web Uploader
    	    uploader = WebUploader.create({

    	        // 自动上传。
    	        auto: true,

    	        // swf文件路径
    	        swf: 'plugins/webuploader/Uploader.swf',

    	        // 文件接收服务端。
    	        server: 'pictures/unloadPicture',

    	        // 选择文件的按钮。可选。
    	        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    	        pick: '#filePicker',

    	        // 只允许选择文件，可选。
    	        accept: {
    	            title: 'Images',
    	            extensions: 'gif,jpg,jpeg,bmp,png',
    	            mimeTypes: 'image/*'
    	        }
    	    });

    	    // 当有文件添加进来的时候
    	    uploader.on( 'fileQueued', function( file ) {
    	    	$("#filePicker").hide();//按纽隐藏
    	        var $li = $(
    	                '<div id="' + file.id + '" class="file-item thumbnail">' +
    	                    '<img>' +
    	                    /* '<div class="info">' + file.name + '</div>' + *///不要文件名称
    	                '</div>'
    	                ),
    	            $img = $li.find('img');

    	        $list.append( $li );

    	        // 创建缩略图
    	        uploader.makeThumb( file, function( error, src ) {
    	            if ( error ) {
    	                $img.replaceWith('<span>不能预览</span>');
    	                return;
    	            }

    	            $img.attr( 'src', src );
    	        }, thumbnailWidth, thumbnailHeight );
    	    });

    	    // 文件上传过程中创建进度条实时显示。
    	    uploader.on( 'uploadProgress', function( file, percentage ) {
    	        var $li = $( '#'+file.id ),
    	            $percent = $li.find('.progress span');

    	        // 避免重复创建
    	        if ( !$percent.length ) {
    	            $percent = $('<p class="progress"><span></span></p>')
    	                    .appendTo( $li )
    	                    .find('span');
    	        }

    	        $percent.css( 'width', percentage * 100 + '%' );
    	    });

    	    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    	    uploader.on( 'uploadSuccess', function( file ) {
    	        $( '#'+file.id ).addClass('upload-state-done');
    	    });

    	    // 文件上传失败，现实上传出错。
    	    uploader.on( 'uploadError', function( file ) {
    	        var $li = $( '#'+file.id ),
    	            $error = $li.find('div.error');

    	        // 避免重复创建
    	        if ( !$error.length ) {
    	            $error = $('<div class="error"></div>').appendTo( $li );
    	        }

    	        $error.text('上传失败');
    	    });

    	    // 完成上传完了，成功或者失败，先删除进度条。
    	    uploader.on( 'uploadComplete', function( file ) {
    	        $( '#'+file.id ).find('.progress').remove();
    	    });
    	    //response是个对象，如果服务器返回是json格式，那么正和你意，都已经解析好了，如果不是json格式，response._raw里面可以拿到原始数据。
    	    uploader.on( 'uploadAccept', function( file, response ) {
    	    	$("#DRAW_IMG").val(response.path+response.id1+"/"+response.id2);
    	        if ( hasError ) {
    	            // 通过return false来告诉组件，此文件上传有错。
    	            return false;
    	        }
    	    });
    	});
    	</script>
</body>
</html>