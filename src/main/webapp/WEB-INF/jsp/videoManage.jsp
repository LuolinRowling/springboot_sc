<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/1/3
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--引入头文件-->
<jsp:include page="header.jsp">
    <jsp:param  name="title" value="视频管理" />
</jsp:include>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!--引入顶部导航栏-->
    <jsp:include page="navigation.jsp"></jsp:include>

    <!--引入侧边导航栏-->
    <jsp:include page="sidebar.jsp"></jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                视频管理
                <small>控制视频流的调度，监控教室教学视频
                </small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-gears"></i> 视频管理</a></li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">远程设备列表 </h3>
                            <a class="btn btn-warning btn-flat pull-right" data-toggle="modal" data-target="#pullModal" onclick="getBuildClass(0,this,'multiple')">多教室拉流</a>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="example2" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>教室</th>
                                    <th>状态</th>
                                    <th>推流</th>
                                    <th>拉流</th>
                                    <th>广播</th>
                                    <th>监控</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${raspberryStreamStatuslist}" var="status">
                                        <tr <c:if test='${status.raspberryStreamStatus==0||status.raspberryStreamStatus==2}'>class="device-disabled" </c:if> id="tr${status.raspberryCode}">
                                            <td>${status.id}</td>
                                            <td class="table-td">${status.buildingNum} ${status.classroomNum}</td>
                                            <td class="table-td stream-status">
                                                <c:if test="${status.raspberryStreamStatus==0}">离线</c:if>
                                                <c:if test="${status.raspberryStreamStatus==1}">在线（空闲）</c:if>
                                                <c:if test="${status.raspberryStreamStatus==2}">异常</c:if>
                                                <c:if test="${status.raspberryStreamStatus==3}">正在推流</c:if>
                                                <c:if test="${status.raspberryStreamStatus==4}">正在拉流</c:if>
                                                <c:if test="${status.raspberryStreamStatus==5}">正在广播</c:if>
                                            </td>
                                            <td class="table-td <c:if test='${status.raspberryStreamStatus==4||status.raspberryStreamStatus==5}'>operate-disabled</c:if>">
                                                <c:if test="${status.raspberryStreamStatus!=3}">
                                                    <button type="button" class="btn btn-success btn-xs col push-btn" onclick="operateStream(${status.id},'start_push',this)"> 开始推流 </button>
                                                </c:if>
                                                <c:if test="${status.raspberryStreamStatus==3}">
                                                    <button type="button" class="btn btn-success btn-xs col push-btn" onclick="operateStream(${status.id},'stop_push',this)"> 停止推流 </button>
                                                </c:if>
                                            </td>
                                            <td class="table-td <c:if test='${status.raspberryStreamStatus==3||status.raspberryStreamStatus==5}'>operate-disabled</c:if>">
                                                <c:if test="${status.raspberryStreamStatus!=4}">
                                                    <button type="button" class="btn btn-success btn-xs col pull-btn" data-toggle="modal" data-target="#pullModal" onclick="getBuildClass(${status.id},this,'single')"> 开始拉流 </button>
                                                </c:if>
                                                <c:if test="${status.raspberryStreamStatus==4}">
                                                    <button type="button" class="btn btn-success btn-xs col pull-btn" onclick="operateStream(${status.id},'stop_pull',this)"> 停止拉流 </button>
                                                </c:if>
                                            </td>
                                            <td class="table-td <c:if test='${status.raspberryStreamStatus==3||status.raspberryStreamStatus==4}'>operate-disabled</c:if>">
                                                <c:if test="${status.raspberryStreamStatus!=5}">
                                                    <button type="button" class="btn btn-warning btn-xs col broadcast-btn" onclick="operateStream(${status.id},'start_broadcast',this)"> 发布广播 </button>
                                                </c:if>
                                                <c:if test="${status.raspberryStreamStatus==5}">
                                                    <button type="button" class="btn btn-warning btn-xs col broadcast-btn" onclick="operateStream(${status.id},'stop_broadcast',this)"> 停止广播 </button>
                                                </c:if>
                                            </td>
                                            <td class="table-td">
                                                <a class="btn btn-primary btn-xs col" href="videoLive.jsp?id=${status.id}" target="_blank">播放视频</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>编号</th>
                                    <th>教室</th>
                                    <th>状态</th>
                                    <th>推流</th>
                                    <th>拉流</th>
                                    <th>广播</th>
                                    <th>监控</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <footer class="main-footer">
        <!-- To the right -->
        <div class="pull-right hidden-xs">
            回到顶部
        </div>
        <!-- Default to the left -->
        <strong>Copyright &copy; 2016 <a href="#">XIXI</a>.</strong> All rights reserved.
    </footer>

    <!-- Add the sidebar's background. This div must be placed
         immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- pullModal -->
<div class="modal fade" id="pullModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="pullModalLabel">选择拉流教室</h4>
            </div>
            <div class="modal-body">
                <div class="modal-width">
                    <!-- form start -->
                    <form class="form-horizontal">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="chooseBuilding" class="col-sm-2 control-label">教学楼</label>
                                <div class="col-sm-4">
                                    <select class="form-control" id="chooseBuilding">

                                    </select>
                                </div>

                                <label for="chooseClassroom" class="col-sm-2 control-label">教室号</label>

                                <div class="col-sm-4">
                                    <select class="form-control" id="chooseClassroom">

                                    </select>
                                </div>
                            </div>
                        </div>
                        <!-- select -->
                        <div class="form-group" id="buildClassSelect">
                            <label class="col-sm-2 control-label">多教室</label>
                            <div class="col-sm-10">
                                <div class="zTreeDemoBackground left">
                                    <ul id="buildClassTree" class="ztree"></ul>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="modalStartPullBtn" data-dismiss="modal" onclick="startPullOperate()">开始拉流</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
<!-- REQUIRED JS SCRIPTS -->
<!-- zTree script -->
<script type="text/javascript" src="./zTree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="./zTree/js/jquery.ztree.excheck.js"></script>

<!-- page script -->
<script>
    $(function () {
        $("#example1").DataTable();
        $('#example2').DataTable({
            "paging": true,
            "lengthChange": false,
            "searching": false,
            "ordering": true,
            "info": true,
            "autoWidth": false
        });
    });
    $(document).ready(function () {
        //树莓派异常或关闭  所有操作都不能进行
        $(".device-disabled").children("td").children("button").attr("disabled","true");
        $(".device-disabled").children("td").children("a").attr("disabled","true");
        //$(".device-disabled .device-checkbox").attr("disabled","true");
        //当一个按钮点击时  其他两个不能点击
        $(".operate-disabled").children("button").attr("disabled","true");
    });
</script>
<script src="./dist/js/jquery.toaster.js"></script>
<script src="./xset/videoManage.js"></script>
<script src="./xset/judgeMessage.js"></script>
<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
</body>
</html>