<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>Breeze Admin</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/adminassets/vendors/mdi/css/materialdesignicons.min.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/adminassets/vendors/flag-icon-css/css/flag-icon.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/vendors/css/vendor.bundle.base.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/adminassets/vendors/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/adminassets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/style.css"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/adminassets/images/favicon.png"/>
</head>

<body>
<div class="container-scroller">

    <!-- SIDEBAR -->
    <jsp:include page="../common/sidebar-manager.jsp"/>

    <!-- BODY WRAPPER -->
    <div class="container-fluid page-body-wrapper">

        <!-- HEADER -->
        <jsp:include page="../common/header-manager.jsp"/>

        <!-- SETTINGS PANEL (có thể đặt trong content-wrapper nếu cần) -->
        <div id="theme-settings" class="settings-panel">
            <i class="settings-close mdi mdi-close"></i>
            <p class="settings-heading">SIDEBAR SKINS</p>
            <div class="sidebar-bg-options selected" id="sidebar-default-theme">
                <div class="img-ss rounded-circle bg-light border mr-3"></div>
                Default
            </div>
            <div class="sidebar-bg-options" id="sidebar-dark-theme">
                <div class="img-ss rounded-circle bg-dark border mr-3"></div>
                Dark
            </div>
            <p class="settings-heading mt-2">HEADER SKINS</p>
            <div class="color-tiles mx-0 px-4">
                <div class="tiles light"></div>
                <div class="tiles dark"></div>
            </div>
        </div>

        <!-- MAIN PANEL -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="main-wrapper">
                    <div class="page-wrapper">
                        <div class="content">
                            <div class="page-header">
                                <div class="page-title">
                                    <h4>Manage Product</h4>
                                    <h6>Add New Product</h6>
                                </div>
                            </div>
                            <div class="col-12 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body">
                                        <c:if test="${not empty error}">
                                            <div class="alert alert-danger" role="alert">
                                                    ${error}
                                            </div>
                                        </c:if>
                                        <form action="productManage" method="post">
                                            <input type="hidden" name="action" value="update">
                                            <div class="form-group">
                                                <label for="exampleInputName1">Product Name</label>
                                                <input name="name" type="text" class="form-control"
                                                       id="exampleInputName1" value="${product.name}"
                                                       placeholder="Enter Product Name...">
                                            </div>
                                            <div class="form-group">
                                                <label for="description-label">Short Description</label>
                                                <textarea
                                                        class="form-control"
                                                        id="description-label"
                                                        name="description"
                                                        rows="5"
                                                        placeholder="Enter product description..."
                                                >${product.description}</textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="information-label">Product Information</label>
                                                <textarea
                                                        class="form-control"
                                                        id="information-label"
                                                        rows="5"
                                                        name="information"
                                                        placeholder="Enter product information..."
                                                >${product.information}</textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="guideline-label">Product Guideline</label>
                                                <textarea
                                                        class="form-control"
                                                        id="guideline-label"
                                                        rows="5"
                                                        name="guideline"
                                                        placeholder="Enter product guideline..."
                                                >${product.guideline}</textarea>
                                            </div>

                                            <div class="form-group">
                                                <label for="subCategory">Sub Product Category</label>
                                                <select name="subProductCategoryId" class="form-control" id="subCategory">
                                                    <c:forEach var="sub" items="${subCategories}">
                                                        <option value="${sub.id}"
                                                                <c:if test="${product.subProductCategoryId == sub.id}">selected</c:if>>
                                                                ${sub.name}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary mr-2"> Update</button>
                                            <a href="${pageContext.request.contextPath}/productManage" class="btn btn-light">Cancel</a>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- plugins:js -->
<script src="${pageContext.request.contextPath}/adminassets/vendors/js/vendor.bundle.base.js"></script>
<!-- endinject -->
<!-- Plugin js for this page -->
<script src="${pageContext.request.contextPath}/adminassets/vendors/chart.js/Chart.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/vendors/flot/jquery.flot.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/vendors/flot/jquery.flot.resize.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/vendors/flot/jquery.flot.categories.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/vendors/flot/jquery.flot.fillbetween.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/vendors/flot/jquery.flot.stack.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/vendors/flot/jquery.flot.pie.js"></script>

<!-- Bootstrap JS (includes Popper) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- End plugin js for this page -->
<!-- inject:js -->
<script src="${pageContext.request.contextPath}/adminassets/js/off-canvas.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/js/hoverable-collapse.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/js/misc.js"></script>
<!-- endinject -->
<!-- Custom js for this page -->
<script src="${pageContext.request.contextPath}/adminassets/js/dashboard.js"></script>
</body>

</html>