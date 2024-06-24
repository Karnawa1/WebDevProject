<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" />
<header class="header flexbox">
    <h1><fmt:message key="user.list.title"/></h1>
    <div class="header-links flexbox">
        <a href="${pageContext.request.contextPath}/users?action=profile&id=${user.id}"><fmt:message key="user.list.actions.view"/></a>
        <a href="?lang=en">En</a>
        <a href="?lang=ru">Ru</a>
    </div>
</header>
<table class="user-info flexbox">
    <thead>
    <tr>
        <th>ID</th>
        <th><fmt:message key="user.create.name"/></th>
        <th><fmt:message key="user.create.surname"/></th>
        <th><fmt:message key="user.create.nickname"/></th>
        <th><fmt:message key="user.create.email"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.nickname}</td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">
    <c:forEach var="i" begin="1" end="${noOfPages}">
        <a href="${pageContext.request.contextPath}/users?page=${i}">${i}</a>
    </c:forEach>
</div>
</body>
</html>
