<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <meta name="description" content="POS - Bootstrap Admin Template">
    <meta name="keywords"
          content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
    <meta name="author" content="Dreamguys - Bootstrap Admin Template">
    <meta name="robots" content="noindex, nofollow">
    <title>News Admin</title>

    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/adminassets/img/favicon.png">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/bootstrap.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/animate.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/plugins/select2/css/select2.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/dataTables.bootstrap4.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/adminassets/plugins/fontawesome/css/fontawesome.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/plugins/fontawesome/css/all.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminStyle.css">
</head>

<body>
<div id="global-loader">
    <div class="whirly-loader"></div>
</div>

<div class="main-wrapper">

    <div class="header">
        <div class="header-left active">
            <a href="" class="logo">
            </a>
            <a href="" class="logo-small">
                <img src="${pageContext.request.contextPath}/adminassets/img/logo-small.png" alt="">
            </a>
        </div>
        <ul class="nav user-menu">
            <li class="nav-item dropdown has-arrow main-drop">
                <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                            <span class="user-img"><img
                                    src="${pageContext.request.contextPath}/adminassets/img/profiles/avator1.jpg"
                                    alt="">
                                <span class="status online"></span></span>
                </a>
                <div class="dropdown-menu menu-drop-user">
                    <div class="profilename">
                        <div class="profileset">
                                    <span class="user-img"><img
                                            src="${pageContext.request.contextPath}/adminassets/img/profiles/avator1.jpg"
                                            alt="">
                                        <span class="status online"></span></span>
                            <div class="profilesets">
                                <h5>Manager</h5>
                            </div>
                        </div>
                        <hr class="m-0">
                        <a class="dropdown-item logout pb-0" href=""><img
                                src="${pageContext.request.contextPath}/adminassets/img/icons/log-out.svg" class="me-2"
                                alt="img">Logout</a>
                    </div>
                </div>
            </li>
        </ul>
    </div>

    <jsp:include page="../common/sidebar-manager.jsp"/>

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Manage Product Variant</h4>
                    <h6>Add New Product Variant</h6>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/productVariantManage?action=add&productId=${productId}" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="add">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger" role="alert">
                                        ${error}
                                </div>
                            </c:if>
<%--                            Product Variant Information--%>
                            <div class="col-lg-12 col-sm-6 col-12">
                                <div class="form-group">
                                    <div class="form-control-plaintext">
                                        <c:forEach var="pro" items="${products}">
                                            <c:if test="${pro.id == productId}">
                                                <label>Product Line: ${pro.name}</label>
                                                <input type="hidden" name="ProductId" value="${pro.id}" />
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div><%-- Product Line Information --%>

                            <div class="col-lg-12 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Product Variant Image</label>
                                    <input type="file" name="image" accept="image/*" ${empty previousImageUrl ? 'required' : ''}>
                                    <c:if test="${not empty previousImageUrl}">
                                        <input type="hidden" name="previousImageUrl" value="${previousImageUrl}">
                                        <div class="mt-2">
                                            <span>Current image: ${previousImageUrl}</span>
                                        </div>
                                    </c:if>
                                </div>
                            </div><%-- Product Variant Image --%>

                            <div class="col-lg-12 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Product Variant Color</label>
                                    <input type="text" class="form-control" name="color" value="${tempProductVariation.color}" placeholder="Enter Product Variant Color" >
                                </div><%-- Product Variant Color --%>
                            </div>
                            <div class="col-lg-12 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Product Variant Size</label>
                                    <input type="text" class="form-control" name="size" value="${tempProductVariation.size}" placeholder="Enter size in ml"  />

                                </div>
                            </div><%-- Product Variant Size --%>

                            <div class="col-lg-12 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Product Variant Price</label>
                                    <input type="number" class="form-control" name="price" value="${tempProductVariation.price}" placeholder="Enter Product Variant Price" >
                                </div>
                            </div><%-- Product Variant Price --%>

                            <div class="col-lg-12 col-sm-6 col-12">
                                <div class="form-group">
                                    <label>Product Variant Quantity</label>
                                    <input type="number" class="form-control" name="quantity" value="${tempProductVariation.qtyInStock}" placeholder="Enter Product Variant Quantity" >
                                </div>
                            </div><%-- Product Variant Quantity --%>
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-submit me-2">Post</button>
                                <a href="${pageContext.request.contextPath}/productManage" class="btn btn-cancel">Cancel</a>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/adminassets/js/jquery-3.6.0.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/js/feather.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/js/jquery.slimscroll.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/js/dataTables.bootstrap4.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/js/bootstrap.bundle.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/plugins/select2/js/select2.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/plugins/sweetalert/sweetalerts.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/js/script.js"></script>
</body>

</html>