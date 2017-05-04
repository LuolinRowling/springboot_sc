<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/1
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp">
    <jsp:param  name="title" value="登录" />
</jsp:include>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="../../index2.html"><b>智慧教室</b></a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">登录</p>

        <div>
            <div class="form-group has-feedback">
                <input type="text" id="username" name="username" class="form-control" placeholder="用户名">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" id="password" name="password" class="form-control" placeholder="密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <a href="#">忘记密码</a>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="button" name="submit" class="btn btn-primary btn-block btn-flat" onclick="login()">登录</button>
                </div>
                <!-- /.col -->
            </div>
        </div>
        <!-- /.social-auth-links -->

        <p class="label label-danger wrong-tip" style="display:none;margin-top: 5px;" >用户名或密码错误！</p>
    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="./plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="./bootstrap/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="./plugins/iCheck/icheck.min.js"></script>
<script>
    $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>

<script src="./xset/md5.js"></script>
<script src="./xset/login.js"></script>
</body>
</html>

