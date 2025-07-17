<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Product Details | Nature-Care</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/product-detail.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <![endif]-->
</head><!--/head-->

<body data-page="product-detail">
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

<section>
    <div class="container">
        <div class="row">
            <div class="col-sm-3">
                <div class="left-sidebar">
                    <div class="shipping text-center"><!--shipping-->
                        <img src="images/home/shipping.jpg" alt="" />
                    </div><!--/shipping-->
                </div>
            </div>

            <div class="col-sm-9 padding-right">
                <div class="product-details"><!--product-details-->
                    <div class="col-sm-5">
                        <div class="view-product">
                            <div class="productinfo text-center">
                                <!-- Carousel for product images -->
                                <div id="carousel-${product.id}" class="carousel slide" data-ride="carousel" data-interval="false">
                                    <div class="carousel-inner">
                                        <!-- Display first variation image as default -->
                                        <c:if test="${not empty product.variations}">
                                            <div class="item active">
                                                <img id="product-image" src="${pageContext.request.contextPath}/${product.variations[0].imageUrl}" alt="${product.name}" />
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <h3>${product.name}</h3>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-7">
                        <div class="product-information"><!--/product-information-->
                            <c:if test="${not empty product}">
                                <!-- Product Variations -->
                                <div class="product-variations">
                                    <h3>Available Options</h3>

                                        <%-- Check if there are any colors or sizes --%>
                                    <c:set var="hasColor" value="false" />
                                    <c:set var="hasSize" value="false" />

                                    <c:forEach items="${product.variations}" var="v">
                                        <c:if test="${not empty v.colorId and not empty v.colorName}">
                                            <c:set var="hasColor" value="true" />
                                        </c:if>
                                        <c:if test="${not empty v.sizeId and not empty v.sizeName}">
                                            <c:set var="hasSize" value="true" />
                                        </c:if>
                                    </c:forEach>

                                        <%-- Color Options --%>
                                    <c:if test="${hasColor}">
                                        <div class="colors">
                                            <h4>Colors:</h4>
                                            <div class="color-options">
                                                <c:forEach items="${product.variations}" var="variation">
                                                    <c:if test="${not empty variation.colorId and not empty variation.colorName}">
                                                        <button class="color-option"
                                                                data-color="${variation.colorId}"
                                                                data-color-name="${variation.colorName}"
                                                                <c:if test="${variation.qtyInStock == 0}">disabled class="out-of-stock"</c:if>>
                                                                ${variation.colorName}
                                                        </button>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </c:if>

                                        <%-- Size Options --%>
                                    <c:if test="${hasSize}">
                                        <div class="sizes">
                                            <h4>Sizes:</h4>
                                            <div class="size-options">
                                                <c:forEach items="${product.variations}" var="variation">
                                                    <c:if test="${not empty variation.sizeId and not empty variation.sizeName}">
                                                        <button class="size-option"
                                                                data-size="${variation.sizeId}"
                                                                data-size-name="${variation.sizeName}"
                                                                <c:if test="${variation.qtyInStock == 0}">disabled class="out-of-stock"</c:if>>
                                                                ${variation.sizeName}
                                                        </button>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </c:if>

                                    <!-- Variation Information Display -->
                                    <div id="variation-info" class="variation-info" style="display: none;">
                                        <h4>Selected Option Details:</h4>
                                        <p>Price: <span id="price">N/A</span></p>
                                        <p>In Stock: <span id="stock">N/A</span></p>
                                        <p>Sold: <span id="sold">N/A</span></p>
                                        <div class="d-flex align-items-center mt-3">
                                            <div class="add-cart-wrapper">
                                                <div class="quantity-wrapper">
                                                    <button class="qty-btn minus">−</button>
                                                    <input type="number" id="cart-quantity" class="qty-input" value="1" min="1">
                                                    <button class="qty-btn plus">+</button>
                                                </div>
                                                <button id="add-to-cart-btn" class="cart-btn" disabled>
                                                    Add to Cart
                                                </button>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Hidden variation data with image URLs -->
                                    <div class="variation-details" style="display: none;">
                                        <c:forEach items="${product.variations}" var="variation">
                                            <div class="variation-item"
                                                 data-variation-id="${variation.variationId}"
                                                 data-color="${variation.colorId}"
                                                 data-size="${variation.sizeId}"
                                                 data-image="${variation.imageUrl}"
                                                 data-cart-qty="${cartQuantities[variation.variationId] != null ? cartQuantities[variation.variationId] : 0}">
                                            <span class="price-data">${variation.sell_price}</span>
                                                <span class="stock-data">${variation.qtyInStock}</span>
                                                <span class="sold-data">${variation.sold}</span>
                                                <span class="image-data">${variation.imageUrl}</span>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:if>
                        </div><!--/product-information-->
                    </div>
                </div><!--/product-details-->

                <div class="category-tab shop-details-tab"><!--category-tab-->
                    <div class="col-sm-12">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#description" data-toggle="tab">Description</a></li>
                            <li><a href="#information" data-toggle="tab">Information</a></li>
                            <li><a href="#guideline" data-toggle="tab">Guideline</a></li>
                            <li><a href="#feedback" data-toggle="tab">Feedback</a></li>
                        </ul>
                    </div>
                    <div class="tab-content">
                        <div class="tab-pane fade active in" id="description" >
                            <div class="col-sm-12">
                                <h2>Product Description</h2>
                                <p>
                                    <c:if test="${not empty product}">
                                        <span class="short-text">
                                            ${fn:substring(product.description, 0, 100)}...
                                        </span>
                                        <span class="full-text" style="display: none;">
                                                ${product.description}
                                        </span>
                                        <a href="javascript:void(0);" class="view-toggle">View More</a>
                                    </c:if>
                                </p>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="information" >
                            <div class="col-sm-12">
                                <h2>Product Information</h2>
                                <p>
                                    <c:if test="${not empty product}">
                                        <span class="short-text">
                                            ${fn:substring(product.information, 0, 100)}...
                                        </span>
                                        <span class="full-text" style="display: none;">
                                                ${product.information}
                                        </span>
                                        <a href="javascript:void(0);" class="view-toggle">View More</a>
                                    </c:if>
                                </p>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="guideline" >
                            <div class="col-sm-12">
                                <h2>Product Guideline</h2>
                                <p>
                                    <c:if test="${not empty product}">
                                        <span class="short-text">
                                            ${fn:substring(product.guideline, 0, 100)}...
                                        </span>
                                        <span class="full-text" style="display: none;">
                                                ${product.guideline}
                                        </span>
                                        <a href="javascript:void(0);" class="view-toggle">View More</a>
                                    </c:if>
                                </p>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="feedback" >
                        </div>
                    </div>
                </div><!--/category-tab-->

            </div>
        </div>
    </div>
</section>

<!--Footer-->
<jsp:include page="/view/common/footer.jsp"></jsp:include>
<!--/Footer-->

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/search.js"></script>
<script src="${pageContext.request.contextPath}/js/product-detail.js"></script>
</body>
</html>