/**
 * Created by admin on 2017/1/1.
 */

/**
 * Created by admin on 2016/12/30.
 */


//添加教室，分配设备
function addClassroomDevice() {
    var inputBuilding = $("#addBuilding option:selected").text();
    var inputClassroom = $("#addClassroom option:selected").text();
    console.log(inputBuilding+inputClassroom);
    var computerClassroom = $("#addComputerClassroom").val();
    var cameraClassroom = $("#addCameraClassroom").val();
    var projectorClassroom = $("#addProjectorClassroom").val();
    var raspberryClassroom = $("#addRaspberryClassroom").val();
    var singlechipClassroom = $("#addSinglechipClassroom").val();
    var raspberryCode = $("#addInputRaspberry").val();
    var data = {
        "buildingNum":inputBuilding,
        "classroomNum":inputClassroom,
        "computerTypeId":computerClassroom,
        "cameraTypeId":cameraClassroom,
        "projectorTypeId":projectorClassroom,
        "raspberryTypeId":raspberryClassroom,
        "singlechipTypeId":singlechipClassroom,
        "raspberryCode":raspberryCode
    }
    $.ajax({
        type: "POST",
        url: "/ajax_add_Classroom",
        dataType: "json",
        data: data,
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                window.location.href = "assignDevice";
            }else if(msg.data.judge == "-9"){
                $(".classroom-wrong-tip").css("display","block");
            }else if(msg.data.judge == "-3"){
                $(".classroom-wrong-tip").css("display","block");
                $(".classroom-wrong-tip").text("该教室已分配设备！");
            }else{
                $(".classroom-wrong-tip").css("display","block");
                $(".classroom-wrong-tip").text("信息不能为空！");
            }
        }
    });
}

var deviceid;



//修改教室设备
function editClassroomDevice() {
    var inputBuilding = $("#editBuilding").val();
    var inputClassroom = $("#editClassroom").val();
    var computerClassroom = $("#editComputerClassroom").val();
    var cameraClassroom = $("#editCameraClassroom").val();
    var projectorClassroom = $("#editProjectorClassroom").val();
    var raspberryClassroom = $("#editRaspberryClassroom").val();
    var singlechipClassroom = $("#editSinglechipClassroom").val();
    var raspberryCode = $("#editInputRaspberry").val();
    var data = {
        "did":deviceid,
        "buildingNum":inputBuilding,
        "classroomNum":inputClassroom,
        "computerTypeId":computerClassroom,
        "cameraTypeId":cameraClassroom,
        "projectorTypeId":projectorClassroom,
        "raspberryTypeId":raspberryClassroom,
        "singlechipTypeId":singlechipClassroom,
        "raspberryCode":raspberryCode
    }
    $.ajax({
        type: "POST",
        url: "/ajax_edit_classroom",
        dataType: "json",
        data: data,
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                window.location.href = "assignDevice";
            }else if(msg.data.judge == "-9"){
                $(".classroom-wrong-tip").css("display","block");
            }else{
                $(".classroom-wrong-tip").css("display","block");
                $(".classroom-wrong-tip").text("信息不能为空！");
            }
        }
    });
}

//获取用户点击要删除的id号
function getClassroomId(did){
    deviceid = did;
}

//删除教室
function deleteClassroom() {
    $.ajax({
        type: "POST",
        url: "/ajax_delete_classroom",
        dataType: "json",
        data: {
            "did":deviceid
        },
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                window.location.href = "assignDevice";
            }else if(msg.data.judge == "-1"){
                $(".delete-wrong-tip").css("display","block");
            }else if(msg.data.judge == "-9"){
                $(".delete-wrong-tip").css("display","block");
                $(".delete-wrong-tip").text("删除失败！")
            }
        }
    });
}

$(document).ready(function(){
    $('#addBuilding').change(function(){
        var bid=$(this).children('option:selected').val();
        console.log(bid);
        changeClassroomByBuilding(bid);
    });
});
function changeClassroomByBuilding(bid){
    $.ajax({
        type: "POST",
        url: "/ajax_change_building",
        dataType: "json",
        data: {
            "bid":bid
        },
        success: function(msg){
            console.log(msg.data.classroomlist);
            $('#addClassroom').html("");
            var classroomlist = msg.data.classroomlist;
            for(var i=0;i<classroomlist.length;i++){
                $('#addClassroom').append('<option value="'+classroomlist[i]['id']+'">'+classroomlist[i]['classroomNum']+'</option>');
            }
        }
    });
}


