<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Enrolled Courses</title>
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
        <h2>My Enrolled Courses</h2>
        <table>
            <thead>
            <tr>
                <th>Course</th>
                <th>Materials</th>
                <th>Assignments</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${enrolledCourses}" var="enrollment">
                <tr>
                    <td>${enrollment.courseTitle}</td>
                    <td><a class="small-link" href="${pageContext.request.contextPath}/materials?courseId=${enrollment.courseId}">Open</a></td>
                    <td><a class="small-link" href="${pageContext.request.contextPath}/assignments?courseId=${enrollment.courseId}">Open</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty enrolledCourses}">
                <tr>
                    <td colspan="3">No courses enrolled yet.</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
