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
    <table class="table table-bordered" id="tableDiv">
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
            ${event.eventContent}
            </td>
        </tr>
    </#list>
    </table>



<#if "${isHasPage}"=="1">
    <button class="button bg" id="reloadManyContent" style="width: 100%">
        点击加载更多</button>
    <input value="${size}" hidden id="size">
    <input value="${page}" hidden id="page">
</#if>
</div>
</body>
<script>
    $(document).ready(function(){
        $("#reloadManyContent").click(function() {
            document.getElementById("reloadManyContent").innerHTML="...请稍后，正在加载中...";
            document.getElementById("reloadManyContent").id="none";   //防止重复点击
            var size =  document.getElementById("size").value;
            var page =  document.getElementById("page").value;
            $.get("/meetingSign/index/reoloadManyContent?openid=${openid}&size="+size+"&page="+(page-0+1), function (data, status) {
                var eventList = data.eventList;
                var allcontent = "";
                for(var i = 0; i<eventList.length;i++){
                    allcontent = allcontent+"<tr><td>"+formatDate(new Date(eventList[i].createTime))+"</td><td>"+formatDate(new Date(eventList[i].endTime))+"</td><td>"+eventList[i].eventContent+"</td></tr>"
                }
                document.getElementById("size").value = data.size;
                document.getElementById("page").value = data.page;
                var content =  document.getElementById("tableDiv").innerHTML;
                document.getElementById("tableDiv").innerHTML = content+allcontent;
                if(data.isHasPage=="1"){
                    document.getElementById("none").id="reloadManyContent";
                    document.getElementById("reloadManyContent").innerHTML="点击加载更多";

                }
                else{
                    document.getElementById("none").innerHTML="已到底";

                }
            });
        });
        function getLocalTime(nS) {
            return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
        }
        function formatDate(now) {
            var year=now.getFullYear();
            var month=now.getMonth()+1;
            var date=now.getDate();
            var hour=now.getHours();
            var minute=now.getMinutes();
            var second=now.getSeconds();
            return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
        }
    });

</script>
</html>
