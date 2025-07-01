<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="page-header">
    <h3 class="page-title">Customer Management</h3>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="#">Staff Panel</a></li>
            <li class="breadcrumb-item active" aria-current="page">Customer List</li>
        </ol>
    </nav>
</div>

<!-- Customer List -->
<div class="card">
    <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="card-title mb-0">Customer List (View Only)</h4>
            <span class="badge badge-info">Total: ${customers.size()} customers</span>
        </div>
        
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
                            <td>${customer.username}</td>
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
                            <td colspan="6" class="text-center">
                                <div class="text-muted">
                                    <i class="mdi mdi-account-off mdi-48px"></i>
                                    <p>No customers found in the system.</p>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        
        <c:if test="${not empty customers}">
            <div class="mt-3">
                <small class="text-muted">
                    <i class="mdi mdi-information"></i>
                    You are viewing customer information in read-only mode. 
                    Contact your administrator for any customer-related modifications.
                </small>
            </div>
        </c:if>
    </div>
</div> 