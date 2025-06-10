<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="sidebar sidebar-offcanvas" id="sidebar">
    <div class="text-center sidebar-brand-wrapper d-flex align-items-center">
        <a class="sidebar-brand brand-logo" href="${pageContext.request.contextPath}/admin">
            <img src="${pageContext.request.contextPath}/adminassets/images/logo.svg" alt="logo"/>
        </a>
        <a class="sidebar-brand brand-logo-mini pl-4 pt-3" href="${pageContext.request.contextPath}/admin">
            <img src="${pageContext.request.contextPath}/adminassets/images/logo-mini.svg" alt="logo"/>
        </a>
    </div>
    <ul class="nav">
        <li class="nav-item nav-profile">
            <a href="#" class="nav-link">
                <div class="nav-profile-image">
                    <img src="${pageContext.request.contextPath}/adminassets/images/faces/face1.jpg" alt="profile"/>
                    <span class="login-status online"></span>
                </div>
                <div class="nav-profile-text d-flex flex-column pr-3">
                    <span class="font-weight-medium mb-2">Henry Klein</span>
                </div>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/admin">
                <i class="mdi mdi-home menu-icon"></i>
                <span class="menu-title">Dashboard</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/productManage">
                <i class="mdi mdi-format-list-bulleted menu-icon"></i>
                <span class="menu-title">Product Management</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/category">
                <i class="mdi mdi-tag menu-icon"></i>
                <span class="menu-title">Category Management</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/skill">
                <i class="mdi mdi-school menu-icon"></i>
                <span class="menu-title">Skill Management</span>
            </a>
        </li>
    </ul>
</nav>
