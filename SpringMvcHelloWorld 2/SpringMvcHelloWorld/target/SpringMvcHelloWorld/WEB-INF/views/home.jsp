<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 8/27/2025
  Time: 7:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NutriTime - Eat Smart. Live Light.</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<nav class="navbar navbar-expand-lg custom-navbar">
    <div class="container custom-container">
        <a class="navbar-brand d-flex align-items-center" href="index.html">
            <img src="https://png.pngtree.com/png-clipart/20230923/original/pngtree-green-beauty-and-leaf-logo-sign-vector-natural-line-vector-png-image_12733148.png" alt="NutriTime Logo" class="logo-icon me-2" width="100px" height="100px">
            <span class="brand-text">NutriTime</span>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="index.html">Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                        Products
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="products.html">All Products</a></li>
                        <li><a class="dropdown-item" href="products.html#meal-plans">Meal Plans</a></li>
                        <li><a class="dropdown-item" href="products.html#supplements">Supplements</a></li>
                        <li><a class="dropdown-item" href="products.html#guides">Health Guides</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="about.html">About Us</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="contact.html">Contact</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<section class="hero-section">
    <div class="container custom-container">
        <div class="row align-items-center min-vh-75">
            <div class="col-lg-6">
                <h1 class="display-4 fw-bold mb-4">Eat Smart. Live Light.</h1>
                <p class="lead mb-4">Transform your health journey with our premium nutrition products and personalized meal plans. No workout required - just smart eating habits.</p>
                <div class="d-flex gap-3 flex-wrap">
                    <a href="products.html" class="btn btn-primary btn-lg">Shop Now</a>
                    <a href="about.html" class="btn btn-outline-primary btn-lg">Learn More</a>
                </div>
            </div>
            <div class="col-lg-6">
                <img src="public/home.png" alt="NutriTime App" class="img-fluid rounded-3">
            </div>
        </div>
    </div>
</section>

<section class="features-section">
    <div class="container custom-container">
        <div class="text-center mb-5">
            <h2 class="display-5 fw-bold mb-3">Why Choose NutriTime?</h2>
            <p class="lead">Discover the power of smart eating with our comprehensive health solutions</p>
        </div>
        <div class="row g-4">
            <div class="col-md-6 col-lg-3">
                <div class="feature-card text-center h-100">
                    <div class="feature-icon mb-3">
                        <i class="fas fa-utensils fa-3x"></i>
                    </div>
                    <h4>Daily Meal Logger</h4>
                    <p>Track your meals with ease and build healthy eating habits</p>
                </div>
            </div>
            <div class="col-md-6 col-lg-3">
                <div class="feature-card text-center h-100">
                    <div class="feature-icon mb-3">
                        <i class="fas fa-clock fa-3x"></i>
                    </div>
                    <h4>Meal Timing Tracker</h4>
                    <p>Get reminders and optimize your eating schedule</p>
                </div>
            </div>
            <div class="col-md-6 col-lg-3">
                <div class="feature-card text-center h-100">
                    <div class="feature-icon mb-3">
                        <i class="fas fa-weight fa-3x"></i>
                    </div>
                    <h4>Weight Management</h4>
                    <p>Monitor your progress and achieve your health goals</p>
                </div>
            </div>
            <div class="col-md-6 col-lg-3">
                <div class="feature-card text-center h-100">
                    <div class="feature-icon mb-3">
                        <i class="fas fa-heart fa-3x"></i>
                    </div>
                    <h4>Health Insights</h4>
                    <p>Get personalized recommendations based on your data</p>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="products-section">
    <div class="container custom-container">
        <div class="text-center mb-5">
            <h2 class="display-5 fw-bold mb-3">Featured Products</h2>
            <p class="lead">Start your health journey with our top-rated nutrition solutions</p>
        </div>
        <div class="row g-4">
            <div class="col-md-6 col-lg-4">
                <div class="card product-card h-100">
                    <img src="public/premium-meal-plan-recipies.png" class="card-img-top" alt="Premium Meal Plan">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Premium Meal Plan</h5>
                        <p class="card-text flex-grow-1">30-day personalized meal plan with grocery lists and recipes</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price fw-bold">$49.99</span>
                            <a href="product-detail.html?id=1" class="btn btn-primary">View Details</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-4">
                <div class="card product-card h-100">
                    <img src="public/nutrition-tracker-premium.png" class="card-img-top" alt="Nutrition Tracker Pro">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Nutrition Tracker Pro</h5>
                        <p class="card-text flex-grow-1">Advanced tracking features with AI-powered insights</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price fw-bold">$19.99/mo</span>
                            <a href="product-detail.html?id=2" class="btn btn-primary">View Details</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-4">
                <div class="card product-card h-100">
                    <img src="public/healthy-lifestyle-guide.png" class="card-img-top" alt="Healthy Living Guide">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">Healthy Living Guide</h5>
                        <p class="card-text flex-grow-1">Complete guide to building sustainable healthy habits</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <span class="price fw-bold">$29.99</span>
                            <a href="product-detail.html?id=3" class="btn btn-primary">View Details</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="text-center mt-5">
            <a href="products.html" class="btn btn-outline-primary btn-lg">View All Products</a>
        </div>
    </div>
