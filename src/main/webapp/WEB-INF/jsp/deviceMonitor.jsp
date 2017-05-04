<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/1/1
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--引入头文件-->
<jsp:include page="header.jsp">
    <jsp:param  name="title" value="远程设备管理" />
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
                远程设备管理
                <small>控制远程多种设备的开关并监控
                </small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-gears"></i> 设备管理</a></li>
                <li>远程设备开关</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">远程列表 </h3>
                            <a class="btn btn-warning btn-flat pull-right" onclick="editAllDevice('open')">一键开启</a>
                            <a class="btn btn-primary btn-flat pull-right" style="margin-right: 10px" onclick="editAllDevice('close')">一键关闭</a>
                            <button type="button" class="btn btn-success btn-xs col">开启</button>当前设备离线&nbsp;
                            <button type="button" class="btn btn-danger btn-xs col">关闭</button>当前设备在线&nbsp;
                            <button type="button" class="btn btn-warning btn-xs col">异常</button>当前设备异常&nbsp;
                            <br/><br/>不可点击状态
                            <button type="button" class="btn btn-success btn-xs col" disabled>开启</button>&nbsp;
                            <button type="button" class="btn btn-danger btn-xs col" disabled>关闭</button>&nbsp;
                            <button type="button" class="btn btn-warning btn-xs col" disabled>异常</button>&nbsp;
                            当树莓派关闭或异常，所有操作无法进行；当单片机异常，无法操作电脑和投影仪

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
                                    <c:forEach items="${deviceStatuslist}" var="status">
                                        <tr <c:if test='${status.raspberryStatus==0||status.raspberryStatus==2}'>class="device-disabled" </c:if> id="trDevice${status.raspberryCode}">
                                            <td>
                                                <div class="checkbox">
                                                    <label>
                                                        <input type="checkbox" class="device-checkbox"> ${status.id}
                                                    </label>
                                                </div>
                                            </td>
                                            <td>${status.buildingNum} ${status.classroomNum}</td>
                                            <td <c:if test='${status.singlechipStatus==0||status.singlechipStatus==2}'>class="singlechip-disabled" </c:if>>
                                                <c:if test="${status.computerStatus==0}">
                                                    <button type="button" class="btn btn-success btn-xs col" onclick="operateDevice(${status.id},'computer','open')">开启</button>
                                                </c:if>
                                                <c:if test="${status.computerStatus==1}">
                                                    <button type="button" class="btn btn-danger btn-xs col" onclick="operateDevice(${status.id},'computer','close')">关闭</button>
                                                </c:if>
                                                <c:if test="${status.computerStatus==2}">
                                                    <button type="button" class="btn btn-warning btn-xs col" onclick="operateDevice(${status.id},'computer','open')">异常</button>
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${status.cameraStatus==0}">
                                                    <button type="button" class="btn btn-success btn-xs col" onclick="operateDevice(${status.id},'camera','open')">开启</button>
                                                </c:if>
                                                <c:if test="${status.cameraStatus==1}">
                                                    <button type="button" class="btn btn-danger btn-xs col" onclick="operateDevice(${status.id},'camera','close')">关闭</button>
                                                </c:if>
                                                <c:if test="${status.cameraStatus==2}">
                                                    <button type="button" class="btn btn-warning btn-xs col" onclick="operateDevice(${status.id},'camera','open')">异常</button>
                                                </c:if>
                                            </td>
                                            <td <c:if test='${status.singlechipStatus==0||status.singlechipStatus==2}'>class="singlechip-disabled" </c:if>>
                                                <c:if test="${status.projectorStatus==0}">
                                                    <button type="button" class="btn btn-success btn-xs col" onclick="operateDevice(${status.id},'projector','open')">开启</button>
                                                </c:if>
                                                <c:if test="${status.projectorStatus==1}">
                                                    <button type="button" class="btn btn-danger btn-xs col" onclick="operateDevice(${status.id},'projector','close')">关闭</button>
                                                </c:if>
                                                <c:if test="${status.projectorStatus==2}">
                                                    <button type="button" class="btn btn-warning btn-xs col" onclick="operateDevice(${status.id},'projector','open')">异常</button>
                                                </c:if>
                                            </td>
                                            <td>
                                                <c:if test="${status.raspberryStatus==0}">离线</c:if>
                                                <c:if test="${status.raspberryStatus==1}">在线</c:if>
                                                <c:if test="${status.raspberryStatus==2}">异常</c:if>
                                            </td>
                                            <td>
                                                <c:if test="${status.singlechipStatus==0}">离线</c:if>
                                                <c:if test="${status.singlechipStatus==1}">在线</c:if>
                                                <c:if test="${status.singlechipStatus==2}">异常</c:if>
                                            </td>
                                            <td>
                                                <button type="button" class="btn btn-success btn-xs col" onclick="operateDevice(${status.id},'','open')">开启全部</button>&nbsp;
                                                <button type="button" class="btn btn-danger btn-xs col" onclick="operateDevice(${status.id},'','close')">关闭全部</button>
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
<jsp:include page="footer.jsp"></jsp:include>
<!-- REQUIRED JS SCRIPTS -->
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
            "autoWidth": false,
            "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                /* Append the grade to the default row class name */
                changeButtonDisable();
                //return nRow;
            }
        });
    });


</script>
<script src="./dist/js/jquery.toaster.js"></script>
<script src="./xset/deviceMonitor.js"></script>
<script src="./xset/judgeMessage.js"></script>
<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
</body>
</html>