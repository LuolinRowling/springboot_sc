/**
 * Created by admin on 2017/1/9.
 */


//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

//加载完页面后立刻执行
$(document).ready(function(){
    var id = getUrlParam("id");
    getPullAddress(id);
});

function getPullAddress(did) {
    deviceid = did;
    $.ajax({
        type: "POST",
        url: "/ajax_get_pull_address",
        dataType: "json",
        data: {
            "did":did
        },
        success: function(msg){
            //if(msg.data.judge == "0"){
                var address= msg.data.address;
                getLineVedio(address);
            //}
        }
    });
}

//获取直播视频
getLineVedio = function(url){
    console.log("test:"+url);
    var player = jwplayer('palyerVideoBox').setup({
        /*flashplayer: 'js/plugins/mediaplayer-5.7/player.swf',*/
        file : url,
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
