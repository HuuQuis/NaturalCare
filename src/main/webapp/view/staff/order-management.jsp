<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Management</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Order Management</h2>

    <form method="get" class="form-inline mb-3">
        <input type="text" name="search" class="form-control mr-2" value="${search}" placeholder="Search by Order ID or User ID">
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Order ID</th>
            <th>User ID</th>
            <th>Status</th>
            <th>Created At</th>
            <th>Note</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.userId}</td>
                <td>
                    <c:choose>
                        <c:when test="${order.statusId == 1}">Pending</c:when>
                        <c:when test="${order.statusId == 2}">Processing</c:when>
                        <c:when test="${order.statusId == 3}">Assigned</c:when>
                        <c:when test="${order.statusId == 4}">Shipped</c:when>
                        <c:when test="${order.statusId == 5}">Delivered</c:when>
                        <c:when test="${order.statusId == 6}">Cancelled</c:when>
                        <c:when test="${order.statusId == 7}">Returned</c:when>
                        <c:when test="${order.statusId == 8}">Refunded</c:when>
                    </c:choose>
                </td>
                <td>${order.createdAt}</td>
                <td>${order.note}</td>
                <td>
                    <c:if test="${order.statusId == 1 || order.statusId == 2}">
                        <form method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="orderId" value="${order.orderId}">
                            <button class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Pagination -->
    <nav>
        <ul class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="orderManagement?page=${i}&search=${search}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
