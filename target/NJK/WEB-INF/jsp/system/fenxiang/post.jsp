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
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
                                
                                <script type="text/javascript">
    wx.config({
        //debug: true, // //开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: 'wxa237bc4186ab09ff', // 必填，公众号的唯一标识
        timestamp: '${pd.timestamp}', // 必填，生成签名的时间戳
        nonceStr: '${pd.nonceStr}', // 必填，生成签名的随机串
        signature: '${pd.signature}',// 必填，签名，见附录1
        jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone'] // 必填，需要使用的JS接口列表
    });
    wx.ready(function(){
        wx.onMenuShareTimeline({//分享到朋友圈
            title: '${pd.SUBJECT}', // 分享标题
            link: '${pd.URL}', // 分享链接
            imgUrl: '${pd.PIMG}' // 分享图标
        });
        wx.onMenuShareAppMessage({//分享给朋友
            title: '${pd.SUBJECT}', // 分享标题
            desc: '${pd.JIANJIE}', // 分享描述
            link: '${pd.URL}', // 分享链接
            imgUrl: '${pd.PIMG}', // 分享图标
            type: '', // 分享类型,music、video或link，不填默认为link
            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        wx.onMenuShareQQ({//分享到QQ
            title: "${pd.SUBJECT}", // 分享标题
            desc: '${pd.JIANJIE}', // 分享描述
            link: '${pd.URL}', // 分享链接
            imgUrl: '${pd.PIMG}' // 分享图标
        });
        wx.onMenuShareWeibo({//分享到腾讯微博
            title: '${pd.SUBJECT}', // 分享标题
            desc: '${pd.JIANJIE}', // 分享描述
            link: '${pd.URL}', // 分享链接
            imgUrl: '${pd.PIMG}' // 分享图标
        });
        wx.onMenuShareQZone({//分享到QQ空间
            title: '${pd.SUBJECT}', // 分享标题
            desc: '${pd.JIANJIE}', // 分享描述
            link: '${pd.URL}', // 分享链接
            imgUrl: '${pd.PIMG}' // 分享图标
        });

        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    });

</script>


<style>
.height {
    width: 100%;
    height: 20px;
}
/*******上面css不用管***********/
.line {
    margin-left: 5%;
    width: 90%;
    height: 1px;
    border-bottom: 1px dashed #b5b5b5;
    position: relative;
    margin-top: 35px;
    margin-bottom: 30px;
}

.line p {
    position: absolute;
    display: block;
    top: -35px;
    left: 35%;
    background: #f7f7f7;
    text-align: center;
    width: 100px;
    font-size: 22px;
    color: #999999;
}
</style>
<title>${pd.SUBJECT }</title>
<meta name="keywords" content="农极客">
<meta name="description" content="${pd.JIANJIE}" id="description">   
</head>
<body>
<h3 id="subject"></h3>
<body style="width: 90%; margin: 0 auto; font-family: 微软雅黑;">
    <p>
    <span id="data">${pd.MESSAGE }</span>
        <a href="http://111.7.131.46/cache/softfile.3g.qq.com/myapp/rcps/d/90502/com.tencent.android.qqdownloader_90502_160421113736a.apk?

ich_args=72d36c3eb894dba0bc7a2b23c3a3c4f9_7066_0_0_11_e93a5c356b6742fbbe6ccd1687ce16f0fed21b7fd02107d30cdf18f433cf57fa_378be30144a1559b520c6a216a4223b8_1_0&

ich_ip="  style="text-decoration:none; color:#5e80a6; font-weight:bold;margin-left:10px; width:90%">农极客</a>
    </p>



    <p>

    <div id="message">
    </div>

    <p>
        <style>img{max-width:100%;}</style><span style="margin-top: -50px;"><a 

href="http://111.7.131.46/cache/softfile.3g.qq.com/myapp/rcps/d/90502/com.tencent.android.qqdownloader_90502_160421113736a.apk?

ich_args=72d36c3eb894dba0bc7a2b23c3a3c4f9_7066_0_0_11_e93a5c356b6742fbbe6ccd1687ce16f0fed21b7fd02107d30cdf18f433cf57fa_378be30144a1559b520c6a216a4223b8_1_0&

ich_ip="  style="text-decoration:none; color:#5e80a6; font-weight:bold;margin-left:10px; width:90%"><img
                src="http://localhost:8888/NJK/uploadFiles/uploadImgs/fenxiang.png"></a></span>
    </p>



        <!-------看这个div-------------->
        <div class="line">
            <p>评论</p>
        </div>
        <!-------看这个div-------------->
        <!-----------php----------------------->
<c:forEach items="${varList}" var="var" varStatus="vs">
    <div id="comm">
            <img src="${var.IMG }"
                style="width: 50px; margin: 60px auto auto 20px;">
            <div>
                <div style="margin-left: 90px; margin-top: -40px; width: 60%;">
                    <p id="NAMES">${var.NAME }</p><span id="CommDate">${var.DATE }</span>
                </div>
            </div>
            <p style="margin-left: 90px; width: 60%;" id="commmessage">${var.MESSAGE }</p>


        </div>


        <br />
</c:forEach>
</body>
</html>

