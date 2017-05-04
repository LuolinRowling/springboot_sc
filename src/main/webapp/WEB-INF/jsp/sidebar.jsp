<%@ page import="com.pku.model.User" %><%--
  Created by IntelliJ IDEA.
  User: xixi
  Date: 2016/12/3
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User)request.getSession().getAttribute("user");
%>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- Sidebar user panel (optional) -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="../dist/img/user9-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p><%= user.getUsername() %></p>
                <!-- Status -->
                <a href="#"><i class="fa fa-circle text-success"></i> <%= user.getRole().getR_name() %></a>
            </div>
        </div>

        <!-- search form (Optional) -->
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="搜索...">
                <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
            </div>
        </form>
        <!-- /.search form -->

        <!-- Sidebar Menu -->
        <ul class="sidebar-menu">
            <li class="header">导航</li>
            <!-- Optionally, you can add icons to the links -->
            <li class="device-manage">
                <a href="#"><i class="fa fa-laptop"></i> <span>设备管理</span>
                    <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
                </a>
                <ul class="treeview-menu">
                    <li class="edit-device">
                        <a href="#"><i class="fa fa-circle-o"></i> 编辑设备</a>
                        <ul class="treeview-menu">
                            <li class="device-info"><a href="deviceInfo"><i class="fa fa-circle-o"></i> 设备信息</a></li>
                            <li class="assign-device"><a href="assignDevice"><i class="fa fa-circle-o"></i> 分配设备</a></li>

                        </ul>
                    </li>
                    <li class="device-monitor"><a href="deviceMonitor"><i class="fa fa-circle-o"></i> 远程设备管理</a></li>
                </ul>
            </li>
            <li class="video-manage">
                <a href="#"><i class="fa fa-video-camera"></i> <span>视频管理</span>
                    <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
                </a>
                <ul class="treeview-menu">
                    <li class="video-stream"><a href="videoManage"><i class="fa fa-circle-o"></i> 视频流调度</a></li>
                </ul>
            </li>
            <li class="treeview system-manage">
                <a href="#"><i class="fa fa-gears"></i> <span>系统管理</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i> 学校课表管理</a></li>
                    <li class="privilege-manage">
                        <a href="#">
                            <i class="fa fa-circle-o"></i> 权限管理
                        </a>
                        <ul class="treeview-menu">
                            <li class="user-manage"><a href="userManage"><i class="fa fa-circle-o"></i> 用户管理</a></li>
                            <li class="role-manage"><a href="roleManage"><i class="fa fa-circle-o"></i> 角色管理</a></li>
                        </ul>
                    </li>
                    <li><a href="#"><i class="fa fa-circle-o"></i> 日志管理</a></li>
                    <li class="message-manage"><a href="messageManage"><i class="fa fa-circle-o"></i> 消息管理</a></li>
                </ul>
            </li>
        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>
