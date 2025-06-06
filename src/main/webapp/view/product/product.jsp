<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Shop | Nature-Care</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <![endif]-->
</head><!--/head-->

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

<section id="advertisement">
    <div class="container">
        <img src="images/shop/advertisement.jpg" alt="" />
    </div>
</section>

<section>
    <div class="container">
        <div class="row">
            <div class="col-sm-3">
                <div class="left-sidebar">
                    <jsp:include page="/view/category/category-sidebar.jsp" />
                </div>
            </div>

            <div class="col-sm-9 padding-right">
                <div class="features_items"><!--features_items-->
                    <h2 class="title text-center">Features Items</h2>
                    <c:forEach items="${products}" var="product">
                        <div class="col-sm-4">
                            <div class="product-image-wrapper">
                                <div class="single-products">
                                    <div class="productinfo text-center">
                                        <!-- Carousel for product images -->
                                        <div id="carousel-${product.id}" class="carousel slide" data-ride="carousel">
                                            <div class="carousel-inner">
                                                <c:forEach items="${product.imageUrls}" var="imageUrl" varStatus="status">
                                                    <div class="item ${status.index == 0 ? 'active' : ''}">
                                                        <img src="${pageContext.request.contextPath}/${imageUrl}" alt="${product.name}" />
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <c:if test="${product.imageUrls.size() > 1}">
                                                <a class="left carousel-control" href="#carousel-${product.id}" data-slide="prev">
                                                </a>
                                                <a class="right carousel-control" href="#carousel-${product.id}" data-slide="next">
                                                </a>
                                            </c:if>
                                        </div>
                                        <p>${product.name}</p>
                                        <c:if test="${product.minPrice > 0}">
                                            <p>Start from: <span class="price">${product.minPrice}</span></p>
                                        </c:if>
                                    </div>
                                    <div class="product-overlay">
                                        <div class="overlay-content">
                                            <p>${product.name}</p>
                                            <p>${product.description}</p>
                                            <a href="#" class="btn btn-default add-to-cart">
                                                <i class="fa fa-shopping-cart"></i>Add to cart
                                            </a>
                                            <a href="productDetail?product_id=${product.id}" class="btn btn-default product-details">
                                                <i class="fa fa-info"></i>Product details
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="choose">
                                    <ul class="nav nav-pills nav-justified">
                                        <li><a href="#"><i class="fa fa-plus-square"></i>Add to compare</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div><!--features_items-->

                <div class="col-sm-12 text-center">
                    <ul class="pagination">
                        <c:forEach begin="1" end="${endPage}" var="page">
                            <c:choose>
                                <c:when test="${not empty selectedSubCategoryId}">
                                    <li><a href="products?index=${page}&subcategory=${selectedSubCategoryId}">${page}</a></li>
                                </c:when>
                                <c:when test="${not empty selectedCategoryId}">
                                    <li><a href="products?index=${page}&category=${selectedCategoryId}">${page}</a></li>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </div><!--/pagination-->
            </div>
        </div>
    </div>
</section>

<!--Footer-->
<jsp:include page="/view/common/footer.jsp"></jsp:include>
<!--/Footer--><!--/Footer--><!--/Footer-->

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/search.js"></script>
</body>
</html>
