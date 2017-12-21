<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>任务定时器</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <link rel="stylesheet" href="/meetingSign/css/pintuer.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/weui/0.4.3/style/weui.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/jquery-weui/0.8.3/css/jquery-weui.min.css">
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

    <#--<link rel="stylesheet" type="text/css" href="/meetingSign/css/datedropper.css">-->
    <#--<link rel="stylesheet" type="text/css" href="/meetingSign/css/timedropper.min.css">-->
   <#--<script>-->
       <#--window.onload=function(){-->
           <#--$.alert("自定义的消息内容");-->
       <#--}-->

   <#--</script>-->
</head>

<body>
<div class="alert alert-blue" style="text-align: center;font-size: 13px">
        点击头像可查看已发布的定时任务
</div>
<div id="main" style="text-align: center">
    <a href="/meetingSign/index/myTask?openid=${openid}&imgHead=${imgHead}"><img src="${imgHead}" width="100" height="100" class="img-border radius-circle"style="margin: 0px;text-align: center"/></a>
    <form method="post" action="/meetingSign/index/successEvent" id="eventForm">
    <div class="demo" style="text-align: center">
        <input hidden type="text" value="${openid}" name="openid"/>
        <input hidden type="text" value="${imgHead}" name="imgHead"/>
        <input hidden type="text" value="${nickname}" name="nickname"/>

        <div class="form-group" id="f_1508773867876">
            <div class="label">
                <label for="f_currency_txt">
                   时间
                </label>
            </div>
            <div class="field">
                <#--<input type="text" class="input" id="f_currency_txt" name="f_currency_txt" maxlength="10" value="" data-validate="required:请填写月收入,currency:请输入月收入，1到99岁,length#<10:字数在0-10个" placeholder="输入月收入">-->
                <input class="input weui-input" type="datetime-local" name="eventDate" value="" id="choseTime" placeholder="" style="background-color: white">
            </div>
        </div>

        <div class="form-group" id="f_1506608081185">
            <div class="label">
                <label for="f_address_txt">
                    提醒事件描述
                </label>
            </div>
            <div class="field">
                <textarea type="text" class="input" id="eventContent" name="eventContent" maxlength="500" value="" data-validate="" placeholder="请输入事件信息"></textarea>
            </div>
            <div class="form-group">
                <div class="label">
                    <label for="f_tel_txt">
                        手机号
                    </label>
                </div>
                <div class="field">
                    <input type="text" class="input" id="phoneNumber" name="phoneNumber" maxlength="12" value="${phoneNumber!""}"  placeholder="输入手机号码">
                </div>
            </div>
        </div>
    </div>
    </form>
    <div style="text-align: center">
        <button class="button button-big border-sub" id="submitForm"><span class="icon-calendar"></span>  提交</button>
    </div>
</div>

<#--<script src="/meetingSign/js/datedropper.min.js"></script>-->
<#--<script src="/meetingSign/js/timedropper.min.js"></script>-->
<#--<script>-->
    <#--$("#pickdate").dateDropper({-->
        <#--animate: false,-->
        <#--format: 'Y-m-d',-->
        <#--maxYear: '2020'-->
    <#--});-->
    <#--$("#picktime").timeDropper({-->
        <#--meridians: false,-->
        <#--format: 'HH:mm',-->
    <#--});-->
<#--</script>-->
</div>
</body>
<!-- body 最后 -->
<script src="http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/jquery-weui/0.8.3/js/jquery-weui.min.js"></script>
<script>
    window.onload = function(){
            document.getElementById("submitForm").onclick = function () {
              var choseTime =  document.getElementById("choseTime").value;
              var eventContent = document.getElementById("eventContent").value;
              var phone = document.getElementById("phoneNumber").value;
              if(choseTime.length<=0){
                    $.alert("时间不能为空");
                    return;
                }
                if(eventContent.length<=0){
                    $.alert("事件描述不能为空");
                    return;
                }
                if(phone.length!=11){
                    $.alert("请填写正确的手机号");
                    return;
                }
                document.getElementById("eventForm").submit();
            }
    }
</script>
</html>
