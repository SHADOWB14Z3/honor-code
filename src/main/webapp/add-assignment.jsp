<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Add Assignment</title>
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
        <h2>Create Assignment</h2>
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/assignments" id="assignmentForm">
            <div class="form-group">
                <label>Course ID</label>
                <input type="number" name="courseId" required>
            </div>
            <div class="form-group">
                <label>Assignment Title</label>
                <input type="text" name="title" required>
            </div>
            <div class="form-group">
                <label>Assignment Description</label>
                <textarea name="description" rows="5" required></textarea>
            </div>
            <div class="form-group">
                <label>Due Date</label>
                <input type="date" id="dueDate" name="dueDate" required>
            </div>
            <button type="submit">Save Assignment</button>
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
