<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xixi
  Date: 2016/12/3
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--引入头文件-->
<jsp:include page="header.jsp">
    <jsp:param  name="title" value="权限管理" />
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
                角色管理
                <small>对角色信息进行增加、删除、修改</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-gears"></i> 系统管理</a></li>
                <li>权限管理</li>
                <li>角色管理</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">角色列表</h3>
                            <button class="btn btn-warning btn-flat pull-right" data-toggle="modal" data-target="#addModal"  onclick="getPermissionTree('add','')">添加角色</button>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="example2" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>角色</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${rolelist}" var="role">
                                    <tr>
                                        <td>${role.r_id}</td>
                                        <td>${role.r_name}</td>
                                        <td>
                                            <span class="label label-success cursor-hand" data-toggle="modal" data-target="#addModal" onclick="getRoleId(${role.r_id},'edit')"> 修改 </span>
                                            &nbsp;
                                            <span class="label label-danger cursor-hand" data-toggle="modal" data-target="#deleteModal" onclick="getRoleId(${role.r_id},'delete')"> 删除 </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>编号</th>
                                    <th>角色</th>
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

<!-- addModal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addModalLabel">添加角色</h4>
            </div>
            <div class="modal-body">
                <div class="modal-width">
                    <!-- form start -->
                    <form class="form-horizontal">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="inputRoleName" class="col-sm-2 control-label">角色</label>

                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="inputRoleName" placeholder="角色">
                                </div>
                            </div>
                            <!-- select -->
                            <div class="form-group">
                                <label class="col-sm-2 control-label">权限</label>
                                <div class="col-sm-10">
                                    <div class="zTreeDemoBackground left">
                                        <ul id="roleTree" class="ztree"></ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <p class="label label-danger add-wrong-tip" style="display:none;margin-top: 5px;float: left;" >添加失败！</p>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="roleModalBtn" onclick="addRole()">添加</button>
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
                <h4 class="modal-title" id="deleteModalLabel">删除角色</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal" role="form">

                    <div class="fcol-sm-5 ">
                        <p>确定要删除本角色吗？</p>

                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <p class="label label-danger delete-wrong-tip" style="display:none;margin-top: 5px;float: left;" >角色不存在！</p>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" class="btn btn-primary" onclick="deleteRole()">删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- REQUIRED JS SCRIPTS -->
<jsp:include page="footer.jsp"></jsp:include>

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
</script>
<script src="./xset/roleManage.js"></script>
<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
</body>
</html>
