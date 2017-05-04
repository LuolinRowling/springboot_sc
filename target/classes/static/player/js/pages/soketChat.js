/*websocket chat*/
;void(function(window,document,undefined){
    if(!(typeof websocketChat != "undefined" && websocketChat)){
        var websocketChat = window.websocketChat = {};


        websocketChat.globalParam = {
            //websoket长连接
 

            "course_live_url":'rtmp://media.mytorchwood.com/live/test',         //进入该课程 老师正在直播的课程视频 rtmp://media.mytorchwood.com/live/sskt

            "streamName":'sskt',
            "stu_publish_url":'media.mytorchwood.com/publish',                  //stu端推流 media.mytorchwood.com/publish
            "stu_live_url":'rtmp://media.mytorchwood.com/live/sskt',            //拉stu端推的流
            "appName":''

        };

        websocketChat.btnObj = {
            "sendMsgBtn":$("#sendMsgBtn"),
            "closeRiseHandBtn":$("#closeRiseHandBtn")
        };

        websocketChat.init = function($config){
            this.globalParam.userName = ssktUtil.getCokie("userName");
            this.globalParam.userId = ssktUtil.getCokie("userId");
            this.globalParam.userRole = ssktUtil.getCokie("userRole");
            this.globalParam.courseId = ssktUtil.getUrlParam('courseId');

            //测试暂时写死
            /*this.globalParam.userName = "范冰冰";
            this.globalParam.userId = 28; //34-老师  32 35学生
            this.globalParam.userRole = 3;
            this.globalParam.courseId = ssktUtil.getUrlParam("courseId");*/

            if(this.globalParam.courseId!="" && this.globalParam.userId!="" &&
                !ssktUtil.isNull(this.globalParam.courseId) && !ssktUtil.isNull(this.globalParam.userId)){
                this.globalParam.connectPlg = true;
            }

            if(this.globalParam.connectPlg){
                websocketChat.bind();
            }
        };

        websocketChat.bind = function(){
            //一些效果 TODO
            this.effectView();

            //关闭正在进行的举手发言视频
            this.btnObj.closeRiseHandBtn.on('click',function(){
                if(window.confirm("您确定要进行该操作吗？")){
                    websocketChat.WebSocket.send(websocketChat.globalParam.closeRiseHand);
                    $(this).hide();
                }
            });

            //判断当前浏览器是否支持WebSocket
            if ('WebSocket' in window) {
                //先建立群聊链接
                this.globalParam.connectUrl = this.globalParam.connectUrl
                    .replace("{CHATTYPE}",this.globalParam.chatGroupType)
                    .replace("{COURSEID}",this.globalParam.courseId)
                    .replace("{USERID}",this.globalParam.userId);
                websocketChat.WebSocket = new WebSocket(this.globalParam.connectUrl);

                if(this.globalParam.userRole == "2"){//tea vedio

                    //连接成功建立的回调方法
                    websocketChat.WebSocket.onopen = function (event) {
                        //进入课堂
                        websocketChat.enterCourse();

                        //获取学生列表
                        websocketChat.getCourseStuList();

                        //获取教师直播视频
                        websocketChat.getTeaLineVedio();

                    };

                    //连接关闭的回调方法
                    websocketChat.WebSocket.onclose = function () {
                        //setMessageInnerHTML("断开连接");
                    };

                    //接收到消息的回调方法——tea
                    websocketChat.WebSocket.onmessage = function (event) {
                        var data = $.parseJSON(event.data);

                        if(data.code=="0"){
                            data = data.data;

                            switch (data.message){
                                case websocketChat.globalParam.hansUpMsg://有学生举手发言
                                    websocketChat.dealNewSpeak(data.userId);
                                    break;
                                case websocketChat.globalParam.approveMsg://同意发言后拉取学生视频
                                    var player = jwplayer('altContent2').setup({
                                        file : websocketChat.globalParam.stu_live_url,
                                        width : '100%',
                                        height : '100%',
                                        fallback : 'false',
                                        autostart : 'true',
                                        primary : 'flash',
                                        rtmp : {
                                            bufferlength : 0.1
                                        }
                                    });
                                    websocketChat.btnObj.closeRiseHandBtn.show();
                                    break;
                                case websocketChat.globalParam.logout://有人退出课堂
                                    websocketChat.displaySysMsg("logout",data);
                                    websocketChat.dealCourseStuList("logout",data);//处理左侧学生列表
                                    break;
                                case websocketChat.globalParam.joinin://有人进入课堂
                                    websocketChat.displaySysMsg("joinin",data);
                                    websocketChat.dealCourseStuList("joinin",data);//处理左侧学生列表
                                    break;
                                case websocketChat.globalParam.closeRiseHand://关闭举手发言视频
                                    websocketChat.dealCloseRiseHand("tea",data);
                                    break;
                                default :
                                    websocketChat.displayChatMsg("other",data);
                                    break;
                            }
                        }else{
                            //么有接受消息
                        }


                    };

                    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，
                    // 防止连接还没断开就关闭窗口，server端会抛异常。
                    window.onbeforeunload = function () {
                        if (confirm("确定关闭窗口？")){
                            websocketChat.WebSocket.close();
                        }
                    };


                    ///发言学生头像闪烁（有学生举手发言）
                    websocketChat.dealNewSpeak = function(userId){
                        $("#stuListUl li").each(function(){
                            if($(this).attr("data-id")==$.trim(userId)){$(this).find("a").addClass("active");}
                        });
                    };

                    //老师点击发言学生头像
                    $(document).on("click",'#stuListUl li a.active',function(){
                        $(this).removeClass("active");
                        if(window.confirm("是否同意该同学进行发言？")){
                            websocketChat.WebSocket.send(websocketChat.globalParam.approveMsg);
                        }else{
                            websocketChat.WebSocket.send(websocketChat.globalParam.declineMsg+"-"+$(this).attr("data-id"));
                        }
                    });


                }else if(this.globalParam.userRole == "3"){//stu vedio

                    //连接成功建立的回调方法
                    websocketChat.WebSocket.onopen = function (event) {
                        //进入课堂
                        websocketChat.enterCourse();

                        //获取教师直播视频
                        websocketChat.getTeaLineVedio();
                    };

                    //连接关闭的回调方法
                    websocketChat.WebSocket.onclose = function () {
                        //setMessageInnerHTML("断开连接");
                    };

                    //接收到消息的回调方法——stu
                    websocketChat.WebSocket.onmessage = function (event) {
                        var data = $.parseJSON(event.data);

                        if(data.code=="0"){
                            data = data.data;

                            if(data.message.indexOf(websocketChat.globalParam.declineMsg)>-1){//tea拒绝了stu的发言
                                if(data.message.split("-")[1] == websocketChat.globalParam.userId){
                                    alert("很遗憾，老师拒绝了您的发言请求");
                                }
                            }else{
                                switch (data.message){
                                    case websocketChat.globalParam.approveMsg://tea接受了stu的发言
                                        //stu 推流
                                        var flashvars = {
                                            streamname: websocketChat.globalParam.streamName,
                                            serverurl:websocketChat.globalParam.stu_publish_url,
                                            appname:websocketChat.globalParam.appName
                                        };
                                        var params = {
                                            menu: "false",
                                            scale: "noScale",
                                            allowFullscreen: "true",
                                            allowScriptAccess: "always",
                                            bgcolor: "black",
                                            wmode: "direct" // can cause issues with FP settings & webcam
                                        };
                                        var attributes = {
                                            id:"Hello"
                                        };
                                        swfobject.embedSWF(
                                            "/resource/js/swfobject/Hello.swf",
                                            "altContent", "215", "138", "10.0.0",
                                            "/resource/js/swfobject/expressInstall.swf",
                                            flashvars, params, attributes);

                                        $("#altContent").hide();
                                        $("#altContent2").show();

                                        //接受视频流
                                        var player = jwplayer('altContent2').setup({
                                            file : websocketChat.globalParam.stu_live_url,
                                            width : '100%',
                                            height : '100%',
                                            fallback : 'false',
                                            autostart : 'true',
                                            primary : 'flash',
                                            rtmp : {
                                                bufferlength : 0.1
                                            }
                                        });
                                        websocketChat.btnObj.closeRiseHandBtn.show();
                                        break;
                                    case websocketChat.globalParam.logout://有人退出课堂
                                        websocketChat.displaySysMsg("logout",data);
                                        break;
                                    case websocketChat.globalParam.joinin://有人进入课堂
                                        websocketChat.displaySysMsg("joinin",data);
                                        break;
                                    case websocketChat.globalParam.closeRiseHand://关闭举手发言视频
                                        websocketChat.dealCloseRiseHand("stu",data);
                                        break;
                                    default :
                                        websocketChat.displayChatMsg("other",data);
                                        break;
                                }
                            }

                        }else{
                            //么有接受消息
                        }

                    };

                    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，
                    // 防止连接还没断开就关闭窗口，server端会抛异常。
                    window.onbeforeunload = function () {
                        if (confirm("确定关闭窗口？")){
                            websocketChat.WebSocket.close();
                        }
                    };

                }


                //连接发生错误的回调方法
                websocketChat.WebSocket.onerror = function () {
                    //setMessageInnerHTML("error");
                };

                //发送消息
                websocketChat.btnObj.sendMsgBtn.on('click',function(){
                    var msg = $("#msgTxtArea").text();
                    if($.trim(msg)==""){
                        alert("消息不能为空");
                        return;
                    }
                    websocketChat.WebSocket.send(msg);
                    $("#msgTxtArea").text("");

                    var data = {
                        "userPhoto":'../../resource/images/tea1.jpg',//TODO 这个图片待处理
                        "userRealName":websocketChat.globalParam.userName,
                        "message":msg
                    };
                    websocketChat.displayChatMsg("me",data);

                });

            }else{
                alert("对不起！你的浏览器不支持webSocket！暂无法进行实时课堂");
                return;
            }
        };

        websocketChat.effectView = function(){
            $(".operation .action").mouseover(function(){
                $(this).parent().find(".function_hand").removeClass("de");
            });
            $(".operation .action").mouseout(function(){
                $(this).parent().find(".function_hand").addClass("de");
            });

            $(".draw_close").click(function(){
                $(".vote_wrap").addClass("de");
            });


            //历史答题弹出框操作
            $(".operation_QA").click(function(){
                $(".QABox").removeClass("de");
                $(".askteaBox").removeClass("de");
            });
            //答题弹出框操作
            $(".operation_DT").click(function(){
                $(".stualltiBox").removeClass("de");
            });
            //简介弹出框操作
            $(".operation_I").click(function(){
                $(".desBox").removeClass("de");
            });


            //学生举手发言提问
            $(".operation_raise").click(function(){
                //需要一个弹出框 loading 效果
                //$(".raiseBox").removeClass("de");
                if(!websocketChat.globalParam.connectPlg){
                    alert("websocket链接建立失败，请稍后再试");
                    return;
                }
                websocketChat.WebSocket.send(websocketChat.globalParam.hansUpMsg);
                alert("您的发言请求已发出，请您耐心等待老师回复");
            });


        };


        //获取教师直播视频
        websocketChat.getTeaLineVedio = function(){
            //用jwplayer 播放教师视频
            var player = jwplayer('teaVideoBox').setup({
                /*flashplayer: 'js/plugins/mediaplayer-5.7/player.swf',*/
                file : websocketChat.globalParam.course_live_url,
                width : '100%',
                height : '100%',
                fallback : 'false',
                autostart : 'true',
                primary : 'flash',
                rtmp : {
                    bufferlength : 0.1
                }
            });
        };

        //进入课堂
        websocketChat.enterCourse = function(){
            var url = ssktUtil.interFace + ssktUtil.interFaceAfterParamObj.enterCourseUrl;
            url = url.replace('{COURSEID}',this.globalParam.courseId)
                .replace('{USERID}',this.globalParam.userId);
            this.ajaxJsonGet(url,"",'enterCoursePlg');
        };
        websocketChat.enterCourseCallback = function(data){

        };

        //离开课堂
        websocketChat.leaveCourse = function(){
            var url = ssktUtil.interFace + ssktUtil.interFaceAfterParamObj.leaveCourseUrl;
            url = url.replace('{COURSEID}',this.globalParam.courseId)
                .replace('{USERID}',this.globalParam.userId);
            this.ajaxJsonGet(url,"",'leaveCoursePlg');
        };
        websocketChat.leaveCourseCallback = function(data){

        };

        //获得该课堂学生列表 gyy
        websocketChat.getCourseStuList = function(){
            var url = ssktUtil.interFace + ssktUtil.interFaceAfterParamObj.courseStuListUrl;
            url = url.replace('{COURSEID}',this.globalParam.courseId);
            this.ajaxJsonGet(url,"",'getStuListPlg');
        };
        websocketChat.getCourseStuListCallback = function(data){
            var i = 0, l = data.length,str="";
            for(;i<l;i+=1){
                //如果有学生来消息  给a标签加  class="active"  闪烁效果 TODO
                str += '<li data-id="'+data[i].userId+'"><a class="stuA" href="javascript:void(0);" data-id="'+data[i].userId+'"><span><img src="'+data[i].headImgUrl+'"></span>'+data[i].realName+'</a></li>'
            }
            $("#stuListUl").empty().append(str);
        };

        //显示聊天内容
        websocketChat.displayChatMsg = function(type,data){
            var chartBoxDiv = $("<div></div>");
            chartBoxDiv.addClass("chart_box");
            var chartBoxDiv_innerDiv = $("<div></div>");
            chartBoxDiv_innerDiv.addClass("chart_me clearfix");

            chartBoxDiv_innerDiv.append('<div class="chart_img"><span><img src="'+data.userPhoto+'"></span><span class="chart_name">'+data.userRealName+':</span></div>');
            chartBoxDiv_innerDiv.append('<div class="'+(type=="me"?"chart_mecon":"chart_othercon")+'">'+data.message+'</div>');

            chartBoxDiv.append(chartBoxDiv_innerDiv);

            $("#chatListCon").append(chartBoxDiv);
        };

        //显示系统消息
        websocketChat.displaySysMsg = function(type,data){
            switch (type){
                case "logout":
                    $("#chatListCon").append('<p>学生【'+data.userRealName+'】退出该课程</p>');
                    break;
                case "joinin":
                    $("#chatListCon").append('<p>学生【'+data.userRealName+'】加入该课程</p>');
                    break;
            }
        };

        //处理左侧学生列表
        websocketChat.dealCourseStuList = function(type,data){
            var userId = data.userId;
            switch (type){
                case "logout":
                    $("#stuListUl").children().each(function(){
                        if($(this).attr("data-id")==userId){
                            $(this).remove();
                        }
                    });
                    break;
                case "joinin":
                    var str = '<li data-id="'+data.userId+'"><a class="stuA" href="javascript:void(0);" data-id="'+data.userId+'"><span><img src="'+data.userPhoto+'"></span>'+data.userRealName+'</a></li>';
                    $("#stuListUl").append(str);
                    break;
            }
        };

        //关闭正在进行的举手发言视频
        websocketChat.dealCloseRiseHand = function(type,data){
            websocketChat.btnObj.closeRiseHandBtn.hide();
            $("#chatListCon").append('<p>学生【'+data.userRealName+'】已断开和老师的视频链接</p>');
            if(type=='stu'){
                $("#altContentCon").empty();
                $("#altContentCon").append('<div class="vedio_userimg" id="altContent"><img src="../../resource/images/avatar-boy.gif"></div>');
                $("#altContentCon").append('<div class="vedio_userimg" id="altContent2"></div>');
            }else{
                $("#altContentCon").empty();
                $("#altContentCon").append('<div id="altContent2"></div>');
            }
        };






        //ajsx jsonp POST
        websocketChat.ajaxJsonPost = function(url,data,urlFlg){
            $.ajax({
                urlFlg:urlFlg,//标识url类别-回调
                url: url,
                data:data,
                dataType:'json',
                type:"POST",
                ContentType:'application/x-www-form-urlencoded',
                timeout: 10000,
                beforeSend:function(){
                    websocketChat.doAjaxBeforeSend(this.urlFlg);
                },
                success: function(data){
                    websocketChat.doAjaxSuccessCallBack(this.urlFlg,data);
                },
                error: function(){
                    //alert('error,请稍后再试');
                }
            });
        };
        websocketChat.ajaxJsonGet = function(url,data,urlFlg){
            $.ajax({
                urlFlg:urlFlg,//标识url类别-回调
                url: url,
                data:data,
                dataType:'json',
                type:"GET",
                ContentType:'application/x-www-form-urlencoded',
                timeout: 10000,
                beforeSend:function(){
                    websocketChat.doAjaxBeforeSend(this.urlFlg);
                },
                success: function(data){
                    websocketChat.doAjaxSuccessCallBack(this.urlFlg,data);
                },
                error: function(){
                    //alert('error,请稍后再试');
                }
            });

            //本地模拟数据测试
            /*$.getJSON(url, function(data){
                websocketChat.doAjaxSuccessCallBack(urlFlg,data);
            });*/
        };

        websocketChat.doAjaxBeforeSend = function (urlFlg){
            switch (urlFlg){

            }
        };
        websocketChat.doAjaxSuccessCallBack = function (urlFlg,data){
            if(data.code!="0"){alert(data.msg);return;}
            switch (urlFlg){
                case "getStuListPlg"://获取学生列表
                    websocketChat.getCourseStuListCallback(data.data);
                    break;
                case "enterCoursePlg"://进入课堂
                    websocketChat.enterCourseCallback(data.data);
                    break;
                case "leaveCoursePlg"://离开课堂
                    websocketChat.leaveCourseCallback(data.data);
                    break;
            }
        };

    }
})(this,this.document);