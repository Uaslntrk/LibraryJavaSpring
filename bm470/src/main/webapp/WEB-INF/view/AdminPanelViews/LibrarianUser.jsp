<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book Info</title>
    <link rel="stylesheet" href="../../../static/css/style.css">


<body>

<!-- Kitap Arama kısmı -->
<h2> Üye Yönetimi</h2>

<div class="search-container">
    <input type="text" class="search-input" placeholder="Üyenin ID yada İsmini Ara...">
    <div class="filter-icon">⚙️</div>
</div>
<button class="button" onclick="toggleUserForm()">Üye Ekle</button>

<div id="user-overlay"></div>

<div id="userForm">
    <h3>Yeni üye Ekle</h3>
    <form>
        <div class="form-group">
            <input type="number" id="userID" class="form-input" placeholder="Üye numarası girin">
        </div>
        <div class="form-group">
            <input type="text" id="user-name" class="form-input" placeholder="Üye adını girin">
        </div>
        <div class="form-group">
            <input type="number" id="publicationDate" class="form-input" placeholder="Üye kimliğini girin">
        </div>
        <div class="form-group">
            <input type="text" id="uder-gmail" class="form-input" placeholder="Üye g-mail girin">
        </div>
        <div class="form-group">
            <input type="text" id="user-password" class="form-input" placeholder="Üye Şifresini  girin">
        </div>
        <div class="form-group">
            <input type="text" id="user-phone" class="form-input" placeholder="Üye numarasını girin">
        </div>
        <div class="form-group">
            <input type="text" id="user-gender" class="form-input" placeholder=" Üye cinsiyetini girin">
        </div>

        <button type="button" onclick="addBook()" class="submit-btn">Üye Ekle</button>
        <button type="button" onclick="closeUserForm()" class="submit-btn">Kapat</button>

    </form>
</div>


<!-- Kitap Tablosu -->
<div class="book-list">
    <table>
        <thead>
        <tr>
            <th>Üye ID </th>
            <th>üye Kimlik </th>
            <th>Üye Adı</th>
            <th>Üye Maili</th>
            <th>Üye Telefonu </th>
            <th>Üye Şifresi </th>
            <th>detaylar</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>000000</td>
            <td>1111111111</td>
            <td>Betül Güleç</td>
            <td>betul@example.com</td>
            <td>0555-555-55-55</td>
            <td>0555-555-55-55</td>
            <td>
                <button class="details-btn" onclick="DeleteUser()">Sil</button>
            </td>

        </tr>

        </tbody>
    </table>
</div>

<script>
    function toggleUserForm() {
        const userForm = document.getElementById("userForm");
        const overlay = document.getElementById("user-overlay");
        userForm.style.display = "block";
        overlay.style.display = "block";
    }

    function closeUserForm() {
        const userForm = document.getElementById("userForm");
        const overlay = document.getElementById("user-overlay");
        userForm.style.display = "none";
        overlay.style.display = "none";
    }

    // İstersen: Overlay'e tıklayınca da form kapansın
    document.getElementById("user-overlay").addEventListener("click", closeUserForm);

    function DeleteUser(button) {
        // Butonun bulunduğu satırı (tr) bul ve sil
        const row = button.closest("tr");
        row.remove();
    }
</script>

</body>
</html>
