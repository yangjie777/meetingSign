<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>会议系统-我的个人信息</title>
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
    <div class="alert alert-blue">
        <span class="close rotate-hover"></span><strong>提示：</strong>为了方便，个人信息将显示于签到界面
    </div>
    <form action="/meetingSign/userInfo/saveUserInfo" method="get">
        <div class="label">
            <label for="f_address_txt">
               姓名
            </label>
        </div>

        <input type="text" name="username" class="input" placeholder="请输入姓名" value="${(userInfo.username)!""}"/>
        <div class="label">
            <label for="f_address_txt">
                手机号
            </label>
        </div>
        <input type="text" name="telphone" class="input" placeholder="请输入手机号" value="${(userInfo.telphone)!""}"/>
        <input hidden type="text" value="${openid}" name="openid">
        <br><br>
        <button class="button button-big bg-main" style="width: 100px" type="submit">
            保存信息</button>
    </form>

    <br>
</div>
<#include "./bttomMenu.ftl">
</body>
</html>