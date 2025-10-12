<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration - NutriTime User Management System</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen flex items-center justify-center">
<div class="max-w-md w-full bg-white rounded-lg shadow-md p-8">
    <div class="text-center mb-8">
        <div class="mx-auto w-16 h-16 bg-green-500 rounded-full flex items-center justify-center mb-4">
            <svg class="w-8 h-8 text-white" fill="currentColor" viewBox="0 0 20 20">
                <path d="M8 9a3 3 0 100-6 3 3 0 000 6zM8 11a6 6 0 016 6H2a6 6 0 016-6zM16 7a1 1 0 10-2 0v1h-1a1 1 0 100 2h1v1a1 1 0 102 0v-1h1a1 1 0 100-2h-1V7z"/>
            </svg>
        </div>
        <h1 class="text-3xl font-bold text-gray-800">NutriTime User Registration</h1>
        <p class="text-gray-600 mt-2">Create your account to access the NutriTime system</p>
    </div>

    <form id="registrationForm" class="space-y-6">
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
                class="w-full bg-green-500 hover:bg-green-600 text-white font-medium py-2 px-4 rounded-md transition duration-200">
            Register
        </button>
    </form>

    <div class="mt-6 text-center">
        <p class="text-gray-600">Already have an account?
            <a href="/SpringMvcHelloWorld/login" class="text-blue-500 hover:text-blue-600 font-medium">Login here</a>
        </p>
    </div>

    <div id="result" class="mt-4"></div>
</div>

<script>
    document.getElementById('registrationForm').addEventListener('submit', async function(e) {
        e.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const resultDiv = document.getElementById('result');

        if (!username || !password) {
            showResult(resultDiv, 'Please enter both username and password', 'error');
            return;
        }

        try {
            const response = await fetch('/SpringMvcHelloWorld/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password })
            });

            const data = await response.json();

            if (data.success) {
                showResult(resultDiv, 'Registration successful! Redirecting to login...', 'success');
                setTimeout(() => {
                    window.location.href = '/SpringMvcHelloWorld/login';
                }, 2000);
            } else {
                showResult(resultDiv, data.message || 'Registration failed', 'error');
            }
        } catch (error) {
            showResult(resultDiv, 'Error: ' + error.message, 'error');
        }
    });

    function showResult(element, message, type) {
        const className = type === 'success' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800';
        element.innerHTML = '<div class="p-3 rounded-md ' + className + '">' + message + '</div>';
    }
</script>
</body>
</html>
