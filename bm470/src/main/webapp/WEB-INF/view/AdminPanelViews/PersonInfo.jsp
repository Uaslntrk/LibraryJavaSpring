<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="tr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${memberTransactions}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
  <style>
    body { background-color: #f8f9fa; font-family: 'Poppins', sans-serif; }
    .container { max-width: 1200px; margin-top: 30px; }
    .card { border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
    .table th, .table td { vertical-align: middle; }
    .btn-action { margin-right: 5px; }
    .debug { color: red; font-weight: bold; }
    #searchInput { max-width: 300px; margin-bottom: 15px; }
  </style>
</head>
<body>
<div class="container">
  <h2 class="mb-4">${librarianAll}</h2>

  <c:if test="${not empty success}">
    <div class="alert alert-success">${success}</div>
  </c:if>
  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <c:if test="${empty persons}">
    <p class="debug">Hata: </p>
  </c:if>

  <!-- Arama Kutusu -->
  <input type="text" id="searchInput" class="form-control" placeholder="TCKN, Ad, Soyad, E-posta, Telefon...">

  <div class="card">
    <div class="card-body">
      <c:if test="${empty persons}">
        <p class="text-muted">${librarianInfo}</p>
      </c:if>
      <c:if test="${not empty persons}">
        <p>${cartRentalSize}: ${persons.size()}</p>
        <table class="table table-hover" id="personTable">
          <thead>
          <tr>
            <th>${registerTCKN}</th>
            <th>${registerFirstname}</th>
            <th>${registerLastname}</th>
            <th>${registerGender}</th>
            <th>${registerBirthday}</th>
            <th>${registerEmail}</th>
            <th>${registerPhoneNumber}</th>
            <th>${cartRentalStatus}</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="person" items="${persons}">
            <tr>
              <td><c:out value="${person.tckn != null ? person.tckn : 'TCKN Boş'}"/></td>
              <td><c:out value="${person.firstName != null ? person.firstName : 'Ad Boş'}"/></td>
              <td><c:out value="${person.lastName != null ? person.lastName : 'Soyad Boş'}"/></td>
              <td><c:out value="${person.gender != null ? person.gender : 'Cinsiyet Boş'}"/></td>
              <td>
                <c:choose>
                  <c:when test="${not empty person.birthday}">
                    <fmt:formatDate value="${person.birthday}" pattern="dd.MM.yyyy HH:mm:ss" />
                  </c:when>
                  <c:otherwise>${booksUnknow}</c:otherwise>
                </c:choose>
              </td>
              <td><c:out value="${person.email != null ? person.email : 'E-posta Boş'}"/></td>
              <td><c:out value="${person.phoneNumber != null ? person.phoneNumber : 'Telefon Boş'}"/></td>
              <td>
                <c:choose>
                  <c:when test="${person.banned}">
                    <span class="badge bg-danger">${librarianBanned}</span>
                  </c:when>
                  <c:otherwise>
                    <span class="badge bg-success">${librarianAktive}</span>
                    <form action="blockUser" method="post" style="display:inline;">
                      <input type="hidden" name="tckn" value="${person.tckn}" />
                      <button type="submit" class="btn btn-sm btn-warning btn-action">${librarianDoBanned}</button>
                    </form>
                  </c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </c:if>
    </div>
  </div>
</div>

<!-- JS: Arama Özelliği -->
<script>
  const searchInput = document.getElementById("searchInput");
  searchInput.addEventListener("keyup", function () {
    const filter = this.value.toLowerCase();
    const rows = document.querySelectorAll("#personTable tbody tr");

    rows.forEach(row => {
      const rowText = row.textContent.toLowerCase();
      row.style.display = rowText.includes(filter) ? "" : "none";
    });
  });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
