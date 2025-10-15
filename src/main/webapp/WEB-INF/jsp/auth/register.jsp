<%--
  Created by IntelliJ IDEA.
  User: Danijela
  Date: 15. 10. 2025.
  Time: 05:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Register</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>
    <div class="container">
    <c:if test="${not empty(error)}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty(success)}">
    <div class="alert alert-success">${success}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/auth/register" method="post">
        <label class="register-label"> Username:
            <input type="text" name="username" placeholder="Username" required/>
        </label> <br>
        <label class="register-label">Password:
            <input type="password" name="password" placeholder="Password" required/>
        </label> <br>
        <label class="register-label">Email:
            <input type="email" name="email" placeholder="Email" required/>
        </label> <br>
        <input type="submit" value="Register"> <br>
    </form> <br>

    Already have an account? <a href="${pageContext.request.contextPath}/auth/login">Login</a>
</div>
</body>
</html>
