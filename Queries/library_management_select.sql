--- SELECT FUNCTIONS --

-- Get roles
SELECT * FROM Roles

-- Get persons
SELECT * FROM Person

-- Get readers
SELECT * FROM Reader

-- Get persons who are not readers
SELECT * FROM Person WHERE TCKN NOT IN (SELECT TCKN FROM Reader)

-- Get librarians
SELECT * FROM Librarian

-- Get librarians with their creditentials
SELECT lib.TCKN, librarianId, firstName + ' ' + lastName as Name, gender, email, phoneNumber 
FROM Librarian lib JOIN Person per
ON lib.TCKN = per.TCKN

-- Get books
SELECT * FROM Book

-- Get genres
SELECT * FROM Genre

-- Get categories
SELECT * FROM Category

-- Get books with details
SELECT bd.ISBN, title, author, genreName, publicationDate, bookDescription, copiesAvailable, bookCover 
FROM Book book JOIN BookDetail bd ON book.ISBN = bd.ISBN
JOIN Category cat ON cat.ISBN = book.ISBN
JOIN Genre gen ON gen.genreId = cat.genreId

-- Get rents
SELECT * FROM Rent





