<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="tr">
<head>
  <meta charset="UTF-8">
  <title>Kitap Detay</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Bootstrap CSS -->
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
  <style>
    body {
      font-family: 'Poppins', sans-serif;
    }
    .book-detail-container {
      background-color: #ffffff;
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      margin-top: 30px;
    }
    .book-image {
      max-width: 250px;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    .book-info {
      font-size: 18px;
      line-height: 1.6;
      margin-left: 20px;
    }
    .book-info p {
      font-weight: 500;
    }
    .book-title {
      font-size: 32px;
      font-weight: 600;
      color: #2c3e50;
      margin-bottom: 15px;
    }
    .book-author {
      font-size: 20px;
      font-weight: 500;
      color: #7f8c8d;
      margin-bottom: 10px;
    }
    .book-category, .book-isbn, .book-quantity {
      font-size: 18px;
      margin-bottom: 10px;
    }
    .btn-detail {
      background-color: #3498db;
      color: white;
      padding: 12px 20px;
      border: none;
      border-radius: 5px;
      text-decoration: none;
      font-weight: 600;
    }
    .btn-detail:hover {
      background-color: #2980b9;
    }
  </style>
</head>
<body class="bg-light">

<div class="container">
  <div class="row justify-content-center">
    <div class="col-md-10 col-lg-8">
      <div class="book-detail-container text-center">

        <!-- Kitap Resmi -->
        <div class="text-center mb-4">
          <img src="../../static/images/books/default-book.webp" alt="Kitap Resmi" class="book-image">
        </div>

        <!-- Kitap Adı -->
        <h3 class="book-title" id="bookTitle">Sırlar</h3>

        <!-- Yazar Adı -->
        <p class="book-author" id="bookAuthor">Yazar: Ayşe Demir</p>

        <div class="d-flex justify-content-center">
          <!-- Kitap Bilgileri -->
          <div class="book-info">
            <p id="bookCategory" class="book-category">Kategori: Yazılım</p>
            <p id="bookISBN" class="book-isbn">ISBN: 978-3-16-148410-0</p>
            <p id="bookQuantity" class="book-quantity">Adet: 3</p>
          </div>
        </div>

        <!-- Detay Butonu -->
        <a href="#" class="btn-detail" style="background-color: red;">Sil</a>
        <a href="#" class="btn-detail">Düzenle</a>
      </div>
    </div>
  </div>
</div>

<!-- Bootstrap JS, jQuery ve Popper.js -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
  // Statik veri ile sayfa içeriklerini güncelleme
  document.getElementById('bookTitle').textContent = "Java ile Programlama";
  document.getElementById('bookAuthor').textContent = "Yazar: Ayşe Demir";
  document.getElementById('bookCategory').textContent = "Kategori: Yazılım";
  document.getElementById('bookISBN').textContent = "ISBN: 978-3-16-148410-0";
  document.getElementById('bookQuantity').textContent = "Adet: 3";
</script>

</body>
</html>
