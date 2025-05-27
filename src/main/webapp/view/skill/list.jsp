<%--
  Created by IntelliJ IDEA.
  User: telamon
  Date: 27/5/25
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Skill Management</title>
</head>
<body>
<h2>Skill Management</h2>

<form action="skills" method="get">
  <input type="text" name="search" placeholder="Search skill..." value="${search}" />
  <input type="submit" value="Search" />
</form>

<form action="skills" method="post">
  <input type="hidden" name="action" value="add" />
  <input type="text" name="name" placeholder="New skill name" required />
  <input type="text" name="description" placeholder="Description" required />
  <input type="submit" value="Add" />
</form>

<table border="1">
  <tr>
    <th>ID</th><th>Name</th><th>Description</th><th>Actions</th>
  </tr>
  <c:forEach var="s" items="${skills}">
    <tr>
      <form action="skills" method="post">
        <input type="hidden" name="action" value="update"/>
        <input type="hidden" name="id" value="${s.id}" />
        <td>${s.id}</td>
        <td><input type="text" name="name" value="${s.name}" /></td>
        <td><input type="text" name="description" value="${s.description}" /></td>
        <td>
          <input type="submit" value="Update" />
      </form>
      <form action="skills" method="post" style="display:inline">
        <input type="hidden" name="action" value="delete"/>
        <input type="hidden" name="id" value="${s.id}" />
        <input type="submit" value="Delete" />
      </form>
      </td>
    </tr>
  </c:forEach>
</table>

</body>
</html>
