
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><html>
<head>
    <title>Books</title>
</head>
<body>
  <table>
    <thead>
    <tr>
      <th>#</th>
      <th>Title</th>
      <th>Genres</th>
      <th>Authors</th>
      <th>Image</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="b" items="${books}">
      <tr>
        <td>${b.id}</td>
          <td> <a href="${pageContext.request.contextPath}/books/${b.id}"> ${b.name} </a></td>
        <td>
          <c:forEach var="g" items="${b.genre}" varStatus="status">
              <c:out value="${g}"/> <c:if test="${!status.last}">, </c:if>
          </c:forEach>
        </td>
        <td><c:forEach var="author" items="${b.author}" varStatus="status">
          ${author.firstName} <c:out value="${author.lastName}" default=""/> <c:if test="${!status.last}">, </c:if>
        </c:forEach></td>
        <td>
          <c:if test="${b.imagePath != null}">
            <img src="${pageContext.request.contextPath}${b.imagePath}" width="100"  alt="Cover Image"/>
          </c:if>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</body>
</html>
