<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Details - ${user.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="min-vh-100">
    <!-- Header -->
    <nav class="navbar navbar-expand-lg navbar-light bg-white shadow">
        <div class="container-fluid">
            <div class="d-flex align-items-center">
                <a href="/SpringMvcHelloWorld/user/list" class="text-primary me-3">
                    <i class="fas fa-arrow-left fs-5"></i>
                </a>
                <i class="fas fa-user text-primary fs-2 me-3"></i>
                <h1 class="navbar-brand mb-0 h3">NutriTime User Details</h1>
            </div>
            <div class="d-flex gap-2">
                <a href="/SpringMvcHelloWorld/user/edit/${user.userId}"
                   class="btn btn-primary">
                    <i class="fas fa-edit me-2"></i>
                    Edit User
                </a>
                <a href="/SpringMvcHelloWorld/user/list"
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

        <!-- User Profile Card -->
        <div class="card shadow">
            <div class="card-header">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="card-title mb-1">User Information</h5>
                        <p class="card-text text-muted small">Complete details for ${user.name}</p>
                    </div>
                    <div class="d-flex gap-2">
                        <a href="/SpringMvcHelloWorld/user/edit/${user.userId}"
                           class="btn btn-primary btn-sm">
                            <i class="fas fa-edit me-2"></i>
                            Edit
                        </a>
                        <a href="#"
                           onclick="confirmDelete(${user.userId}, '${user.name}')"
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
                                            ${user.name.charAt(0)}
                                        </span>
                                </div>
                            </div>
                            <h4 class="fw-bold">${user.name}</h4>
                            <p class="text-muted fs-5">${user.position}</p>
                            <small class="text-muted">User ID: ${user.userId}</small>
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
                                <p class="mb-0">${user.name}</p>
                            </div>

                            <!-- Position -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-briefcase text-muted me-2"></i>
                                    <small class="text-muted fw-medium">Position</small>
                                </div>
                                <span class="badge bg-primary">${user.position}</span>
                            </div>

                            <!-- Address -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-user text-muted me-2"></i>
                                    <small class="text-muted fw-medium">Address</small>
                                </div>
                                <p class="mb-0">${user.address}</p>
                            </div>

                            <!-- Email -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-envelope text-muted me-2"></i>
                                    <small class="text-muted fw-medium">Email Address</small>
                                </div>
                                <a href="mailto:${user.email}" class="text-decoration-none">
                                    ${user.email}
                                </a>
                            </div>

                            <!-- Contact Number -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-phone text-muted me-2"></i>
                                    <small class="text-muted fw-medium">Contact Number</small>
                                </div>
                                <a href="tel:${user.contactNumber}" class="text-decoration-none">
                                    ${user.contactNumber}
                                </a>
                            </div>

                            <!-- User ID -->
                            <div class="col-md-6">
                                <div class="d-flex align-items-center mb-2">
                                    <i class="fas fa-id-card text-muted me-2"></i>
                                    <small class="text-muted fw-medium">User ID</small>
                                </div>
                                <p class="mb-0">${user.userId}</p>
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
                    Are you sure you want to delete <strong id="userName"></strong>?
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
    function confirmDelete(userId, userName) {
        document.getElementById('userName').textContent = userName;
        document.getElementById('deleteForm').action = '/SpringMvcHelloWorld/user/delete/' + userId;
        var deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
        deleteModal.show();
    }
</script>
</body>
</html>