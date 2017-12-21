<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>会议系统-发布会议</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/meetingSign/css/pintuer.css">
    <script src="/meetingSign/js/jquery.js"></script>
    <script src="/meetingSign/js/pintuer.js"></script>
    <#--<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>-->
    <style>
    #bottomMenu {
        position: fixed;
        bottom: 1px;
    }
    .button{
        padding: 5px;
        margin: 0px;
    }
    </style>

</head>
<body>

<div id="displayFunction" style="overflow: auto;text-align: center" >
    <div class="alert alert-blue">
        <span class="close rotate-hover"></span><strong>提示：</strong>默认开启签到的哦
    </div>
    <form  action="/meetingSign/user/createMeeting" method="post" id="meetingform">
        <div class="form-group" id="f_1504851450924">
        <div class="label">
            <label for="f_chinesename_txt">
                会议名称
            </label>
        </div>
        <div class="field">
            <input type="text" class="input" maxlength="15" name="meetingName"  placeholder="请输入会议名称"  data-validate="required:请填写会议名称" id="meetName">
        </div>
    </div>

     <div class="form-group" id="f_1504851063507">
        <div class="label">
            <label for="f_address_txt">
                会议简介
            </label>
        </div>
        <div class="field">
            <textarea type="text" class="input" id="f_address_txt" name="meetingLocated" maxlength="500" value="" data-validate="" placeholder="请输入会议简介"></textarea>
        </div>
    </div>
        <#--<div class="form-group">-->
            <#--<div class="label">-->
                <#--<label for="f_address_txt">-->
                  <#--位置信息(误差:<span style="color: red" id="accuracy"></span>米)-->
                <#--</label>-->
            <#--</div>-->
            <#--<div class="field">-->
                <#--<input type="text" class="input" id="location" readonly="true">-->
            <#--</div>-->
        <#--</div>-->

        <div class="button-group checkbox">
            <label class="button active">
                <input name="meetingStatus" value="1" type="checkbox" checked><span class="icon icon-check"></span> 发布即开启签到
            </label>
        </div>
        <input hidden name="openid" value="${openid}">
        <input hidden id="username" value="${username!""}">
        <input hidden id="telphone" value="${telphone!""}">
        <br>  <br>
        <strong class="text-gray">Tips:如果发布者的姓名未设置，将默认由微信名作为发布者的姓名。如需更改请移步个人信息修改</strong>
    </form>
    <br>
    <button class="button button-big bg-main" style="width: 100px" onclick="submitMettingForm();" >
        发布</button>

    <script>
        <#--wx.config({-->
            <#--debug: 0, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。-->
            <#--appId: '${wxJsVO.appId}', // 必填，公众号的唯一标识-->
            <#--timestamp: ${wxJsVO.timestamp}, // 必填，生成签名的时间戳-->
            <#--nonceStr: '${wxJsVO.nonceStr}', // 必填，生成签名的随机串-->
            <#--signature: '${wxJsVO.signature}',// 必填，签名，见附录1-->
            <#--jsApiList: ['getLocation','openLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2-->
        <#--});-->
        <#--wx.error(function(res){-->
            <#--alert("请打开位置信息，然后刷新页面或者点击下角的【发起会议 】");-->
            <#--// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。-->
        <#--});-->
        <#--wx.checkJsApi({-->
            <#--jsApiList: ['getLocation'], // 需要检测的JS接口列表，所有JS接口列表见附录2,-->
            <#--success: function(res) {-->
                <#--console.log("接口是否可以调用："+res);-->
                <#--// 以键值对的形式返回，可用的api值true，不可用为false-->
                <#--// 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}-->
            <#--}-->
        <#--});-->
        <#--wx.ready(function(){-->
        <#--wx.getLocation({-->
            <#--type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'-->
            <#--success: function (res) {-->
                <#--var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90-->
                <#--var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。-->
                <#--var speed = res.speed; // 速度，以米/每秒计-->
                <#--var accuracy = res.accuracy; // 位置精度-->
                <#--document.getElementById("accuracy").innerHTML=accuracy;-->
                <#--document.getElementById("location").value=latitude+","+longitude;-->

                <#--wx.openLocation({-->
                    <#--latitude: latitude, // 纬度，浮点数，范围为90 ~ -90-->
                    <#--longitude: longitude, // 经度，浮点数，范围为180 ~ -180。-->
                    <#--name: '查看参议人员位置', // 位置名-->
                    <#--address: '请注意地址有误差，此地址位置精度为:'+accuracy, // 地址详情说明-->
                    <#--scale: 17, // 地图缩放级别,整形值,范围从1~28。默认为最大-->
                    <#--infoUrl: 'https://www.baidu.com' // 在查看位置界面底部显示的超链接,可点击跳转-->
                <#--});-->


            <#--}-->

        <#--});-->
        <#--});-->

        function  submitMettingForm() {
            var username =  document.getElementById("username").value;
            var telphone =  document.getElementById("telphone").value;
              var meetingform = document.getElementById("meetingform");
            if(username.length>=2&&telphone.length>=4) {
                var meetName =  document.getElementById("meetName").value;
                if(meetName.length>=1){
                    meetingform.submit();
                }else{
                    alert("请填写会议名称");
                }

            }else{
                alert("请您移步到【个人信息】填写信息");
              }
        }

    </script>

</div>
<#include "./bttomMenu.ftl">
</body>

</html>