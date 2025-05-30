<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 5/23/2025
  Time: 11:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header-middle">
    <div class="container">
        <div class="row">
            <div class="col-sm-4">
                <div class="logo pull-left">
                    <a href="${pageContext.request.contextPath}/home">
                        <img src="https://naturalcare.vercel.app/naturalcare/NLC-Logo.png" alt="" style="max-height: 70px"/>
                    </a>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="search-box center-block">
                    <input oninput="searchByName(this)" value="${txt}" id="searchInput" type="text" name="txt" autocomplete="off" placeholder="Search products..."/>
                    <div id="searchList" class="search-list"></div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="shop-menu pull-right">
                    <ul class="nav navbar-nav">
<%--                        if dont have username then show login--%>
                        <c:choose>
                            <c:when test="${not empty username}">
                                <li><a href=""><i class="fa fa-user"></i> ${username}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="login"><i class="fa fa-lock"></i> Login</a></li>
                            </c:otherwise>
                        </c:choose>
                        <li><a href=""><i class="fa fa-shopping-cart"></i> Cart</a></li>
<%--                        <li><a href=""><i class="fa fa-lock"></i> Login</a></li>--%>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>