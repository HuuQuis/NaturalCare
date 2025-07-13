<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String view = (String) request.getAttribute("view");
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>Manager | Nature Care</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/vendors/mdi/css/materialdesignicons.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/vendors/flag-icon-css/css/flag-icon.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/vendors/css/vendor.bundle.base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/vendors/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/style.css" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/adminassets/images/favicon.png" />
</head>

<body>
<div class="container-scroller">

    <!-- SIDEBAR -->
    <jsp:include page="../common/sidebar-manager.jsp" />

    <!-- BODY WRAPPER -->
    <div class="container-fluid page-body-wrapper">

        <!-- HEADER -->
        <jsp:include page="../common/header-manager.jsp" />

        <!-- SETTINGS PANEL (có thể đặt trong content-wrapper nếu cần) -->
        <div id="theme-settings" class="settings-panel">
            <i class="settings-close mdi mdi-close"></i>
            <p class="settings-heading">SIDEBAR SKINS</p>
            <div class="sidebar-bg-options selected" id="sidebar-default-theme">
                <div class="img-ss rounded-circle bg-light border mr-3"></div> Default
            </div>
            <div class="sidebar-bg-options" id="sidebar-dark-theme">
                <div class="img-ss rounded-circle bg-dark border mr-3"></div> Dark
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
                <%-- Nội dung động ở đây, ví dụ: --%>
                <% if ("subcategory".equals(view)) { %>
                <jsp:include page="../category/list.jsp" />
                <% } %>
                    <% if ("productCategory".equals(view)) { %>
                    <jsp:include page="../category/category-list.jsp" />
                    <% } %>
                    
                    <% if ("expert-skill".equals(view)) { %>
                        <jsp:include page="../manage/expert-management.jsp" />
                    <% } %>
                    
                    <% if ("expert-insert".equals(view)) { %>
                        <jsp:include page="../manage/expert-insert.jsp" />
                    <% } %>
                    
                    <% if ("expert-detail".equals(view)) { %>
                        <jsp:include page="../manage/expert-detail.jsp" />
                    <% } %>

                    <% if ("product".equals(view)) { %>
                <jsp:include page="../manage/product-manage.jsp" />
                <% } %>
                
                    <% if ("user-management".equals(view)) { %>
                    <jsp:include page="../manage/user-manage.jsp" />
                    <% } %>
                    
                    <% if ("admin-manager-management".equals(view)) { %>
                    <jsp:include page="../manage/manager-manage.jsp" />
                    <% } %>
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
<!-- End custom js for this page -->
</body>

</html>