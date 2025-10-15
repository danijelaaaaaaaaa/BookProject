
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css'/>">
    <title>${category.name}</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>

<div class="container">
<c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <h4>${category.name}</h4>
    <p>${category.description}</p> <br>
    <c:if test="${category.owner.username == pageContext.request.userPrincipal.name}">
    <form action="${pageContext.request.contextPath}/categories/delete/${category.id}"
          method="post"
          onsubmit="return confirm('Are you sure you want to delete this category?');">
        <input type="submit" value="Delete Category" class="btn-unfollow"/>
    </form>
</c:if>

    <h4>Books in this category:</h4>

    <c:choose>
        <c:when test="${not empty books}">
            <div class="book-grid">
                <c:forEach var="b" items="${books}">
                    <div class="book-card"
                         onclick="location.href='${pageContext.request.contextPath}/books/details/${b.id}'">
                        <img src="${pageContext.request.contextPath}${b.imagePath}" width="120"
                             alt="${b.name}" class="book-cover"/>
                        <h4>${b.name}</h4>
                        <c:if test="${canManage}">
                          <form action="${pageContext.request.contextPath}/categories/removeBook" method="post" style="margin-top:8px;">
                            <input type="hidden" name="bookId" value="${b.id}" />
                            <input type="hidden" name="categoryId" value="${category.id}" />
                            <input type="submit" value="Remove" class="btn-unfollow" />
                          </form>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <p>No books in this category yet.</p>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>
