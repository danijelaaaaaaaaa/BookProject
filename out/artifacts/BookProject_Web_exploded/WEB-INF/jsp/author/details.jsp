
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Author details</title>
</head>
<body>
    ${author.firstName}
    <c:if test="${not empty author.lastName}">${author.lastName}  </c:if> <br>
    <c:if test="${not empty author.biography}">${author.biography}  </c:if> <br>
    <ul>
        <c:forEach var="book" items="${author.books}">
            <li><a href="${pageContext.request.contextPath}/books/${book.id}">${book.name}</a></li>
        </c:forEach>
    </ul>
</body>
</html>
