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
                <button class="btn btn-primary" onclick="openAddModal()">
                    <i class="fa fa-plus me-1"></i> Add New Category
                </button>
            </div>
        </div>

        <form method="get" action="subcategory">
            <div class="d-flex flex-wrap gap-2 mb-3">
                <select class="form-select" style="max-width: 200px;" name="productCategoryId">
                    <option value="">All Categories</option>
                    <c:forEach var="c" items="${categoryList}">
                        <option value="${c.id}" ${param.productCategoryId == c.id ? 'selected' : ''}>${c.name}</option>
                    </c:forEach>
                </select>
                <input type="text" name="search" class="form-control ms-auto" placeholder="Search by Sub Category name..." style="max-width: 300px;" value="${param.search}"/>
                <button class="btn btn-primary" type="submit">Filter</button>
            </div>
        </form>

        <div class="table-responsive">
            <table class="table table-hover">
                <thead class="table-light">
                <tr>
                    <th>No.</th>
                    <th>Sub Category Name</th>
                    <th>Category</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody id="subTableBody">
                <c:forEach var="s" items="${subList}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${s.name}</td>
                        <td>${s.categoryName}</td>
                        <td>
                            <a href="javascript:void(0);" onclick="openEditSubModal(${s.id}, '${s.name}', ${s.productCategoryId})">
                                <i class="fa fa-edit text-primary me-2"></i>
                            </a>
                            <form action="subcategory" method="post" style="display:inline;" onsubmit="return confirm('Delete this subcategory?');">
                                <input type="hidden" name="action" value="delete"/>
                                <input type="hidden" name="id" value="${s.id}"/>
                                <input type="hidden" name="productCategoryId" value="${s.productCategoryId}"/>
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
            </table>
        </div>
    </div>
</div>

<!-- Category Modal -->
<div class="modal fade" id="categoryModal" tabindex="-1" aria-labelledby="categoryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="category" method="post" class="modal-content" onsubmit="return validateCategoryForm();">
            <div class="modal-header">
                <h5 class="modal-title" id="categoryModalLabel">Add New Category</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="action" value="add" id="categoryAction">
                <input type="hidden" name="id" id="productCategoryId">
                <div class="mb-3">
                    <label for="categoryName" class="form-label">Category Name:</label>
                    <input type="text" class="form-control" name="name" id="categoryName" maxlength="15" required>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success"><i class="fa fa-save"></i> Save</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            </div>
        </form>
    </div>
</div>

<!-- Subcategory Modal -->
<div class="modal fade" id="subModal" tabindex="-1" aria-labelledby="subModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="subcategory" method="post" class="modal-content" onsubmit="return validateSubForm();">
            <div class="modal-header">
                <h5 class="modal-title" id="subModalLabel">Add Subcategory</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="action" id="subAction" value="add">
                <input type="hidden" name="id" id="subId">
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
    function openAddModal() {
        document.getElementById('categoryModalLabel').innerText = 'Add New Category';
        document.getElementById('categoryAction').value = 'add';
        document.getElementById('productCategoryId').value = '';
        document.getElementById('categoryName').value = '';
        new bootstrap.Modal(document.getElementById('categoryModal')).show();
    }

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

    function validateCategoryForm() {
        const name = document.getElementById('categoryName').value.trim();
        const isOnlyDigits = /^\d+$/.test(name);
        if (name === "") {
            alert("Category name cannot be empty.");
            return false;
        }
        if (isOnlyDigits) {
            alert("Category name cannot be only numbers.");
            return false;
        }
        if (name.length > 15) {
            alert("Category name must be 15 characters or fewer.");
            return false;
        }
        return true;
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
</script>
