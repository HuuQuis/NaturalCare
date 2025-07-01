<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<div class="page-header">
    <h3 class="page-title">Manager Management</h3>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="#">Admin Panel</a></li>
            <li class="breadcrumb-item active" aria-current="page">Manager Management</li>
        </ol>
    </nav>
</div>

<!-- Success/Error Messages -->
<c:if test="${param.success != null}">
    <div class="alert alert-success" role="alert">
        <i class="mdi mdi-check-circle"></i> ${param.success}
    </div>
</c:if>

<c:if test="${param.error != null}">
    <div class="alert alert-danger" role="alert">
        <i class="mdi mdi-alert-circle"></i> ${param.error}
    </div>
</c:if>

<!-- Add/Edit Form -->
<c:if test="${param.action == 'add' || param.action == 'edit'}">
    <div class="card mb-4">
        <div class="card-body">
            <h4 class="card-title">
                <c:choose>
                    <c:when test="${param.action == 'add'}">
                        <i class="mdi mdi-account-plus"></i> Add New Manager
                    </c:when>
                    <c:otherwise>
                        <i class="mdi mdi-account-edit"></i> Edit Manager
                    </c:otherwise>
                </c:choose>
            </h4>

            <form method="post" action="${pageContext.request.contextPath}/admin/managerManage">
                <input type="hidden" name="action" value="${param.action == 'add' ? 'create' : 'update'}">
                <c:if test="${param.action == 'edit'}">
                    <input type="hidden" name="userId" value="${editUser.id}">
                </c:if>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="username">Username <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="username" name="username"
                                   value="${editUser.username}" required
                                   placeholder="Enter username">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="email">Email <span class="text-danger">*</span></label>
                            <input type="email" class="form-control" id="email" name="email"
                                   value="${editUser.email}" required
                                   placeholder="Enter email address">
                        </div>
                    </div>
                </div>

                <c:if test="${param.action == 'add'}">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="password">Password <span class="text-danger">*</span></label>
                                <input type="password" class="form-control" id="password" name="password"
                                       required placeholder="Enter password">
                            </div>
                        </div>
                    </div>
                </c:if>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="firstName">First Name <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="firstName" name="firstName"
                                   value="${editUser.firstName}" required
                                   placeholder="Enter first name">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="lastName">Last Name <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="lastName" name="lastName"
                                   value="${editUser.lastName}" required
                                   placeholder="Enter last name">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="text" class="form-control" id="phone" name="phone"
                                   value="${editUser.phone}"
                                   placeholder="Enter phone number (optional)">
                        </div>
                    </div>
                </div>

                <div class="form-group mt-3">
                    <button type="submit" class="btn btn-primary me-2">
                        <i class="mdi mdi-content-save"></i>
                        <c:choose>
                            <c:when test="${param.action == 'add'}">Create Manager</c:when>
                            <c:otherwise>Update Manager</c:otherwise>
                        </c:choose>
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/managerManage" class="btn btn-light">
                        <i class="mdi mdi-cancel"></i> Cancel
                    </a>
                </div>
            </form>
        </div>
    </div>
</c:if>

