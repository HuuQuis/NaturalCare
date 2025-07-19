<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Payment Canceled</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
  <style>
    .cancel-container {
      text-align: center;
      padding: 100px 20px;
    }
    .cancel-container h2 {
      color: #d9534f;
      margin-bottom: 20px;
    }
    .cancel-container p {
      font-size: 18px;
      color: #555;
    }
    .countdown {
      font-weight: bold;
      color: #5bc0de;
    }
  </style>
  <script>
    let seconds = 5;
    function updateCountdown() {
      if (seconds > 0) {
        document.getElementById("countdown").innerText = seconds;
        seconds--;
      } else {
        window.location.href = '${pageContext.request.contextPath}/home';
      }
    }
    setInterval(updateCountdown, 1000);
  </script>

</head>
<body>
<header id="header">
  <jsp:include page="/view/common/header-top.jsp" />
  <jsp:include page="/view/common/header-middle.jsp" />
  <jsp:include page="/view/common/header-bottom.jsp" />
</header>

<section class="cancel-container">
  <h2>Payment was canceled or failed</h2>
  <p>You will be redirected to the checkout page in <span id="countdown" class="countdown">5</span> seconds.</p>
  <p>If you are not redirected, <a href="${pageContext.request.contextPath}/home">Click here</a>.</p>
</section>

<jsp:include page="/view/common/footer.jsp" />
</body>
</html>
