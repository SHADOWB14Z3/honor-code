<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Java Assessment</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="dashboard-page">
<header class="app-header">
    <a class="brand" href="${pageContext.request.contextPath}/student-dashboard">HONOR<span>CODE</span></a>
    <div class="header-right">
        <span class="user-chip">${sessionScope.user.fullName}</span>
        <a class="text-link" href="${pageContext.request.contextPath}/logout">Sign out</a>
    </div>
</header>
<main class="assessment-wrap">
    <a class="back-link" href="${pageContext.request.contextPath}/student-dashboard">← Back to dashboard</a>

    <c:if test="${not empty errorMessage}">
        <div class="alert error">${errorMessage}</div>
    </c:if>

    <c:if test="${not empty assessmentScore}">
        <section class="result-banner">
            <span class="result-icon">✓</span>
            <div>
                <span class="eyebrow">ASSESSMENT SAVED · ${assessmentCourse}</span>
                <h1>${assessmentScore}% <span>· ${assessmentCorrect} of 10 correct</span></h1>
                <p>Your latest course result has been updated on your dashboard.</p>
            </div>
            <a class="button button-primary" href="${pageContext.request.contextPath}/student-dashboard">View dashboard <span>→</span></a>
        </section>
    </c:if>

    <section class="assessment-intro">
        <span class="eyebrow">HONOR CODE · KNOWLEDGE CHECK</span>
        <h1>Java fundamentals</h1>
        <p>Review Java object-oriented programming, collections and core syntax. Your answers are checked on the server and the latest result is saved for the selected course.</p>
    </section>

    <c:choose>
        <c:when test="${empty enrolledCourses}">
            <section class="empty-state">
                <div class="empty-icon">01</div>
                <h2>Enroll in a course first</h2>
                <p>Choose one of your enrolled courses when you start an assessment.</p>
                <a class="button button-primary" href="${pageContext.request.contextPath}/student-dashboard">Explore courses <span>→</span></a>
            </section>
        </c:when>
        <c:otherwise>
            <form method="post" action="${pageContext.request.contextPath}/assessment" class="assessment-form">
                <section class="assessment-toolbar">
                    <div>
                        <span class="eyebrow">ASSESSMENT DETAILS</span>
                        <p>Choose an enrolled course. Unanswered questions count as incorrect.</p>
                    </div>
                    <div class="assessment-tools">
                        <label class="course-picker" for="courseId">
                            <span>Save result to</span>
                            <select id="courseId" name="courseId" required>
                                <option value="">Select a course</option>
                                <c:forEach items="${enrolledCourses}" var="enrollment">
                                    <option value="${enrollment.courseId}">${enrollment.courseTitle}</option>
                                </c:forEach>
                            </select>
                        </label>
                        <div class="assessment-timer"><span>TIME LEFT</span><strong id="assessmentTimer">25:00</strong></div>
                    </div>
                </section>

                <div id="assessmentTimeNotice" class="time-notice" aria-live="polite"></div>
                <div id="assessmentForm" data-assessment-form>
                <c:forEach items="${questions}" var="question" varStatus="questionStatus">
                    <fieldset class="question-card">
                        <legend>
                            <span class="question-number">Q${question.number}</span>
                            <span>${question.question}</span>
                        </legend>
                        <div class="answer-options">
                            <c:forEach items="${question.options}" var="option" varStatus="optionStatus">
                                <label class="answer-option">
                                    <input type="radio" name="answer_${questionStatus.index}" value="${optionStatus.index}">
                                    <span class="answer-letter">${optionStatus.index + 1}</span>
                                    <span><c:out value="${option}"/></span>
                                </label>
                            </c:forEach>
                        </div>
                    </fieldset>
                </c:forEach>

                <div class="assessment-submit">
                    <p>Take your time and review your choices before submitting.</p>
                    <button class="button button-primary" type="submit">Submit assessment <span>→</span></button>
                </div>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</main>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
