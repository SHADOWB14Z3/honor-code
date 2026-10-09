<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Student Dashboard</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="dashboard-page">
<div class="dashboard-shell">
    <aside class="dashboard-sidebar">
        <a class="brand brand-light" href="${pageContext.request.contextPath}/">HONOR<span>CODE</span></a>
        <span class="sidebar-caption">STUDENT SPACE</span>
        <nav class="sidebar-nav">
            <a class="sidebar-link active" href="${pageContext.request.contextPath}/student-dashboard"><span>⌂</span> Dashboard</a>
            <a class="sidebar-link" href="${pageContext.request.contextPath}/enroll"><span>▤</span> My courses</a>
            <a class="sidebar-link" href="${pageContext.request.contextPath}/assessment"><span>✓</span> Assessments</a>
            <a class="sidebar-link" href="#available-courses"><span>＋</span> Explore courses</a>
        </nav>
        <div class="sidebar-bottom">
            <div class="sidebar-profile"><span class="avatar">${sessionScope.user.fullName.substring(0,1)}</span><span><strong>${sessionScope.user.fullName}</strong><small>Student</small></span></div>
            <a class="sidebar-link signout-link" href="${pageContext.request.contextPath}/logout"><span>↗</span> Sign out</a>
        </div>
    </aside>

    <div class="dashboard-main">
        <header class="app-header">
            <div><span class="eyebrow">YOUR LEARNING SPACE</span><h1>Dashboard</h1></div>
            <div class="header-right"><span class="user-chip">Student account</span></div>
        </header>
        <main class="dashboard-content">
            <c:if test="${not empty errorMessage}">
                <div class="alert error">${errorMessage}</div>
            </c:if>

            <section class="welcome-panel">
                <div>
                    <span class="welcome-label">WELCOME BACK</span>
                    <h2>${sessionScope.user.fullName}</h2>
                    <p>Keep learning, keep growing. Your next step is ready when you are.</p>
                </div>
                <a class="button button-white" href="${pageContext.request.contextPath}/assessment">Start assessment <span>→</span></a>
                <div class="welcome-decoration"></div>
            </section>

            <section class="stat-grid">
                <article class="stat-card"><span class="stat-symbol stat-purple">▤</span><span class="stat-label">Enrolled courses</span><strong>${enrollments.size()}</strong><small>Courses in your learning space</small></article>
                <article class="stat-card"><span class="stat-symbol stat-blue">✓</span><span class="stat-label">Results recorded</span><strong>${results.size()}</strong><small>Assessment results saved</small></article>
                <article class="stat-card"><span class="stat-symbol stat-green">↗</span><span class="stat-label">Average score</span><strong><c:choose><c:when test="${not empty results}">${averageScore}%</c:when><c:otherwise>—</c:otherwise></c:choose></strong><small>Across your saved results</small></article>
            </section>

            <section class="content-panel">
                <div class="panel-heading"><div><span class="eyebrow">YOUR STUDIES</span><h2>Continue learning</h2></div><a class="small-link" href="${pageContext.request.contextPath}/enroll">View all courses <span>→</span></a></div>
                <c:choose>
                    <c:when test="${not empty enrollments}">
                        <div class="learning-grid">
                            <c:forEach items="${enrollments}" var="enrollment">
                                <article class="learning-card">
                                    <div class="learning-card-top"><span class="course-mark">HC</span><span class="course-status">ENROLLED</span></div>
                                    <h3>${enrollment.courseTitle}</h3>
                                    <p>Your course materials and practice assignments are ready.</p>
                                    <div class="course-actions">
                                        <a href="${pageContext.request.contextPath}/materials?courseId=${enrollment.courseId}">Study materials</a>
                                        <a href="${pageContext.request.contextPath}/assignments?courseId=${enrollment.courseId}">Assignments</a>
                                    </div>
                                </article>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-state compact"><h3>No courses yet</h3><p>Explore an available course to start building your learning space.</p></div>
                    </c:otherwise>
                </c:choose>
            </section>

            <section class="content-panel" id="available-courses">
                <div class="panel-heading"><div><span class="eyebrow">FIND YOUR NEXT COURSE</span><h2>Explore courses</h2></div><a class="small-link" href="#available-courses">See available courses <span>↓</span></a></div>
                <c:choose>
                    <c:when test="${not empty courses}">
                        <div class="learning-grid">
                            <c:forEach items="${courses}" var="course">
                                <article class="learning-card">
                                    <div class="learning-card-top"><span class="course-mark course-mark-blue">HC</span><span class="course-status">AVAILABLE</span></div>
                                    <h3>${course.title}</h3>
                                    <p>${course.description}</p>
                                    <small class="teacher-byline">Led by ${course.teacherName}</small>
                                    <form action="${pageContext.request.contextPath}/enroll" method="post">
                                        <input type="hidden" name="courseId" value="${course.id}">
                                        <button class="button button-primary course-enroll" type="submit">Enroll in course <span>→</span></button>
                                    </form>
                                </article>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise><p class="empty-copy">You’re all caught up. There are no new courses to join.</p></c:otherwise>
                </c:choose>
            </section>

            <c:if test="${not empty results}">
                <section class="content-panel">
                    <div class="panel-heading"><div><span class="eyebrow">KEEP TRACK</span><h2>Recent results</h2></div><a class="small-link" href="${pageContext.request.contextPath}/assessment">Take an assessment <span>→</span></a></div>
                    <div class="results-list">
                        <c:forEach items="${results}" var="result">
                            <div class="result-row"><span class="result-dot"></span><span class="result-name">${result.courseTitle} · Grade ${result.grade}</span><span class="result-date">${result.updatedAt}</span><strong>${result.score}%</strong></div>
                        </c:forEach>
                    </div>
                </section>
            </c:if>
        </main>
    </div>
</div>
</body>
</html>
