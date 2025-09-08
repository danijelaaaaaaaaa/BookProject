<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>BookProject</title>
</head>
<body>
<h1>Hello from index.jsp </h1>

<a href="${pageContext.request.contextPath}/admin/genre/list"> Click</a> <br>
<a href="${pageContext.request.contextPath}/books/new"> Click2</a> <br>
<a href="${pageContext.request.contextPath}/books/list-books"> Click3</a> <br>
<a href="${pageContext.request.contextPath}/author/new"> Click3</a> <br>
<a href="${pageContext.request.contextPath}/auth/login"> LOGIN</a> <br>

</body>
</html>