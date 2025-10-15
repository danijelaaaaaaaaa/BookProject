<%--
  Created by IntelliJ IDEA.
  User: Danijela
  Date: 15. 10. 2025.
  Time: 06:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Dashboard</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>

<div class="container">
    <h1> You are logged in as admin</h1>
    <table>
        <tr>
            <th>Section</th>
            <th>Action</th>
        </tr>
        <tr>
            <td>Manage Genres</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/genre/list">
                    <button>Open</button>
                </a>
            </td>
        </tr>
        <tr>
            <td>Add new author</td>
            <td>
                <a href="${pageContext.request.contextPath}/author/new">
                <button>Open</button>
                </a>
            </td>
        </tr>
        <tr>
            <td> ...</td>
            <td>
                <a href="${pageContext.request.contextPath}/index">
                    <button>Open</button>
                </a>
            </td>
        </tr>
    </table>
    <form action="${pageContext.request.contextPath}/logout" method="POST">
        <input type="submit" value="Logout">
    </form>
</div>

</body>
</html>
