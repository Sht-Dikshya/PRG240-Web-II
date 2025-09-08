<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 9/5/2025
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NutriTime - Signup</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/signup.css">
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

<section class="signup-section">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-5">
                <div class="signup-card shadow-lg p-4 rounded">
                    <h2 class="text-center mb-4">Create Your NutriTime Account</h2>

                    <!-- Spring Signup Form -->
                    <form:form action="${pageContext.request.contextPath}/user/signup" method="post" modelAttribute="signup">

                        <div class="mb-3">
                            <label for="fullName" class="form-label">Full Name *</label>
                            <form:input path="fullName" id="fullName" class="form-control" required="true"/>
                            <form:errors path="fullName" cssClass="text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email *</label>
                            <form:input path="email" id="email" class="form-control" required="true"/>
                            <form:errors path="email" cssClass="text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="phone" class="form-label">Phone Number *</label>
                            <form:input path="phone" id="phone" class="form-control" required="true"/>
                            <form:errors path="phone" cssClass="text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Password *</label>
                            <form:password path="password" id="password" class="form-control" required="true"/>
                            <form:errors path="password" cssClass="text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Confirm Password *</label>
                            <form:password path="confirmPassword" id="confirmPassword" class="form-control" required="true"/>
                            <form:errors path="confirmPassword" cssClass="text-danger"/>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-success btn-lg">Sign Up</button>
                        </div>
                    </form:form>
                    <p class="text-center mt-3">
                        Already have an account? <a href="${pageContext.request.contextPath}/user/login">Login</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
