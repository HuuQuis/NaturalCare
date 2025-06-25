<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach var="item" items="${cartItems}">
  <div class="cart-item" style="display: flex; margin-bottom: 12px;">
    <c:if test="${not empty item.variation.imageUrl}">
      <img src="${item.variation.imageUrl}" width="60" style="margin-right: 10px;"  alt="Product Image"/>
    </c:if>
    <div class="cart-item-details">
      <div><strong>${item.variation.productName}</strong></div>
      <c:if test="${not empty item.variation.colorName}">
        <div>${item.variation.colorName}</div>
      </c:if>
      <c:if test="${not empty item.variation.colorName}">
        <div>${item.variation.sizeName}</div>
      </c:if>
      <div>${item.quantity} ×
        <fmt:formatNumber value="${item.variation.sell_price}" type="number" groupingUsed="true" /> đ
        =
        <fmt:formatNumber value="${item.quantity * item.variation.sell_price}" type="number" groupingUsed="true" /> đ
      </div>
    </div>
  </div>
</c:forEach>

<c:if test="${not empty cartItems}">
  <div style="text-align: right; font-weight: bold; margin-top: 15px;">
    Total: <fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true" /> đ
  </div>
</c:if>

<c:if test="${empty cartItems}">
  <p>Empty Cart</p>
</c:if>
