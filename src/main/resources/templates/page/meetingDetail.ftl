<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>会议系统-会议详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/meetingSign/css/pintuer.css">
    <script src="/meetingSign/js/jquery.js"></script>
    <script src="/meetingSign/js/pintuer.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
</head>
<style>
    #bottomMenu {
        position: fixed;
        bottom: 1px;
        margin-left: 3px;

    }
    .button{
        padding: 5px;
        margin: 0px;
    }

</style>
<body>
<div id="displayFunction" style="overflow: auto;text-align: center" >
<div class="keypoint bg-blue bg-inverse radius text-center">
    <h2>名称:${meeting.meetingName}</h2>
    <p>
        简介:${meeting.meetingLocated!""}
    </p>
    已签到人数: ${meetingAndUsersList?size}人     <strong><a href="/meetingSign/user/displayCode?openid=${meeting.openid}&meetingId=${meeting.meetingId}"  class="text-mix text-big">二维码</a></strong>
    <br>

    <br>
            <#if ("${meeting.meetingStatus}")=="1">
               <a href="/meetingSign/user/changeMeetingStatus?openid=${openid}&meetingId=${meeting.meetingId}&meetingStatus=1">
                   <button class="button button-big bg-dot" >停止签到</button></a>
            <#else >
                <a href="/meetingSign/user/changeMeetingStatus?openid=${openid}&meetingId=${meeting.meetingId}&meetingStatus=2">
                   <button class="button button-big bg-main">开启签到</button></a>
            </#if>
</div>
<div class="table-responsive">
    <table class="table table-bordered">
        <tr>
            <th style="text-align: center">
              签名者
            </th>
            <th style="text-align: center">
            签名时间
        </th>
            <th style="text-align: center">
                位置经纬度
            </th>
        </tr>
    <#list meetingAndUsersList as meetingAndUsers>
        <tr>
            <td style="text-align: center">
              <button class="button tips" data-toggle="click" data-place="right" title="手机号:${meetingAndUsers.telphone}">
              ${meetingAndUsers.name}</button>
            </td>
            <td style="text-align: center">
             ${meetingAndUsers.createTime}
            </td>
            <td style="text-align: center">
             <button onclick="lookLocated('${meetingAndUsers.location}','${meetingAndUsers.accuracy}')">打开地图</button>
            </td>
        </tr>
    </#list>
    </table>
</div>
</div>
<#include "./bttomMenu.ftl">
</body>
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
        alert("加载位置信息错误");
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
    function lookLocated(locations,accuracy) {
        var location = locations.split(",");
        var latitude = location[0];
        var longitude = location[1];
        wx.openLocation({
            latitude: latitude, // 纬度，浮点数，范围为90 ~ -90
            longitude: longitude, // 经度，浮点数，范围为180 ~ -180。
            name: '红色为签到人员位置,绿点为本人位置', // 位置名
            address: '请注意地址有误差，此地址位置精度为:' + accuracy, // 地址详情说明
            scale: 18, // 地图缩放级别,整形值,范围从1~28。默认为最大
            infoUrl: 'https://www.baidu.com' // 在查看位置界面底部显示的超链接,可点击跳转
        });
    }
</script>
</html>