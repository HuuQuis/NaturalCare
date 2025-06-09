<%--
  Created by IntelliJ IDEA.
  User: telamon
  Date: 26/5/25
  Time: 22:00
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
    <title>${skill.skillId > 0 ? 'Edit Skill' : 'Add New Skill'} | Natural Care</title>

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
                    <h4>${skill.skillId > 0 ? 'Edit Skill' : 'Add New Skill'}</h4>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">${error}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/skill" method="post" onsubmit="return validateSkillForm()">
                        <input type="hidden" name="skill_id" id="skillId" value="${skill.skillId}">
                        <div class="row">
                            <div class="col-lg-12 col-sm-12 col-12">
                                <div class="form-group">
                                    <label>Skill Name</label>
                                    <input type="text" class="form-control" id="skillName" name="skill_name" value="${skill.skillName}" placeholder="Enter skill name" required>
                                    <span class="text-danger d-none" id="duplicateError">Skill name already exists</span>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <button type="submit" class="btn btn-submit me-2">Save</button>
                                <a href="${pageContext.request.contextPath}/skill" class="btn btn-cancel">Cancel</a>
                            </div>
                        </div>
                    </form>
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
    function validateSkillForm() {
        const skillName = document.getElementById('skillName').value.trim();
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