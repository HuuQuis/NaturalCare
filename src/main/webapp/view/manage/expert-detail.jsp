<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Expert Detail</title>
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
                <p><strong>Expert Name:</strong> ${expertDetail.getUser_name()}</p>
                <label for="skill_id_update">Skill:</label>
                <select name="skill_id" id="skill_id_update" required>
                    <c:forEach var="skill" items="${allSkills}">
                        <option value="${skill.getSkillId()}" ${skill.getSkillId() == expertDetail.skill_id ? 'selected' : ''}>${skill.getSkillName()}</option>
                    </c:forEach>
                </select>
                <button type="submit">Update</button>
        </form>
    </c:if>

    <hr/>

    <p><a href="expertListManage">Back to Expert List</a></p>

</div>
<style>
    * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
    }

    body {
        font-family: 'Inter', 'Segoe UI', system-ui, -apple-system, sans-serif;
        background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
        color: #2d3748;
        line-height: 1.6;
    }

    h2 {
        color: #3f50f6;
        margin-bottom: 1.5rem;
        font-size: 2rem;
        font-weight: 700;
        text-align: center;
        letter-spacing: -0.5px;
    }

    .container {
        max-width: 640px;
        margin: 0 auto;
        background: white;
        padding: 2.5rem;
        border-radius: 16px;
        box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
        border: 1px solid rgba(0, 0, 0, 0.03);
    }

    .message {
        background: linear-gradient(to right, #e3f2fd, #bbdefb);
        color: #1565c0;
        padding: 1rem 1.5rem;
        border-radius: 8px;
        margin-bottom: 2rem;
        font-weight: 500;
        border-left: 4px solid #2196f3;
        box-shadow: 0 2px 4px rgba(33, 150, 243, 0.1);
    }

    .form-row {
        margin-bottom: 1.75rem;
    }

    label {
        display: block;
        font-weight: 600;
        margin-bottom: 0.75rem;
        color: #4a5568;
        font-size: 0.95rem;
    }

    p {
        font-size: 1rem;
        margin-bottom: 1.5rem;
        color: #4a5568;
    }

    p strong {
        color: #2d3748;
        font-weight: 600;
    }

    input[type="text"],
    select {
        width: 100%;
        padding: 0.875rem 1.25rem;
        border-radius: 8px;
        border: 1px solid #e2e8f0;
        font-size: 1rem;
        transition: all 0.3s ease;
        background-color: #f8fafc;
        appearance: none;
    }

    input[type="text"]:focus,
    select:focus {
        outline: none;
        border-color: #3f50f6;
        box-shadow: 0 0 0 3px rgba(63, 80, 246, 0.1);
        background-color: white;
    }

    select {
        background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='%234a5568' viewBox='0 0 16 16'%3E%3Cpath d='M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z'/%3E%3C/svg%3E");
        background-repeat: no-repeat;
        background-position: right 1rem center;
        background-size: 12px;
    }

    form button {
        background: linear-gradient(to right, #3f50f6, #6573f8);
        border: none;
        color: white;
        font-weight: 600;
        padding: 0.875rem 2rem;
        border-radius: 8px;
        cursor: pointer;
        font-size: 1rem;
        transition: all 0.3s ease;
        margin-top: 0.5rem;
        width: 100%;
        box-shadow: 0 4px 6px rgba(63, 80, 246, 0.15);
        margin-top: 20px;
    }

    button:hover {
        background: linear-gradient(to right, #3949db, #5a68e5);
        box-shadow: 0 6px 8px rgba(63, 80, 246, 0.2);
        transform: translateY(-1px);
    }

    button:active {
        transform: translateY(0);
    }

/*    a {
        color: #3f50f6;
        text-decoration: none;
        font-weight: 600;
        display: inline-flex;
        align-items: center;
        gap: 0.5rem;
        margin-top: 2rem;
        padding: 0.5rem 1rem;
        border-radius: 6px;
        transition: all 0.2s ease;
    }*/

    a:hover {
        background-color: rgba(63, 80, 246, 0.1);
        text-decoration: none;
    }

    hr {
        margin: 2.5rem 0;
        border: none;
        border-top: 1px solid #edf2f7;
    }

    @media (max-width: 768px) {
        .container {
            padding: 1.75rem;
        }
        
        h2 {
            font-size: 1.75rem;
        }
    }
</style>
</body>
</html>
