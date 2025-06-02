<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Category Management</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f0fdf4;
            font-family: 'Segoe UI', sans-serif;
        }
        .container {
            max-width: 900px;
            margin: 60px auto;
            background-color: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,128,0,0.05);
        }
        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #2e7d32;
        }
        .btn-add {
            margin-bottom: 20px;
        }
        .table th, .table td {
            vertical-align: middle !important;
            text-align: center;
        }
        .btn i {
            margin-right: 5px;
        }
        .btn-edit {
            background-color: #f0ad4e;
            color: white;
            border: none;
            padding: 5px 12px;
            border-radius: 5px;
        }
        .btn-delete {
            background-color: #d9534f;
            color: white;
            border: none;
            padding: 5px 12px;
            border-radius: 5px;
        }
        .btn-add {
            background-color: #43a047;
            color: white;
            padding: 8px 16px;
            border-radius: 6px;
            text-decoration: none;
            display: inline-block;
        }
        .btn-add i {
            margin-right: 6px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Product Management</h2>
    <a href="view/product/product-edit.jsp" class="btn btn-add">
        <i class="fa fa-plus"></i> Add New Product
    </a>

    <table class="table table-bordered table-hover">
        <thead class="table-light">
        <tr>
            <th>#</th>
            <th>Category Name</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${products}" varStatus="loop">
            <tr>
                <td>${loop.index + 1}</td>
                <td>${c.name}</td>
                <td>
                    <a class="btn btn-edit" href="productManage?action=edit&id=${c.id}">
                        <i class="fa fa-pencil"></i> Edit
                    </a>
                    <form action="productManage" method="post" style="display:inline;" onsubmit="return confirm('Are you sure to delete this category?');">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="id" value="${c.id}"/>
                        <button type="submit" class="btn btn-delete">
                            <i class="fa fa-trash"></i> Delete
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
