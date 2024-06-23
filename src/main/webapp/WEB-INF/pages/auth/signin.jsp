<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign In</title>
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" />
<h1><fmt:message key="signin.title"/></h1>
<form action="${pageContext.request.contextPath}/auth?action=signin" method="post">
    <div>
        <label for="email"><fmt:message key="signin.email"/></label>
        <input type="email" id="email" name="email" required>
    </div>
    <div>
        <label for="password"><fmt:message key="signin.password"/></label>
        <input type="password" id="password" name="password" required>
    </div>
    <button type="submit"><fmt:message key="signin.button"/></button>
    <div>
        <a href="${pageContext.request.contextPath}/auth?action=signup"><fmt:message key="signin.signup"/></a>
    </div>
    <c:if test="${not empty errorMessage}">
        <div style="color: red;">
                ${errorMessage}
        </div>
    </c:if>
</form>
<div>
    <a href="?lang=en">English</a>
    <a href="?lang=ru">Русский</a>
</div>
</body>
</html>
