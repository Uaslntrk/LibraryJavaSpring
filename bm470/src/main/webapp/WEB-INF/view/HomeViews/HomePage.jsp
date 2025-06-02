<%@ page contentType="text/html;charset=UTF-8" language="java" %>




<main class="main" >
  <!-- Hero Section -->
  <section id="hero" class="hero section">

    <div class="container" data-aos="fade-up" data-aos-delay="100">

      <div class="row align-items-center">
        <div class="col-lg-6">
          <div class="hero-content" data-aos="fade-up" data-aos-delay="200">
            <div class="company-badge mb-4">
              <i class="bi bi-gear-fill me-2"></i>
              ${heroBadge}
            </div>

            <h1 class="mb-4">
              ${heroTitle1} <br>
              ${heroTitle2} <br>
              <span class="accent-text">${heroTitle3}</span>
            </h1>

            <p class="mb-4 mb-md-5">
              ${heroDescription}
            </p>


          </div>
        </div>

        <div class="col-lg-6">
          <div class="hero-image" data-aos="zoom-out" data-aos-delay="300">
            <img src="../../static/images/library.jpg" alt="Hero Image" class="img-fluid">
          </div>
        </div>
      </div>

      <div class="row stats-row gy-4 mt-5" data-aos="fade-up" data-aos-delay="500">
        <div class="col-lg-3 col-md-6">
          <div class="stat-item">
            <div class="stat-icon">
              <i class="bi bi-trophy"></i>
            </div>
            <div class="stat-content">
              <h4>${statsBooks}</h4>
              <p class="mb-0">${statsBooksDesc}</p>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-6">
          <div class="stat-item">
            <div class="stat-icon">
              <i class="bi bi-briefcase"></i>
            </div>
            <div class="stat-content">
              <h4>${statsaAuthors}</h4>
              <p class="mb-0">${statsAuthorsDesc}</p>
            </div>
          </div>
        </div>
        <div class="col-lg-3 col-md-6">
          <div class="stat-item">
            <div class="stat-icon">
              <i class="bi bi-graph-up"></i>
            </div>
            <div class="stat-content">
              <h4>${statsCatagories}</h4>
              <p class="mb-0">${statsCatagoriesDesc}</p>
            </div>
          </div>
        </div>

      </div>

    </div>

  </section><!-- /Hero Section -->



  <!-- Features Section -->
  <section id="features" class="features section">

    <!-- Section Title -->
    <div class="container section-title" data-aos="fade-up">
      <h2>${serviesTitle}</h2>
    </div><!-- End Section Title -->

    <div class="container">

      <div class="d-flex justify-content-center">

        <ul class="nav nav-tabs" data-aos="fade-up" data-aos-delay="100">

          <li class="nav-item">
            <a class="nav-link active show" data-bs-toggle="tab" data-bs-target="#features-tab-1">
              <h4>${servicesTab1}</h4>
            </a>
          </li><!-- End tab nav item -->

          <li class="nav-item">
            <a class="nav-link" data-bs-toggle="tab" data-bs-target="#features-tab-2">
              <h4>${servicesTab2}</h4>
            </a><!-- End tab nav item -->

          </li>
          <li class="nav-item">
            <a class="nav-link" data-bs-toggle="tab" data-bs-target="#features-tab-3">
              <h4>${servicesTab3}</h4>
            </a>
          </li><!-- End tab nav item -->

        </ul>

      </div>

      <div class="tab-content" data-aos="fade-up" data-aos-delay="200">

        <div class="tab-pane fade active show" id="features-tab-1">
          <div class="row">
            <div class="col-lg-6 order-2 order-lg-1 mt-3 mt-lg-0 d-flex flex-column justify-content-center">
              <h3>${tab1Title}</h3>
              <p class="fst-italic">
                ${tab1Description}
              </p>
              <ul>
                <li><i class="bi bi-check2-all"></i> <span>${tab1Point1}</span></li>
                <li><i class="bi bi-check2-all"></i> <span>${tab1Point2}</span></li>
                <li><i class="bi bi-check2-all"></i> <span>${tab1Point3}</span></li>
              </ul>
            </div>
            <div class="col-lg-6 order-1 order-lg-2 text-center">
              <img src="../../static/images/historylib.jpg" alt="" class="img-fluid">
            </div>
          </div>
        </div><!-- End tab content item -->

        <div class="tab-pane fade" id="features-tab-2">
          <div class="row">
            <div class="col-lg-6 order-2 order-lg-1 mt-3 mt-lg-0 d-flex flex-column justify-content-center">
              <h3>${tab2Title}</h3>
              <p class="fst-italic">
                ${tab2Description}
              </p>
              <ul>
                <li><i class="bi bi-check2-all"></i> <span> ${tab2Point1}</span></li>
                <li><i class="bi bi-check2-all"></i> <span>${tab2Point2}</span></li>
              </ul>
            </div>
            <div class="col-lg-6 order-1 order-lg-2 text-center">
              <img src="../../static/images/library.jpg" alt="" class="img-fluid">
            </div>
          </div>
        </div><!-- End tab content item -->

        <div class="tab-pane fade" id="features-tab-3">
          <div class="row">
            <div class="col-lg-6 order-2 order-lg-1 mt-3 mt-lg-0 d-flex flex-column justify-content-center">
              <h3>${tab3Title}</h3>
              <ul>
                <li><i class="bi bi-check2-all"></i> <span>${tab3Point1}</span></li>
                <li><i class="bi bi-check2-all"></i> <span>${tab3Point2}</span></li>
              </ul>
              <p class="fst-italic">
                ${tab3Description}
              </p>
            </div>
            <div class="col-lg-6 order-1 order-lg-2 text-center">
              <img src="../../static/images/historylib.jpg" alt="" class="img-fluid">
            </div>
          </div>
        </div><!-- End tab content item -->

      </div>

    </div>

  </section><!-- /Features Section -->









  <!-- Stats Section -->
  <section id="stats" class="stats section">

    <div class="container" data-aos="fade-up" data-aos-delay="100">

      <div class="row gy-4">

        <div class="col-lg-3 col-md-6">
          <div class="stats-item text-center w-100 h-100">
            <span data-purecounter-start="0" data-purecounter-end="232" data-purecounter-duration="1" class="purecounter"></span>
            <p>${statsEncyclopedia}</p>
          </div>
        </div><!-- End Stats Item -->

        <div class="col-lg-3 col-md-6">
          <div class="stats-item text-center w-100 h-100">
            <span data-purecounter-start="0" data-purecounter-end="521" data-purecounter-duration="1" class="purecounter"></span>
            <p>${statsNovels}</p>
          </div>
        </div><!-- End Stats Item -->

        <div class="col-lg-3 col-md-6">
          <div class="stats-item text-center w-100 h-100">
            <span data-purecounter-start="0" data-purecounter-end="1453" data-purecounter-duration="1" class="purecounter"></span>
            <p>${statsScifi}</p>
          </div>
        </div><!-- End Stats Item -->

        <div class="col-lg-3 col-md-6">
          <div class="stats-item text-center w-100 h-100">
            <span data-purecounter-start="0" data-purecounter-end="32" data-purecounter-duration="1" class="purecounter"></span>
            <p>${statsHorror}</p>
          </div>
        </div><!-- End Stats Item -->

      </div>

    </div>

  </section><!-- /Stats Section -->

</main>
</html>