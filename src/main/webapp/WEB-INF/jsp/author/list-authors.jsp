<%--
  Created by IntelliJ IDEA.
  User: Danijela
  Date: 15. 10. 2025.
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Authors</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>

<div class="search-container">
    <form action="${pageContext.request.contextPath}/author/search-author" method="get" class="search-form">
        <label> Search authors:
            <input type="text" name="keyword" placeholder="Search authors by name..."
                   value="${param.keyword != null ? param.keyword : ''}" class="search-input"/>
        </label>
        <button type="submit" class="search-button">Search</button>
    </form>
</div>
<div class="author-list-container">
    <div class="author-grid">
         <c:forEach var="a" items="${authors}">
         <div class="author-card" onclick="location.href='${pageContext.request.contextPath}/author/details/${a.id}'">

         <c:if test="${not empty a.imagePath}">
         <img  src="${pageContext.request.contextPath}${a.imagePath}"  alt="${a.firstName}" class="author-image">
        </c:if>
        <h3 class="author-name"> ${a.firstName} <c:out value="${a.lastName}"> </c:out>  </h3>
        </div>
         </c:forEach>
    </div>
</div>

</body>
</html>
