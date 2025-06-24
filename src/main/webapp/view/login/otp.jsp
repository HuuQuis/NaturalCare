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
<html>
<head>
    <title>Enter OTP</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="col-sm-offset-3 col-sm-6">
        <div class="login-form">
            <h2>Enter OTP</h2>
            <form action="register" method="POST">
                <input type="text" name="otp" placeholder="Enter OTP" required class="form-control" style="margin-bottom: 10px;"/>
                <button type="submit" class="btn btn-default">Verify</button>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" style="margin-top:10px">${error}</div>
                </c:if>
            </form>
        </div>
    </div>
</div>
</body>
</html>
