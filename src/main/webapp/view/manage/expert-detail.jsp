<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết chuyên gia</title>
    <style>
        /* Reset cơ bản */
        * {
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f9fafb;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        h2, h3 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        /* Container chính */
        .container {
            max-width: 700px;
            margin: 0 auto;
            background: #fff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        /* Message thành công */
        .message {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            padding: 12px 20px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-weight: 600;
        }

        form {
            margin-bottom: 30px;
        }

        label {
            display: block;
            font-weight: 600;
            margin-bottom: 8px;
            color: #34495e;
        }

        p {
            margin-bottom: 15px;
        }

        input[type="text"], select {
            width: 100%;
            padding: 10px 14px;
            border-radius: 6px;
            border: 1.5px solid #bdc3c7;
            font-size: 1rem;
            transition: border-color 0.3s ease;
        }

        input[type="text"]:focus, select:focus {
            outline: none;
            border-color: #2980b9;
            box-shadow: 0 0 5px rgba(41, 128, 185, 0.5);
        }

        button {
            background-color: #2980b9;
            border: none;
            color: white;
            font-weight: 700;
            padding: 12px 25px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1rem;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #1c5980;
        }

        a {
            color: #2980b9;
            text-decoration: none;
            font-weight: 600;
        }

        a:hover {
            text-decoration: underline;
        }

        /* Flex cho form cập nhật để đẹp hơn */
        .form-row {
            display: flex;
            align-items: center;
            gap: 15px;
            flex-wrap: wrap;
        }
        .form-row > p {
            flex: 1;
            margin-bottom: 0;
        }
        .form-row select {
            max-width: 250px;
            flex-shrink: 0;
        }

    </style>
</head>
<body>

<div class="container">

    <h2>Chi tiết chuyên gia</h2>

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <c:if test="${not empty expertDetail}">
        <form method="post" action="expertDetail">
            <input type="hidden" name="action" value="update" />
            <input type="hidden" name="user_id" value="${expertDetail.getUser_id()}" />
            <div class="form-row">
                <p><strong>Tên chuyên gia:</strong> ${expertDetail.getUser_name()}</p>
                <label for="skill_id_update">Kỹ năng:</label>
                <select name="skill_id" id="skill_id_update" required>
                    <c:forEach var="skill" items="${allSkills}">
                        <option value="${skill.getSkillId()}" ${skill.getSkillId() == expertDetail.skill_id ? 'selected' : ''}>${skill.getSkillName()}</option>
                    </c:forEach>
                </select>
                <button type="submit">Cập nhật</button>
            </div>
        </form>
    </c:if>

    <hr/>

    <h3>Thêm chuyên gia mới</h3>
    <form method="post" action="expertDetail">
        <label for="user_name_new">Tên chuyên gia:</label>
        <input type="text" id="user_name_new" name="user_name" required/>

        <label for="skill_id_new">Chọn kỹ năng:</label>
        <select name="skill_id" id="skill_id_new" required>
            <c:forEach var="skill" items="${allSkills}">
                <option value="${skill.getSkillId()}">${skill.getSkillName()}</option>
            </c:forEach>
        </select>

        <button type="submit" style="margin-top: 15px;">Thêm chuyên gia</button>
    </form>

    <p><a href="expertListManage">Quay lại danh sách chuyên gia</a></p>

</div>

</body>
</html>
