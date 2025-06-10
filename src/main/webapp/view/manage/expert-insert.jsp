<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Expert Insert</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f9f4;
            margin: 0;
            padding: 0;
        }

        h3 {
            color: #2ecc71;
            text-align: center;
            margin-top: 30px;
        }

        form {
            background-color: white;
            margin: 30px auto;
            padding: 25px 30px;
            border-radius: 12px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 600px;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: 600;
            color: #333;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-sizing: border-box;
            font-size: 15px;
        }

        input:focus, select:focus {
            border-color: #2ecc71;
            outline: none;
            box-shadow: 0 0 4px #2ecc71;
        }

        button {
            width: 100%;
            background-color: #2ecc71;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 10px;
            margin-top: 20px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #27ae60;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            text-decoration: none;
            color: #2ecc71;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
            color: #27ae60;
        }
    </style>
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
</body>
</html>
