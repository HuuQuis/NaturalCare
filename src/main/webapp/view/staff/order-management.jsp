<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Order Management</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      background-color: #f9f9f9;
    }
    h2 {
      margin-bottom: 20px;
    }
    .filter-section {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      margin-bottom: 20px;
    }
    .filter-section input,
    .filter-section select,
    .filter-section button {
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    .filter-section button {
      background-color: #007bff;
      color: white;
      cursor: pointer;
    }
    .filter-section button.reset {
      background-color: #dc3545;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      background-color: white;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    th, td {
      padding: 12px;
      border: 1px solid #ddd;
      text-align: left;
    }
    th {
      background-color: #f1f1f1;
    }
    .actions button {
      padding: 5px 10px;
      margin-right: 5px;
      border: none;
      border-radius: 3px;
      cursor: pointer;
    }
    .actions .delete {
      background-color: #dc3545;
      color: white;
    }
    .pagination {
      margin-top: 20px;
      display: flex;
      justify-content: center;
      gap: 10px;
    }
    .pagination a {
      padding: 6px 12px;
      border: 1px solid #ccc;
      background-color: white;
      text-decoration: none;
      color: black;
    }
    .pagination .active a {
      background-color: #007bff;
      color: white;
    }
  </style>
</head>
<body>
<h2>Order Management</h2>

<form method="get" class="filter-section">
  <input type="text" name="search" placeholder="Search by ID, UserName,..." value="${search}">
  <select name="status">
    <option value="">-- All status --</option>
    <option value="1" ${status == '1' ? 'selected' : ''}>Pending</option>
    <option value="2" ${status == '2' ? 'selected' : ''}>Processing</option>
    <option value="3" ${status == '3' ? 'selected' : ''}>Assigned to Shipper</option>
    <option value="4" ${status == '4' ? 'selected' : ''}>Shipped</option>
    <option value="5" ${status == '5' ? 'selected' : ''}>Delivered</option>
    <option value="6" ${status == '6' ? 'selected' : ''}>Cancelled</option>
    <option value="7" ${status == '7' ? 'selected' : ''}>Returned</option>
    <option value="8" ${status == '8' ? 'selected' : ''}>Refunded</option>
  </select>
  <input type="date" name="fromDate" value="${fromDate}">
  <input type="date" name="toDate" value="${toDate}">
  <button type="submit">Filter</button>
  <a href="orderManagement" class="reset" style="text-decoration:none; padding:8px; border-radius:4px; color:white;">Detele filter</a>
</form>

<c:if test="${not empty message}">
  <div style="color: green; margin-bottom: 10px;">${message}</div>
</c:if>
<c:if test="${not empty error}">
  <div style="color: red; margin-bottom: 10px;">${error}</div>
</c:if>

<table>
  <thead>
    <tr>
      <th>No.</th>
        <th>Order ID</th>
        <th>Customer</th>
        <th>Created Date</th>
        <th>Status</th>
        <th>Note</th>
        <th>Shipper</th>
        <th>Address</th>
        <th>Discount Code</th>
        <th>Actions</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="order" items="${orders}" varStatus="loop">
      <tr>
        <td>${loop.index + 1}</td>
        <td>#${order.orderId}</td>
        <td>${order.userId}</td>
        <td>${order.createdAt}</td>
        <td>
          <c:choose>
            <c:when test="${order.statusId == 1}">Pending</c:when>
            <c:when test="${order.statusId == 2}">Processing</c:when>
            <c:when test="${order.statusId == 3}">Assigned to Shipper</c:when>
            <c:when test="${order.statusId == 4}">Shipped</c:when>
            <c:when test="${order.statusId == 5}">Delivered</c:when>
            <c:when test="${order.statusId == 6}">Cancelled</c:when>
            <c:when test="${order.statusId == 7}">Returned</c:when>
            <c:when test="${order.statusId == 8}">Refunded</c:when>
            <c:otherwise>Unknown</c:otherwise>
          </c:choose>
        </td>
        <td>${order.note}</td>
        <td>
          <c:if test="${not empty order.shipperId}">
            ${order.shipperId}
          </c:if>
        </td>
        <td>${order.addressId}</td>
        <td>
          <c:if test="${not empty order.couponId}">
            ${order.couponId}
          </c:if>
        </td>

        <td class="actions">
          <c:if test="${order.statusId == 1 || order.statusId == 2}">
            <form method="post" style="display:inline;">
              <input type="hidden" name="action" value="delete">
              <input type="hidden" name="orderId" value="${order.orderId}">
              <button type="submit" class="delete" onclick="return confirm('Are you sure to delete this order?')">Delete</button>
            </form>
          </c:if>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<div class="pagination">
  <c:forEach var="i" begin="1" end="${totalPages}">
    <div class="${i == currentPage ? 'active' : ''}">
      <a href="orderManagement?page=${i}&search=${search}&status=${status}&fromDate=${fromDate}&toDate=${toDate}">${i}</a>
    </div>
  </c:forEach>
</div>

</body>
</html>
