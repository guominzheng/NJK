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
var data=url.split("STATUS=");
var data1="";
console.log(data[1])
data1=data[1].split("&");

$(function(){
		$.ajax({  
        url : "http://m.nongjike.cn/NJK/app/WeiXinfindZPaiHangBang",
        type: "POST",
        data:{USER_ID:strs12[0],url:location.href.split('#')[0],STATUS:data1[0]},
        dataType: "json",
        ContentType:'application/json; charset=utf-8',
        success: function(data){
				console.log(data.data)
				//var ziji = JSON.parse('{"code":"1","data":[{"count1":8,"FRACTION":"100","IMG":"http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg","WENDA_COUNT_ID":"0d7587f8eab14dc6894b4d1146b9c905","USER_ID":"14","NAME":"豆豆豆"},{"count1":1,"FRACTION":"100","IMG":"http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg","WENDA_COUNT_ID":"2678d0c092b143cb809231a13d3aff9e","USER_ID":"15","NAME":"13838282012"},{"count1":1,"FRACTION":"100","IMG":"http://obmmqs3o7.bkt.clouddn.com/dftheader.jpg","WENDA_COUNT_ID":"1da17fa799b24625bda8d4f26fdd5055","USER_ID":"120","NAME":"你的世界有我陪伴"},{"count1":1,"FRACTION":"100","IMG":"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/touxiang/20170620/088f13449e344053895cfdf4372d15ec.jpg","WENDA_COUNT_ID":"2f8daf472e6240c3a4c8e75f7ccb7e70","USER_ID":"32","NAME":"每天农资瑶瑶"},{"count1":1,"FRACTION":"100","IMG":"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/touxiang/20170621/9c26b4d6d4ea4e60b59a37bcf807f0fb.jpg","WENDA_COUNT_ID":"75cc5413a16a4eaf87448d5482c4017a","USER_ID":"13","NAME":"大小孩子啊？？"}],"paiming":{"count1":1,"rowno":5.0,"IMG":"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/touxiang/20170621/9c26b4d6d4ea4e60b59a37bcf807f0fb.jpg","WENDA_COUNT_ID":"75cc5413a16a4eaf87448d5482c4017a","USER_ID":"13","NAME":"大小孩子啊？？"},"message":"正确返回数据!"}')
				var clientWidth = document.documentElement.clientWidth
				var clientHeight = document.documentElement.clientHeight
				var background = document.getElementById("background")
				var photoBackground = document.getElementById("photoBackground")
				var Own=document.getElementById("Own")
				var Ranking=document.getElementById("Ranking")
				var touxiang=document.getElementById("touxiang")
				var touxiang_img=touxiang.getElementsByTagName("img")[0]
				var cishu=document.getElementById("cishu")
				var maxWidth = 0
				var maxleft = 0
				if (clientWidth >= 500) {
					maxWidth = 500
					maxleft = (clientWidth - 500) / 2
				}else {
					maxWidth = clientWidth
				}
				Own.style.height=clientHeight*0.133+"px"
				Own.style.marginTop=clientHeight*0.015+"px";
				if(data.paiming!=null){
					Ranking.style.marginLeft=maxWidth*0.11+"px"
				Ranking.style.lineHeight=clientHeight*0.133+"px"
				Ranking.innerHTML=data.paiming.count1+"<span>分</span>"
				touxiang_img.style.width=maxWidth*0.2+"px"
				touxiang_img.style.marginLeft=maxWidth*0.16+"px"
				touxiang_img.src=data.paiming.IMG
				cishu.style.lineHeight=clientHeight*0.133+"px"
				cishu.style.marginLeft=maxWidth*0.16+"px"
				cishu.innerHTML=data.paiming.rowno+"<span>名</span>"
				}
				
				for(var i=0;i<data.data.length;i++){
					var imgs="images1/"+parseInt(i+1)+".png";
					if(parseInt(i%2)!=0){
						var backLine = document.createElement("div")
						backLine.className="liebiao2";
						backLine.style.height=clientHeight*0.12+"px"
						var paiming = document.createElement("div")
						paiming.className="paiming"
						paiming.style.width=maxWidth*0.16+"px"
						paiming.style.height=clientHeight*0.12+"px"
					
						var paiming_img=document.createElement("img")
						paiming_img.className="paiming_img"
						paiming_img.style.marginTop=(clientHeight*0.12)*0.218+"px"
						paiming_img.style.width=maxWidth*0.08+"px"
						paiming_img.src=imgs
						paiming.appendChild(paiming_img);
			
		
						var xiaotouxiang= document.createElement("div")
						xiaotouxiang.style.height=clientHeight*0.12+"px"
						xiaotouxiang.className="xiaotouxiang"
					
						var xiaotouxiang_img=document.createElement("img")
						xiaotouxiang_img.className="xiaotouxiang_img"
						xiaotouxiang_img.src=data.data[i].IMG
						xiaotouxiang_img.style.width=maxWidth*0.16+"px"
						xiaotouxiang_img.style.marginTop=(clientHeight*0.12)*0.125+"px"
						xiaotouxiang.appendChild(xiaotouxiang_img);
			
						var name= document.createElement("div");
						name.className="name";
						name.style.height=clientHeight*0.12+"px"
						name.style.lineHeight=clientHeight*0.12+"px"
						name.style.marginLeft=maxWidth*0.021+"px"
						if(data.data[i].NAME.length>7){
							data.data[i].NAME=data.data[i].NAME.substring(0,7)+"..."
						}else{
							data.data[i].NAME=data.data[i].NAME
						}
						name.innerHTML=data.data[i].NAME
			
						var cishus= document.createElement("div")
						cishus.className="cishus"
						cishus.innerHTML=data.data[i].count1+"分"
						cishus.style.height=clientHeight*0.12+"px"
						cishus.style.lineHeight=clientHeight*0.12+"px"
						cishus.style.marginRight=maxWidth*0.042+"px"
					
						backLine.appendChild(paiming)
						backLine.appendChild(xiaotouxiang)
						backLine.appendChild(name)
						backLine.appendChild(cishus)
					
					
					
						background.appendChild(backLine)
					}else{
						var backLine = document.createElement("div")
						backLine.className="liebiao";
						backLine.style.height=clientHeight*0.12+"px"
						var paiming = document.createElement("div")
						paiming.className="paiming"
						paiming.style.width=maxWidth*0.16+"px"
						paiming.style.height=clientHeight*0.12+"px"
					
						var paiming_img=document.createElement("img")
						paiming_img.className="paiming_img"
						paiming_img.style.marginTop=(clientHeight*0.12)*0.218+"px"
						paiming_img.style.width=maxWidth*0.08+"px"
						paiming_img.src=imgs
						paiming.appendChild(paiming_img);
			
		
						var xiaotouxiang= document.createElement("div")
						xiaotouxiang.style.height=clientHeight*0.12+"px"
						xiaotouxiang.className="xiaotouxiang"
					
						var xiaotouxiang_img=document.createElement("img")
						xiaotouxiang_img.className="xiaotouxiang_img"
						xiaotouxiang_img.src=data.data[i].IMG
						xiaotouxiang_img.style.width=maxWidth*0.16+"px"
						xiaotouxiang_img.style.marginTop=(clientHeight*0.12)*0.125+"px"
						xiaotouxiang.appendChild(xiaotouxiang_img);
			
						var name= document.createElement("div");
						name.className="name";
						name.style.height=clientHeight*0.12+"px"
						name.style.lineHeight=clientHeight*0.12+"px"
						name.style.marginLeft=maxWidth*0.021+"px"
						if(data.data[i].NAME.length>7){
							data.data[i].NAME=data.data[i].NAME.substring(0,7)+"..."
						}else{
							data.data[i].NAME=data.data[i].NAME
						}
						name.innerHTML=data.data[i].NAME
			
						var cishus= document.createElement("div")
						cishus.className="cishus"
						cishus.innerHTML=data.data[i].count1+"分"
						cishus.style.height=clientHeight*0.12+"px"
						cishus.style.lineHeight=clientHeight*0.12+"px"
						cishus.style.marginRight=maxWidth*0.042+"px"
					
						backLine.appendChild(paiming)
						backLine.appendChild(xiaotouxiang)
						backLine.appendChild(name)
						backLine.appendChild(cishus)
					
					
					
						background.appendChild(backLine)
					}
			
				}
				
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
                      title: "农极客APP：农业题库成绩排行PK榜，测一测你的肥料技能排第几？", // 分享标题
                      link:data.url, // 分享链接
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/niurenbang.png" // 分享图标
                  });
                  wx.onMenuShareAppMessage({//分享给朋友
                      title:"农极客APP：农业题库成绩排行PK榜，测一测你的肥料技能排第几？", // 分享标题
                      desc:"敢来和大家比比吗？", // 分享描述
                      link:data.url, // 分享链接
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/niurenbang.png" // 分享图标
                  });
                  wx.onMenuShareQQ({//分享到QQ
                      title: "农极客APP：农业题库成绩排行PK榜，测一测你的肥料技能排第几？", // 分享标题
                      desc:"敢来和大家比比吗？", // 分享描述
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/niurenbang.png" // 分享图标
                  });
                  wx.onMenuShareWeibo({//分享到腾讯微博
                      title:"农极客APP：农业题库成绩排行PK榜，测一测你的肥料技能排第几？", // 分享标题
                      desc:"敢来和大家比比吗？", // 分享描述
                      link:data.url, // 分享链接
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/niurenbang.png" // 分享图标
                  });
                  wx.onMenuShareQZone({//分享到QQ空间
                      title:"农极客APP：农业题库成绩排行PK榜，测一测你的肥料技能排第几？", // 分享标题
                      desc:"敢来和大家比比吗？", // 分享描述
                      link:data.url, // 分享链接
                      imgUrl:"http://m.nongjike.cn/NJK/uploadFiles/uploadImgs/niurenbang.png" // 分享图标
                  });

                  // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
              });
		
        }
    }); 
})

