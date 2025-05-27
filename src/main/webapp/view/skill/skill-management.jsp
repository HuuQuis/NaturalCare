<%--
  Created by IntelliJ IDEA.
  User: telamon
  Date: 26/5/25
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Skill" %>
<%@ page import="java.util.List" %>
<%
    List<Skill> list = (List<Skill>) request.getAttribute("list");
    String keyword = request.getAttribute("keyword") != null ? (String) request.getAttribute("keyword") : "";
    Skill skill = (Skill) request.getAttribute("skill");
%>
<html>
<head>
    <title>Skill Management</title>
</head>
<body>
<h2>Skill Management</h2>

<form method="get">
    <input type="text" name="keyword" value="<%= keyword %>" placeholder="Search skill name..."/>
    <input type="submit" value="Search"/>
</form>

<form method="post">
    <input type="hidden" name="id" value="<%= skill != null ? skill.getSkillId() : "" %>"/>
    <input type="text" name="name" value="<%= skill != null ? skill.getSkillName() : "" %>" required/>
    <input type="submit" value="<%= skill != null ? "Update" : "Add" %>"/>
</form>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    <% for (Skill s : list) { %>
    <tr>
        <td><%= s.getSkillId() %></td>
        <td><%= s.getSkillName() %></td>
        <td>
            <a href="skill?action=edit&id=<%= s.getSkillId() %>">Edit</a>
            <a href="skill?action=delete&id=<%= s.getSkillId() %>" onclick="return confirm('Delete this skill?')">Delete</a>
        </td>
    </tr>
    <% } %>
</table>

</body>
</html>
