<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<div class="page-header">
    <h3 class="page-title">Customer Management</h3>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="#">Staff Panel</a></li>
            <li class="breadcrumb-item active" aria-current="page">Customer List</li>
        </ol>
    </nav>
</div>

<!-- Search Form -->
<form method="post" action="${pageContext.request.contextPath}/staff/customerList" class="row g-3">
    <div class="col-md-8">
        <div class="form-group">
            <label for="search" class="form-label">Search Customers</label>
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
                <a href="${pageContext.request.contextPath}/staff/customerList" class="btn btn-light">
                    <i class="mdi mdi-close"></i> Clear
                </a>
            </c:if>
        </div>
    </div>
</form>

<!-- Customer List -->
<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="card-title mb-0">Customer List (View Only)</h4>
            <div class="d-flex align-items-center">
                <c:if test="${not empty search}">
                    <span class="badge badge-warning me-2">
                        <i class="mdi mdi-filter"></i> Filtered
                    </span>
                </c:if>
            </div>
        </div>

        <!-- Results Info -->
        <c:if test="${totalCustomers > 0}">
            <div class="mb-3">
                <small class="text-muted">
                    Showing ${startResult} - ${endResult} of ${totalCustomers} customers
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
                </tr>
                </thead>
                <tbody>
                <c:forEach var="customer" items="${customers}">
                    <tr>
                        <td>${customer.id}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty search}">
                                    ${customer.username}
                                </c:when>
                                <c:otherwise>
                                    ${customer.username}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${customer.firstName} ${customer.lastName}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phone != null ? customer.phone : 'N/A'}</td>
                        <td>
                            <span class="badge badge-info">Customer</span>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty customers}">
                    <tr>
                        <td colspan="6" class="text-center py-5">
                            <div class="text-muted">
                                <i class="mdi mdi-account-off mdi-48px"></i>
                                <p class="mt-2">
                                    <c:choose>
                                        <c:when test="${not empty search}">
                                            No customers found matching "<strong>${search}</strong>".
                                            <br><a href="${pageContext.request.contextPath}/staff/customerList"
                                                   class="btn btn-sm btn-outline-primary mt-2">
                                            <i class="mdi mdi-refresh"></i> Show All Customers
                                        </a>
                                        </c:when>
                                        <c:otherwise>
                                            No customers found in the system.
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

                <nav aria-label="Customer pagination">
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

<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Handle Enter key in search input
        document.getElementById("search").addEventListener("keypress", function (e) {
            if (e.key === "Enter") {
                e.preventDefault();
                this.form.submit();
            }
        });
    });
</script> 