<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <title>${loginLogin}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>

    <style>
        body {
            font-family: 'Poppins', sans-serif;
        }
        .login-container {
            max-width: 900px;
            margin: 60px auto;
            background: #fff;
            border-radius: 1rem;
            overflow: hidden;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
        }
        .image-side {
            background-color: #f8f9fa;
        }
        .image-side img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .form-side {
            padding: 40px;
        }
        .form-control {
            border-radius: 0.75rem;
        }
        .form-icon {
            position: absolute;
            top: 50%;
            left: 15px;
            transform: translateY(-50%);
            color: #6c757d;
        }
        .form-group {
            position: relative;
            margin-bottom: 1.5rem;
        }
        .form-group input {
            padding-left: 40px;
        }
        .btn-login {
            background-color: #0d6efd;
            color: #fff;
            font-weight: 600;
            border-radius: 0.75rem;
        }
        .login-title {
            font-weight: 600;
        }
    </style>
</head>
<body>

<div class="container login-container row g-0">
    <!-- Sol görsel tarafı -->
    <div class="col-md-6 d-none d-md-block image-side">
        <img src="${pageContext.request.contextPath}/static/images/signin-image.jpg" alt="Giriş görseli">
    </div>

    <!-- Sağ form tarafı -->
    <div class="col-md-6 form-side">
        <h3 class="text-center login-title mb-4">${loginLogin}</h3>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${error}"/>
            </div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success" role="alert">
                <c:out value="${success}"/>
            </div>
        </c:if>

        <form method="POST" action="${pageContext.request.contextPath}/login">
            <div class="form-group">
                <i class="fas fa-id-card form-icon"></i>
                <input type="text" name="tckn" id="tckn" class="form-control" placeholder="${registerTCKN}" required maxlength="11" pattern="^\d{11}$" title="${registerTCKNError}">
            </div>
            <div class="form-group">
                <i class="fas fa-lock form-icon"></i>
                <input type="password" name="password" id="password" class="form-control" placeholder="${registerPassword}" required minlength="6"  title="${registerPasswordError}">
            </div>
            <div class="d-grid">
                <button type="submit" class="btn btn-login">${loginLogin}</button>
            </div>
        </form>

        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/register" class="text-decoration-none">
                ${loginCreateAccount}
            </a>
        </div>
        <div class="language-switcher d-flex align-items-center gap-3">
            <a href="?lang=tr_TR" title="Türkçe">
                <img src="https://flagcdn.com/w20/tr.png" alt="Türkçe" width="20" height="15" style="border-radius:3px;">
            </a>
            <a href="?lang=en_US" title="English">
                <img src="https://flagcdn.com/w20/us.png" alt="English" width="20" height="15" style="border-radius:3px;">
            </a>
        </div>

        <div class="text-start mt-4">
            <button id="adminRedirectBtn" class="btn btn-outline-secondary btn-sm">
                <i class="fas fa-user-shield me-1"></i> ${loginAdminPage}
            </button>
            <div id="adminRedirectMsg" class="mt-2 d-none text-muted small">
                <span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                ${loginAdminPageRedirected}
            </div>
        </div>

    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const contextPath = '${pageContext.request.contextPath}';

    document.getElementById("adminRedirectBtn").addEventListener("click", function () {
        const msg = document.getElementById("adminRedirectMsg");
        msg.classList.remove("d-none");
        setTimeout(function () {
            window.location.href = contextPath + '/admin/adminlogin';
        }, 1500);
    });
</script>
</body>
</html>
