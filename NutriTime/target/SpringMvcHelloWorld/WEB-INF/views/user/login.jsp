<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 9/5/2025
  Time: 7:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NutriTime - Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<nav class="navbar navbar-expand-lg custom-navbar">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">
            <img src="https://png.pngtree.com/png-clipart/20230923/original/pngtree-green-beauty-and-leaf-logo-sign-vector-natural-line-vector-png-image_12733148.png"
                 alt="NutriTime Logo" class="logo-icon me-2" width="50" height="50">
            <span class="brand-text">NutriTime</span>
        </a>
    </div>
</nav>

<section class="login-section">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-5">
                <div class="login-card shadow-lg p-4 rounded">
                    <h2 class="text-center mb-4">Login to NutriTime</h2>

                    <!-- Spring Login Form -->
                    <form:form action="${pageContext.request.contextPath}/user/login" method="post" modelAttribute="login">

                        <div class="mb-3">
                            <label for="username" class="form-label">Username *</label>
                            <form:input path="username" id="username" class="form-control" required="true"/>
                            <form:errors path="username" cssClass="text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Password *</label>
                            <form:password path="password" id="password" class="form-control" required="true"/>
                            <form:errors path="password" cssClass="text-danger"/>
                        </div>

                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <div class="form-check">
                                <form:checkbox path="rememberMe" id="rememberMe" class="form-check-input"/>
                                <label class="form-check-label" for="rememberMe">Remember me</label>
                            </div>
                            <a href="#" class="forgot-link">Forgot Password?</a>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-lg">Login</button>
                        </div>
                    </form:form>

                    <p class="text-center mt-3">
                        Don't have an account? <a href="${pageContext.request.contextPath}/user/signup">Sign Up</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

