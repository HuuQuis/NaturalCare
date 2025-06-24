<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/address-modals.css">
</head>
<style>
    .profile-dropdown {
        position: relative;
        display: inline-block;
    }

    .dropdown-content {
        display: none;
        position: absolute;
        background-color: #f9f9f9;
        min-width: 200px;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
        z-index: 1000;
        padding: 12px;
        border-radius: 4px;
        right: 0;
    }

    .profile-dropdown:hover .dropdown-content {
        display: block;
    }

    .user-info {
        padding: 10px;
        border-bottom: 1px solid #ddd;
    }

    .address-list {
        padding: 10px;
    }

    .address-item {
        margin: 5px 0;
        font-size: 14px;
    }

    .dropdown-content a {
        text-decoration: none;
        color: #333;
        display: block;
        padding: 8px 0;
    }

    .dropdown-content a:hover {
        color: #fe980f;
    }
</style>

<div class="header-middle">
    <div class="container">
        <div class="row">
            <div class="col-sm-4">
                <div class="logo pull-left">
                    <a href=${pageContext.request.contextPath}/home><img
                            src="https://naturalcare.vercel.app/naturalcare/NLC-Logo.png" alt=""
                            style="max-height: 70px"/></a>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="search-box center-block">
                    <input oninput="searchByName(this)" value="${txt}" id="searchInput" type="text" name="txt"
                           autocomplete="off" placeholder="Search products..."/>
                    <div id="searchList" class="search-list"></div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="shop-menu pull-right">
                    <ul class="nav navbar-nav">
                        <c:choose>
                            <c:when test="${not empty user}">
                                <li><a href="cart" aria-label="Shopping Cart"><i class="fa fa-shopping-cart"></i>
                                    Cart</a></li>
                                <li class="profile-dropdown">
                                    <a href="" aria-label="User Profile"><i class="fa fa-user"></i>${user.username}</a>
                                    <div class="dropdown-content">
                                        <div class="user-info">
                                            <a href="#" aria-label="User Profile"><i class="fa fa-user-circle"></i> My Profile</a>
                                        </div>
                                        <div class="address-list">
                                            <a href="#" onclick="openAddressModal()" aria-label="Address List"><i class="fa fa-map-marker"></i> Address List</a>
                                        </div>
                                    </div>
                                </li>
                                <li><a href="logout" aria-label="Logout"><i class="fa fa-lock"></i> Logout</a></li>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${not fn:contains(pageContext.request.requestURI, 'login')}">
                                    <li><a href="login" aria-label="Login"><i class="fa fa-lock"></i> Login</a></li>
                                </c:if>
                                <li><a href="cart" aria-label="Shopping Cart"><i class="fa fa-shopping-cart"></i>
                                    Cart</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/view/address/address-list.jsp" />
<jsp:include page="/view/address/address-add.jsp" />
<script src="${pageContext.request.contextPath}/js/address-modals.js" defer></script>
