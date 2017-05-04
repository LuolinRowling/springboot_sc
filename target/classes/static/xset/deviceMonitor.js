/**
 * Created by admin on 2017/1/2.
 */

function operateDevice(cid,device,operate) {
    $.ajax({
        type: "POST",
        url: "/ajax_edit_device_status",
        dataType: "json",
        data: {
            "did":cid,
            "device":device,
            "operation":operate
        },
        success: function(msg){
            //run();
        }
    });
}



function editAllDevice(operate) {
    $.ajax({
        type: "POST",
        url: "/ajax_edit_all_device_status",
        data:{
            "operation":operate
        },
        dataType: "json",
        success: function() {
            //run()
        }
    });
}

function changeButtonDisable(){
    //树莓派异常或关闭  所有操作都不能进行
    $(".device-disabled").children("td").children("button").attr("disabled","true");
    $(".device-disabled .device-checkbox").attr("disabled","true");
    //单片机异常或关闭  电脑和投影仪 都不能进行
    $(".singlechip-disabled").children("button").attr("disabled","true");
}

// $(document).ready(function () {
//     changeButtonDisable();
// });

