
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Author details</title>
    <style>
        .mini-book-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
            gap: 20px;
            margin-top: 15px;
        }

        .mini-book-card {
            background-color: #fffaf3;
            border: 1px solid #cbbf9d;
            border-radius: 8px;
            text-align: center;
            padding: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        .mini-book-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
        }

        .mini-book-card img {
            width: 100%;
            height: 180px;
            object-fit: cover;
            border-radius: 6px;
            margin-bottom: 6px;
        }

        .mini-book-title {
            font-size: 14px;
            color: #3b2e1e;
        }
    </style>
</head>
<body>
<%@ include file="../utils/header.jsp" %>
<div class="book-details-container">
    <div class="book-details-card">

        <div class="book-image">
            <c:if test="${not empty author.imagePath}">
                <img src="${pageContext.request.contextPath}${author.imagePath}"
                     alt="${author.firstName}" class="book-cover-large">
            </c:if>
        </div>

        <div class="book-info">
            <h1 class="book-title">
                ${author.firstName} <c:if test="${not empty author.lastName}">${author.lastName}</c:if>
            </h1>

            <div class="author-bio">
                <strong>Biography:</strong><br>
                <c:out value="${author.biography}" default="No biography available." />
            </div>

            <div class="book-list-by-author">
                <h4>Books by this author:</h4>
                <div class="mini-book-grid">
                    <c:forEach var="book" items="${author.books}" end="2">
                        <div class="mini-book-card"
                             onclick="location.href='${pageContext.request.contextPath}/books/details/${book.id}'">
                            <c:if test="${not empty book.imagePath}">
                                <img src="${pageContext.request.contextPath}${book.imagePath}" alt="${book.name}">
                            </c:if>
                            <div class="mini-book-title">${book.name}</div>
                        </div>
                    </c:forEach>

                    <c:if test="${empty author.books}">
                        <p><em>No books found for this author.</em></p>
                    </c:if>
                </div>
            </div>

            <button class="back-btn" onclick="history.back()">← Back</button>

            <sec:authorize access="hasRole('ROLE_ADMIN')">
               <form action="${pageContext.request.contextPath}/author/editAuthor/${author.id}" method="get" style="display:inline;">
            <button type="submit">Edit</button>
                </form>
                <form action="${pageContext.request.contextPath}/author/delete/${author.id}" method="post" style="display:inline;">
                    <button type="submit" class="btn-delete"
                            onclick="return confirm('Are you sure you want to delete this author?')">Delete</button>
                </form>
            </sec:authorize>
        </div>

    </div>
</div>
</body>
</html>
