<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Courses</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div><strong>Honor Code</strong></div>
    <div>
        <a href="${pageContext.request.contextPath}/student-dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/enroll">My Courses</a>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</nav>

<div class="container">
    <div class="card">
        <h2>Available Courses</h2>
        <div class="grid">
            <c:forEach items="${courses}" var="course">
                <div class="course-card">
                    <h3>${course.title}</h3>
                    <p>${course.description}</p>
                    <p><strong>Teacher:</strong> ${course.teacherName}</p>
                    <form method="post" action="${pageContext.request.contextPath}/enroll">
                        <input type="hidden" name="courseId" value="${course.id}">
                        <button type="submit">Enroll</button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
