<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="min-vh-100">
    <!-- Header -->
    <nav class="navbar navbar-expand-lg navbar-light bg-white shadow">
        <div class="container-fluid">
            <div class="d-flex align-items-center">
                <i class="fas fa-users text-primary fs-2 me-3"></i>
                <h1 class="navbar-brand mb-0 h3">NutriTime User Management</h1>
            </div>
            <div class="d-flex">
                <a href="/SpringMvcHelloWorld/user/add"
                   class="btn btn-primary">
                    <i class="fas fa-plus me-2"></i>
                    Add User
                </a>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <main class="container-fluid py-4">
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

        <!-- User List -->
        <div class="card shadow">
            <div class="card-header">
                <h5 class="card-title mb-1">User List</h5>
                <p class="card-text text-muted small">Manage your user database</p>
            </div>

            <c:choose>
                <c:when test="${empty users}">
                    <div class="card-body text-center py-5">
                        <i class="fas fa-users text-muted" style="font-size: 4rem;"></i>
                        <h5 class="mt-3 mb-2">No users found</h5>
                        <p class="text-muted mb-4">Get started by adding your first user.</p>
                        <a href="/SpringMvcHelloWorld/user/add"
                           class="btn btn-primary">
                            <i class="fas fa-plus me-2"></i>
                            Add First User
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-hover mb-0">
                            <thead class="table-light">
                            <tr>
                                <th>User</th>
                                <th>Position</th>
                                <th>Address</th>
                                <th>Contact</th>
                                <th>Email</th>
                                <th class="text-end">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <div class="flex-shrink-0 me-3">
                                                <div class="bg-primary rounded-circle d-flex align-items-center justify-content-center"
                                                     style="width: 40px; height: 40px;">
                                                            <span class="text-white fw-bold">
                                                                    ${user.name.charAt(0)}
                                                            </span>
                                                </div>
                                            </div>
                                            <div>
                                                <div class="fw-medium">${user.name}</div>
                                                <small class="text-muted">ID: ${user.userId}</small>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                                <span class="badge bg-primary">
                                                        ${user.position}
                                                </span>
                                    </td>
                                    <td>${user.address}</td>
                                    <td>${user.contactNumber}</td>
                                    <td>${user.email}</td>
                                    <td class="text-end">
                                        <div class="btn-group" role="group">
                                            <a href="/SpringMvcHelloWorld/user/view/${user.userId}"
                                               class="btn btn-sm btn-outline-primary"
                                               title="View Details">
                                                <i class="fas fa-eye"></i>
                                            </a>
                                            <a href="/SpringMvcHelloWorld/user/edit/${user.userId}"
                                               class="btn btn-sm btn-outline-secondary"
                                               title="Edit User">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="#"
                                               onclick="confirmDelete(${user.userId}, '${user.name}')"
                                               class="btn btn-sm btn-outline-danger"
                                               title="Delete User">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
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