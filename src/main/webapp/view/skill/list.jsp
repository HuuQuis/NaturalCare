<%--
  Created by IntelliJ IDEA.
  User: telamon
  Date: 27/5/25
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Skill" %>
<%
  List<Skill> list = (List<Skill>) request.getAttribute("list");

  int currentPage;
  try {
    currentPage = Integer.parseInt(String.valueOf(request.getAttribute("page")));
  } catch (Exception e) {
    currentPage = 1;
  }

  int totalPages;
  try {
    totalPages = Integer.parseInt(String.valueOf(request.getAttribute("totalPages")));
  } catch (Exception e) {
    totalPages = 1;
  }

  String search = request.getAttribute("search") != null ? String.valueOf(request.getAttribute("search")) : "";
  String sort = request.getAttribute("sort") != null ? String.valueOf(request.getAttribute("sort")) : "asc";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Skill List | Natural Care</title>
  <link href="<%= request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet" />
  <style>
    table th, table td {
      vertical-align: middle !important;
    }
    .pagination a {
      margin: 0 3px;
      text-decoration: none;
    }
  </style>
</head>
<body>
<header id="header">
  <jsp:include page="/view/common/header-top.jsp" />
  <jsp:include page="/view/common/header-middle.jsp" />
  <jsp:include page="/view/common/header-bottom.jsp" />
</header>

<section class="container" style="margin-top: 30px; max-width: 900px;">
  <h2>Skill List</h2>

  <form method="get" action="skill" class="form-inline mb-3">
    <div class="form-group mr-2">
      <label for="search" class="mr-1">Search:</label>
      <input type="text" id="search" name="search" class="form-control" value="<%= search %>" placeholder="Skill name..." />
    </div>
    <div class="form-group mr-2">
      <label for="sort" class="mr-1">Sort:</label>
      <select name="sort" id="sort" class="form-control">
        <option value="asc" <%= "asc".equals(sort) ? "selected" : "" %>>A-Z</option>
        <option value="desc" <%= "desc".equals(sort) ? "selected" : "" %>>Z-A</option>
      </select>
    </div>
    <button type="submit" class="btn btn-primary">Filter</button>
    <a href="skill?action=form" class="btn btn-success ml-3">Add New Skill</a>
  </form>

  <table class="table table-bordered table-striped">
    <thead class="thead-dark">
    <tr>
      <th style="width: 10%;">ID</th>
      <th style="width: 70%;">Name</th>
      <th style="width: 20%;">Action</th>
    </tr>
    </thead>
    <tbody>
    <% if (list != null && !list.isEmpty()) {
      for (Skill s : list) { %>
    <tr>
      <td><%= s.getSkillId() %></td>
      <td><%= s.getSkillName() %></td>
      <td>
        <a href="skill?action=form&id=<%= s.getSkillId() %>" class="btn btn-sm btn-warning">Edit</a>
        <a href="skill?action=delete&id=<%= s.getSkillId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Delete this skill?')">Delete</a>
      </td>
    </tr>
    <% }
    } else { %>
    <tr>
      <td colspan="3" class="text-center">No skills found.</td>
    </tr>
    <% } %>
    </tbody>
  </table>

  <nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
      <% for (int i = 1; i <= totalPages; i++) {
        String activeClass = (i == currentPage) ? "active" : "";
      %>
      <li class="page-item <%= activeClass %>">
        <a class="page-link" href="skill?page=<%= i %>&search=<%= search %>&sort=<%= sort %>"><%= i %></a>
      </li>
      <% } %>
    </ul>
  </nav>
</section>

<jsp:include page="/view/common/footer.jsp" />

<script src="<%= request.getContextPath() %>/js/jquery.js"></script>
<script src="<%= request.getContextPath() %>/js/bootstrap.min.js"></script>
</body>
</html>
