<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" />
<h1><fmt:message key="user.profile.title"/></h1>
<div>
    <p><fmt:message key="user.name"/>: ${user.name}</p>
    <p><fmt:message key="user.surname"/>: ${user.surname}</p>
    <p><fmt:message key="user.nickname"/>: ${user.nickname}</p>
    <p><fmt:message key="user.email"/>: ${user.email}</p>
</div>
<form action="${pageContext.request.contextPath}/auth?action=signout" method="post">
    <button type="submit"><fmt:message key="user.signout.button"/></button>
</form>

<a href="${pageContext.request.contextPath}/users"><fmt:message key="user.list.back"/></a>
</body>
</html>