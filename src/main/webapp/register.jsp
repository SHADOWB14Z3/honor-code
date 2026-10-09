<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Register</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header class="auth-nav">
    <a class="brand" href="${pageContext.request.contextPath}/">HONOR<span>CODE</span></a>
    <a class="text-link" href="${pageContext.request.contextPath}/login">Already registered? Sign in</a>
</header>
<main class="auth-layout auth-layout-single">
    <section class="auth-intro">
        <span class="eyebrow">JOIN HONOR CODE</span>
        <h1>Start your next<br><span>learning chapter.</span></h1>
        <p>Create a student or teacher account and make a little more room for learning.</p>
    </section>
    <section class="auth-card">
        <span class="eyebrow">CREATE AN ACCOUNT</span>
        <h2>Welcome to Honor Code</h2>
        <p class="muted">Add a few details to get started.</p>
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/register" class="auth-form">
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input id="fullName" name="fullName" type="text" autocomplete="name" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input id="email" name="email" type="email" autocomplete="email" required>
            </div>
            <div class="form-group">
                <label for="username">Username</label>
                <input id="username" name="username" type="text" autocomplete="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input id="password" name="password" type="password" autocomplete="new-password" required>
            </div>
            <div class="form-group">
                <label for="role">Account type</label>
                <select id="role" name="role">
                    <option value="STUDENT">Student</option>
                    <option value="TEACHER">Teacher</option>
                </select>
            </div>
            <button type="submit">Create account <span>→</span></button>
        </form>
    </section>
</main>
</body>
</html>
