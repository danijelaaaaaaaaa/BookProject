
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Books</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>


<c:if test="${not myBooksPage}">
    <div class="search-container">
        <form action="${pageContext.request.contextPath}/books/search-books" method="get" class="search-form">
            <label> Search books:
    <input type="text" name="keyword" placeholder="Search books by title..."
                   value="${param.keyword != null ? param.keyword : ''}" class="search-input"/>
    </label>
            <button type="submit" class="search-button">Search</button>
        </form>
    </div>

    <div class="filter-container">
    <form action="${pageContext.request.contextPath}/books/filter" method="get">
            <label> Filter by genre:
        <select name="genre" class="filter-select">
        <option value="">All genres</option>
        <c:forEach var="g" items="${genres}">
                <option value="${g.id}">${g.name}</option>
        </c:forEach>
        </select>
        </label>

        <label> Filter by Author:
        <select name="author" class="filter-select">
         <option value="">All authors</option>
         <c:forEach var="a" items="${authors}">
            <option value="${a.id}">${a.firstName} ${a.lastName}</option>
         </c:forEach>
        </select>
        </label>

        <button type="submit" class="filter-button">Apply</button>
    </form>
    </div>


    </c:if>


<div class="book-list-container">
    <c:if test="${empty books}"> <h3>No books </h3> </c:if>
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