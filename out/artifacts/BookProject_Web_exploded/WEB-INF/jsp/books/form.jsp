<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Add book</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>
<div class="container">

<c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
</c:if>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
</c:if>
    <c:choose>
      <c:when test="${isEdit}">
      <h2>Edit book</h2>
        <c:set var="formAction" value="${pageContext.request.contextPath}/books/editBook/${bookDTO.id}" />
      </c:when>
      <c:otherwise>
      <h2>Add a a new book</h2>
        <c:set var="formAction" value="${pageContext.request.contextPath}/books/save" />
      </c:otherwise>
    </c:choose>
    <form:form modelAttribute="bookDTO"
                method="post"
               action="${formAction}"
                enctype="multipart/form-data">
        <div>
        <form:hidden path="id"/>
        <form:label path="name"> Name: </form:label>
        <form:input path="name" required="required" maxlength="200" cssClass="form-control"/>
         <form:errors path="name" element="div" cssClass="text-danger" />
        </div>
        <div class="form-field">
        <form:label path="description">Description:</form:label><br>
        <form:textarea path="description" rows="4" cols="50" placeholder="Enter book description..." />
        <form:errors path="description" cssClass="text-danger" />
    </div>
        <div class="form-field">
            <form:label path="genreIds"> Genres: </form:label>
            <form:select path="genreIds" multiple="true">
                <c:forEach var="genre" items="${genres}">
                    <form:option value="${genre.id}" label="${genre.name}"/>
                </c:forEach>
            </form:select>
        </div>
        <div class="form-field">
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
                <input type="hidden" name="existingImagePath" value="${bookDTO.imagePath}" />
            </c:if>
        </div>
        <div>
            <button type="submit">Save</button>
            <button class="back-btn" onclick="history.back()">Cancel</button>
        </div>
    </form:form>
</div>
</body>
</html>
