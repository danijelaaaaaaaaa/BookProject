<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Book details</title>
</head>
<body>
    ${book.name} <br>
    <c:if test="${not empty book.imagePath}"> <img src="${pageContext.request.contextPath}${book.imagePath}"  alt="Cover Image"/> </c:if>
    <br>
    Genres:
    <ul>
      <c:forEach var="g" items="${book.genre}">
        <li><a href="">${g.name}</a></li>
      </c:forEach>
    </ul> <br>
    Authors:
    <ul>
      <c:forEach var="a" items="${book.author}">
        <li><a href="${pageContext.request.contextPath}/author/${a.id}">${a}</a></li>
      </c:forEach>
    </ul>
</body>
</html>
