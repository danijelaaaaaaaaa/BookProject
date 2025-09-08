<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Add book</title>
</head>
<body>
    <form:form modelAttribute="bookDTO"
                method="post"
               action="${pageContext.request.contextPath}/books/save"
                enctype="multipart/form-data">
        <div>
        <form:hidden path="id"/>
        <form:label path="name"> Name: </form:label>
        <form:input path="name"/>
        <form:errors path="name"/>
        </div>
        <div>
            <form:label path="genreIds"> Genres: </form:label>
            <form:select path="genreIds" multiple="true">
                <c:forEach var="genre" items="${genres}">
                    <form:option value="${genre.id}" label="${genre.name}"/>
                </c:forEach>
            </form:select>
        </div>
        <div>
            <form:label path="authorIds"> Authors: </form:label>
            <form:select path="authorIds" multiple="true">
                <c:forEach var="author" items="${authors}">
                    <form:option value="${author.id}"> ${author.firstName} <c:out value="${author.lastName}" default=""/> </form:option>
                </c:forEach>
            </form:select>
        </div>
        <div>
            <label>Image:
            <input type="file" name="file" accept="image/*">
            </label>
            <c:if test="${bookDTO.imagePath != null}">
                <p>Now picture: </p>
                <img src="${pageContext.request.contextPath}/uploads/${bookDTO.imagePath}" width="150"  alt="Cover of the book"/>
            </c:if>
        </div>
        <div>
            <button type="submit">Save</button>
            <a href="${pageContext.request.contextPath}/index">Cancel</a>
        </div>
    </form:form>
</body>
</html>
