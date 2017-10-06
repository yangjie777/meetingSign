<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>会议系统-我的会议</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="/meetingSign/js/jquery.js"></script>
    <script src="/meetingSign/js/pintuer.js"></script>
    <link rel="stylesheet" href="/meetingSign/css/pintuer.css">
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
    <script>
        function changeMeetingStatus(number,meetingStatus){
            if(meetingStatus=="1"){
                document.getElementById("meetingStatus_"+number).innerHTML="<span class='text-main' "+">正签到中</span>";
            }
            else{
                document.getElementById("meetingStatus_"+number).innerHTML="<span class='text-dot' "+">停止签到</span>";
            }
        }

    </script>
</head>
<body>
<div id="displayFunction" style="overflow: auto;text-align: center" >
    <table class="table table-bordered">
        <tr>
            <th style="text-align: center;width: 90px" class="text-more">
              会议名称
            </th>
            <th style="text-align: center">
            会议简介
        </th>
          <th style="text-align: center">
                会议状态
            </th>
            <th colspan="2" style="text-align: center">
                操作
            </th>
        </tr>
    <#list meetingList as meeting>
        <tr>
            <td>
                <a href="/meetingSign/user/meetingJoined?openid=${meeting.openid}&meetingId=${meeting.meetingId}"  class="text-main">
               ${meeting.meetingName}
                </a>
            </td>
            <td>
                <button class="button dialogs" onclick="alertMeetingContent('${meeting.meetingName}','${meeting.meetingLocated}')" data-toggle="click" data-target="#mydialog" data-mask="1" data-width="80%">
                    点击查看</button>
            </td>
            <td id = "meetingStatus_${meeting.id}">
                <script>
                    changeMeetingStatus('${meeting.id}','${meeting.meetingStatus}');
                </script>

            </td>
            <td>
                <a href="/meetingSign/user/displayCode?openid=${meeting.openid}&meetingId=${meeting.meetingId}">二维码</a>
            </td>
            <td>
                <a href="/meetingSign/user/deteleMeeting?openid=${meeting.openid}&meetingId=${meeting.meetingId}">删除</a>
            </td>
        </tr>
    </#list>
    </table>
    <div id="mydialog">
        <div class="dialog">
            <div class="dialog-head">
                <span class="close rotate-hover"></span><strong id="alertTitle">对话框标题</strong>
            </div>
            <div class="dialog-body" id="alertContent">
                对话框内容</div>
        </div>
    </div>
    <script>
        function alertMeetingContent(title,content){
                document.getElementById("alertTitle").innerHTML = title;
                document.getElementById("alertContent").innerHTML = content;
                if(content==""){
                    document.getElementById("alertContent").innerHTML = "无";
                }
        }
    </script>
</div>
<#include "./bttomMenu.ftl">
</body>

</html>