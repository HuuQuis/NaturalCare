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
                <!-- Sort Dropdown -->
                <div style="margin-bottom: 15px;">
                    <form id="sortForm" method="get" style="display:inline;">
                        <c:if test="${not empty param.category}">
                            <input type="hidden" name="category" value="${param.category}">
                        </c:if>
                        <c:if test="${not empty param.subcategory}">
                            <input type="hidden" name="subcategory" value="${param.subcategory}">
                        </c:if>
                        <input type="hidden" name="index" value="${param.index}">
                        <label for="sort" style="font-weight:bold;">Sort by:</label>
                        <select id="sort" name="sort" class="form-control" style="width:auto;display:inline-block;">
                            <option value="">-- Select --</option>
                            <option value="name-asc" ${sort == 'name-asc' ? 'selected' : ''}>Sort Product Name From A to Z</option>
                            <option value="name-desc" ${sort == 'name-desc' ? 'selected' : ''}>Sort Product Name From Z to A</option>
                            <option value="price-asc" ${sort == 'price-asc' ? 'selected' : ''}>Sort Product by Price ascending</option>
                            <option value="price-desc" ${sort == 'price-desc' ? 'selected' : ''}>Sort Product by Price descending</option>
                        </select>
                    </form>
                </div>
                <script>
                    document.getElementById('sort').addEventListener('change', function() {
                        document.getElementById('sortForm').submit();
                    });
                </script>
                <!-- End Sort Dropdown -->

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
                                                <c:choose>
                                                    <c:when test="${not empty product.imageUrls}">
                                                        <c:forEach items="${product.imageUrls}" var="imageUrl" varStatus="status">
                                                            <div class="item ${status.index == 0 ? 'active' : ''}">
                                                                <img src="${pageContext.request.contextPath}/${imageUrl}" alt="${product.name}" />
                                                            </div>
                                                        </c:forEach>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <div class="item active">
                                                            <img src="${pageContext.request.contextPath}/images/default-product.png" alt="No Image Available" />
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>

                                            <c:if test="${product.imageUrls != null && product.imageUrls.size() > 1}">
                                                <a class="left carousel-control" href="#carousel-${product.id}" data-slide="prev">
                                                    <span class="glyphicon glyphicon-chevron-left"></span>
                                                </a>
                                                <a class="right carousel-control" href="#carousel-${product.id}" data-slide="next">
                                                    <span class="glyphicon glyphicon-chevron-right"></span>
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
                        <c:if test="${endPage > 1}">
                            <!-- First page button -->
                            <li class="${empty param.index || param.index == 1 ? 'disabled' : ''}">
                                <c:choose>
                                    <c:when test="${not empty selectedSubCategoryId}">
                                        <a href="products?index=1&subcategory=${selectedSubCategoryId}">&laquo;</a>
                                    </c:when>
                                    <c:when test="${not empty selectedCategoryId}">
                                        <a href="products?index=1&category=${selectedCategoryId}">&laquo;</a>
                                    </c:when>
                                </c:choose>
                            </li>
                        </c:if>

                        <c:forEach begin="1" end="${endPage}" var="page">
                            <c:choose>
                                <c:when test="${not empty selectedSubCategoryId}">
                                    <li class="${empty param.index && page == 1 || param.index == page ? 'active' : ''}">
                                        <a href="products?index=${page}&subcategory=${selectedSubCategoryId}">${page}</a>
                                    </li>
                                </c:when>
                                <c:when test="${not empty selectedCategoryId}">
                                    <li class="${empty param.index && page == 1 || param.index == page ? 'active' : ''}">
                                        <a href="products?index=${page}&category=${selectedCategoryId}">${page}</a>
                                    </li>
                                </c:when>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${endPage > 1}">
                            <!-- Last page button -->
                            <li class="${param.index == endPage ? 'disabled' : ''}">
                                <c:choose>
                                    <c:when test="${not empty selectedSubCategoryId}">
                                        <a href="products?index=${endPage}&subcategory=${selectedSubCategoryId}">&raquo;</a>
                                    </c:when>
                                    <c:when test="${not empty selectedCategoryId}">
                                        <a href="products?index=${endPage}&category=${selectedCategoryId}">&raquo;</a>
                                    </c:when>
                                </c:choose>
                            </li>
                        </c:if>
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