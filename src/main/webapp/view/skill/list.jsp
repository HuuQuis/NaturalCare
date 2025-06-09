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
  <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
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
</head>
<body>
<div id="global-loader">
  <div class="whirly-loader"></div>
</div>

<div class="main-wrapper">
  <div class="header">
    <div class="header-left active">
      <a href="" class="logo"></a>
      <a href="" class="logo-small">
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
              <span class="input-group-text"><i class="fa fa-search"></i></span>
              <input type="text" id="searchInput" class="form-control" placeholder="Search skills" value="${search}">
            </div>
          </div>

          <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">${error}</div>
          </c:if>

          <div class="table-responsive">
            <table class="table table-bordered">
              <thead class="table-light">
              <tr>
                <th>No.</th>
                <th>Skill Name <a href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort == 'asc' ? 'desc' : 'asc'}&page=${page}"><i class="fas ${sort == 'asc' ? 'fa-sort-up' : 'fa-sort-down'}"></i></a></th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="skill" items="${list}" varStatus="loop">
                <tr>
                  <td>${(page - 1) * 5 + loop.index + 1}</td>
                  <td>${skill.skillName}</td>
                  <td>
                    <a class="me-3" href="javascript:void(0);" onclick="openEditModal(${skill.skillId}, '${skill.skillName}')">
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
                <c:forEach var="i" begin="1" end="${totalPages}">
                  <li class="page-item ${i == page ? 'active' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort}&page=${i}">${i}</a>
                  </li>
                </c:forEach>
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
      <form action="${pageContext.request.contextPath}/skill" method="post" class="modal-content" onsubmit="return validateSkillForm()">
        <div class="modal-header">
          <h5 class="modal-title" id="skillModalLabel">Add New Skill</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="skill_id" id="skillId">
          <div class="mb-3">
            <label for="skillName" class="form-label">Skill Name</label>
            <input type="text" class="form-control" name="skill_name" id="skillName" maxlength="50" required>
            <span class="text-danger d-none" id="duplicateError">Skill name already exists</span>
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
<script src="${pageContext.request.contextPath}/adminassets/plugins/sweetalert/sweetalerts.min.js"></script>
<script src="${pageContext.request.contextPath}/adminassets/js/script.js"></script>
<script>
  function openAddModal() {
    document.getElementById('skillModalLabel').innerText = 'Add New Skill';
    document.getElementById('skillId').value = '';
    document.getElementById('skillName').value = '';
    document.getElementById('duplicateError').classList.add('d-none');
    new bootstrap.Modal(document.getElementById('skillModal')).show();
  }

  function openEditModal(id, name) {
    document.getElementById('skillModalLabel').innerText = 'Edit Skill';
    document.getElementById('skillId').value = id;
    document.getElementById('skillName').value = name;
    document.getElementById('duplicateError').classList.add('d-none');
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
          $('#confirmDelete').show().attr('href', '${pageContext.request.contextPath}/skill?action=delete&id=' + id + '&search=${search}&sort=${sort}&page=${page}');
        }
        new bootstrap.Modal(document.getElementById('deleteModal')).show();
      }
    });
  }

  function validateSkillForm() {
    const skillName = document.getElementById('skillName').value.trim();
    const skillId = document.getElementById('skillId').value || 0;
    if (!skillName.match(/^[a-zA-Z\s]+$/)) {
      alert('Skill name can only contain letters and spaces.');
      return false;
    }
    if (skillName.length < 3 || skillName.length > 50) {
      alert('Skill name must be between 3 and 50 characters.');
      return false;
    }
    return true;
  }

  $(document).ready(function() {
    $('#searchInput').on('input', function() {
      const filter = this.value.toLowerCase().trim();
      window.location.href = '${pageContext.request.contextPath}/skill?search=' + encodeURIComponent(filter) + '&sort=${sort}&page=1';
    });

    $('#skillName').on('input', function() {
      const skillName = $(this).val().trim();
      const skillId = $('#skillId').val() || 0;
      if (skillName.length >= 3) {
        $.ajax({
          url: '${pageContext.request.contextPath}/skill',
          type: 'POST',
          data: { action: 'checkDuplicate', skill_name: skillName, skill_id: skillId },
          success: function(response) {
            if (response === 'duplicate') {
              $('#duplicateError').removeClass('d-none');
            } else {
              $('#duplicateError').addClass('d-none');
            }
          }
        });
      }
    });
  });
</script>
</body>
</html>