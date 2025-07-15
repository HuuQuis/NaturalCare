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

    <c:set var="isBlogPage" value="${fn:contains(pageContext.request.requestURI, '/blog')}" />
    <c:set var="isBlogCatPage" value="${fn:contains(pageContext.request.requestURI, '/blog-category')}" />
    <c:set var="isBlogMenuActive" value="${isBlogPage or isBlogCatPage}" />

    <li class="nav-item">
      <a class="nav-link ${isBlogMenuActive ? '' : 'collapsed'}" data-bs-toggle="collapse" href="#blogMenu"
         aria-expanded="${isBlogMenuActive}" aria-controls="blogMenu">
        <i class="mdi mdi-note-text menu-icon"></i>
        <span class="menu-title">Blog Management</span>
        <i class="menu-arrow"></i>
      </a>
      <div class="collapse ${isBlogMenuActive ? 'show' : ''}" id="blogMenu">
        <ul class="nav flex-column sub-menu">
          <li class="nav-item">
            <a class="nav-link ${isBlogPage ? 'active' : ''}" href="${pageContext.request.contextPath}/blog-manage">
              Blog List
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link ${isBlogCatPage ? 'active' : ''}" href="${pageContext.request.contextPath}/blog-category">
              Blog Category
            </a>
          </li>
        </ul>
      </div>
    </li>


  </ul>
</nav>
