<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-wrapper">
  <div class="content">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h4 class="fw-bold">Blog Category Management</h4>
      <button class="btn btn-primary" onclick="openAddModal()">
        <i class="fa fa-plus me-1"></i> Add Blog Category
      </button>
    </div>

    <!-- Message Alert -->
    <c:if test="${not empty sessionScope.message}">
      <div id="autoAlert" class="alert alert-${sessionScope.messageType} alert-dismissible fade show"
           style="position: fixed; top: 20px; right: 20px; z-index: 1055; min-width: 300px;" role="alert">
          ${sessionScope.message}
            <button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
              <i class="fa fa-times"></i>
            </button>
      </div>
      <c:remove var="message" scope="session"/>
      <c:remove var="messageType" scope="session"/>
    </c:if>

    <!-- Table -->
    <div class="table-responsive">
      <table class="table table-hover table-bordered rounded shadow-sm bg-white">
        <thead class="table-light">
        <tr>
          <th>No.</th>
          <th>Blog Category Name</th>
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
              <a href="javascript:void(0);" onclick="openEditModal(${c.id}, '${c.name}')">
                <i class="fa fa-edit text-primary me-2"></i>
              </a>
              <form action="blog-category" method="post" style="display:inline;" onsubmit="return confirm('Delete this blog category?');">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="${c.id}"/>
                <input type="hidden" name="page" value="${page}"/>
                <button type="submit" class="btn btn-link p-0">
                  <i class="fa fa-trash text-danger"></i>
                </button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty list}">
          <tr><td colspan="3" class="text-center text-muted fst-italic">No blog categories found.</td></tr>
        </c:if>
        <c:remove var="updatedCategoryId" scope="session"/>
        </tbody>
      </table>

      <!-- Pagination -->
      <div class="d-flex justify-content-end mt-3">
        <ul class="pagination">
          <c:forEach var="i" begin="1" end="${totalPage}">
            <li class="page-item ${i == page ? 'active' : ''}">
              <a class="page-link" href="blog-category?page=${i}">${i}</a>
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="blogCatModal" tabindex="-1" aria-labelledby="blogCatModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <form action="blog-category" method="post" class="modal-content" onsubmit="return validateBlogCategoryForm()">
      <div class="modal-header">
        <h5 class="modal-title" id="blogCatModalLabel">Add Blog Category</h5>
        <button type="button" class="btn" data-bs-dismiss="modal" aria-label="Close">
          <i class="fa fa-times"></i>
        </button>
      </div>
      <div class="modal-body">
        <input type="hidden" name="action" id="blogCatAction" value="add">
        <input type="hidden" name="id" id="blogCatId">
        <input type="hidden" name="page" value="${page}">
        <div class="mb-3">
          <label for="blogCatName" class="form-label">Blog Category Name</label>
          <input type="text" class="form-control" name="name" id="blogCatName" required maxlength="255">
          <div class="invalid-feedback" id="blogCatError"></div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-success">Save</button>
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
      </div>
    </form>
  </div>
</div>

<!-- Script -->
<script>

  function validateBlogCategoryForm() {
    const input = document.getElementById('blogCatName');
    const errorDiv = document.getElementById('blogCatError');
    const name = input.value.trim();
    const isOnlyDigits = /^\d+$/.test(name);

    input.classList.remove('is-invalid');
    errorDiv.innerText = '';

    if (name === '') {
      showError("Blog category name cannot be empty.");
      return false;
    }
    if (isOnlyDigits) {
      showError("Blog category name cannot be only numbers.");
      return false;
    }
    if (name.length > 20) {
      showError("Blog category name must be 20 characters or fewer.");
      return false;
    }
    return true;

    function showError(msg) {
      input.classList.add('is-invalid');
      errorDiv.innerText = msg;
    }
  }

  function openAddModal() {
    document.getElementById("blogCatModalLabel").innerText = "Add Blog Category";
    document.getElementById("blogCatAction").value = "add";
    document.getElementById("blogCatId").value = "";
    document.getElementById("blogCatName").value = "";
    new bootstrap.Modal(document.getElementById("blogCatModal")).show();
  }

  function openEditModal(id, name) {
    document.getElementById("blogCatModalLabel").innerText = "Edit Blog Category";
    document.getElementById("blogCatAction").value = "update";
    document.getElementById("blogCatId").value = id;
    document.getElementById("blogCatName").value = name;
    new bootstrap.Modal(document.getElementById("blogCatModal")).show();
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
