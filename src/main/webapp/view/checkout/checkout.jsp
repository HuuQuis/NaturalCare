<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
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
</style>
<body>
<header id="header">
    <jsp:include page="/view/common/header-top.jsp"></jsp:include>
    <jsp:include page="/view/common/header-middle.jsp"></jsp:include>
    <jsp:include page="/view/common/header-bottom.jsp"></jsp:include>
</header>

<div class="container mt-4">
    <div class="row">
        <div class="col-md-7 checkout-form">
            <div class="address-section">
                <h3>Select a shipping address</h3>
                <a href="#" class="add-new-address">+ Add new address</a>
                <c:forEach var="addr" items="${addressList}">
                    <label class="address-div ${addr.defaultAddress ? 'selected' : ''}">
                        <input type="radio" name="addressId" value="${addr.addressId}" ${addr.defaultAddress ? 'checked' : ''} />
                        <div>
                                ${addr.detail}, ${addr.ward.name}, ${addr.district.name}, ${addr.province.name}
                            <c:if test="${addr.defaultAddress}">
                                <span class="badge">Default</span>
                            </c:if>
                        </div>
                    </label>
                </c:forEach>
            </div>
        </div>

        <div class="col-md-5 checkout-summary">
<%--            <jsp:include page="/view/checkout/cart-summary.jsp" />--%>
        </div>
    </div>
</div>

<jsp:include page="/view/common/footer.jsp"></jsp:include>

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
