<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
    boolean isEdit = product != null;
%>
<!DOCTYPE html>
<html>
<head>
    <title><%= isEdit ? "Edit" : "Add" %> Category</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f0fdf4;
            font-family: 'Segoe UI', sans-serif;
        }
        .container {
            max-width: 500px;
            margin: 60px auto;
            background-color: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,128,0,0.1);
        }
        h2 {
            text-align: center;
            color: #2e7d32;
            margin-bottom: 30px;
        }
        label {
            font-weight: 600;
            color: #333;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-top: 8px;
            margin-bottom: 20px;
            border: 1px solid #c5e1a5;
            border-radius: 6px;
        }
        button {
            background-color: #43a047;
            color: white;
            border: none;
            padding: 10px;
            border-radius: 6px;
            width: 100%;
            font-size: 16px;
        }
        button:hover {
            background-color: #388e3c;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #388e3c;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">
    <h2><%= isEdit ? "Update Product" : "Add New Product" %></h2>
    <form action="${pageContext.request.contextPath}/productManage" method="post">
        <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
        <% if (isEdit) { %>
        <input type="hidden" name="id" value="<%= product.getId() %>">
        <% } %>

        <label for="name">Product Name:</label>
        <input type="text" name="name" id="name" value="<%= isEdit ? product.getName() : "" %>" required>

        <button type="submit">
            <i class="fa fa-save"></i> <%= isEdit ? "Update" : "Add" %>
        </button>
    </form>
    <a class="back-link" href="${pageContext.request.contextPath}/productManage">
        <i class="fa fa-arrow-left"></i> Back to list
    </a>
</div>
</body>
</html>
