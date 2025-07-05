<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    a.category-active {
        color: #5cb85c !important;
    }
    a.sub-active {
        font-weight: bold;
        color: #449d44 !important;
    }
</style>

<div class="left-sidebar" style="border-right: 1px solid #eee; padding-right: 15px;">
    <h2 style="font-size: 20px; border-bottom: 2px solid #a8d08d; padding-bottom: 10px;">CATEGORY</h2>

    <div class="panel-group category-products" id="category-accordion">
        <c:forEach var="cat" items="${categories}">
            <div class="panel panel-default">
                <div class="panel-heading" style="background-color: #f5f5f5;">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" href="#cat${cat.id}"
                           class="<c:if test='${selectedCategoryId == cat.id}'>category-active</c:if>"
                           style="display: block; font-weight: bold; text-decoration: none;">
                                ${cat.name}
                        </a>
                    </h4>
                </div>

                <div id="cat${cat.id}" class="panel-collapse collapse
                     <c:if test='${selectedCategoryId == cat.id}'> in</c:if>">
                    <div class="panel-body" style="padding-left: 20px;">
                        <ul class="list-unstyled">
                            <c:forEach var="sub" items="${subCategories}">
                                <c:if test="${sub.productCategoryId == cat.id}">
                                    <li>
                                        <a href="products?subcategory=${sub.id}"
                                           class="<c:if test='${selectedSubCategoryId == sub.id}'>sub-active</c:if>"
                                           style="text-decoration: none;">
                                            - ${sub.name}
                                        </a>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
