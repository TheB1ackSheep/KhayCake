<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%--
  Created by IntelliJ IDEA.
  User: Falook Glico
  Date: 4/11/2015
  Time: 02:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    Context ctx = null;
    ctx = new InitialContext();
    String ds = (String) ctx.lookup("resource/uploadImageUrl");
%>

<%=ds%>
</body>
</html>
