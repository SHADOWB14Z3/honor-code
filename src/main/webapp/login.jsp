<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header class="auth-nav">
    <a class="brand" href="${pageContext.request.contextPath}/">HONOR<span>CODE</span></a>
    <a class="button button-light button-small" href="${pageContext.request.contextPath}/register">Create account</a>
</header>
<main class="auth-layout">
    <section class="auth-intro">
        <span class="eyebrow">ACADEMIC LEARNING PLATFORM</span>
        <h1>Learn with purpose.<br><span>Grow with integrity.</span></h1>
        <p>Courses, study material, assignments and progress tools to support your academic journey.</p>
        <div class="auth-checks">
            <span>✓ Learn at your pace</span>
            <span>✓ Keep your work organized</span>
        </div>
    </section>
    <section class="auth-card">
        <span class="eyebrow">WELCOME BACK</span>
        <h2>Choose your sign in</h2>
        <p class="muted">Continue to your Honor Code workspace.</p>
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert success">${successMessage}</div>
        </c:if>
        <div class="login-options">
            <a class="login-option" href="${pageContext.request.contextPath}/student-login">
                <span class="option-icon">S</span>
                <span><strong>Student</strong><small>Access courses and assignments</small></span>
                <span class="option-arrow">→</span>
            </a>
            <a class="login-option" href="${pageContext.request.contextPath}/teacher-login">
                <span class="option-icon teacher-icon">T</span>
                <span><strong>Teacher</strong><small>Manage classes and learning content</small></span>
                <span class="option-arrow">→</span>
            </a>
        </div>
        <p class="auth-footer">New to Honor Code? <a class="small-link" href="${pageContext.request.contextPath}/register">Create an account</a></p>
    </section>
</main>
</body>
</html>
