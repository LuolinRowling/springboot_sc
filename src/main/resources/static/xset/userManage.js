/**
 * Created by admin on 2016/12/28.
 */

//添加用户
function addUser() {
    var username = $("#addInputName").val();
    var password = $("#addInputPassword").val();
    var role = $("#addSelectRole").val();
    var data = {
        "username":username,
        "password":hex_md5(password),
        "r_id":role
    }
    //alert(username);
    $.ajax({
        type: "POST",
        url: "/ajax_add_user",
        dataType: "json",
        data: data,
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                window.location.href = "userManage";
            }else if(msg.data.judge == "-1"){
                $(".add-wrong-tip").css("display","block");
                $(".add-wrong-tip").text("请输入用户名！")
            }else if(msg.data.judge == "-2"){
                $(".add-wrong-tip").css("display","block");
                $(".add-wrong-tip").text("请输入密码！")
            }else if(msg.data.judge == "-3"){
                $(".add-wrong-tip").css("display","block");
                $(".add-wrong-tip").text("该用户名已存在！");
            }else if(msg.data.judge == "-9"){
                $(".add-wrong-tip").css("display","block");
            }
        }
    });
}

var userPassword;
var userId;
var userName;

//根据uid获取用户信息，并显示在修改用户模态框上
function getUserInfo(uid) {
    userId = uid;
    $.ajax({
        type: "POST",
        url: "/ajax_get_user_info",
        dataType: "json",
        data: {
            "uid":uid
        },
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                var user= msg.data.user;
                $("#editInputName").val(user.username);
                $("#editSelectRole").val(user.r_id);
                userPassword = user.password;
                userName = user.username;
            }else{
                $(".edit-wrong-tip").css("display","block");
            }
        }
    });
}

//修改用户信息
function editUser() {
    var username = $("#editInputName").val();
    var radio = $("input:radio:checked").val();
    var password = userPassword;
    //判断username有无变更 默认为变了
    var judge = true;
    if(username == userName){
        judge = false;
    }
    if(radio=="yes"){
        password = hex_md5("123456");
    }
    var role = $("#editSelectRole").val();
    var data = {
        "uid":userId,
        "username":username,
        "password":password,
        "r_id":role,
        "judge":judge
    }
    //alert(username);
    $.ajax({
        type: "POST",
        url: "/ajax_edit_user",
        dataType: "json",
        data: data,
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                window.location.href = "userManage";
            }else if(msg.data.judge == "-1"){
                $(".edit-wrong-tip").css("display","block");
                $(".edit-wrong-tip").text("请输入用户名！")
            }else if(msg.data.judge == "-2"){
                $(".edit-wrong-tip").css("display","block");
                $(".edit-wrong-tip").text("该用户名已存在！");
            }else if(msg.data.judge == "-9"){
                $(".edit-wrong-tip").css("display","block");
                $(".edit-wrong-tip").text("修改失败！")
            }
        }
    });
}

//获取用户点击要删除的id号
function getUserId(uid){
    userId = uid;
}

//删除用户
function deleteUser() {
    $.ajax({
        type: "POST",
        url: "/ajax_delete_user",
        dataType: "json",
        data: {
            "uid":userId
        },
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                window.location.href = "userManage";
            }else if(msg.data.judge == "-1"){
                $(".delete-wrong-tip").css("display","block");
            }else if(msg.data.judge == "-9"){
                $(".edit-wrong-tip").css("display","block");
                $(".edit-wrong-tip").text("删除失败！")
            }
        }
    });
}