<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/1/1
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--引入头文件-->
<jsp:include page="header.jsp">
    <jsp:param  name="title" value="添加教室" />
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
                添加教室
                <small>添加教室信息，并分配设备</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-gears"></i> 设备管理</a></li>
                <li>编辑设备</li><li>分配设备</li><li>添加教室</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">


                    <!-- Horizontal Form -->
                    <div class="box box-info">

                        <!-- /.box-header -->
                        <div class="box-header with-border">
                            <h3 class="box-title">教室信息</h3>
                        </div>
                        <!-- form start -->
                        <form class="form-horizontal">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="addBuilding" class="col-sm-2 control-label">教学楼</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="addBuilding">
                                            <c:forEach items="${buildinglist}" var="building">
                                                <option value="${building.id}">${building.buildingNum}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <label for="addClassroom" class="col-sm-1 control-label">教室号</label>

                                    <div class="col-sm-3">
                                        <select class="form-control" id="addClassroom">
                                            <c:forEach items="${classroomlist}" var="classroom">
                                                <option value="${classroom.id}">${classroom.classroomNum}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">电脑信息</label>

                                    <div class="col-sm-7">
                                        <select class="form-control" id="addComputerClassroom">
                                            <c:forEach items="${computerTypelist}" var="computer">
                                                <option value="${computer.computerTypeId}">
                                                    型号：${computer.computerTypeName}&nbsp;&nbsp;
                                                    内存：${computer.memorySize}G&nbsp;&nbsp;
                                                    硬盘：${computer.diskSize}G&nbsp;&nbsp;
                                                    操作系统：${computer.operatingSystem}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">摄像头信息</label>

                                    <div class="col-sm-7">
                                        <select class="form-control" id="addCameraClassroom">
                                            <c:forEach items="${cameraTypelist}" var="camera">
                                                <option value="${camera.cameraTypeId}">${camera.cameraTypeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">投影仪信息</label>

                                    <div class="col-sm-7">
                                        <select class="form-control" id="addProjectorClassroom">
                                            <c:forEach items="${projectorTypelist}" var="projector">
                                                <option value="${projector.projectorTypeId}">${projector.projectorTypeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">树莓派信息</label>

                                    <div class="col-sm-3">
                                        <select class="form-control" id="addRaspberryClassroom">
                                            <c:forEach items="${raspberryTypelist}" var="raspberry">
                                                <option value="${raspberry.raspberryTypeId}">${raspberry.raspberryTypeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label">序列号</label>

                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" id="addInputRaspberry" placeholder="树莓派序列号">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">单片机信息</label>

                                    <div class="col-sm-7">
                                        <select class="form-control" id="addSinglechipClassroom">
                                            <c:forEach items="${singlechipTypelist}" var="singlechip">
                                                <option value="${singlechip.singlechipTypeId}">${singlechip.singlechipTypeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <p class="label label-danger classroom-wrong-tip" style="display:none;margin-top: 5px;float: left;" >操作失败！</p>
                                <button type="button" class="btn btn-info pull-right" onclick="addClassroomDevice()">添加教室</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
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
<jsp:include page="footer.jsp"></jsp:include>
<!-- REQUIRED JS SCRIPTS -->


<script src="./xset/assignDevice.js"></script>
<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
</body>
</html>