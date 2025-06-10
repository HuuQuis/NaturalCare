<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Staff Home | Nature Care</title>

  <!-- Bootstrap & Custom CSS -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">

  <!-- Custom inline CSS -->
  <style>
    body {
      background-color: #f0fdf4;
      font-family: 'Segoe UI', sans-serif;
      color: #333;
      margin: 0;
      padding: 0;
    }

    .container {
      padding: 50px 15px;
    }

    h1 {
      color: #28a745;
      font-weight: bold;
    }

    .lead {
      font-size: 1.25rem;
      color: #555;
    }

    .btn-green {
      background-color: #28a745;
      border-color: #28a745;
      color: white;
      transition: 0.3s ease-in-out;
    }

    .btn-green:hover {
      background-color: #218838;
      border-color: #1e7e34;
      color: white;
    }

    .text-center {
      text-align: center;
    }

    @media (max-width: 768px) {
      .container {
        padding: 30px 10px;
      }

      h1 {
        font-size: 2rem;
      }

      .lead {
        font-size: 1rem;
      }
    }
  </style>
</head>

<body>
  <div class="container mt-5">
    <div class="text-center">
      <h1 class="mb-4">Welcome, Staff!</h1>
      <p class="lead">This is your homepage where you can manage orders and perform staff tasks.</p>
      <a href="${pageContext.request.contextPath}/orderManagement" class="btn btn-green btn-lg mt-3">
        Go to Order Management
      </a>
    </div>
  </div>

  <!-- Scripts -->
  <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
  <script src="${pageContext.request.contextPath}/js/main.js"></script>
  <script>
    const contextPath = "${pageContext.request.contextPath}";
  </script>
  <script src="${pageContext.request.contextPath}/js/search.j
