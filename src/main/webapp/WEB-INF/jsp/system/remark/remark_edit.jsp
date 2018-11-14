<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <title></title>
    <meta name="description" content="overview & stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link href="static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="static/css/bootstrap-responsive.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="static/css/font-awesome.min.css"/>
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/css/chosen.css"/>

    <link rel="stylesheet" href="static/css/ace.min.css"/>
    <link rel="stylesheet" href="static/css/ace-responsive.min.css"/>
    <link rel="stylesheet" href="static/css/ace-skins.min.css"/>

    <link rel="stylesheet" href="static/css/datepicker.css"/><!-- 日期框 -->
    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
    <!-- 上传图片样式包 -->
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/webuploader.css"/>
    <link rel="stylesheet" type="text/css" href="plugins/webuploader/style.css"/>
    <!-- Ueditor编辑器JS -->
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">


        //保存
        function save() {
            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }

        //删除图片
        function delP(IMG, REMARK_ID) {
            if (confirm("确定要删除图片？")) {
                var url = "remark/deltp.do?IMG=" + IMG + "&REMARK_ID=" + REMARK_ID + "&guid=" + new Date().getTime();
                $.get(url, function (data) {
                    document.location.reload();
                    if (data == "success") {
                        $("#uploadName").val("");
                        $("#uploader").show();
                        $("#delP").hide();
                    }
                });
            }
        }

        function delPP(TIMG, REMARK_ID) {
            if (confirm("确定要删除图片？")) {
                var url = "remark/deltpp.do?TIMG=" + TIMG + "&REMARK_ID=" + REMARK_ID + "&guid=" + new Date().getTime();
                $.get(url, function (data) {
                    document.location.reload();
                    if (data == "success") {
                        $("#uploadName").val("");
                        $("#uploader").show();
                        $("#delP").hide();
                    }
                });
            }
        }

        //删除图片
    </script>
</head>
<body>
<form action="remark/${msg }.do" name="Form" id="Form" method="post">
    <input type="hidden" name="PRODUCT_ID" id="PRODUCT_ID" value="${pd.PRODUCT_ID}"/>
    <input type="hidden" name="REMARK_ID" id="REMARK_ID" value="${pd.REMARK_ID}"/>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">规格:</td>
                <td><input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="32"
                           placeholder="这里输入商品规格" title="商品规格"/></td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">价格:</td>
                <td><input type="number" name="PRICE" id="PRICE" value="${pd.PRICE}" maxlength="32" placeholder="这里输入价格"
                           title="价格"/></td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">库存:</td>
                <td><input type="number" name="KUCUN" id="KUCUN" value="${pd.KUCUN}" maxlength="32" placeholder="这里输入库存"
                           title="库存"/></td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">最小购买量:</td>
                <td><input type="number" name="NUM" id="NUM" value="${pd.NUM}" maxlength="32" placeholder="这里输入最小购买量"
                           title="最小购买量"/></td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">最大购买量:</td>
                <td><input type="text" name="MAXNUM" id="MAXNUM" value="${pd.MAXNUM}" maxlength="32" placeholder="最大购买量"
                           title="购买"/></td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">设置为活动规格:</td>
                <td>
                    <select  name="PLANT_PRICE" id="PLANT_PRICE" >
                        <!-- <option value=""></option> -->
                        <option value="0" <c:if test="${pd.PLANT_PRICE=='0'}">selected</c:if>>否</option>
                        <option value="1" <c:if test="${pd.PLANT_PRICE=='1'}">selected</c:if>>是</option>
                    </select>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">排序:</td>
                <td><input type="text" name="ORDE_BY" id="ORDE_BY" value="${pd.ORDE_BY}" maxlength="32" placeholder="排序"
                           title="排序"/></td>
            </tr>

            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">说明:</td>
                <td><input type="text" name="EXPLAIN1" id="EXPLAIN1" value="${pd.EXPLAIN1}" maxlength="32"
                           placeholder="请输入说明" title="请输入说明"/></td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">备注:</td>
                <td><input type="text" name="SHUMING" id="SHUMING" value="${pd.SHUMING}" maxlength="32"
                           placeholder="请输入说明" title="请输入说明"/></td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">图片:<input type="hidden" id="uploadName"
                                                                                      name="IMG" value="${pd.IMG}"/>
                </td>
                <td>
                    <c:if test="${(empty pd.IMG) || (pd.IMG eq '')}">
                        <div id="uploader" style="width: 150px;height: 200px;">
                            <!--用来存放item-->
                            <div id="fileList" class="uploader-list"></div>
                            <div id="filePicker">选择图片</div>
                        </div>
                    </c:if>
                    <c:if test="${(not empty pd.IMG) && (pd.IMG ne '')}">
                        <div id="delP">
                            <input type="button" class="btn btn-mini btn-danger" value="删除图片"
                                   onclick="delP('${pd.IMG}','${pd.REMARK_ID }');"/><br>
                            <a href="${pd.IMG}" target="_blank"><img src="${pd.IMG}" width="100"/></a>
                        </div>
                    </c:if>
                </td>
            </tr>

            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">分享图:<input type="hidden" id="uploadName2"
                                                                                       name="TIMG" value="${pd.TIMG}"/>
                </td>
                <td>
                    <c:if test="${(empty pd.TIMG) || (pd.TIMG eq '')}">
                        <div id="uploader2" style="width: 150px;height: 200px;">
                            <!--用来存放item-->
                            <div id="fileList2" class="uploader-list"></div>
                            <div id="filePicker2">选择图片</div>
                        </div>
                    </c:if>
                    <c:if test="${(not empty pd.TIMG) && (pd.TIMG ne '')}">
                        <div id="delPP">
                            <input type="button" class="btn btn-mini btn-danger" value="删除图片"
                                   onclick="delPP('${pd.TIMG}','${pd.REMARK_ID }');"/><br>
                            <a href="${pd.TIMG}" target="_blank"><img src="${pd.TIMG}" width="100"/></a>
                        </div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                    <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
        </table>
    </div>

    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img
            src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中...</h4></div>

