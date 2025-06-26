<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 5/27/2025
  Time: 12:55 PM
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
    <title>Register | Natural Care</title>
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

    <style>
        .error-message-red {
            color: #a94442;
            padding: 5px;
            border-radius: 4px;
            margin-top: 5px;
            display: block;
        }
    </style>

</head><!--/head-->

<body>
<header id="header"><!--header-->
    <!--/header_top-->
    <jsp:include page="../common/header-top.jsp"></jsp:include>

    <!--/header-middle-->
    <jsp:include page="../common/header-middle.jsp"></jsp:include>

    <!--/header-bottom-->
    <jsp:include page="../common/header-bottom.jsp"></jsp:include>
</header><!--/header-->

<!--form-->
<div class="container">
    <div class="col-sm-offset-3 col-sm-6">
        <!--sign up form-->
        <div class="signup-form">
            <h2>New User Signup!</h2>
            <form action="register" method="POST" id="registerForm">
                <div class="row">
                    <!-- Cột trái -->
                    <div class="col-sm-6">
                        Username:<input type="text" name="username" id="username" placeholder="Username" required
                                        minlength="6"/>
                        <div class="error-message-red" id="usernameError"></div>

                        Password:<input type="password" name="password" id="password" placeholder="Password" required/>
                        <div class="error-message-red" id="passwordError"></div>

                        Confirm Password:<input type="password" name="password-confirm" id="confirmPassword"
                                                placeholder="Re-enter your password" required/>
                        <div class="error-message-red" id="confirmPasswordError"></div>
                    </div>
                    <!-- Cột phải -->
                    <div class="col-sm-6">
                        First Name:<input type="text" name="firstName" id="firstName" placeholder="First Name" required
                                          value="${param.firstName}"/>
                        <div class="error-message-red" id="firstNameError"></div>

                        Last Name:<input type="text" name="lastName" id="lastName" placeholder="Last Name" required
                                         value="${param.lastName}"/>
                        <div class="error-message-red" id="lastNameError"></div>

                        Email:<input type="email" name="email" id="email" placeholder="Email Address" required
                                     value="${param.email}"/>
                        <div class="error-message-red" id="emailError"></div>
                    </div>
                </div>
                Phone:<input type="text" name="phone" id="phone" placeholder="Phone Number" required
                             value="${param.phone}"/>
                <div class="error-message-red" id="phoneError"></div>

                <c:if test="${not empty error}">
                    <span style="color: #a94442; background-color: #f2dede; border: 1px solid #ebccd1; padding: 8px 15px; border-radius: 4px; display: table;">
                            <strong>${error}</strong>
                    </span>
                </c:if>

                <button style="margin-top: 20px" type="button" onclick="validateForm()" class="btn btn-default">Signup</button>

                <hr>
                <p class="message">Already registered? <a
                        href="${pageContext.request.contextPath}/login">Login</a>
                </p>

            </form>
        </div>
        <!--/sign up form-->
    </div>
</div>


<!--/Footer-->
<jsp:include page="../common/footer.jsp"></jsp:include>

<!--/Footer-->
<script>
    // Main validation function for this form
    function validateForm() {
        return validateRegistrationForm('registerForm');
    }

    // Optional: Enable real-time validation
    // Uncomment the line below to enable validation on blur events
    // document.addEventListener('DOMContentLoaded', function() {
    //     enableRealTimeValidation('registerForm');
    // });
</script>


<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/price-range.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/validate.js"></script>
</body>
</html>
