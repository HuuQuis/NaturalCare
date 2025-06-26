<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="cart-scroll-content" style="max-height: 300px; overflow-y: auto;">
<c:forEach var="item" items="${cartItems}">
  <div class="cart-item" style="display: flex; margin-bottom: 12px;">
    <c:if test="${not empty item.variation.imageUrl}">
      <img src="${pageContext.request.contextPath}/${item.variation.imageUrl}" width="70px" height="70px" style="margin-right: 10px;"  alt="Product Image"/>
    </c:if>
    <div class="cart-item-details">
      <div><strong>${item.variation.productName}</strong></div>
      <c:if test="${not empty item.variation.colorName}">
        <div>${item.variation.colorName}</div>
      </c:if>
      <c:if test="${not empty item.variation.sizeName}">
        <div>${item.variation.sizeName}</div>
      </c:if>
      <div class="quantity-wrapper">
        <button class="qty-btn minus">−</button>
        <input type="number" class="quantity-input"
               value="${item.quantity}"
               data-price="${item.variation.sell_price}"
               data-variation-id="${item.variation.variationId}"
               data-max="${item.variation.qtyInStock}"
        />
        <button class="qty-btn plus">+</button>
      </div>
      <span class="unit-price">
        <fmt:formatNumber value="${item.variation.sell_price}" type="number" groupingUsed="true" />
      </span> đ
      =
      <span class="line-total">
        <fmt:formatNumber value="${item.quantity * item.variation.sell_price}" type="number" groupingUsed="true" />
      </span> đ
    </div>
  </div>
</c:forEach>
</div>

<c:if test="${not empty cartItems}">
  <div class="cart-footer" style="border-top: 1px solid #ddd; padding: 10px;">
    Total: <span id="cart-total">
      <fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true" />
    </span> đ
  </div>
</c:if>

<c:if test="${empty cartItems}">
  <<div class="cart-footer" style="border-top: 1px solid #ddd; padding: 10px;">
    <p>Empty Cart</p>
  </div>
</c:if>
