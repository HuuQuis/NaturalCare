<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Order History | Nature Care</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
  <style>
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

    /* Nội dung Order Management */
    h1 {
      font-size: 28px;
      font-weight: bold;
      color: #1e3d59;
    }

    .filter-section {
      display: flex;
      flex-wrap: wrap;
      gap: 15px;
      margin: 25px 0;
      align-items: center;
    }

    .filter-section input,
    .filter-section select {
      padding: 8px 12px;
      border: 1px solid #ccc;
      border-radius: 6px;
      font-size: 14px;
      min-width: 180px;
    }

    .filter-section button,
    .filter-section a.reset {
      padding: 8px 16px;
      border-radius: 6px;
      font-size: 14px;
      cursor: pointer;
      text-decoration: none;
    }

    .filter-section button {
      background-color: #4caf50;
      border: none;
      color: white;
    }

    .filter-section button:hover {
      background-color: #43a047;
    }

    .filter-section a.reset {
      background-color: #dc3545;
      color: white;
    }

    .filter-section a.reset:hover {
      background-color: #c82333;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
      background-color: #fff;
      border-radius: 8px;
      overflow: hidden;
      box-shadow: 0 4px 8px rgba(0,0,0,0.05);
    }

    th, td {
      padding: 14px;
      border-bottom: 1px solid #ddd;
      text-align: left;
      font-size: 14px;
    }

    th {
      background-color: #e8f5e9;
      font-weight: 600;
    }

    tr:hover {
      background-color: #f1f8e9;
    }

    .actions button {
      padding: 6px 12px;
      border: none;
      border-radius: 4px;
      font-size: 13px;
      background-color: #dc3545;
      color: white;
    }

    .actions button:hover {
      background-color: #c82333;
    }

    .pagination {
      margin-top: 30px;
      display: flex;
      justify-content: center;
      gap: 8px;
    }

    .pagination a {
      padding: 8px 14px;
      border: 1px solid #ccc;
      border-radius: 4px;
      background-color: white;
      text-decoration: none;
      color: #4caf50;
      font-weight: 500;
    }

    .pagination .active a {
      background-color: #4caf50;
      color: white;
      border-color: #4caf50;
    }

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

    <!-- Nội dung chính -->
    <div class="staff-content">
      <h1>Order History</h1>

      <form method="get" class="filter-section">
        <input type="text" name="search" placeholder="Search..." value="${search}">
        <select name="status">
          <option value="">-- All status --</option>
          <option value="1" ${status == '1' ? 'selected' : ''}>Pending</option>
          <option value="2" ${status == '2' ? 'selected' : ''}>Processing</option>
          <!-- ... other statuses -->
        </select>
        <input type="date" name="fromDate" value="${fromDate}">
        <input type="date" name="toDate" value="${toDate}">
        <button type="submit">Filter</button>
        <a href="orderManagement" class="reset">Reset</a>
      </form>

      <c:if test="${not empty message}">
        <div style="color: green;">${message}</div>
      </c:if>
      <c:if test="${not empty error}">
        <div style="color: red;">${error}</div>
      </c:if>

      <table>
        <thead>
          <tr>
            <th>Order ID</th>
            <th>Date</th>
            <th>Status</th>
            <th>Shipper</th>
            <th>Address</th>
            <th>Coupon</th>
            <th>Note</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="order" items="${orders}">
            <tr>
              <td>
                <a href="OrderHistoryDetail?orderId=${order.orderId}">
                  ${order.orderId}
                </a>
              </td>
              <td>${order.createdAt}</td>
              <td>
                <c:choose>
                  <c:when test="${order.statusId == 1}">Pending</c:when>
                  <c:when test="${order.statusId == 2}">Processing</c:when>
                  <c:when test="${order.statusId == 3}">Shipped</c:when>
                  <c:when test="${order.statusId == 4}">Completed</c:when>
                  <c:when test="${order.statusId == 5}">Cancelled</c:when>
                  <c:otherwise>${order.statusId}</c:otherwise>
                </c:choose>
              </td>
              <td>${order.shipperName}</td>
              <td>${order.addressDisplay}</td>
              <td>${order.couponCode}</td>
              <td>${order.note}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <div class="pagination">
        <c:forEach var="i" begin="1" end="${totalPages}">
          <div class="${i == currentPage ? 'active' : ''}">
            <a href="OrderHistory?userId=${userId}&page=${i}&search=${search}&status=${status}&fromDate=${fromDate}&toDate=${toDate}">${i}</a>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>
</div>

<jsp:include page="/view/common/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
