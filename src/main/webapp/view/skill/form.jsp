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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>${skill.skillId > 0 ? 'Edit Skill' : 'Add New Skill'} | Natural Care</title>
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
        .form-group { animation-delay: 0.2s; }
        .btn { transition: transform 0.3s ease, background-color 0.3s ease; }
        .btn:hover { transform: scale(1.1); }
        .error { animation: shake 0.5s; }
        .form-control:focus { transition: box-shadow 0.3s ease; box-shadow: 0 0 8px rgba(0, 123, 255, 0.5); }
        .loading { opacity: 0.6; pointer-events: none; }
    </style>
</head>
<body>
<header id="header">
    <jsp:include page="/view/common/header-top.jsp"/>
    <jsp:include page="/view/common/header-middle.jsp"/>
    <jsp:include page="/view/common/header-bottom.jsp"/>
</header>

<section id="skill-form" class="animated fadeInUp">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <h2 class="title text-center animated fadeInDown">${skill.skillId > 0 ? 'Edit Skill' : 'Add New Skill'}</h2>
                <div class="col-md-6 col-md-offset-3">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger animated shake">${error}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/skill" method="POST" class="form-horizontal">
                        <input type="hidden" name="skill_id" value="${skill.skillId}">
                        <div class="form-group animated slideInLeft">
                            <label for="skillName" class="col-sm-3 control-label">Skill Name</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="skillName" name="skill_name" value="${skill.skillName}" placeholder="Enter skill name" required>
                            </div>
                        </div>
                        <div class="form-group animated slideInUp">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" class="btn btn-default get">Submit</button>
                                <a href="${pageContext.request.contextPath}/skill" class="btn btn-default">Cancel</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

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

        // Animate form elements on load with stagger
        $('.form-group').each(function(index) {
            $(this).css('animation-delay', (index * 0.2) + 's');
        });

        // Animate input on focus
        $('#skillName').on('focus', function() {
            $(this).css('box-shadow', '0 0 8px rgba(0, 123, 255, 0.5)');
        }).on('blur', function() {
            $(this).css('box-shadow', 'none');
        });

        // Prevent animation flicker on buttons
        $('.btn').on('animationend', function() {
            $(this).removeClass('animated bounceIn');
        });

        // Scroll-based animation
        let isScrolling = false;
        $(window).on('scroll', debounce(function() {
            if ($(window).scrollTop() > 50 && !isScrolling) {
                isScrolling = true;
                $('#skill-form').addClass('animated tada');
                setTimeout(function() {
                    $('#skill-form').removeClass('animated tada');
                    isScrolling = false;
                }, 1000);
            }
        }, 200));

        // Duplicate skill check with loading state
        let isChecking = false;
        $('#skillName').on('input', debounce(function() {
            if (isChecking) return;
            var skillName = $(this).val().trim();
            var skillId = $('input[name="skill_id"]').val() || 0;
            if (skillName.length >= 3) {
                isChecking = true;
                $('#skillName').addClass('loading');
                $.ajax({
                    url: '${pageContext.request.contextPath}/skill',
                    type: 'POST',
                    data: { action: 'checkDuplicate', skill_name: skillName, skill_id: skillId },
                    success: function(response) {
                        if (response === 'duplicate') {
                            $('#skillName').next('.error').remove();
                            $('#skillName').after('<span class="error text-danger animated shake">Skill name already exists.</span>');
                        } else {
                            $('#skillName').next('.error').remove();
                        }
                    },
                    complete: function() {
                        isChecking = false;
                        $('#skillName').removeClass('loading');
                    }
                });
            }
        }, 300));

        // Debounce form submission
        $('form').on('submit', debounce(function(e) {
            if ($(this).find('.btn').hasClass('loading')) {
                e.preventDefault();
            } else {
                $(this).find('.btn').addClass('loading');
            }
        }, 200));
    });
</script>
</body>
</html>