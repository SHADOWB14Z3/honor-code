<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Honor Code - Academic Learning Platform</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="landing-page">
<canvas class="landing-wave-canvas" id="landingWaveCanvas" aria-hidden="true"></canvas>
<nav class="landing-nav">
    <a class="brand brand-light" href="${pageContext.request.contextPath}/">HONOR<span>CODE</span></a>
    <div class="landing-links">
        <a href="#how-it-works">How it works</a>
        <a href="#features">For students</a>
        <a href="#features">For teachers</a>
    </div>
    <div class="landing-actions">
        <a class="landing-signin" href="${pageContext.request.contextPath}/login">Sign in</a>
        <a class="button button-primary button-small" href="${pageContext.request.contextPath}/register">Get started <span>→</span></a>
    </div>
</nav>

<main>
    <section class="landing-hero">
        <div class="landing-glow"></div>
        <div class="landing-hero-content">
            <img class="landing-center-logo" src="${pageContext.request.contextPath}/images/honorcode-center-logo.png" alt="HonorCode logo">
            <h1>Turn learning into<br><span>lasting progress.</span></h1>
            <p>One simple space for students and teachers to connect through courses, useful materials and meaningful practice.</p>
            <div class="landing-actions landing-hero-actions">
                <a class="button button-primary" href="${pageContext.request.contextPath}/student-login">I’m a student <span>→</span></a>
                <a class="button button-outline-light" href="${pageContext.request.contextPath}/teacher-login">I’m a teacher</a>
            </div>
            <div class="landing-proof">
                <span><strong>01</strong> Explore courses</span>
                <span><strong>02</strong> Learn at your pace</span>
                <span><strong>03</strong> Track your progress</span>
            </div>
        </div>
    </section>

    <section class="landing-section" id="features">
        <div class="section-heading">
            <span class="eyebrow">MADE FOR LEARNING</span>
            <h2>Everything you need to keep moving forward.</h2>
            <p>Helpful tools for the everyday work of teaching and learning.</p>
        </div>
        <div class="feature-grid">
            <article class="feature-card">
                <div class="feature-icon feature-purple">01</div>
                <h3>Structured courses</h3>
                <p>Find courses created by teachers and keep your enrolled learning in one place.</p>
            </article>
            <article class="feature-card">
                <div class="feature-icon feature-blue">02</div>
                <h3>Study materials</h3>
                <p>Open course notes and learning resources shared by your teacher when you need them.</p>
            </article>
            <article class="feature-card">
                <div class="feature-icon feature-green">03</div>
                <h3>Practice and progress</h3>
                <p>Work through assignments and check your assessment results from your dashboard.</p>
            </article>
        </div>
    </section>

    <section class="how-section" id="how-it-works">
        <div class="how-copy">
            <span class="eyebrow">A SIMPLE ROUTINE</span>
            <h2>Small steps.<br>Steady progress.</h2>
            <p>Join a course, use the materials your teacher provides, and put your understanding into practice.</p>
        </div>
        <div class="how-steps">
            <div class="how-step"><span>01</span><div><strong>Join your learning space</strong><p>Register and find a course that fits your studies.</p></div></div>
            <div class="how-step"><span>02</span><div><strong>Learn and practice</strong><p>Use course materials and complete assignments.</p></div></div>
            <div class="how-step"><span>03</span><div><strong>See your progress</strong><p>Take an assessment and review your results.</p></div></div>
        </div>
    </section>

    <section class="landing-cta">
        <span class="eyebrow">READY WHEN YOU ARE</span>
        <h2>Make your next learning step count.</h2>
        <p>Create an account to get started with Honor Code.</p>
        <a class="button button-white" href="${pageContext.request.contextPath}/register">Create your account <span>→</span></a>
    </section>
</main>

<footer class="landing-footer">
    <a class="brand" href="${pageContext.request.contextPath}/">HONOR<span>CODE</span></a>
    <span>Academic learning, built on curiosity and integrity.</span>
    <a href="${pageContext.request.contextPath}/login">Sign in</a>
</footer>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
