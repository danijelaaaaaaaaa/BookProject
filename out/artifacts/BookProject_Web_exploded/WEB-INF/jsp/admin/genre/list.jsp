<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />"><meta charset="UTF-8">
    <title>Genres</title>
    </head>
<body>
<%@ include file="../../utils/header.jsp" %>
<div class="container">
<c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
</c:if>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
</c:if>

<h4> Add a new genre </h4>
<form:form modelAttribute="genreDTO" action="${pageContext.request.contextPath}/admin/genre/saveGenre" method="post">
    Name of the genre:
    <form:input path="name" required="required" maxlength="60" />
    <form:errors path="name" element="div" cssClass="text-danger" />
    <input type="submit" value="Save" />
</form:form> <br>

    <h4> Search genre </h4>
<form action="${pageContext.request.contextPath}/admin/genre/searchGenres" method="get">
    <label>
        <input type="text" name="query" placeholder="Search genre...">
    </label>
    <button type="submit">Search</button>
</form> <br>

    <h4> List of genres </h4>
<table>
    <thead>
    <tr>
        <th>#</th>
        <th>Name of genre</th>
        <th>Book count</th>
        <th>Change the name</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="genre" items="${items}" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${genre.name}</td>
                <td>${genre.bookCount}</td>

                <td>
                    <form action="${pageContext.request.contextPath}/admin/genre/updateGenre" method="post">
                        <input type="hidden" name="oldName" value="${genre.name}" />
                        <label>
                        <input type="text" name="newName" value="${genre.name}" required maxlength="60" />
                        </label>
                        <input type="submit" value="Update" />
                    </form>

                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/genre/deleteGenre" method="post">
                        <input type="hidden" name="genreName" value="${genre.name}" />
                        <input type="submit" value="Delete"  />
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
    <button class="back-btn" onclick="history.back()">← Back</button>
</div>

</body>
</html>