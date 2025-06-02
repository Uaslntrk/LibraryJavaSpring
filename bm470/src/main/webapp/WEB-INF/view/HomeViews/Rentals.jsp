<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container my-5">
  <h2 class="mb-4">${rentalTitle}</h2>
  <p class="text-muted">${cartInfo}</p>

  <c:choose>
    <c:when test="${empty loggedInUser}">
      <a href="${pageContext.request.contextPath}/login" class="btn btn-warning btn-sm">
        <i class="bi bi-person"></i> ${loginLogin}
      </a>
    </c:when>
    <c:otherwise>
      <c:choose>
        <c:when test="${not empty rentalRecords}">
          <div class="row row-cols-1 row-cols-md-3 g-4">
            <c:forEach var="rental" items="${rentalRecords}" varStatus="status">
              <c:set var="book" value="${matchedRentals[status.index]}" />
              <div class="col">
                <div class="card h-100 shadow-sm">
                  <img src="${book.coverImageUrl}" class="card-img-top" alt="Kapak">
                  <div class="card-body d-flex flex-column">
                    <h5 class="card-title">${rental.title}</h5>
                    <p class="card-text mb-1"><strong>${bookBookAuthor}:</strong>
                      <c:forEach var="author" items="${book.authors}" varStatus="loop">
                        ${author}<c:if test="${!loop.last}">, </c:if>
                      </c:forEach>
                    </p>
                    <p class="card-text mb-3"><strong>${bookBookPublicationDate}:</strong> ${book.firstPublishYear}</p>

                    <div class="mt-auto">
                      <span class="badge bg-success"><i class="bi bi-check-circle"></i> ${rentalRental}</span>
                    </div>
                    <div class="d-flex gap-2 mt-2">
                      <form action="${pageContext.request.contextPath}/return-book" method="post" class="d-inline">
                        <input type="hidden" name="rentalId" value="${rental.id}" />
                        <button type="submit" class="btn btn-success btn-sm">
                          <i class="bi bi-arrow-counterclockwise"></i> ${rentalBack}
                        </button>
                      </form>
                      <form action="${pageContext.request.contextPath}/delete-rental" method="post" class="d-inline">
                        <input type="hidden" name="rentalId" value="${rental.id}" />
                        <button type="submit" class="btn btn-danger btn-sm">
                          <i class="bi bi-trash"></i> ${librarianUserDelete}
                        </button>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </c:forEach>
          </div>
        </c:when>
        <c:otherwise>
          <div class="alert alert-info mt-4">
           ${rentalInfo2}
          </div>
        </c:otherwise>
      </c:choose>
    </c:otherwise>
  </c:choose>
</div>

<!-- Bootstrap Icons CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<script>
  window.addEventListener('DOMContentLoaded', function () {
    const header = document.getElementById('header');
    if (header) {
      header.style.position = 'relative';
    }
  });
</script>