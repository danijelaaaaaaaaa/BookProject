<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>BookProject</title>
</head>
<body>
<%@ include file="utils/header.jsp" %>
<div class="container">
<h1>Welcome to Book project</h1>
<p>Your personal space for tracking books, authors, and genres.</p> <br>

<a href="${pageContext.request.contextPath}/books/list-books" class="btn-message"> View Books</a>
<a href="${pageContext.request.contextPath}/auth/login" class="btn-message">> Login</a> <br>
</div>
</body>
</html>