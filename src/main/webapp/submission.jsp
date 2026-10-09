<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Assignment Submission</title>
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
        <h2>Submit Assignment</h2>
        <c:if test="${not empty assignment}">
            <h3>${assignment.title}</h3>
            <p>${assignment.description}</p>
            <p><strong>Due date:</strong> ${assignment.dueDate}</p>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/submission">
            <input type="hidden" name="assignmentId" value="${assignment.id}">
            <div class="form-group">
                <label for="content">Your answer</label>
                <textarea id="content" name="content" rows="8" required>${currentSubmission.content}</textarea>
            </div>
            <button type="submit">Submit Assignment</button>
        </form>

        <c:if test="${not empty currentSubmission}">
            <hr>
            <h3>Previous Submission</h3>
            <p>${currentSubmission.content}</p>
        </c:if>
    </div>
</div>
</body>
</html>
