/**
 * Created by admin on 2017/1/6.
 */
//第一次进入页面的时候先执行一次
$(document).ready(function() {
    run();
});

function run(){
    setInterval('longPolling()',10*1000);
}

function longPolling()
{
    $.ajax({
        type: "POST",
        url: "/ajax_get_message_list",
        dataType: "json",
        success: function(msg) {
            var list = msg.data.list;
            var length = msg.data.length;
            //实时更新数量
            $("#messageLength").html(length);
            console.log(list);
            for(var i=0;i<list.length;i++){
                var ownId = list[i]['ownId'];
                var message = list[i]['message'];
                var time = list[i]['nowTime'];
                var buildClass = list[i]['buildClass'];
                var judge = list[i]['judge'];
                var tab = list[i]['tab'];
                var deviceInfo = list[i]['deviceInfo'];
                if(judge == "success"){
                    $.toaster({ title : '成功', priority : 'success', message : buildClass+" "+message });
                }else if(judge == "fail"){
                    $.toaster({ title : '失败', priority : 'danger', message : buildClass+" "+message });
                }else if(judge == "timeout"){
                    $.toaster({ title : '超时', priority : 'warning', message : buildClass+" "+message });
                }else{
                    $.toaster({ title : '离线', priority : 'warning', message : buildClass+" "+message });
                }
                if(tab == "video"){
                    handleVideo(deviceInfo);
                }else if(tab == "device"){
                    handleDevice(deviceInfo);
                }else{
                    handleVideo(deviceInfo);
                    handleDevice(deviceInfo);
                }
                deleteMessage(ownId,time);
            }
        }
    });
}

function deleteMessage(ownId,time) {
    $.ajax({
        type: "POST",
        url: "/ajax_delete_message_list",
        data:{
            "ownId": ownId,
            "time": time
        },
        dataType: "json",
        success: function(msg) {
            var list = msg.data.judge;
            console.log(list);
        }
    });
}

function handleDevice(deviceInfo) {
    console.log("test");
    console.log(deviceInfo);

    var raspberryStatus = deviceInfo['raspberryStatus'];
    var singlechipStatus = deviceInfo['singlechipStatus'];
    var ownId = deviceInfo['raspberryCode'];
    var tr = $("#" + "trDevice" + ownId);
    //单片机离线异常状态 电脑和投影仪无法使用
    if(singlechipStatus == 0||singlechipStatus == 2){
        tr.children("td").eq(2).children("button").attr("disabled","true");
        tr.children("td").eq(4).children("button").attr("disabled","true");
        var statusZh = (singlechipStatus==0)?"离线":"异常";
        tr.children("td").eq(6).html(statusZh);
    }else {
        tr.children("td").eq(2).children("button").removeAttr("disabled");
        tr.children("td").eq(4).children("button").removeAttr("disabled");
        tr.children("td").eq(5).html("在线");
    }
    //树莓派离线、异常状态
    if(raspberryStatus == 0||raspberryStatus == 2){
        tr.children("td").children("button").attr("disabled","true");
        tr.children(".device-checkbox").attr("disabled","true");
        var statusZh = (raspberryStatus==0)?"离线":"异常";
        tr.children("td").eq(5).html(statusZh);
    }else {
        tr.children("td").children("button").removeAttr("disabled");
        tr.children(".device-checkbox").removeAttr("disabled");
        tr.children("td").eq(5).html("在线");
    }


    deviceStatus(tr,deviceInfo);
}

