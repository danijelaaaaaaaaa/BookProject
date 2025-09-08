
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
    <sec:authorize access="!isAuthenticated()">
        <div class="login">
            <form action="${pageContext.request.contextPath}/auth/login" method="POST" >
                <label class="login-label">Username:
                <input type="text" name="username" class="login-input" required/>
                </label>
                <label class="login-label">Password:
                <input type="password" name="password" class="login-input" required />
                </label>
                <input type="submit" class="login-input" />
            </form>
            <c:if test="${not empty param.error}">
                <label class="login-error">Invalid username or password</label>
            </c:if>
        </div>
    </sec:authorize>
</body>
</html>
