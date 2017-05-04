<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/2/19
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--引入头文件-->
<jsp:include page="header.jsp">
    <jsp:param  name="title" value="消息管理" />
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
                消息管理
                <small>查看当天所有消息信息</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-gears"></i> 系统管理</a></li>
                <li class="active">消息管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">消息列表</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="example2" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>地点</th>
                                    <th>时间</th>
                                    <th>内容</th>
                                    <th>来源</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${messageListCenter}" var="message">
                                    <tr>
                                        <td>${message.buildClass}</td>
                                        <td>${message.nowTime}</td>
                                        <td>${message.message}</td>
                                        <td>
                                            <c:if test="${message.tab=='video'}">视频</c:if>
                                            <c:if test="${message.tab=='device'}">设备</c:if>
                                            <c:if test="${message.tab=='all'}">树莓派</c:if>
                                        </td>
                                        <%--<td>--%>
                                            <%--<c:if test="${message.judge=='success'}"><span class="label label-success"> 成功 </span></c:if>--%>
                                            <%--<c:if test="${message.judge=='offline'}"><span class="label label-success"> 成功 </span></c:if>--%>
                                            <%--<c:if test="${message.judge=='timeout'}"><span class="label label-warning"> 超时 </span></c:if>--%>
                                            <%--<c:if test="${message.judge=='fail'}"><span class="label label-danger"> 失败 </span></c:if>--%>
                                        <%--</td>--%>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>地点</th>
                                    <th>时间</th>
                                    <th>内容</th>
                                    <th>来源</th>
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
<script src="./xset/websocket2.js"></script>
<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
</body>
</html>
