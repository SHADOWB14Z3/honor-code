<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Add Material</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div><strong>Honor Code</strong></div>
    <div>
        <a href="${pageContext.request.contextPath}/teacher-dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="container">
    <div class="card">
        <h2>Add Study Material</h2>
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/materials">
            <div class="form-group">
                <label>Course ID</label>
                <input type="number" name="courseId" required>
            </div>
            <div class="form-group">
                <label>Material Title</label>
                <input type="text" name="title" required>
            </div>
            <div class="form-group">
                <label>Content</label>
                <textarea name="content" rows="6" required></textarea>
            </div>
            <button type="submit">Save Material</button>
        </form>
    </div>
</div>
</body>
</html>
