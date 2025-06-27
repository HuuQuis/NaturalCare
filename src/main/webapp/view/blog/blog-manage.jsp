<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="page-wrapper">
  <div class="content">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h4 class="fw-bold">Blog Management</h4>
      <a class="btn btn-primary" href="blog-manage?action=form&page=${page}">
        <i class="fa fa-plus me-1"></i> Add Blog
      </a>
    </div>

    <!-- Alert Message -->
    <c:if test="${not empty sessionScope.message}">
      <div id="autoAlert" class="alert alert-${sessionScope.messageType} alert-dismissible fade show"
           style="position: fixed; top: 20px; right: 20px; z-index: 1055; min-width: 300px;" role="alert">
          ${sessionScope.message}
        <button type="button" class="close" data-bs-dismiss="alert" aria-label="Close">
          <i class="fa fa-times"></i>
        </button>
      </div>
      <c:remove var="message" scope="session"/>
      <c:remove var="messageType" scope="session"/>
    </c:if>

    <!-- Table -->
    <!-- Filter Form -->
    <form method="get" action="blog-manage" class="d-flex flex-wrap gap-2 mb-3">
      <!-- Dropdown Category -->
      <select name="categoryId" class="form-select" style="max-width: 200px;">
        <option value="-1" ${selectedCategory == -1 ? 'selected' : ''}>All Categories</option>
        <c:forEach var="cat" items="${categories}">
          <option value="${cat.id}" ${selectedCategory == cat.id ? 'selected' : ''}>${cat.name}</option>
        </c:forEach>
      </select>
      &nbsp;&nbsp;
      <button type="submit" class="btn btn-primary">Filter</button>
      &nbsp;&nbsp;
      <!-- Optional: Search by title -->
      <input type="text" name="keyword" class="form-control" placeholder="Search by title..." value="${param.keyword}" style="max-width: 300px;"/>

    </form>
    <div class="table-responsive">
      <table class="table table-hover table-bordered rounded shadow-sm bg-white">
        <thead class="table-light">
        <tr>
          <th>No.</th>
          <th>Title</th>
          <th>Category</th>
          <th>Description</th>
          <th>Image</th>
          <th>Published</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="b" items="${list}" varStatus="loop">
          <tr>
            <td>${startIndex + loop.index + 1}</td>
            <td>${b.blogTitle}</td>
            <td>${b.blogCategory.name}</td>
            <td title="${b.blogDescription}">
              <c:choose>
                <c:when test="${fn:length(b.blogDescription) > 20}">
                  ${fn:substring(b.blogDescription, 0, 20)}...
                </c:when>
                <c:otherwise>
                  ${b.blogDescription}
                </c:otherwise>
              </c:choose>
            </td>
            <td>
              <c:if test="${not empty b.imageUrl}">
                <img src="${pageContext.request.contextPath}/${b.imageUrl}"
                     width="100" height="70" class="img-thumbnail"
                     data-bs-toggle="modal" data-bs-target="#imageModal"
                     onclick="showImageModal('${pageContext.request.contextPath}/${b.imageUrl}')"/>
              </c:if>
              <c:if test="${empty b.imageUrl}">
                <span class="text-muted fst-italic">No image</span>
              </c:if>
            </td>
            <td>${b.datePublished}</td>
            <td>
              <a href="blog-manage?action=form&id=${b.blogId}&page=${page}" class="text-primary">
                <i class="fa fa-edit"></i>
              </a>
              &nbsp;
              <form method="post" action="blog-manage" onsubmit="return confirm('Delete this blog?');">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="${b.blogId}"/>
                <input type="hidden" name="page" value="${page}"/>
                <button type="submit" class="btn btn-link text-danger p-0">
                  <i class="fa fa-trash"></i>
                </button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty list}">
          <tr><td colspan="7" class="text-center text-muted fst-italic">No blogs found.</td></tr>
        </c:if>
        </tbody>
      </table>

      <!-- Image Modal -->
      <div class="modal fade" id="imageModal" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
          <div class="modal-content">
            <div class="modal-body text-center">
              <img id="modalImage" src="" class="img-fluid rounded" alt="Blog Image"/>
            </div>
          </div>
        </div>
      </div>

      <!-- Pagination -->
      <div class="d-flex justify-content-end mt-3">
        <ul class="pagination">
          <c:forEach var="i" begin="1" end="${totalPage}">
            <li class="page-item ${i == page ? 'active' : ''}">
              <a class="page-link"
                 href="blog-manage?page=${i}&categoryId=${selectedCategory}&keyword=${fn:escapeXml(param.keyword)}">
                  ${i}
              </a>
            </li>
          </c:forEach>
        </ul>
      </div>
    </div>
  </div>
</div>

<script>
  window.onload = function () {
    const alertBox = document.getElementById("autoAlert");
    if (alertBox) {
      setTimeout(() => {
        const alertInstance = bootstrap.Alert.getOrCreateInstance(alertBox);
        alertInstance.close();
      }, 3000);
    }
  };

  function showImageModal(imageUrl) {
    document.getElementById("modalImage").src = imageUrl;
  }
</script>