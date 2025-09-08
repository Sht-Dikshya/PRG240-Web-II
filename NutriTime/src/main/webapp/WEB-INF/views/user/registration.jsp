<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 9/3/2025
  Time: 6:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NutriTime Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style1.css">
</head>
<body class="employee-form">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h3 class="mb-0">Send us a Message</h3>
                </div>
                <div class="card-body">
                    <form:form action="${pageContext.request.contextPath}/employee/register"
                               method="post" modelAttribute="employee">

                        <div class="mb-3">
                            <label for="firstname" class="form-label">First Name *</label>
                            <form:input path="firstname" type="text" class="form-control"
                                        id="firstname" placeholder="Enter your first name" required="true"/>
                            <form:errors path="firstname" cssClass="text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="lastname" class="form-label">Last Name *</label>
                            <form:input path="lastname" type="text" class="form-control"
                                        id="lastname" placeholder="Enter your last name" required="true"/>
                            <form:errors path="lastname" cssClass="text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email Address *</label>
                            <form:input path="email" type="email" class="form-control"
                                        id="email" placeholder="Enter your email address" required="true"/>
                            <form:errors path="email" cssClass="text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="address" class="form-label">Address *</label>
                            <form:input path="address" type="text" class="form-control"
                                        id="address" placeholder="Enter your permanent address" required="true"/>
                            <form:errors path="address" cssClass="text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="contactNumber" class="form-label">Contact Number *</label>
                            <form:input path="contactNumber" type="tel" class="form-control"
                                        id="contactNumber" placeholder="Enter your contact number" required="true"/>
                            <form:errors path="contactNumber" cssClass="text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label for="position" class="form-label">Position *</label>
                            <form:select path="position" class="form-select" id="position" required="true">
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
                            </form:select>
                            <form:errors path="position" cssClass="text-danger"/>
                        </div>

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <button type="submit" class="btn btn-primary btn-lg">Register Employee</button>
                        </div>
                    </form:form>
                </div>
            </div>

            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">Back to Home</a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

