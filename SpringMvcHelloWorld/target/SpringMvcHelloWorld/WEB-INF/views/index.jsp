<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Spring MVC Hello World - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="home-page">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white text-center">
                        <h1 class="mb-0">Spring MVC Hello World</h1>
                    </div>
                    <div class="card-body text-center">
                        <div class="mb-4">
                            <h3 class="text-primary">Welcome to Spring MVC Application</h3>
                            <p class="lead">This is a demonstration of Spring MVC with user management functionality.</p>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h5 class="card-title text-primary">User Registration</h5>
                                        <p class="card-text">Register new users with their details.</p>
                                        <a href="<%= request.getContextPath() %>/employee/register" 
                                           class="btn btn-primary btn-lg w-100">
                                            Register User
                                        </a>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h5 class="card-title text-success">API Testing</h5>
                                        <p class="card-text">Test the REST API endpoints for user management.</p>
                                        <a href="<%= request.getContextPath() %>/api/user/health"
                                           class="btn btn-success btn-lg w-100">
                                            Test API
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

