<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Registration - Spring MVC</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="employee-form">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h3 class="mb-0">Employee Registration Form</h3>
                    </div>
                    <div class="card-body">
                        <form id="employeeForm" action="${pageContext.request.contextPath}/employee/register" method="POST">
                            <div class="mb-3">
                                <label for="employeeId" class="form-label">Employee ID *</label>
                                <input type="number" class="form-control" id="employeeId" name="employeeId" 
                                       placeholder="Enter employee ID" required>
                                <div id="employeeIdError" class="text-danger"></div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="name" class="form-label">Full Name *</label>
                                <input type="text" class="form-control" id="name" name="name" 
                                       placeholder="Enter your full name" required>
                                <div id="nameError" class="text-danger"></div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="email" class="form-label">Email Address *</label>
                                <input type="email" class="form-control" id="email" name="email" 
                                       placeholder="Enter your email address" required>
                                <div id="emailError" class="text-danger"></div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="contactNumber" class="form-label">Contact Number *</label>
                                <input type="tel" class="form-control" id="contactNumber" name="contactNumber" 
                                       placeholder="Enter your contact number (10 digits)" required>
                                <div id="contactNumberError" class="text-danger"></div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="position" class="form-label">Position *</label>
                                <select class="form-select" id="position" name="position" required>
                                    <option value="">Select a position</option>
                                    <option value="Software Developer">Software Developer</option>
                                    <option value="System Analyst">System Analyst</option>
                                    <option value="Project Manager">Project Manager</option>
                                    <option value="Business Analyst">Business Analyst</option>
                                    <option value="QA Engineer">QA Engineer</option>
                                    <option value="DevOps Engineer">DevOps Engineer</option>
                                    <option value="UI/UX Designer">UI/UX Designer</option>
                                    <option value="Data Scientist">Data Scientist</option>
                                    <option value="Other">Other</option>
                                </select>
                                <div id="positionError" class="text-danger"></div>
                            </div>
                            
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button type="submit" class="btn btn-primary btn-lg" id="submitBtn">
                                    <span id="submitText">Register Employee</span>
                                    <span id="loadingText" style="display: none;">
                                        <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                                        Processing...
                                    </span>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                
                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">Back to Home</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        document.getElementById('employeeForm').addEventListener('submit', function(e) {
            // Clear previous errors
            clearErrors();
            
            // Show loading state
            const submitBtn = document.getElementById('submitBtn');
            const submitText = document.getElementById('submitText');
            const loadingText = document.getElementById('loadingText');
            
            submitBtn.disabled = true;
            submitText.style.display = 'none';
            loadingText.style.display = 'inline';
            
            // Let the form submit normally to the POST mapping
            // The form will be processed by the EmployeeController.registerEmployee method
        });
        
        function clearErrors() {
            const errorElements = document.querySelectorAll('.text-danger');
            errorElements.forEach(element => {
                element.textContent = '';
            });
        }
    </script>
</body>
</html>
