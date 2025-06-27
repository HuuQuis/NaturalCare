<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Order Management | Nature Care</title>
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
    
    form label {
        display: block;
        margin-top: 15px;
        font-weight: 500;
      }

      form input[type="text"],
      form select {
        width: 100%;
        padding: 8px 12px;
        margin-top: 5px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 14px;
      }

      form button {
        margin-top: 20px;
        background-color: #1e88e5;
        color: white;
        border: none;
        padding: 10px 20px;
        border-radius: 6px;
        font-size: 14px;
        cursor: pointer;
      }

      form button:hover {
        background-color: #1565c0;
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
  <h2>Order Detail - ID: ${order.orderId}</h2>

    <form method="post" action="OrderDetail">
      <input type="hidden" name="orderId" value="${order.orderId}">

        <p><strong>Customer:</strong> ${userName}</p>
        <p><strong>Created At:</strong> ${order.createdAt}</p>
        
      <label>Note:</label>
      <input type="text" name="note" value="${order.note}"><br>

      <label>Status:</label>
      <select name="statusId" id="statusId" onchange="toggleShipper()">
        <c:forEach var="status" items="${statusList}">
          <option value="${status.statusId}" ${status.statusId == order.statusId ? "selected" : ""}>
            ${status.statusName}
          </option>
        </c:forEach>
      </select><br>

      <div id="shipperSection" style="display:none;">
        <label>Shipper:</label>
        <select name="shipperId">
          <option value="">-- Select --</option>
          <c:forEach var="s" items="${shippers}">
            <option value="${s.id}" ${s.id == order.shipperId ? "selected" : ""}>
              ${s.firstName} ${s.lastName}
            </option>
          </c:forEach>
        </select><br>
      </div>

      <label>Address:</label>
      <select name="addressId">
        <c:forEach var="addr" items="${addresses}">
            <option value="${addr.addressId}"
                <c:if test="${addr.addressId == order.addressId}">selected</c:if>>
                ${addr.province.name}, ${addr.district.name}, ${addr.ward.name} - ${addr.detail}
            </option>
        </c:forEach>
    </select><br>

      <label>Coupon:</label>
      <select name="couponId">
        <option value="">-- None --</option>
        <c:forEach var="cp" items="${coupons}">
          <option value="${cp.couponId}" ${cp.couponId == order.couponId ? "selected" : ""}>
            ${cp.code}
          </option>
        </c:forEach>
      </select><br>

      <button type="submit">Update</button>
    </form>

    <br>
    <a href="http://localhost:8080/NaturalCare/orderManagement">← Back to Order Management</a>

</div>

<jsp:include page="/view/common/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script>
  function toggleShipper() {
    const statusId = parseInt(document.getElementById("statusId").value);
    const show = statusId >= 3;
    document.getElementById("shipperSection").style.display = show ? "block" : "none";
  }

  toggleShipper();
</script>
</body>
</html>
