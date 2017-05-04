/**
 * Created by admin on 2017/1/3.
 */
//start_push,stop_push,start_pull,stop_pull,start_broadcast,stop_broadcast
function operateStream(did,operate,obj) {
    //禁止同一行其他按钮的点击事件
    $(obj).parent('td').siblings('td').children('button').attr('disabled','true');
    $(obj).html('指令已发出，勿重复点击');
    //当操作是发布广播时，把除本行的其他按钮都禁用，因为发布广播，其他教室都自动变为正在拉流对的状态
    // if(operate == 'start_broadcast'){
    //     $(obj).parent('tr').siblings('tr').children("button").attr("disabled","true");
    // }
    $.ajax({
        type: "POST",
        url: "/ajax_edit_stream_status",
        dataType: "json",
        data: {
            "did":did,
            "operation":operate
        },
        success: function(msg){

        }
    });
}

//记录用户选择的拉流id和按钮
var pullId;
var pullBtn;

//获取正在推流的教学楼列表,以及排在第一个的building对应的classroomlist
function getBuildClass(did,obj,from){
    pullId = did;
    pullBtn = $(obj);
    $('#buildClassSelect').css('display','none');
    $('#modalStartPullBtn').attr('onclick','startPullOperate()');
    $.ajax({
        type: "POST",
        url: "/ajax_get_push_building",
        dataType: "json",
        success: function(msg){
            console.log(msg.data.buildinglist);
            $('#chooseBuilding').html("");
            $('#chooseClassroom').html("");
            var buildinglist = msg.data.buildinglist;
            for(var i=0;i<buildinglist.length;i++){
                $('#chooseBuilding').append('<option value="'+buildinglist[i]['buildingNum']+'">'+buildinglist[i]['buildingNum']+'</option>');
            }
            var classroomlist = msg.data.classroomlist;
            for(var i=0;i<classroomlist.length;i++){
                $('#chooseClassroom').append('<option value="'+classroomlist[i]['classroomNum']+'">'+classroomlist[i]['classroomNum']+'</option>');
            }

            if(from == 'multiple'){
                $('#modalStartPullBtn').attr('onclick','GetCheckedAll()');
                getMultiPullStreamTree();
            }
        }
    });
}

//当教学楼被选中，根据教学楼id获取classroomlist
$(document).ready(function(){
    $('#chooseBuilding').change(function(){
        var name=$(this).children('option:selected').val();
        console.log(name);
        changeClassroomByBuilding(name);
    });
});

//获取classroomlist
function changeClassroomByBuilding(name){
    $.ajax({
        type: "POST",
        url: "/ajax_get_classroom_by_building",
        dataType: "json",
        data: {
            "name":name
        },
        success: function(msg){
            console.log(msg.data.classroomlist);
            $('#chooseClassroom').html("");
            var classroomlist = msg.data.classroomlist;
            for(var i=0;i<classroomlist.length;i++){
                $('#chooseClassroom').append('<option value="'+classroomlist[i]['classroomNum']+'">'+classroomlist[i]['classroomNum']+'</option>');
            }
        }
    });
}

//发送start_pull消息
function startPullOperate() {
    //其他按钮禁用
    pullBtn.parent('td').siblings('td').children('button').attr('disabled','true');
    pullBtn.html('指令已发出，勿重复点击');

    var inputBuilding = $("#chooseBuilding option:selected").text();
    var inputClassroom = $("#chooseClassroom option:selected").text();
    var data = {
        "buildingNum":inputBuilding,
        "classroomNum":inputClassroom,
        "did":pullId
    }
    $.ajax({
        type: "POST",
        url: "/ajax_pull_stream_status",
        dataType: "json",
        data: data,
        success: function(){

        }
    });
}


