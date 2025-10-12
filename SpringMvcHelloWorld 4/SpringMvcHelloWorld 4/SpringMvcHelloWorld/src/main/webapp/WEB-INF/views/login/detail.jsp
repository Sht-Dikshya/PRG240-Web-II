<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NutriTime User Details - ${login.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <script>
        // Check authentication on page load
        window.addEventListener('load', function() {
            const token = localStorage.getItem('jwt_token');
            const username = localStorage.getItem('username');

            if (!token || !username) {
                // No token or username, redirect to login
                window.location.href = '/SpringMvcHelloWorld/login';
                return;
            }

            // Validate token
            validateToken(token);
        });

        async function validateToken(token) {
            try {
                const response = await fetch('/SpringMvcHelloWorld/api/auth/validate', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ token: token })
                });

                const data = await response.json();

                if (!data.success || data.expired) {
                    // Token is invalid or expired
                    logout();
                }
            } catch (error) {
                // Error validating token
                logout();
            }
        }

        function logout() {
            localStorage.removeItem('jwt_token');
            localStorage.removeItem('username');
            window.location.href = '/SpringMvcHelloWorld/login';
        }
    </script>
</head>
<body class="bg-light">
<div class="min-vh-100">
    <!-- Header -->
    <nav class="navbar navbar-expand-lg navbar-light bg-white shadow">
        <div class="container-fluid">
            <div class="d-flex align-items-center">
                <a href="/SpringMvcHelloWorld/login/list" class="text-primary me-3">
                    <i class="fas fa-arrow-left fs-5"></i>
                </a>
                <i class="fas fa-user text-primary fs-2 me-3"></i>
                <h1 class="navbar-brand mb-0 h3">NutriTime User Details</h1>
            </div>
            <div class="d-flex gap-2">
                <a href="/SpringMvcHelloWorld/login/edit/${login.loginId}"
                   class="btn btn-primary">
                    <i class="fas fa-edit me-2"></i>
                    Edit User
                </a>
                <a href="/SpringMvcHelloWorld/login/list"
                   class="btn btn-secondary">
                    <i class="fas fa-list me-2"></i>
                    Back to List
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

        <!-- Login Profile Card -->
        <div class="card shadow">
            <div class="card-header">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="card-title mb-1">User Information</h5>
                        <p class="card-text text-muted small">Complete details for ${login.name}</p>
                    </div>
                    <div class="d-flex gap-2">
                        <a href="/SpringMvcHelloWorld/login/edit/${login.loginId}"
                           class="btn btn-primary btn-sm">
                            <i class="fas fa-edit me-2"></i>
                            Edit
                        </a>
                        <a href="#"
                           onclick="confirmDelete(${login.loginId}, '${login.name}')"
                           class="btn btn-danger btn-sm">
                            <i class="fas fa-trash me-2"></i>
                            Delete
                        </a>
                    </div>
                </div>
            </div>

            <div class="card-body">
                <div class="row g-4">
                    <!-- Profile Picture and Basic Info -->
                    <div class="col-lg-4">
                        <div class="text-center">
                            <div class="mx-auto mb-4" style="width: 120px; height: 120px;">
                                <div class="bg-primary rounded-circle d-flex align-items-center justify-content-center h-100">
                                        <span class="text-white fs-1 fw-bold">
                                            ${login.name.charAt(0)}
                                        </span>
                                </div>
                            </div>
                            <h4 class="fw-bold">${login.name}</h4>
                            <p class="text-muted fs-5">${login.position}</p>
                            <small class="text-muted">Login ID: ${login.loginId}</small>
                        </div>
                    </div>

                    <!-- Detailed Information -->
                    <div class="col-lg-8">
                        <div class="row g-3">
                            <!-- Name -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-user text-muted me-2"></i>
                                    <small class="text-muted fw-medium">Full Name</small>
                                </div>
                                <p class="mb-0">${login.name}</p>
                            </div>

                            <!-- Position -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-briefcase text-muted me-2"></i>
                                    <small class="text-muted fw-medium">Position</small>
                                </div>
                                <span class="badge bg-primary">${login.position}</span>
                            </div>

                            <!-- Email -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-envelope text-muted me-2"></i>
                                    <small class="text-muted fw-medium">Email Address</small>
                                </div>
                                <a href="mailto:${login.email}" class="text-decoration-none">
                                    ${login.email}
                                </a>
                            </div>

                            <!-- Contact Number -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-phone text-muted me-2"></i>
                                    <small class="text-muted fw-medium">Contact Number</small>
                                </div>
                                <a href="tel:${login.contactNumber}" class="text-decoration-none">
                                    ${login.contactNumber}
                                </a>
                            </div>

                            <!-- Login ID -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-id-card text-muted me-2"></i>
                                    <small class="text-muted fw-medium">Login ID</small>
                                </div>
                                <p class="mb-0">${login.loginId}</p>
                            </div>

                            <!-- Status -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-check-circle text-muted me-2"></i>
                                    <small class="text-muted fw-medium">Status</small>
                                </div>
                                <span class="badge bg-success">Active</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </main>
</div>

<!-- Delete Confirmation Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">Delete User</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-center">
                <div class="mx-auto mb-3" style="width: 60px; height: 60px;">
                    <div class="bg-danger rounded-circle d-flex align-items-center justify-content-center h-100">
                        <i class="fas fa-exclamation-triangle text-white fs-4"></i>
                    </div>
                </div>
                <p class="mb-0">
                    Are you sure you want to delete <strong id="loginName"></strong>?
                    This action cannot be undone.
                </p>
            </div>
            <div class="modal-footer">
                <form id="deleteForm" method="post" class="d-inline">
                    <button type="submit" class="btn btn-danger">
                        Delete
                    </button>
                </form>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                    Cancel
                </button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function confirmDelete(loginId, loginName) {
        document.getElementById('loginName').textContent = loginName;
        document.getElementById('deleteForm').action = '/SpringMvcHelloWorld/login/delete/' + loginId;
        var deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
        deleteModal.show();
    }
</script>
</body>
</html>
