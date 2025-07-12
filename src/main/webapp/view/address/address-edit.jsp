<%@ page contentType="text/html;charset=UTF-8" %>

<div id="addAddressModal" class="modal-address" style="display: none;">
  <div class="modal-content">
    <div class="modal-header">
      <h5>Edit Address</h5>
      <button onclick="closeAddAddressModal()" class="modal-close">&times;</button>
    </div>
    <div class="modal-body">
      <div id="messageContainer"></div>
      <form id="addAddressForm" method="post" action="address?action=update">
        <input type="hidden" name="addressId" id="addressId"/>

        <div class="form-group user_info">
          <label for="firstName">First Name</label>
          <input type="text" name="firstName" id="firstName" class="form-control" placeholder="e.g. John" required />
        </div>

        <div class="form-group user_info">
          <label for="lastName">Last Name</label>
          <input type="text" name="lastName" id="lastName" class="form-control" placeholder="e.g. Doe" required />
        </div>

        <div class="form-group user_info">
          <label for="email">Email</label>
          <input type="email" name="email" id="email" class="form-control" placeholder="e.g. john@example.com" required />
        </div>

        <div class="form-group user_info">
          <label for="phoneNumber">Phone Number</label>
          <input type="text" name="phoneNumber" id="phoneNumber" class="form-control" placeholder="10-digit number" maxlength="10" required />
        </div>

        <div class="form-group">
          <label for="provinceSelect">Province</label>
          <select name="provinceCode" id="provinceSelect" class="form-control" required></select>
        </div>

        <div class="form-group">
          <label for="districtSelect">District</label>
          <select name="districtCode" id="districtSelect" class="form-control" required></select>
        </div>

        <div class="form-group">
          <label for="wardSelect">Ward</label>
          <select name="wardCode" id="wardSelect" class="form-control" required></select>
        </div>

        <div class="form-group">
          <label for="detail">Detail</label>
          <input type="text" name="detail" id="detail" class="form-control" placeholder="Street, building, etc." required />
        </div>

        <div class="modal-footer">
          <button type="submit" class="btn btn-primary">Save</button>
          <button type="button" class="btn btn-secondary" onclick="closeAddAddressModal()">Cancel</button>
        </div>
      </form>
    </div>
  </div>
</div>