<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign In</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/signIn.css">
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="messages" />
<header class="header flexbox">
    <h1 class="title"><fmt:message key="signin.title"/></h1>
</header>
<form action="${pageContext.request.contextPath}/auth?action=signin" method="post" class="form">
    <div class="form-group">
        <label for="email" class="label"><fmt:message key="signin.email"/></label>
        <input type="email" id="email" name="email" class="input" required>
    </div>
    <div class="form-group">
        <label for="password" class="label"><fmt:message key="signin.password"/></label>
        <input type="password" id="password" name="password" class="input" required>
    </div>
    <button type="submit" class="button"><fmt:message key="signin.button"/></button>
    <div class="form-group">
        <a href="${pageContext.request.contextPath}/auth?action=signup" class="link"><fmt:message key="signin.signup"/></a>
    </div>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>
</form>
<div class="language-links">
    <a href="?lang=en" class="link">English</a>
    <a href="?lang=ru" class="link">Русский</a>
</div>
</body>
</html>
