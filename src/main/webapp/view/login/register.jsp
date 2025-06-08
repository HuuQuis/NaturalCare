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
<section id="form">
    <div class="container">
        <div class="row">
            <div class="col-sm-offset-3 col-sm-6">
                <!--sign up form-->
                <div class="signup-form">
                    <h2>New User Signup!</h2>
                    <form action="register" method="POST">
                        <input type="text" name="username" placeholder="Username" required minlength="6"/>
                        <input type="password" name="password" placeholder="Password" required minlength="6"/>
                        <input type="password" name="password-confirm" placeholder="Re-enter your password" required/>
                        <input type="text" name="firstName" placeholder="First Name" required/>
                        <input type="text" name="lastName" placeholder="Last Name" required/>
                        <input type="email" name="email" placeholder="Email Address" required/>
                        <input type="text" name="phone" placeholder="Phone Number" pattern="0[0-9]{10}" required/>
                        <button type="submit" class="btn btn-default">Signup</button>
                        ${error}
                        <hr>
                        <p class="message">Already registered? <a href="${pageContext.request.contextPath}/login">Login</a></p>
                    </form>
                </div>
                <!--/sign up form-->
            </div>
        </div>
    </div>
</section><!--/form-->


<!--/Footer-->
<jsp:include page="../common/footer.jsp"></jsp:include>

</section><!--/Footer-->
<script>
    document.querySelector('form').addEventListener('submit', function (event) {
        const password = document.querySelector('input[name="password"]').value;
        const confirmPassword = document.querySelector('input[name="password-confirm"]').value;
        const username = document.querySelector('input[name="username"]').value;
        const firstName = document.querySelector('input[name="firstName"]').value;
        const lastName = document.querySelector('input[name="lastName"]').value;
        const email = document.querySelector('input[name="email"]').value;
        const phone = document.querySelector('input[name="phone"]').value;

        // Check if any field contains only whitespace
        if (username.trim() === '' || firstName.trim() === '' || lastName.trim() === '' || 
            email.trim() === '' || phone.trim() === '') {
            event.preventDefault();
            alert("Please do not enter only spaces in any field!");
            return;
        }

        if (password !== confirmPassword) {
            event.preventDefault();
            alert("Passwords do not match!");
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
