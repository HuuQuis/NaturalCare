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
        .error-message {
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
                        <div class="error-message" id="usernameError"></div>

                        Password:<input type="password" name="password" id="password" placeholder="Password" required
                                        minlength="6"/>
                        <div class="error-message" id="passwordError"></div>

                        Confirm Password:<input type="password" name="password-confirm" id="confirmPassword"
                                                placeholder="Re-enter your password" required/>
                        <div class="error-message" id="confirmPasswordError"></div>
                    </div>
                    <!-- Cột phải -->
                    <div class="col-sm-6">
                        First Name:<input type="text" name="firstName" id="firstName" placeholder="First Name" required
                                          value="${param.firstName}"/>
                        <div class="error-message" id="firstNameError"></div>

                        Last Name:<input type="text" name="lastName" id="lastName" placeholder="Last Name" required
                                         value="${param.lastName}"/>
                        <div class="error-message" id="lastNameError"></div>

                        Email:<input type="email" name="email" id="email" placeholder="Email Address" required
                                     value="${param.email}"/>
                        <div class="error-message" id="emailError"></div>
                    </div>
                </div>
                Phone:<input type="text" name="phone" id="phone" placeholder="Phone Number" required
                             value="${param.phone}"/>
                <div class="error-message" id="phoneError"></div>

                <c:if test="${not empty error}">
                    <span style="color: #a94442; background-color: #f2dede; border: 1px solid #ebccd1; padding: 8px 15px; border-radius: 4px; display: table;">
                            <strong>${error}</strong>
                    </span>
                </c:if>

                <button style="margin-top: 20px" type="submit" class="btn btn-default">Signup</button>

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
    document.querySelector('form')?.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent form submission to handle validation


        const inputs = {
            username: document.querySelector('input[name="username"]'),
            password: document.querySelector('input[name="password"]'),
            confirmPassword: document.querySelector('input[name="password-confirm"]'),
            firstName: document.querySelector('input[name="firstName"]'),
            lastName: document.querySelector('input[name="lastName"]'),
            email: document.querySelector('input[name="email"]'),
            phone: document.querySelector('input[name="phone"]'),
        };

        const errors = {
            username: document.querySelector('#usernameError'),
            password: document.querySelector('#passwordError'),
            confirmPassword: document.querySelector('#confirmPasswordError'),
            firstName: document.querySelector('#firstNameError'),
            lastName: document.querySelector('#lastNameError'),
            email: document.querySelector('#emailError'),
            phone: document.querySelector('#phoneError'),
        };

        for (const [key, input] of Object.entries(inputs)) {
            if (!input || !errors[key]) {
                console.error(`${key} input or error element not found!`);
                return;
            }
        }

        Object.values(errors).forEach(error => error.textContent = '');

        let hasError = false;
        const errorMessages = [];

        for (const [key, input] of Object.entries(inputs)) {
            if (input.value.trim() === '') {
                errorMessages.push({field: errors[key], message: `${key} cannot be empty!`});
                hasError = true;
            }
        }

        // Check for whitespaces in username and password
        if (/\s/.test(inputs.username.value)) {
            errorMessages.push({field: errors.username, message: 'Username cannot contain spaces!'});
            hasError = true;
        }
        if (/\s/.test(inputs.password.value)) {
            errorMessages.push({field: errors.password, message: 'Password cannot contain spaces!'});
            hasError = true;
        }

        // Check for minimum length of username and password
        if (inputs.password.value.length < 6) {
            errorMessages.push({field: errors.password, message: 'Password must be at least 6 characters long!'});
            hasError = true;
        }

        // Check for minimum length of first and last names
        if (inputs.firstName.value.length < 2) {
            errorMessages.push({field: errors.firstName, message: 'First Name must be at least 2 characters long!'});
            hasError = true;
        }
        if (inputs.lastName.value.length < 2) {
            errorMessages.push({field: errors.lastName, message: 'Last Name must be at least 2 characters long!'});
            hasError = true;
        }

        // Check if passwords match
        if (inputs.password.value !== inputs.confirmPassword.value) {
            errorMessages.push({field: errors.confirmPassword, message: 'Passwords do not match!'});
            hasError = true;
        }

        // Validate email and phone formats
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailPattern.test(inputs.email.value)) {
            errorMessages.push({field: errors.email, message: 'Please enter a valid email address!'});
            hasError = true;
        }
        const phonePattern = /^0\d{9}$/;
        if (!phonePattern.test(inputs.phone.value)) {
            errorMessages.push({field: errors.phone, message: 'Phone number must start with 0 and be 10 digits long!'});
            hasError = true;
        }

        if (hasError) {
            errorMessages.forEach(error => error.field.textContent = error.message);
        } else {
            this.submit();
        }
    });
</script>

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/price-range.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
