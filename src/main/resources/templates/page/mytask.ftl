<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>我的任务定时器</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" href="/meetingSign/css/pintuer.css">
    <script src="/meetingSign/js/jquery.js"></script>
    <script src="/meetingSign/js/pintuer.js"></script>
</head>

<body>
<div class="alert alert-blue" style="text-align: center">
    <span class="close rotate-hover"></span>点击头像可返回发布页
</div>
<div id="main" style="text-align: center">
    <a href="/meetingSign/index/Demo?openid=${openid}&imgHead=${imgHead}&nickname=${nickname}"><img src="${imgHead}" width="100" height="100" class="img-border radius-circle"style="margin: 0px;text-align: center"/></a>
</div>
<div>
    <br>
    <table class="table table-bordered" >
        <tr>
            <th>
            创建时间
            </th>
            <th>
            结束时间
            </th>
            <th>
                任务内容
            </th>
        </tr>
<#list eventList as event>
        <tr>
            <td>
                ${event.createTime}
           </td>
            <td>
            ${event.endTime}
            </td>
            <td>
                <button class="button tips" data-toggle="click" data-place="top" title="${event.eventContent}" style="width: 70px">
                  点击显示</button>
            </td>
        </tr>
</#list>
    </table>
</div>
</body>
</html>
