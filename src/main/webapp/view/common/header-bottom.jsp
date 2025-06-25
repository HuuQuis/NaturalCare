<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header-bottom">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="mainmenu pull-left">
                    <ul class="nav navbar-nav collapse navbar-collapse">
                        <li><a href="home" class="active" >Home</a></li>
                        <c:forEach items="${categories}" var="category">
                            <li class="dropdown">
                                <a href="${pageContext.request.contextPath}/products?category=${category.id}">
                                    ${category.name}<i class="fa fa-angle-down"></i>
                                </a>
                                <ul role="menu" class="sub-menu">
                                    <c:forEach items="${subCategories}" var="subcategory">
                                        <c:if test="${subcategory.productCategoryId == category.id}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/products?subcategory=${subcategory.id}">
                                                    ${subcategory.name}
                                                </a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                        <li><a href="${pageContext.request.contextPath}/view/contact-us.jsp" >Contact</a></li>
                        <li><a href="${pageContext.request.contextPath}/view/about-us.jsp">About Us</a></li>
                        <li class="dropdown">
                            <a href="${pageContext.request.contextPath}/blogs">Blog <i class="fa fa-angle-down"></i></a>
                            <ul role="menu" class="sub-menu">
                                <c:forEach items="${blogCategories}" var="blogCategory">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/blogs?blogCategory=${blogCategory.id}">
                                                ${blogCategory.name}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
