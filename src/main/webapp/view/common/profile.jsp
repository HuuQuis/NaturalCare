<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Account | NaturalCare</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Profile.css">
</head>
<body>

<jsp:include page="/view/common/header-top.jsp" />
<jsp:include page="/view/common/header-middle.jsp" />
<c:if test="${sessionScope.user.role == 1}">
  <jsp:include page="/view/common/header-bottom.jsp" />
</c:if>

<div class="container profile-wrapper">
  <div class="row">
    <!-- Sidebar -->
    <div class="col-md-4 sidebar-left pe-4">
      <p class="mb-3">Hello, <strong>${sessionScope.user.firstName}</strong></p>
      <ul>
        <li><a class="nav-link" href="${pageContext.request.contextPath}/profile">Personal Information</a></li>

        <c:if test="${sessionScope.user.role == 1}">
          <li><a class="nav-link" href="${pageContext.request.contextPath}/recharge-history">Top-up History</a></li>
          <li><a class="nav-link" href="${pageContext.request.contextPath}/order-history">Order History</a></li>
          <li><a class="nav-link" href="${pageContext.request.contextPath}/purchase-history">Purchase History</a></li>
          <li><a class="nav-link" href="#">Send Feedback</a></li>
        </c:if>

<%--        <c:if test="${sessionScope.user.role != 1}">--%>
<%--          <li><a class="nav-link" href="${pageContext.request.contextPath}/user-manage">User Management</a></li>--%>
<%--          <li><a class="nav-link" href="${pageContext.request.contextPath}/blog-manage">Blog Management</a></li>--%>
<%--          <li><a class="nav-link" href="${pageContext.request.contextPath}/login">Product Management</a></li>--%>
<%--        </c:if>--%>

        <li><a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a></li>
      </ul>
    </div>

    <!-- Form Content -->
    <div class="col-md-8 ps-4">
      <div class="form-section-title">Account Information</div>

      <!-- Alert Message -->
      <c:if test="${not empty message}">
        <div id="alertBox" class="alert alert-success text-center" role="alert">${message}</div>
      </c:if>
      <c:if test="${not empty error}">
        <div id="alertBox" class="alert alert-danger text-center" role="alert">${error}</div>
      </c:if>

      <form action="${pageContext.request.contextPath}/profile" method="post" id ="profileForm">
        <div class="row">
          <div class="col-md-6 mb-3">
            <label class="form-label">First Name</label>
            <input type="text" name="firstName" class="form-control editable-field" value="${user.firstName}" readonly>
          </div>
          <div class="col-md-6 mb-3">
            <label class="form-label">Last Name</label>
            <input type="text" name="lastName" class="form-control editable-field" value="${user.lastName}" readonly>
          </div>
        </div>

        <div class="mb-3">
          <label class="form-label">Phone Number</label>
          <input type="text" name="phone" class="form-control editable-field" value="${user.phone}" readonly>
        </div>

        <div class="mb-3">
          <label class="form-label">Email</label>
          <input type="email" name="email" class="form-control" value="${user.email}" readonly>
        </div>

        <div class="mb-4 text-center">
          <a href="reset" class="text-decoration-underline">Change Password</a>
        </div>

        <div class="text-center mt-5">
          <button type="button" id="editBtn" class="btn btn-outline-secondary me-2">Edit Info</button>
          <button type="submit" class="btn btn-update-profile" disabled>Update Account</button>
        </div>
      </form>
    </div>
  </div>
</div>

<c:if test="${sessionScope.user.role == 1}">
  <jsp:include page="/view/common/footer.jsp" />
</c:if>

<!-- Scripts -->
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

<script>
  // Enable edit
  document.getElementById('editBtn').addEventListener('click', function () {
    document.querySelectorAll('.editable-field').forEach(el => {
      el.removeAttribute('readonly');
      el.removeAttribute('disabled');
    });
    document.querySelector('.btn-update-profile').disabled = false;
    this.style.display = 'none';
  });

  // Hide alert after 3 seconds
  setTimeout(() => {
    const alertBox = document.getElementById("alertBox");
    if (alertBox) alertBox.style.display = "none";
  }, 3000);

  // Form validation
  document.getElementById('profileForm').addEventListener('submit', function (e) {
    const firstName = document.querySelector('input[name="firstName"]').value.trim();
    const lastName = document.querySelector('input[name="lastName"]').value.trim();
    const phone = document.querySelector('input[name="phone"]').value.trim();
    const email = document.querySelector('input[name="email"]').value.trim();

    const phoneRegex = /^[0-9]{9,12}$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    let errorMsg = "";
    if (!firstName || !lastName) {
      errorMsg = "First and Last name cannot be empty.";
    } else if (!phoneRegex.test(phone)) {
      errorMsg = "Invalid phone number.";
    } else if (!emailRegex.test(email)) {
      errorMsg = "Invalid email.";
    }

    if (errorMsg) {
      e.preventDefault();
      showTempAlert(errorMsg, "danger");
    }
  });

  // Custom alert display
  function showTempAlert(msg, type = "success") {
    const alert = document.createElement("div");
    alert.className = `alert alert-${type} text-center`;
    alert.role = "alert";
    alert.textContent = msg;

    const formSection = document.querySelector(".form-section-title");
    formSection.insertAdjacentElement("afterend", alert);

    setTimeout(() => alert.remove(), 3000);
  }
</script>

</body>
</html>
