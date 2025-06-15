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
                    <h4>Product List</h4>
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
                    <div class="table-top">
                        <div class="search-set d-flex align-items-center flex-wrap" style="gap: 15px;">
                            <form id="filterForm" method="get" action="productManage"
                                  class="d-flex align-items-center flex-wrap" style="gap: 10px;">
                                <select name="categoryId" id="categorySelect" class="form-select" style="width: 180px;">
                                    <option value="">All Categories</option>
                                    <c:forEach var="cat" items="${categories}">
                                        <option value="${cat.id}" ${cat.id == selectedCategoryId ? 'selected' : ''}>${cat.name}</option>
                                    </c:forEach>
                                </select>
                                <select name="subCategoryId" id="subCategorySelect" class="form-select"
                                        style="width: 180px;">
                                    <option value="">All Subcategories</option>
                                    <c:forEach var="sub" items="${subCategories}">
                                        <option value="${sub.id}"
                                                data-category="${sub.productCategoryId}" ${sub.id == selectedSubCategoryId ? 'selected' : ''}>${sub.name}</option>
                                    </c:forEach>
                                </select>
                                <button type="submit" class="btn btn-primary">Filter</button>
                            </form>

                            <div class="position-relative">
                                <input type="text" id="productSearchInput" class="form-control"
                                       placeholder="Search by product name..."
                                       style="padding-left: 30px; min-width: 250px;"/>
                                <i class="mdi mdi-magnify position-absolute"
                                   style="left: 10px; top: 50%; transform: translateY(-50%); pointer-events: none;"></i>
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
                            <tbody id="variantAccordion">
                            <c:forEach var="c" items="${products}" varStatus="loop">
                                <tr data-name="${c.name.toLowerCase()}">
                                <!-- Row chính -->
                                    <td>${(page - 1) * pageSize + loop.index + 1}</td>
                                    <td>
                                            ${c.name}
                                        <button class="btn btn-link toggle-variant-btn" type="button"
                                                data-target="#variations${c.id}">
                                            <i class="mdi mdi-arrow-down-drop-circle-outline chevron-icon"
                                               id="chevron-${c.id}"
                                               style="cursor: pointer;font-size: 20px;"></i>
                                        </button>
                                    </td>
                                    <td>${c.description.length() > 10 ? c.description.substring(0,10).concat('...') : c.description}</td>
                                    <td>${c.information.length() > 10 ? c.information.substring(0,10).concat('...') : c.information}</td>
                                    <td>${c.guideline.length() > 10 ? c.guideline.substring(0,10).concat('...') : c.guideline}</td>
                                    <td>
                                        <a class="me-3" href="productManage?action=edit&id=${c.id}">
                                            <i class="mdi mdi-table-edit"
                                               style="display: inline-block;
                                                  font-size: 20px;
                                                  width: 40px;
                                                  text-align: center;
                                                  color: #3f50f6;"></i>
                                        </a>
                                        <form action="productManage" method="post" style="display:inline;"
                                              onsubmit="return confirm('Are you sure to delete this product?');">
                                            <input type="hidden" name="action" value="delete"/>
                                            <input type="hidden" name="id" value="${c.id}"/>

                                            <!-- Nút submit ẩn để gọi submit hợp lệ -->
                                            <button type="submit" id="submit-product-${c.id}" style="display:none;"></button>

                                            <!-- Icon đóng vai trò nút -->
                                            <i class="mdi mdi-delete"
                                               role="button"
                                               tabindex="0"
                                               onclick="document.getElementById('submit-product-${c.id}').click();"
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

                                <!-- Row phụ hiển thị variants -->
                                <tr class="collapse" id="variations${c.id}" data-bs-parent="#variantAccordion">
                                    <td colspan="6">
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
                                                    <th>Actions</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="variation" items="${productVariantsMap[c.id]}" varStatus="loop">
                                                    <tr>
                                                        <td>${loop.index + 1}</td>
                                                        <td><img src="${pageContext.request.contextPath}/${variation.imageUrl}" style="max-width: 100px; max-height: 100px;"></td>
                                                        <td>${variation.colorId}</td>
                                                        <td>${variation.sizeId}</td>
                                                        <td>${variation.price}</td>
                                                        <td>${variation.qtyInStock}</td>
                                                        <td>${variation.sold}</td>
                                                        <td>
                                                            <a class="me-3" href="productVariantManage?action=edit&id=${variation.variationId}">
                                                                <i class="mdi mdi-table-edit"
                                                                   style="display: inline-block;
                                                                      font-size: 20px;
                                                                      width: 40px;
                                                                      text-align: center;
                                                                      color: #3f50f6;">
                                                                </i>
                                                            </a>
                                                            <form action="productVariantManage" method="post" style="display:inline;"
                                                                  onsubmit="return confirm('Are you sure to delete this product variant?');">
                                                                <input type="hidden" name="action" value="delete"/>
                                                                <input type="hidden" name="variantId" value="${variation.variationId}"/>

                                                                <button type="submit" id="submit-variant-${variation.variationId}" style="display:none;"></button>

                                                                <i class="mdi mdi-delete"
                                                                   role="button"
                                                                   tabindex="0"
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
        const searchInput = document.getElementById('productSearchInput');
        const rows = document.querySelectorAll('tbody#variantAccordion tr');

        searchInput.addEventListener('input', function () {
            const searchTerm = this.value.trim().toLowerCase();

            let currentMainRow = null;
            rows.forEach((row, index) => {
                if (row.hasAttribute('data-name')) {
                    currentMainRow = row;
                    const match = row.getAttribute('data-name').includes(searchTerm);

                    // Hiển thị hoặc ẩn dòng chính
                    row.style.display = match ? '' : 'none';

                    // Ẩn luôn dòng phụ theo nó
                    const variantRow = rows[index + 1];
                    if (variantRow && variantRow.classList.contains('collapse')) {
                        variantRow.style.display = match ? '' : 'none';
                    }
                }
            });
        });
        // Filter subcategories based on selected category
        const categorySelect = document.getElementById('categorySelect');
        const subCategorySelect = document.getElementById('subCategorySelect');
        const allSubOptions = Array.from(subCategorySelect.options);

        function filterSubCategories() {
            const selectedCategory = categorySelect.value;
            const selectedSubCategory = subCategorySelect.getAttribute('data-selected');
            subCategorySelect.innerHTML = '';
            // Always add "All Subcategories"
            const allOption = document.createElement('option');
            allOption.value = '';
            allOption.textContent = 'All Subcategories';
            subCategorySelect.appendChild(allOption);

            allSubOptions.forEach(opt => {
                if (!opt.value) return; // skip the default "All Subcategories" from original
                if (!selectedCategory || opt.getAttribute('data-category') === selectedCategory) {
                    const newOpt = opt.cloneNode(true);
                    // Restore selected subcategory if matches
                    if (selectedSubCategory && newOpt.value === selectedSubCategory) {
                        newOpt.selected = true;
                    }
                    subCategorySelect.appendChild(newOpt);
                }
            });
        }

        // --- Fix: Set category if subcategory is selected ---
        // If a subcategory is selected, set the category dropdown accordingly
        const selectedSubCategoryOption = subCategorySelect.querySelector('option[selected]');
        if (selectedSubCategoryOption && selectedSubCategoryOption.value) {
            const subCatCategoryId = selectedSubCategoryOption.getAttribute('data-category');
            if (subCatCategoryId) {
                categorySelect.value = subCatCategoryId;
            }
            // Mark the selected subcategory for filterSubCategories
            subCategorySelect.setAttribute('data-selected', selectedSubCategoryOption.value);
        }

        // Initial filter on page load
        filterSubCategories();

        // When category changes, filter subcategories
        categorySelect.addEventListener('change', function () {
            subCategorySelect.removeAttribute('data-selected');
            filterSubCategories();
            // Optionally reset subcategory selection
            subCategorySelect.selectedIndex = 0;
        });
    });
</script>


</html>