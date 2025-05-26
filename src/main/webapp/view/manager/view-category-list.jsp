<%--
  Created by IntelliJ IDEA.
  User: phung
  Date: 5/27/2025
  Time: 12:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Category List - Manager</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap & FontAwesome -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 30px;
        }
        .category-table th, .category-table td {
            vertical-align: middle !important;
        }
        .container h2 {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Category Management</h2>
    <table class="table table-bordered table-striped category-table">
        <thead class="thead-dark">
        <tr>
            <th>#</th>
            <th>Category Name</th>
            <th>Description</th>
            <th>Parent Category</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Example row -->
        <tr>
            <td>1</td>
            <td>Sportswear</td>
            <td>Active wear for sports activities</td>
            <td>None</td>
            <td>
                <a href="#" class="btn btn-sm btn-warning"><i class="fa fa-pencil"></i> Edit</a>
                <a href="#" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> Delete</a>
            </td>
        </tr>
        <tr>
            <td>2</td>
            <td>Mens</td>
            <td>Men's clothing & accessories</td>
            <td>Fashion</td>
            <td>
                <a href="#" class="btn btn-sm btn-warning"><i class="fa fa-pencil"></i> Edit</a>
                <a href="#" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> Delete</a>
            </td>
        </tr>
        <tr>
            <td>3</td>
            <td>Kids</td>
            <td>Clothing for children</td>
            <td>None</td>
            <td>
                <a href="#" class="btn btn-sm btn-warning"><i class="fa fa-pencil"></i> Edit</a>
                <a href="#" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> Delete</a>
            </td>
        </tr>
        <!-- Add more categories as needed -->
        </tbody>
    </table>

    <a href="#" class="btn btn-primary"><i class="fa fa-plus"></i> Add New Category</a>
</div>

<!-- JS Libraries -->
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>