function deviceStatus(tr,deviceInfo){
    var td = tr.children("td");
    var computerStatus = deviceInfo['computerStatus'];
    var cameraStatus = deviceInfo['cameraStatus'];
    var projectorStatus = deviceInfo['projectorStatus'];
    var raspberryStatus = deviceInfo['raspberryStatus'];
    var singlechipStatus = deviceInfo['singlechipStatus'];
    var id = deviceInfo['id'];
    //先把原有的button样式移除
    td.eq(2).children("button").removeClass("btn-success btn-danger btn-warning");
    td.eq(3).children("button").removeClass("btn-success btn-danger btn-warning");
    td.eq(4).children("button").removeClass("btn-success btn-danger btn-warning");
    //电脑
    if(computerStatus==0){
        td.eq(2).children("button").addClass("btn-success");
        td.eq(2).children("button").html("开启");
        td.eq(2).children("button").attr('onclick',"operateDevice("+id+",'computer','open')");
    }else if(computerStatus==1){
        td.eq(2).children("button").addClass("btn-danger");
        td.eq(2).children("button").html("关闭");
        td.eq(2).children("button").attr('onclick',"operateDevice("+id+",'computer','close')");
    }else if(computerStatus==2){
        td.eq(2).children("button").addClass("btn-warning");
        td.eq(2).children("button").html("异常");
        td.eq(2).children("button").attr('onclick',"operateDevice("+id+",'computer','open')");
    }
    //摄像头
    if(cameraStatus==0){
        td.eq(3).children("button").addClass("btn-success");
        td.eq(3).children("button").html("开启");
        td.eq(3).children("button").attr('onclick',"operateDevice("+id+",'camera','open')");
    }else if(cameraStatus==1){
        td.eq(3).children("button").addClass("btn-danger");
        td.eq(3).children("button").html("关闭");
        td.eq(3).children("button").attr('onclick',"operateDevice("+id+",'camera','close')");
    }else if(cameraStatus==2){
        td.eq(3).children("button").addClass("btn-warning");
        td.eq(3).children("button").html("异常");
        td.eq(3).children("button").attr('onclick',"operateDevice("+id+",'camera','open')");
    }

    //投影仪
    if(projectorStatus==0){
        td.eq(4).children("button").addClass("btn-success");
        td.eq(4).children("button").html("开启");
        td.eq(4).children("button").attr('onclick',"operateDevice("+id+",'projector','open')");
    }else if(projectorStatus==1){
        td.eq(4).children("button").addClass("btn-danger");
        td.eq(4).children("button").html("关闭");
        td.eq(4).children("button").attr('onclick',"operateDevice("+id+",'projector','close')");
    }else if(projectorStatus==2){
        td.eq(4).children("button").addClass("btn-warning");
        td.eq(4).children("button").html("异常");
        td.eq(4).children("button").attr('onclick',"operateDevice("+id+",'projector','open')");
    }

}


function handleVideo(deviceInfo) {
    var status = deviceInfo['raspberryStreamStatus'];
    var ownId = deviceInfo['raspberryCode'];
    var id = deviceInfo['id'];
    var td = $("#" + "tr" + ownId).children("td");

    //修正三个button状态
    td.eq(3).children("button").html("开始推流");
    td.eq(4).children("button").html("开始拉流");
    td.eq(5).children("button").html("发布广播");
    td.eq(3).children("button").attr('onclick',"operateStream("+id+",'start_push',this)");
    td.eq(4).children("button").attr('onclick',"getBuildClass("+id+",this)");
    td.eq(4).children("button").attr('data-toggle',"modal");
    td.eq(4).children("button").attr('data-target',"#pullModal");
    td.eq(5).children("button").attr('onclick',"operateStream("+id+",'start_broadcast',this)");
    //在线 异常 离线处理
    if(status== 0||status==1||status==2){
        if(status == 1){
            //整行数据启用
            td.children("button").removeAttr("disabled");
            td.children("a").removeAttr("disabled");
        }else {
            //整行数据禁用
            td.children("button").attr("disabled","true");
            td.children("a").attr("disabled","true");
        }
    }else if(status==3){
        td.eq(3).children("button").html("停止推流");
        td.children("button").attr("disabled","true");
        td.eq(3).children("button").removeAttr("disabled");
        td.eq(3).children("button").attr('onclick',"operateStream("+id+",'stop_push',this)");
    }else if(status==4){
        td.eq(4).children("button").html("停止拉流");
        td.children("button").attr("disabled","true");
        td.eq(4).children("button").removeAttr("disabled");
        td.eq(4).children("button").attr('onclick',"operateStream("+id+",'stop_pull',this)");
        td.eq(4).children("button").removeAttr("data-toggle");
        td.eq(4).children("button").removeAttr("data-target");
    }else if(status==5){
        td.eq(5).children("button").html("停止广播");
        td.children("button").attr("disabled","true");
        td.eq(5).children("button").removeAttr("disabled");
        td.eq(5).children("button").attr('onclick',"operateStream("+id+",'stop_broadcast',this)");
    }
    //0 离线，1 空闲（在线），2 异常，3 正在推流，4 正在拉流，5 正在广播
    videoRaspberryStatus(td,status);
}

function videoRaspberryStatus(td,status){
    var statusZn;
    switch(status)
    {
        case 0:
            statusZn = "离线";
            break;
        case 1:
            statusZn = "空闲（在线）";
            break;
        case 2:
            statusZn = "异常"
            break;
        case 3:
            statusZn = "正在推流";
            break;
        case 4:
            statusZn = "正在拉流";
            break;
        case 5:
            statusZn = "正在广播"
            break;
    }
    td.eq(2).html(statusZn);
}

