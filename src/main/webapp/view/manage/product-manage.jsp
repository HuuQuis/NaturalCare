<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<style>
    tr.collapse:not(.show) {
        display: none;
    }

    tr.collapse.show {
        display: table-row;
        animation: slideDown 0.25s ease;
    }

    @keyframes slideDown {
        from {
            opacity: 0;
            transform: scaleY(0.95);
        }
        to {
            opacity: 1;
            transform: scaleY(1);
        }
    }
</style>

<div class="main-wrapper">
    <div class="page-wrapper">
        <div class="content">
            <div class="page-header">
                <div class="page-title">
                    <h4>Product Line List</h4>
                </div>
                <div class="page-btn" >
                    <button type="button" class="btn btn-info d-inline-flex align-items-center" onclick="location.href='${pageContext.request.contextPath}/productManage?action=add';" style="gap: 6px;">
                        <i class="mdi mdi-plus-one" style="font-size: 19px; color: white;"></i>
                        Add New Product
                    </button>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <c:if test="${not empty notification}">
                        <div class="alert alert-warning" role="alert">
                            ${notification}
                        </div>
                    </c:if>
                    <div class="table-top">
                        <div class="search-set d-flex align-items-center flex-wrap" style="gap: 15px;">
                            <form id="filterForm" method="get" action="productManage"
                                  class="d-flex align-items-center flex-wrap" style="gap: 10px;">
                                <input type="text" name="search" id="productSearchInput" class="form-control"
                                       placeholder="Search by product name..." value="${searchKeyword}"
                                       style="min-width: 250px;"/>
                                <select name="categoryId" id="categorySelect" class="form-select"
                                        style="width: fit-content; height: 35px">
                                    <option value="">All Categories</option>
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat.id}" ${cat.id == selectedCategoryId ? 'selected' : ''}>${cat.name}</option>
                                    </c:forEach>
                                </select>
                                <select name="subCategoryId" id="subCategorySelect" class="form-select"
                                        style="width: fit-content; height: 35px">
                                    <option value="">All Subcategories</option>
                                    <c:forEach var="sub" items="${subCategories}">
                                        <option value="${sub.id}"
                                                data-category="${sub.productCategoryId}" ${sub.id == selectedSubCategoryId ? 'selected' : ''}>${sub.name}</option>
                                    </c:forEach>
                                </select>
                                <button type="submit" class="btn btn-primary" id="filterBtn">Filter</button>
                                <button type="button" class="btn btn-secondary" onclick="window.location.href='${pageContext.request.contextPath}/productManage'">
                                    Reset
                                </button>

                            </form>
                        </div>
                    </div>
                        <table class="table">
                            <thead>
                            <tr>
                                <th>No.</th>
                                <th>Product Name</th>
                                <th>Description</th>
                                <th>Information</th>
                                <th>Created At</th>
                                <th>Updated At</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody id="variantAccordion">
                            <c:forEach var="c" items="${products}" varStatus="loop">
                                <tr data-name="${c.name.toLowerCase()}">
                                <!-- Row chính -->
                                    <td>${(page - 1) * pageSize + loop.index + 1}</td>
                                    <td style="word-break: break-word; white-space: normal; max-width: 250px;">
                                            ${c.name}
                                        <button class="btn btn-link toggle-variant-btn" type="button"
                                                data-target="#variations${c.id}">
                                            <i class="mdi mdi-arrow-down-drop-circle-outline chevron-icon"
                                               id="chevron-${c.id}"
                                               style="cursor: pointer;font-size: 20px;"></i>
                                        </button>
                                    </td>
                                    <td data-bs-toggle="tooltip" title="${c.description}">
                                            ${c.description.length() > 20 ? c.description.substring(0,20).concat('...') : c.description}
                                    </td>
                                    <td data-bs-toggle="tooltip" title="${c.information}">
                                            ${c.information.length() > 20 ? c.information.substring(0,20).concat('...') : c.information}
                                    </td>
                                    <td>${c.createdAtFormatted}</td>
                                    <td>${c.updatedAtFormatted}</td>
                                    <td>
                                        <a class="me-3" href="productManage?action=edit&id=${c.id}">
                                            <i class="mdi mdi-table-edit"
                                               data-bs-toggle="tooltip"
                                               data-bs-placement="top"
                                               title="Edit product"
                                               style="display: inline-block;
                                                  font-size: 20px;
                                                  width: 40px;
                                                  text-align: center;
                                                  color: #3f50f6;"></i>
                                        </a>
                                        <form action="productManage" method="post" style="display:inline;"
                                              onsubmit="return confirm('${c.active ? 'Are you sure to deactivate this product?' : 'Are you sure to restore this product?'}');">
                                            <input type="hidden" name="action" value="delete"/>
                                            <input type="hidden" name="id" value="${c.id}"/>

                                            <button type="submit" id="submit-product-${c.id}" style="display:none;"
                                                <c:if test="${productVariantsMap[c.id].size() > 0}">disabled</c:if>></button>

                                            <i class="mdi ${c.active ? 'mdi-toggle-switch-off' : 'mdi-toggle-switch'}"
                                               role="button"
                                               tabindex="0"
                                               data-bs-toggle="tooltip"
                                               data-bs-placement="top"
                                               title="${c.active ? (productVariantsMap[c.id].size() > 0 ? 'Cannot deactivate: product has active variants' : 'Switch to inactive') : 'Switch to active'}"
                                               <c:if test="${productVariantsMap[c.id].size() > 0}">
                                                   style="cursor: not-allowed; opacity:0.5; display: inline-block; font-size: 20px; width: 40px; text-align: center; color: #3f50f6;"
                                               </c:if>
                                               <c:if test="${productVariantsMap[c.id].size() == 0}">
                                                   style="cursor: pointer; display: inline-block; font-size: 20px; width: 40px; text-align: center; color: #3f50f6;"
                                                   onclick="document.getElementById('submit-product-${c.id}').click();"
                                               </c:if>
                                            ></i>
                                        </form>
                                    </td>
                                </tr>

                                <!-- Row phụ hiển thị variants -->
                                <tr class="collapse" id="variations${c.id}" data-bs-parent="#variantAccordion">
                                    <td colspan="9">
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
                                                    <th>Sell Price</th>
                                                    <th>Stock</th>
                                                    <th>Sold</th>
                                                    <th>Actions</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="variation" items="${productVariantsMap[c.id]}" varStatus="loop">
                                                    <tr>
                                                        <td>
                                                            ${(variantPageMap[c.id] - 1) * variantPageSize + loop.index + 1}
                                                        </td>
                                                        <td><img src="${pageContext.request.contextPath}/${variation.imageUrl}" style="max-width: 100px; max-height: 100px;"></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${not empty variation.colorName}">
                                                                    ${variation.colorName}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${variation.colorId}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${not empty variation.sizeName}">
                                                                    ${variation.sizeName}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${variation.sizeId}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${variation.price}</td>
                                                        <td>${variation.sell_price}</td>
                                                        <td>${variation.qtyInStock}</td>
                                                        <td>${variation.sold}</td>
                                                        <td>
                                                            <a class="me-3" href="productVariantManage?action=edit&variantId=${variation.variationId}">
                                                                <i class="mdi mdi-table-edit"
                                                                   data-bs-toggle="tooltip"
                                                                   data-bs-placement="top"
                                                                   title="Edit variant"
                                                                   style="display: inline-block;
                                                                      font-size: 20px;
                                                                      width: 40px;
                                                                      text-align: center;
                                                                      color: #3f50f6;">
                                                                </i>
                                                            </a>
                                                            <form action="productVariantManage" method="post" style="display:inline;"
                                                                  onsubmit="return confirm('${variation.active ? 'Are you sure to deactivate this product variant?' : 'Are you sure to restore this product variant?'}');">
                                                                <input type="hidden" name="action" value="delete"/>
                                                                <input type="hidden" name="variantId" value="${variation.variationId}"/>
                                                                <input type="hidden" name="active" value="${variation.active}"/>

                                                                <button type="submit" id="submit-variant-${variation.variationId}" style="display:none;"></button>

                                                                <i class="mdi ${variation.active ? 'mdi-toggle-switch-off' : 'mdi-toggle-switch'}"
                                                                   role="button"
                                                                   tabindex="0"
                                                                   data-bs-toggle="tooltip"
                                                                   data-bs-placement="top"
                                                                   title="${variation.active ? 'Switch to inactive' : 'Switch to active'}"
                                                                   onclick="document.getElementById('submit-variant-${variation.variationId}').click();"
                                                                   style="cursor: pointer;
                                                                      display: inline-block;
                                                                      font-size: 20px;
                                                                      width: 40px;
                                                                      text-align: center;
                                                                      color: #3f50f6;">
                                                                </i>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            <!-- Variant pagination controls -->
                                            <c:if test="${variantTotalPageMap[c.id] > 1}">
                                                <div class="d-flex justify-content-end mt-3">
                                                    <ul class="pagination">
                                                        <c:url var="variantBaseUrl" value="/productManage">
                                                            <c:param name="page" value="${page}" />
                                                            <c:if test="${not empty selectedCategoryId}">
                                                                <c:param name="categoryId" value="${selectedCategoryId}" />
                                                            </c:if>
                                                            <c:if test="${not empty selectedSubCategoryId}">
                                                                <c:param name="subCategoryId" value="${selectedSubCategoryId}" />
                                                            </c:if>
                                                        </c:url>

                                                        <c:set var="vPage" value="${variantPageMap[c.id]}" />
                                                        <c:set var="vTotalPage" value="${variantTotalPageMap[c.id]}" />

                                                        <c:choose>
                                                            <c:when test="${vTotalPage <= 5}">
                                                                <c:forEach var="i" begin="1" end="${vTotalPage}">
                                                                    <li class="page-item ${i == vPage ? 'active' : ''}">
                                                                        <a class="page-link"
                                                                           href="${variantBaseUrl}&variantPage${c.id}=${i}#variations${c.id}">
                                                                                ${i}
                                                                        </a>
                                                                    </li>
                                                                </c:forEach>
                                                            </c:when>

                                                            <c:when test="${vPage <= 3}">
                                                                <c:forEach var="i" begin="1" end="4">
                                                                    <li class="page-item ${i == vPage ? 'active' : ''}">
                                                                        <a class="page-link"
                                                                           href="${variantBaseUrl}&variantPage${c.id}=${i}#variations${c.id}">
                                                                                ${i}
                                                                        </a>
                                                                    </li>
                                                                </c:forEach>
                                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                                <li class="page-item">
                                                                    <a class="page-link"
                                                                       href="${variantBaseUrl}&variantPage${c.id}=${vTotalPage}#variations${c.id}">
                                                                            ${vTotalPage}
                                                                    </a>
                                                                </li>
                                                            </c:when>

                                                            <c:when test="${vPage >= vTotalPage - 2}">
                                                                <li class="page-item">
                                                                    <a class="page-link"
                                                                       href="${variantBaseUrl}&variantPage${c.id}=1#variations${c.id}">
                                                                        1
                                                                    </a>
                                                                </li>
                                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                                <c:forEach var="i" begin="${vTotalPage - 3}" end="${vTotalPage}">
                                                                    <li class="page-item ${i == vPage ? 'active' : ''}">
                                                                        <a class="page-link"
                                                                           href="${variantBaseUrl}&variantPage${c.id}=${i}#variations${c.id}">
                                                                                ${i}
                                                                        </a>
                                                                    </li>
                                                                </c:forEach>
                                                            </c:when>

                                                            <c:otherwise>
                                                                <li class="page-item">
                                                                    <a class="page-link"
                                                                       href="${variantBaseUrl}&variantPage${c.id}=1#variations${c.id}">
                                                                        1
                                                                    </a>
                                                                </li>
                                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                                <c:forEach var="i" begin="${vPage - 1}" end="${vPage + 1}">
                                                                    <li class="page-item ${i == vPage ? 'active' : ''}">
                                                                        <a class="page-link"
                                                                           href="${variantBaseUrl}&variantPage${c.id}=${i}#variations${c.id}">
                                                                                ${i}
                                                                        </a>
                                                                    </li>
                                                                </c:forEach>
                                                                <li class="page-item disabled"><span class="page-link">...</span></li>
                                                                <li class="page-item">
                                                                    <a class="page-link"
                                                                       href="${variantBaseUrl}&variantPage${c.id}=${vTotalPage}#variations${c.id}">
                                                                            ${vTotalPage}
                                                                    </a>
                                                                </li>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </ul>
                                                </div>
                                            </c:if>
                                            <div class="text-end">
                                                <button type="button" class="btn btn-info d-inline-flex align-items-center" onclick="location.href='${pageContext.request.contextPath}/productVariantManage?action=add&productId=${c.id}';" style="gap: 6px;">
                                                    Add New Product Variant
                                                </button>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
