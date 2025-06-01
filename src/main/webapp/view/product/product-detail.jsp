<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <![endif]-->
    <style>
        .color-option, .size-option {
            margin: 5px;
            padding: 8px 12px;
            border: 2px solid #ddd;
            background: #fff;
            cursor: pointer;
            border-radius: 4px;
        }
        .color-option.selected, .size-option.selected {
            border-color: #007bff;
            background-color: #007bff;
            color: white;
        }
        .variation-info {
            margin-top: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .variation-info h4 {
            margin-bottom: 10px;
            color: #333;
        }
        .variation-info p {
            margin-bottom: 5px;
            font-weight: bold;
        }
        .carousel-inner .item img {
            max-width: 100%;
            height: auto;
            transition: opacity 0.3s ease;
        }
    </style>
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
                    <div class="col-sm-5" style="padding-top: 70px">
                        <div class="view-product">
                            <div class="productinfo text-center">
                                <!-- Carousel for product images -->
                                <div id="carousel-${product.id}" class="carousel slide" data-ride="carousel" data-interval="false">
                                    <div class="carousel-inner">
                                        <!-- Display first variation image as default -->
                                        <c:if test="${not empty product.variations}">
                                            <div class="item active">
                                                <img src="${pageContext.request.contextPath}/${product.variations[0].imageUrl}" alt="${product.name}" />
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

                                    <!-- Colors -->
                                    <c:if test="${not empty product.variations[0].color}">
                                        <div class="colors">
                                            <h4>Colors:</h4>
                                            <div class="color-options">
                                                <c:forEach items="${product.variations}" var="variation">
                                                    <c:if test="${not empty variation.color}">
                                                        <button class="color-option" data-color="${variation.color}">
                                                                ${variation.color}
                                                        </button>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </c:if>

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

                                    <!-- Variation Information Display -->
                                    <div id="variation-info" class="variation-info" style="display: none;">
                                        <h4>Selected Option Details:</h4>
                                        <p>Price: <span id="price">N/A</span></p>
                                        <p>In Stock: <span id="stock">N/A</span></p>
                                        <p>Sold: <span id="sold">N/A</span></p>
                                    </div>

                                    <!-- Hidden variation data with image URLs -->
                                    <div class="variation-details" style="display: none;">
                                        <c:forEach items="${product.variations}" var="variation">
                                            <div class="variation-item"
                                                 data-color="${variation.color}"
                                                 data-size="${variation.size}"
                                                 data-image="${variation.imageUrl}">
                                                <span class="price-data">${variation.price}</span>
                                                <span class="stock-data">${variation.qtyInStock}</span>
                                                <span class="sold-data">${variation.solded}</span>
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
<script>
    let selectedColor = null;
    let selectedSize = null;
    const defaultImageUrl = '${not empty product.variations ? product.variations[0].imageUrl : ""}';

    // No need to store original images since product doesn't have original images

    // Handle color selection
    document.querySelectorAll('.color-option').forEach(button => {
        button.addEventListener('click', function() {
            // Remove selected class from all color buttons
            document.querySelectorAll('.color-option').forEach(btn => btn.classList.remove('selected'));
            // Add selected class to clicked button
            this.classList.add('selected');

            selectedColor = this.getAttribute('data-color');
            updateVariationInfo();
            updateProductImages();
        });
    });

    // Handle size selection
    document.querySelectorAll('.size-option').forEach(button => {
        button.addEventListener('click', function() {
            // Remove selected class from all size buttons
            document.querySelectorAll('.size-option').forEach(btn => btn.classList.remove('selected'));
            // Add selected class to clicked button
            this.classList.add('selected');

            selectedSize = this.getAttribute('data-size');
            updateVariationInfo();
            updateProductImages();
        });
    });

    // Function to format number with commas
    function formatNumber(num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    // Function to format price in VND
    function formatPrice(price) {
        const numPrice = parseFloat(price);
        if (isNaN(numPrice)) return 'N/A';
        return formatNumber(numPrice) + ' VND';
    }

    function updateVariationInfo() {
        const variationInfo = document.getElementById('variation-info');

        // Show variation info section
        variationInfo.style.display = 'block';

        const variationItems = document.querySelectorAll('.variation-item');
        let found = false;

        variationItems.forEach(item => {
            const color = item.getAttribute('data-color');
            const size = item.getAttribute('data-size');

            // Check if this variation matches the selected options
            const colorMatch = !selectedColor || color === selectedColor;
            const sizeMatch = !selectedSize || size === selectedSize;

            if (colorMatch && sizeMatch) {
                const price = item.querySelector('.price-data').textContent;
                const stock = item.querySelector('.stock-data').textContent;
                const sold = item.querySelector('.sold-data').textContent;

                document.getElementById('price').textContent = formatPrice(price);
                document.getElementById('stock').textContent = formatNumber(stock);
                document.getElementById('sold').textContent = formatNumber(sold);
                found = true;
            }
        });

        if (!found) {
            document.getElementById('price').textContent = 'N/A';
            document.getElementById('stock').textContent = 'N/A';
            document.getElementById('sold').textContent = 'N/A';
        }
    }

    function updateProductImages() {
        const variationItems = document.querySelectorAll('.variation-item');
        let newImageUrl = null;
        let found = false;

        // Find matching variation and get its image
        variationItems.forEach(item => {
            const color = item.getAttribute('data-color');
            const size = item.getAttribute('data-size');

            // Check if this variation matches the selected options
            const colorMatch = !selectedColor || color === selectedColor;
            const sizeMatch = !selectedSize || size === selectedSize;

            if (colorMatch && sizeMatch && !found) {
                const imageElement = item.querySelector('.image-data');
                if (imageElement && imageElement.textContent.trim()) {
                    newImageUrl = imageElement.textContent.trim();
                    found = true;
                }
            }
        });

        // Update the carousel image
        const carouselImg = document.querySelector('#carousel-${product.id} .carousel-inner .item img');

        if (found && newImageUrl && carouselImg) {
            // Update with variation image
            carouselImg.src = contextPath + newImageUrl;
        } else if (!found && defaultImageUrl && carouselImg) {
            // Reset to default image (first variation) if no specific match found
            carouselImg.src = contextPath + defaultImageUrl;
        }
    }

    // Add reset functionality (optional)
    function resetSelection() {
        selectedColor = null;
        selectedSize = null;

        // Remove all selected classes
        document.querySelectorAll('.color-option.selected, .size-option.selected').forEach(btn => {
            btn.classList.remove('selected');
        });

        // Hide variation info
        document.getElementById('variation-info').style.display = 'none';

        // Reset to default image (first variation)
        const carouselImg = document.querySelector('#carousel-${product.id} .carousel-inner .item img');
        if (defaultImageUrl && carouselImg) {
            carouselImg.src = contextPath + defaultImageUrl;
        }
    }
</script>

</body>
</html>