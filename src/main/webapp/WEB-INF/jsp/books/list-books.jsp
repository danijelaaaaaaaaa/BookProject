
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Books</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>

<div class="book-list-container">

    <div class="book-grid">
        <c:forEach var="b" items="${books}">
            <div class="book-card"   onclick="location.href='<c:url value='/books/details/${b.id}' />'">
                <img src="${pageContext.request.contextPath}${b.imagePath}" width="100"  alt="${b.name}"  class="book-cover"/>
                <h3 class="book-title"> ${b.name} </h3>
                <p class="book-author">
                <c:forEach var="author" items="${b.author}" varStatus="status">
                 <a href="<c:url value='/author/details/${author.id}' />" class="author-link">
                 ${author.firstName} <c:out value="${author.lastName}" default=""/>
                    </a>
                 <c:if test="${!status.last}">, </c:if>
                </c:forEach>
               </p>

            </div>
        </c:forEach>
    </div>

</div>


</body>
</html>
