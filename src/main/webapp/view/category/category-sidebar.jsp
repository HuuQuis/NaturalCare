<%--
  Created by IntelliJ IDEA.
  User: phung
  Date: 6/2/2025
  Time: 11:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="left-sidebar" style="border-right: 1px solid #eee; padding-right: 15px;">
    <h2 style="font-size: 20px; border-bottom: 2px solid #a8d08d; padding-bottom: 10px;">DANH MỤC SẢN PHẨM</h2>

    <div class="panel-group category-products" id="category-accordion">
        <c:forEach var="cat" items="${categories}">
            <div class="panel panel-default">
                <div class="panel-heading" style="background-color: #f5f5f5;">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" href="#cat${cat.id}" style="display: block; font-weight: bold; text-decoration: none; color: #333;">
                                ${cat.name}
                        </a>
                    </h4>
                </div>
                <div id="cat${cat.id}" class="panel-collapse collapse">
                    <div class="panel-body" style="padding-left: 20px;">
                        <ul class="list-unstyled">
                            <c:forEach var="sub" items="${subCategories}">
                                <c:if test="${sub.productCategoryId == cat.id}">
                                    <li><a href="products?subcategory=${sub.id}" style="text-decoration: none; color: #555;">- ${sub.name}</a></li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>



