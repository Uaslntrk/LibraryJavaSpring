<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="tr">
<head>
  <meta charset="UTF-8">
  <title>Profil Düzenle</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap 5 CDN -->
  <link href="../../static/assets/vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

  <style>
    .profile-img {
      width: 150px;
      height: 150px;
      object-fit: cover;
      border-radius: 50%;
      border: 3px solid #dee2e6;
      margin-bottom: 20px;
    }
  </style>
</head>
<body class="bg-light">

<div class="container py-5">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card shadow-sm">
        <div class="card-body">
          <div class="text-center">
            <img src="../../../static/images/Profile.png" alt="Profil Resmi" class="profile-img">
            <h4 class="mt-2">Profil Bilgileri</h4>
          </div>

          <form action="edit_librarianprofile.jsp" method="post" class="mt-4">
            <div class="row mb-3">
              <label class="col-sm-4 col-form-label">Kimlik Numarası</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" name="kimlik" value="000000000">
              </div>
            </div>

            <div class="row mb-3">
              <label class="col-sm-4 col-form-label">Çalışan ID</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" name="calisanId" value="111111">
              </div>
            </div>

            <div class="row mb-3">
              <label class="col-sm-4 col-form-label">İsim Soyisim</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" name="isim" value="Betül Güleç">
              </div>
            </div>

            <div class="row mb-3">
              <label class="col-sm-4 col-form-label">E-posta</label>
              <div class="col-sm-8">
                <input type="email" class="form-control" name="email" value="betul@example.com">
              </div>
            </div>

            <div class="row mb-3">
              <label class="col-sm-4 col-form-label">Cinsiyet</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" name="cinsiyet" value="Kadın">
              </div>
            </div>

            <div class="row mb-3">
              <label class="col-sm-4 col-form-label">Telefon Numarası</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" name="telefon" value="0555-555-55-55">
              </div>
            </div>

            <div class="row mb-4">
              <label class="col-sm-4 col-form-label">Rol</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" name="rol" value="Kütüphane Görevlisi">
              </div>
            </div>

            <div class="d-flex justify-content-end">
              <button type="button" class="btn btn-secondary me-2" onclick="window.history.back()">Vazgeç</button>
              <button type="submit" class="btn btn-primary">Kaydet</button>
            </div>
          </form>

        </div>
      </div>
    </div>
  </div>
</div>

<!-- Bootstrap JS (isteğe bağlı) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
