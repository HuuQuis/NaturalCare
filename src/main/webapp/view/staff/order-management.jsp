<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Order Management</title>
  <style>
    :root {
      --green: #28a745;
      --green-dark: #218838;
      --green-light: #d4edda;
      --gray: #f8f9fa;
      --text-dark: #212529;
      --border: #dee2e6;
      --danger: #dc3545;
      --danger-dark: #c82333;
    }

    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      margin: 30px;
      background-color: var(--gray);
      color: var(--text-dark);
    }

    h2 {
      margin-bottom: 25px;
      font-size: 28px;
      color: var(--green);
    }

    .filter-section {
      display: flex;
      flex-wrap: wrap;
      gap: 15px;
      margin-bottom: 25px;
      align-items: center;
    }

    .filter-section input,
    .filter-section select {
      padding: 8px 12px;
      border: 1px solid var(--border);
      border-radius: 6px;
      font-size: 14px;
      min-width: 180px;
    }

    .filter-section button {
      padding: 8px 16px;
      border: none;
      border-radius: 6px;
      font-size: 14px;
      background-color: var(--green);
      color: white;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    .filter-section button:hover {
      background-color: var(--green-dark);
    }

    .filter-section a.reset {
      background-color: var(--danger);
      padding: 8px 16px;
      color: white;
      text-decoration: none;
      border-radius: 6px;
      transition: background-color 0.3s ease;
    }

    .filter-section a.reset:hover {
      background-color: var(--danger-dark);
    }

    table {
      width: 100%;
      border-collapse: collapse;
      background-color: white;
      border-radius: 8px;
      overflow: hidden;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
    }

    th, td {
      padding: 14px;
      border-bottom: 1px solid var(--border);
      text-align: left;
      font-size: 14px;
    }

    th {
      background-color: var(--green-light);
      color: var(--text-dark);
      font-weight: 600;
    }

    tr:hover {
      background-color: #e8f5e9;
    }

    .actions button {
      padding: 6px 12px;
      border: none;
      border-radius: 4px;
      font-size: 13px;
      cursor: pointer;
    }

    .actions .delete {
      background-color: var(--danger);
      color: white;
      transition: background-color 0.3s ease;
    }

    .actions .delete:hover {
      background-color: var(--danger-dark);
    }

    .pagination {
      margin-top: 30px;
      display: flex;
      justify-content: center;
      gap: 8px;
      flex-wrap: wrap;
    }

    .pagination a {
      padding: 8px 14px;
      border: 1px solid var(--border);
      border-radius: 4px;
      background-color: white;
      text-decoration: none;
      color: var(--green);
      font-weight: 500;
      transition: background-color 0.3s ease;
    }

    .pagination .active a {
      background-color: var(--green);
      color: white;
      border-color: var(--green);
    }

    .pagination a:hover {
      background-color: #e2fbe5;
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
  <a href="orderManagement" class="reset">Delete filter</a>
</form>

<c:if test="${not empty message}">
  <div style="color: var(--green); margin-bottom: 10px;">${message}</div>
</c:if>
<c:if test="${not empty error}">
  <div style="color: var(--danger); margin-bottom: 10px;">${error}</div>
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
        <td><c:if test="${not empty order.shipperId}">${order.shipperId}</c:if></td>
        <td>${order.addressId}</td>
        <td><c:if test="${not empty order.couponId}">${order.couponId}</c:if></td>
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
