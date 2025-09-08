<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Signup Summary - NutriTime</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/signup.css">
</head>
<body class="signup-summary">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header bg-success text-white">
                    <h3 class="mb-0">
                        Signup Successful!
                    </h3>
                </div>
                <div class="card-body">
                    <div class="alert alert-success" role="alert">
                        <h5 class="alert-heading">Welcome, ${user.fullname}!</h5>
                        <p>Your NutriTime account has been created successfully. Here are your details:</p>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <h5 class="text-primary mb-3">User Information</h5>
                            <table class="table table-borderless">
                                <tr>
                                    <td class="fw-bold">Full Name:</td>
                                    <td>${signup.fullName}</td>
                                </tr>
                                <tr>
                                    <td class="fw-bold">Email:</td>
                                    <td>${signup.email}</td>
                                </tr>
                                <tr>
                                    <td class="fw-bold">Phone Number:</td>
                                    <td>${signup.phone}</td>
                                </tr>
                                <tr>
                                    <td class="fw-bold">Password:</td>
                                    <td>${signup.password}</td> <!-- Show password here -->
                                </tr>
                                <tr>
                                    <td class="fw-bold">Confirm Password:</td>
                                    <td>${signup.confirmPassword}</td>
                                </tr>
                            </table>
                        </div>

                        <div class="col-md-6">
                            <div class="card bg-light">
                                <div class="card-body">
                                    <h6 class="card-title text-muted">Account Details</h6>
                                    <p class="card-text">
                                        <small class="text-muted">
                                            Signup completed at: ${pageContext.request.serverName}<br>
                                            Status: Active
                                        </small>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <hr class="my-4">

                    <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                        <a href="${pageContext.request.contextPath}/user/signup"
                           class="btn btn-primary btn-lg">Register Another Account</a>
                        <a href="${pageContext.request.contextPath}/user/login"
                           class="btn btn-outline-secondary btn-lg">Go to Login</a>
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
