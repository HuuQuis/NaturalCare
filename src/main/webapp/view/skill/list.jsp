<%--
  Created by IntelliJ IDEA.
  User: telamon
  Date: 27/5/25
  Time: 12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
  <meta name="description" content="POS - Bootstrap Admin Template">
  <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, tools, responsive">
  <meta name="author" content="Dreamguys - Bootstrap Admin Template">
  <meta name="robots" content="noindex, nofollow">
  <title>Skill List | Natural Care</title>

  <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/adminassets/img/favicon.png">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/animate.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/plugins/select2/css/select2.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/dataTables.bootstrap4.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/plugins/fontawesome/css/fontawesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/plugins/fontawesome/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/adminassets/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminStyle.css">
  <style>
    .table th:nth-child(2), .table td:nth-child(2) {
      max-width: 300px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  </style>
</head>
<body>
<div id="global-loader">
  <div class="whirly-loader"></div>
</div>

<div class="main-wrapper">
  <div class="header">
    <div class="header-left active">
      <a href="#" class="logo"></a>
      <a href="#" class="logo-small">
        <img src="${pageContext.request.contextPath}/adminassets/img/logo-small.png" alt="">
      </a>
    </div>
    <ul class="nav user-menu">
      <li class="nav-item dropdown has-arrow main-drop">
        <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                    <span class="user-img"><img src="${pageContext.request.contextPath}/adminassets/img/profiles/avator1.jpg" alt="">
                        <span class="status online"></span></span>
        </a>
        <div class="dropdown-menu menu-drop-user">
          <div class="profilename">
            <div class="profileset">
                            <span class="user-img"><img src="${pageContext.request.contextPath}/adminassets/img/profiles/avator1.jpg" alt="">
                                <span class="status online"></span></span>
              <div class="profilesets">
                <h5>Manager</h5>
              </div>
            </div>
            <hr class="m-0">
            <a class="dropdown-item logout pb-0" href="logout"><img src="${pageContext.request.contextPath}/adminassets/img/icons/log-out.svg" class="me-2" alt="img">Logout</a>
          </div>
        </div>
      </li>
    </ul>
  </div>

  <c:set var="view" value="skill" scope="request"/>
  <jsp:include page="../common/sidebar-manager.jsp"/>

  <div class="page-wrapper">
    <div class="content">
      <div class="page-header">
        <div class="page-title">
          <h4>Skill List</h4>
        </div>
        <div class="page-btn">
          <a href="javascript:void(0);" class="btn btn-added" onclick="openAddModal()">
            <img src="${pageContext.request.contextPath}/adminassets/img/icons/plus.svg" alt="img" class="me-1">Add New Skill
          </a>
        </div>
      </div>

      <div class="card">
        <div class="card-body">
          <div class="mb-3 d-flex justify-content-between align-items-center">
            <div class="input-group" style="max-width: 300px;">
              <form id="searchForm" action="${pageContext.request.contextPath}/skill" method="get">
                <div class="input-group">
                  <input type="text" id="searchInput" name="search" class="form-control" placeholder="Search skills" value="${search}">
                  <button type="submit" class="btn btn-outline-secondary"><i class="fa fa-search"></i></button>
                </div>
                <span class="text-danger d-none" id="searchError">${searchError}</span>
              </form>
            </div>
            <div class="d-flex align-items-center">
              <label for="pageSize" class="me-2">Show</label>
              <select id="pageSize" class="form-select" style="width: 100px;">
                <option value="10" ${size == 10 ? 'selected' : ''}>10</option>
                <option value="20" ${size == 20 ? 'selected' : ''}>20</option>
                <option value="30" ${size == 30 ? 'selected' : ''}>30</option>
                <option value="40" ${size == 40 ? 'selected' : ''}>40</option>
                <option value="50" ${size == 50 ? 'selected' : ''}>50</option>
              </select>
              <span class="ms-2">per page</span>
            </div>
          </div>

          <div class="table-responsive">
            <table class="table table-bordered">
              <thead class="table-light">
              <tr>
                <th>No.</th>
                <th>Skill Name<a href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort == 'asc' ? 'desc' : 'asc'}&page=${page}&size=${size}" class="ms-1"><i class="fas ${sort == 'asc' ? 'fa-sort-up' : 'fa-sort-down'}"></i></a></th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="skill" items="${list}" varStatus="loop">
                <tr>
                  <td>${(page - 1) * size + loop.index + 1}</td>
                  <td>${skill.skillName}</td>
                  <td>
                    <a class="me-3" href="javascript:void(0);" onclick="openEditModal(${skill.skillId}, '${skill.skillName.replace("'", "\\'")}')">
                      <img src="${pageContext.request.contextPath}/adminassets/img/icons/edit.svg" alt="edit">
                    </a>
                    <a href="javascript:void(0);" class="me-3 delete-btn" data-id="${skill.skillId}" onclick="openDeleteModal(${skill.skillId})">
                      <img src="${pageContext.request.contextPath}/adminassets/img/icons/delete.svg" alt="delete">
                    </a>
                  </td>
                </tr>
              </c:forEach>
              <c:if test="${empty list}">
                <tr><td colspan="3" class="text-muted fst-italic">No skills found</td></tr>
              </c:if>
              </tbody>
            </table>
          </div>

          <c:if test="${totalPages > 1}">
            <div class="d-flex justify-content-end mt-3">
              <ul class="pagination">
                <li class="page-item ${page == 1 ? 'disabled' : ''}">
                  <a class="page-link" href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort}&page=1&size=${size}" title="First Page">
                    <i class="fas fa-angle-double-left"></i>
                  </a>
                </li>
                <li class="page-item ${page == 1 ? 'disabled' : ''}">
                  <a class="page-link" href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort}&page=${page - 1}&size=${size}" title="Previous Page">
                    <i class="fas fa-angle-left"></i>
                  </a>
                </li>
                <c:forEach var="i" begin="${page - 2 > 1 ? page - 2 : 1}" end="${page + 2 < totalPages ? page + 2 : totalPages}">
                  <li class="page-item ${i == page ? 'active' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort}&page=${i}&size=${size}">${i}</a>
                  </li>
                </c:forEach>
                <li class="page-item ${page == totalPages ? 'disabled' : ''}">
                  <a class="page-link" href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort}&page=${page + 1}&size=${size}" title="Next Page">
                    <i class="fas fa-angle-right"></i>
                  </a>
                </li>
              </ul>
            </div>
          </c:if>
        </div>
      </div>
    </div>
  </div>

  <!-- Skill Modal -->
  <div class="modal fade" id="skillModal" tabindex="-1" aria-labelledby="skillModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <form action="${pageContext.request.contextPath}/skill" method="post" class="modal-content" id="skillForm">
        <div class="modal-header">
          <h5 class="modal-title" id="skillModalLabel">Add New Skill</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="skill_id" id="skillId" value="${skill.skillId}">
          <div class="mb-3">
            <label for="skillName" class="form-label">Skill Name</label>
            <input type="text" class="form-control" name="skill_name" id="skillName" maxlength="50" value="${skill.skillName}" required>
            <span class="text-danger d-none" id="skillNameError"></span>
          </div>
        </div>
        <div class="modal-footer">
          <button type="submit" class="btn btn-success">Save</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
        </div>
      </form>
    </div>
  </div>

  <!-- Delete Modal -->
  <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="deleteModalLabel">Confirm Delete</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <p>Are you sure you want to delete this skill?</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <a href="#" id="confirmDelete" class="btn btn-danger">Delete</a>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="${pageContext.request.contextPath}/adminassets/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/js/feather.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/js/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/js/dataTables.bootstrap4.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/plugins/select2/js/select2.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/js/script.js"></script>
