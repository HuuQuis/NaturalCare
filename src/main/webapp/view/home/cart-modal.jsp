<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach var="item" items="${cartItems}">
  <div class="cart-item" style="display: flex; margin-bottom: 12px;">
    <img src="${item.variation.imageUrl}" width="60" style="margin-right: 10px;" />
    <div>
      <div><strong>${item.variation.productName}</strong></div>
      <div>${item.variation.colorName} / ${item.variation.sizeName}</div>
      <div>${item.quantity} × <fmt:formatNumber value="${item.variation.sell_price}" type="currency" currencySymbol="đ" /></div>
    </div>
  </div>
</c:forEach>

<c:if test="${empty cartItems}">
  <p>Empty Cart</p>
</c:if>