</form>


<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript">
    $(top.hangge());
    $(function () {

        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect: true});

        //日期框
        $('.date-picker').datepicker();

    });
    //实例化Ueditor编辑器
    var ue = UE.getEditor('editor');
</script>
<!-- 图片上传 -->
<script type="text/javascript" src="plugins/webuploader/webuploader.js"></script>
<script type="text/javascript">
    // 图片上传

    jQuery(function () {
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
        uploader.on('fileQueued', function (file) {
            $("#filePicker").hide();//按纽隐藏
            var $li = $(
                '<div id="' + file.id + '" class="file-item thumbnail">' +
                '<img>' +
                /* '<div class="info">' + file.name + '</div>' + *///不要文件名称
                '</div>'
                ),
                $img = $li.find('img');

            $list.append($li);

            // 创建缩略图
            uploader.makeThumb(file, function (error, src) {
                if (error) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }

                $img.attr('src', src);
            }, thumbnailWidth, thumbnailHeight);
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<p class="progress"><span></span></p>')
                    .appendTo($li)
                    .find('span');
            }

            $percent.css('width', percentage * 100 + '%');
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function (file) {
            $('#' + file.id).addClass('upload-state-done');
        });

        // 文件上传失败，现实上传出错。
        uploader.on('uploadError', function (file) {
            var $li = $('#' + file.id),
                $error = $li.find('div.error');

            // 避免重复创建
            if (!$error.length) {
                $error = $('<div class="error"></div>').appendTo($li);
            }

            $error.text('上传失败');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').remove();
        });
        //response是个对象，如果服务器返回是json格式，那么正和你意，都已经解析好了，如果不是json格式，response._raw里面可以拿到原始数据。
        uploader.on('uploadAccept', function (file, response) {
            $("#uploadName").val(response.path + response.id1 + "/" + response.id2);
            if (hasError) {
                // 通过return false来告诉组件，此文件上传有错。
                return false;
            }
        });
    });


    jQuery(function () {
        var $ = jQuery,
            $list = $('#fileList2'),
            // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,

            // 缩略图大小
            thumbnailWidth = 100 * ratio,
            thumbnailHeight = 100 * ratio,

            // Web Uploader实例
            uploader2;

        // 初始化Web Uploader
        uploader2 = WebUploader.create({

            // 自动上传。
            auto: true,

            // swf文件路径
            swf: 'plugins/webuploader/Uploader.swf',

            // 文件接收服务端。
            server: 'pictures/unloadPicture',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePicker2',

            // 只允许选择文件，可选。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });

        // 当有文件添加进来的时候
        uploader2.on('fileQueued', function (file) {
            $("#filePicker2").hide();//按纽隐藏
            var $li = $(
                '<div id="' + file.id + '" class="file-item thumbnail">' +
                '<img>' +
                /* '<div class="info">' + file.name + '</div>' + *///不要文件名称
                '</div>'
                ),
                $img = $li.find('img');

            $list.append($li);

            // 创建缩略图
            uploader2.makeThumb(file, function (error, src) {
                if (error) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }

                $img.attr('src', src);
            }, thumbnailWidth, thumbnailHeight);
        });

        // 文件上传过程中创建进度条实时显示。
        uploader2.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<p class="progress"><span></span></p>')
                    .appendTo($li)
                    .find('span');
            }

            $percent.css('width', percentage * 100 + '%');
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader2.on('uploadSuccess', function (file) {
            $('#' + file.id).addClass('upload-state-done');
        });

        // 文件上传失败，现实上传出错。
        uploader2.on('uploadError', function (file) {
            var $li = $('#' + file.id),
                $error = $li.find('div.error');

            // 避免重复创建
            if (!$error.length) {
                $error = $('<div class="error"></div>').appendTo($li);
            }

            $error.text('上传失败');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader2.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').remove();
        });
        //response是个对象，如果服务器返回是json格式，那么正和你意，都已经解析好了，如果不是json格式，response._raw里面可以拿到原始数据。
        uploader2.on('uploadAccept', function (file, response) {
            $("#uploadName2").val(response.path + response.id1 + "/" + response.id2);
            if (hasError) {
                // 通过return false来告诉组件，此文件上传有错。
                return false;
            }
        });

    });
</script>
</body>
</html>