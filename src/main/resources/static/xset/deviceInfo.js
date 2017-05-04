/**
 * Created by admin on 2016/12/30.
 */

//点击添加前 清空模态框信息 切换添加或修改事件（减少冗余，添加和修改共用模态框）
function resetModal() {
    $("#inputComputerName").val("");
    $("#inputOS").val("");
    $("#inputMemory").val("");
    $("#inputDisk").val("");
    $('.computer-btn').attr('onclick','addComputer()');
}
function resetCameraModal() {
    $("#cameraType").val("");
//     $('.camera-btn').attr('onclick','addCamera()');
}
function resetProjectorModal() {
    $("#projectorType").val("");
    // $('.camera-btn').attr('onclick','addProjector()');
}
function resetRaspberryModal() {
    $("#raspberryType").val("");
    // $('.camera-btn').attr('onclick','addProjector()');
}
function resetSingleChipModal() {
    $("#singlechipType").val("");
    // $('.camera-btn').attr('onclick','addProjector()');
}
//添加电脑设备
function addComputer() {
    var computername = $("#inputComputerName").val();
    var os = $("#inputOS").val();
    var memory = $("#inputMemory").val();
    var disk = $("#inputDisk").val();
    var data = {
        "computerTypeName":computername,
        "memorySize":memory,
        "diskSize":disk,
        "operatingSystem":os
    }
    $.ajax({
        type: "POST",
        url: "/ajax_add_computerType",
        dataType: "json",
        data: data,
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                //$.cookie('tab','computer',{expires:1});
                window.location.href = "deviceInfo";
            }else if(msg.data.judge == "-9"){
                $(".device-wrong-tip").css("display","block");
            }else if(msg.data.judge == "-5"){
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("该设备已存在！");
            }else{
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("信息不能为空！");
            }
        }
    });
}

var coid;
var device;
var cameraID;
var projectorID;
var raspberryID;
var singlechipID;
//添加摄像头设备
function addCamera() {
    // alert($("#cameraType").val());
    var data = {
        "cameraType":$("#cameraType").val()
    }
    $.ajax({
        type:"POST",
        url:"ajax_get_cameraType_info",
        dataType:"json",
        data:data,
        success:function (msg) {
            if(msg.data.judge == "0")
            {
                // alert("success");
                //$.cookie('tab','camera',{expires:1});
                window.location.href = "deviceInfo";
            }else if(msg.data.judge == "block")
            {
                // alert("block");
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("信息不能为空！");
            }else if(msg.data.judge == "-5"){
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("已存在该设备！");
            }
        }
    })
}

//添加投影仪设备
function addProjector() {
    var data = {
        "projectorType":$("#projectorType").val()
    }
    $.ajax({
        type: "POST",
        url: "ajax_add_projectorType",
        dataType: "json",
        data: data,
        success:function (msg) {
            // alert(msg);
            if(msg.judge == "0")
            {
                //$.cookie('tab','projector',{expires:1});
                window.location.href = "deviceInfo";
            }else if(msg.judge == "block"){
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("信息不能为空！");
            }else if(msg.judge == "-5"){
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("已存在该设备！");
            }
        }
    })
}
//添加树莓派设备
function addRaspBerry()
{
    // alert("add raspberry");
    var data = {
        "raspberryType":$("#raspberryType").val()
    }
    $.ajax({
        type: "POST",
        url: "ajax_add_raspberryType",
        dataType:"json",
        data:data,
        success:function (msg) {
            if(msg.judge == "block")
            {
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("信息不能为空！");
            }else if(msg.judge == "-5")
            {
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("已存在该设备！");
            }else
            {
                //$.cookie('tab','raspberry',{expires:1});
                window.location.href = "deviceInfo";
            }
        }
    })
}

function addSingleChip() {
    // alert("add singlechip");
    var data = {
        "singlechipType":$("#singlechipType").val()
    }
    $.ajax({
        type:"POST",
        url:"ajax_add_singlechipType",
        dataType:"json",
        data:data,
        success:function (msg) {
            if(msg.judge == "block")
            {
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("信息不能为空！");
            }else if(msg.judge == "-5")
            {
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("已存在该设备！");
            }else
            {
                //$.cookie('tab','singlechip',{expires:1});
                window.location.href = "deviceInfo";
            }
        }
    })
}
//根据id获取电脑设备信息，并显示在修改电脑设备模态框上
function getComputerInfo(cid) {
    coid = cid;

    $('.computer-btn').attr('onclick','editComputer()');
    $.ajax({
        type: "POST",
        url: "/ajax_get_computerType_info",
        dataType: "json",
        data: {
            "cid":cid
        },
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                var computer= msg.data.computer;

                $("#inputComputerName").val(computer.computerTypeName);
                $("#inputOS").val(computer.operatingSystem);
                $("#inputMemory").val(computer.memorySize);
                $("#inputDisk").val(computer.diskSize);

            }else{
                $(".device-wrong-tip").css("display","block");
            }
        }
    });
}
//获取摄像头设备信息
function getCameraInfo(cameraId) {
    cameraID = cameraId;
    $('.camera-btn').attr('onclick','editCameraInfo()');
    $.ajax({
        type:"POST",
        url:"ajax_get_cameraInfo",
        dataType:"json",
        data:{
            "cameraId":cameraId
        },
        success:function (msg) {
            console.log(msg.data);
            if(msg.data.judge=="0")
            {
                var cameraT = msg.data.cameraT;
                // alert(cameraT.cameraTypeName);
                $("#cameraType").val(cameraT.cameraTypeName);
            }else
            {
                alert("did not get camera info!")
            }
        }
    })
}

