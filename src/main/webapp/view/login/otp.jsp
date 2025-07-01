<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 6/17/2025
  Time: 11:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>OTP Verification | Natural Care</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
</head><!--/head-->
<body>
<header id="header">
    <jsp:include page="../common/header-top.jsp"></jsp:include>
    <jsp:include page="../common/header-middle.jsp"></jsp:include>
    <jsp:include page="../common/header-bottom.jsp"></jsp:include>
</header>

<div class="container">
    <div class="col-sm-offset-3 col-sm-6">
        <div class="login-form">
            <h2>Enter OTP</h2>
            <form action="register" method="POST">
                <input type="text" name="otp" placeholder="Enter OTP" required class="form-control"
                       style="margin-bottom: 10px;"/>
                <div style="display: flex; gap: 10px; margin-bottom: 10px;">
                    <button type="submit" class="btn btn-default">Verify</button>
                </div>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" style="margin-top:10px">${error}</div>
                </c:if>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"></jsp:include>

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/price-range.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
