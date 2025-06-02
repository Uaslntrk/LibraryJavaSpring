<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="container my-5">
    <h2 class="mb-4">${cartTitle}</h2>
    <p class="text-muted"> ${cartInfo}</p>
    <c:choose>
        <c:when test="${empty loggedInUser}">
            <a href="${pageContext.request.contextPath}/login" class="btn btn-warning btn-sm">
                <i class="bi bi-person"></i> ${loginLogin}
            </a>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${not empty matchedBooks}">
                    <form action="${pageContext.request.contextPath}/rent-books" method="post" class="d-inline">
                        <button type="submit" class="btn btn-primary btn-sm">
                            <i class="bi bi-book"></i> ${cartRental}
                        </button>
                    </form>

                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/Books" class="btn btn-info btn-sm">
                        <i class="bi bi-bookmark-plus"></i> ${cartStartLook}
                    </a>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>


    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach var="book" items="${matchedBooks}">
            <div class="col">
                <div class="card h-100 shadow-sm">
                    <img src="${book.coverImageUrl}" class="card-img-top" alt="Kapak">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text mb-1"><strong>${bookBookAuthor}:</strong>
                            <c:forEach var="author" items="${book.authors}" varStatus="loop">
                                ${author}<c:if test="${!loop.last}">, </c:if>
                            </c:forEach>
                        </p>
                        <p class="card-text mb-3"><strong>${bookBookPublicationDate}:</strong> ${book.firstPublishYear}</p>

                        <div class="mt-auto">
                            <form action="/remove-from-cart" method="post" class="d-inline">
                                <input type="hidden" name="title" value="${book.title}" />
                                <input type="hidden" name="author" value="${book.authors[0]}" />
                                <button type="submit" class="btn btn-danger btn-sm me-2">
                                    <i class="bi bi-trash"></i> ${booksCartDelete}
                                </button>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
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
