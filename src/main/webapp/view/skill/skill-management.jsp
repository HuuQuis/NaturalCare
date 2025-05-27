<%--
  Created by IntelliJ IDEA.
  User: telamon
  Date: 26/5/25
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Skill" %>
<%
    List<Skill> skills = (List<Skill>) request.getAttribute("skills");
%>
<html>
<head>
    <title>Skill Management</title>
</head>
<body>
<h2>Skill Management</h2>

<form method="get" action="">
    Search: <input type="text" name="search" />
    Sort:
    <select name="sort">
        <option value="asc">ASC</option>
        <option value="desc">DESC</option>
    </select>
    <button type="submit">Filter</button>
</form>

<form method="post" action="">
    <input type="text" name="skillName" placeholder="New skill name"/>
    <input type="hidden" name="action" value="add"/>
    <button type="submit">Add Skill</button>
</form>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    <%
        for (Skill s : skills) {
    %>
    <tr>
        <form method="post" action="">
            <td><%= s.getSkillId() %></td>
            <td>
                <input type="text" name="skillName" value="<%= s.getSkillName() %>"/>
            </td>
            <td>
                <input type="hidden" name="skillId" value="<%= s.getSkillId() %>"/>
                <button name="action" value="update">Update</button>
                <button name="action" value="delete">Delete</button>
            </td>
        </form>
    </tr>
    <% } %>
</table>

</body>
</html>
