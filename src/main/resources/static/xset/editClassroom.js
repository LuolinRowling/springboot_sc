/**
 * Created by admin on 2017/1/1.
 */
var deviceid;
//根据id获取电脑设备信息，并显示在修改电脑设备模态框上
function getClassroomInfo(did) {
    deviceid = did;

    $.ajax({
        type: "POST",
        url: "/ajax_get_classroom_info",
        dataType: "json",
        data: {
            "did":did
        },
        success: function(msg){
            if(msg.data.judge == "0"){
                var deviceInfo= msg.data.deviceInfo;

                $("#editBuilding").val(deviceInfo.buildingNum);
                $("#editClassroom").val(deviceInfo.classroomNum);
                $("#editComputerClassroom").val(deviceInfo.computerTypeId);
                $("#editCameraClassroom").val(deviceInfo.cameraTypeId);
                $("#editProjectorClassroom").val(deviceInfo.projectorTypeId);
                $("#editRaspberryClassroom").val(deviceInfo.raspberryTypeId);
                $("#editSinglechipClassroom").val(deviceInfo.singlechipTypeId);
                $("#editInputRaspberry").val(deviceInfo.raspberryCode);

            }
        }
    });
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

//加载完页面后立刻执行
$(document).ready(function(){
    var id = getUrlParam("id");
    getClassroomInfo(id);
});