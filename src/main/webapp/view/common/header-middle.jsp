<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .profile-dropdown {
        position: relative;
        display: inline-block;
    }

    .dropdown-content {
        display: none;
        position: absolute;
        background-color: #f9f9f9;
        min-width: 200px;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
        z-index: 1000;
        padding: 12px;
        border-radius: 4px;
        right: 0;
    }

    .profile-dropdown:hover .dropdown-content {
        display: block;
    }

    .user-info {
        padding: 10px;
        border-bottom: 1px solid #ddd;
    }

    .address-list {
        padding: 10px;
    }

    .address-item {
        margin: 5px 0;
        font-size: 14px;
    }

    .dropdown-content a {
        text-decoration: none;
        color: #333;
        display: block;
        padding: 8px 0;
    }

    .dropdown-content a:hover {
        color: #fe980f;
    }

    /* Modal Styles */
    .modal {
        display: none;
        position: fixed;
        z-index: 10000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0,0,0);
        background-color: rgba(0,0,0,0.4);
    }

    .modal-content {
        background-color: #fefefe;
        margin: 5% auto;
        padding: 0;
        border: none;
        width: 80%;
        max-width: 600px;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .modal-header {
        background-color: #fe980f;
        color: white;
        padding: 20px;
        border-radius: 8px 8px 0 0;
        position: relative;
    }

    .modal-header h2 {
        margin: 0;
        font-size: 24px;
        font-weight: 500;
    }

    .close {
        color: white;
        float: right;
        font-size: 28px;
        font-weight: bold;
        position: absolute;
        right: 20px;
        top: 15px;
        cursor: pointer;
    }

    .close:hover,
    .close:focus {
        color: #ccc;
        text-decoration: none;
    }

    .modal-body {
        padding: 20px;
        max-height: 400px;
        overflow-y: auto;
    }

    .address-card {
        border: 1px solid #e0e0e0;
        border-radius: 6px;
        padding: 15px;
        margin-bottom: 15px;
        background-color: #f9f9f9;
        transition: all 0.3s ease;
    }

    .address-card:hover {
        border-color: #fe980f;
        box-shadow: 0 2px 8px rgba(254, 152, 15, 0.2);
    }

    .address-card.default {
        border-color: #fe980f;
        background-color: #fff5e6;
    }

    .address-type {
        display: inline-block;
        background-color: #fe980f;
        color: white;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: bold;
        margin-bottom: 8px;
    }

    .address-type.work {
        background-color: #17a2b8;
    }

    .address-type.other {
        background-color: #6c757d;
    }

    .default-badge {
        float: right;
        background-color: #28a745;
        color: white;
        padding: 2px 6px;
        border-radius: 3px;
        font-size: 11px;
    }

    .address-details {
        margin-bottom: 10px;
    }

    .address-details strong {
        color: #333;
        display: block;
        margin-bottom: 5px;
    }

    .address-text {
        color: #666;
        line-height: 1.4;
    }

    .address-actions {
        margin-top: 10px;
        padding-top: 10px;
        border-top: 1px solid #e0e0e0;
    }

    .btn-address {
        background-color: #fe980f;
        color: white;
        border: none;
        padding: 6px 12px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 12px;
        margin-right: 8px;
        transition: background-color 0.3s ease;
    }

    .btn-address:hover {
        background-color: #e8850d;
    }

    .btn-address.secondary {
        background-color: #6c757d;
    }

    .btn-address.secondary:hover {
        background-color: #5a6268;
    }

    .add-address-btn {
        width: 100%;
        padding: 15px;
        background-color: #f8f9fa;
        border: 2px dashed #dee2e6;
        border-radius: 6px;
        cursor: pointer;
        text-align: center;
        color: #6c757d;
        transition: all 0.3s ease;
        margin-bottom: 15px;
    }

    .add-address-btn:hover {
        border-color: #fe980f;
        color: #fe980f;
        background-color: #fff5e6;
    }

    .empty-state {
        text-align: center;
        padding: 40px 20px;
        color: #666;
    }

    .empty-state i {
        font-size: 48px;
        color: #ddd;
        margin-bottom: 15px;
    }
</style>

<div class="header-middle">
    <div class="container">
        <div class="row">
            <div class="col-sm-4">
                <div class="logo pull-left">
                    <a href=${pageContext.request.contextPath}/home><img
                            src="https://naturalcare.vercel.app/naturalcare/NLC-Logo.png" alt=""
                            style="max-height: 70px"/></a>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="search-box center-block">
                    <input oninput="searchByName(this)" value="${txt}" id="searchInput" type="text" name="txt"
                           autocomplete="off" placeholder="Search products..."/>
                    <div id="searchList" class="search-list"></div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="shop-menu pull-right">
                    <ul class="nav navbar-nav">
                        <c:choose>
                            <c:when test="${not empty user}">
                                <li><a href="cart" aria-label="Shopping Cart"><i class="fa fa-shopping-cart"></i>
                                    Cart</a></li>
                                <li class="profile-dropdown">
                                    <a href="#" aria-label="User Profile"><i class="fa fa-user"></i>${user.username}</a>
                                    <div class="dropdown-content">
                                        <div class="user-info">
                                            <a href="#" aria-label="User Profile"><i class="fa fa-user-circle"></i> My Profile</a>
                                        </div>
                                        <div class="address-list">
                                            <a href="#" onclick="openAddressModal()" aria-label="Address List"><i class="fa fa-map-marker"></i> Address List</a>
                                        </div>
                                    </div>
                                </li>
                                <li><a href="logout" aria-label="Logout"><i class="fa fa-lock"></i> Logout</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="login" aria-label="Login"><i class="fa fa-lock"></i> Login</a></li>
                                <li><a href="cart" aria-label="Shopping Cart"><i class="fa fa-shopping-cart"></i>
                                    Cart</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Address List Modal -->
<div id="addressModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <span class="close">&times;</span>
            <h2><i class="fa fa-map-marker"></i> My Addresses</h2>
        </div>
        <div class="modal-body">
            <!-- Add New Address Button -->
            <div class="add-address-btn" onclick="addNewAddress()">
                <i class="fa fa-plus"></i> Add New Address
            </div>

            <!-- Address List Container -->
            <div id="addressListContainer">
                <!-- Sample addresses - replace with dynamic content -->
                <div class="address-card default">
                    <span class="address-type">Home</span>
                    <span class="default-badge">Default</span>
                    <div class="address-details">
                        <strong>John Doe</strong>
                        <div class="address-text">
                            123 Main Street, Apartment 4B<br>
                            New York, NY 10001<br>
                            Phone: +1 (555) 123-4567
                        </div>
                    </div>
                    <div class="address-actions">
                        <button class="btn-address" onclick="editAddress(1)">Edit</button>
                        <button class="btn-address secondary" onclick="deleteAddress(1)">Delete</button>
                        <button class="btn-address" onclick="setDefaultAddress(1)">Set as Default</button>
                    </div>
                </div>

                <div class="address-card">
                    <span class="address-type work">Work</span>
                    <div class="address-details">
                        <strong>John Doe</strong>
                        <div class="address-text">
                            456 Business Ave, Suite 100<br>
                            New York, NY 10002<br>
                            Phone: +1 (555) 987-6543
                        </div>
                    </div>
                    <div class="address-actions">
                        <button class="btn-address" onclick="editAddress(2)">Edit</button>
                        <button class="btn-address secondary" onclick="deleteAddress(2)">Delete</button>
                        <button class="btn-address" onclick="setDefaultAddress(2)">Set as Default</button>
                    </div>
                </div>

                <div class="address-card">
                    <span class="address-type other">Other</span>
                    <div class="address-details">
                        <strong>Jane Smith</strong>
                        <div class="address-text">
                            789 Park Avenue<br>
                            Brooklyn, NY 11201<br>
                            Phone: +1 (555) 555-1234
                        </div>
                    </div>
                    <div class="address-actions">
                        <button class="btn-address" onclick="editAddress(3)">Edit</button>
                        <button class="btn-address secondary" onclick="deleteAddress(3)">Delete</button>
                        <button class="btn-address" onclick="setDefaultAddress(3)">Set as Default</button>
                    </div>
                </div>
            </div>

            <!-- Empty State (show when no addresses) -->
            <div id="emptyState" class="empty-state" style="display: none;">
                <i class="fa fa-map-marker"></i>
                <h3>No addresses found</h3>
                <p>Add your first address to get started with faster checkout.</p>
            </div>
        </div>
    </div>
</div>

<script>
    // Modal functionality
    function openAddressModal() {
        document.getElementById('addressModal').style.display = 'block';
        // Close dropdown when modal opens
        document.querySelector('.profile-dropdown').blur();
    }

    function closeAddressModal() {
        document.getElementById('addressModal').style.display = 'none';
    }

    // Close modal when clicking outside
    window.onclick = function(event) {
        var modal = document.getElementById('addressModal');
        if (event.target == modal) {
            closeAddressModal();
        }
    }

    // Close modal when clicking X
    document.querySelector('.close').onclick = function() {
        closeAddressModal();
    }

    // Address management functions
    function addNewAddress() {
        alert('Add new address functionality - redirect to add address form');
        // You can redirect to add address page or open another modal
        // window.location.href = 'add-address.jsp';
    }

    function editAddress(addressId) {
        alert('Edit address ID: ' + addressId);
        // Implement edit functionality
        // window.location.href = 'edit-address.jsp?id=' + addressId;
    }

    function deleteAddress(addressId) {
        if (confirm('Are you sure you want to delete this address?')) {
            alert('Delete address ID: ' + addressId);
            // Implement delete functionality
            // You can make AJAX call to delete address
        }
    }

    function setDefaultAddress(addressId) {
        alert('Set default address ID: ' + addressId);
        // Implement set default functionality
        // Make AJAX call to set default address
        // Then refresh the modal content
    }

    // Close dropdown when clicking elsewhere
    document.addEventListener('click', function(event) {
        var dropdown = document.querySelector('.profile-dropdown');
        if (!dropdown.contains(event.target)) {
            dropdown.blur();
        }
    });
</script>