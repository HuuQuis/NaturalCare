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
    List<Skill> skills = (List<Skill>) request.getAttribute("skills");
    Skill editing = (Skill) request.getAttribute("skill");
%>
<html>
<head>
    <title>Skill Management</title>
</head>
<body>
<h2>Skill Management</h2>
<form action="view/skill" method="post">
    <input type="hidden" name="id" value="<%= editing != null ? editing.getSkillId() : "" %>"/>
    <input type="text" name="name" placeholder="Skill name" value="<%= editing != null ? editing.getSkillName() : "" %>" required/>
    <button type="submit"><%= editing != null ? "Update" : "Add" %></button>
</form>

<form method="get" action="view/skill">
    <input type="text" name="search" placeholder="Search by name"/>
    <button type="submit">Search</button>
</form>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    <% for (Skill s : skills) { %>
    <tr>
        <td><%= s.getSkillId() %></td>
        <td><%= s.getSkillName() %></td>
        <td>
            <a href="view/skill?action=edit&id=<%= s.getSkillId() %>">Edit</a> |
            <a href="view/skill?action=delete&id=<%= s.getSkillId() %>">Delete</a>
        </td>
    </tr>
    <% } %>
</table>
</body>
</html>
