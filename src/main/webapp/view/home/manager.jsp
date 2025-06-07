<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/plugins/fontawesome/css/all.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminStyle.css">
</head>

<body>
<div id="global-loader">
    <div class="whirly-loader"> </div>
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
                            <span class="user-img"><img src="${pageContext.request.contextPath}/adminassets/img/profiles/avator1.jpg" alt="">
                                <span class="status online"></span></span>
                </a>
                <div class="dropdown-menu menu-drop-user">
                    <div class="profilename">
                        <div class="profileset">
                                    <span class="user-img"><img src="${pageContext.request.contextPath}/adminassets/img/profiles/avator1.jpg" alt="">
                                        <span class="status online"></span></span>
                            <div class="profilesets">
                                <h5>Manager</h5>
                            </div>
                        </div>
                        <hr class="m-0">
                        <a class="dropdown-item logout pb-0" href=""><img
                                src="${pageContext.request.contextPath}/adminassets/img/icons/log-out.svg" class="me-2" alt="img">Logout</a>
                    </div>
                </div>
            </li>
        </ul>
    </div>

    // Sidebar
    <jsp:include page="../common/sidebar-manager.jsp" />
</div>

<%
    String view = (String) request.getAttribute("view");
%>

<div class="main-content">
    <% if ("category".equals(view)) { %>
    <jsp:include page="../category/list.jsp" />
    <% } %>
</div>


<script>
    function mess(id){
        confirmation = confirm('Are you sure to delete?');
        if(confirmation===true){
            window.location.href = 'deleteNews?new_id=' + id;
        }
    }
</script>

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