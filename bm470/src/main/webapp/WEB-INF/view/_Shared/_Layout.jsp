<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

  <!-- Favicons -->
  <link href="../../../static/assets/img/favicon.png" rel="icon">
  <link href="../../../static/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Font Awesome CDN -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

  <!-- Fonts -->
  <link href="https://fonts.googleapis.com" rel="preconnect">
  <link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&family=Inter:wght@100;200;300;400;500;600;700;800;900&family=Nunito:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="../../../static/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="../../../static/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="../../../static/assets/vendor/aos/aos.css" rel="stylesheet">
  <link href="../../../static/assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
  <link href="../../../static/assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

  <!-- Main CSS File -->
  <link href="../../../static/assets/css/main.css" rel="stylesheet">

  <title>${layoutName}</title>
</head>
<body >
<header id="header" class="header d-flex align-items-center fixed-top ">
  <div class="header-container container-fluid container-xl position-relative d-flex align-items-center justify-content-between">

    <a href="/" class="logo d-flex align-items-center me-auto me-xl-0">

      <img src="../assets/img/logo.png" alt="">
      <h1 class="sitename">${layoutName}</h1>
    </a>

    <nav id="navmenu" class="navmenu">
      <ul>
        <li class="dropdown"><a href="#"><span>${layoutAbout}</span> <i class="bi bi-chevron-down toggle-dropdown"></i></a>
          <ul>
            <li><a href="/history">${layoutHistory}</a></li>
            <li><a href="/mission">${layoutMission}</a></li>
            <li><a href="/vision">${layoutVision}</a></li>
          </ul>
        </li>
        <li class="dropdown"><a href="#hero" ><span>${layoutCollection}</span><i class="bi bi-chevron-down toggle-dropdown"></i></a>
          <ul>
            <li><a href="/Books">${layoutAllBooks}</a></li>
          </ul>
        </li>
        <li><a href="/contact">${layoutContact}</a></li>

      </ul>
      <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
    </nav>
    <div class="d-flex align-items-center justify-content-end gap-3">

      <!-- Sepet İkonu -->
      <a href="/Cart">
        <i class="fas fa-shopping-cart"></i>
      </a>


      <%
        tr.edu.duzce.mf.bm.entity.Person user = (tr.edu.duzce.mf.bm.entity.Person) session.getAttribute("loggedInUser");
        if (user != null) {
      %>
      <div class="dropdown">
        <button class="btn btn-primary dropdown-toggle" type="button" id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
          <%= user.getFirstName() %>
        </button>
        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
          <li><a class="dropdown-item" href="/profile">${layoutProfile}</a></li>
          <li><a class="dropdown-item" href="/Favorites">${layoutFavorite}</a></li>
          <li><a class="dropdown-item" href="/RentalsList">${layoutRental}</a></li>
          <li><a class="dropdown-item" href="/returned">${layoutBookPast}</a></li>

          <li><hr class="dropdown-divider"></li>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">${layoutSignOut}</a></li>

        </ul>
      </div>
      <%
      } else {
      %>
      <a class="btn btn-outline-primary" href="/login">${layoutLogin}</a>
      <%
        }
      %>

      <div class="language-switcher">
        <a href="?lang=tr_TR">Türkçe</a> |
        <a href="?lang=en_US">English</a>
      </div>
    </div>



  </div>

</header>
<div id="content">
  <jsp:include page="${body}" />
</div>
<footer id="footer" class="footer" style="margin-top: 100px;">

  <div class="container footer-top">
    <div class="row gy-4">
      <div class="col-lg-4 col-md-6 footer-about">
        <a href="/Login" class="logo d-flex align-items-center">
          <span class="sitename">${layoutName}</span>
        </a>
        <div class="footer-contact pt-3">
          <p>${layoutUniversity}</p>
          <p>${layoutLocation}</p>
          <p class="mt-3"><strong>Tel:</strong> <span>${layoutNumber}</span></p>
          <p><strong>${layoutEmail}</strong> <span>${layoutEmailAnswer}</span></p>
        </div>
        <div class="social-links d-flex mt-4">
          <a href=""><i class="bi bi-twitter-x"></i></a>
          <a href=""><i class="bi bi-facebook"></i></a>
          <a href=""><i class="bi bi-instagram"></i></a>
          <a href=""><i class="bi bi-linkedin"></i></a>
        </div>
      </div>

      <div class="col-lg-2 col-md-3 footer-links">
        <h4>${layoutAbout}</h4>
        <ul>
          <li><a href="/mission">${layoutMission}</a></li>
          <li><a href="/vision">${layoutVision}</a></li>
          <li><a href="/history">${layoutHistory}</a></li>
        </ul>
      </div>


    </div>
  </div>

  <div class="container copyright text-center mt-4">
    <p>© ${layoutRights}</p>
    <div class="credits">

    </div>
  </div>

</footer>

<!-- Scroll Top -->
<a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

<!-- Vendor JS Files -->
<script src="../../../static/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="../../../static/assets/vendor/php-email-form/validate.js"></script>
<script src="../../../static/assets/vendor/aos/aos.js"></script>
<script src="../../../static/assets/vendor/glightbox/js/glightbox.min.js"></script>
<script src="../../../static/assets/vendor/swiper/swiper-bundle.min.js"></script>
<script src="../../../static/assets/vendor/purecounter/purecounter_vanilla.js"></script>

<!-- Main JS File -->
<script src="../../../static/assets/js/main.js"></script>
<script src="../../static/components/navbar.js"></script>

</body>
</html>
