<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Teacher Dashboard</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="dashboard-page">
<div class="dashboard-shell">
    <aside class="dashboard-sidebar">
        <a class="brand brand-light" href="${pageContext.request.contextPath}/">HONOR<span>CODE</span></a>
        <span class="sidebar-caption">TEACHER SPACE</span>
        <nav class="sidebar-nav">
            <a class="sidebar-link active" href="${pageContext.request.contextPath}/teacher-dashboard"><span>⌂</span> Dashboard</a>
            <a class="sidebar-link" href="${pageContext.request.contextPath}/add-course.jsp"><span>＋</span> Create a course</a>
        </nav>
        <div class="sidebar-bottom">
            <div class="sidebar-profile"><span class="avatar">${sessionScope.user.fullName.substring(0,1)}</span><span><strong>${sessionScope.user.fullName}</strong><small>Teacher</small></span></div>
            <a class="sidebar-link signout-link" href="${pageContext.request.contextPath}/logout"><span>↗</span> Sign out</a>
        </div>
    </aside>

    <div class="dashboard-main">
        <header class="app-header">
            <div><span class="eyebrow">YOUR TEACHING SPACE</span><h1>Dashboard</h1></div>
            <div class="header-right"><a class="button button-primary button-small" href="${pageContext.request.contextPath}/add-course.jsp">＋ Create course</a></div>
        </header>
        <main class="dashboard-content">
            <c:if test="${not empty errorMessage}">
                <div class="alert error">${errorMessage}</div>
            </c:if>
            <section class="welcome-panel teacher-welcome">
                <div>
                    <span class="welcome-label">WELCOME BACK</span>
                    <h2>${sessionScope.user.fullName}</h2>
                    <p>Share knowledge and make your courses a great place to learn.</p>
                </div>
                <a class="button button-white" href="${pageContext.request.contextPath}/add-course.jsp">Create a course <span>→</span></a>
                <div class="welcome-decoration"></div>
            </section>
            <section class="content-panel">
                <div class="panel-heading"><div><span class="eyebrow">YOUR CLASSROOMS</span><h2>Your courses</h2></div><a class="small-link" href="${pageContext.request.contextPath}/add-course.jsp">Add a course <span>→</span></a></div>
                <c:choose>
                    <c:when test="${not empty courses}">
                        <div class="learning-grid">
                            <c:forEach items="${courses}" var="course">
                                <article class="learning-card">
                                    <div class="learning-card-top"><span class="course-mark">HC</span><span class="course-status">COURSE</span></div>
                                    <h3>${course.title}</h3>
                                    <p>${course.description}</p>
                                    <div class="course-actions">
                                        <a href="${pageContext.request.contextPath}/materials?courseId=${course.id}">Study materials</a>
                                        <a href="${pageContext.request.contextPath}/assignments?courseId=${course.id}">Assignments</a>
                                    </div>
                                </article>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-state compact"><h3>Your courses start here</h3><p>Create a course to share materials and assignments with students.</p><a class="button button-primary" href="${pageContext.request.contextPath}/add-course.jsp">Create first course <span>→</span></a></div>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>
    </div>
</div>
</body>
</html>
