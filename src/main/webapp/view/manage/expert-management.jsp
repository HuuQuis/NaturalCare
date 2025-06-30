<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Expert Management</title>
  
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
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f9f9f9;
        margin: 0;
    }

    h2 {
        margin-bottom: 20px;
        color: #333;
        font-size: 24px;
    }

    /* Filter section */
    .filter-section {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        margin-bottom: 25px;
        align-items: center;
    }

    .filter-section input {
        padding: 10px 15px;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 14px;
        min-width: 250px;
    }

    .filter-section button {
        padding: 10px 20px;
        background-color: #616eeb;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
    }

    .filter-section button:hover {
        background-color: #2c3ac0;
    }

    .filter-section a {
        padding: 10px 20px;
        text-decoration: none;
        border-radius: 4px;
        font-size: 14px;
        display: inline-flex;
        align-items: center;
    }

    .filter-section a.reset {
        background-color: #f44336;
        color: white;
    }

    .filter-section a.reset:hover {
        background-color: #d32f2f;
    }

    .filter-section a.add-new {
        background-color: #4CAF50;
        color: white;
    }

    .filter-section a.add-new:hover {
        background-color: #388E3C;
    }

    /* Table styles */
    table {
        width: 100%;
        border-collapse: collapse;
        background-color: white;
        box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        margin-bottom: 25px;
    }

    th, td {
        padding: 15px;
        border: 1px solid #e0e0e0;
        text-align: left;
    }

    th {
        background-color: #f5f5f5;
        color: #333;
        font-weight: 600;
    }

    tr:nth-child(even) {
        background-color: #fafafa;
    }

    tr:hover {
        background-color: #f1f1f1;
    }

    /* ID link style */
    td a {
        color: #3f50f6;
        text-decoration: none;
        font-weight: 500;
    }

    td a:hover {
        text-decoration: underline;
    }

    /* Pagination */
    .pagination {
        display: flex;
        justify-content: center;
        gap: 8px;
        margin-top: 25px;
    }

    .pagination div {
        padding: 8px 15px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    .pagination a {
        color: #ccc;
        text-decoration: none;
        font-weight: 500;
    }

    .pagination div.active {
        background-color: #616eeb;
        color: white;
        border-color: #3f50f6;
    }

    /* Message styles */
    .message-success {
        color: #4CAF50;
        margin-bottom: 20px;
        font-weight: 500;
        padding: 10px;
        background-color: #e8f5e9;
        border-radius: 4px;
    }

    .message-error {
        color: #f44336;
        margin-bottom: 20px;
        font-weight: 500;
        padding: 10px;
        background-color: #ffebee;
        border-radius: 4px;
    }
</style>
</html>
