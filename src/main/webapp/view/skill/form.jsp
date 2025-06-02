<%--
  Created by IntelliJ IDEA.
  User: telamon
  Date: 26/5/25
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    model.Skill skill = (model.Skill) request.getAttribute("skill");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${skill != null && skill.skillId != 0 ? "Update Skill" : "Add Skill"} | Natural Care</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet" />
</head>
<body>
<header id="header">
    <jsp:include page="/view/common/header-top.jsp" />
    <jsp:include page="/view/common/header-middle.jsp" />
    <jsp:include page="/view/common/header-bottom.jsp" />
</header>

<section class="container" style="margin-top: 30px; max-width: 600px;">
    <h2>${skill != null && skill.skillId != 0 ? "Update Skill" : "Add Skill"}</h2>
    <form method="post" action="skill">
        <input type="hidden" name="skill_id" value="${skill != null ? skill.skillId : 0}" />
        <div class="form-group">
            <label for="skill_name">Skill Name:</label>
            <input type="text" id="skill_name" name="skill_name" class="form-control" value="${skill != null ? skill.skillName : ''}" required />
        </div>
        <c:if test="${not empty error}">
            <p class="text-danger">${error}</p>
        </c:if>
        <button type="submit" class="btn btn-primary">Submit</button>
        <a href="skill" class="btn btn-secondary">Back to list</a>
    </form>
</section>

<jsp:include page="/view/common/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
