<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>会议系统-发布会议</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/meetingSign/css/pintuer.css">
    <script src="/meetingSign/js/jquery.js"></script>
    <script src="/meetingSign/js/pintuer.js"></script>
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
    </div><div class="form-group" id="f_1504851192213">

    </div>

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