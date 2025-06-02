<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <title>${librarianProfile}</title>

</head>

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6 bg-white shadow rounded-4 p-4">
            <div class="text-center mb-4">
                <h2 class="mt-3 fw-bold">${librarianTitle}</h2>
            </div>

            <!-- Hata veya başarı mesajları -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/update" method="post" novalidate>
                <input type="hidden" name="TCKN" value="${user.tckn}"/>

                <div class="mb-3">
                    <label for="TCKN" class="form-label fw-semibold">${librarianTCKN}</label>
                    <input type="text" id="TCKN" value="${user.tckn}" readonly class="form-control-plaintext">
                </div>

                <div class="mb-3">
                    <label for="name" class="form-label fw-semibold">${librarianName}</label>
                    <input type="text" id="name" name="firstName" value="${user.firstName}" required class="form-control" placeholder="İsim giriniz">
                </div>

                <div class="mb-3">
                    <label for="lastName" class="form-label fw-semibold">${librarianName}</label>
                    <input type="text" id="lastName" name="lastName" value="${user.lastName}" required class="form-control" placeholder="Soyisim giriniz">
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label fw-semibold">${librarianEmail}</label>
                    <input type="email" id="email" name="email" value="${user.email}" required class="form-control" placeholder="E-posta adresi">
                </div>

                <div class="mb-3">
                    <label for="gender" class="form-label fw-semibold">${librarianGender}</label>
                    <select id="gender" name="gender" class="form-select" required>
                        <option value="" disabled ${user.gender == null ? "selected" : ""}>Seçiniz</option>
                        <option value="Kadın" ${user.gender == 'Kadın' ? 'selected' : ''}>Kadın</option>
                        <option value="Erkek" ${user.gender == 'Erkek' ? 'selected' : ''}>Erkek</option>
                    </select>
                </div>

                <div class="mb-3">

                    <input type="hidden" name="birthday" value="${user.birthday}"/>
                </div>

                <div class="mb-3">
                    <label for="phoneNumber" class="form-label fw-semibold">${librarianNumber}</label>
                    <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber != null ? user.phoneNumber : ''}" class="form-control" placeholder="Telefon numarası">
                </div>


                <div class="mb-3">
                    <label for="password" class="form-label fw-semibold">${librarianPassword}</label>
                    <input type="text" id="password" name="password" value="${user.password != null ? user.password : ''}" required class="form-control" placeholder="Şifre">
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-primary rounded-pill">${librarianChange}</button>
                </div>
            </form>
        </div>
    </div>
</div>


<script>
    window.addEventListener('DOMContentLoaded', function () {
        const header = document.getElementById('header');
        if (header) {
            header.style.position = 'relative';
        }
    });
</script>