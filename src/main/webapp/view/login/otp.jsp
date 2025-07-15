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
            <p style="color: #696763; margin-bottom: 20px;">We've sent a verification code to your email address.</p>
            
            <form action="register" method="POST">
                <input type="text" name="otp" placeholder="Enter OTP" required class="form-control"
                       style="margin-bottom: 10px;" maxlength="6" pattern="[0-9]{6}"
                       title="Please enter a 6-digit OTP"/>
                <div style="display: flex; gap: 10px; margin-bottom: 10px;">
                    <button type="submit" class="btn btn-default">Verify</button>
                </div>
            </form>
            
            <!-- Resend OTP Form -->
            <form action="${pageContext.request.contextPath}/resend-otp" method="POST" style="margin-top: 15px; padding-top: 15px; border-top: 1px solid #F0F0E9;">
                <p style="color: #696763; margin-bottom: 10px; text-align: center;">
                    Didn't receive email? 
                    <button type="submit" class="btn btn-link" style="padding: 0; color: #FE980F; text-decoration: underline;">
                        Resend OTP
                    </button>
                </p>
            </form>
            
            <!-- Success Message -->
            <c:if test="${not empty message}">
                <span style="color: #155724; background-color: #d4edda; border: 1px solid #c3e6cb; padding: 8px 15px; border-radius: 4px; display: table; margin-top: 10px;">
                    <strong>${message}</strong>
                </span>
            </c:if>
            
            <!-- Error Message -->
            <c:if test="${not empty error}">
                <span style="color: #a94442; background-color: #f2dede; border: 1px solid #ebccd1; padding: 8px 15px; border-radius: 4px; display: table; margin-top: 10px;">
                    <strong>${error}</strong>
                </span>
            </c:if>
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

<script>
// Simple enhancement - auto focus on OTP input and number-only validation
document.addEventListener('DOMContentLoaded', function() {
    const otpInput = document.querySelector('input[name="otp"]');
    if (otpInput) {
        otpInput.focus();
        
        // Only allow numbers in OTP input
        otpInput.addEventListener('input', function(e) {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    }
});
</script>
</body>
</html>
