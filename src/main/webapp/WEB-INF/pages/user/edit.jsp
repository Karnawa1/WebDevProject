<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/edit.css">
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" />
<header class="header flexbox">
    <h1><fmt:message key="user.edit.title"/></h1>
</header>
<form action="${pageContext.request.contextPath}/users?action=edit" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${user.id}">
    <div>
        <label for="name"><fmt:message key="user.edit.name"/></label>
        <input type="text" id="name" name="name" value="${user.name}" required>
    </div>
    <div>
        <label for="surname"><fmt:message key="user.edit.surname"/></label>
        <input type="text" id="surname" name="surname" value="${user.surname}" required>
    </div>
    <div>
        <label for="nickname"><fmt:message key="user.edit.nickname"/></label>
        <input type="text" id="nickname" name="nickname" value="${user.nickname}" required>
    </div>
    <div>
        <label for="password"><fmt:message key="user.edit.password"/></label>
        <input type="password" id="password" name="password">
    </div>
    <div>
        <label for="email"><fmt:message key="user.edit.email"/></label>
        <input type="email" id="email" name="email" value="${user.email}" required>
    </div>
    <div>
        <label for="avatar"><fmt:message key="signup.avatar"/></label>
        <input type="file" id="avatar" name="avatar">
    </div>
    <button type="submit"><fmt:message key="user.edit.button"/></button>
</form>
<div class="language-links">
    <a href="?lang=en">English</a>
    <a href="?lang=ru">Русский</a>
</div>
</body>
</html>
