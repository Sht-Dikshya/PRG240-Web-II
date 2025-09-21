<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${formTitle} - Employee Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="min-vh-100">
        <!-- Header -->
        <nav class="navbar navbar-expand-lg navbar-light bg-white shadow">
            <div class="container-fluid">
                <div class="d-flex align-items-center">
                    <a href="/SpringMvcHelloWorld/employee/list" class="text-primary me-3">
                        <i class="fas fa-arrow-left fs-5"></i>
                    </a>
                    <i class="fas fa-user-edit text-primary fs-2 me-3"></i>
                    <h1 class="navbar-brand mb-0 h3">${formTitle}</h1>
                </div>
                <div class="d-flex">
                    <a href="/SpringMvcHelloWorld/employee/list" 
                       class="btn btn-secondary">
                        <i class="fas fa-list me-2"></i>
                        Employee List
                    </a>
                </div>
            </div>
        </nav>

        <!-- Main Content -->
        <main class="container py-4">
            <!-- Flash Messages -->
            <c:if test="${not empty success}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="fas fa-check-circle me-2"></i>
                    ${success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="fas fa-exclamation-circle me-2"></i>
                    ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <!-- Employee Form -->
            <div class="card shadow">
                <div class="card-header">
                    <h5 class="card-title mb-1">Employee Information</h5>
                    <p class="card-text text-muted small">
                        <c:choose>
                            <c:when test="${formAction == 'add'}">
                                Please fill in the details to add a new employee.
                            </c:when>
                            <c:otherwise>
                                Please update the employee information below.
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
                
                <form method="post" 
                      action="/SpringMvcHelloWorld/employee/${formAction}" 
                      class="p-4">
                    
                    <c:if test="${formAction == 'edit'}">
                        <input type="hidden" name="employeeId" value="${employee.employeeId}" />
                    </c:if>
                    
                    <div class="row g-3">
                        <!-- Name Field -->
                        <div class="col-md-6">
                            <label for="name" class="form-label">
                                Full Name <span class="text-danger">*</span>
                            </label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="fas fa-user text-muted"></i>
                                </span>
                                <input type="text" 
                                       name="name" 
                                       id="name" 
                                       value="${employee.name}"
                                       required
                                       class="form-control"
                                       placeholder="Enter full name">
                            </div>
                        </div>

                        <!-- Position Field -->
                        <div class="col-md-6">
                            <label for="position" class="form-label">
                                Position <span class="text-danger">*</span>
                            </label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="fas fa-briefcase text-muted"></i>
                                </span>
                                <input type="text" 
                                       name="position" 
                                       id="position" 
                                       value="${employee.position}"
                                       required
                                       class="form-control"
                                       placeholder="Enter job position">
                            </div>
                        </div>

                        <!-- Address Field -->
                        <div class="col-md-6">
                            <label for="address" class="form-label">
                                Address <span class="text-danger">*</span>
                            </label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="fas fa-briefcase text-muted"></i>
                                </span>
                                <input type="text"
                                       name="address"
                                       id="address"
                                       value="${employee.address}"
                                       required
                                       class="form-control"
                                       placeholder="Enter your address">
                            </div>
                        </div>

                        <!-- Email Field -->
                        <div class="col-md-6">
                            <label for="email" class="form-label">
                                Email Address <span class="text-danger">*</span>
                            </label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="fas fa-envelope text-muted"></i>
                                </span>
                                <input type="email" 
                                       name="email" 
                                       id="email" 
                                       value="${employee.email}"
                                       required
                                       class="form-control"
                                       placeholder="Enter email address">
                            </div>
                        </div>

                        <!-- Contact Number Field -->
                        <div class="col-md-6">
                            <label for="contactNumber" class="form-label">
                                Contact Number <span class="text-danger">*</span>
                            </label>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="fas fa-phone text-muted"></i>
                                </span>
                                <input type="tel" 
                                       name="contactNumber" 
                                       id="contactNumber" 
                                       value="${employee.contactNumber}"
                                       required
                                       class="form-control"
                                       placeholder="Enter contact number">
                            </div>
                        </div>
                    </div>

                    <!-- Form Actions -->
                    <div class="d-flex justify-content-end gap-3 mt-4 pt-3 border-top">
                        <a href="/SpringMvcHelloWorld/employee/list" 
                           class="btn btn-secondary">
                            <i class="fas fa-times me-2"></i>
                            Cancel
                        </a>
                        <button type="submit" 
                                class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>
                            <c:choose>
                                <c:when test="${formAction == 'add'}">
                                    Add Employee
                                </c:when>
                                <c:otherwise>
                                    Update Employee
                                </c:otherwise>
                            </c:choose>
                        </button>
                    </div>
                </form>
            </div>

            <!-- Form Help -->
            <div class="mt-4">
                <div class="alert alert-info">
                    <div class="d-flex">
                        <div class="flex-shrink-0">
                            <i class="fas fa-info-circle text-info"></i>
                        </div>
                        <div class="ms-3">
                            <h6 class="alert-heading">Form Guidelines</h6>
                            <ul class="mb-0 small">
                                <li>All fields marked with <span class="text-danger">*</span> are required</li>
                                <li>Email must be in a valid format (e.g., user@example.com)</li>
                                <li>Contact number should include country code if applicable</li>
                                <li>Position should reflect the employee's job title or role</li>
                                <li>Address should reflect the employee's temporary living location</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Form validation
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.querySelector('form');
            const nameInput = document.getElementById('name');
            const emailInput = document.getElementById('email');
            const contactInput = document.getElementById('contactNumber');
            const positionInput = document.getElementById('position');
            const addressInput = document.getElementById('address');

            form.addEventListener('submit', function(e) {
                let isValid = true;
                
                // Clear previous error styles
                [nameInput, emailInput, contactInput, positionInput, addressInput].forEach(input => {
                    input.classList.remove('is-invalid');
                });

                // Validate name
                if (!nameInput.value.trim()) {
                    nameInput.classList.add('is-invalid');
                    isValid = false;
                }

                // Validate email
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailInput.value.trim() || !emailRegex.test(emailInput.value)) {
                    emailInput.classList.add('is-invalid');
                    isValid = false;
                }

                // Validate contact number
                if (!contactInput.value.trim()) {
                    contactInput.classList.add('is-invalid');
                    isValid = false;
                }

                // Validate position
                if (!positionInput.value.trim()) {
                    positionInput.classList.add('is-invalid');
                    isValid = false;
                }

                // Validate address
                if (!addressInput.value.trim()) {
                    addressInput.classList.add('is-invalid');
                    isValid = false;
                }

                if (!isValid) {
                    e.preventDefault();
                    alert('Please fill in all required fields correctly.');
                }
            });
        });
    </script>
</body>
</html>
