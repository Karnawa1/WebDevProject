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
<h1><fmt:message key="profile.title"/></h1>
<div>
    <p><fmt:message key="profile.name"/>: ${user.name}</p>
    <p><fmt:message key="profile.surname"/>: ${user.surname}</p>
    <p><fmt:message key="profile.nickname"/>: ${user.nickname}</p>
    <p><fmt:message key="profile.email"/>: ${user.email}</p>
</div>
<div>
    <a href="${pageContext.request.contextPath}/users?action=edit&id=${user.id}"><fmt:message key="profile.edit"/></a>
    <a href="${pageContext.request.contextPath}/users?action=delete&id=${user.id}" onclick="return confirm('<fmt:message key="profile.delete.confirm"/>')"><fmt:message key="profile.delete"/></a>
    <a href="${pageContext.request.contextPath}/auth?action=signout"><fmt:message key="profile.signout"/></a>
    <a href="${pageContext.request.contextPath}/users"><fmt:message key="profile.list"/></a>
</div>
<div>
    <a href="?lang=en">English</a>
    <a href="?lang=ru">Русский</a>
</div>
</body>
</html>