<%--                    pagination--%>
                    <div class="d-flex justify-content-end mt-3">
                        <ul class="pagination">
                            <c:set var="pageUrlBase" value="productManage?" />
                            <c:if test="${not empty selectedCategoryId}">
                                <c:set var="pageUrlBase" value="${pageUrlBase}&categoryId=${selectedCategoryId}" />
                            </c:if>
                            <c:if test="${not empty selectedSubCategoryId}">
                                <c:set var="pageUrlBase" value="${pageUrlBase}&subCategoryId=${selectedSubCategoryId}" />
                            </c:if>

                            <c:choose>
                                <c:when test="${totalPage <= 5}">
                                    <c:forEach var="i" begin="1" end="${totalPage}">
                                        <li class="page-item ${i == page ? 'active' : ''}">
                                            <a class="page-link" href="${pageUrlBase}&page=${i}">${i}</a>
                                        </li>
                                    </c:forEach>
                                </c:when>

                                <c:when test="${page <= 3}">
                                    <c:forEach var="i" begin="1" end="4">
                                        <li class="page-item ${i == page ? 'active' : ''}">
                                            <a class="page-link" href="${pageUrlBase}&page=${i}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                    <li class="page-item">
                                        <a class="page-link" href="${pageUrlBase}&page=${totalPage}">${totalPage}</a>
                                    </li>
                                </c:when>

                                <c:when test="${page >= totalPage - 2}">
                                    <li class="page-item">
                                        <a class="page-link" href="${pageUrlBase}&page=1">1</a>
                                    </li>
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                    <c:forEach var="i" begin="${totalPage - 3}" end="${totalPage}">
                                        <li class="page-item ${i == page ? 'active' : ''}">
                                            <a class="page-link" href="${pageUrlBase}&page=${i}">${i}</a>
                                        </li>
                                    </c:forEach>
                                </c:when>

                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link" href="${pageUrlBase}&page=1">1</a>
                                    </li>
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                    <c:forEach var="i" begin="${page - 1}" end="${page + 1}">
                                        <li class="page-item ${i == page ? 'active' : ''}">
                                            <a class="page-link" href="${pageUrlBase}&page=${i}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item disabled"><span class="page-link">...</span></li>
                                    <li class="page-item">
                                        <a class="page-link" href="${pageUrlBase}&page=${totalPage}">${totalPage}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        document.querySelectorAll('.toggle-variant-btn').forEach(btn => {
            btn.addEventListener('click', function (e) {
                e.preventDefault();

                const targetId = btn.getAttribute('data-target');
                const targetCollapse = document.querySelector(targetId);
                const icon = btn.querySelector('.chevron-icon');

                const isShown = targetCollapse.classList.contains('show');

                // Đóng tất cả hàng khác
                document.querySelectorAll('tr.collapse.show').forEach(row => {
                    if (row !== targetCollapse) {
                        row.classList.remove('show');
                        const iconReset = row.previousElementSibling?.querySelector('.chevron-icon');
                        if (iconReset) {
                            iconReset.classList.remove('mdi-arrow-up-drop-circle-outline');
                            iconReset.classList.add('mdi-arrow-down-drop-circle-outline');
                        }
                    }
                });

                // Toggle cái hiện tại
                if (isShown) {
                    targetCollapse.classList.remove('show');
                    icon.classList.remove('mdi-arrow-up-drop-circle-outline');
                    icon.classList.add('mdi-arrow-down-drop-circle-outline');
                } else {
                    targetCollapse.classList.add('show');
                    icon.classList.remove('mdi-arrow-down-drop-circle-outline');
                    icon.classList.add('mdi-arrow-up-drop-circle-outline');
                }
            });
        });

        // Filter subcategories based on selected category
        const categorySelect = document.getElementById('categorySelect');
        const subCategorySelect = document.getElementById('subCategorySelect');
        const searchInput = document.getElementById('productSearchInput');
        const filterBtn = document.getElementById('filterBtn');
        const allSubOptions = Array.from(subCategorySelect.options);

        // Cập nhật trạng thái của nút Filter
        function updateFilterButtonState() {
            const categoryVal = categorySelect.value;
            const subCategoryVal = subCategorySelect.value;
            const searchVal = searchInput.value.trim();
            const hasFilter = categoryVal || subCategoryVal || searchVal;
            filterBtn.disabled = !hasFilter;
        }

        // Lọc subcategory theo category
        function filterSubCategories() {
            const selectedCategory = categorySelect.value;
            const selectedSubCategory = subCategorySelect.getAttribute('data-selected');
            subCategorySelect.innerHTML = '';

            const allOption = document.createElement('option');
            allOption.value = '';
            allOption.textContent = 'All Subcategories';
            subCategorySelect.appendChild(allOption);

            allSubOptions.forEach(opt => {
                if (!opt.value) return;
                if (!selectedCategory || opt.getAttribute('data-category') === selectedCategory) {
                    const newOpt = opt.cloneNode(true);
                    if (selectedSubCategory && newOpt.value === selectedSubCategory) {
                        newOpt.selected = true;
                    }
                    subCategorySelect.appendChild(newOpt);
                }
            });

            updateFilterButtonState(); // cập nhật khi thay đổi subcategory
        }

        // Set category nếu có subcategory được chọn sẵn
        const selectedSubCategoryOption = subCategorySelect.querySelector('option[selected]');
        if (selectedSubCategoryOption && selectedSubCategoryOption.value) {
            const subCatCategoryId = selectedSubCategoryOption.getAttribute('data-category');
            if (subCatCategoryId) {
                categorySelect.value = subCatCategoryId;
            }
            subCategorySelect.setAttribute('data-selected', selectedSubCategoryOption.value);
        }

        // Gọi lúc load trang
        filterSubCategories();
        updateFilterButtonState();

        // Lắng nghe thay đổi
        categorySelect.addEventListener('change', () => {
            subCategorySelect.removeAttribute('data-selected');
            filterSubCategories();
            subCategorySelect.selectedIndex = 0;
        });
        subCategorySelect.addEventListener('change', updateFilterButtonState);
        searchInput.addEventListener('input', updateFilterButtonState);
    });
</script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        tooltipTriggerList.forEach(function (tooltipTriggerEl) {
            new bootstrap.Tooltip(tooltipTriggerEl);
        });
    });
</script>
<script>
    function notifyCannotDeactivate() {
        alert('Cannot deactivate product: it still has active variations.');
    }
</script>
</html>
