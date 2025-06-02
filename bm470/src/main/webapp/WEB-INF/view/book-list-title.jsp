<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title>Kitap Arama</title>
</head>
<body>
<h2>Kitap Arama</h2>
<form method="get" action="/search">
  <input type="text" name="title" value="${param.title}" placeholder="Kitap ara..."/>
  <button type="submit">Ara</button>
</form>

<h3>Sonuçlar</h3>
<ul>
  <c:forEach var="book" items="${books}">
    <div style="margin-bottom: 20px;">
      <img src="${book.coverImageUrl}" alt="Kapak" style="height: 150px;" />
      <h3>${book.title}</h3>
      <p>Yazar: <c:forEach var="author" items="${book.authors}">${author}<br/></c:forEach></p>
      <p>Yıl: ${book.firstPublishYear}</p>
    </div>
  </c:forEach>
</ul>
</body>
</html>
