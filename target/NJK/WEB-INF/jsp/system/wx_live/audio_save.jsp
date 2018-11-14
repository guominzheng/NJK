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
    <link rel="stylesheet" href="static/css/laydate.css"/>
    <link rel="stylesheet" href="static/css/need/laydate.css"/>

    <link rel="stylesheet" href="static/css/ace.min.css"/>
    <link rel="stylesheet" href="static/css/ace-responsive.min.css"/>
    <link rel="stylesheet" href="static/css/ace-skins.min.css"/>

    <link rel="stylesheet" href="static/css/datepicker.css"/><!-- 日期框 -->
    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>
    <script type="text/javascript" src="static/js/laydate.js"></script>
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
            //判断字符串如果是整数不能以0开头后面加正整数，如果是浮点数整数部分不能为两个0：如00.00
            var re = /^(([1-9][0-9]*\.[0-9][0-9]*)|([0]\.[0-9][0-9]*)|([1-9][0-9]*)|([0]{1}))$/;
            if ($("#REMARKS").val() == "") {
                $("#REMARKS").tips({
                    side: 3,
                    msg: '请输入备注',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#REMARKS").focus();
                return false;
            }
            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }

    </script>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        html {
            background-color: #E3E3E3;
            font-size: 14px;
            color: #000;
            font-family: '微软雅黑'
        }

        h2 {
            line-height: 30px;
            font-size: 20px;
        }

        a, a:hover {
            text-decoration: none;
        }

        pre {
            font-family: '微软雅黑'
        }

        .box {
            width: 970px;
            padding: 10px 20px;
            background-color: #fff;
            margin: 10px auto;
        }

        .box a {
            padding-right: 20px;
        }

        .demo1, .demo2, .demo3, .demo4, .demo5, .demo6 {
            margin: 25px 0;
        }

        h3 {
            margin: 10px 0;
        }

        .layinput {
            height: 22px;
            line-height: 22px;
            width: 150px;
            margin: 0;
        }
    </style>
    <script type="text/javascript">
        !function () {
            laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
            laydate({elem: '#demo'});//绑定元素
        }();

        //日期范围限制
        var start = {
            elem: '#start',
            format: 'YYYY-MM-DD',
            min: laydate.now(), //设定最小日期为当前日期
            max: '2099-06-16', //最大日期
            istime: true,
            istoday: false,
            choose: function (datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas //将结束日的初始值设定为开始日
            }
        };

        var end = {
            elem: '#end',
            format: 'YYYY-MM-DD',
            min: laydate.now(),
            max: '2099-06-16',
            istime: true,
            istoday: false,
            choose: function (datas) {
                start.max = datas; //结束日选好后，充值开始日的最大日期
            }
        };
        laydate(start);
        laydate(end);

        //自定义日期格式
        laydate({
            elem: '#test1',
            format: 'YYYY年MM月DD日',
            festival: true, //显示节日
            choose: function (datas) { //选择日期完毕的回调
                alert('得到：' + datas);
            }
        });

        //日期范围限定在昨天到明天
        laydate({
            elem: '#hello3',
            min: laydate.now(-1), //-1代表昨天，-2代表前天，以此类推
            max: laydate.now(+1) //+1代表明天，+2代表后天，以此类推
        });
    </script>
    <script type="text/javascript">
        $(function () {
            var imgs = $("#img");
            imgs.hide();
            $("#upload").click(function () {
                var formData = new FormData();
                formData.append("file", document.getElementById("file").files[0]);
                formData.append("id", new Date().getTime());
                $.ajax({
                    type: 'post',
                    url: "//hitman.net.cn:8080/NJK/app/shangchuan/nativeUpload",
                    data: formData,
                    cache: false,
                    enctype: 'multipart/form-data',
                    processData: false,
                    contentType: false
                }).success(function (data) {
                    var a = document.getElementById("path");
                    a.setAttribute("value", data);
                    var img = document.getElementById("img");
                    img.setAttribute("src", "https://hitman.net.cn/NJK/uploadFiles/uploadImgs/uploadok.png");
                    imgs.show();
                }).error(function () {
                    alert("上传失败");
                    img.setAttribute("src", "https://hitman.net.cn/NJK/uploadFiles/uploadImgs/uploaderr.png");
                    imgs.show();
                });
            });
        });
    </script>
</head>
<body>
<form action="wxController/${msg}" name="Form" id="Form" method="post">
    <c:if test="${not empty pd}">
        <input type="hidden" name="record_liveId" value="${pd.live_id}" id="record_liveId"/>
    </c:if>
    <div id="zhongxin">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">上传资料:</td>
                <td><input type="file" name="file" id="file" maxlength="32"
                           title="上传资料"
                /><input style="width: 50px; height: 30px" type="button" name="upload" value="上传" id="upload">
                    <img id="img" style="height: 20px;width: 20px"/>
                </td>
                <input type="hidden" id="path" name="record_message"/>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">上传类型:</td>
                <td>
                    <select name="audio_text">
                        <option value="1">音频</option>
                        <option value="2">图片</option>
                        <option value="4">视频</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">时长:</td>
                <td><input type="text" name="size" id="size" maxlength="32"
                           title="时长"/></td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="10">
                    <a class="btn btn-mini btn-primary" onclick="save();"> 保存 </a>
                    <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"> 取消 </a>
                </td>
            </tr>


        </table>
    </div>
    <div id="zhongxin2" class="center" style="display:none"><br/> <br/> <br/> <br/> <br/> <img
            src="static/images/jiazai.gif"/> <br/>
        <h4 class="lighter block green"> 提交中... </h4></div>

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
</body>
</html>