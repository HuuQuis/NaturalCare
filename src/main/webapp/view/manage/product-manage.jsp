<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<div class="main-wrapper">
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Product List</h4>
                </div>
                <div class="page-btn" >
                    <a href="${pageContext.request.contextPath}/productManage?action=add" class="btn btn-added"><img
                            src="${pageContext.request.contextPath}/adminassets/img/icons/plus.svg" alt="img"
                            class="me-1">Add New Product</a>

                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <div class="table-top">
                        <div class="search-set">
                            <div class="search-path">
                                <a class="btn btn-filter" id="filter_search">
                                    <img src="${pageContext.request.contextPath}/adminassets/img/icons/filter.svg"
                                         alt="img">
                                    <span><img src="${pageContext.request.contextPath}/adminassets/img/icons/closes.svg"
                                               alt="img"></span>
                                </a>
                            </div>
                            <div class="search-input">
                                <a class="btn btn-searchset"><img
                                        src="${pageContext.request.contextPath}/adminassets/img/icons/search-white.svg"
                                        alt="img"></a>
                            </div>
                        </div>
                    </div>

                        <table class="table">
                            <thead>
                            <tr>
                                <th>No.</th>
                                <th>Product Name</th>
                                <th>Description</th>
                                <th>Information</th>
                                <th>Guideline</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="c" items="${products}" varStatus="loop">
                                <tr>
                                    <td>${(page - 1) * pageSize + loop.index + 1}</td>
                                    <td>
                                            ${c.name}
                                                <button class="btn btn-link toggle-variant-btn" type="button"
                                                        data-bs-toggle="collapse"
                                                        data-bs-target="#variations${c.id}"
                                                        aria-expanded="false"
                                                        aria-controls="variations${c.id}">
                                                    <i class="fas fa-chevron-down chevron-icon" id="chevron-${c.id}"></i>
                                                </button>


                                    </td>
                                    <td>${c.description.length() > 10 ? c.description.substring(0,10).concat('...') : c.description}</td>
                                    <td>${c.information.length() > 10 ? c.information.substring(0,10).concat('...') : c.information}</td>
                                    <td>${c.guideline.length() > 10 ? c.guideline.substring(0,10).concat('...') : c.guideline}</td>
                                    <td>
                                        <a class="me-3" href="productManage?action=edit&id=${c.id}">
                                            <img src="${pageContext.request.contextPath}/adminassets/img/icons/edit.svg"
                                                 alt="img">
                                        </a>
                                        <form action="productManage" method="post" style="display:inline;"
                                              onsubmit="return confirm('Are you sure to delete this product?');">
                                            <input type="hidden" name="action" value="delete"/>
                                            <input type="hidden" name="id" value="${c.id}"/>
                                            <button type="submit" class="btn btn-delete">
                                                <img src="${pageContext.request.contextPath}/adminassets/img/icons/delete.svg"
                                                     alt="img">
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                                <!-- Bảng phụ nằm bên ngoài bảng chính -->
                            </c:forEach>
                            </tbody>
                        </table>
                    <div id="variantAccordion">
                        <c:forEach var="c" items="${products}">
                            <div class="collapse mt-2" id="variations${c.id}" data-bs-parent="#variantAccordion">
                                <div class="card card-body border border-1">
                                    <h6 class="mb-3">Variants of ${c.name}</h6>
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>No.</th>
                                            <th>Image</th>
                                            <th>Color</th>
                                            <th>Size</th>
                                            <th>Price</th>
                                            <th>Stock</th>
                                            <th>Sold</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="variation" items="${productVariantsMap[c.id]}" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td>
                                                <td><img src="${pageContext.request.contextPath}/${variation.imageUrl}" alt="product image"
                                                         style="max-width: 100px; max-height: 100px;"></td>
                                                <td>${variation.color}</td>
                                                <td>${variation.size}</td>
                                                <td>${variation.price}</td>
                                                <td>${variation.qtyInStock}</td>
                                                <td>${variation.sold}</td>
                                                <td>
<%--                                                    <a class="me-3" href="productVariantManage?action=edit&id=${variation.variationId}">--%>
<%--                                                        <img src="${pageContext.request.contextPath}/adminassets/img/icons/edit.svg"--%>
<%--                                                             alt="img">--%>
<%--                                                    </a>--%>
                                                    <form action="productVariantManage" method="post" style="display:inline;"
                                                          onsubmit="return confirm('Are you sure to delete this product variant?');">
                                                        <input type="hidden" name="action" value="delete"/>
                                                        <input type="hidden" name="variantId" value="${variation.variationId}"/>
                                                        <button type="submit" class="btn btn-delete">
                                                            <img src="${pageContext.request.contextPath}/adminassets/img/icons/delete.svg"
                                                                 alt="img">
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <div class="page-header">
                                            <div class="page-btn" >
                                                <a href="${pageContext.request.contextPath}/productVariantManage?action=add&productId=${c.id}" class="btn btn-added">
                                                    <img src="${pageContext.request.contextPath}/adminassets/img/icons/plus.svg" alt="img" class="me-1">
                                                    Add New Product Variant
                                                </a>
                                            </div>
                                        </div>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
<%--                    pagination--%>
                    <div class="d-flex justify-content-end mt-3">
                        <ul class="pagination">
                            <c:forEach var="i" begin="1" end="${totalPage}">
                                <li class="page-item ${i == page ? 'active' : ''}">
                                    <a class="page-link" href="productManage?page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/adminassets/js/jquery-3.6.0.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/js/feather.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/js/jquery.slimscroll.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/js/bootstrap.bundle.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/plugins/select2/js/select2.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/plugins/sweetalert/sweetalerts.min.js"></script>

<script src="${pageContext.request.contextPath}/adminassets/js/script.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const allIcons = document.querySelectorAll('.chevron-icon');

        document.querySelectorAll('.toggle-variant-btn').forEach(btn => {
            const targetId = btn.getAttribute('data-bs-target');
            const collapseEl = document.querySelector(targetId);

            if (collapseEl) {
                collapseEl.addEventListener('show.bs.collapse', () => {
                    // Reset all arrows to down
                    allIcons.forEach(icon => {
                        icon.classList.remove('fa-chevron-up');
                        icon.classList.add('fa-chevron-down');
                    });

                    // change the clicked button's icon to up
                    const icon = btn.querySelector('.chevron-icon');
                    if (icon) {
                        icon.classList.remove('fa-chevron-down');
                        icon.classList.add('fa-chevron-up');
                    }
                });

                collapseEl.addEventListener('hide.bs.collapse', () => {
                    const icon = btn.querySelector('.chevron-icon');
                    if (icon) {
                        icon.classList.remove('fa-chevron-up');
                        icon.classList.add('fa-chevron-down');
                    }
                });
            }
        });
    });
</script>

</html>
