<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${cartBookTitle} </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    <style>
        body { background-color: #f8f9fa; font-family: 'Poppins', sans-serif; }
        .container { max-width: 1200px; margin-top: 30px; }
        .card { border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }
        .table th, .table td { vertical-align: middle; }
        .btn-action { margin-right: 5px; }
        .debug { color: red; font-weight: bold; }
    </style>
</head>
<body>
<div class="container">
    <h2 class="mb-4">${cartBookAll}</h2>

    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <c:if test="${empty rentals}">
        <p class="debug">Hata:</p>
    </c:if>

    <div class="card">
        <div class="card-body">

            <c:if test="${not empty rentals}">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <input type="text" id="searchInput" class="form-control w-50" placeholder="TCKN, Ad Soyad, Kitap Ara...">
                    <select id="statusFilter" class="form-select w-25">
                        <option value="all">${cartBookAll}</option>
                        <option value="returned">${cartDelivered}</option>
                        <option value="overdue">${cartReturned}</option>
                        <option value="rented">${cartRental}</option>
                    </select>
                </div>

                <p>${cartRentalSize}: ${rentals.size()}</p>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>${loginTckn}</th>
                        <th>${registerFirstname} ${registerLastname}</th>
                        <th>${bookBookName}</th>
                        <th>${bookBookAuthor}</th>
                        <th>${cartRentalDate}</th>
                        <th>${returnedDate}</th>
                        <th>${cartRentalStatus}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="rental" items="${rentals}">
                        <tr data-status="${rental.returned ? 'returned' : (rental.overdue ? 'overdue' : 'rented')}">
                            <td>
                                <c:choose>
                                    <c:when test="${not empty rental.user and not empty rental.user.tckn}">
                                        <c:out value="${rental.user.tckn}"/>
                                    </c:when>
                                    <c:otherwise>${booksUnknow}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty rental.user and not empty rental.user.firstName and not empty rental.user.lastName}">
                                        <c:out value="${rental.user.firstName} ${rental.user.lastName}"/>
                                    </c:when>
                                    <c:otherwise>${booksUnknow}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty rental.title}">
                                        <c:out value="${rental.title}"/>
                                    </c:when>
                                    <c:otherwise>${booksUnknow}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty rental.author}">
                                        <c:out value="${rental.author}"/>
                                    </c:when>
                                    <c:otherwise>${booksUnknow}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty rental.rentalDate}">
                                        <c:out value="${fn:replace(rental.rentalDate.toString(), '-', '.')}"/>
                                    </c:when>
                                    <c:otherwise>${booksUnknow}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty rental.returnDate}">
                                        <c:out value="${fn:replace(rental.returnDate.toString(), '-', '.')}"/>
                                    </c:when>
                                    <c:otherwise>${booksUnknow}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${rental.returned}">
                                        <span class="badge bg-success">${cartnotReturned}</span>
                                    </c:when>
                                    <c:when test="${rental.overdue}">
                                        <span class="badge bg-danger">${cartReturned}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-warning">${cartRental}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty rentals}">
                <p class="text-muted">${booksUnknow}</p>
            </c:if>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const searchInput = document.getElementById("searchInput");
    const statusFilter = document.getElementById("statusFilter");
    const rows = document.querySelectorAll("table tbody tr");

    function filterTable() {
        const search = searchInput.value.toLowerCase();
        const status = statusFilter.value;

        rows.forEach(row => {
            const rowText = row.textContent.toLowerCase();
            const rowStatus = row.getAttribute("data-status");

            const matchesSearch = rowText.includes(search);
            const matchesStatus = (status === "all" || rowStatus === status);

            row.style.display = (matchesSearch && matchesStatus) ? "" : "none";
        });
    }

    searchInput.addEventListener("keyup", filterTable);
    statusFilter.addEventListener("change", filterTable);
</script>
</body>
</html>
