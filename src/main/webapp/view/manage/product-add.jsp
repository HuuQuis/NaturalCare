<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
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
            box-shadow: 0 4px 15px rgba(0, 128, 0, 0.1);
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

        textarea {
            width: 100%;
            padding: 10px;
            margin-top: 8px;
            margin-bottom: 20px;
            border: 1px solid #c5e1a5;
            border-radius: 6px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Add Product</h2>
    <form action="productManage" method="post">
        <input type="hidden" name="action" value="add">

        <label for="name">Product Name:</label>
        <input type="text" name="name" id="name" value="" required>

        <label for="description">Description:</label>
        <textarea name="description" id="description" rows="3" required></textarea>

        <label for="information">Information:</label>
        <textarea name="information" id="information" rows="3" required></textarea>

        <label for="guideline">Guideline:</label>
        <textarea name="guideline" id="guideline" rows="3" required></textarea>

        <label for="subProductCategoryId">Sub Product Category:</label>
        <select name="subProductCategoryId" id="subProductCategoryId">
            <c:forEach var="sub" items="${subCategories}">
                <option value="${sub.id}">
                        ${sub.name}
                </option>
            </c:forEach>
        </select>

        <button type="submit">
            <i class="fa fa-save"></i> Add
        </button>
    </form>
    <a class="back-link" href="${pageContext.request.contextPath}/productManage">
        <i class="fa fa-arrow-left"></i> Back to list
    </a>
</div>
</body>
</html>