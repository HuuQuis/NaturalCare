<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addAddressModal" class="modal-address">
  <div class="modal-content">
    <div class="modal-header">
      <span class="close" onclick="closeAddAddressModal()">&times;</span>
      <h2><i class="fa fa-plus"></i> Add New Address</h2>
      <button type="button" class="close" onclick="closeAddAddressModal()">×</button>
    </div>
    <div id="messageContainer"></div>
    <div class="modal-body">
      <form id="addAddressForm">
        <div class="form-group user_info">
          <label for="firstName">First Name</label>
          <input type="text" id="firstName" name="firstName" placeholder="e.g. John" required>
        </div>
        <div class="form-group user_info">
          <label for="lastName">Last Name</label>
          <input type="text" id="lastName" name="lastName" placeholder="e.g. Doe" required />
        </div>
        <div class="form-group user_info">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" placeholder="e.g. john.doe@example.com" required />
        </div>
        <div class="form-group user_info">
          <label for="phoneNumber">Phone Number</label>
          <input type="text" id="phoneNumber" name="phoneNumber" placeholder="e.g. 0123456789" required maxlength="10" />
        </div>
        <div class="form-group">
          <label for="provinceSelect">Province</label>
          <select id="provinceSelect" name="provinceCode" required></select>
        </div>
        <div class="form-group">
          <label for="districtSelect">District</label>
          <select id="districtSelect" name="districtCode" required></select>
        </div>
        <div class="form-group">
          <label for="wardSelect">Ward</label>
          <select id="wardSelect" name="wardCode" required></select>
        </div>
        <div class="form-group">
          <label for="detail">Detail Address</label>
          <textarea id="detail" name="detail" rows="3" placeholder="e.g. 123 Street, Block 4" required></textarea>
        </div>
        <button type="submit" class="btn-address">Save</button>
      </form>
    </div>
  </div>
</div>