<!-- Search and Manager List -->
<c:if test="${param.action != 'add' && param.action != 'edit'}">
    <!-- Search Form -->
    <form method="post" action="${pageContext.request.contextPath}/admin/managerManage" class="row g-3">
        <input type="hidden" name="action" value="search">
        <div class="col-md-8">
            <div class="form-group">
                <label for="search" class="form-label">Search Managers</label>
                <input type="text" class="form-control" id="search" name="search"
                       value="${search}"
                       placeholder="Search by username, email, name, or phone number...">
            </div>
        </div>
        <div class="col-md-4 d-flex align-items-end">
            <div class="form-group w-100">
                <button type="submit" class="btn btn-primary me-2">
                    <i class="mdi mdi-magnify"></i> Search
                </button>
                <c:if test="${not empty search}">
                    <a href="${pageContext.request.contextPath}/admin/managerManage" class="btn btn-light">
                        <i class="mdi mdi-close"></i> Clear
                    </a>
                </c:if>
            </div>
        </div>
    </form>

    <!-- Manager List -->
    <div class="card">
        <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="card-title mb-0">Manager List</h4>
                <div class="d-flex align-items-center">
                    <c:if test="${not empty search}">
                        <span class="badge badge-warning me-2">
                            <i class="mdi mdi-filter"></i> Filtered
                        </span>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/admin/managerManage?action=add" class="btn btn-primary">
                        <i class="mdi mdi-plus"></i> Add New Manager
                    </a>
                </div>
            </div>

            <!-- Results Info -->
            <c:if test="${totalManagers > 0}">
                <div class="mb-3">
                    <small class="text-muted">
                        Showing ${startResult} - ${endResult} of ${totalManagers} managers
                        <c:if test="${not empty search}">
                            matching "<strong>${search}</strong>"
                        </c:if>
                    </small>
                </div>
            </c:if>

            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="manager" items="${managers}">
                        <tr>
                            <td>${manager.id}</td>
                            <td>${manager.username}</td>
                            <td>${manager.firstName} ${manager.lastName}</td>
                            <td>${manager.email}</td>
                            <td>${manager.phone != null ? manager.phone : 'N/A'}</td>
                            <td>
                                <span class="badge badge-success">Manager</span>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/managerManage?action=edit&id=${manager.id}"
                                   class="btn btn-primary btn-sm" title="Edit Manager">
                                    <i class="mdi mdi-pencil"></i>
                                </a>
                                <button type="button" class="btn btn-danger btn-sm"
                                        onclick="confirmDelete(${manager.id}, '${manager.username}')"
                                        title="Delete Manager">
                                    <i class="mdi mdi-delete"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty managers}">
                        <tr>
                            <td colspan="7" class="text-center py-5">
                                <div class="text-muted">
                                    <i class="mdi mdi-account-off mdi-48px"></i>
                                    <p class="mt-2">
                                        <c:choose>
                                            <c:when test="${not empty search}">
                                                No managers found matching "<strong>${search}</strong>".
                                                <br><a href="${pageContext.request.contextPath}/admin/managerManage"
                                                       class="btn btn-sm btn-outline-primary mt-2">
                                                <i class="mdi mdi-refresh"></i> Show All Managers
                                            </a>
                                            </c:when>
                                            <c:otherwise>
                                                No managers found in the system.
                                                <br><a
                                                    href="${pageContext.request.contextPath}/admin/managerManage?action=add"
                                                    class="btn btn-sm btn-primary mt-2">
                                                <i class="mdi mdi-plus"></i> Add First Manager
                                            </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>

            <!-- Pagination -->
            <c:if test="${totalPages > 1}">
                <div class="d-flex justify-content-between align-items-center mt-4">
                    <div>
                        <small class="text-muted">
                            Page ${currentPage} of ${totalPages}
                        </small>
                    </div>

                    <nav aria-label="Manager pagination">
                        <ul class="pagination mb-0">
                            <!-- Previous Button -->
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <c:choose>
                                    <c:when test="${currentPage == 1}">
                                        <span class="page-link">
                                            <i class="mdi mdi-chevron-left"></i>
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="page-link"
                                           href="?page=${currentPage - 1}${not empty search ? '&search=' : ''}${search}">
                                            <i class="mdi mdi-chevron-left"></i>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </li>

                            <!-- Page Numbers -->
                            <c:forEach var="pageNum" items="${pageNumbers}">
                                <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                    <c:choose>
                                        <c:when test="${pageNum == currentPage}">
                                            <span class="page-link">${pageNum}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="page-link"
                                               href="?page=${pageNum}${not empty search ? '&search=' : ''}${search}">
                                                    ${pageNum}
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </li>
                            </c:forEach>

                            <!-- Next Button -->
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <c:choose>
                                    <c:when test="${currentPage == totalPages}">
                                        <span class="page-link">
                                            <i class="mdi mdi-chevron-right"></i>
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="page-link"
                                           href="?page=${currentPage + 1}${not empty search ? '&search=' : ''}${search}">
                                            <i class="mdi mdi-chevron-right"></i>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </ul>
                    </nav>
                </div>
            </c:if>
        </div>
    </div>
</c:if>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Handle Enter key in search input
        const searchInput = document.getElementById("search");
        if (searchInput) {
            searchInput.addEventListener("keypress", function (e) {
                if (e.key === "Enter") {
                    e.preventDefault();
                    this.form.submit();
                }
            });
        }
    });

    function confirmDelete(userId, username) {
        if (confirm('Are you sure you want to delete manager "' + username + '"?\n\nThis action cannot be undone.')) {
            window.location.href = '${pageContext.request.contextPath}/admin/managerManage?action=delete&id=' + userId;
        }
    }
</script>