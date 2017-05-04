<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/30
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--引入头文件-->
<jsp:include page="header.jsp">
    <jsp:param  name="title" value="分配设备" />
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
                分配设备
                <small>为教室分配设备</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-gears"></i> 设备管理</a></li>
                <li>编辑设备</li>
                <li>分配设备</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">教室列表</h3>
                            <a class="btn btn-warning btn-flat pull-right" href="addClassroom">添加教室</a>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="example2" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>教室</th>
                                    <th>电脑</th>
                                    <th>摄像头</th>
                                    <th>投影仪</th>
                                    <th>树莓派</th>
                                    <th>单片机</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${deviceInfolist}" var="device">
                                        <tr>
                                            <td>${device.id}</td>
                                            <td>${device.buildingNum} ${device.classroomNum}</td>
                                            <td>类型：${device.computerType.computerTypeName} <br/>
                                                内存：${device.computerType.memorySize}G  &nbsp; 硬盘：${device.computerType.diskSize}G<br/>
                                                操作系统：${device.computerType.operatingSystem}</td>
                                            <td>${device.cameraType.cameraTypeName}</td>
                                            <td>${device.projectorType.projectorTypeName}</td>
                                            <td>
                                                类型：${device.raspberryType.raspberryTypeName}<br/>
                                                序列号：${device.raspberryCode}
                                            </td>
                                            <td>${device.singlechipType.singlechipTypeName}</td>
                                            <td>
                                                <a class="label label-success cursor-hand" href="editClassroom?id=${device.id}"> 修改 </a>
                                                &nbsp;
                                                <span class="label label-danger cursor-hand" data-toggle="modal" data-target="#deleteModal" onclick="getClassroomId(${device.id})"> 删除 </span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>编号</th>
                                    <th>教室</th>
                                    <th>电脑</th>
                                    <th>摄像头</th>
                                    <th>投影仪</th>
                                    <th>树莓派</th>
                                    <th>单片机</th>
                                    <th>操作</th>
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

<!--模态框删除-->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="deleteModalLabel">删除教室</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal" role="form">

                    <div class="fcol-sm-5 ">
                        <p>确定要删除本教室吗？</p>

                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <p class="label label-danger delete-wrong-tip" style="display:none;margin-top: 5px;float: left;" >教室不存在！</p>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" class="btn btn-primary" onclick="deleteClassroom()">删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- REQUIRED JS SCRIPTS -->

<jsp:include page="footer.jsp"></jsp:include>
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
</script>
<script src="./xset/assignDevice.js"></script>
<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
</body>
</html>
