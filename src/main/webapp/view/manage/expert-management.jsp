<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Expert Management</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      background-color: #f9f9f9;
    }

    h2 {
      margin-bottom: 20px;
      color: #2e7d32;
    }

    .filter-section {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      margin-bottom: 20px;
    }

    .filter-section input,
    .filter-section select,
    .filter-section button,
    .filter-section a {
      padding: 8px;
      border: 1px solid #a5d6a7;
      border-radius: 4px;
      font-size: 14px;
    }

    .filter-section input:focus {
      outline: none;
      border-color: #66bb6a;
      box-shadow: 0 0 5px rgba(102, 187, 106, 0.5);
    }

    .filter-section button {
      background-color: #43a047;
      color: white;
      cursor: pointer;
      border: none;
      transition: background-color 0.3s ease;
    }

    .filter-section button:hover {
      background-color: #2e7d32;
    }

    .filter-section a.reset {
      background-color: #c62828;
      color: white;
      text-decoration: none;
      border: none;
    }

    .filter-section a.reset:hover {
      background-color: #8e0000;
    }

    .filter-section a {
      text-decoration: none;
      color: white;
      background-color: #43a047;
      transition: background-color 0.3s ease;
    }

    .filter-section a:hover {
      background-color: #2e7d32;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      background-color: white;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }

    th, td {
      padding: 12px;
      border: 1px solid #ddd;
      text-align: left;
    }

    th {
      background-color: #e8f5e9;
      color: #2e7d32;
    }

    td a {
      color: #2e7d32;
      font-weight: bold;
      text-decoration: none;
    }

    td a:hover {
      text-decoration: underline;
    }

    .pagination {
      margin-top: 20px;
      display: flex;
      justify-content: center;
      gap: 10px;
    }

    .pagination a {
      padding: 6px 12px;
      border: 1px solid #a5d6a7;
      background-color: white;
      text-decoration: none;
      color: #2e7d32;
      font-weight: bold;
      border-radius: 4px;
    }

    .pagination .active a {
      background-color: #66bb6a;
      color: white;
      border-color: #388e3c;
    }

    .message-success {
      color: #2e7d32;
      margin-bottom: 10px;
      font-weight: bold;
    }

    .message-error {
      color: #c62828;
      margin-bottom: 10px;
      font-weight: bold;
    }
  </style>
</head>
<body>
<h2>Expert Management</h2>

<form method="get" class="filter-section">
  <input type="text" name="search" placeholder="Find by Name" value="${search}">
  <input type="text" name="skill" placeholder="Find by Skill" value="${skill}">
  <button type="submit">Filter</button>
  <a href="expertListManage" class="reset">Delete filter</a>
  <a href="expertInsert">Add new expert</a>
</form>

<c:if test="${not empty message}">
  <div class="message-success">${message}</div>
</c:if>
<c:if test="${not empty error}">
  <div class="message-error">${error}</div>
</c:if>

<table>
  <thead>
    <tr>
      <th>No.</th>
      <th>Expert ID</th>
      <th>Expert Name</th>
      <th>Skill ID</th>
      <th>Skill Name</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="expert" items="${experts}" varStatus="loop">
      <tr>
        <td>${loop.index + 1}</td>
        <td><a href="expertDetail?user_id=${expert.getUser_id()}">#${expert.getUser_id()}</a></td>
        <td>${expert.getUser_name()}</td>
        <td>${expert.getSkill_id()}</td>
        <td>${expert.getSkill_name()}</td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<div class="pagination">
  <c:forEach var="i" begin="1" end="${totalPages}">
    <div class="${i == currentPage ? 'active' : ''}">
      <a href="expertListManage?page=${i}&search=${search}&skill=${skill}">${i}</a>
    </div>
  </c:forEach>
</div>

</body>
</html>
