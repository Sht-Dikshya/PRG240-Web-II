<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration Summary - Spring MVC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="user-summary">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h3 class="mb-0">
                            <i class="bi bi-check-circle-fill me-2"></i>
                            User Registration Successful!
                        </h3>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-success" role="alert">
                            <h5 class="alert-heading">Congratulations!</h5>
                            <p>The user has been registered successfully. Here are the details:</p>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <h5 class="text-primary mb-3">User Information</h5>
                                <table class="table table-borderless">
                                    <tr>
                                        <td class="fw-bold">User ID:</td>
                                        <td>${user.userId}</td>
                                    </tr>
                                    <tr>
                                        <td class="fw-bold">Full Name:</td>
                                        <td>${user.name}</td>
                                    </tr>
                                    <tr>
                                        <td class="fw-bold">Email Address:</td>
                                        <td>${user.email}</td>
                                    </tr>
                                    <tr>
                                        <td class="fw-bold">Contact Number:</td>
                                        <td>${user.contactNumber}</td>
                                    </tr>
                                    <tr>
                                        <td class="fw-bold">Position:</td>
                                        <td>${user.position}</td>
                                    </tr>
                                </table>
                            </div>
                            
                            <div class="col-md-6">
                                <div class="card bg-light">
                                    <div class="card-body">
                                        <h6 class="card-title text-muted">Registration Details</h6>
                                        <p class="card-text">
                                            <small class="text-muted">
                                                Registration completed at: ${pageContext.request.serverName}<br>
                                                Status: Active
                                            </small>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <hr class="my-4">
                        
                        <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                            <a href="${pageContext.request.contextPath}/user/register"
                               class="btn btn-primary btn-lg">Register Another User</a>
                            <a href="${pageContext.request.contextPath}/" 
                               class="btn btn-outline-secondary btn-lg">Back to Home</a>
                        </div>
                    </div>
                </div>
                
                <div class="text-center mt-4">
                    <p class="text-muted">
                        <small>
                            This is a demonstration of Spring MVC form handling with:<br>
                            • Controller-based routing • Service layer implementation • POJO data binding • JSP view rendering
                        </small>
                    </p>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
