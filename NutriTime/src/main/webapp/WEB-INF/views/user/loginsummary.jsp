<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 9/5/2025
  Time: 7:47 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Summary - NutriTime</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body class="login-summary">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header bg-success text-white">
                    <h3 class="mb-0">
                        <i class="bi bi-check-circle-fill me-2"></i>
                        Login Successful!
                    </h3>
                </div>
                <div class="card-body">
                    <div class="alert alert-success" role="alert">
                        <h5 class="alert-heading">Welcome, ${login.username}!</h5>
                        <p>You have logged in successfully. Here are your login details:</p>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <h5 class="text-primary mb-3">Login Information</h5>
                            <table class="table table-borderless">
                                <tr>
                                    <td class="fw-bold">Username:</td>
                                    <td>${login.username}</td>
                                </tr>
                                <tr>
                                    <td class="fw-bold">Remember Me:</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${login.rememberMe}">Yes</c:when>
                                            <c:otherwise>No</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    <hr class="my-4">

                    <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                        <a href="${pageContext.request.contextPath}/user/login"
                           class="btn btn-danger btn-lg">Logout</a>
                        <a href="${pageContext.request.contextPath}/user/login"
                           class="btn btn-primary btn-lg">Go to Dashboard</a>
                    </div>
                </div>
            </div>

            <div class="text-center mt-4">
                <p class="text-muted">
                    <small>
                        This is a demonstration of Spring MVC form handling with:<br>
                        • Controller-based routing • POJO data binding • JSP view rendering
                    </small>
                </p>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>