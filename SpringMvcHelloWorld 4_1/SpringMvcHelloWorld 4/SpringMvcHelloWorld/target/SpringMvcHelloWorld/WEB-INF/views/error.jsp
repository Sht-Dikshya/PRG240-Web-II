<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Spring MVC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-danger text-white">
                        <h3 class="mb-0">Error Occurred</h3>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-danger" role="alert">
                            <h5 class="alert-heading">Oops! Something went wrong</h5>
                            <p>We're sorry, but an error occurred while processing your request.</p>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <h6>Error Details:</h6>
                                <p><strong>Status Code:</strong> ${pageContext.errorData.statusCode}</p>
                                <p><strong>Request URI:</strong> ${pageContext.errorData.requestURI}</p>
                                <p><strong>Servlet Name:</strong> ${pageContext.errorData.servletName}</p>
                            </div>
                            <div class="col-md-6">
                                <h6>What you can do:</h6>
                                <ul>
                                    <li>Check the URL for typos</li>
                                    <li>Go back to the <a href="${pageContext.request.contextPath}/">home page</a></li>
                                    <li>Try refreshing the page</li>
                                </ul>
                            </div>
                        </div>
                        
                        <hr class="my-4">
                        
                        <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Go to Home</a>
                            <button onclick="history.back()" class="btn btn-outline-secondary">Go Back</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>



