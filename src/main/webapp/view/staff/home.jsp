<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Staff | Nature Care</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <style>
        /* Đảm bảo CSS không ảnh hưởng global */
        .staff-dashboard {
            display: flex;
            margin: 40px auto;
            max-width: 1200px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.05);
            overflow: hidden;
        }

        .staff-sidebar {
            width: 220px;
            background-color: #f0f0e9;
            padding: 20px;
            border-right: 1px solid #ddd;
        }

        .staff-sidebar h3 {
            font-size: 20px;
            color: #2d3436;
            margin-bottom: 20px;
            font-weight: 600;
        }

        .staff-sidebar a {
            display: block;
            color: #2d3436;
            padding: 10px 15px;
            border-radius: 5px;
            margin-bottom: 10px;
            text-decoration: none;
            transition: 0.3s;
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
        }

        .staff-sidebar a:hover {
            background-color: #e3f2fd;
            color: #0d47a1;
            border-color: #90caf9;
        }

        .staff-content {
            flex-grow: 1;
            padding: 40px;
        }

        .staff-content h1 {
            font-size: 28px;
            font-weight: bold;
            color: #1e3d59;
        }

        .staff-content p {
            font-size: 16px;
            color: #555;
            margin: 10px 0 20px;
        }

        .staff-content .btn-primary {
            background-color: #4caf50;
            border-color: #4caf50;
            padding: 10px 20px;
        }

        .staff-content .btn-primary:hover {
            background-color: #43a047;
            border-color: #388e3c;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .staff-dashboard {
                flex-direction: column;
            }

            .staff-sidebar {
                width: 100%;
                border-right: none;
                border-bottom: 1px solid #ddd;
            }
        }
    </style>
</head>
<body>
<jsp:include page="/view/common/header-top.jsp" />
<jsp:include page="/view/common/header-middle.jsp" />

<div class="container">
    <div class="staff-dashboard">
        <!-- Sidebar -->
        <div class="staff-sidebar">
            <h3>Staff Dashboard</h3>
            <a href="${pageContext.request.contextPath}/orderManagement">📦 Order Management</a>
            <a href="${pageContext.request.contextPath}/staff">🏠 Dashboard</a>
            <!-- Future: <a href="#">👥 Manage Users</a> -->
        </div>

        <!-- Main content -->
        <div class="staff-content">
            <h1>Welcome, Staff!</h1>
            <p>This is your homepage where you can manage orders and perform staff tasks.</p>
        </div>
    </div>
</div>

<jsp:include page="/view/common/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
