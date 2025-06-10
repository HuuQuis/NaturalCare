<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="page-wrapper">
    <div class="content">
        <!-- Hiển thị thông báo -->
        <c:if test="${not empty sessionScope.message}">
            <script>
                alert("${sessionScope.message}");
            </script>
            <c:remove var="message" scope="session" />
        </c:if>

        <div class="page-header">
            <div class="page-title">
                <h4>Category List</h4>
            </div>
            <div class="page-btn">
                <a href="javascript:void(0);" class="btn btn-added" onclick="openAddModal()">
                    <img src="${pageContext.request.contextPath}/adminassets/img/icons/plus.svg" alt="img" class="me-1">
                    Add New Category
                </a>
            </div>
        </div>

        <div class="card">
            <div class="card-body">
                <div class="mb-3 d-flex justify-content-between align-items-center">
                    <div class="input-group" style="max-width: 300px;">
                        <span class="input-group-text"><i class="fa fa-search"></i></span>
                        <input type="text" id="searchInput" class="form-control" placeholder="Search">
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead class="table-light">
                        <tr>
                            <th>No.</th>
                            <th>Category Name</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="c" items="${list}" varStatus="loop">
                            <tr class="accordion-toggle" data-bs-toggle="collapse" data-bs-target="#collapse${c.id}">
                                <td>${(page - 1) * pageSize + loop.index + 1}</td>
                                <td><i class="fas fa-chevron-down me-2 text-primary"></i> ${c.name}</td>
                                <td>
                                    <a class="me-3" href="javascript:void(0);" onclick="openEditModal(${c.id}, '${c.name}')">
                                        <img src="${pageContext.request.contextPath}/adminassets/img/icons/edit.svg" alt="edit">
                                    </a>
                                    <form action="category" method="post" style="display:inline;" onsubmit="return confirm('Are you sure to delete this category?');">
                                        <input type="hidden" name="action" value="delete" />
                                        <input type="hidden" name="id" value="${c.id}" />
                                        <button type="submit" class="btn btn-delete">
                                            <img src="${pageContext.request.contextPath}/adminassets/img/icons/delete.svg" alt="delete">
                                        </button>
                                    </form>
                                </td>
                            </tr>

                            <!-- Subcategory Row -->
                            <tr class="collapse" id="collapse${c.id}">
                                <td colspan="3" class="p-0">
                                    <div class="p-3">
                                        <table class="table table-sm table-borderless mb-0 ms-4">
                                            <thead>
                                            <tr class="table-light">
                                                <th>#</th>
                                                <th>Subcategory Name</th>
                                                <th>Actions</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="s" items="${c.subList}" varStatus="subLoop">
                                                <tr>
                                                    <td>${subLoop.index + 1}</td>
                                                    <td>${s.name}</td>
                                                    <td>
                                                        <a href="javascript:void(0);" class="me-2" onclick="openEditSubModal(${s.id}, '${s.name}', ${c.id})">
                                                            <img src="${pageContext.request.contextPath}/adminassets/img/icons/edit.svg" alt="edit">
                                                        </a>
                                                        <form action="subcategory" method="post" style="display:inline;" onsubmit="return confirm('Delete this subcategory?');">
                                                            <input type="hidden" name="action" value="delete"/>
                                                            <input type="hidden" name="id" value="${s.id}"/>
                                                            <input type="hidden" name="categoryId" value="${c.id}"/>
                                                            <button type="submit" class="btn btn-delete">
                                                                <img src="${pageContext.request.contextPath}/adminassets/img/icons/delete.svg" alt="delete">
                                                            </button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty c.subList}">
                                                <tr><td colspan="3" class="text-muted fst-italic">No subcategories</td></tr>
                                            </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                    <a href="javascript:void(0);"
                                       class="btn btn-success btn-sm ms-4 text-white"
                                       onclick="openAddSubModal(${c.id})">
                                        <i class="fa fa-plus me-1"></i> Add Subcategory
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- PHÂN TRANG -->
                <div class="d-flex justify-content-end mt-3">
                    <ul class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPage}">
                            <li class="page-item ${i == page ? 'active' : ''}">
                                <a class="page-link" href="category?page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal Popup -->
