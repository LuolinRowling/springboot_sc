<%--
  Created by IntelliJ IDEA.
  User: jiangdongyu
  Date: 2016/11/28
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title></title>
</head>
<body>
登录失败!
${message}
<br>
<a href="<%=path%>/login.jsp">返回</a>
</body>
</html>
