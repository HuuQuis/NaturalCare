<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>Manager | Nature Care</title>
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
                                    <h4>Manage Product Variant</h4>
                                    <h6>Add New Product Variant</h6>
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
                                        <form action="${pageContext.request.contextPath}/productVariantManage?action=add&productId=${productId}"
                                              method="post"
                                              enctype="multipart/form-data">
                                            <input type="hidden" name="action" value="add">
                                            <div class="form-group">
                                                <div class="form-control-plaintext">
                                                    <c:forEach var="pro" items="${products}">
                                                        <c:if test="${pro.id == productId}">
                                                            <label>Product Line: ${pro.name}</label>
                                                            <input type="hidden" name="ProductId" value="${pro.id}"/>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>File upload</label>
                                                <input type="file" name="image" class="file-upload-default"
                                                       accept="image/*" ${empty previousImageUrl ? 'required' : ''}
                                                       style="display: none;"/>

                                                <div class="input-group col-xs-12">
                                                    <input type="text" class="form-control file-upload-info" disabled
                                                           placeholder="Upload Image"
                                                           value="${not empty previousImageUrl ? previousImageUrl : ''}"/>
                                                    <span class="input-group-append">
                                                        <button class="file-upload-browse btn btn-primary" type="button">
                                                            ${not empty previousImageUrl ? 'Change Image' : 'Upload'}
                                                        </button>
                                                    </span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label>Product Variant Color</label>
                                                <input type="text" class="form-control" name="color"
                                                       value="${tempProductVariation.color}" placeholder="Enter Product Variant Color">
                                            </div><%-- Product Variant Color --%>
                                            <div class="form-group">
                                                <label>Product Variant Size</label>
                                                <input type="text" class="form-control" name="size"
                                                       value="${tempProductVariation.size}" placeholder="Enter size in ml" />
                                            </div>

                                            <div class="form-group">
                                                <label>Product Variant Price</label>
                                                <input type="number" class="form-control" name="price"
                                                       value="${tempProductVariation.price}"
                                                       placeholder="Enter Product Variant Price">
                                            </div>

                                            <div class="form-group">
                                                <label>Product Variant Quantity</label>
                                                <input type="number" class="form-control" name="quantity"
                                                       value="${tempProductVariation.qtyInStock}"
                                                       placeholder="Enter Product Variant Quantity">
                                            </div>
                                            <button type="submit" class="btn btn-primary mr-2"> Submit</button>
                                            <a href="${pageContext.request.contextPath}/productManage"
                                               class="btn btn-light">Cancel</a>
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
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const fileInput = document.querySelector('.file-upload-default');
        const browseButton = document.querySelector('.file-upload-browse');
        const fileInfoInput = document.querySelector('.file-upload-info');

        browseButton.addEventListener('click', function () {
            fileInput.click();
        });

        fileInput.addEventListener('change', function () {
            const fileName = fileInput.files.length > 0 ? fileInput.files[0].name : '';
            fileInfoInput.value = fileName;
        });
    });
</script>
</body>

</html>