<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Product Details | E-Shopper</title>
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
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-7">
                        <div class="product-information"><!--/product-information-->
                            <c:if test="${not empty product}">
                                <h2>${product.name}</h2>
                                
                                <!-- Product Variations -->
                                <div class="product-variations">
                                    <h3>Available Options</h3>
                                    
                                    <!-- Colors -->
                                    <div class="colors">
                                        <h4>Colors:</h4>
                                        <div class="color-options">
                                            <c:forEach items="${product.variations}" var="variation">
                                                <button class="color-option" data-color="${variation.color}">
                                                    ${variation.color}
                                                </button>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <!-- Sizes -->
                                    <c:if test="${not empty product.variations[0].size}">
                                        <div class="sizes">
                                            <h4>Sizes:</h4>
                                            <div class="size-options">
                                                <c:forEach items="${product.variations}" var="variation">
                                                    <c:if test="${not empty variation.size}">
                                                        <button class="size-option" data-size="${variation.size}">
                                                            ${variation.size}
                                                        </button>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </c:if>

                                    <!-- Variation Details -->
                                    <div class="variation-details">
                                        <c:forEach items="${product.variations}" var="variation">
                                            <div class="variation-item" data-color="${variation.color}" data-size="${variation.size}">
                                                <p>Price: $${variation.price}</p>
                                                <p>In Stock: ${variation.qtyInStock}</p>
                                                <p>Sold: ${variation.solded}</p>
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
                                        ${product.description}
                                    </c:if>
                                </p>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="information" >
                            <div class="col-sm-12">
                                <h2>Product Information</h2>
                                <p>
                                    <c:if test="${not empty product}">
                                        ${product.information}
                                    </c:if>
                                </p>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="guideline" >
                            <div class="col-sm-12">
                                <h2>Product Guideline</h2>
                                <p>
                                    <c:if test="${not empty product}">
                                        ${product.guideline}
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
<!--/Footer--><!--/Footer-->

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
