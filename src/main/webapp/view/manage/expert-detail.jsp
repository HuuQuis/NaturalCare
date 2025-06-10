<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Expert Detail</title>
    <style>
    * {
        box-sizing: border-box;
    }

    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: #f9fafb;
        margin: 0;
        padding: 40px 0;
        color: #333;
    }

    h2 {
        color: #2e7d32; /* Xanh lá đậm */
        margin-bottom: 25px;
        font-size: 28px;
        text-align: center;
    }

    .container {
        max-width: 600px;
        margin: 0 auto;
        background-color: #fff;
        padding: 35px 40px;
        border-radius: 12px;
        box-shadow: 0 6px 15px rgba(0, 0, 0, 0.08);
    }

    .message {
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
        padding: 12px 20px;
        border-radius: 6px;
        margin-bottom: 20px;
        font-weight: 600;
        text-align: center;
    }

    label {
        display: block;
        font-weight: 600;
        margin-bottom: 8px;
        color: #2e7d32;
    }

    p {
        font-size: 1rem;
        margin-bottom: 16px;
    }

    input[type="text"], select {
        width: 100%;
        padding: 10px 14px;
        border-radius: 6px;
        border: 1.5px solid #c8e6c9;
        font-size: 1rem;
        transition: border-color 0.3s ease;
    }

    input[type="text"]:focus, select:focus {
        outline: none;
        border-color: #43a047;
        box-shadow: 0 0 6px rgba(76, 175, 80, 0.4);
    }

    button {
        background-color: #43a047;
        border: none;
        color: white;
        font-weight: 700;
        padding: 12px 24px;
        border-radius: 8px;
        cursor: pointer;
        font-size: 1rem;
        transition: background-color 0.3s ease;
        margin-top: 10px;
    }

    button:hover {
        background-color: #2e7d32;
    }

    a {
        color: #388e3c;
        text-decoration: none;
        font-weight: 600;
        display: inline-block;
        margin-top: 20px;
        text-align: center;
    }

    a:hover {
        text-decoration: underline;
    }

    .form-row {
        margin-bottom: 20px;
    }

    hr {
        margin-top: 40px;
        margin-bottom: 20px;
        border: none;
        border-top: 1px solid #e0e0e0;
    }
</style>
</head>
<body>

<div class="container">

    <h2>Expert Management</h2>

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <c:if test="${not empty expertDetail}">
        <form method="post" action="expertDetail">
            <input type="hidden" name="action" value="update" />
            <input type="hidden" name="user_id" value="${expertDetail.getUser_id()}" />
            <div class="form-row">
                <p><strong>Expert Name:</strong> ${expertDetail.getUser_name()}</p>
                <label for="skill_id_update">Skill:</label>
                <select name="skill_id" id="skill_id_update" required>
                    <c:forEach var="skill" items="${allSkills}">
                        <option value="${skill.getSkillId()}" ${skill.getSkillId() == expertDetail.skill_id ? 'selected' : ''}>${skill.getSkillName()}</option>
                    </c:forEach>
                </select>
                <button type="submit">Update</button>
            </div>
        </form>
    </c:if>

    <hr/>

    <p><a href="expertListManage">Back to Expert List</a></p>

</div>

</body>
</html>
