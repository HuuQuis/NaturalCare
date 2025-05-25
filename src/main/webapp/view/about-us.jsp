<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 5/23/2025
  Time: 9:48 PM
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
    <title>About Us | Natural Care</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/prettyPhoto.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/price-range.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-57-precomposed.png">

    <style>
        .about-content section {
            margin-bottom: 25px;
        }
        .about-content h3 {
            color: #FE980F;
            margin-bottom: 15px;
        }
        .about-content ul {
            list-style: disc;
            padding-left: 20px;
        }
        .about-content ul ul {
            list-style: circle;
        }
    </style>

</head><!--/head-->

<body>
<header id="header"><!--header-->
    <!--header_top-->
    <jsp:include page="common/header-top.jsp"></jsp:include>
    <!--/header_top-->

    <!--header-middle-->
    <jsp:include page="common/header-middle.jsp"></jsp:include>
    <!--/header-middle-->

    <!--header-bottom-->
    <jsp:include page="common/header-bottom.jsp"></jsp:include>
    <!--/header-bottom-->
</header><!--/header-->

<div id="contact-page" class="container">
    <div class="bg">
        <div class="row">
            <div class="col-sm-12">
                <h2 class="title text-center">About <strong>Us</strong></h2>
                <div class="about-content">
                    <section>
                        <h3>Giới thiệu về Natural Care</h3>
                        <p>Chào mừng bạn đến với Natural Care, nơi chúng tôi cam kết mang đến những sản phẩm mỹ phẩm tự nhiên chất lượng cao, được thiết kế để nâng niu làn da và tôn vinh vẻ đẹp tự nhiên của bạn. Được thành lập với sứ mệnh cung cấp giải pháp chăm sóc da an toàn và bền vững, Natural Care tự hào là điểm đến yêu thích của những ai tìm kiếm sự tinh tế và thân thiện với môi trường.</p>
                    </section>

                    <section>
                        <h3>Sứ mệnh và Giá trị</h3>
                        <ul>
                            <li><strong style="color:#FE980F;">Sứ mệnh:</strong> Cung cấp các sản phẩm mỹ phẩm từ thiên nhiên, không hóa chất độc hại, giúp khách hàng tự tin với làn da khỏe mạnh.</li>
                            <li><strong style="color:#FE980F;">Giá trị cốt lõi:</strong>
                                <ul>
                                    <li><strong style="color:#FE980F;">Tự nhiên:</strong> Sử dụng nguyên liệu hữu cơ, an toàn cho da và môi trường.</li>
                                    <li><strong style="color:#FE980F;">Chất lượng:</strong> Đảm bảo từng sản phẩm được kiểm định nghiêm ngặt.</li>
                                    <li><strong style="color:#FE980F;">Dịch vụ tận tâm:</strong> Đội ngũ chuyên gia và nhân viên luôn sẵn sàng hỗ trợ khách hàng.</li>
                                </ul>
                            </li>
                        </ul>
                    </section>

                    <section>
                        <h3>Đội ngũ của chúng tôi</h3>
                        <p>Tại Natural Care, chúng tôi có một đội ngũ chuyên nghiệp và tận tâm, bao gồm:</p>
                        <ul>
                            <li><strong style="color:#FE980F;">Quản lý:</strong> Đảm bảo vận hành trơn tru, quản lý sản phẩm, nhân viên và quy trình giao hàng.</li>
                            <li><strong style="color:#FE980F;">Chuyên gia tư vấn:</strong> Cung cấp lời khuyên cá nhân hóa để khách hàng chọn sản phẩm phù hợp.</li>
                            <li><strong style="color:#FE980F;">Nhân viên giao hàng:</strong> Đảm bảo sản phẩm đến tay bạn một cách nhanh chóng và an toàn, đồng thời hỗ trợ quy trình hoàn hàng nếu cần.</li>
                            <li><strong style="color:#FE980F;">Đội ngũ marketing:</strong> Tạo ra các chiến dịch sáng tạo để lan tỏa thông điệp của chúng tôi.</li>
                        </ul>
                    </section>

                    <section>
                        <h3>Hành trình của chúng tôi</h3>
                        <p>Natural Care bắt đầu như một ý tưởng nhỏ, với mong muốn kết nối thiên nhiên và sắc đẹp. Qua thời gian, chúng tôi đã phát triển thành một thương hiệu đáng tin cậy, cung cấp đa dạng sản phẩm từ kem dưỡng, serum đến các dòng mỹ phẩm trang điểm. Blog của chúng tôi là nơi chia sẻ kiến thức chăm sóc da, giúp khách hàng hiểu rõ hơn về cách sử dụng sản phẩm hiệu quả. Với đội ngũ chuyên gia và quy trình hoàn hàng minh bạch, chúng tôi cam kết đồng hành cùng bạn trên hành trình làm đẹp.</p>
                    </section>

                </div>

            </div>
        </div>
        <div class="row">

            <div>
                <div class="contact-info">
                    <h2 class="title text-center">Contact Info</h2>
                    <address>
                        <p>Nếu bạn muốn biết thêm về sản phẩm hoặc cần hỗ trợ, hãy liên hệ:</p>
                        <ul>
                            <li><strong>Email:</strong> naturalcare@info.com</li>
                            <li><strong>Điện thoại:</strong> +84 123 456 789</li>
                            <li><strong>Địa chỉ:</strong> Hola Park, Hoa Lac Hi-tech Park, Lm29, Thang Long Highway, Hanoi, Vietnam</li>
                        </ul>
                        <p>Hãy theo dõi blog của chúng tôi để cập nhật các mẹo làm đẹp và ưu đãi mới nhất!</p>
                    </address>
                    <div class="social-networks">
                        <h2 class="title text-center">Social Networking</h2>
                        <ul>
                            <li>
                                <a href="https://www.facebook.com/tung.lam.375757"><i class="fa fa-facebook"></i></a>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-twitter"></i></a>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-google-plus"></i></a>
                            </li>
                            <li>
                                <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ"><i class="fa fa-youtube"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div><!--/#contact-page-->

<!--Footer-->
<jsp:include page="common/footer.jsp"></jsp:include>
<!--/Footer-->



<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/gmaps.js"></script>
<script src="${pageContext.request.contextPath}/js/contact.js"></script>
<script src="${pageContext.request.contextPath}/js/price-range.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.scrollUp.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.prettyPhoto.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
