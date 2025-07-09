<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link href="https://cdn.jsdelivr.net/npm/nouislider@15.6.0/dist/nouislider.min.css" rel="stylesheet">

    <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
        <![endif]-->
</head><!--/head-->
<style>
    .multi-column {
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
        gap: 10px;
    }

    .multi-column .form-check {
        width: 45%;
    }
</style>
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
        <img src="images/shop/advertisement.jpg" alt=""/>
    </div>
</section>

<section>
    <div class="container">
        <div class="row">
            <div class="col-sm-3">
                <div class="left-sidebar">
                    <jsp:include page="/view/category/category-sidebar.jsp"/>
                </div>

                <!-- Start Filter Form -->
                <form id="filterForm" method="get">
                    <!-- Giữ các filter khác -->
                    <c:if test="${not empty param.category}">
                        <input type="hidden" name="category" value="${param.category}"/>
                    </c:if>
                    <c:if test="${not empty param.subcategory}">
                        <input type="hidden" name="subcategory" value="${param.subcategory}"/>
                    </c:if>
                    <c:if test="${not empty param.sort}">
                        <input type="hidden" name="sort" value="${param.sort}"/>
                    </c:if>

                    <!-- PRICE FILTER -->
                    <div class="price-filter-box" style="margin-top: 30px;">
                        <input type="hidden" id="minPrice" name="minPrice"
                               value="${fn:trim(param.minPrice != null && param.minPrice != '' ? param.minPrice : '0')}"/>
                        <input type="hidden" id="maxPrice" name="maxPrice"
                               value="${fn:trim(param.maxPrice != null && param.maxPrice != '' ? param.maxPrice : '9999999')}"/>

                        <h5 style="display: flex; justify-content: center; padding-bottom: 35px">Filter by Price
                            Range</h5>
                        <div id="slider-range" style="margin: 15px 0; padding-right: 15px"></div>

                        <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 10px;">
                            <label for="minInput"></label><input type="number" id="minInput" min="0" step="10000"
                                                                 class="form-control"/>
                            <span style="white-space: nowrap;">–</span>
                            <label for="maxInput"></label><input type="number" id="maxInput" min="0" step="10000"
                                                                 class="form-control"/>
                        </div>

                        <div style="margin-top: 10px; text-align: center;">
                            <button style="margin-top:0 !important;border-radius: 30px !important;" type="button"
                                    id="applyPriceBtn" class="btn btn-primary btn-sm">Apply
                            </button>
                        </div>
                    </div>

                    <!-- COLOR FILTER -->
                    <div class="price-filter-box" style="margin-top: 30px;">
                        <h5 style="text-align:center;">Filter by Color</h5>
                        <div class="multi-column">
                            <c:forEach var="color" items="${colorList}">
                                <c:set var="checked" value="false"/>
                                <c:forEach var="cid" items="${paramValues['color']}">
                                    <c:if test="${cid == color.name}">
                                        <c:set var="checked" value="true"/>
                                    </c:if>
                                </c:forEach>

                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="color"
                                           value="${color.name}" id="color${color.id}"
                                           <c:if test="${checked}">checked</c:if> >
                                    <label class="form-check-label" for="color${color.id}">${color.name}</label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>


                    <!-- SIZE FILTER -->
                    <div class="price-filter-box" style="margin-top: 30px;">
                        <h5 style="text-align:center;">Filter by Size</h5>
                        <div class="multi-column">
                            <c:forEach var="size" items="${sizeList}">
                                <c:set var="checked" value="false"/>
                                <c:forEach var="sid" items="${paramValues['size']}">
                                    <c:if test="${sid == size.name}">
                                        <c:set var="checked" value="true"/>
                                    </c:if>
                                </c:forEach>

                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="size"
                                           value="${size.name}" id="size${size.id}"
                                           <c:if test="${checked}">checked</c:if> >
                                    <label class="form-check-label" for="size${size.id}">${size.name}</label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </form>
                <!-- End Filter Form -->
            </div>

            <div class="col-sm-9 padding-right">
                <!-- Sort Dropdown -->
                <div style="margin-bottom: 15px;">
                    <c:set var="sort" value="${param.sort}"/>
                    <form id="sortForm" method="get" style="display:inline;">
                        <c:if test="${not empty param.category}">
                            <input type="hidden" name="category" value="${fn:trim(param.category)}"/>
                        </c:if>
                        <c:if test="${not empty param.subcategory}">
                            <input type="hidden" name="subcategory" value="${fn:trim(param.subcategory)}"/>
                        </c:if>
                        <c:if test="${not empty param.minPrice}">
                            <input type="hidden" name="minPrice" value="${fn:trim(param.minPrice)}"/>
                        </c:if>
                        <c:if test="${not empty param.maxPrice}">
                            <input type="hidden" name="maxPrice" value="${fn:trim(param.maxPrice)}"/>
                        </c:if>
                        <c:forEach var="colorName" items="${paramValues['color']}">
                            <input type="hidden" name="color" value="${colorName}"/>
                        </c:forEach>
                        <c:forEach var="sizeName" items="${paramValues['size']}">
                            <input type="hidden" name="size" value="${sizeName}"/>
                        </c:forEach>
                        <input type="hidden" name="index" value="1"/>
                        <label for="sort" style="font-weight:bold;">Sort by:</label>
                        <select id="sort" name="sort" class="form-control" style="width:auto;display:inline-block;">
                            <option value="">Default</option>
                            <option value="name-asc" ${sort == 'name-asc' ? 'selected' : ''}>Sort Product Name From A to
                                Z
                            </option>
                            <option value="name-desc" ${sort == 'name-desc' ? 'selected' : ''}>Sort Product Name From Z
                                to A
                            </option>
                            <option value="price-asc" ${sort == 'price-asc' ? 'selected' : ''}>Sort Product by Price
                                ascending
                            </option>
                            <option value="price-desc" ${sort == 'price-desc' ? 'selected' : ''}>Sort Product by Price
                                descending
                            </option>
                        </select>
                    </form>
                </div>
                <script>
                    document.getElementById('sort').addEventListener('change', function () {
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
                                                        <c:forEach items="${product.imageUrls}" var="imageUrl"
                                                                   varStatus="status">
                                                            <div class="item ${status.index == 0 ? 'active' : ''}">
                                                                <img src="${pageContext.request.contextPath}/${imageUrl}"
                                                                     alt="${product.name}"/>
                                                            </div>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="item active">
                                                            <img src="${pageContext.request.contextPath}/images/product/default-image.jpg"
                                                                 alt="No Image Available"/>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <p>${product.name}</p>
                                        <c:if test="${product.minPrice > 0}">
                                            <p>Start from: <span class="price">${product.minPrice}</span></p>
                                        </c:if>
                                    </div>
                                    <div class="product-overlay">
                                        <div class="overlay-content">

                                            <a href="productDetail?product_id=${product.id}"
                                               class="btn btn-default product-details">
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

                <!-- Tạo queryString để dùng lại -->
                <c:set var="queryString" value=""/>
                <c:if test="${not empty selectedCategoryId}">
                    <c:set var="queryString" value="${queryString}&category=${fn:trim(selectedCategoryId)}"/>
                </c:if>
                <c:if test="${not empty selectedSubCategoryId}">
                    <c:set var="queryString" value="${queryString}&subcategory=${fn:trim(selectedSubCategoryId)}"/>
                </c:if>
                <c:if test="${not empty sort}">
                    <c:set var="queryString" value="${queryString}&sort=${fn:trim(sort)}"/>
                </c:if>
                <c:if test="${not empty param.minPrice}">
                    <c:set var="queryString" value="${queryString}&minPrice=${fn:trim(param.minPrice)}"/>
                </c:if>
                <c:if test="${not empty param.maxPrice}">
                    <c:set var="queryString" value="${queryString}&maxPrice=${fn:trim(param.maxPrice)}"/>
                </c:if>

                <div class="col-sm-12 text-center">
                    <ul class="pagination">
                        <c:if test="${endPage > 1}">
                            <!-- First page -->
                            <li class="${empty param.index || param.index == 1 ? 'disabled' : ''}">
                                <a href="products?index=1${queryString}">&laquo;</a>
                            </li>
                        </c:if>

                        <!-- Page numbers -->
                        <c:forEach begin="1" end="${endPage}" var="page">
                            <c:set var="curIndex" value="${param.index != null ? fn:trim(param.index) : '1'}"/>
                            <li class="${curIndex == page ? 'active' : ''}">
                                <a href="products?index=${page}${queryString}">${page}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${endPage > 1}">
                            <!-- Last page -->
                            <li class="${param.index == endPage ? 'disabled' : ''}">
                                <a href="products?index=${endPage}${queryString}">&raquo;</a>
                            </li>
                        </c:if>
                    </ul>
                </div>

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
<script src="https://cdn.jsdelivr.net/npm/nouislider@15.6.0/dist/nouislider.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/wnumb@1.2.0/wNumb.min.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const slider = document.getElementById('slider-range');
        const form = document.getElementById('filterForm');

        const hiddenMin = document.getElementById('minPrice');
        const hiddenMax = document.getElementById('maxPrice');
        const inputMin = document.getElementById('minInput');
        const inputMax = document.getElementById('maxInput');
        const applyBtn = document.getElementById('applyPriceBtn');

        const step = 10000;
        const startMin = parseInt(hiddenMin.value) || 0;
        const startMax = parseInt(hiddenMax.value) || 9999999;

        let priceManuallyApplied = false; // 🆕 Flag

        noUiSlider.create(slider, {
            start: [startMin, startMax],
            connect: true,
            range: {min: 0, max: 9999999},
            step: step,
            tooltips: [
                wNumb({decimals: 0, thousand: '.', suffix: ' ₫'}),
                wNumb({decimals: 0, thousand: '.', suffix: ' ₫'})
            ]
        });

        slider.noUiSlider.on('update', function (values) {
            const min = Math.round(values[0]);
            const max = Math.round(values[1]);
            hiddenMin.value = min;
            hiddenMax.value = max;
            inputMin.value = min;
            inputMax.value = max;
        });

        // 🧠 Chỉ set flag và submit nếu user Apply
        slider.noUiSlider.on('change', function (values) {
            let min = Math.round(values[0]);
            let max = Math.round(values[1]);
            if (min >= max) {
                if (min === max) {
                    if (min === 0) {
                        max = step;
                    } else {
                        min = Math.max(0, min - step);
                    }
                }
                slider.noUiSlider.set([min, max]);
                return;
            }

            priceManuallyApplied = true;
            form.submit();
        });

        function updateSliderFromInputs(submitAfter = false) {
            let minVal = parseInt(inputMin.value) || 0;
            let maxVal = parseInt(inputMax.value) || 9999999;

            // Kiểm tra số âm
            if (minVal < 0 || maxVal < 0) {
                alert("Prices must be non-negative.");
                return;
            }

            // Kiểm tra min >= max
            if (maxVal <= minVal) {
                alert("Max price must be greater than Min price. It has been adjusted automatically.");
                maxVal = minVal + step;
                inputMax.value = maxVal;
            }

            // Cập nhật hidden inputs
            hiddenMin.value = minVal;
            hiddenMax.value = maxVal;

            // Cập nhật lại slider
            slider.noUiSlider.set([minVal, maxVal]);

            if (submitAfter) {
                priceManuallyApplied = true;
                setTimeout(() => form.submit(), 50);
            }
        }

        function handleEnter(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                updateSliderFromInputs(true);
            }
        }

        inputMin.addEventListener('keydown', handleEnter);
        inputMax.addEventListener('keydown', handleEnter);
        applyBtn.addEventListener('click', () => updateSliderFromInputs(true));

        // ✅ Auto-submit khi chọn color/size — nhưng KHÔNG gửi minPrice/maxPrice nếu chưa Apply
        document.querySelectorAll('#filterForm input[type="checkbox"]').forEach(cb => {
            cb.addEventListener('change', () => {
                if (!priceManuallyApplied) {
                    hiddenMin.removeAttribute("name");
                    hiddenMax.removeAttribute("name");
                }
                form.submit();
            });
        });
    });
</script>

</body>
</html>