var url=location.href;
var strs= new Array(); //定义一数组 
strs=url.split("?");
var strs1=new Array();
strs1=strs[1].split("=");
var strs12=new Array();
strs12=strs1[1].split("&");
console.log(strs1[1])
var baifei;
var biaoyu="";
var subject="农极客在线农药答题"
var data=url.split("data=");
console.log(data[1])
var data1=data[1].split("&");
console.log(data1[0])
var sear=new RegExp('status');
var str=location.href.split('#')[0]
var caishu="肥料"
$(function(){
		$.ajax({  
        url : "http://m.nongjike.cn/NJK/app/findWeiXinZuoGuoShiJuan",
        type: "POST",
        data:{ZUOGUO_SHIJUAN_ID:strs12[0],url:location.href.split('#')[0]},
        dataType: "json",
        ContentType:'application/json; charset=utf-8',
        success: function(data){
		console.log(data)
		if(sear.test(str)){
			var status=url.split("status=");
			var status1=status[1].split("&");
			console.log(status1[0])
			if(status1[0]=="0"){
				caishu="农业技术"
			}else if(status1[0]=="1"){
				caishu="肥料"
			}else if(status1[0]=="2"){
				caishu="农药"
			}else if(status1[0]=="3"){
				caishu="病虫害"
			}else if(status1[0]=="5"){
				caishu="农业管理"
			}
			console.log(caishu)
		}
			var clientWidth = document.documentElement.clientWidth
    var clientHeight = document.documentElement.clientHeight
	
	var title = document.getElementById("title")
	 var photoBackground = document.getElementById("photoBackground")
	 var photo = document.getElementById("photo")
     var photo_img = photo.getElementsByTagName("img")[0]
	 var erweima = document.getElementById("erweima")
	 var erweima_img = erweima.getElementsByTagName("img")[0]
	 var scoreNum = parseInt(data.data.FRACTION)
	 var maxWidth = 0
	 var maxleft = 0
     if (clientWidth >= 500) {
        maxWidth = 500
        maxleft = (clientWidth - 500) / 2
     }else {
        maxWidth = clientWidth
     }
	 var sub_title = document.getElementById("subTitle")
     sub_title.style.width = maxWidth - 22 * 2 + "px"
     sub_title.style.left = maxleft + 22 + "px"
     sub_title.style.top = 95 + "px"
	 var percent = Math.floor(Math.random() * 10)
    var percent2 = Math.floor(Math.random() * 10)
	 title.innerHTML="恭喜"+data.data.NAME+"在本次考试中获得<span>"+data.data.FRACTION+"</span>分"
	if(scoreNum==0){
		baifei=90 + percent + "." + percent2;
		subject=data.data.NAME+"刚才在农极客"+caishu+"考试里获得"+scoreNum+"分，有"+baifei+"%人的分数超过了我"
		biaoyu="据说，得零分才是真正的大牛！";
		photoBackground.src = "images1/10-0.jpg"
        sub_title.textContent = "打败了和你同时做题的"+data1[0]+"%人,据说，得零分才是真正的大牛！天呐，"+caishu+"你竟然可以得零分，牛得不行不行的了！我要拜师！"
	}else if(scoreNum==10){
		baifei=90 + percent + "." + percent2;
		subject=data.data.NAME+"刚才在农极客"+caishu+"考试里获得"+scoreNum+"分，有"+baifei+"%人的分数超过了我"
		biaoyu="不敢相信……蒙都不至于蒙这点分啊";
		photoBackground.src = "images1/10-0.jpg"
        sub_title.textContent = "打败了和你同时做题的"+data1[0]+"%人,肯定是故意得分这么少的，对不对？不敢相信……蒙都不至于蒙这点分啊——"
	}else if(scoreNum>10&&scoreNum<=50){
		 baifei=(scoreNum + percent2) + "." + percent;
		subject=data.data.NAME+"刚才在农极客"+caishu+"考试里获得"+scoreNum+"分，打败同时做题的"+baifei+"%人"
		biaoyu=caishu+"技术升级之路任重而道远，新一波刷题开始";
		photoBackground.src = "images1/20-50.jpg"
        sub_title.textContent = "打败了和你同时做题的"+data1[0]+"%人,赶紧的，"+caishu+"技术升级之路还任重而道远，抓紧时间再重新刷一遍题！！！"
	}else if(scoreNum>50&&scoreNum<80){
		 baifei=(scoreNum + percent2) + "." + percent;
		subject=data.data.NAME+"刚才在农极客"+caishu+"考试里获得"+scoreNum+"分，打败同时做题的"+baifei+"%人，战斗实力可怕"
		biaoyu="常见"+caishu+"知识有所了解，但还仍需努力哦~";
		photoBackground.src = "images1/60-70.jpg"
        sub_title.textContent = "打败了和你同时做题的"+data1[0]+"%人,及格了！对一些常见"+caishu+"知识有所了解，但还要更努力才能称得上是一个优秀农资人哦~"
	}else if(scoreNum>80&&scoreNum<100){
		baifei=(scoreNum + percent2) + "." + percent;
		subject=data.data.NAME+"刚才在农极客"+caishu+"考试里获得"+scoreNum+"分，打败同时做题的"+baifei+"%人，战斗实力可怕"
		biaoyu="技术杠杠的！不服来比~";
		photoBackground.src = "images1/80-90.jpg"
        sub_title.textContent = "打败了和你同时做题的"+data1[0]+"%人,赞赞赞，"+caishu+"技术杠杠的！一般人都比不过他了，不服来比~"
	}else{
		baifei=(scoreNum + percent2) + "." + percent;
		subject=data.data.NAME+"刚才在农极客"+caishu+"考试里获得100分，打败了同时做题的99.9%人，战斗实力可怕"
		biaoyu="跟着我，带你狂虐"+caishu+"小白";
		photoBackground.src = "images1/100.jpg"
        sub_title.textContent = "打败了和你同时做题的"+data1[0]+"%人,战斗实力可怕，什么"+caishu+"都难不倒他！专业、靠谱、牛气冲天——大神，等着大家来膜拜吧"
	}
	
	photoBackground.style.width = maxWidth * 0.385 + "px"
    photoBackground.style.left = maxleft + maxWidth * 0.307 + "px"
    photoBackground.style.height = maxWidth * 0.7 * 0.3 + "px"
	var sub_title_bottom = sub_title.offsetTop + sub_title.offsetHeight + clientHeight/13
    photoBackground.style.top = sub_title_bottom + "px"
	
	photo.style.left = (maxWidth - maxWidth * 0.454) / 2 + maxleft + "px"
    photo_img.style.width = maxWidth * 0.454  + "px"
    photo_img.style.height = maxWidth * 0.454 + "px"
	console.log(data.data.IMG)
	photo_img.src=data.data.IMG
	
	var photoBackground_bottom = photoBackground.offsetTop + photoBackground.offsetHeight
	var photo_img_bottom = photo_img.offsetTop + photo_img.offsetHeight
    var centerY = photoBackground_bottom-photo_img.height/5
	
    photo.style.top = centerY + "px"
	
	
	var centerYS= photo.offsetTop + photo.offsetHeight+clientHeight/13
	erweima.style.top = centerYS + "px"
	erweima_img.src="images1/chengji.jpg"
	
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
                      title: subject, // 分享标题
                      link:data.url, // 分享链接
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/duolihai.png" // 分享图标
                  });
                  wx.onMenuShareAppMessage({//分享给朋友
                      title:subject, // 分享标题
                      desc:biaoyu, // 分享描述
                      link:data.url, // 分享链接
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/duolihai.png" // 分享图标
                  });
                  wx.onMenuShareQQ({//分享到QQ
                      title: subject, // 分享标题
                      desc:biaoyu, // 分享描述
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/duolihai.png" // 分享图标
                  });
                  wx.onMenuShareWeibo({//分享到腾讯微博
                      title:subject, // 分享标题
                      desc:biaoyu, // 分享描述
                      link:data.url, // 分享链接
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/duolihai.png" // 分享图标
                  });
                  wx.onMenuShareQZone({//分享到QQ空间
                      title:subject, // 分享标题
                      desc:biaoyu, // 分享描述
                      link:data.url, // 分享链接
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/duolihai.png" // 分享图标
                  });

                  // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
              });
		}
		})
})