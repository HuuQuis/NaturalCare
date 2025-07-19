<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setTimeZone value="Asia/Ho_Chi_Minh"/>
<html>
<head>
  <title>Order Confirmation</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
</head>
<body>
<header id="header"><!--header-->
  <!--header_top-->
  <jsp:include page="/view/common/header-top.jsp"></jsp:include>
  <!--/header_top-->

  <!--header-middle-->
  <jsp:include page="/view/common/header-middle.jsp"></jsp:include>
  <!--/header-middle-->

  <!--header-bottom-->
  <jsp:include page="/view/common/header-bottom.jsp"></jsp:include>
  <!--/header-bottom-->
</header><!--/header-->
<div class="container mt-5">
  <h2 class="text-success">🎉 Thank you for your order!</h2>
  <p class="mt-3">Your order has been placed successfully.</p>
  <p><strong>Order ID:</strong> #${order.orderId}</p>
  <p><strong>Created At:</strong>
    <fmt:formatDate value="${order.createAt}" pattern="dd/MM/yyyy HH:mm" />
  </p>
  <p><strong>Status:</strong> ${order.statusName}</p>
  <c:if test="${not empty order .note}">
    <p><strong>Note:</strong> ${order.note}</p>
  </c:if>

<%--  <c:if test="${not empty order.shipperId}">--%>
<%--    <p><strong>Shipper ID:</strong> ${order.shipperInfo}</p>--%>
<%--  </c:if>--%>

<%--  <c:if test="${not empty order.couponId}">--%>
<%--    <p><strong>Coupon ID:</strong> ${order.couponInfo}</p>--%>
<%--  </c:if>--%>

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

<!--Footer-->
<jsp:include page="/view/common/footer.jsp"></jsp:include>
<!--/Footer--><!--/Footer-->
</body>
</html>
