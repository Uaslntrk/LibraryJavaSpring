<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
  <title>${bookTransaction}</title>
  <style>
    .book-card {
      height: 100%;
      display: flex;
      flex-direction: column;
      background: #fff;
      border-radius: 12px;
      box-shadow: 0 2px 6px rgb(0 0 0 / 0.1);
      transition: box-shadow 0.3s ease, transform 0.3s ease;
      cursor: pointer;
    }

    .book-card:hover {
      box-shadow: 0 12px 24px rgb(0 0 0 / 0.25);
      transform: translateY(-6px);
    }

    .book-cover {
      object-fit: cover;
      height: 250px;
      width: 100%;
      border-top-left-radius: 12px;
      border-top-right-radius: 12px;
    }

    .card-body {
      padding: 1rem 1.25rem;
      display: flex;
      flex-direction: column;
      flex-grow: 1;
    }

    .book-title {
      font-size: 1.25rem;
      font-weight: 700;
      margin-top: 0.5rem;
      color: #333;
      flex-grow: 0;
    }

    .book-author, .book-year {
      font-size: 0.95rem;
      color: #666;
      margin-top: 0.4rem;
      flex-grow: 0;
    }

    .btn {
      font-weight: 600;
      border-radius: 6px;
      padding: 6px 12px;
      font-size: 0.85rem;
      transition: background-color 0.3s ease, color 0.3s ease;
    }

    .btn-outline-success:hover {
      background-color: #28a745;
      color: white;
    }

    .btn-outline-danger:hover {
      background-color: #dc3545;
      color: white;
    }

    .btn-outline-warning:hover {
      background-color: #ffc107;
      color: white;
    }

    .mt-auto {
      margin-top: auto;
    }
  </style>

</head>

<div class="container my-5">
  <h2 class="mb-4 text-center">${booksBookSearch}</h2>

  <!-- Arama Formu -->
  <form method="get" action="/Books" class="input-group mb-4">
    <input type="text" name="q" class="form-control" value="${query}" placeholder="${booksSearch}">
    <button class="btn btn-primary" type="submit">${booksBookSearch}</button>
  </form>
  <c:if test="${not empty adderrormessage}">
    <div class="alert alert-info alert-dismissible fade show" role="alert">
        ${adderrormessage}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  </c:if>
  <!-- Kitap Listesi -->
  <div class="row g-3">
    <c:forEach items="${books}" var="book">
      <div class="col-6 col-sm-4 col-md-3 col-lg-2">
        <div class="card book-card h-100 shadow-sm">
          <a href="/book-detail?title=${book.title}&author=${book.authors[0]}" class="text-decoration-none">
            <c:choose>
              <c:when test="${not empty book.coverImageUrl}">
                <img src="${book.coverImageUrl}" class="book-cover" alt="Kapak"
                     onerror="this.onerror=null; this.src='/images/default-cover.jpg';">
              </c:when>
              <c:otherwise>
                <img src="../../static/images/default-cover.jpg" class="book-cover" alt="Kapak Yok">
              </c:otherwise>
            </c:choose>
          </a>
          <div class="card-body p-2 d-flex flex-column">
            <div class="book-title text-dark">${book.title}</div>

            <div class="book-meta">
              <strong>${bookBookAuthor}</strong>
              <c:choose>
                <c:when test="${not empty book.authors}">
                  ${book.authors[0]}
                </c:when>
                <c:otherwise>
                 ${booksUnknow}
                </c:otherwise>
              </c:choose>
            </div>

            <div class="book-meta">
              <strong>${bookBookPublicationDate}</strong>
              <c:choose>
                <c:when test="${book.firstPublishYear != null}">
                  ${book.firstPublishYear}
                </c:when>
                <c:otherwise>
                  ${booksUnknow}
                </c:otherwise>
              </c:choose>
            </div>

            <div class="mt-auto book-actions">
              <c:set var="inCart" value="false" />
              <c:forEach items="${cartItems}" var="cartItem">
                <c:if test="${cartItem.title == book.title && cartItem.author == book.authors[0]}">
                  <c:set var="inCart" value="true" />
                </c:if>
              </c:forEach>

              <c:choose>
                <c:when test="${inCart}">
                  <form action="/remove-from-cart" method="post">
                    <input type="hidden" name="title" value="${book.title}" />
                    <input type="hidden" name="author" value="${book.authors[0]}" />
                    <button class="btn btn-outline-danger btn-sm">${booksCartDelete}</button>
                  </form>
                </c:when>
                <c:otherwise>
                  <form action="/add-to-cart" method="post">
                    <input type="hidden" name="title" value="${book.title}" />
                    <input type="hidden" name="author" value="${book.authors[0]}" />
                    <button class="btn btn-outline-success btn-sm">${booksCartAdd}</button>
                  </form>
                </c:otherwise>
              </c:choose>

              <c:set var="inFavorites" value="false" />
              <c:forEach items="${favItems}" var="favBook">
                <c:if test="${favBook.title == book.title && favBook.author == book.authors[0]}">
                  <c:set var="inFavorites" value="true" />
                </c:if>
              </c:forEach>

              <c:choose>
                <c:when test="${inFavorites}">
                  <form action="/remove-from-favorites" method="post">
                    <input type="hidden" name="title" value="${book.title}" />
                    <input type="hidden" name="author" value="${book.authors[0]}" />
                    <button class="btn btn-outline-danger btn-sm">${booksFavoriteDelete}</button>
                  </form>
                </c:when>
                <c:otherwise>
                  <form action="/add-to-favorites" method="post">
                    <input type="hidden" name="title" value="${book.title}" />
                    <input type="hidden" name="author" value="${book.authors[0]}" />
                    <button class="btn btn-outline-warning btn-sm">${booksFavoriteAdd}</button>
                  </form>
                </c:otherwise>
              </c:choose>

            </div>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>

  <!-- Sayfalama -->
  <div class="d-flex justify-content-center mt-5">
    <nav>
      <ul class="pagination">
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
          <a class="page-link" href="/Books?q=${query}&page=${currentPage - 1}&pageSize=${pageSize}">← ${booksPrevius}</a>
        </li>
        <li class="page-item disabled">
          <a class="page-link">${booksBookPage} ${currentPage} / ${totalPages}</a>
        </li>
        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
          <a class="page-link" href="/Books?q=${query}&page=${currentPage + 1}&pageSize=${pageSize}">${booksPrevius} →</a>
        </li>
      </ul>
    </nav>
  </div>
</div>

<!-- Bootstrap JS -->
<script>
  function checkLoginAndSubmit(formId) {
    var loggedIn = ${not empty sessionScope.loggedInUser ? "true" : "false"};
    if (!loggedIn) {
      alert("Lütfen önce giriş yapınız.");
      return false; // form submit engellendi
    }
    document.getElementById(formId).submit();
  }
</script>

</html>
