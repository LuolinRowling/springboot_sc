function login() {
    var username = $("#username").val();
    var password = $("#password").val();
    var data = {
        "username":username,
        "password":hex_md5(password)
    }
    $.ajax({
        type: "POST",
        url: "/login",
        dataType: "json",
        data: data,
        success: function(msg){
            console.log(msg);
            if(msg.data.judge == "0"){
                window.location.href = "index";
            }else{
                $(".wrong-tip").css("display","block");
            }
        }
    });
}
