<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Order Management | Nature Care</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/view/common/header-top.jsp" />
<jsp:include page="/view/common/header-middle.jsp" />

<div class="container mt-5">

  <!-- Kiểm tra user trong session -->
  <c:choose>
    <c:when test="${not empty sessionScope.user}">
      <h2>Welcome, ${sessionScope.user.username}!</h2>
      <p>Your user ID: ${sessionScope.user.id}</p>

      <button class="btn btn-primary">
        <a href="/NaturalCare/OrderHistory?userId=${sessionScope.user.id}" style="color: white; text-decoration: none;">
          Go to Order History
        </a>
      </button>
    </c:when>
    <c:otherwise>
      <div class="alert alert-warning">
        You are not logged in. <a href="login">Click here to login</a>
      </div>
    </c:otherwise>
  </c:choose>

</div>

<jsp:include page="/view/common/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
