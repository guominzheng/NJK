/**
 * Created by meitiannongzi on 2017/8/8.
 */
function requsetFirst() {
    var requset = new XMLHttpRequest()
    requset.open("POST", "http://www.meitiannongzi.com/NJK/app/WeiXin7", true)
    requset.setRequestHeader("Content-type","application/x-www-form-urlencoded");

    var params = "url=" + "" + "&TIWEN_ID=" + "118bd7bce86e4c5d8850d14e4910a1d8"
    requset.send(params)
    requset.onreadystatechange = function() {
        if (requset.readyState === 4) {
            if (requset.status == 200) {
                var result = requset.responseText
                var jsonOBJ = JSON.parse(result)

                if(jsonOBJ.code != "1") return false
                var tiwen = jsonOBJ.tiwen
                document.getElementById("location").innerHTML = tiwen.ADDRESS
                document.getElementById("name").innerHTML = tiwen.NAME
                document.getElementById("message").innerHTML = tiwen.MESSAGE
                var img = document.getElementById("headPhoto")
                img.setAttribute("src",tiwen.IMG)

                var timgs = tiwen.TIMG
                for(var i=0; i<timgs.length; i++) {
                    var img = timgs[i]
                    createSwiperImage(img.IMG)
                }

                var comm = data.comm
                document.getElementById("commentNumber").innerHTML = "评论: (" + comm.length + ")"
                for(var i=0; i<comm.length; i++) {
                    var object = comm[i]
                    var name = object.NAME
                    var message = object.MESSAGE
                    var img = object.IMG
                    var date = object.YUE
                    createComment(img,date,name,message)
                }
            }
        }
    }
}


function createSwiperImage(src) {
// <div class="swiper-slide"><img alt="农极客" src="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4279488454,2375510965&fm=200&gp=0.jpg" /></div>
//         <div class="swiper-slide"><img alt="农极客" src="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4279488454,2375510965&fm=200&gp=0.jpg" /></div>
//         <div class="swiper-slide"><img alt="农极客" src="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=4279488454,2375510965&fm=200&gp=0.jpg" /></div>
    var swiper = document.getElementById("swiper-wrapper")

    var imgdiv = document.createElement("div")
    imgdiv.setAttribute("class","swiper-slide")

    var img = document.createElement("img")
    img.setAttribute("src",src)
    img.setAttribute("class","cycleImage")
    // img.setAttribute("width", "100%")
    // img.setAttribute("height", "220px")
    imgdiv.appendChild(img)

    swiper.appendChild(imgdiv)

}


function createComment(img, date, name, message) {
    var comment = document.createElement("div")
    // var parent = document.getElementById("background")
    // parent.appendChild(comment)
    comment.setAttribute("class","comment")

    var imgdiv = document.createElement("div")
    comment.appendChild(imgdiv)
    var image = document.createElement("img")
    image.setAttribute("src",img)
    imgdiv.appendChild(image)

    var subComent = document.createElement("div")
    subComent.setAttribute("class", "subComment")
    comment.appendChild(subComent)

    var pName = document.createElement("p")
    pName.setAttribute("class","commentUser")
    var nameText = document.createTextNode(name)
    pName.appendChild(nameText)
    subComent.appendChild(pName)

    var pMessage = document.createElement("p")
    pMessage.setAttribute("class","commentMessage")
    var messageText = document.createTextNode(message)
    pMessage.appendChild(messageText)
    subComent.appendChild(pMessage)

    var dateLabel = document.createElement("p")
    dateLabel.setAttribute("class", "date")
    var dateString = document.createTextNode(date)
    dateLabel.appendChild(dateString)
    subComent.appendChild(dateLabel)

    var commentNumer = document.getElementById("comment1")
    commentNumer.appendChild(comment)
}


