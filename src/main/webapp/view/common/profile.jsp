<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.User" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Thông tin tài khoản</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet"/>
  <link href="${pageContext.request.contextPath}/css/profile.css" rel="stylesheet"/>
</head>
<body>
<jsp:include page="/view/common/header-top.jsp"/>
<div class="row">
  <div class="col-sm-4">
    <div class="logo pull-left">
      <a href=${pageContext.request.contextPath}/home><img
              src="https://naturalcare.vercel.app/naturalcare/NLC-Logo.png" alt=""
              style="max-height: 70px"/></a>
    </div>
  </div>
</div>
<jsp:include page="/view/common/header-bottom.jsp"/>

<div class="container mt-5 mb-5">
  <div class="row">
    <!-- Sidebar trái -->
    <div class="col-md-3">
      <div class="card p-3 shadow-sm">
        <h5>Xin chào,<br><strong>${user.fullName}</strong></h5>
        <hr>
        <ul class="list-unstyled">
          <li><i class="fa fa-user me-2 text-warning"></i> <a href="#">Thông tin cá nhân</a></li>
          <li><i class="fa fa-file-text-o me-2 text-primary"></i> <a href="#">Danh sách đơn hàng</a></li>
          <li><i class="fa fa-sign-out me-2 text-danger"></i> <a href="logout">Thoát</a></li>
        </ul>
      </div>
    </div>

    <!-- Form thông tin tài khoản -->
    <div class="col-md-9">
      <div class="card p-4 shadow-sm">
        <h4 class="mb-4">Thông tin tài khoản</h4>
        <form method="post" action="updateProfile">
          <div class="mb-3">
            <label>Họ Tên *</label>
            <input type="text" name="fullName" class="form-control" value="${user.fullName}" required>
          </div>
          <div class="mb-3">
            <label>Số điện thoại *</label>
            <input type="text" name="phone" class="form-control" value="${user.phone}" required>
          </div>
          <div class="mb-3">
            <label>Email</label>
            <input type="email" class="form-control" value="${user.email}" readonly>
          </div>
          <div class="mb-3">
            <label>Địa chỉ</label>
            <input type="text" name="address" class="form-control" value="${user.address}">
          </div>


          <div class="row mb-3">
            <div class="col-md-4">
              <label>Tỉnh/Thành</label>
              <select class="form-control" name="province">
                <option>Chọn Tỉnh...</option>
              </select>
            </div>
            <div class="col-md-4">
              <label>Quận/Huyện</label>
              <select class="form-control" name="district">
                <option>Chọn Quận...</option>
              </select>
            </div>
            <div class="col-md-4">
              <label>Phường/Xã</label>
              <select class="form-control" name="ward">
                <option>Chọn Phường...</option>
              </select>
            </div>
          </div>

          <div class="mb-4">
            <label>Giới tính</label><br>
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="gender" value="Nam" ${user.gender == 'Nam' ? 'checked' : ''}>
              <label class="form-check-label">Nam</label>
            </div>
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="gender" value="Nữ" ${user.gender == 'Nữ' ? 'checked' : ''}>
              <label class="form-check-label">Nữ</label>
            </div>
          </div>

          <a href="" class="text-decoration-underline text-success">Thay Đổi Mật Khẩu</a>
          <br><br>
          <button type="submit" class="btn btn-success w-100">Cập nhật tài khoản</button>
        </form>
      </div>
    </div>
  </div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
