console.log(window.location.pathname);
var judgeName = window.location.pathname;
if(judgeName.indexOf("userManage")!=-1){
    $(".system-manage").addClass("active");
    $(".privilege-manage").addClass("active");
    $(".user-manage").addClass("active");
}else if(judgeName.indexOf("roleManage")!=-1){
    $(".system-manage").addClass("active");
    $(".privilege-manage").addClass("active");
    $(".role-manage").addClass("active");
}else if(judgeName.indexOf("deviceInfo")!=-1){
    $(".device-manage").addClass("active");
    $(".edit-device").addClass("active");
    $(".device-info").addClass("active");
}else if(judgeName.indexOf("assignDevice")!=-1||judgeName.indexOf("addClassroom")!=-1||judgeName.indexOf("editClassroom")!=-1){
    $(".device-manage").addClass("active");
    $(".edit-device").addClass("active");
    $(".assign-device").addClass("active");
}else if(judgeName.indexOf("deviceMonitor")!=-1){
    $(".device-manage").addClass("active");
    $(".device-monitor").addClass("active");
}else if(judgeName.indexOf("videoManage")!=-1||judgeName.indexOf("videoLive")!=-1){
    $(".video-manage").addClass("active");
    $(".video-stream").addClass("active");
}else if(judgeName.indexOf("messageManage")!=-1){
    $(".system-manage").addClass("active");
    $(".message-manage").addClass("active");
}