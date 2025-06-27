<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="page-wrapper">
    <div class="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h4 class="fw-bold">Sub Category List</h4>
            <div>
                <button class="btn btn-primary me-2" onclick="openAddSubModal(0)">
                    <i class="fa fa-plus me-1"></i> Add New Sub Category
                </button>
            </div>
        </div>

        <!-- Message -->
        <c:if test="${not empty sessionScope.message}">
            <div id="autoAlert" class="alert alert-${sessionScope.messageType eq 'danger' ? 'danger' : 'success'} alert-dismissible fade show"
                 style="position: fixed; top: 20px; right: 20px; z-index: 1055; min-width: 500px;" role="alert">
                    ${sessionScope.message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
                    <i class="fa fa-times"></i>
                </button>
            </div>
            <c:remove var="message" scope="session"/>
            <c:remove var="messageType" scope="session"/>
        </c:if>

        <form method="get" action="subcategory">
            <div class="d-flex flex-wrap gap-2 mb-3">
                <select class="form-select" style="max-width: 200px;" name="productCategoryId">
                    <option value="">All Categories</option>
                    <c:forEach var="c" items="${categoryList}">
                        <option value="${c.id}" ${param.productCategoryId == c.id ? 'selected' : ''}>${c.name}</option>
                    </c:forEach>
                </select>
                &nbsp;&nbsp;
                <button class="btn btn-primary" type="submit">Filter</button>
                &nbsp;&nbsp;
                <input type="text" name="search" class="form-control ms-auto" placeholder="Search by Sub Category name..." style="max-width: 300px;" value="${param.search}"/>
            </div>
        </form>

        <div class="table-responsive">
            <table class="table table-hover table-bordered rounded shadow-sm bg-white">
                <thead class="table-light">
                <tr>
                    <th>No.</th>
                    <th>Sub Category Name</th>
                    <th>Category</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody id="subTableBody">
                <c:set var="updatedId" value="${sessionScope.updatedSubCategoryId}" />
                <c:forEach var="s" items="${subList}" varStatus="loop">
                    <tr class="${updatedId == s.id ? 'table-success' : ''}">
                        <td>${startIndex + loop.index + 1}</td>
                        <td>${s.name}</td>
                        <td>${s.categoryName}</td>
                        <td>
                            <a href="javascript:void(0);" onclick="openEditSubModal(${s.id}, '${s.name}', ${s.productCategoryId})">
                                <i class="fa fa-edit text-primary me-2"></i>
                            </a>
                            <form action="subcategory" method="post" style="display:inline;" onsubmit="return confirm('Delete this subcategory?');">
                                <input type="hidden" name="action" value="delete"/>
                                <input type="hidden" name="id" value="${s.id}"/>
                                <input type="hidden" name="page" value="${page}"/>
                                <c:if test="${not empty param.productCategoryId}">
                                    <input type="hidden" name="productCategoryId" value="${param.productCategoryId}"/>
                                </c:if>
                                <input type="hidden" name="search" value="${param.search}"/>
                                <button type="submit" class="btn btn-link p-0">
                                    <i class="fa fa-trash text-danger"></i>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty subList}">
                    <tr><td colspan="4" class="text-center text-muted fst-italic">No subcategories found.</td></tr>
                </c:if>
                </tbody>
                <c:remove var="updatedSubCategoryId" scope="session"/>
            </table>

            <!-- Pagination -->
            <div class="d-flex justify-content-end mt-3">
                <ul class="pagination">
                    <c:forEach var="i" begin="1" end="${totalPage}">
                        <li class="page-item ${i == page ? 'active' : ''}">
                            <a class="page-link"
                               href="subcategory?page=${i}
                   <c:if test='${not empty param.productCategoryId}'>&productCategoryId=${param.productCategoryId}</c:if>
                   <c:if test='${not empty param.search}'>&search=${param.search}</c:if>">
                                    ${i}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>

        </div>
    </div>
</div>

<!-- Subcategory Modal -->
<div class="modal fade" id="subModal" tabindex="-1" aria-labelledby="subModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="subcategory" method="post" class="modal-content" onsubmit="return validateSubForm();">
            <div class="modal-header">
                <h5 class="modal-title" id="subModalLabel">Add Subcategory</h5>
                <button type="button" class="btn" data-bs-dismiss="modal" aria-label="Close">
                    <i class="fa fa-times"></i>
                </button>

            </div>
            <div class="modal-body">
                <input type="hidden" name="action" id="subAction" value="add">
                <input type="hidden" name="id" id="subId">
                <input type="hidden" name="page" value="${page}"/>
                <input type="hidden" name="search" value="${param.search}"/>
                <%--<input type="hidden" name="productCategoryId" value="${param.productCategoryId}"/>--%>
                <div class="mb-3">
                    <label for="subName" class="form-label">Subcategory Name</label>
                    <input type="text" class="form-control" name="name" id="subName" required maxlength="15">
                </div>
                <div class="mb-3">
                    <label for="subCategorySelect" class="form-label">Category</label>
                    <select class="form-select" name="productCategoryId" id="subCategorySelect" required>
                        <c:forEach var="c" items="${categoryList}">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Save</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            </div>
        </form>
    </div>
</div>

<script>
    function openAddSubModal(productCategoryId) {
        document.getElementById('subModalLabel').innerText = 'Add Subcategory';
        document.getElementById('subAction').value = 'add';
        document.getElementById('subId').value = '';
        document.getElementById('subName').value = '';
        document.getElementById('subCategorySelect').value = productCategoryId || '';
        new bootstrap.Modal(document.getElementById('subModal')).show();
    }

    function openEditSubModal(id, name, productCategoryId) {
        document.getElementById('subModalLabel').innerText = 'Edit Subcategory';
        document.getElementById('subAction').value = 'update';
        document.getElementById('subId').value = id;
        document.getElementById('subName').value = name;
        document.getElementById('subCategorySelect').value = productCategoryId;
        new bootstrap.Modal(document.getElementById('subModal')).show();
    }

    function validateSubForm() {
        const name = document.getElementById('subName').value.trim();
        const isOnlyDigits = /^\d+$/.test(name);
        if (name === '') {
            alert("Subcategory name cannot be empty.");
            return false;
        }
        if (isOnlyDigits) {
            alert("Subcategory name cannot be only numbers.");
            return false;
        }
        if (name.length > 15) {
            alert("Subcategory name must be 15 characters or fewer.");
            return false;
        }
        return true;
    }

    window.onload = function () {
        const updatedRow = document.querySelector("tr.table-success");
        if (updatedRow) {
            updatedRow.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }

        const alertBox = document.getElementById("autoAlert");
        if (alertBox) {
            setTimeout(() => {
                const alertInstance = bootstrap.Alert.getOrCreateInstance(alertBox);
                alertInstance.close();
            }, 3000);
        }
    };
</script>
