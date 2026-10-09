<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Assignments</title>
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
        <h2>Assignments</h2>
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>

        <c:if test="${not empty assignments}">
            <table>
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Due Date</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${assignments}" var="assignment">
                    <tr>
                        <td>${assignment.title}</td>
                        <td>${assignment.dueDate}</td>
                        <td>${assignment.description}</td>
                        <td><a class="small-link" href="${pageContext.request.contextPath}/submission?assignmentId=${assignment.id}">Submit</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty assignments}">
            <p>No assignments have been posted for this course yet.</p>
        </c:if>
    </div>
</div>
</body>
</html>
