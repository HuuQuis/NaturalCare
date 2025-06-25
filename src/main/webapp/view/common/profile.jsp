<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.User" %>

<%
  User user = (User) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }
%>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Profile</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="${pageContext.request.contextPath}/css/Profile.css" rel="stylesheet"/>
</head>
<body>


<div class="form-profile py-5">
  <div class="container">
    <div class="row profile-card">
      <!-- Cột trái: ảnh avatar -->
      <div class="col-lg-4">
        <div class="card mb-4 text-center">
          <div class="card-body">
            <c:choose>
              <c:when test="${not empty user.avatarUrl}">
                <img src="${user.avatarUrl}"
                     alt="avatar"
                     class="rounded-circle profile-img"/>
              </c:when>
              <c:otherwise>
                <img src="https://www.w3schools.com/howto/img_avatar.png"
                     alt="avatar"
                     class="rounded-circle profile-img"/>
              </c:otherwise>
            </c:choose>
            <h5 class="mt-3">${user.firstName} ${user.lastName}</h5>
          </div>
        </div>
      </div>

      <!-- Cột phải: thông tin -->
      <div class="col-lg-8">
        <div class="card mb-4">
          <div class="card-body">
            <div class="row mb-2">
              <div class="col-sm-3"><p>Email</p></div>
              <div class="col-sm-9"><p class="text-muted">${user.email}</p></div>
            </div>
            <hr/>
            <div class="row mb-2">
              <div class="col-sm-3"><p>Giới tính</p></div>
              <div class="col-sm-9">
                <p class="text-muted">
                  <c:choose>
                    <c:when test="${user.gender == 1}">Nam</c:when>
                    <c:when test="${user.gender == 2}">Nữ</c:when>
                    <c:otherwise>Không rõ</c:otherwise>
                  </c:choose>
                </p>
              </div>
            </div>
            <hr/>
            <div class="row mb-2">
              <div class="col-sm-3"><p>Số điện thoại</p></div>
              <div class="col-sm-9"><p class="text-muted">${user.phoneNumber}</p></div>
            </div>
            <hr/>
            <div class="row mb-2">
              <div class="col-sm-3"><p>Birth</p></div>
              <div class="col-sm-9"><p class="text-muted">${user.dateOfBirth}</p></div>
            </div>
            <hr/>
            <div class="row mb-2">
              <div class="col-sm-3"><p>Vai trò</p></div>
              <div class="col-sm-9">
                <p class="text-muted">
                  <c:choose>
                    <c:when test="${not empty user.role}">
                      ${user.role.name}
                    </c:when>
                    <c:otherwise>Khách</c:otherwise>
                  </c:choose>
                </p>
              </div>
            </div>

            <hr/>
            <a href="changePassword.jsp" class="btn btn-warning">Change Password</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<%--<jsp:include page="/view/common/footer.jsp"/>--%>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
