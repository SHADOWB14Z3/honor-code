<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Student Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header class="auth-nav">
    <a class="brand" href="${pageContext.request.contextPath}/">HONOR<span>CODE</span></a>
    <a class="text-link" href="${pageContext.request.contextPath}/login">← Login options</a>
</header>
<main class="auth-layout auth-layout-single">
    <section class="auth-intro">
        <span class="eyebrow">STUDENT PORTAL</span>
        <h1>Your learning,<br><span>all in one place.</span></h1>
        <p>Pick up where you left off with your enrolled courses, learning materials and assessments.</p>
    </section>
    <section class="auth-card">
        <span class="eyebrow">STUDENT ACCOUNT</span>
        <h2>Welcome back</h2>
        <p class="muted">Sign in to continue learning.</p>
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/student-login" class="auth-form">
            <div class="form-group">
                <label for="username">Username</label>
                <input id="username" name="username" type="text" placeholder="Enter your username" autocomplete="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input id="password" name="password" type="password" placeholder="Enter your password" autocomplete="current-password" required>
            </div>
            <button type="submit">Login as Student <span>→</span></button>
        </form>
        <p class="auth-footer">Need an account? <a class="small-link" href="${pageContext.request.contextPath}/register">Register here</a></p>
        <p class="auth-switch">Teacher? <a class="small-link" href="${pageContext.request.contextPath}/teacher-login">Go to teacher login</a></p>
    </section>
</main>
</body>
</html>
