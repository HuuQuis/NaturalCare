<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Management</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
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
        .variant-row {
            background: #f9f9f9;
        }
        .variant-toggle {
            cursor: pointer;
            color: #43a047;
            font-weight: bold;
        }
        .variant-toggle:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Product Management</h2>
    <a href="productManage?action=add" class="btn btn-add">
        <i class="fa fa-plus"></i> Add New Product
    </a>

    <a href="" class="btn btn-add">
        <i class="fa fa-plus"></i> Add New Product Variant
    </a>

    <table class="table table-bordered table-hover">
        <thead class="table-light">
        <tr>
            <th>#</th>
            <th>Product Name</th>
            <th>Description</th>
            <th>Information</th>
            <th>Guideline</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${products}" varStatus="loop">
            <tr>
                <td>${loop.index + 1}</td>
                <td>
                    ${c.name}
                    <c:if test="${not empty productVariantsMap[c.id]}">
                        <span class="variant-toggle" onclick="toggleVariants('${c.id}')">
                            <i class="fa fa-caret-down"></i> Variants
                        </span>
                    </c:if>
                </td>
                <td>${c.description}</td>
                <td>${c.information}</td>
                <td>${c.guideline}</td>
                <td>
                    <a class="btn btn-edit" href="productManage?action=edit&id=${c.id}">
                        <i class="fa fa-pencil"></i> Edit
                    </a>
                    <form action="productManage" method="post" style="display:inline;" onsubmit="return confirm('Are you sure to delete this product?');">
                        <input type="hidden" name="action" value="delete"/>
                        <input type="hidden" name="id" value="${c.id}"/>
                        <button type="submit" class="btn btn-delete">
                            <i class="fa fa-trash-o"></i> Delete
                        </button>
                    </form>
                </td>
            </tr>
            <c:if test="${not empty productVariantsMap[c.id]}">
                <tr id="variants-${c.id}" class="variant-row" style="display:none;">
                    <td colspan="6" style="padding:0;">
                        <table class="table table-sm mb-0">
                            <thead>
                                <tr>
                                    <th>Image</th>
                                    <th>Color</th>
                                    <th>Size</th>
                                    <th>Price</th>
                                    <th>Qty In Stock</th>
                                    <th>Sold</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="v" items="${productVariantsMap[c.id]}">
                                    <tr>
                                        <td>
                                            <c:if test="${not empty v.imageUrl}">
                                                <img src="${v.imageUrl}" alt="Variant Image" style="width:100px;height:100px;object-fit:cover;">
                                            </c:if>
                                        </td>
                                        <td>${v.color}</td>
                                        <td>${v.size}</td>
                                        <td>${v.price}</td>
                                        <td>${v.qtyInStock}</td>
                                        <td>${v.sold}</td>

<%--                                    <td>--%>
<%--                                        <a class="btn btn-edit" href="?action=edit&id=${v.id}">--%>
<%--                                            <i class="fa fa-pencil"></i> Edit--%>
<%--                                        </a>--%>
<%--                                        <form action="" method="post" style="display:inline;" onsubmit="return confirm('Are you sure to delete this product variant?');">--%>
<%--                                            <input type="hidden" name="action" value="delete"/>--%>
<%--                                            <input type="hidden" name="id" value="${v.id}"/>--%>
<%--                                            <button type="submit" class="btn btn-delete">--%>
<%--                                                <i class="fa fa-trash"></i> Delete--%>
<%--                                            </button>--%>
<%--                                        </form>--%>
<%--                                    </td>--%>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script>
    function toggleVariants(productId) {
        var row = document.getElementById('variants-' + productId);
        if (row.style.display === 'none' || row.style.display === '') {
            row.style.display = 'table-row';
        } else {
            row.style.display = 'none';
        }
    }
</script>
</html>
