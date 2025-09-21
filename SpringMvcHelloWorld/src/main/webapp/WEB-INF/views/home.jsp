<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spring MVC Hello World - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="home-page">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white text-center">
                        <h1 class="mb-0">
                            <i class="bi bi-house-fill me-2"></i>
                            Spring MVC Hello World
                        </h1>
                    </div>
                    <div class="card-body text-center">
                        <div class="mb-4">
                            <h3 class="text-primary">Welcome to Spring MVC Application</h3>
                            <p class="lead">This is a demonstration of Spring MVC with employee management functionality.</p>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h5 class="card-title text-primary">
                                            <i class="bi bi-person-plus me-2"></i>
                                            Employee Registration
                                        </h5>
                                        <p class="card-text">Register new employees with their details including Employee ID, name, email, contact number, and position.</p>
                                        <a href="${pageContext.request.contextPath}/employee/register" 
                                           class="btn btn-primary btn-lg w-100">
                                            Register Employee
                                        </a>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h5 class="card-title text-success">
                                            <i class="bi bi-gear me-2"></i>
                                            API Testing
                                        </h5>
                                        <p class="card-text">Test the REST API endpoints for employee registration and other services.</p>
                                        <a href="${pageContext.request.contextPath}/api-test" 
                                           class="btn btn-success btn-lg w-100">
                                            API Test Page
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <hr class="my-4">
                        
                        <div class="row">
                            <div class="col-md-12">
                                <h5 class="text-muted mb-3">Application Features</h5>
                                <div class="row">
                                    <div class="col-md-6">
                                        <ul class="list-unstyled">
                                            <li><i class="bi bi-check-circle text-success me-2"></i>Spring MVC Framework</li>
                                            <li><i class="bi bi-check-circle text-success me-2"></i>JSP View Technology</li>
                                            <li><i class="bi bi-check-circle text-success me-2"></i>Form Validation</li>
                                            <li><i class="bi bi-check-circle text-success me-2"></i>REST API Support</li>
                                        </ul>
                                    </div>
                                    <div class="col-md-6">
                                        <ul class="list-unstyled">
                                            <li><i class="bi bi-check-circle text-success me-2"></i>Employee Management</li>
                                            <li><i class="bi bi-check-circle text-success me-2"></i>JSON Data Exchange</li>
                                            <li><i class="bi bi-check-circle text-success me-2"></i>Bootstrap UI</li>
                                            <li><i class="bi bi-check-circle text-success me-2"></i>Error Handling</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="text-center mt-4">
                    <p class="text-muted">
                        <small>
                            Built with Spring MVC 6.2.10 | Java 17 | Bootstrap 5.3.3
                        </small>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
