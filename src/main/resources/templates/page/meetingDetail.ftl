<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>会议系统-会议详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/meetingSign/css/pintuer.css">
    <script src="/meetingSign/js/jquery.js"></script>
    <script src="/meetingSign/js/pintuer.js"></script>

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
        </tr>
    </#list>
    </table>
</div>
</div>
<#include "./bttomMenu.ftl">
</body>
</html>