<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>任务定时器</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" href="/meetingSign/css/pintuer.css">
    <script src="/meetingSign/js/jquery.js"></script>
    <script src="/meetingSign/js/pintuer.js"></script>
    <link rel="stylesheet" type="text/css" href="/meetingSign/css/datedropper.css">
    <link rel="stylesheet" type="text/css" href="/meetingSign/css/timedropper.min.css">
    <style type="text/css">
        .demo{margin:80px auto 40px auto;width:320px;margin-top: 20px}
        .input{padding:6px;border:1px solid #d3d3d3}
    </style>

</head>

<body>
<div class="alert alert-blue" style="text-align: center">
    <span class="close rotate-hover"></span>点击头像可查看已发布的定时任务
</div>
<div id="main" style="text-align: center">
    <a href="/meetingSign/index/myTask?openid=${openid}&imgHead=${imgHead}"><img src="${imgHead}" width="100" height="100" class="img-border radius-circle"style="margin: 0px;text-align: center"/></a>
    <form method="post" action="/meetingSign/index/successEvent">
    <div class="demo" style="text-align: center">
        <input hidden type="text" value="${openid}" name="openid"/>
        <input hidden type="text" value="${imgHead}" name="imgHead"/>
        <input hidden type="text" value="${nickname}" name="nickname"/>
        <p><strong>请选择日期：</strong><input type="text" class="input" id="pickdate" style="background-color: white" name="eventDate" value="${nowTime!""}"/></p>
        <p><strong>请选择时间：</strong><input type="text" class="input" id="picktime"  style="background-color: white" name="eventTime"/></p>
        <div class="form-group" id="f_1506608081185">
            <div class="label">
                <label for="f_address_txt">
                    提醒事件描述
                </label>
            </div>
            <div class="field">
                <textarea type="text" class="input" id="f_address_txt" name="eventContent" maxlength="500" value="" data-validate="" placeholder="请输入事件信息"></textarea>
            </div>
            <div class="form-group" id="f_1507103567924">
                <div class="label">
                    <label for="f_tel_txt">
                        手机号
                    </label>
                </div>
                <div class="field">
                    <input type="text" class="input" id="f_tel_txt" name="phoneNumber" maxlength="12" value="${phoneNumber!""}" data-validate="required:请填写手机号,tel:请输入手机号" placeholder="输入手机号码">
                </div>
            </div>
            <br>
            <div style="text-align: center">
                <button class="button button-big border-sub"><span class="icon-calendar"></span>  提交</button>
            </div>
        </div>
    </div>
    </form>
</div>
<script type="text/javascript" src="/meetingSign/js/jquery-1.12.3.min.js"></script>
<script src="/meetingSign/js/datedropper.min.js"></script>
<script src="/meetingSign/js/timedropper.min.js"></script>
<script>
    $("#pickdate").dateDropper({
        animate: false,
        format: 'Y-m-d',
        maxYear: '2020'
    });
    $("#picktime").timeDropper({
        meridians: false,
        format: 'HH:mm',
    });
</script>
</div>
</body>
</html>
