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

    .dropdown-content a {
        text-decoration: none;
        color: #333;
        display: block;
        padding: 8px 0;
    }

    .dropdown-content a:hover {
        color: #fe980f;
    }

    #cart-modal {
        position: absolute;
        top: 100%;
        right: 0;
        width: 320px;
        max-height: 400px;
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        display: none;
        flex-direction: column;
        z-index: 1000;
    }

    #cart-modal.show {
        display: flex;
    }

    .cart-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 12px 16px;
        background: #f8f8f8;
        border-bottom: 1px solid #ddd;
        font-weight: bold;
    }

    body.modal-open {
        overflow: hidden;
    }

    #cart-close-btn {
        background: none;
        border: none;
        font-size: 18px;
        cursor: pointer;
    }

    .cart-body {
        overflow-y: auto;
        padding: 12px 16px;
        flex: 1;
        max-height: 300px;
    }

    /* Spinner trong cart modal body */
    .cart-spinner {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100px;
    }

    .cart-spinner::after {
        content: "";
        width: 30px;
        height: 30px;
        border: 4px solid #f3f3f3;
        border-top: 4px solid #ff9800; /* màu cam */
        border-radius: 50%;
        animation: spin 0.8s linear infinite;
    }

    @keyframes spin {
        to { transform: rotate(360deg); }
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
                                <li id="cart-icon-wrapper" style="position: relative;">
                                    <a href="cart" id="cart-icon" aria-label="Shopping Cart">
                                        <i class="fa fa-shopping-cart"></i> Cart
                                    </a>
                                    <div id="cart-loading"></div>
                                    <div id="cart-modal">
                                        <div class="cart-header">
                                            <span>My Cart</span>
                                            <button id="cart-close-btn">&times;</button>
                                        </div>
                                        <div class="cart-body" id="cart-items">
                                            <p>Loading...</p>
                                        </div>
                                    </div>
                                </li>
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
                                <li id="cart-icon-wrapper" style="position: relative;">
                                    <a href="cart" id="cart-icon" aria-label="Shopping Cart">
                                        <i class="fa fa-shopping-cart"></i> Cart
                                    </a>
                                    <div id="cart-loading"></div>
                                    <div id="cart-modal">
                                        <div class="cart-header">
                                            <span>My Cart</span>
                                            <button id="cart-close-btn">&times;</button>
                                        </div>
                                        <div class="cart-body" id="cart-items">
                                            <p>Loading...</p>
                                        </div>
                                    </div>
                                </li>

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
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const cartIcon = document.getElementById("cart-icon");
        const cartModal = document.getElementById("cart-modal");
        const closeBtn = document.getElementById("cart-close-btn");
        const cartItems = document.getElementById("cart-items");

        let hoverTimer;

        cartIcon.addEventListener("mouseenter", function () {
            hoverTimer = setTimeout(() => {
                cartModal.classList.add("show");
                document.body.classList.add("modal-open"); // ✅ Chặn scroll nền

                cartItems.innerHTML = `<div class="cart-spinner"></div>`;

                setTimeout(() => {
                    loadCartItems();
                }, 500);
            }, 500);
        });

        cartIcon.addEventListener("mouseleave", function () {
            clearTimeout(hoverTimer);
        });

        closeBtn.addEventListener("click", closeCartModal);
        cartModal.addEventListener("mouseleave", () => {
            setTimeout(closeCartModal, 500);
        });

        function closeCartModal() {
            cartModal.classList.remove("show");
            document.body.classList.remove("modal-open");
        }

        function loadCartItems() {
            fetch("cart-items")
                .then(res => res.text())
                .then(html => {
                    cartItems.innerHTML = html;
                });
        }
    });
</script>