</section>

<section class="testimonials-section">
    <div class="container custom-container">
        <div class="text-center mb-5">
            <h2 class="display-5 fw-bold mb-3">What Our Users Say</h2>
        </div>
        <div class="row g-4">
            <div class="col-md-4">
                <div class="testimonial-card text-center">
                    <div class="stars mb-3">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                    </div>
                    <p class="mb-3">"NutriTime helped me lose 15 pounds just by changing my eating habits. No gym required!"</p>
                    <h6>- Sarah Johnson</h6>
                </div>
            </div>
            <div class="col-md-4">
                <div class="testimonial-card text-center">
                    <div class="stars mb-3">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                    </div>
                    <p class="mb-3">"The meal timing tracker changed my life. I feel more energetic and focused throughout the day."</p>
                    <h6>- Mike Chen</h6>
                </div>
            </div>
            <div class="col-md-4">
                <div class="testimonial-card text-center">
                    <div class="stars mb-3">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                    </div>
                    <p class="mb-3">"Simple, effective, and actually works. Best health app I've ever used!"</p>
                    <h6>- Emily Davis</h6>
                </div>
            </div>
        </div>
    </div>
</section>

<footer class="footer">
    <div class="container custom-container">
        <div class="row">
            <div class="col-md-6">
                <div class="d-flex align-items-center mb-3">
                    <img src="https://png.pngtree.com/png-clipart/20230923/original/pngtree-green-beauty-and-leaf-logo-sign-vector-natural-line-vector-png-image_12733148.png" alt="NutriTime Logo" class="footer-logo me-2" width="100px" height="100px">
                    <span class="footer-brand">NutriTime</span>
                </div>
                <p class="mb-3">Eat smart. Live light.</p>
                <div class="social-icons">
                    <a href="#" class="me-3"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="me-3"><i class="fab fa-instagram"></i></a>
                    <a href="#" class="me-3"><i class="fab fa-whatsapp"></i></a>
                </div>
            </div>
            <div class="col-md-6">
                <h5 class="mb-3">Quick Links</h5>
                <ul class="list-unstyled">
                    <li><a href="index.html" class="footer-link">Home</a></li>
                    <li><a href="products.html" class="footer-link">Products</a></li>
                    <li><a href="about.html" class="footer-link">About Us</a></li>
                    <li><a href="contact.html" class="footer-link">Contact</a></li>
                </ul>
            </div>
        </div>
        <hr class="my-4">
        <div class="text-center">
            <p class="mb-0">&copy; 2025 NutriTime. All rights reserved. Made with ❤️ to promote smart eating habits</p>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

