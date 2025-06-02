<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kullanıcı Profili</title>
    <link rel="stylesheet" href="../../static/css/style.css">

</head>
<body>

<!--Profil kısmı-->

<div class="container">
    <aside class="profile-card">
        <div class="profile-image">
            <img src="../../../static/images/Profile.png" alt="Kullanıcı Resmi">
        </div>
        <div>
            <h1 class="profile-title">Profil Bilgileri</h1>
            <div class="profile-row"><p><strong>Kimlik:</strong> 000000000 <span>/</span> <strong>Çalışan ID:</strong> 111111</p></div>
            <div class="profile-row"><p><strong>İsim Soyisim:</strong> Betül Güleç <span>/</span> <strong>Email:</strong> betul@example.com</p></div>
            <div class="profile-row"><p><strong>Cinsiyet:</strong> Kadın <span>/</span> <strong>Telefon Numarası:</strong> 0555-555-55-55</p></div>
            <div class="profile-row"><p><strong>Rol:</strong> Kütüphane Görevlisi</p></div>
            <a href="/admin/LibrarianProfile-edit" class="form-submit">Düzenle</a>
        </div>
    </aside>
</div>

<script>
    function kitapFormuGoster() {
        var kitapForm = document.getElementById("kitapForm");
        kitapForm.classList.toggle("active");
    }
</script>
</body>
</html>
