<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addAddressModal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <span class="close" onclick="closeAddAddressModal()">&times;</span>
      <h2><i class="fa fa-plus"></i> Add New Address</h2>
    </div>
    <div class="modal-body">
      <form id="addAddressForm">
        <div class="form-group">
          <label>Province</label>
          <select id="provinceSelect" name="provinceCode" required></select>
        </div>
        <div class="form-group">
          <label>District</label>
          <select id="districtSelect" name="districtCode" required></select>
        </div>
        <div class="form-group">
          <label>Ward</label>
          <select id="wardSelect" name="wardCode" required></select>
        </div>
        <div class="form-group">
          <label>Detail Address</label>
          <input type="text" name="detail" placeholder="e.g. 123 Street, Block 4" required />
        </div>
        <button type="submit" class="btn-address">Save</button>
      </form>
    </div>
  </div>
</div>
