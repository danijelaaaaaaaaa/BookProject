<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>Category</title>
</head>
<body>
<%@ include file="../utils/header.jsp" %>
<c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
</c:if>
<div class="container">
<form:form modelAttribute="category" action="${pageContext.request.contextPath}/categories/save" method="post">

    <div class="form-field">
    <label for="name">Name of category:</label>
    <form:input path="name" id="name" required="true"/>
    </div>

    <div class="form-field">
    <label for="description">Description</label>
    <form:input path="description" id="description" />
    </div>

    <div class="form-field">
    <label>
            <form:checkbox path="publicCategory"/> Make this category public
        </label>
    </div>

    <div>
        <input type="submit" value="Save"/>
    </div>
</form:form>
</div>
</body>
</html>
