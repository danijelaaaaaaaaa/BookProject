<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Categories</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>

<div class="container">
    <h2>My Categories</h2>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <a href="${pageContext.request.contextPath}/categories/new" class="back-btn">+ Add new category</a> <br>
    <br>
    <c:choose>
        <c:when test="${empty categories}">
            <p>No categories yet. Create your first one!</p>
        </c:when>
        <c:otherwise>
             <div class="book-grid">
          <c:forEach var="c" items="${categories}">
              <div class="book-card" onclick="location.href='${pageContext.request.contextPath}/categories/details/${c.id}'">
                  <h3>${c.name}</h3>
                  <p>${c.description}</p>
                  <p><strong>Books:</strong> ${fn:length(c.books)}</p>
                  <p><strong>Public:</strong> ${c.publicCategory ? 'Yes' : 'No'}</p>
              </div>
          </c:forEach>
      </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
