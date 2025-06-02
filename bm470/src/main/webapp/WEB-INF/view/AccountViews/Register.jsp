<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="tr">
<head>
  <meta charset="UTF-8">
  <title>${registerSignup}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap 5 CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- FontAwesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
  <!-- Özel CSS -->
</head>


<body class="bg-light" >

<style>
  .toggle-password {
    position: absolute;
    top: 50%;
    right: 10px;
    transform: translateY(-50%);
    cursor: pointer;
    color: #666;
    z-index: 2;
  }

</style>
<div class="container py-5">
  <div class="row justify-content-center align-items-center">
    <div class="col-md-10 col-lg-8 shadow-lg rounded-4 bg-white p-4">
      <div class="row g-4 align-items-center">
        <!-- Görsel -->
        <div class="col-md-6 text-center">
          <img src="${pageContext.request.contextPath}/static/images/signup-image.jpg" alt="Kayıt ol görseli" class="img-fluid rounded-3">
        </div>

        <!-- Form -->
        <div class="col-md-6">
          <h3 class="fw-bold mb-3 text-center">${registerSignup}</h3>

          <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
          </c:if>

          <form:form method="POST" action="${pageContext.request.contextPath}/register" modelAttribute="person">
            <div class="mb-3 position-relative">
              <form:input path="tckn" cssClass="form-control" id="tckn" placeholder="${registerTCKN}" required="true" maxlength="11" pattern="^\d{11}$" title="${registerTCKNError}"/>
              <form:errors path="tckn" cssClass="text-danger small"/>
            </div>
            <div class="mb-3 position-relative">
              <form:input path="firstName" cssClass="form-control" id="firstName" placeholder="${registerFirstname}" required="true"  pattern="^[a-zA-ZğüşöçİĞÜŞÖÇ]{3,}$" title="${registerFirstnameError}"/>
              <form:errors path="firstName" cssClass="text-danger small"/>
            </div>
            <div class="mb-3 position-relative">
              <form:input path="lastName" cssClass="form-control" id="lastName" placeholder="${registerLastname}" required="true"  pattern="^[a-zA-ZğüşöçİĞÜŞÖÇ]{3,}$"  title="${registerLastnameError}"/>
              <form:errors path="lastName" cssClass="text-danger small"/>
            </div>
            <div class="mb-3 position-relative">
              <form:input path="email" type="email" cssClass="form-control" id="email" placeholder="${registerEmail}" required="true"/>
              <form:errors path="email" cssClass="text-danger small"/>
            </div>
            <div class="mb-3">
              <input type="text" name="telnumber" id="telnumber" class="form-control" placeholder="${registerPhoneNumber}" required   pattern="^\d{10,11}$"   title="${registerPhonenumberError}"/>
              <c:if test="${not empty telnumberError}">
                <span class="text-danger small">${telnumberError}</span>
              </c:if>
            </div>
            <div class="mb-3">
              <form:select path="gender" cssClass="form-select" id="gender" required="true">
                <form:option value="" disabled="true">${registerGender}</form:option>
                <form:option value="Female">${registerGenderWomen}</form:option>
                <form:option value="Male">${registerGenderMan}</form:option>
              </form:select>
              <form:errors path="gender" cssClass="text-danger small"/>
            </div>
            <div class="mb-3">
              <input type="date" name="birthday" id="birthday" class="form-control" required/>
              <c:if test="${not empty birthdayError}">
                <span class="text-danger small">${birthdayError}</span>
              </c:if>
            </div>
            <div class="form-group position-relative align-items-center d-flex">
              <form:input path="password" id="pass" type="password" cssClass="form-control" placeholder="${registerPassword}" required="true"/>
              <span class="toggle-password " style=""  onclick="togglePasswordVisibility('pass', this)">
              <i class="fas fa-eye"></i>
              </span>
              <form:errors path="password" cssClass="error"/>
              <c:if test="${not empty passwordError}">
                <span class="error">${passwordError}</span>
              </c:if>
            </div>

            <div class="form-group position-relative align-items-center d-flex">
              <input type="password" name="re_pass" id="re_pass" class="form-control" placeholder="${registerPasswordAgain}" required/>
              <span class="toggle-password" onclick="togglePasswordVisibility('re_pass', this)">
               <i class="fas fa-eye"></i>
               </span>
            </div>

            <div class="mb-3 form-check">
              <input type="checkbox" class="form-check-input" name="agree-term" id="agree-term" required/>
              <label class="form-check-label" for="agree-term">
                <a href="#" class="text-decoration-underline" data-bs-toggle="modal" data-bs-target="#contractModal">
                    ${registerContract}
                </a>
              </label>
              <c:if test="${not empty agreeTermError}">
                <span class="text-danger small d-block">${agreeTermError}</span>
              </c:if>
            </div>
            <div class="d-grid mb-3">
              <input type="submit" class="btn btn-primary rounded-pill" value="${registerSignup}"/>
            </div>
            <div class="text-center small">
                ${registerAlreadyAccount} <a href="/login">${registerLogin}</a>
            </div>
          </form:form>
          <div class="language-switcher d-flex align-items-center gap-3">
            <a href="?lang=tr_TR" title="Türkçe">
              <img src="https://flagcdn.com/w20/tr.png" alt="Türkçe" width="20" height="15" style="border-radius:3px;">
            </a>
            <a href="?lang=en_US" title="English">
              <img src="https://flagcdn.com/w20/us.png" alt="English" width="20" height="15" style="border-radius:3px;">
            </a>
          </div>

          <form:form method="POST"
                     action="${pageContext.request.contextPath}/register"
                     modelAttribute="person">
            <form:hidden path="banned" value="false"></form:hidden>
          </form:form>

        </div>
      </div>
    </div>
  </div>
</div>
<!-- Modal (Sözleşme Penceresi) -->
<div class="modal fade" id="contractModal" tabindex="-1" aria-labelledby="contractModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="contractModalLabel">${registerUserContract}</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label=${close}></button>
      </div>
      <div class="modal-body">
        <h6> ${registerContractOne}</h6>
        <p>${registerContractOneOne}</p>
        <h6>${registerContractTwo}</h6>
        <ul>
          <li>${registerContractTwoOne}</li>
          <li>${registerContractTwoTwo}</li>
          <li>${registerContractTwoThree}</li>
        </ul>
        <h6>${registerContractThree}</h6>
        <ul>
          <li>${registerContractThreeOne}</li>
          <li>${registerContractThreeTwo}</li>
          <li>${registerContractThreeThree}</li>
        </ul>
        <h6> ${registerContractFour}</h6>
        <p>${registerContractFourOne}</p>
        <h6>${registerContractFive}</h6>
        <p>${registerContractFiveOne}</p>
        <p class="mt-4"><strong>${registerContractAccept}</strong></p>
      </div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">${close}</button>
    </div>
  </div>
</div>
</div>

<!-- Bootstrap JS (opsiyonel) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  function togglePasswordVisibility(inputId, toggleElement) {
    const input = document.getElementById(inputId);
    const icon = toggleElement.querySelector('i');
    if (input.type === "password") {
      input.type = "text";
      icon.classList.remove('fa-eye');
      icon.classList.add('fa-eye-slash');
    } else {
      input.type = "password";
      icon.classList.remove('fa-eye-slash');
      icon.classList.add('fa-eye');
    }
  }
</script>

</body>
</html>
