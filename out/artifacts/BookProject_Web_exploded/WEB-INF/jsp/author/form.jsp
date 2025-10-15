<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Add author</title>
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

<sec:authorize access="hasRole('ROLE_ADMIN')">
<c:choose>
<c:when test="${isEdit}">
<c:set var="formAction" value="${pageContext.request.contextPath}/author/editAuthor/${authorDTO.id}" />
</c:when>
<c:otherwise>
<c:set var="formAction" value="${pageContext.request.contextPath}/author/saveAuthor" />
</c:otherwise>
</c:choose>
    <form:form modelAttribute="authorDTO"
           method="post"
           action="${formAction}"
           enctype="multipart/form-data">
        <div>
            <form:hidden path="id"/>

            <form:label path="firstName">First name:</form:label>
            <form:input path="firstName" required="required" maxlength="200" cssClass="form-control"/>
            <form:errors path="firstName" element="div" cssClass="text-danger"/> <br>

            <form:label path="lastName">Last name:</form:label>
            <form:input path="lastName"  maxlength="300" cssClass="form-control"/>
            <form:errors path="lastName"  element="div" cssClass="text-danger"/> <br>

            <form:label path="biography">Biography:</form:label>
            <form:input path="biography"  rows="4" cols="50" placeholder="Write short biography..."/>
            <form:errors path="biography"  element="div" cssClass="text-danger"/> <br>
        </div>
        <div class="form-field">
            <form:label path="booksIds"> Books: </form:label>
            <form:select path="booksIds" multiple="true">
                <c:forEach var="book" items="${books}">
                    <form:option value="${book.id}" label="${book.name}"/>
                </c:forEach>
            </form:select>
        </div> <br>
        <div>
            <label>Image:
            <input type="file" name="file" accept="image/*">
            </label>
            <c:if test="${authorDTO.imagePath != null}">
                <p>Now picture: </p>
                <img src="${pageContext.request.contextPath}/uploads/${authorDTO.imagePath}" width="150"  alt="Cover of the book"/>
            </c:if>
        </div>
        <button type="submit">Save</button>
        <button class="back-btn" onclick="history.back()">Cancel</button>
    </form:form>
    </sec:authorize>
</div>
</body>
</html>
