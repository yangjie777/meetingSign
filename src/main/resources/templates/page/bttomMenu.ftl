<div id="bottomMenu" style="margin:10,auto;text-align:center;background-color: white;width: 100%" >
    <a href="/meetingSign/user/indexMeeting?openid=${openid}"><button class="button"><span class="icon-child"></span> 发起会议</button></a>
    <a href="/meetingSign/user/myMeeting?openid=${openid}"><button class="button"><span class="icon-user"></span> 我的会议</button></a>
    <a href="/meetingSign/userInfo/joinedMeeting?openid=${openid}"><button class="button"><span class="icon-group (alias)"></span> 已参加</button></a>
    <a href="/meetingSign/userInfo/lookUserInfo?openid=${openid}"><button class="button"><span class="icon-cog"></span> 个人信息</button></a>
</div>
<script>
    window.onload = function(){
        document.getElementById("displayFunction").style.height= (window.screen.availHeight-138)+"px" ;
    }

</script>