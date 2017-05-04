<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/1/6
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--引入头文件-->
<jsp:include page="header.jsp">
    <jsp:param  name="title" value="视频播放" />
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
                视频播放
                <small>实时播放教室视频
                </small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-gears"></i> 视频管理</a></li>
                <li>视频播放</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">视频 </h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="videoBox">
                                <div id="palyerVideoBox"></div>
                            </div><!-- videoBox -->
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

<script type="text/javascript" src="./player/js/jwplayer/jwplayer.js"></script>
<script type="text/javascript" src="./xset/videoLive.js"></script>
<%--百度直播--%>
<%--<script type="text/javascript" src="./player/cyberplayer.js"></script>--%>
<%--<script type="text/javascript">--%>
    <%--var player = cyberplayer("playercontainer").setup({--%>
        <%--width: 854,--%>
        <%--height: 480,--%>
        <%--stretching: "uniform",--%>
        <%--file: "http://gkdp982dqqza47gihc1.exp.bcelive.com/lss-gm4k64ts8y7kevfi/live.m3u8",--%>
        <%--autostart: true,--%>
        <%--repeat: false,--%>
        <%--volume: 90,--%>
        <%--controls: true,--%>
        <%--ak: 'ba77daba024d4bbe91fda6da0d600352' // 公有云平台注册即可获得accessKey--%>
    <%--});--%>
<%--</script>--%>

<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
</body>
</html>