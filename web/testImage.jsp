<%--
  Created by IntelliJ IDEA.
  User: Pasuth
  Date: 30/4/2558
  Time: 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>File Upload</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form method="POST" action="/khaycake/picture" enctype="multipart/form-data" >
  File:
  <input type="file" name="pictures" /> <br/>
  Destination:
  <input type="text" value="/tmp" name="destination"/>
  </br>
  <input type="submit" value="Upload" name="upload" id="upload" />
</form>
</body>
</html>
