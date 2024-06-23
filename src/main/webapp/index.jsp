<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Home Page</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1>Welcome to WebDevProject</h1>
<p><a href="${pageContext.request.contextPath}/auth?action=signin">Sign In</a></p>
<p><a href="${pageContext.request.contextPath}/auth?action=signup">Sign Up</a></p>
</body>
</html>