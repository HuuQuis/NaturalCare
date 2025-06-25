<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Reset Password | Natural Care</title>
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

</head>

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
                <div class="login-form">
                    <h2>Reset Password</h2>
                    <form action="${pageContext.request.contextPath}/reset" method="POST">
                        <input type="hidden" name="token" value="${token}">
                        New password:
                        <input name="password" type="password" placeholder="Enter new password" required/>
                        <div class="error-message" id="passwordError"></div>
                        Confirm new password:
                        <input name="confirmPassword" type="password" placeholder="Confirm new password" required/>
                        <div class="error-message" id="confirmError"></div>

                        <br>
                        <c:if test="${not empty message}">
                        <span style="color: #a94442; background-color: #f2dede; border: 1px solid #ebccd1; padding: 8px 15px; border-radius: 4px; display: table;">
                            <strong>${error}</strong>
                        </span>
                        </c:if>
                        <c:if test="${not empty error}">
                        <span style="color: #a94442; background-color: #f2dede; border: 1px solid #ebccd1; padding: 8px 15px; border-radius: 4px; display: table;">
                            <strong>${error}</strong>
                        </span>
                        </c:if>

                        <div style="display: flex; gap: 10px; margin-top: 10px;">
                            <button type="submit" class="btn btn-default">Reset Password</button>
                            <button type="button" class="btn btn-default"
                                    onclick="window.location.href='${pageContext.request.contextPath}/login'">Back to
                                Login
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../common/footer.jsp"></jsp:include>

<script>
    document.querySelector('form')?.addEventListener('submit', function (event) {
        event.preventDefault(); // Tạm thời chặn để kiểm tra validate

        const password = document.querySelector('input[name="password"]').value.trim();
        const confirmPassword = document.querySelector('input[name="confirmPassword"]').value.trim();

        let hasError = false;

        // Xóa lỗi cũ
        document.getElementById('passwordError').textContent = '';
        document.getElementById('confirmError').textContent = '';

        // Kiểm tra mật khẩu rỗng hoặc toàn dấu cách
        if (password === '') {
            document.getElementById('passwordError').textContent = 'Please enter a valid password.';
            hasError = true;
        } else if (password.length < 6) {
            document.getElementById('passwordError').textContent = 'Password must be at least 6 characters.';
            hasError = true;
        }

        // Kiểm tra xác nhận mật khẩu
        if (confirmPassword === '') {
            document.getElementById('confirmError').textContent = 'Please confirm your password.';
            hasError = true;
        } else if (password !== confirmPassword) {
            document.getElementById('confirmError').textContent = 'Passwords do not match!';
            hasError = true;
        }

        if (!hasError) {
            this.submit(); // Submit form nếu không có lỗi
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