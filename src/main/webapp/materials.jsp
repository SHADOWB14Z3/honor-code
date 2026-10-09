<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Study Materials</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div><strong>Honor Code</strong></div>
    <div>
        <a href="${pageContext.request.contextPath}/student-dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="container">
    <div class="card">
        <h2>Study Material</h2>
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>

        <c:if test="${not empty materials}">
            <div class="grid">
                <c:forEach items="${materials}" var="material">
                    <div class="course-card">
                        <h3>${material.title}</h3>
                        <p>${material.content}</p>
                        <small>Added on: ${material.createdAt}</small>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty materials}">
            <p>No study material has been posted for this course yet.</p>
        </c:if>

        <c:if test="${sessionScope.user.role == 'TEACHER'}">
            <hr>
            <h3>Add Material</h3>
            <form method="post" action="${pageContext.request.contextPath}/materials">
                <input type="hidden" name="courseId" value="${courseId}">
                <div class="form-group">
                    <label>Title</label>
                    <input type="text" name="title" required>
                </div>
                <div class="form-group">
                    <label>Content</label>
                    <textarea name="content" rows="6" required></textarea>
                </div>
                <button type="submit">Save Material</button>
            </form>
        </c:if>
    </div>
</div>
</body>
</html>
