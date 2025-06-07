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


    <div class="sidebar" id="sidebar">
        <div class="sidebar-inner slimscroll">
            <div id="sidebar-menu" class="sidebar-menu">
                <ul>
                    <li class="submenu">

                        <a href="javascript:void(0);"><img
                                src="${pageContext.request.contextPath}/adminassets/img/icons/product.svg"
                                alt="img"><span>
                                        Edit</span> <span class="menu-arrow"></span></a>
                        <ul>
                            <li><a href="">DashBoard</a></li>
                            <li><a href="${pageContext.request.contextPath}/productManage" class="active">Product
                                List</a></li>
                            <li><a href="${pageContext.request.contextPath}/category">Category List</a></li>
                            <li><a href="">Expert List</a></li>
                            <li><a href="">Staff List</a></li>
                            <li><a href="">Shipper List</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Product List</h4>
                </div>
                <div class="page-btn">
                    <a href="${pageContext.request.contextPath}/productManage?action=add" class="btn btn-added"><img
                            src="${pageContext.request.contextPath}/adminassets/img/icons/plus.svg" alt="img"
                            class="me-1">Add New Product</a>
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
                                <td>${c.name}</td>
                                <td>${c.description.length() > 10 ? c.description.substring(0,10).concat('...') : c.description}</td>
                                <td>${c.information.length() > 10 ? c.information.substring(0,10).concat('...') : c.information}</td>
                                <td>${c.guideline.length() > 10 ? c.guideline.substring(0,10).concat('...') : c.guideline}</td>
                                <td>
                                    <a class="me-3" href="productManage?action=edit&id=${c.id}">
                                        <img src="${pageContext.request.contextPath}/adminassets/img/icons/edit.svg" alt="img">
                                    </a>
                                    <form action="productManage" method="post" style="display:inline;"
                                          onsubmit="return confirm('Are you sure to delete this product?');">
                                        <input type="hidden" name="action" value="delete"/>
                                        <input type="hidden" name="id" value="${c.id}"/>
                                        <button type="submit" class="btn btn-delete">
                                            <img src="${pageContext.request.contextPath}/adminassets/img/icons/delete.svg" alt="img">
                                        </button>
                                    </form>
                                </td>
                            </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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
</body>

</html>