<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="sidebar sidebar-offcanvas" id="sidebar">
    <div class="text-center sidebar-brand-wrapper d-flex align-items-center">
        <a class="sidebar-brand brand-logo" href="">
            <img src="${pageContext.request.contextPath}/adminassets/images/logo.svg" alt="logo"/>
        </a>
        <a class="sidebar-brand brand-logo-mini pl-4 pt-3" href="">
            <img src="${pageContext.request.contextPath}/adminassets/images/logo-mini.svg" alt="logo"/>
        </a>
    </div>
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="">
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

        <%-- Check active state for category menu --%>
        <c:set var="isCategoryPage" value="${fn:contains(pageContext.request.requestURI, '/productCategory')}" />
        <c:set var="isSubPage" value="${fn:contains(pageContext.request.requestURI, '/subcategory')}" />
        <c:set var="isCatMenuActive" value="${isCategoryPage or isSubPage}" />

        <li class="nav-item">
            <a class="nav-link ${isCatMenuActive ? '' : 'collapsed'}" data-bs-toggle="collapse" href="#categoryMenu"
               aria-expanded="${isCatMenuActive}" aria-controls="categoryMenu">
                <i class="mdi mdi-tag-multiple menu-icon"></i>
                <span class="menu-title">Category Management</span>
                <i class="menu-arrow"></i>
            </a>
            <div class="collapse ${isCatMenuActive ? 'show' : ''}" id="categoryMenu">
                <ul class="nav flex-column sub-menu">
                    <li class="nav-item">
                        <a class="nav-link ${isSubPage ? 'active' : ''}" href="${pageContext.request.contextPath}/subcategory">
                            Subcategory List
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${isCategoryPage ? 'active' : ''}" href="${pageContext.request.contextPath}/productCategory">
                            Category List
                        </a>
                    </li>
                </ul>
            </div>
        </li>

        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/shipper">
                <i class="mdi mdi-format-list-bulleted menu-icon"></i>
                <span class="menu-title">Shipper Management</span>
            </a>
        </li>

        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/skill">
                <i class="mdi mdi-school menu-icon"></i>
                <span class="menu-title">Skill Management</span>
            </a>
        </li>
        
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/expertListManage">
                <i class="mdi mdi-format-list-bulleted menu-icon"></i>
                <span class="menu-title">Expert Management</span>
            </a>
        </li>

        <c:if test="${sessionScope.user.role == 2 }">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/staff/customerList">
                    <i class="mdi mdi-account-multiple menu-icon"></i>
                    <span class="menu-title">Customer Management</span>
                </a>
            </li>
        </c:if>

        <c:if test="${sessionScope.user.role == 3}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/managerManage">
                    <i class="mdi mdi-account-supervisor menu-icon"></i>
                    <span class="menu-title">Manager Management</span>
                </a>
            </li>
        </c:if>
    </ul>
</nav>