//获取投影仪设备信息
function getProjectorInfo(projectorId) {
    projectorID = projectorId;
    $('.projector-btn').attr('onclick','editProjectorInfo()');
    $.ajax({
        type:"POST",
        url:"ajax_get_projectorInfo",
        dataType:"json",
        data:{
            "projectorID":projectorId
        },
        success:function (msg) {
            if(msg.judge=="0")
            {
                var projectorT = msg.data.projectorT;
                $("#projectorType").val(projectorT.projectorTypeName);
            }else
            {
                alert("did not get projector info!")
            }
        }
    })
}


//获取树莓派设备信息
function getRaspBerryInfo(raspberryId) {
    raspberryID = raspberryId;
    $('.raspberry-btn').attr('onclick','editRaspberryInfo()');
    // alert(raspberryID);
    var data={
        "raspberryID":raspberryID
    }
    $.ajax({
        type:"POST",
        url:"ajax_get_raspberryInfo",
        dataType:"json",
        data:data,
        success:function (msg) {
            if(msg.judge == "0")
            {
                $("#raspberryType").val(msg.data);
            }else
            {
                alert("can not get raspberry info");
            }
        }
    })
}

//获取单片机设备信息
function getSingleChipInfo(singlechipId) {
    singlechipID = singlechipId;
    // alert(singlechipID);
    $('.singlechip-btn').attr('onclick','editSingleChipInfo()');
    var data = {
        "singlechipID":singlechipID
    }
    $.ajax({
        type:"POST",
        url:"ajax_get_singlechipType",
        dataType:"json",
        data:data,
        success:function (msg) {
            if(msg.judge == "0")
            {
                $("#singlechipType").val(msg.data);
            }else {
                alert("can not get singlechip info");
            }
        }
    })
}

//修改电脑设备
function editComputer() {
    var computername = $("#inputComputerName").val();
    var os = $("#inputOS").val();
    var memory = $("#inputMemory").val();
    var disk = $("#inputDisk").val();
    var data = {
        "coid":coid,
        "computerTypeName":computername,
        "memorySize":memory,
        "diskSize":disk,
        "operatingSystem":os
    }
    $.ajax({
        type: "POST",
        url: "/ajax_edit_computerType",
        dataType: "json",
        data: data,
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                //$.cookie('tab','computer',{expires:1});
                window.location.href = "deviceInfo";
            }else if(msg.data.judge == "-9"){
                $(".device-wrong-tip").css("display","block");
            }else if(msg.data.judge == "-5"){
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("该设备已存在！");
            }else{
                $(".device-wrong-tip").css("display","block");
                $(".device-wrong-tip").text("信息不能为空！");
            }
        }
    });
}
//修改摄像头设备信息
function editCameraInfo(){
    // alert("editCameraInfo");
    var cameraName = $("#cameraType").val();
    var data = {
        "cameraId":cameraID,
        "cameraTypeName":cameraName
    }
    $.ajax({
        type:"POST",
        url:"ajax_edit_cameraType",
        dataType:"json",
        data:data,
        success:function (msg) {
            console.log(msg);
            if (msg.judge == "block") {
                $(".device-wrong-tip").css("display", "block");
                $(".device-wrong-tip").text("信息不能为空！");
            }else if(msg.judge == "0"){
                //$.cookie('tab','camera',{expires:1});
                window.location.href = "deviceInfo";
            }else if(msg.judge == "-5"){
                $(".device-wrong-tip").css("display", "block");
                $(".device-wrong-tip").text("没有设备信息被修改！");
            }
        }
    });
}

