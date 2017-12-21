<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>会议系统-会议签到</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/meetingSign/css/pintuer.css">
    <script src="/meetingSign/js/jquery.js"></script>
    <script src="/meetingSign/js/pintuer.js"></script>
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
        <br>
        <a id="signJoin"><button class="button bg-main">
            点击签到</button></a>
    </p>
</div>
<script>
    $(document).ready(function(){
        $("#signJoin").click(function() {
            var username =  document.getElementById("username").value;
            var telphone =  document.getElementById("telphone").value;
                if(username.length>=2&&telphone.length>=4) {
                    if (confirm("Tips:签到成功将自动将信息更新到个人信息。")) {
                        $.get("/meetingSign/sign/joinSuccess?openid=${openid}&meetingId=${meeting.meetingId}&username=" + username + "&status=1&telphone="+telphone, function (data, status) {
                            alert(data);
                        });
                    }
                }else{
                    alert("信息不能为空或者信息不正确");
                }
            });
       });

</script>
</body>
</html>