<div class="modal fade" id="categoryModal" tabindex="-1" aria-labelledby="categoryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form action="category" method="post" class="modal-content" onsubmit="return validateCategoryForm();">
            <div class="modal-header">
                <h5 class="modal-title" id="categoryModalLabel">Add New Category</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="action" value="add" id="categoryAction">
                <input type="hidden" name="id" id="categoryId">
                <div class="mb-3">
                    <label for="categoryName" class="form-label">Category Name:</label>
                    <input type="text" class="form-control" name="name" id="categoryName" maxlength="15" required>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">
                    <i class="fa fa-save"></i> Save
                </button>
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
                <input type="hidden" name="categoryId" id="subCategoryId">

                <div class="mb-3">
                    <label for="subName" class="form-label">Subcategory Name</label>
                    <input type="text" class="form-control" name="name" id="subName" required maxlength="15">
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Save</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            </div>
        </form>
    </div>
</div>

<!-- Scripts -->
<script>
    function openAddModal() {
        document.getElementById('categoryModalLabel').innerText = 'Add New Category';
        document.getElementById('categoryAction').value = 'add';
        document.getElementById('categoryId').value = '';
        document.getElementById('categoryName').value = '';
        new bootstrap.Modal(document.getElementById('categoryModal')).show();
    }

    function openEditModal(id, name) {
        document.getElementById('categoryModalLabel').innerText = 'Update Category';
        document.getElementById('categoryAction').value = 'update';
        document.getElementById('categoryId').value = id;
        document.getElementById('categoryName').value = name;
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
</script>


<script>
    function openAddSubModal(categoryId) {
        document.getElementById('subModalLabel').innerText = 'Add Subcategory';
        document.getElementById('subAction').value = 'add';
        document.getElementById('subId').value = '';
        document.getElementById('subCategoryId').value = categoryId;
        document.getElementById('subName').value = '';
        new bootstrap.Modal(document.getElementById('subModal')).show();
    }

    function openEditSubModal(id, name, categoryId) {
        document.getElementById('subModalLabel').innerText = 'Edit Subcategory';
        document.getElementById('subAction').value = 'update';
        document.getElementById('subId').value = id;
        document.getElementById('subCategoryId').value = categoryId;
        document.getElementById('subName').value = name;
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
</script>

<script>
    document.getElementById('searchInput').addEventListener('input', function () {
        const filter = this.value.toLowerCase().trim();
        const rows = document.querySelectorAll('tbody tr.accordion-toggle');

        rows.forEach(row => {
            const categoryCell = row.querySelector('td:nth-child(2)');
            const categoryName = categoryCell ? categoryCell.innerText.toLowerCase() : '';
            const collapseId = row.getAttribute('data-bs-target');
            const subRow = document.querySelector(collapseId);
            let match = categoryName.includes(filter);

            // Nếu chưa match, kiểm tra các subcategory bên trong
            if (!match && subRow) {
                const subTexts = subRow.querySelectorAll('td:nth-child(2)');
                for (const sub of subTexts) {
                    if (sub.innerText.toLowerCase().includes(filter)) {
                        match = true;
                        break;
                    }
                }
            }

            // Hiển thị nếu có match
            row.style.display = match ? '' : 'none';
            if (subRow) subRow.style.display = match ? '' : 'none';
        });
    });
</script>

<c:if test="${hasDependency}">
    <script>
        if (confirm("Không thể xoá Category vì vẫn còn SubCategory hoặc Sản phẩm.\nBạn có muốn ẩn Category này khỏi danh sách không?")) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'category';

            const actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'hide';

            const idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'id';
            idInput.value = '${categoryIdToHide}';

            form.appendChild(actionInput);
            form.appendChild(idInput);
            document.body.appendChild(form);
            form.submit();
        }
    </script>
</c:if>


<c:if test="${hasSubDependency}">
    <script>
        if (confirm("Không thể xoá SubCategory vì vẫn còn sản phẩm.\nBạn có muốn ẩn SubCategory này khỏi danh sách không?")) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'subcategory';
            form.innerHTML = `
                <input type="hidden" name="action" value="hide">
                <input type="hidden" name="id" value="${subCategoryIdToHide}">
                <input type="hidden" name="categoryId" value="0"> <!-- hoặc truyền đúng categoryId nếu bạn có -->
            `;
            document.body.appendChild(form);
            form.submit();
        }
    </script>
</c:if>
