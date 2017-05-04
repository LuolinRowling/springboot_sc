<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/30
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--引入头文件-->
<jsp:include page="header.jsp">
    <jsp:param  name="title" value="设备信息" />
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
                设备信息
                <small>对基础设备进行增加、删除、修改</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-gears"></i> 设备管理</a></li>
                <li>编辑设备</li>
                <li>设备信息</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Custom Tabs -->
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active" id="tabComputer"><a href="#tab_1" data-toggle="tab">电脑</a></li>
                            <li id="tabCamera"><a href="#tab_2" data-toggle="tab">摄像头</a></li>
                            <li id="tabProjector"><a href="#tab_3" data-toggle="tab">投影仪</a></li>
                            <li id="tabRaspberry"><a href="#tab_4" data-toggle="tab">树莓派</a></li>
                            <li id="tabSingleChip"><a href="#tab_5" data-toggle="tab">单片机</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="tab_1">
                                <table class="table table-bordered table-hover table-page">
                                    <thead>
                                    <tr>
                                        <th>编号</th>
                                        <th>型号</th>
                                        <th>操作系统</th>
                                        <th>内存</th>
                                        <th>硬盘</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${computerTypelist}" var="computer">
                                            <tr>
                                                <td>${computer.computerTypeId}</td>
                                                <td>${computer.computerTypeName}</td>
                                                <td>${computer.operatingSystem}</td>
                                                <td>${computer.memorySize}G</td>
                                                <td>${computer.diskSize}G</td>
                                                <td>
                                                    <span class="label label-success cursor-hand" data-toggle="modal" data-target="#computerModal" onclick="getComputerInfo(${computer.computerTypeId})"> 修改 </span>
                                                    &nbsp;
                                                    <span class="label label-danger cursor-hand" data-toggle="modal" data-target="#deleteModal" onclick="getDeviceId(${computer.computerTypeId},'computer')"> 删除 </span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>编号</th>
                                        <th>型号</th>
                                        <th>操作系统</th>
                                        <th>内存</th>
                                        <th>硬盘</th>
                                        <th>操作</th>
                                    </tr>
                                    </tfoot>
                                </table>
                                <button class="btn btn-warning btn-flat" data-toggle="modal" data-target="#computerModal" onclick="resetModal()">添加电脑</button>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="tab_2">
                                <table id="table2" class="table table-bordered table-hover table-page">
                                    <thead>
                                    <tr>
                                        <th>编号</th>
                                        <th>型号</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${cameraTypelist}" var="camera">
                                            <tr>
                                                <td>${camera.cameraTypeId}</td>
                                                <td>${camera.cameraTypeName}</td>
                                                <td>
                                                    <span class="label label-success cursor-hand" data-toggle="modal" data-target="#cameraModal" onclick="getCameraInfo(${camera.cameraTypeId})"> 修改 </span>
                                                    &nbsp;
                                                    <span class="label label-danger cursor-hand" data-toggle="modal" data-target="#deleteModal" onclick="getDeviceId(${camera.cameraTypeId},'camera')"> 删除 </span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>编号</th>
                                        <th>型号</th>
                                        <th>操作</th>
                                    </tr>
                                    </tfoot>
                                </table>
                                <button class="btn btn-warning btn-flat" data-toggle="modal" data-target="#cameraModal" onclick="resetCameraModal()">添加摄像头</button>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="tab_3">
                                <table id="table3" class="table table-bordered table-hover table-page">
                                    <thead>
                                    <tr>
                                        <th>编号</th>
                                        <th>型号</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${projectorTypelist}" var="projector">
                                            <tr>
                                                <td>${projector.projectorTypeId}</td>
                                                <td>${projector.projectorTypeName}</td>
                                                <td>
                                                    <span class="label label-success cursor-hand" data-toggle="modal" data-target="#projectorModal" onclick="getProjectorInfo(${projector.projectorTypeId})"> 修改 </span>
                                                    &nbsp;
                                                    <span class="label label-danger cursor-hand" data-toggle="modal" data-target="#deleteModal" onclick="getDeviceId(${projector.projectorTypeId},'projector')"> 删除 </span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>编号</th>
                                        <th>型号</th>
                                        <th>操作</th>
                                    </tr>
                                    </tfoot>
                                </table>
                                <button class="btn btn-warning btn-flat" data-toggle="modal" data-target="#projectorModal" onclick="resetProjectorModal()">添加投影仪</button>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="tab_4">
                                <table id="table4" class="table table-bordered table-hover table-page">
                                    <thead>
                                    <tr>
                                        <th>编号</th>
                                        <th>型号</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${raspberryTypelist}" var="raspberry">
                                            <tr>
                                                <td>${raspberry.raspberryTypeId}</td>
                                                <td>${raspberry.raspberryTypeName}</td>
                                                <td>
                                                    <span class="label label-success cursor-hand" data-toggle="modal" data-target="#raspberryModal" onclick="getRaspBerryInfo(${raspberry.raspberryTypeId})"> 修改 </span>
                                                    &nbsp;
                                                    <span class="label label-danger cursor-hand" data-toggle="modal" data-target="#deleteModal" onclick="getDeviceId(${raspberry.raspberryTypeId},'raspberry')" > 删除 </span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>编号</th>
                                        <th>型号</th>
                                        <th>操作</th>
                                    </tr>
                                    </tfoot>
                                </table>
                                <button class="btn btn-warning btn-flat" data-toggle="modal" data-target="#raspberryModal" onclick="resetRaspberryModal()">添加树莓派</button>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="tab_5">
                                <table id="table5" class="table table-bordered table-hover table-page">
                                    <thead>
                                    <tr>
                                        <th>编号</th>
                                        <th>型号</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${singlechipTypelist}" var="singlechip">
                                            <tr>
                                                <td>${singlechip.singlechipTypeId}</td>
                                                <td>${singlechip.singlechipTypeName}</td>
                                                <td>
                                                    <span class="label label-success cursor-hand" data-toggle="modal" data-target="#singlechipModal" onclick="getSingleChipInfo(${singlechip.singlechipTypeId})"> 修改 </span>
                                                    &nbsp;
                                                    <span class="label label-danger cursor-hand" data-toggle="modal" data-target="#deleteModal" onclick="getDeviceId(${singlechip.singlechipTypeId},'singlechip')"> 删除 </span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>编号</th>
                                        <th>型号</th>
                                        <th>操作</th>
                                    </tr>
                                    </tfoot>
                                </table>
                                <button class="btn btn-warning btn-flat" data-toggle="modal" data-target="#singlechipModal" onclick="resetSingleChipModal()">添加单片机</button>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
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
<!-- computerModal -->
<div class="modal fade" id="computerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">电脑信息</h4>
            </div>
            <div class="modal-body">
                <div class="modal-width">
                    <!-- form start -->
                    <form class="form-horizontal">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="inputComputerName" class="col-sm-2 control-label">型号</label>

                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="inputComputerName" placeholder="电脑型号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputOS" class="col-sm-2 control-label">系统</label>

                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="inputOS" placeholder="电脑操作系统">
                                </div>
                            </div>
                            <div class="form-group">

                                <label for="inputMemory" class="col-sm-2 control-label">内存</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="inputMemory" placeholder="电脑内存">
                                        <span class="input-group-addon">G</span>
                                    </div>
                                </div>

                                <label for="inputDisk" class="col-sm-2 control-label">硬盘</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="inputDisk" placeholder="电脑硬盘">
                                        <span class="input-group-addon">G</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <p class="label label-danger device-wrong-tip" style="display:none;margin-top: 5px;float: left;" >操作失败！</p>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary computer-btn" onclick="addComputer()">提交</button>
            </div>
        </div>
    </div>
