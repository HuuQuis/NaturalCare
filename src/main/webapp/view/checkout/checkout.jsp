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
    <link href="${pageContext.request.contextPath}/css/checkout.css" rel="stylesheet">
</head>
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

                <!-- ✅ checkout.jsp - Hiển thị thông tin người nhận từ defaultAddress -->
                <div class="row mb-3">
                    <c:choose>
                        <c:when test="${not empty defaultAddress}">
                            <div class="col-md-6 mb-3">
                                <input type="text" class="form-control" placeholder="First name"
                                       data-field="firstName" value="${defaultAddress.firstName}" readonly>
                            </div>
                            <div class="col-md-6 mb-3">
                                <input type="text" class="form-control" placeholder="Last name"
                                       data-field="lastName" value="${defaultAddress.lastName}" readonly>
                            </div>
                            <div class="col-md-12 mb-3">
                                <input type="text" class="form-control" placeholder="Phone number"
                                       data-field="phoneNumber" value="${defaultAddress.phoneNumber}" readonly>
                            </div>
                            <div class="col-md-12 mb-3">
                                <input type="email" class="form-control" placeholder="Email address"
                                       data-field="email" value="${defaultAddress.email}" readonly>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-md-12">
                                <div class="alert alert-warning">No default address selected. Please choose one below.</div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <div class="col-md-12">
                        <textarea class="form-control" placeholder="Note (e.g. Preferred delivery time)" name="note" rows="3"></textarea>
                    </div>
                </div>
                <div class="address-section mb-4">
                    <h3>Select a shipping address</h3>
                    <div class="add-address-btn" onclick="addNewAddress()">
                        <i class="fa fa-plus"></i> Add New Address
                    </div>
                    <c:forEach var="addr" items="${addressList}">
                        <label class="address-div ${addr.defaultAddress ? 'selected' : ''}">
                            <input type="radio" name="addressId"
                                   value="${addr.addressId}"
                                   data-firstname="${addr.firstName}"
                                   data-lastname="${addr.lastName}"
                                   data-email="${addr.email}"
                                   data-phone="${addr.phoneNumber}"
                                ${addr.defaultAddress ? 'checked' : ''} />
                            <div>
                                    ${addr.detail}, ${addr.ward.name}, ${addr.district.name}, ${addr.province.name}
                                <c:if test="${addr.defaultAddress}">
                                    <span class="badge">Default</span>
                                </c:if>
                            </div>
                        </label>
                    </c:forEach>
                </div>
                <div class="payment-methods mb-4">
                    <h4 class="fw-bold mb-2">Choose Payment Method</h4>
                    <div class="payment-option">
                        <label>
                            <input type="radio" name="paymentMethod" value="cod" checked/>
                            <img src="${pageContext.request.contextPath}/images/payment/shipcod-logo.jpg" alt="ZaloPay" />
                            Thanh toán khi nhận hàng (COD)
                        </label>
                    </div>
                    <div class="payment-option">
                        <label>
                            <input type="radio" name="paymentMethod" value="vnpay"/>
                            <img src="${pageContext.request.contextPath}/images/payment/vnpay-logo.jpg" alt="VNPAY" />
                            Thẻ ATM / Thẻ tín dụng / Thẻ ghi nợ (qua VNPAY)
                        </label>
                    </div>
                </div>
                <div class="text-end mt-4">
                    <button type="submit"
                            class="btn btn-success px-4 py-2"
                            style="margin-top: 10px;"
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
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const radios = document.querySelectorAll('input[name="addressId"]');
        const overlay = document.getElementById("loadingOverlay");

        radios.forEach(radio => {
            radio.addEventListener('change', function () {
                // Hiện overlay
                overlay.style.display = "flex";

                // Lấy dữ liệu từ data-* attributes
                const firstName = this.dataset.firstname;
                const lastName = this.dataset.lastname;
                const email = this.dataset.email;
                const phone = this.dataset.phone;

                // Cập nhật các ô input (readonly vẫn cho phép JS thay đổi)
                document.querySelector('input[data-field="firstName"]').value = firstName;
                document.querySelector('input[data-field="lastName"]').value = lastName;
                document.querySelector('input[data-field="email"]').value = email;
                document.querySelector('input[data-field="phoneNumber"]').value = phone;

                // Bỏ class selected khỏi tất cả
                document.querySelectorAll('.address-div').forEach(label => {
                    label.classList.remove('selected');
                });

                // Gán lại selected cho label hiện tại
                this.closest('.address-div').classList.add('selected');

                // Tắt overlay sau delay nhẹ
                setTimeout(() => {
                    overlay.style.display = "none";
                }, 1000);
            });
        });
    });
</script>

<div id="loadingOverlay">
    <div class="spinner"></div>
</div>
</body>
</html>