//修改投影仪设备信息
function editProjectorInfo(){
    // alert("editProjectorInfo");
    var projectorName = $("#projectorType").val();
    var data = {
        "projectorId":projectorID,
        "projectorTypeName":projectorName
    }
    $.ajax({
        type:"POST",
        url:"ajax_edit_projectorType",
        dataType:"json",
        data:data,
        success:function (msg) {
            if (msg.judge == "block") {
                $(".device-wrong-tip").css("display", "block");
                $(".device-wrong-tip").text("信息不能为空！");
            }else if(msg.judge == "0"){
                //$.cookie('tab','projector',{expires:1});
                window.location.href = "deviceInfo";
            }else if(msg.judge == "-5"){
                $(".device-wrong-tip").css("display", "block");
                $(".device-wrong-tip").text("没有设备信息被修改！");
            }

        }
    });
}

//修改树莓派设备信息
function editRaspberryInfo() {
    // alert("edit raspberry info");
    var raspberryTypeName = $("#raspberryType").val();
    var data = {
        "raspberryId":raspberryID,
        "raspberryName":raspberryTypeName
    }
    $.ajax({
        type:"POST",
        url:"ajax_edit_raspberryType",
        dataType:"json",
        data:data,
        success:function (msg) {
            if(msg.judge == "0")
            {
                //$.cookie('tab','raspberry',{expires:1});
                window.location.href = "deviceInfo";
            }else if(msg.judge == "block")
            {
                $(".device-wrong-tip").css("display", "block");
                $(".device-wrong-tip").text("信息不能为空！");
            }else if(msg.judge == "-5")
            {
                $(".device-wrong-tip").css("display", "block");
                $(".device-wrong-tip").text("没有设备信息被修改！");
            }
        }
    })
}

//修改单片机设备信息
function editSingleChipInfo() {
    // alert("edit singlechip info");
    var singlechipName= $("#singlechipType").val();
    var data = {
        "singlechipID":singlechipID,
        "singlechipName":singlechipName
    }
    $.ajax({
        type:"POST",
        url:"ajax_edit_singlechipType",
        dataType:"json",
        data:data,
        success:function (msg) {
            if(msg.judge == "0")
            {
                //$.cookie('tab','singlechip',{expires:1});
                window.location.href = "deviceInfo";
            }else if(msg.judge == "block")
            {
                $(".device-wrong-tip").css("display", "block");
                $(".device-wrong-tip").text("信息不能为空！");
            }else if(msg.judge == "-5")
            {
                $(".device-wrong-tip").css("display", "block");
                $(".device-wrong-tip").text("没有设备信息被修改！");
            }
        }
    })
}

//获取用户点击要删除的id号
function getDeviceId(cid,deviceName){
    coid = cid;
    device = deviceName;
}

//删除设备
function deleteDevice() {
    $.ajax({
        type: "POST",
        url: "/ajax_delete_device",
        dataType: "json",
        data: {
            "coid":coid,
            "device":device
        },
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                //$.cookie('tab',device,{expires:1});
                window.location.href = "deviceInfo";
            }else if(msg.data.judge == "-1"){
                $(".delete-wrong-tip").css("display","block");
            }else if(msg.data.judge == "-9"){
                $(".delete-wrong-tip").css("display","block");
                $(".delete-wrong-tip").text("删除失败！")
            }
        }
    });
}
//判断应该是哪个tab
function judgeTab() {
    var lastTab = $.cookie('tab');
    if(lastTab == 'camera'){
        $('#tabCamera').addClass('active');
        $('#tabComputer').removeClass('active');
        $('#tab_2').addClass('active');
        $('#tab_1').removeClass('active');
    }else if(lastTab == 'projector'){
        $('#tabProjector').addClass('active');
        $('#tabComputer').removeClass('active');
        $('#tab_3').addClass('active');
        $('#tab_1').removeClass('active');
    }else if(lastTab == 'raspberry'){
        $('#tabRaspberry').addClass('active');
        $('#tabComputer').removeClass('active');
        $('#tab_4').addClass('active');
        $('#tab_1').removeClass('active');
    }else if(lastTab == 'singlechip'){
        $('#tabSingleChip').addClass('active');
        $('#tabComputer').removeClass('active');
        $('#tab_5').addClass('active');
        $('#tab_1').removeClass('active');
    }
}
//监听tab点击事件
function listenerTab(){
    $('#tabComputer').on('click',function () {
        $.cookie('tab','computer',{expires:1});
    });
    $('#tabCamera').on('click',function () {
        $.cookie('tab','camera',{expires:1});
    });
    $('#tabProjector').on('click',function () {
        $.cookie('tab','projector',{expires:1});
    });
    $('#tabRaspberry').on('click',function () {
        $.cookie('tab','raspberry',{expires:1});
    });
    $('#tabSingleChip').on('click',function () {
        $.cookie('tab','singlechip',{expires:1});
    });
}
$(document).ready(function(){
    judgeTab();
    listenerTab();
});