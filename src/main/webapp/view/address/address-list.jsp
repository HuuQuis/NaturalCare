<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addressModal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <span class="close" onclick="closeAddressModal()">&times;</span>
      <h2><i class="fa fa-map-marker"></i> My Addresses</h2>
    </div>
    <div class="modal-body">
      <div id="messageContainer"></div>
      <div class="add-address-btn" onclick="addNewAddress()">
        <i class="fa fa-plus"></i> Add New Address
      </div>
      <div id="loadingState" class="loading" style="display: none;">
        <i class="fa fa-spinner fa-spin"></i> Loading addresses...
      </div>
      <div id="addressListContainer"></div>
      <div id="emptyState" class="empty-state" style="display: none;">
        <i class="fa fa-map-marker"></i>
        <h3>No addresses were found</h3>
        <p>Add your first address to get started with faster checkout.</p>
      </div>
    </div>
  </div>
</div>