<script>
  function openAddModal() {
    document.getElementById('skillModalLabel').innerText = 'Add New Skill';
    document.getElementById('skillId').value = '';
    document.getElementById('skillName').value = '';
    document.getElementById('skillNameError').classList.add('d-none');
    new bootstrap.Modal(document.getElementById('skillModal')).show();
  }

  function openEditModal(id, name) {
    document.getElementById('skillModalLabel').innerText = 'Edit Skill';
    document.getElementById('skillId').value = id;
    document.getElementById('skillName').value = name;
    document.getElementById('skillNameError').classList.add('d-none');
    new bootstrap.Modal(document.getElementById('skillModal')).show();
  }

  function openDeleteModal(id) {
    $.ajax({
      url: '${pageContext.request.contextPath}/skill',
      type: 'GET',
      data: { action: 'check', id: id },
      success: function(response) {
        if (response === 'inUse') {
          $('#deleteModal .modal-body p').text('Cannot delete this skill because it is in use.');
          $('#confirmDelete').hide();
        } else {
          $('#deleteModal .modal-body p').text('Are you sure you want to delete this skill?');
          $('#confirmDelete').show().attr('href', '${pageContext.request.contextPath}/skill?action=delete&id=' + id + '&search=${search}&sort=${sort}&page=${page}&size=${size}');
        }
        new bootstrap.Modal(document.getElementById('deleteModal')).show();
      }
    });
  }

  function validateSkillForm() {
    const skillName = document.getElementById('skillName').value.trim();
    const skillId = document.getElementById('skillId').value || 0;
    if (!skillName.match(/^[a-zA-Z\s]+$/)) {
      document.getElementById('skillNameError').textContent = 'Skill name can only contain letters and spaces.';
      document.getElementById('skillNameError').classList.remove('d-none');
      return false;
    }
    if (skillName.length < 3) {
      document.getElementById('skillNameError').textContent = 'Skill name must be at least 3 characters long.';
      document.getElementById('skillNameError').classList.remove('d-none');
      return false;
    }
    if (skillName.length > 50) {
      document.getElementById('skillNameError').textContent = 'Skill name must not exceed 50 characters.';
      document.getElementById('skillNameError').classList.remove('d-none');
      return false;
    }
    return true;
  }

  function validateSearchForm() {
    const search = document.getElementById('searchInput').value.trim();
    const searchError = document.getElementById('searchError');
    if (search.length > 50) {
      searchError.textContent = 'Search keyword must not exceed 50 characters.';
      searchError.classList.remove('d-none');
      return false;
    }
    if (!search.match(/^[a-zA-Z\s]*$/) && search.length > 0) {
      searchError.textContent = 'Search keyword can only contain letters and spaces.';
      searchError.classList.remove('d-none');
      return false;
    }
    searchError.classList.add('d-none');
    return true;
  }

  $(document).ready(function() {
    $('#pageSize').on('change', function() {
      const size = this.value;
      window.location.href = '${pageContext.request.contextPath}/skill?search=${search}&sort=${sort}&page=1&size=' + size;
    });

    $('#searchForm').on('submit', validateSearchForm);

    $('#searchInput').on('input', function() {
      const search = $(this).val().trim();
      const searchError = $('#searchError');
      if (search.length > 50) {
        searchError.text('Search keyword must not exceed 50 characters.');
        searchError.removeClass('d-none');
      } else if (!search.match(/^[a-zA-Z\s]*$/) && search.length > 0) {
        searchError.text('Search keyword can only contain letters and spaces.');
        searchError.removeClass('d-none');
      } else {
        searchError.addClass('d-none');
      }
    });

    $('#skillName').on('input', function() {
      const skillName = $(this).val().trim();
      const skillId = $('#skillId').val() || 0;
      if (skillName.length >= 3 && skillName.length <= 50) {
        $.ajax({
          url: '${pageContext.request.contextPath}/skill',
          type: 'POST',
          data: { action: 'checkDuplicate', skill_name: skillName, skill_id: skillId },
          success: function(response) {
            if (response === 'duplicate') {
              $('#skillNameError').text('Skill name already exists.');
              $('#skillNameError').removeClass('d-none');
            } else {
              $('#skillNameError').addClass('d-none');
            }
          }
        });
      }
    });

    $('#skillForm').on('submit', validateSkillForm);

    <c:if test="${not empty error}">
    document.getElementById('skillModalLabel').innerText = '${skill.skillId > 0 ? "Edit Skill" : "Add New Skill"}';
    document.getElementById('skillId').value = '${skill.skillId}';
    document.getElementById('skillName').value = '${skill.skillName}';
    $('#skillNameError').text('${error}');
    $('#skillNameError').removeClass('d-none');
    new bootstrap.Modal(document.getElementById('skillModal')).show();
    </c:if>

    <c:if test="${not empty searchError}">
    $('#searchError').text('${searchError}');
    $('#searchError').removeClass('d-none');
    </c:if>
  });
</script>
</body>
</html>