</div>

<!-- cameraModal -->
<div class="modal fade" id="cameraModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">摄像头</h4>
            </div>
            <div class="modal-body">
                <div class="modal-width">
                    <!-- form start -->
                    <form class="form-horizontal">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="cameraType" class="col-sm-2 control-label">型号</label>

                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="cameraType" placeholder="摄像头型号">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <p class="label label-danger device-wrong-tip" style="display:none;margin-top: 5px;float: left;" >操作失败！</p>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary camera-btn" onclick="addCamera()">提交</button>
            </div>
        </div>
    </div>
</div>

<!-- projectorModal -->
<div class="modal fade" id="projectorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">投影仪</h4>
            </div>
            <div class="modal-body">
                <div class="modal-width">
                    <!-- form start -->
                    <form class="form-horizontal">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="projectorType" class="col-sm-2 control-label">型号</label>

                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="projectorType" placeholder="投影仪型号">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <p class="label label-danger device-wrong-tip" style="display:none;margin-top: 5px;float: left;" >操作失败！</p>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary projector-btn" onclick="addProjector()">提交</button>
            </div>
        </div>
    </div>
</div>

<!-- raspberryModal -->
<div class="modal fade" id="raspberryModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">树莓派</h4>
            </div>
            <div class="modal-body">
                <div class="modal-width">
                    <!-- form start -->
                    <form class="form-horizontal">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="raspberryType" class="col-sm-2 control-label">型号</label>

                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="raspberryType" placeholder="树莓派型号">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <p class="label label-danger device-wrong-tip" style="display:none;margin-top: 5px;float: left;" >操作失败！</p>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary raspberry-btn" onclick="addRaspBerry()">提交</button>
            </div>
        </div>
    </div>
</div>

<!-- singlechipModal -->
<div class="modal fade" id="singlechipModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">单片机</h4>
            </div>
            <div class="modal-body">
                <div class="modal-width">
                    <!-- form start -->
                    <form class="form-horizontal">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="singlechipType" class="col-sm-2 control-label">型号</label>

                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="singlechipType" placeholder="单片机型号">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <p class="label label-danger device-wrong-tip" style="display:none;margin-top: 5px;float: left;" >操作失败！</p>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary singlechip-btn" onclick="addSingleChip()">提交</button>
            </div>
        </div>
    </div>
</div>

<!--模态框删除-->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="deleteModalLabel">删除设备</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal" role="form">

                    <div class="fcol-sm-5 ">
                        <p>确定要删除该设备吗？</p>

                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <p class="label label-danger delete-wrong-tip" style="display:none;margin-top: 5px;float: left;" >设备不存在！</p>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" class="btn btn-primary" onclick="deleteDevice()">删除</button>
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
        $('.table-page').DataTable({
            "paging": true,
            "lengthChange": false,
            "searching": false,
            "ordering": true,
            "info": true,
            "autoWidth": false
        });

    });
</script>

<script src="./xset/deviceInfo.js"></script>
<!-- Optionally, you can add Slimscroll and FastClick plugins.
Both of these plugins are recommended to enhance the
user experience. Slimscroll is required when using the
fixed layout. -->
</body>
</html>