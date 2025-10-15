<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Book details</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>


<div class="book-details-container">
    <div class="book-details-card">

        <div class="book-image">
            <c:if test="${not empty book.imagePath}">
                <img src="${pageContext.request.contextPath}${book.imagePath}"
                     alt="${book.name}" class="book-cover-large">
            </c:if>
        </div>

        <div class="book-info">
            <h1 class="book-title">${book.name}</h1>

            <p class="book-authors">
                <strong>Authors:</strong>
                <c:forEach var="a" items="${book.author}" varStatus="status">
                    <a href="${pageContext.request.contextPath}/author/${a.id}" class="author-link">
                        <c:out value="${a.firstName}" /> <c:out value="${a.lastName}" />
                    </a>
                    <c:if test="${!status.last}">, </c:if>
                </c:forEach>
            </p>

            <p class="book-genres">
                <strong>Genres:</strong>
                <c:forEach var="g" items="${book.genre}" varStatus="status">
                    <span class="genre-pill">${g.name}</span>
                    <c:if test="${!status.last}"> </c:if>
                </c:forEach>
            </p>
            <div class="book-description">
                <strong>Description:</strong><br>
                <c:out value="${book.name}" default="No description available." />
            </div>

            <button class="back-btn" onclick="history.back()">← Back</button>
        </div>
    </div>
</div>
</body>
</html>
