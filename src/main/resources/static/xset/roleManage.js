/**
 * Created by admin on 2017/1/14.
 */


var setting = {
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    }
};

var zNodes;
var editNodes;

var code;

function setCheck() {
    var zTree = $.fn.zTree.getZTreeObj("roleTree"),
    type = { "Y" : "ps", "N" : "ps" };
    zTree.setting.check.chkboxType = type;
    showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
}
function showCode(str) {
    if (!code) code = $("#code");
    code.empty();
    code.append("<li>"+str+"</li>");
}

function initTree(from){
    var treeNodes;
    if(from == 'edit'){
        treeNodes = editNodes;
    }else{
        treeNodes = zNodes;
    }
    $.fn.zTree.init($("#roleTree"), setting, treeNodes);
    setCheck();
    $("#py").bind("change", setCheck);
    $("#sy").bind("change", setCheck);
    $("#pn").bind("change", setCheck);
    $("#sn").bind("change", setCheck);
}

//获取权限树
function getPermissionTree(from,role) {
    if(from == "edit"){
        $("#inputRoleName").val(role['r_name']);
        $.ajax({
            type: "POST",
            url: "/ajax_get_permission_tree",
            dataType: "json",
            success: function(msg){
                var permissionList = msg.data.permissionlist;

                editNodes = new Array();
                for (var i=0;i<permissionList.length;i++){

                    var node=new Object();
                    node.id = permissionList[i]['id'];
                    node.pId = permissionList[i]['parent_id'];
                    node.open = true;
                    node.name =permissionList[i]['name'];
                    node.icon = permissionList[i]['icon'];

                    //判断role的长度，看来自于add还是edit
                    var p_ids = role['p_ids'];
                    console.log(p_ids);
                    if(p_ids.length>0){
                        if($.inArray(node.id,p_ids)!=-1){
                            console.log(node.id);
                            node.checked = true;
                        }
                    }
                    editNodes.push(node);
                }
                initTree(from);
            }
        });
    }else{
        //修改成添加模态框模式
        changeModalAdd();
        if(zNodes==null){
            $.ajax({
                type: "POST",
                url: "/ajax_get_permission_tree",
                dataType: "json",
                success: function(msg){
                    var permissionList = msg.data.permissionlist;

                    zNodes = new Array();
                    for (var i=0;i<permissionList.length;i++){

                        var node=new Object();
                        node.id = permissionList[i]['id'];
                        node.pId = permissionList[i]['parent_id'];
                        node.open = true;
                        node.name =permissionList[i]['name'];
                        node.icon = permissionList[i]['icon'];

                        zNodes.push(node);
                    }
                    initTree(from);
                }
            });
        }else{
            initTree(from);
        }
    }


}

//获取所有选中节点的值
function GetPermissionCheckedAll() {
    var treeObj = $.fn.zTree.getZTreeObj("roleTree");
    var nodes = treeObj.getCheckedNodes(true);
    var permissionNodes = new Array();
    for (var i = 0; i < nodes.length; i++) {
        permissionNodes.push(nodes[i].id);
    }
    console.log(permissionNodes);
    return permissionNodes;
}

function addRole() {
    var roleName = $("#inputRoleName").val();
    var permissionNodes = GetPermissionCheckedAll();
    var data = {
        "r_name" : roleName,
        "p_ids" : permissionNodes
    };
    $.ajax({
        type: "POST",
        url: "/ajax_add_role",
        dataType: "json",
        data: data,
        traditional:true,
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                window.location.href = "roleManage";
            }else if(msg.data.judge == "-1"){
                $(".add-wrong-tip").css("display","block");
                $(".add-wrong-tip").text("请输入角色名！")
            }else if(msg.data.judge == "-9"){
                $(".add-wrong-tip").css("display","block");
                $(".add-wrong-tip").text("添加失败！")
            }
        }
    });
}
//获取被修改、删除的角色ID
var r_id;
function getRoleId(rId,from) {
    r_id = rId;
    //如过点击的是修改按钮
    if(from == 'edit'){
        changeModalEdit();
        //获取该角色的信息
        getRoleMessage(rId);
    }
}

function deleteRole() {
    $.ajax({
        type: "POST",
        url: "/ajax_delete_role",
        dataType: "json",
        data: {
            "rid":r_id
        },
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                window.location.href = "roleManage";
            }else if(msg.data.judge == "-1"){
                $(".delete-wrong-tip").css("display","block");
            }else if(msg.data.judge == "-9"){
                $(".delete-wrong-tip").css("display","block");
                $(".delete-wrong-tip").text("删除失败！")
            }
        }
    });
}

//添加修改共用模态框
function changeModalEdit() {
    $("#addModalLabel").html("修改");
    $('#roleModalBtn').attr('onclick','editRole()');
    $('#roleModalBtn').html("修改");
}

//添加修改共用模态框
function changeModalAdd() {
    $("#addModalLabel").html("添加");
    $('#roleModalBtn').attr('onclick','addRole()');
    $('#roleModalBtn').html("添加");
    $("#inputRoleName").val('');
}

//根据ID获取被修改的角色信息
function getRoleMessage(rid) {
    $.ajax({
        type: "POST",
        url: "/ajax_get_role_info",
        dataType: "json",
        data: {
            "rid":rid
        },
        success: function(msg){
            var role = "";
            if(msg.data.judge == "0"){
                role = msg.data.role;
                console.log(role);

                //根据获取的角色信息 获取角色树
                getPermissionTree("edit",role);
            }else if(msg.data.judge == "-1"){
                $(".add-wrong-tip").css("display","block");
                $(".add-wrong-tip").text("角色不存在！")
            }
        }
    });
}

function editRole() {
    var roleName = $("#inputRoleName").val();
    var permissionNodes = GetPermissionCheckedAll();
    var data = {
        "rid":r_id,
        "r_name" : roleName,
        "p_ids" : permissionNodes
    };
    $.ajax({
        type: "POST",
        url: "/ajax_edit_role",
        dataType: "json",
        data: data,
        traditional:true,
        success: function(msg){
            console.log(msg.data);
            if(msg.data.judge == "0"){
                window.location.href = "roleManage";
            }else if(msg.data.judge == "-1"){
                $(".add-wrong-tip").css("display","block");
                $(".add-wrong-tip").text("请输入用户名！")
            }else if(msg.data.judge == "-2"){
                $(".add-wrong-tip").css("display","block");
                $(".add-wrong-tip").text("该用户名已存在！");
            }else if(msg.data.judge == "-9"){
                $(".add-wrong-tip").css("display","block");
                $(".add-wrong-tip").text("修改失败！")
            }
        }
    });
}