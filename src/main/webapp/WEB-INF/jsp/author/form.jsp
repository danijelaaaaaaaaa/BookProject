<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Add author</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>
<div class="container">
    <form:form modelAttribute="authorDTO"
           method="post"
           action="${pageContext.request.contextPath}/author/saveAuthor"
           enctype="multipart/form-data">
        <div>
            <form:hidden path="id"/>

            <form:label path="firstName">First name:</form:label>
            <form:input path="firstName"/>
            <form:errors path="firstName"/>

            <form:label path="lastName">Last name:</form:label>
            <form:input path="lastName"/>
            <form:errors path="lastName"/>

            <form:label path="biography">Biography:</form:label>
            <form:input path="biography"/>
            <form:errors path="biography"/>
        </div>
        <div>
            <form:label path="booksIds"> Books: </form:label>
            <form:select path="booksIds" multiple="true">
                <c:forEach var="book" items="${books}">
                    <form:option value="${book.id}" label="${book.name}"/>
                </c:forEach>
            </form:select>
        </div>
        <button type="submit">Save</button>
    </form:form>
</div>
</body>
</html>
