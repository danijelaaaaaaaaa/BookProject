<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Book details</title>
    <style>

    </style>
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
                    <a href="${pageContext.request.contextPath}/author/details/${a.id}" class="author-link">
                        <c:out value="${a.firstName}" /> <c:out value="${a.lastName}" />
                    </a>
                    <c:if test="${!status.last}">, </c:if>
                </c:forEach>
            </p>

            <p class="book-genres">
                <strong>Genres:</strong>
                <c:forEach var="g" items="${book.genre}" varStatus="status">
                    <a href="${pageContext.request.contextPath}/books/filter?genre=${g.id}&author=" class="genre-pill">${g.name}</a>
                    <c:if test="${!status.last}"> </c:if>
                </c:forEach>
            </p>
            <div class="book-description">
                <strong>Description:</strong><br>
                <c:out value="${book.description}" default="No description available." />
            </div>
            <c:if test="${book.addedBy != null}">
                <p>
                    Added by:
                    <a href="${pageContext.request.contextPath}/user/profile/${book.addedBy.id}">
                        ${book.addedBy.username}
                    </a>
                </p>
            </c:if>
            <button class="back-btn" onclick="history.back()">← Back</button>

            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN') or (book.addedBy != null and pageContext.request.userPrincipal.name == book.addedBy.username)}">
            <form action="${pageContext.request.contextPath}/books/editBook/${book.id}" method="get" style="display:inline;">
             <button type="submit">Edit</button>
            </form>
                <form action="${pageContext.request.contextPath}/books/delete/${book.id}" method="post">
                <button type="submit" class="btn-delete" onclick="return confirm('Are you sure you want to delete this book?')">Delete</button>
            </form>
            </c:if>
            <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
                 <c:choose>
                  <c:when test="${empty userCategories}">
                      <p style="font-style: italic; color: #6b5c4a;">This book is already in all your categories.</p>
                     </c:when>
                     <c:otherwise>
                  <form action="${pageContext.request.contextPath}/categories/addBookToCategory" method="post" class="inline-form">
                    <input type="hidden" name="bookId" value="${book.id}" />
                    <select name="categoryId" class="filter-select">
                      <c:forEach var="cat" items="${userCategories}">
                        <option value="${cat.id}">${cat.name}</option>
                      </c:forEach>
                    </select>
                    <input type="submit" value="Add to Category" class="filter-button" />
                  </form>
                  </c:otherwise>
                 </c:choose>
                </sec:authorize>
        </div>
    </div>
</div>
</body>
</html>
