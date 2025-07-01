<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Expert Insert</title>
    
</head>
<body>
    <h3>Add New Expert</h3>
    <form method="post" action="expertDetail">
        <input type="hidden" name="action" value="add" />

        <label for="username_new">Username:</label>
        <input type="text" id="username_new" name="username" required/>

        <label for="password_new">Password:</label>
        <input type="password" id="password_new" name="password" required/>

        <label for="first_name_new">First Name:</label>
        <input type="text" id="first_name_new" name="first_name" required/>

        <label for="last_name_new">Last Name:</label>
        <input type="text" id="last_name_new" name="last_name" required/>

        <label for="email_new">Email:</label>
        <input type="email" id="email_new" name="email" required/>

        <label for="phone_number_new">Phone Number:</label>
        <input type="tel" id="phone_number_new" name="phone_number" required/>

        <label for="skill_id_new">Select Skill:</label>
        
        <select name="skill_id" id="skill_id_update" required>
            <c:forEach var="skill" items="${allSkills}">
                <option value="${skill.getSkillId()}">${skill.getSkillName()}</option>
            </c:forEach>
        </select>

        <button type="submit">Add Expert</button>
        <c:if test="${not empty error}">
            <p style="color: red;">${error}</p>
        </c:if>
        <c:if test="${not empty message}">
            <p style="color: green;">${message}</p>
        </c:if>
    </form>

    <p><a href="expertListManage">Back to Expert List</a></p>

    <style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
        margin: 0;
    }

    h3 {
        color: #3f50f6;
        text-align: center;
        margin: 30px 0;
        font-size: 28px;
        font-weight: 600;
        text-shadow: 0 1px 2px rgba(0,0,0,0.1);
    }

    form {
        background: white;
        margin: 0 auto;
        padding: 30px 40px;
        border-radius: 12px;
        box-shadow: 0 6px 18px rgba(0,0,0,0.08);
        width: 100%;
        max-width: 600px;
        border: 1px solid rgba(0,0,0,0.05);
    }

    label {
        display: block;
        margin-top: 20px;
        margin-bottom: 8px;
        font-weight: 500;
        color: #555;
        font-size: 15px;
    }

    input, select {
        width: 100%;
        padding: 12px 15px;
        margin-top: 0;
        border: 1px solid #e0e0e0;
        border-radius: 8px;
        box-sizing: border-box;
        font-size: 15px;
        transition: all 0.3s ease;
        background-color: #fafafa;
    }

    input:focus, select:focus {
        border-color: #3f50f6;
        outline: none;
        box-shadow: 0 0 0 3px rgba(63, 80, 246, 0.1);
        background-color: white;
    }

    form button {
        width: 100%;
        background: linear-gradient(to right, #3f50f6, #6573f8);
        color: white;
        padding: 14px;
        border: none;
        border-radius: 8px;
        margin-top: 30px;
        font-size: 16px;
        font-weight: 500;
        cursor: pointer;
        transition: all 0.3s ease;
        box-shadow: 0 2px 6px rgba(63, 80, 246, 0.2);
    }

    button:hover {
        background: linear-gradient(to right, #3949db, #5a68e5);
        box-shadow: 0 4px 8px rgba(63, 80, 246, 0.3);
        transform: translateY(-1px);
    }

    button:active {
        transform: translateY(0);
    }

    .message-success {
        color: #4CAF50;
        background-color: #e8f5e9;
        padding: 12px 15px;
        border-radius: 8px;
        margin: 20px 0;
        font-weight: 500;
        border-left: 4px solid #4CAF50;
    }

    .message-error {
        color: #f44336;
        background-color: #ffebee;
        padding: 12px 15px;
        border-radius: 8px;
        margin: 20px 0;
        font-weight: 500;
        border-left: 4px solid #f44336;
    }

    /* Responsive adjustments */
    @media (max-width: 768px) {
        form {
            padding: 25px;
            margin: 0 15px;
        }
        
        h3 {
            font-size: 24px;
        }
    }
</style>
</body>
</html>
