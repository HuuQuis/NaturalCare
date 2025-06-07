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
    <title>Product Management</title>

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
                        <a class="dropdown-item logout pb-0" href="logout"><img
                                src="${pageContext.request.contextPath}/adminassets/img/icons/log-out.svg" class="me-2"
                                alt="img">Logout</a>
                    </div>
                </div>
            </li>
        </ul>
    </div>

<%--    <jsp:include page="../common/sidebar-manager.jsp" />--%>
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Product List</h4>
                </div>
                <div class="page-btn" >
                    <a href="${pageContext.request.contextPath}/productManage?action=add" class="btn btn-added"><img
                            src="${pageContext.request.contextPath}/adminassets/img/icons/plus.svg" alt="img"
                            class="me-1">Add New Product</a>
                    <a href="${pageContext.request.contextPath}/productVariantManage?action=add" class="btn btn-added"><img
                            src="${pageContext.request.contextPath}/adminassets/img/icons/plus.svg" alt="img"
                            class="me-1">Add New Product Variant</a>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <div class="table-top">
                        <div class="search-set">
                            <div class="search-path">
                                <a class="btn btn-filter" id="filter_search">
                                    <img src="${pageContext.request.contextPath}/adminassets/img/icons/filter.svg"
                                         alt="img">
                                    <span><img src="${pageContext.request.contextPath}/adminassets/img/icons/closes.svg"
                                               alt="img"></span>
                                </a>
                            </div>
                            <div class="search-input">
                                <a class="btn btn-searchset"><img
                                        src="${pageContext.request.contextPath}/adminassets/img/icons/search-white.svg"
                                        alt="img"></a>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table datanew">
                            <thead>
                            <tr>
                                <th>No.</th>
                                <th>Product Name</th>
                                <th>Description</th>
                                <th>Information</th>
                                <th>Guideline</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="c" items="${products}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>
                                            ${c.name}
                                                <button class="btn btn-link toggle-variant-btn" type="button"
                                                        data-bs-toggle="collapse"
                                                        data-bs-target="#variations${c.id}"
                                                        aria-expanded="false"
                                                        aria-controls="variations${c.id}">
                                                    <i class="fas fa-chevron-down chevron-icon" id="chevron-${c.id}"></i>
                                                </button>


                                    </td>
                                    <td>${c.description.length() > 10 ? c.description.substring(0,10).concat('...') : c.description}</td>
                                    <td>${c.information.length() > 10 ? c.information.substring(0,10).concat('...') : c.information}</td>
                                    <td>${c.guideline.length() > 10 ? c.guideline.substring(0,10).concat('...') : c.guideline}</td>
                                    <td>
                                        <a class="me-3" href="productManage?action=edit&id=${c.id}">
                                            <img src="${pageContext.request.contextPath}/adminassets/img/icons/edit.svg"
                                                 alt="img">
                                        </a>
                                        <form action="productManage" method="post" style="display:inline;"
                                              onsubmit="return confirm('Are you sure to delete this product?');">
                                            <input type="hidden" name="action" value="delete"/>
                                            <input type="hidden" name="id" value="${c.id}"/>
                                            <button type="submit" class="btn btn-delete">
                                                <img src="${pageContext.request.contextPath}/adminassets/img/icons/delete.svg"
                                                     alt="img">
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                                <!-- Bảng phụ nằm bên ngoài bảng chính -->
                            </c:forEach>
                            </tbody>
                        </table>
                    </div> <!-- Đóng table-responsive -->
                    <div id="variantAccordion">
                        <c:forEach var="c" items="${products}">
                            <div class="collapse mt-2" id="variations${c.id}" data-bs-parent="#variantAccordion">
                                <div class="card card-body border border-1">
                                    <h6 class="mb-3">Variants of ${c.name}</h6>
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>Image</th>
                                            <th>Color</th>
                                            <th>Size</th>
                                            <th>Price</th>
                                            <th>Stock</th>
                                            <th>Sold</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="variation" items="${productVariantsMap[c.id]}">
                                            <tr>
                                                <td><img src="${pageContext.request.contextPath}/${variation.imageUrl}" alt="product image"
                                                         style="max-width: 100px; max-height: 100px;"></td>
<%--                                                <td>${variation.variationId}</td>--%>
                                                <td>${variation.color}</td>
                                                <td>${variation.size}</td>
                                                <td>${variation.price}</td>
                                                <td>${variation.qtyInStock}</td>
                                                <td>${variation.sold}</td>
                                                <td>
<%--                                                    <a class="me-3" href="productVariantManage?action=edit&id=${variation.variationId}">--%>
<%--                                                        <img src="${pageContext.request.contextPath}/adminassets/img/icons/edit.svg"--%>
<%--                                                             alt="img">--%>
<%--                                                    </a>--%>
                                                    <form action="productVariantManage" method="post" style="display:inline;"
                                                          onsubmit="return confirm('Are you sure to delete this product variant?');">
                                                        <input type="hidden" name="action" value="delete"/>
                                                        <input type="hidden" name="id" value="${c.id}"/>
                                                        <button type="submit" class="btn btn-delete">
                                                            <img src="${pageContext.request.contextPath}/adminassets/img/icons/delete.svg"
                                                                 alt="img">
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
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
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const allIcons = document.querySelectorAll('.chevron-icon');

        document.querySelectorAll('.toggle-variant-btn').forEach(btn => {
            const targetId = btn.getAttribute('data-bs-target');
            const collapseEl = document.querySelector(targetId);

            if (collapseEl) {
                collapseEl.addEventListener('show.bs.collapse', () => {
                    // Reset all arrows to down
                    allIcons.forEach(icon => {
                        icon.classList.remove('fa-chevron-up');
                        icon.classList.add('fa-chevron-down');
                    });

                    // change the clicked button's icon to up
                    const icon = btn.querySelector('.chevron-icon');
                    if (icon) {
                        icon.classList.remove('fa-chevron-down');
                        icon.classList.add('fa-chevron-up');
                    }
                });

                collapseEl.addEventListener('hide.bs.collapse', () => {
                    const icon = btn.querySelector('.chevron-icon');
                    if (icon) {
                        icon.classList.remove('fa-chevron-up');
                        icon.classList.add('fa-chevron-down');
                    }
                });
            }
        });
    });
</script>

</body>

</html>
