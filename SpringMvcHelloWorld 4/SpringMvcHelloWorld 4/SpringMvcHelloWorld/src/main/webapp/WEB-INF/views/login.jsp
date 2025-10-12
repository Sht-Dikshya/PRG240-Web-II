<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Login - NutriTime User Management System</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex items-center justify-center">
<div class="max-w-md w-full bg-white rounded-lg shadow-md p-8">
    <div class="text-center mb-8">
        <div class="mx-auto w-16 h-16 bg-blue-500 rounded-full flex items-center justify-center mb-4">
            <svg class="w-8 h-8 text-white" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd"/>
            </svg>
        </div>
        <h1 class="text-3xl font-bold text-gray-800">NutriTime User Login</h1>
        <p class="text-gray-600 mt-2">Sign in to access the NutriTime system</p>
    </div>

    <form id="loginForm" class="space-y-6">
        <div>
            <label for="username" class="block text-sm font-medium text-gray-700 mb-2">Username</label>
            <input type="text" id="username" name="username" required
                   class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                   placeholder="Enter username">
        </div>

        <div>
            <label for="password" class="block text-sm font-medium text-gray-700 mb-2">Password</label>
            <input type="password" id="password" name="password" required
                   class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                   placeholder="Enter password">
        </div>

        <button type="submit"
                class="w-full bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-4 rounded-md transition duration-200">
            Login
        </button>
    </form>

    <div class="mt-6 text-center">
        <p class="text-gray-600">Don't have an account?
            <a href="/SpringMvcHelloWorld/register" class="text-blue-500 hover:text-blue-600 font-medium">Register here</a>
        </p>
    </div>

    <div id="result" class="mt-4"></div>

    <!-- Demo Credentials -->
    <div class="mt-6 bg-yellow-50 border border-yellow-200 rounded-lg p-4">
        <h3 class="text-sm font-semibold text-yellow-800 mb-2">Demo Credentials</h3>
        <div class="text-xs text-yellow-700">
            <p><strong>Username:</strong> admin, <strong>Password:</strong> admin123</p>
            <p><strong>Username:</strong> user, <strong>Password:</strong> user123</p>
            <p><strong>Username:</strong> test, <strong>Password:</strong> test123</p>
        </div>
    </div>
</div>

<script>
    // Check if user is already logged in
    window.addEventListener('load', function() {
        const token = localStorage.getItem('jwt_token');
        if (token) {
            // Validate token
            validateTokenAndRedirect(token);
        }
    });

    document.getElementById('loginForm').addEventListener('submit', async function(e) {
        e.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const resultDiv = document.getElementById('result');

        if (!username || !password) {
            showResult(resultDiv, 'Please enter both username and password', 'error');
            return;
        }

        try {
            const response = await fetch('/SpringMvcHelloWorld/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password })
            });

            const data = await response.json();

            if (data.success) {
                // Store token in localStorage
                localStorage.setItem('jwt_token', data.token);
                localStorage.setItem('username', data.username);

                showResult(resultDiv, `Login successful! Welcome ${data.username}. Redirecting...`, 'success');

                // Redirect to login dashboard
                setTimeout(() => {
                    window.location.href = '/SpringMvcHelloWorld/login/dashboard';
                }, 1500);
            } else {
                showResult(resultDiv, data.message || 'Login failed', 'error');
            }
        } catch (error) {
            showResult(resultDiv, 'Error: ' + error.message, 'error');
        }
    });

    async function validateTokenAndRedirect(token) {
        try {
            const response = await fetch('/SpringMvcHelloWorld/api/auth/validate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ token })
            });

            const data = await response.json();

            if (data.success && !data.expired) {
                // Token is valid, redirect to dashboard
                window.location.href = '/SpringMvcHelloWorld/login/dashboard';
            } else {
                // Token is invalid or expired, remove it
                localStorage.removeItem('jwt_token');
                localStorage.removeItem('username');
            }
        } catch (error) {
            // Error validating token, remove it
            localStorage.removeItem('jwt_token');
            localStorage.removeItem('username');
        }
    }

    function showResult(element, message, type) {
        const className = type === 'success' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800';
        element.innerHTML = '<div class="p-3 rounded-md ' + className + '">' + message + '</div>';
    }
</script>
</body>
</html>
