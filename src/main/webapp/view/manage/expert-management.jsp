<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Order Management</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      background-color: #f9f9f9;
    }
    h2 {
      margin-bottom: 20px;
    }
    .filter-section {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      margin-bottom: 20px;
    }
    .filter-section input,
    .filter-section select,
    .filter-section button {
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }
    .filter-section button {
      background-color: #007bff;
      color: white;
      cursor: pointer;
    }
    .filter-section button.reset {
      background-color: #dc3545;
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
      background-color: #f1f1f1;
    }
    .actions button {
      padding: 5px 10px;
      margin-right: 5px;
      border: none;
      border-radius: 3px;
      cursor: pointer;
    }
    .actions .delete {
      background-color: #dc3545;
      color: white;
    }
    .pagination {
      margin-top: 20px;
      display: flex;
      justify-content: center;
      gap: 10px;
    }
    .pagination a {
      padding: 6px 12px;
      border: 1px solid #ccc;
      background-color: white;
      text-decoration: none;
      color: black;
    }
    .pagination .active a {
      background-color: #007bff;
      color: white;
    }
  </style>
</head>
<body>
<h2>Quản lý đơn hàng</h2>

<form method="get" class="filter-section">
  <input type="text" name="search" placeholder="Tìm kiếm theo tên" value="${search}">
  <input type="text" name="skill" placeholder="Tìm kiếm theo skill" value="${skill}">
  <button type="submit">Lọc</button>
  <a href="expertListManage" class="reset" style="text-decoration:none; padding:8px; border-radius:4px; color:white;">Xóa bộ lọc</a>
</form>

<c:if test="${not empty message}">
  <div style="color: green; margin-bottom: 10px;">${message}</div>
</c:if>
<c:if test="${not empty error}">
  <div style="color: red; margin-bottom: 10px;">${error}</div>
</c:if>

<table>
  <thead>
    <tr>
      <th>STT</th>
      <th>Mã người chuyên gia</th>
      <th>Tên người chuyên gia</th>
      <th>Mã kỹ năng</th>
      <th>Tên kỹ năng</th>
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
