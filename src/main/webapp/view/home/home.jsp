<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Home | Nature Care</title>
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
</head>

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

<section id="slider"><!--slider-->
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div id="slider-carousel" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#slider-carousel" data-slide-to="0" class="active"></li>
                        <li data-target="#slider-carousel" data-slide-to="1"></li>
                        <li data-target="#slider-carousel" data-slide-to="2"></li>
                    </ol>

                    <div class="carousel-inner">
                        <div class="item active">
                            <div class="col-sm-6" style="padding-left: 90px">
                                <h1><span>Nature</span>-Care</h1>
                                <h2>New Arrive!</h2>
                                <p>Our natural lipstick is made from plant-based ingredients, offering beautiful color with gentle care for your lips.</p>
                                <button type="button" class="btn btn-default get">Get it now</button>
                            </div>
                            <div class="col-sm-6" style="padding-left: 90px">
                                <img style="width: 400px; height: 400px; object-fit: cover;border-radius: 10% !important"
                                        src="${pageContext.request.contextPath}/images/product/lipstick-red.jpg" class="img-responsive" alt="" />
                            </div>
                        </div>
                        <div class="item">
                            <div class="col-sm-6" style="padding-left: 90px">
                                <h1><span>Nature</span>-Care</h1>
                                <h2>Buy 2 Get 1 Free</h2>
                                <p>Our Natural Lipstick Gift Set combines beauty and care, featuring plant-based lipsticks in a beautifully packaged box—perfect for any occasion.</p>
                                <button type="button" class="btn btn-default get">Get it now</button>
                            </div>
                            <div class="col-sm-6" style="padding-left: 90px">
                                <img style="width: 400px; height: 400px; object-fit: cover; border-radius: 10% !important"
                                        src="${pageContext.request.contextPath}/images/product/giftset.jpg" class="img-responsive" alt="" />
                            </div>
                        </div>

                        <div class="item">
                            <div class="col-sm-6" style="padding-left: 90px">
                                <h1><span>Nature</span>-Care</h1>
                                <h2>Hot Deal</h2>
                                <p>Our natural shampoo is crafted with herbal extracts to gently cleanse your hair while nourishing the scalp—free from sulfates and harsh chemicals.</p>
                                <button type="button" class="btn btn-default get">Get it now</button>
                            </div>
                            <div class="col-sm-6" style="padding-left: 90px">
                                <img style="width: 400px; height: 400px; object-fit: cover;border-radius: 10% !important"
                                        src="${pageContext.request.contextPath}/images/product/shampoo.jpg" class="img-responsive" alt="" />
                            </div>
                        </div>
                    </div>

                    <a href="#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
                        <i class="fa fa-angle-left"></i>
                    </a>
                    <a href="#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
                        <i class="fa fa-angle-right"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
</section><!--/slider-->


<section>
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="features_items"><!--features_items-->
                    <h2 class="title text-center">New Arrival</h2>
                    <div id="feature-item-carousel" class="carousel slide" data-ride="carousel">
                        <div class="carousel-inner">
                            <div class="item active">

                            </div>
                            <div class="item">

                            </div>
                        </div>
                        <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
                            <i class="fa fa-angle-left"></i>
                        </a>
                        <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
                            <i class="fa fa-angle-right"></i>
                        </a>
                    </div>
                </div><!--features_items-->

                <div class="recommended_items"><!--recommended_items-->
                    <h2 class="title text-center">Top Selling Products</h2>
                    <div id="recommended-item-carousel" class="carousel slide" data-ride="carousel" data-interval="2000" data-pause="hover">
                        <div class="carousel-inner">
                            <div class="item active">
                                <c:forEach var="product" items="${topProducts}" varStatus="loop">
                                    <div class="col-sm-3">
                                        <div class="product-image-wrapper">
                                            <div class="single-products">
                                                <div class="productinfo text-center">
                                                    <a href="${pageContext.request.contextPath}/productDetail?product_id=${product.id}">
                                                        <c:choose>
                                                            <c:when test="${not empty product.imageUrls}">
                                                                <img src="${pageContext.request.contextPath}/${product.imageUrls[0]}" alt="" style="width: 200px !important; height: 200px !important;" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img src="${pageContext.request.contextPath}/images/product/default.jpg" alt="" style="width: 200px !important; height: 200px !important;" />
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </a>
                                                    <h2>${product.minPrice}₫</h2>
                                                    <a href="${pageContext.request.contextPath}/productDetail?product_id=${product.id}" style="text-decoration: none;">
                                                        <p style="color: #333;">${product.name}</p>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
                            <i class="fa fa-angle-left"></i>
                        </a>
                        <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
                            <i class="fa fa-angle-right"></i>
                        </a>
                    </div>
                </div><!--/recommended_items-->

            </div>
        </div>
    </div>
    
<button class="btn btn-primary">
        <a href="/NaturalCare/OrderHistory?userId=${sessionScope.user.id}" style="color: white; text-decoration: none;">
          Go to Order History
        </a>
</button>
</section>


<!--Footer-->
<jsp:include page="/view/common/footer.jsp"></jsp:include>
<!--/Footer--><!--/Footer-->

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