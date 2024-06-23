<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create User</title>
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" />
<h1><fmt:message key="user.create.title"/></h1>
<form action="${pageContext.request.contextPath}/users?action=create" method="post">
    <div>
        <label for="name"><fmt:message key="user.create.name"/></label>
        <input type="text" id="name" name="name" required>
    </div>
    <div>
        <label for="surname"><fmt:message key="user.create.surname"/></label>
        <input type="text" id="surname" name="surname" required>
    </div>
    <div>
        <label for="nickname"><fmt:message key="user.create.nickname"/></label>
        <input type="text" id="nickname" name="nickname" required>
    </div>
    <div>
        <label for="email"><fmt:message key="user.create.email"/></label>
        <input type="email" id="email" name="email" required>
    </div>
    <div>
        <label for="password"><fmt:message key="user.create.password"/></label>
        <input type="password" id="password" name="password" required>
    </div>
    <button type="submit"><fmt:message key="user.create.button"/></button>
</form>
<div>
    <a href="?lang=en">English</a>
    <a href="?lang=ru">Русский</a>
</div>
</body>
</html>