/**
 * zTree
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
var code;

function setCheck() {
    var zTree = $.fn.zTree.getZTreeObj("buildClassTree"),
        type = { "Y" : "ps", "N" : "ps" };
    zTree.setting.check.chkboxType = type;
    showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
}
function showCode(str) {
    if (!code) code = $("#code");
    code.empty();
    code.append("<li>"+str+"</li>");
}

function getMultiPullStreamTree() {
    $.ajax({
        type: "POST",
        url: "/ajax_multi_pull_stream_tree",
        dataType: "json",
        success: function(msg){
            zNodes = new Array();
            //console.log(msg.data[0]['buildingNum']);
            var buildClassList = msg.data;
            var buildNum = "";
            var count = 0;
            var countChild = 1;
            //{ id:1, pId:0, name:"设备管理", open:true, icon:"../dist/img/computer.png"}
            for (var i=0;i<buildClassList.length;i++){
                var nodeHead=new Object();
                if(buildClassList[i]['buildingNum']!=buildNum){
                    count++;
                    nodeHead.id = count;
                    nodeHead.pId = 0;
                    nodeHead.name = buildClassList[i]['buildingNum'];
                    nodeHead.open = true;
                    nodeHead.icon = "../dist/img/roll.png";
                    zNodes.push(nodeHead);
                    var node=new Object();
                    node.id = count*10+countChild;
                    node.pId = count;
                    node.name = buildClassList[i]['classroomNum'];
                    node.icon = "../dist/img/roll.png";
                    zNodes.push(node);
                    countChild++;
                    buildNum = buildClassList[i]['buildingNum'];
                }else{
                    var node=new Object();
                    node.id = count*10+countChild;
                    node.pId = count;
                    node.name = buildClassList[i]['classroomNum'];
                    node.icon = "../dist/img/roll.png";
                    zNodes.push(node);
                    countChild++;
                }
            }
            $('#buildClassSelect').css('display','block');
            $.fn.zTree.init($("#buildClassTree"), setting, zNodes);
            setCheck();
            $("#py").bind("change", setCheck);
            $("#sy").bind("change", setCheck);
            $("#pn").bind("change", setCheck);
            $("#sn").bind("change", setCheck);
        }
    });
}


//获取所有选中节点的值
function GetCheckedAll() {
    var treeObj = $.fn.zTree.getZTreeObj("buildClassTree");
    var nodes = treeObj.getCheckedNodes(true);
    var buildClassNodes = new Array();
    var buildingNum = '';
    for (var i = 0; i < nodes.length; i++) {
        var buildClass = new Object();

        if(nodes[i].pId==null){
            //buildClass.buildingNum = nodes[i].name;
            buildingNum = nodes[i].name;
        }else{
            buildClass.buildingNum = buildingNum;
            buildClass.classroomNum = nodes[i].name;
            buildClassNodes.push(buildingNum+";"+nodes[i].name);
        }

        //msg += nodes[i].name+"--"+nodes[i].id+"--"+nodes[i].pId+"\n";

    }
    //console.log(buildClassNodes);
    startMultiplePullOperate(buildClassNodes);
}
//操作多个教室拉流
function startMultiplePullOperate(pullList) {
    console.log(pullList);
    var inputBuilding = $("#chooseBuilding option:selected").text();
    var inputClassroom = $("#chooseClassroom option:selected").text();

    var data = {
        "buildingNum":inputBuilding,
        "classroomNum":inputClassroom,
        "pullList":pullList
    };

    $.ajax({
        type: "POST",
        url: "/ajax_multiple_pull_stream_status",
        dataType: "json",
        data: data,
        success: function(){
            window.location.href = "videoManage";
        }
    });

}

function changeButtonDisabled() {
    //树莓派异常或关闭  所有操作都不能进行
    $(".device-disabled").children("td").children("button").attr("disabled","true");
    $(".device-disabled").children("td").children("a").attr("disabled","true");
    //$(".device-disabled .device-checkbox").attr("disabled","true");
    //当一个按钮点击时  其他两个不能点击
    $(".operate-disabled").children("button").attr("disabled","true");
}