/**
 * Created by meitiannongzi on 2017/12/5.
 */
var screenWidth = document.body.clientWidth
var screenHeight = $(document).height()
var maxWidth = screenWidth > 500 ? 500 : screenWidth
var baseW = maxWidth / 375
var url=location.href;
    var strs= new Array(); //定义一数组 
    strs=url.split("?");
    var strs1=new Array();
    strs1=strs[1].split("=");
    var strs12=new Array();
    strs12=strs1[1].split("&");
	var zhengquelu;
	var zhou;
$(function () {

	var opts = {
			position:"fixed",
            lines: 15, // 花瓣数目
            length: 10, // 花瓣长度
            width: 3, // 花瓣宽度
            radius: 10, // 花瓣距中心半径
            scale: 1,
            corners: 1, // 花瓣圆滑度 (0-1)
            color: '#000000', // 花瓣颜色
            opacity: 0.25,
            rotate: 0, // 花瓣旋转角度
            direction: 1, // 花瓣旋转方向 1: 顺时针, -1: 逆时针
            speed: 1, // 花瓣旋转速度
            trail: 60, // 花瓣旋转时的拖影(百分比)
            zIndex: 2e9, // spinner的z轴 (默认是2000000000)
            className: 'spinner', // spinner css 样式名称
            top: '50%', // spinner 相对父容器Top定位 单位 px
            left: '50%', // spinner 相对父容器Left定位 单位 px
            shadow: false, // 花瓣是否显示阴影
            hwaccel: false, //spinner 是否启用硬件加速及高速旋转
            
        };
			$("#shadouTop").show();
			$("#shadowbootto").show();
			var spinner = new Spinner(opts);
			$(".spin").show();
			var target = $(".spin")[0];
			spinner.spin(target);
	
	$.ajax({
		url: 'http://m.nongjike.cn/NJK/app/WenXinFenXiang',
		type: 'post',
		data:{USER_ID:strs12[0],url:location.href.split('#')[0]},
		dataType: 'json',
		success: function(data) {
			console.log(data)
			$("#photo>img").attr("src",data.data.IMG)
			$("#name").text(data.data.NAME)
			$("#sign").text("连续做题"+data.data.COUNT+"天")
			if(data.ThisWeek.count1=="null"){
				$("#BenZhou").text("0")
				$("#BenZhouLv").text("总分0,0%的正确率")
				zhengquelu="0"
				zhou="0"
			}else{
				console.log(data.ZhouCorrect.count2)
				console.log(data.ZhouCorrect.count1)
				console.log(data.ThisWeek)
				$("#BenZhou").text(data.ThisWeek.rowno)
				$("#BenZhouLv").text("总分"+data.ThisWeek.count1+","+(100-(data.ZhouCorrect.count2/data.ZhouCorrect.count1*100)).toFixed(2)+"%正确率")
				zhou=data.ThisWeek.rowno
				zhengquelu=(100-(data.ZhouCorrect.count2/data.ZhouCorrect.count1*100)).toFixed(2);
			}
			if(data.ThisMonth.count1=="null"){
				$("#BenYue").text("0")
				$("#BenYueLv").text("总分0,0%的正确率")
			}else{
				$("#BenYue").text(data.ThisMonth.rowno)
				$("#BenYueLv").text("总分"+data.ThisMonth.count1+","+(100-(data.YueCorrect.count2/data.YueCorrect.count1*100)).toFixed(2)+"%正确率")
			}
			if(data.ThisYear.coun1=="null"){
				$("#BenNian").text("0")
				$("#BenNianLv").text("总分0,0%的正确率")
			}else{
				$("#BenNian").text(data.ThisYear.rowno)
				$("#BenNianLv").text("总分"+data.ThisYear.count1+","+(100-(data.NianCorrect.count2/data.NianCorrect.count1*100)).toFixed(2)+"%正确率")
			}
			if(data.LastWeek.count1=="null"){
				$("#shangZhou").text("0"+"名")
				$("#shangZhouFe").text(0+"分")
			}else{
				$("#shangZhou").text(data.LastWeek.rowno+"名")
				$("#shangZhouFe").text(data.LastWeek.count1+"分")
			}
			if(data.LastYear.count1=="null"){
				$("#shangYue").text("0名")
				$("#shangYueFen").text("0分")
			}else{
				$("#shangYue").text(data.LastYear.rowno+"名")
				$("#shangYueFen").text(data.LastYear.count1+"分")
			}
			

				$("#shadow").hide();
				$(".spin").hide();
				
			wx.config({
                //debug : true,
                appId: data.appId,  
                timestamp:data.timestamp,  
                nonceStr:data.nonceStr,  
                signature:data.signature,  
                jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone'] // 必填，需要使用的JS接口列表
            });
              wx.ready(function(){
                  wx.onMenuShareTimeline({//分享到朋友圈
                      title: data.data.NAME+"的农业题库战绩本周排名"+zhou+"，越战越勇！", // 分享标题
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/zhanji.png" // 分享图标
                  });
                  wx.onMenuShareAppMessage({//分享给朋友
                      title:data.data.NAME+"的农业题库战绩本周排名"+zhou+"，越战越勇！", // 分享标题
                      desc:"连续做题"+data.data.COUNT+"天，正确率达"+zhengquelu+"%！", // 分享描述
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/zhanji.png" // 分享图标
                  });
                  wx.onMenuShareQQ({//分享到QQ
                      title:data.data.NAME+"的农业题库战绩本周排名"+zhou+"，越战越勇！", // 分享标题
                      desc:"连续做题"+data.data.COUNT+"天，正确率达"+zhengquelu+"%！",
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/zhanji.png" // 分享图标
                  });
                  wx.onMenuShareWeibo({//分享到腾讯微博
                      title:data.data.NAME+"的农业题库战绩本周排名"+zhou+"，越战越勇！", // 分享标题
                      desc:"连续做题"+data.data.COUNT+"天，正确率达"+zhengquelu+"%！",
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/zhanji.png" // 分享图标
                  });
                  wx.onMenuShareQZone({//分享到QQ空间
                      title:data.data.NAME+"的农业题库战绩本周排名"+zhou+"，越战越勇！", // 分享标题
                      desc:"连续做题"+data.data.COUNT+"天，正确率达"+zhengquelu+"%！",
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/zhanji.png" // 分享图标
                  });

                  // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
              });
		}
	})

    
	$(".table_img>img").css("width", baseW * 70)
    $("#history").css("width", maxWidth * 0.9467).css("left", maxWidth * 0.02665)
    $("#history_bg").css("width", maxWidth)
    $("#history_bg>img").css("width", 0.304 * maxWidth)
	
	
})


function print(a) {
    console.log(a)
}