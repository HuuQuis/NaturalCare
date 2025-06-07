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
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Skills Management | Natural Care</title>
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
  <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <![endif]-->
  <style>
    .animated { animation-duration: 1s; animation-fill-mode: both; }
    .table tr { animation-delay: 0.2s; }
    .btn { transition: transform 0.3s ease, background-color 0.3s ease; }
    .btn:hover { transform: scale(1.1); }
    .form-control:focus { transition: box-shadow 0.3s ease; box-shadow: 0 0 8px rgba(0, 123, 255, 0.5); }
    .modal-content { animation-duration: 0.5s; }
    .pagination li a { transition: transform 0.3s ease; }
    .pagination li a:hover { transform: scale(1.1); }
    .table tr:hover { transition: background-color 0.3s ease; background-color: #f5f5f5; }
    .loading { opacity: 0.6; pointer-events: none; }
    .action-buttons { display: flex; gap: 10px; white-space: nowrap; justify-content: center; align-items: center; }
    .action-buttons .btn { margin: 0; }
  </style>
</head>
<body>
<header id="header">
  <jsp:include page="/view/common/header-top.jsp"/>
  <jsp:include page="/view/common/header-middle.jsp"/>
  <jsp:include page="/view/common/header-bottom.jsp"/>
</header>

<section id="skill-list" class="animated fadeInUp">
  <div class="container">
    <div class="row">
      <div class="col-sm-12">
        <h2 class="title text-center animated fadeInDown">Skills Management</h2>
        <c:if test="${not empty error}">
          <div class="alert alert-danger text-center animated shake">${error}</div>
        </c:if>
        <div class="row">
          <div class="col-md-12">
            <form action="${pageContext.request.contextPath}/skill" method="GET" class="form-inline pull-left animated slideInLeft" style="margin-bottom: 20px;">
              <div class="form-group">
                <input type="text" name="search" class="form-control" placeholder="Search skills..." value="${search}">
              </div>
              <button type="submit" class="btn btn-default get"><i class="fa fa-search"></i></button>
              <input type="hidden" name="sort" value="${sort}">
              <input type="hidden" name="page" value="${page}">
            </form>
            <a href="${pageContext.request.contextPath}/skill?action=form" class="btn btn-default get pull-right animated slideInRight">Add New Skill</a>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <table class="table table-bordered">
              <thead>
              <tr>
                <th>ID</th>
                <th><a href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort == 'asc' ? 'desc' : 'asc'}&page=${page}" class="animated slideInUp">Skill Name <i class="fa ${sort == 'asc' ? 'fa-sort-asc' : 'fa-sort-desc'}"></i></a></th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${list}" var="skill" varStatus="loop">
                <tr class="animated slideInRight" style="animation-delay: ${loop.index * 0.1}s;">
                  <td>${skill.skillId}</td>
                  <td>${skill.skillName}</td>
                  <td>
                    <div class="action-buttons">
                      <a href="${pageContext.request.contextPath}/skill?action=form&id=${skill.skillId}" class="btn btn-default get btn-sm">Edit</a>
                      <a href="#" class="btn btn-default btn-sm delete-btn" data-id="${skill.skillId}" data-toggle="modal" data-target="#deleteModal">Delete</a>
                    </div>
                  </td>
                </tr>
              </c:forEach>
              <c:if test="${empty list}">
                <tr><td colspan="3" class="text-center animated fadeIn">No skills found.</td></tr>
              </c:if>
              </tbody>
            </table>
          </div>
        </div>
        <c:if test="${totalPages > 1}">
          <div class="row">
            <div class="col-md-12 text-center">
              <ul class="pagination animated fadeInUp">
                <c:if test="${page > 1}">
                  <li><a href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort}&page=${page - 1}" class="animated">←</a></li>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="i">
                  <li class="${page == i ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort}&page=${i}" class="animated">${i}</a>
                  </li>
                </c:forEach>
                <c:if test="${page < totalPages}">
                  <li><a href="${pageContext.request.contextPath}/skill?search=${search}&sort=${sort}&page=${page + 1}" class="animated">→</a></li>
                </c:if>
              </ul>
            </div>
          </div>
        </c:if>
      </div>
    </div>
  </div>
</section>

<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content animated zoomIn">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span>×</span></button>
        <h4 class="modal-title">Confirm Delete</h4>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to delete this skill?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <a href="#" id="confirmDelete" class="btn btn-default get">Delete</a>
      </div>
    </div>
  </div>
</div>

<jsp:include page="/view/common/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
  const contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/js/search.js"></script>
<script>
  $(document).ready(function() {
    // Debounce function to prevent multiple clicks
    function debounce(func, wait) {
      let timeout;
      return function() {
        clearTimeout(timeout);
        timeout = setTimeout(func.bind(this), wait);
      };
    }

    // Staggered animation for table rows
    $('table tbody tr').each(function(index) {
      $(this).css('animation-delay', (index * 0.1) + 's');
    });

    // Animate search input on focus
    $('.form-control').on('focus', function() {
      $(this).css('box-shadow', '0 0 8px rgba(0, 123, 255, 0.5)');
    }).on('blur', function() {
      $(this).css('box-shadow', 'none');
    });

    // Prevent animation flicker on buttons
    $('.btn, .pagination a').on('animationend', function() {
      $(this).removeClass('animated bounceIn');
    });

    // Scroll-based animation
    let isScrolling = false;
    $(window).on('scroll', debounce(function() {
      if ($(window).scrollTop() > 50 && !isScrolling) {
        isScrolling = true;
        $('#skill-list').addClass('animated tada');
        setTimeout(function() {
          $('#skill-list').removeClass('animated tada');
          isScrolling = false;
        }, 1000);
      }
    }, 200));

    // Table row hover effect
    $('table tr').on('mouseenter', function() {
      $(this).css('background-color', '#f5f5f5');
    }).on('mouseleave', function() {
      $(this).css('background-color', '');
    });

    // Modal animation on show/hide
    $('#deleteModal').on('show.bs.modal', function() {
      $('.modal-content').removeClass('zoomOut').addClass('animated zoomIn');
    }).on('hide.bs.modal', function() {
      $('.modal-content').removeClass('zoomIn').addClass('animated zoomOut');
    });

    // Delete button logic with loading state
    let isChecking = false;
    $('.delete-btn').on('click', debounce(function() {
      if (isChecking) return;
      var id = $(this).data('id');
      isChecking = true;
      $(this).addClass('loading');
      $('#confirmDelete').attr('href', '${pageContext.request.contextPath}/skill?action=delete&id=' + id + '&search=${search}&sort=${sort}&page=${page}');
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
            $('#confirmDelete').show();
          }
        },
        complete: function() {
          isChecking = false;
          $('.delete-btn').removeClass('loading');
        }
      });
    }, 200));
  });
</script>
</body>
</html>