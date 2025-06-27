<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.OrderDetailItem" %>
<%
    List<OrderDetailItem> orderDetails = (List<OrderDetailItem>) request.getAttribute("orderDetails");
%>
<html>
<head>
    <title>Order Details</title>
    
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <style>
        /* Shopee-inspired styling - only affects this page */
        body.order-detail-page {
            font-family: 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
            color: #333;
        }
        
        .order-detail-container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 0 15px;
        }
        
        .order-status-tabs {
            display: flex;
            background: white;
            border-radius: 4px;
            box-shadow: 0 1px 2px rgba(0,0,0,0.1);
            margin-bottom: 15px;
            overflow: hidden;
        }
        
        .order-status-tab {
            padding: 15px 20px;
            cursor: pointer;
            font-size: 14px;
            color: #555;
            border-bottom: 2px solid transparent;
        }
        
        .order-status-tab.active {
            color: #ee4d2d;
            border-bottom-color: #ee4d2d;
            font-weight: 500;
        }
        
        .search-box {
            background: white;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 4px;
            box-shadow: 0 1px 2px rgba(0,0,0,0.1);
        }
        
        .search-box input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        
        .order-card {
            background: white;
            border-radius: 4px;
            box-shadow: 0 1px 2px rgba(0,0,0,0.1);
            margin-bottom: 15px;
            overflow: hidden;
        }
        
        .store-header {
            display: flex;
            align-items: center;
            padding: 15px;
            border-bottom: 1px solid #f1f1f1;
        }
        
        .store-name {
            font-weight: 500;
            color: #ee4d2d;
            margin-left: 10px;
        }
        
        .store-actions {
            margin-left: auto;
            display: flex;
            gap: 15px;
        }
        
        .store-action-btn {
            color: #555;
            font-size: 13px;
            cursor: pointer;
        }
        
        .order-status {
            background: #f1f1f1;
            color: #555;
            padding: 3px 8px;
            border-radius: 4px;
            font-size: 12px;
            margin-left: 10px;
        }
        
        .order-item {
            display: flex;
            padding: 15px;
            border-bottom: 1px solid #f1f1f1;
        }
        
        .product-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 4px;
            border: 1px solid #f1f1f1;
            margin-right: 15px;
        }
        
        .product-info {
            flex: 1;
        }
        
        .product-name {
            font-size: 14px;
            margin-bottom: 5px;
        }
        
        .product-variant {
            font-size: 12px;
            color: #888;
            margin-bottom: 5px;
        }
        
        .product-quantity {
            font-size: 13px;
            color: #888;
        }
        
        .product-price {
            font-size: 14px;
            color: #ee4d2d;
            font-weight: 500;
            min-width: 100px;
            text-align: right;
        }
        
        .order-summary {
            padding: 15px;
            text-align: right;
            font-size: 14px;
        }
        
        .order-total {
            font-weight: 500;
            color: #ee4d2d;
        }
        
        /* New refund/return section */
        .order-actions {
            display: flex;
            justify-content: flex-end;
            padding: 10px 15px;
            border-top: 1px solid #f1f1f1;
            background: #fafafa;
        }
        
        .refund-btn {
            background: white;
            color: #ee4d2d;
            border: 1px solid #ee4d2d;
            padding: 8px 15px;
            border-radius: 4px;
            font-size: 13px;
            cursor: pointer;
            margin-left: 10px;
        }
        
        .refund-btn:hover {
            background: #ffeee8;
        }
        
        .refund-status {
            color: #ee4d2d;
            font-size: 13px;
            padding: 8px 0;
        }
    </style>
</head>
<body>
    
<jsp:include page="/view/common/header-top.jsp" />
<jsp:include page="/view/common/header-middle.jsp" />
<div class="order-detail-page">
    <div class="order-detail-container">
        <div class="order-status-tabs">
            <div class="order-status-tab active">All</div>
            <div class="order-status-tab">Awaiting Payment</div>
            <div class="order-status-tab">Shipping</div>
            <div class="order-status-tab">Awaiting Delivery</div>
            <div class="order-status-tab">Completed</div>
            <div class="order-status-tab">Cancelled</div>
            <div class="order-status-tab">Return/Refund</div>
        </div>
        
        <div class="search-box">
            <input type="text" placeholder="Search by Shop Name, Order ID or Product Name">
        </div>
        
        <% for (OrderDetailItem item : orderDetails) { %>
        <div class="order-card">
            <div class="order-item">
                <img src="<%= request.getContextPath() + item.getProductImage() %>" class="product-image">
                <div class="product-info">
                    <div class="product-name"><%= item.getProductName() %></div>
                    <% if (item.getColorName() != null || item.getSizeName() != null) { %>
                    <div class="product-variant">
                        Variant: 
                        <%= item.getColorName() != null ? item.getColorName() : "" %>
                        <%= item.getSizeName() != null ? " - " + item.getSizeName() : "" %>
                    </div>
                    <% } %>
                    <div class="product-quantity">x<%= item.getQuantity() %></div>
                </div>
                <div class="product-price">$<%= item.getTotalPrice() %></div>
            </div>
            
            <div class="order-summary">
                Total Amount: <span class="order-total">$<%= item.getTotalPrice() %></span>
            </div>
            
            <!-- New refund/return section -->
            <div class="order-actions">
                    <button class="refund-btn">Request Refund</button>
            </div>
        </div>
        <% } %>
    </div>
</div>
    
<jsp:include page="/view/common/footer.jsp" />
</body>
</html>