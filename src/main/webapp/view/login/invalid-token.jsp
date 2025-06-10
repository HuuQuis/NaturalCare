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
<header id="header">
    <jsp:include page="../common/header-top.jsp"></jsp:include>
    <jsp:include page="../common/header-middle.jsp"></jsp:include>
    <jsp:include page="../common/header-bottom.jsp"></jsp:include>
</header>

<section id="form">
    <div class="container">
        <div class="row">
            <div class="col-sm-offset-3 col-sm-6">
                <div class="signup-form">
                    <h2>Invalid or Expired Token</h2>
                    <div class="alert alert-warning">
                        <p>The password reset link you used is either invalid or has expired.</p>
                        <p>This could happen if:</p>
                        <ul>
                            <li>The link has expired (links are valid for 24 hours)</li>
                            <li>The link has already been used</li>
                            <li>The link is invalid or corrupted</li>
                        </ul>
                    </div>

                    <div style="display: flex; gap: 10px; margin-top: 20px;">
                        <form action="${pageContext.request.contextPath}/forgot" method="get">
                            <button type="submit" class="btn btn-default">Request New Reset Link</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/login" method="get">
                            <button type="submit" class="btn btn-default">Back to Login</button>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../common/footer.jsp"></jsp:include>

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/price-range.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html> 