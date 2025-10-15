
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Log in</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>
 <div class="container">
    <sec:authorize access="!isAuthenticated()">

            <form action="${pageContext.request.contextPath}/auth/process" method="POST" >
                <label class="login-label">Username:
                <input type="text" name="username" class="login-input" required/>
                </label> <br>
                <label class="login-label">Password:
                <input type="password" name="password" class="login-input" required />
                </label><br>
                <input type="submit" class="login-input" /> <br>
            </form>
            <c:if test="${not empty param.error}">
                <div class="alert alert-success"> >Invalid username or password</div>
            </c:if>

    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
    <h1>You are already logged in!</h1>
    <a href="${pageContext.request.contextPath}/index">Go to homepage</a>

    <form action="${pageContext.request.contextPath}/logout" method="POST">
    <input type="submit" value="Logout" />
    </form>
    </sec:authorize>
     </div>
</body>
</html>
