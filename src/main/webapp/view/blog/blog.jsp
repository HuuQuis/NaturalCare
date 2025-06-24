<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <link href="${pageContext.request.contextPath}/css/blog.css" rel="stylesheet">
</head>

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

<div class="container mt-3">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb bg-white p-2 rounded">
            <li class="breadcrumb-item">
                <a href="${pageContext.request.contextPath}/">
                    <i class="fa fa-home"></i> Homepage
                </a>
            </li>
            <li class="breadcrumb-item">
                <a href="${pageContext.request.contextPath}/blogs">Blogs</a>
            </li>
            <c:if test="${not empty selectedCategory}">
                <li class="breadcrumb-item active" aria-current="page">
                        ${selectedCategory.name}
                </li>
            </c:if>
        </ol>
    </nav>
</div>

<div class="container mt-5">
    <c:choose>
        <c:when test="${empty blogList}">
            <p class="text-center text-muted">Hiện chưa có bài viết nào.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="blog" items="${blogList}">
                <div class="blog-card">
                    <img src="${pageContext.request.contextPath}/${blog.imageUrl}" alt="${blog.blogTitle}" class="blog-card-img" />
                    <div class="view-count">
                        <i class="fa fa-eye"></i> 227 lượt xem
                    </div>

                    <h5>${blog.blogTitle}</h5>

                    <p>${fn:substring(blog.blogDescription, 0, 180)}...</p>

                    <a href="#" class="btn btn-view mt-2">XEM THÊM</a>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <!-- Pagination -->
    <nav class="mt-4">
        <ul class="pagination">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/blogs?page=${currentPage - 1}">Previous</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/blogs?page=${i}">${i}</a>
                </li>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/blogs?page=${currentPage + 1}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>


<jsp:include page="/view/common/footer.jsp" />
