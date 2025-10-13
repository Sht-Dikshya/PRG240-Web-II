<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NutriTime User Dashboard - NutriTime User Management System</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
<div class="container mx-auto px-4 py-8">
    <!-- Header -->
    <div class="bg-white rounded-lg shadow-md p-6 mb-8">
        <div class="flex justify-between items-center">
            <div>
                <h1 class="text-3xl font-bold text-gray-800">NutriTime User Dashboard</h1>
                <p class="text-gray-600 mt-2">Welcome, <span id="username"></span>!</p>
            </div>
            <div>
                <button onclick="logout()" class="bg-red-500 hover:bg-red-600 text-white font-medium py-2 px-4 rounded-md transition duration-200">
                    Logout
                </button>
            </div>
        </div>
    </div>

    <!-- Navigation -->
    <div class="bg-white rounded-lg shadow-md p-6 mb-8">
        <h2 class="text-xl font-semibold mb-4 text-gray-700">NutriTime User Management</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
            <a href="/SpringMvcHelloWorld/login/add" class="bg-blue-500 hover:bg-blue-600 text-white font-medium py-3 px-4 rounded-md transition duration-200 text-center">
                Add New User
            </a>
            <a href="/SpringMvcHelloWorld/login/list" class="bg-green-500 hover:bg-green-600 text-white font-medium py-3 px-4 rounded-md transition duration-200 text-center">
                View All User
            </a>
            <a href="/SpringMvcHelloWorld/login/" class="bg-purple-500 hover:bg-purple-600 text-white font-medium py-3 px-4 rounded-md transition duration-200 text-center">
                User Home
            </a>
        </div>
    </div>

    <!-- Welcome Message -->
    <div class="bg-white rounded-lg shadow-md p-6">
        <h2 class="text-xl font-semibold mb-4 text-gray-700">Welcome to NutriTime User Management System</h2>
        <p class="text-gray-600 mb-4">Use the navigation buttons above to manage users. You can add new users, view the user list, and perform other management tasks.</p>
        <div class="bg-blue-50 border border-blue-200 rounded-lg p-4">
            <h3 class="text-sm font-semibold text-blue-800 mb-2">Quick Actions</h3>
            <ul class="text-sm text-blue-700 space-y-1">
                <li>• <strong>Add New User:</strong> Click the blue button to create a new user record</li>
                <li>• <strong>View All Users:</strong> Click the green button to see all users in the system</li>
                <li>• <strong>User Home:</strong> Click the purple button to go to the main user management page</li>
            </ul>
        </div>
    </div>
</div>

<script>
    let currentToken = null;

    // Check authentication on page load
    window.addEventListener('load', function() {
        console.log('=== DASHBOARD LOADED ===');
        console.log('Dashboard loaded, checking authentication...');
        const token = localStorage.getItem('jwt_token');
        const username = localStorage.getItem('username');

        console.log('Token from localStorage:', token ? 'Present' : 'Missing');
        console.log('Username from localStorage:', username);
        console.log('Full token value:', token);

        if (!token || !username) {
            console.log('No token or username found, redirecting to login');
            // No token or username, redirect to login
            window.location.href = '/SpringMvcHelloWorld/login';
            return;
        }

        currentToken = token;
        document.getElementById('username').textContent = username;
        console.log('Token set successfully, currentToken:', currentToken);
        console.log('=== DASHBOARD INITIALIZATION COMPLETE ===');

        // Skip token validation for now to test
        // validateToken();
    });

    async function validateToken() {
        console.log('Validating token:', currentToken ? 'Present' : 'Missing');
        console.log('Token value:', currentToken);
        try {
            const response = await fetch('/SpringMvcHelloWorld/api/auth/validate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ token: currentToken })
            });

            console.log('Token validation response status:', response.status);
            const data = await response.json();
            console.log('Token validation response:', data);

            if (!data.success || data.expired) {
                console.log('Token validation failed, logging out');
                // Token is invalid or expired
                logout();
            } else {
                console.log('Token validation successful');
            }
        } catch (error) {
            console.log('Error validating token:', error);
            // Error validating token
            logout();
        }
    }




    function logout() {
        localStorage.removeItem('jwt_token');
        localStorage.removeItem('username');
        window.location.href = '/SpringMvcHelloWorld/login';
    }

    function showResult(element, message, type) {
        const className = type === 'success' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800';
        element.innerHTML = '<div class="p-3 rounded-md ' + className + '">' + message + '</div>';
    }
</script>
</body>
</html>
