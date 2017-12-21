<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>会议系统-会议签到</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/meetingSign/css/pintuer.css">
    <script src="/meetingSign/js/jquery.js"></script>
    <script src="/meetingSign/js/pintuer.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</head>
<body>
<div class="keypoint bg-blue bg-inverse radius text-center">
    <h1>
         ${meeting.meetingName!""}</h1>
        发起者:${nickName!""}
    <p>
        会议内容:${meeting.meetingLocated}</p>
    <p>
        签名:<input type="text" id="username" class="input" placeholder="请输入姓名" value="${mynickName!""}"/>
        手机号:<input type="text" id="telphone" class="input" placeholder="请输入手机号" value="${telphone!""}"/>
        <div class="form-group">
        <div class="label">
            <label for="f_address_txt">
                位置精度:<span style="color: red" id="accuracy"></span>
            </label>
        </div>
        <div class="field">
            <input type="text" class="input" id="location" readonly="true" onclick="openTencentMp();">
        </div>
    </div>
        <br>
        <a id="signJoin"><button class="button bg-main">
            点击签到</button></a>
    </p>
</div>
<script>
    wx.config({
        debug: 0, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: '${wxJsVO.appId}', // 必填，公众号的唯一标识
        timestamp: ${wxJsVO.timestamp}, // 必填，生成签名的时间戳
        nonceStr: '${wxJsVO.nonceStr}', // 必填，生成签名的随机串
        signature: '${wxJsVO.signature}',// 必填，签名，见附录1
        jsApiList: ['getLocation','openLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });
    wx.error(function(res){
        alert("请打开位置信息，然后刷新页面或者点击下角的【发起会议 】");
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    });
    wx.checkJsApi({
        jsApiList: ['getLocation'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
        success: function(res) {
            console.log("接口是否可以调用："+res);
            // 以键值对的形式返回，可用的api值true，不可用为false
            // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
        }
    });
    wx.ready(function(){
        wx.getLocation({
            type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
            success: function (res) {
                var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                var speed = res.speed; // 速度，以米/每秒计
                var accuracy = res.accuracy; // 位置精度
                document.getElementById("accuracy").innerHTML=accuracy;
                document.getElementById("location").value=latitude+","+longitude;
            }
            });

    });
    function openTencentMp() {
        var location = document.getElementById("location").value.split(",");
        var latitude = location[0];
        var longitude = location[1];
        wx.openLocation({
            latitude: latitude, // 纬度，浮点数，范围为90 ~ -90
            longitude: longitude, // 经度，浮点数，范围为180 ~ -180。
            name: '我的位置', // 位置名
            address: '请注意地址有误差，此地址位置精度为:' + document.getElementById("accuracy").innerHTML, // 地址详情说明
            scale: 18, // 地图缩放级别,整形值,范围从1~28。默认为最大
            infoUrl: 'https://www.baidu.com' // 在查看位置界面底部显示的超链接,可点击跳转
        });
    }

    $(document).ready(function(){
        $("#signJoin").click(function() {
            var username =  document.getElementById("username").value;
            var telphone =  document.getElementById("telphone").value;
            var location = document.getElementById("location").value;
            var accuracy= document.getElementById("accuracy").innerHTML
                if(username.length>=2&&telphone.length>=4) {
                    if (location.length < 1) {
                        alert("请打开位置信息或者GPS然后刷新页面");
                    } else {
                            $.get("/meetingSign/sign/joinSuccess?openid=${openid}&meetingId=${meeting.meetingId}&username=" + username + "&status=1&telphone=" + telphone+"&location="+location+"&accuracy="+accuracy , function (data, status) {
                                alert(data);
                            });
                    }
                }
                else{
                    alert("信息不能为空或者信息不正确");
                }
            });
       });

</script>
</body>
</html>