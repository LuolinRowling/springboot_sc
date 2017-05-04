/**
 * Created by admin on 2017/1/17.
 */
//消息中心
function getMessageListCenter(){
    $.ajax({
        type: "POST",
        url: "/ajax_get_message_list_center",
        dataType: "json",
        success: function(msg) {
            var listCenter = msg.data.listCenter;
            var length = listCenter.length;
            var end = 0;
            console.log(listCenter);
            $("#messageLength").html(length);
            $('#messageHeader').html('您有 '+length+' 条通知');
            $('#messageList').html('');
            if((length-10)<=0){
                end = 0;
            }else{
                end = length - 10;
            }
            for(var i=length-1;i>=end;i--){
                var message = listCenter[i]['message'];
                var time = listCenter[i]['nowTime'];
                var buildClass = listCenter[i]['buildClass'];
                var judge = listCenter[i]['judge'];
                var tab = listCenter[i]['tab'];
                var tabMessage = "";
                if(tab=='video'){
                    tabMessage = "视频";
                }else if(tab=='device'){
                    tabMessage = "设备";
                }else{
                    tabMessage = "树莓派"
                }
                if(judge == "success"){
                    $('#messageList').append('<li><a href="javascript:void(0);"><i class="fa fa-check-circle text-aqua"></i>'+ tabMessage +" : "+ buildClass+" "+message +'</a></li>');
                }else if(judge == "fail"){
                    $('#messageList').append('<li><a href="javascript:void(0);"><i class="fa fa-times-circle text-red"></i>'+ tabMessage +" : "+  buildClass+" "+message +'</a></li>');
                }else {
                    $('#messageList').append('<li><a href="javascript:void(0);"><i class="fa fa-exclamation-circle text-yellow"></i>'+ tabMessage +" : "+  buildClass+" "+message +'</a></li>');
                }
            }
        }
    });
}

$(document).ready(function() {
    getMessageListCenter();
});

