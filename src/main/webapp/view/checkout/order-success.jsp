<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Order Confirmation</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="text-success">🎉 Thank you for your order!</h2>
  <p class="mt-3">Your order has been placed successfully.</p>
  <p><strong>Order ID:</strong> #${order.orderId}</p>
  <p><strong>Created At:</strong> <fmt:formatDate value="${order.createAt}" pattern="dd/MM/yyyy HH:mm" /></p>

  <h4 class="mt-4">Order Details:</h4>
  <table class="table table-bordered mt-2">
    <thead>
    <tr>
      <th>Product</th>
      <th>Color</th>
      <th>Size</th>
      <th>Quantity</th>
      <th>Unit Price</th>
      <th>Total</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${orderDetails}">
      <c:set var="variation" value="${variationMap[item.variationId]}" />
      <tr>
        <td>${variation.productName}</td>
        <td>${variation.colorName}</td>
        <td>${variation.sizeName}</td>
        <td>${item.quantity}</td>
        <td>
          <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true" /> đ
        </td>
        <td>
          <fmt:formatNumber value="${item.price * item.quantity}" type="number" groupingUsed="true" /> đ
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-4">Back to Home</a>
</div>
</body>
</html>
