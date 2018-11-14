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
<!-- jsp文件头和头部 -->
<%@ include file="../../system/admin/top.jsp"%>
</head>
<body>
            <tr>
                <td style="width:70px;text-align: right;padding-top: 13px;">商品备注:</td>
                <td><input type="text" name="SUBJECT" id="SUBJECT" value="${pd.SUBJECT}" maxlength="32" placeholder="这里输入商品备注" title="商品备注"/></td>
            </tr>
                                <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
                                
                                <script type="text/javascript">
    wx.config({
        //debug: true, // //开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: 'wxa237bc4186ab09ff', // 必填，公众号的唯一标识
        timestamp: '<?php echo $weixindata['timestamp'];?>', // 必填，生成签名的时间戳
        nonceStr: '<?php echo $weixindata['nonceStr'];?>', // 必填，生成签名的随机串
        signature: '<?php echo $weixindata['signature'];?>',// 必填，签名，见附录1
        jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone'] // 必填，需要使用的JS接口列表
    });
    wx.ready(function(){
        wx.onMenuShareTimeline({//分享到朋友圈
            title: '测试', // 分享标题
            link: 'http://m.nongjike.cn', // 分享链接
            imgUrl: 'http://m.nongjike.cn/bbs/h5/logo.png' // 分享图标
        });
        wx.onMenuShareAppMessage({//分享给朋友
            title: '测试', // 分享标题
            desc: '测试', // 分享描述
            link: 'http://m.nongjike.cn', // 分享链接
            imgUrl: 'http://m.nongjike.cn/bbs/h5/logo.png', // 分享图标
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
            title: "${SUBJECT}", // 分享标题
            desc: '<?php echo $weixindata['description'];?>', // 分享描述
            link: '<?php echo $weixindata['link'];?>', // 分享链接
            imgUrl: '<?php echo $weixindata['pic'];?>' // 分享图标
        });
        wx.onMenuShareWeibo({//分享到腾讯微博
            title: '<?php echo $weixindata['title'];?>', // 分享标题
            desc: '<?php echo $weixindata['description'];?>', // 分享描述
            link: '<?php echo $weixindata['link'];?>', // 分享链接
            imgUrl: '<?php echo $weixindata['pic'];?>' // 分享图标
        });
        wx.onMenuShareQZone({//分享到QQ空间
            title: '<?php echo $weixindata['title'];?>', // 分享标题
            desc: '<?php echo $weixindata['description'];?>', // 分享描述
            link: '<?php echo $weixindata['link'];?>', // 分享链接
            imgUrl: '<?php echo $weixindata['pic'];?>' // 分享图标
        });

        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    });

</script>
</body>
</html>

