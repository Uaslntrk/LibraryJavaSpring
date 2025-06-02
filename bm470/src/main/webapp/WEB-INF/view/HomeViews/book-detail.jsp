<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="true" %>

<div class="container my-5" >
    <div class="row g-4">
        <div class="col-md-4 text-center">
            <img src="${book.coverImageUrl}" class="img-fluid rounded shadow-sm" alt="Kapak">
        </div>
        <div class="col-md-8">
            <h2 class="mb-3">${book.title}</h2>

            <p><strong>${bookBookAuthor } :</strong>
                <c:set var="authorsSize" value="${fn:length(book.authors)}"/>
                <c:forEach items="${book.authors}" var="author" varStatus="status">
                    ${author}<c:if test="${status.index != authorsSize - 1}">, </c:if>
                </c:forEach>
            </p>

            <p><strong>${bookBookPublicationDate}:</strong> ${book.firstPublishYear}</p>
            <p><strong>${bookBookPageNumber}:</strong> ${book.number_of_pages}</p>

            <div class="mt-4">
                <form action="/add-to-cart" method="post" class="d-inline me-2">
                    <input type="hidden" name="title" value="${book.title}" />
                    <input type="hidden" name="author" value="${book.authors[0]}" />
                    <button type="submit" class="btn btn-success">
                        <i class="fa fa-shopping-cart me-1"></i> ${booksCartAdd}
                    </button>
                </form>

                <c:if test="${not empty sessionScope.loggedInUser}">
                    <form action="/add-to-favorites" method="post" class="d-inline me-2">
                        <input type="hidden" name="title" value="${book.title}" />
                        <input type="hidden" name="author" value="${book.authors[0]}" />
                        <button class="btn btn-outline-warning btn-sm">${booksFavoriteAdd}</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    window.addEventListener('DOMContentLoaded', function () {
        const header = document.getElementById('header');
        if (header) {
            header.style.position = 'relative';
        }
    });
</script>

