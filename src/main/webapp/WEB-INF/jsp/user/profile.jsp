<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css' />">
    <title>${user.username}'s Profile</title>
    <style>
        .btn-follow[value="Follow"] {
            background-color: #5a7b1e;
        }
        .follow-lists h3 {
            margin-top: 25px;
            border-bottom: 1px solid #d9c7a6;
            padding-bottom: 5px;
            color: #3b2e1e;
        }

        .follow-link {
            color: #6b5c4a;
            text-decoration: none;
        }

        .follow-link:hover {
            color: #3b2e1e;
            text-decoration: underline;
        }
    </style>
</head>
<body>
<%@ include file="../utils/header.jsp" %>

<div class="container">
    <div class="profile-header">
        <h2>${user.username}'s Profile</h2>

       <c:if test="${!isOwner}">
    <c:choose>
        <c:when test="${isFollowing}">
            <form action="${pageContext.request.contextPath}/follow/unfollow/${user.id}" method="post" style="display:inline;">
                <input type="submit" value="Unfollow" class="btn-unfollow"/>
            </form>
        </c:when>
        <c:otherwise>
            <form action="${pageContext.request.contextPath}/follow/${user.id}" method="post" style="display:inline;">
                <input type="submit" value="Follow" class="btn-follow"/>
            </form>
        </c:otherwise>
    </c:choose>

    <a href="${pageContext.request.contextPath}/messages/conversation/${user.id}" class="btn-message">Message</a>
    </c:if>
    </div>

    <h3>Public Categories</h3>
    <c:choose>
        <c:when test="${empty categories}">
            <p>This user has no public categories.</p>
        </c:when>
        <c:otherwise>
            <div class="book-grid">
                <c:forEach var="c" items="${categories}">
                    <div class="book-card" onclick="location.href='<c:url value='/categories/details/${c.id}' />'">
                        <h4>${c.name}</h4>
                        <p>${c.description}</p>
                        <c:if test="${!isOwner}">
                        <form action="${pageContext.request.contextPath}/categories/copy/${c.id}" method="post">
                        <br>
                            <input type="submit" value="Copy to my categories" class="btn-send"/>
                        </form>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="follow-lists">
    <h3>Following</h3>
    <c:if test="${empty following}">
        <p>Not following anyone yet.</p>
    </c:if>
    <c:forEach var="f" items="${following}">
        <a href="${pageContext.request.contextPath}/user/profile/${f.id}" class="follow-link">${f.username}</a><br/>
    </c:forEach>

    <h3>Followers</h3>
    <c:if test="${empty followers}">
        <p>No followers yet.</p>
    </c:if>
    <c:forEach var="f" items="${followers}">
        <a href="${pageContext.request.contextPath}/user/profile/${f.id}" class="follow-link">${f.username}</a><br/>
    </c:forEach>
</div>
</div>
</body>
</html>
