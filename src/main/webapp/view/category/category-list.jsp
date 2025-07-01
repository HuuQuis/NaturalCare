<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="page-wrapper">
  <div class="content">
    <!-- Tiêu đề và nút Add -->
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h4 class="fw-bold">Category List</h4>
      <button class="btn btn-primary" onclick="openAddModal()">
        <i class="fa fa-plus me-1"></i> Add New Category
      </button>
    </div>

    <!-- Message -->
    <c:if test="${not empty sessionScope.message}">
      <div id="autoAlert"
           class="alert alert-${sessionScope.messageType == 'danger' ? 'danger' : 'success'} alert-dismissible fade show"
           style="position: fixed; top: 20px; right: 20px; z-index: 1055; min-width: 500px;" role="alert">
          ${sessionScope.message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close">
          <i class="fa fa-times"></i>
        </button>
      </div>
      <c:remove var="message" scope="session"/>
      <c:remove var="messageType" scope="session"/>
    </c:if>

    <form method="get" action="productCategory">
      <div class="d-flex flex-wrap gap-2 mb-3">
        <select name="sort" class="form-select" style="max-width: 180px;">
          <option value="">Sort by Name</option>
          <option value="asc" ${sort == 'asc' ? 'selected' : ''}>A → Z</option>
          <option value="desc" ${sort == 'desc' ? 'selected' : ''}>Z → A</option>
        </select>

        <select name="status" class="form-select" style="max-width: 180px;">
          <option value="">All Status</option>
          <option value="true" ${statusFilter == 'true' ? 'selected' : ''}>Active</option>
          <option value="false" ${statusFilter == 'false' ? 'selected' : ''}>Inactive</option>
        </select>

        <button class="btn btn-primary" type="submit">Filter</button>
        &nbsp;&nbsp;
        <input type="text" name="search" class="form-control ms-auto"
               placeholder="Search by Category name..." style="max-width: 300px;" value="${search}"/>
      </div>
    </form>

    <!-- Bảng danh sách -->
    <div class="table-responsive">
      <table class="table table-hover table-bordered bg-white shadow-sm">
        <thead class="table-light">
        <tr>
          <th>No.</th>
          <th>Category Name</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="updatedId" value="${sessionScope.updatedCategoryId}" />
        <c:forEach var="c" items="${list}" varStatus="loop">
          <tr class="${updatedId == c.id ? 'table-success' : ''}">
            <td>${startIndex + loop.index + 1}</td>
            <td>${c.name}</td>
            <td>
              <span class="badge ${c.status ? 'bg-success' : 'bg-secondary'}">
                  ${c.status ? 'Active' : 'Inactive'}
              </span>
            </td>
            <td>
              <a href="javascript:void(0)" onclick="openEditModal(${c.id}, '${c.name}', ${c.status})">
              <i class="fa fa-edit text-primary me-2"></i>
              </a>
              <form method="post" action="productCategory" style="display:inline;" onsubmit="return confirm('Delete this category?');">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="${c.id}"/>
                <input type="hidden" name="page" value="${page}"/>
                <button class="btn btn-link p-0"><i class="fa fa-trash text-danger"></i></button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty list}">
          <tr><td colspan="3" class="text-center text-muted fst-italic">No categories found.</td></tr>
        </c:if>
        </tbody>
        <c:remove var="updatedCategoryId" scope="session"/>
      </table>

      <!-- Pagination -->
      <div class="d-flex justify-content-end mt-3">
        <ul class="pagination">
          <c:forEach var="i" begin="1" end="${totalPage}">
            <li class="page-item ${i == page ? 'active' : ''}">
              <a class="page-link"
                 href="productCategory?page=${i}&search=${search}&sort=${sort}&status=${param.status}">
                  ${i}
              </a>
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="categoryModal" tabindex="-1" aria-labelledby="categoryModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <form action="productCategory" method="post" class="modal-content" onsubmit="return validateCategoryForm();">
      <div class="modal-header">
        <h5 class="modal-title" id="categoryModalLabel">Add New Category</h5>
        <button type="button" class="btn" data-bs-dismiss="modal" aria-label="Close">
          <i class="fa fa-times"></i>
        </button>
      </div>
      <div class="modal-body">
        <input type="hidden" name="action" value="add" id="categoryAction">
        <input type="hidden" name="id" id="productCategoryId">
        <input type="hidden" name="page" value="${page}"/>
        <div class="mb-3">
          <label for="categoryName" class="form-label">Category Name:</label>
          <input type="text" class="form-control" name="name" id="categoryName" maxlength="15" required>
        </div>
        <div class="mb-3" id="statusField">
          <label for="categoryStatus" class="form-label">Status:</label>
          <select class="form-select" name="status" id="categoryStatus" required>
            <option value="true">Active</option>
            <option value="false">Inactive</option>
          </select>
        </div>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-success"><i class="fa fa-save"></i> Save</button>
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
    document.getElementById('statusField').style.display = 'none'; // ẩn status khi thêm mới
    new bootstrap.Modal(document.getElementById('categoryModal')).show();
  }

  function openEditModal(id, name, status) {
    document.getElementById('categoryModalLabel').innerText = 'Edit Category';
    document.getElementById('categoryAction').value = 'update';
    document.getElementById('productCategoryId').value = id;
    document.getElementById('categoryName').value = name;
    document.getElementById('categoryStatus').value = status;
    document.getElementById('statusField').style.display = 'block'; // hiện status khi edit
    new bootstrap.Modal(document.getElementById('categoryModal')).show();
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
