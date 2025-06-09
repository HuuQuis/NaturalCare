<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
        <div id="sidebar-menu" class="sidebar-menu">
            <ul>
                <li class="submenu">
                    <a href="javascript:void(0);"><img src="${pageContext.request.contextPath}/adminassets/img/icons/product.svg" alt="img"><span>
                                        Edit</span> <span class="menu-arrow"></span></a>
                    <ul>
                        <li><a href="">DashBoard</a></li>
                        <li>
                            <a href="${pageContext.request.contextPath}/productManage"
                               class="${view eq 'product' ? 'active' : ''}">Manage Product</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/category"
                               class="${view eq 'category' ? 'active' : ''}">Manage Category</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/skill"
                               class="${view eq 'skill' ? 'active' : ''}">Skill list</a>
                        </li>
                        <li><a href="">Expert List</a></li>
                        <li><a href="">Staff List</a></li>
                        <li><a href="">Shipper List</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
