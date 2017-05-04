function sendMessage() {
    var data={
        message:"pull",
        ownId:"1"
    };
    $.ajax({
        type: "POST",
        url: "/sendMessage.do",
        dataType: "json",
        data: data,
        success: function(data){
            console.log(data.msg);
            if(data.msg == "0"){
                window.location.href = "index.do";
            }else{
                $('.login-tip').show();
            }
        }
    });
}