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
    <title>Login | Natural Care</title>
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
    <div class="row">
        <div class="col-sm-offset-3 col-sm-6">
            <!--login form-->
            <div class="login-form">
                <h2>Login to your account</h2>
                <form action="${pageContext.request.contextPath}/login" method="POST" id="loginForm">
                    Username: <input name="username" type="text" placeholder="Username" required/>
                    <div class="error-message-red" id="usernameError"></div>
                    Password: <input name="password" type="password" placeholder="Password" required/>
                    <div class="error-message-red" id="passwordError"></div>
                    <span>
                        <input name="remember-account" type="checkbox" class="checkbox" checked value="on">
								Keep me signed in
                    </span>
                    <c:if test="${not empty error}">
                        <span style="color: #a94442; background-color: #f2dede; border: 1px  #ebccd1; padding: 8px 15px; border-radius: 4px; display: table; margin-top: 10px;">
                            <strong>${error}</strong>
                        </span>
                    </c:if>

                    <div style="display: flex; gap: 10px; margin-top: 10px;">
                        <button type="button" onclick="validateLoginForm()" class="btn btn-default">Login</button>
                        <button type="button" class="btn btn-default"
                                onclick="window.location.href='${pageContext.request.contextPath}/forgot'">Forgot
                            Password
                        </button>
                    </div>

                    <hr>
                    <p class="message">Don’t have an account? <a href="${pageContext.request.contextPath}/register">Register</a>
                    </p>

                </form>
            </div>
            <!--/login form-->
        </div>
    </div>
</div>


<!--/Footer-->
<jsp:include page="../common/footer.jsp"></jsp:include>


<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/price-range.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/validate.js"></script>
</body>
</html>
