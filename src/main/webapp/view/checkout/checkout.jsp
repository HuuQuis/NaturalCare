<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout | Nature Care</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
</head>
<style>
    .checkout-form {
        margin-bottom: 30px;
    }
    .address-section h3 {
        font-size: 1.2rem;
        margin-bottom: 10px;
    }
    .address-div {
        display: flex;
        width: fit-content;
        align-items: flex-start;
        gap: 12px;
        padding: 12px 16px;
        margin-bottom: 12px;
        border: 1px solid #ddd;
        border-radius: 8px;
        background-color: #fff;
        transition: border 0.3s;
    }
    .address-div.selected {
        border-color: #28a745;
        background-color: #f6fff7;
    }
    .address-div input[type="radio"] {
        margin-top: 5px;
    }
    .address-div .badge {
        background-color: #28a745;
        color: white;
        font-size: 0.75rem;
        padding: 2px 6px;
        border-radius: 4px;
        margin-left: 10px;
    }
    .add-new-address {
        display: inline-block;
        margin-top: 10px;
        color: #007bff;
        text-decoration: none;
        font-size: 0.9rem;
    }
    .add-new-address:hover {
        text-decoration: underline;
    }

    .checkout-form .form-control{
        margin-bottom: 16px !important;
        height: 40px !important;
    }

</style>
<body>
<header id="header">
    <jsp:include page="/view/common/header-top.jsp"/>
    <jsp:include page="/view/common/header-middle.jsp"/>
    <jsp:include page="/view/common/header-bottom.jsp"/>
</header>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-7">
            <form class="checkout-form" method="post" action="checkout">
            <h4 class="mb-3 fw-bold">Shipping Information</h4>

                <div class="row mb-3">
                    <div class="col-md-6 mb-3">
                        <input type="text" class="form-control" placeholder="First name" name="firstName"
                               value="${user.firstName}" disabled>
                    </div>
                    <div class="col-md-6 mb-3">
                        <input type="text" class="form-control" placeholder="Last name" name="lastName"
                               value="${user.lastName}" disabled>
                    </div>
                    <div class="col-md-12 mb-3">
                        <input type="text" class="form-control" placeholder="Phone number" name="phone"
                               value="${user.phone}" disabled>
                    </div>
                    <div class="col-md-12 mb-3">
                        <input type="email" class="form-control" placeholder="Email address" name="email"
                               value="${user.email}" disabled>
                    </div>
                    <div class="col-md-12">
                        <textarea class="form-control" placeholder="Note (e.g. Preferred delivery time)" name="note" rows="3"></textarea>
                    </div>
                </div>
                <div class="address-section mb-4">
                    <h3>Select a shipping address</h3>
                    <a href="#" class="add-new-address">+ Add new address</a>
                    <c:forEach var="addr" items="${addressList}">
                        <label class="address-div ${addr.defaultAddress ? 'selected' : ''}">
                            <input type="radio" name="addressId"
                                   value="${addr.addressId}" ${addr.defaultAddress ? 'checked' : ''} />
                            <div>
                                    ${addr.detail}, ${addr.ward.name}, ${addr.district.name}, ${addr.province.name}
                                <c:if test="${addr.defaultAddress}">
                                    <span class="badge">Default</span>
                                </c:if>
                            </div>
                        </label>
                    </c:forEach>
                </div>
                <div class="text-end mt-4">
                    <button type="submit"
                            class="btn btn-success px-4 py-2"
                            <c:if test="${empty cartItems}">disabled</c:if>>
                        <i class="fa fa-shopping-cart"></i> Place Order
                    </button>
                </div>
            </form>
        </div>

        <div class="col-md-5 checkout-summary">
            <h4 class="fw-bold mb-3">Order Summary</h4>

            <div id="cart-scroll-content" style="max-height: 300px; overflow-y: auto;">
                <c:forEach var="item" items="${cartItems}">
                    <div class="cart-item" style="display: flex; margin-bottom: 12px;">
                        <c:if test="${not empty item.variation.imageUrl}">
                            <img src="${pageContext.request.contextPath}/${item.variation.imageUrl}" width="70px" height="70px" style="margin-right: 10px;" alt="Product Image" />
                        </c:if>
                        <div class="cart-item-details">
                            <div><strong>${item.variation.productName}</strong></div>
                            <c:if test="${not empty item.variation.colorName}">
                                <div>${item.variation.colorName}</div>
                            </c:if>
                            <c:if test="${not empty item.variation.sizeName}">
                                <div>${item.variation.sizeName}</div>
                            </c:if>
                            <div style="margin: 5px 0;">
                                Quantity: ${item.quantity}
                            </div>
                            <div>
                                <span class="unit-price">
                                    <fmt:formatNumber value="${item.variation.sell_price}" type="number" groupingUsed="true" />
                                </span> đ ×
                                                    <span>${item.quantity}</span> =
                                                    <span class="line-total">
                                    <fmt:formatNumber value="${item.quantity * item.variation.sell_price}" type="number" groupingUsed="true" />
                                </span> đ
                            </div>
                        </div>
                    </div>

                </c:forEach>
            </div>

            <c:if test="${not empty cartItems}">
                <div class="cart-footer" style="border-top: 1px solid #ddd; padding: 10px;">
                    <strong>Total:</strong> <span id="cart-total">
                <fmt:formatNumber value="${cartTotal}" type="number" groupingUsed="true" />
            </span> đ
                </div>
            </c:if>

            <c:if test="${empty cartItems}">
                <div class="cart-footer" style="border-top: 1px solid #ddd; padding: 10px;">
                    <p>Empty Cart</p>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="/view/common/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/search.js"></script>
</body>
</html>
