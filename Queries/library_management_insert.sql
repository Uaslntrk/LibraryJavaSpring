-- INSERT FUNCTION FOR LIBRARY MANAGEMENT -- 

-- 1. Insert into roles
INSERT INTO Roles (roleId, roleName) VALUES (1, 'ADMIN'), (2, 'USER');

-- 2. Insert into person
INSERT INTO Person (TCKN, firstName, lastName, gender, email, phoneNumber) VALUES
('12345678901', 'Ali', 'Yýlmaz', 'Erkek', 'ali@gmail.com', '901234567890'),
('22345678901', 'Ayþe', 'Demir', 'Kadýn', 'ayse@gmail.com', '901234567891'),
('32345678901', 'Mehmet', 'Kaya', 'Erkek', 'mehmet@gmail.com', '901234567892'),
('42345678901', 'Zeynep', 'Þahin', 'Kadýn', 'zeynep@gmail.com', '901234567893'),
('52345678901', 'Hasan', 'Çelik', 'Erkek', 'hasan@gmail.com', '901234567894'),
('62345678901', 'Elif', 'Koç', 'Kadýn', 'elif@gmail.com', '901234567895'),
('72345678901', 'Murat', 'Aydýn', 'Erkek', 'murat@gmail.com', '901234567896'),
('82345678901', 'Cemre', 'Yýldýz', 'Kadýn', 'cemre@gmail.com', '901234567897'),
('92345678901', 'Deniz', 'Eren', 'Erkek', 'deniz@gmail.com', '901234567898'),
('10345678901', 'Ebru', 'Bulut', 'Kadýn', 'ebru@gmail.com', '901234567899');

-- 3. Insert into reader
INSERT INTO Reader (readerGuid, userName, userPassword, TCKN, roleId, subDate) VALUES
('10', 'aliy', 'password1', '12345678901', 2, '2015-08-14'),
('20', 'aysed', 'password2', '22345678901', 2, '2020-09-16'),
('30', 'mehmetk', 'password3', '32345678901', 2,'2020-06-03'),
('40', 'zeyneps', 'password4', '42345678901', 2,getDate()),
('50', 'hasanc', 'password5', '52345678901', 2,'2023-12-23'),
('60', 'elifk', 'password6', '62345678901', 2,'2018-03-20'),
('70', 'murata', 'password7', '72345678901', 2,'2018-03-20');

--4. Insert into librarian
INSERT INTO Librarian (librarianId, libPassword, TCKN, roleId) VALUES
(1, 'libpass1', '82345678901', 2),
(2, 'libpass2', '92345678901', 2),
(3, 'libpass3', '10345678901', 2);

-- 5. Insert into genre and category
INSERT INTO Genre (genreName) VALUES
('Roman'), ('Bilim Kurgu'), ('Fantastik'), ('Tarih'), ('Biyografi'),
('Kiþisel Geliþim'), ('Psikoloji'), ('Bilim'), ('Sanat'), ('Felsefe');

INSERT INTO Category (ISBN, genreId) VALUES
('9781234567890',1), 
('9782345678901',2), 
('9783456789012',4)

-- 6. Insert into book
INSERT INTO Book (ISBN, title, author, pageNumber, publicationDate) VALUES
('9781234567890', 'Roman Kitabý', 'Yazar A', 300, '2022-05-10'),
('9782345678901', 'Bilim Kurgu Kitabý', 'Yazar B', 400, '2021-07-15'),
('9783456789012', 'Tarih Kitabý', 'Yazar C', 500, '2020-09-20');

-- 7. Insert into bookdetail
INSERT INTO BookDetail (ISBN, bookDescription, copiesAvailable, bookCover) VALUES
('9781234567890','Roman türünde bir kitap', 5, 'Ciltsiz'),
('9782345678901','Bilim kurgu klasiði', 3, 'Sert'),
('9783456789012','Tarih hakkýnda detaylý bir eser', 2, 'Ciltli');

-- 8. Insert into bookshelf
INSERT INTO Bookshelf (shelfNumber, genreId) VALUES
(1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

-- 9.1 Create book first
INSERT INTO Book (ISBN, title, author, pageNumber, publicationDate) VALUES 
('9780131101630', 'The C Programming Language',  'Yazar D', 325, '2020-09-20'),
('9780596009205', 'Head First Java', 'Yazar D', 600, '2015-03-22');

-- 9.2 Create details
INSERT INTO BookDetail (ISBN, bookDescription, copiesAvailable, bookCover) VALUES
('9780131101630','C yazýlým türünde bir kitap', 5, 'Ciltsiz'),
('9780596009205','Java yazýlým türünde bir kitap', 3, 'Sert')

-- 9.3 Create person
INSERT INTO Person (TCKN, firstName, lastName, gender, email, phoneNumber) VALUES 
('98349028932', 'Ali', 'Yýlmaz','Erkek','ali2@gmail.com','908654567890' ),
('98765432100', 'Ayþe', 'Kaya', 'Kadýn','ayasek2@gmail.com','901233087890' );

-- 9.4 Create person into donation
INSERT INTO Donation (TCKN, ISBN, donationDate, bookStatus, librarianId) VALUES 
('98349028932', '9780131101630', '2025-03-20', 'Ýyi', 1),
('98765432100', '9780596009205', '2025-03-18', 'Az Hasarlý', 2);

-- 10. Insert into cart

-- 10.1
INSERT INTO Cart (readerGuid, ISBN, createDate) VALUES 
-- Farklý kategorilerden ekleyen 3 kiþi
('10', '9781234567890', '2025-03-19'),
('20', '9782345678901', '2025-03-19'),
('40', '9783456789012', '2025-03-19'),
-- Ayný kitaplardan ekleyen 3 kiþi
('30', '9780131101630', '2025-03-15'),
('60', '9780131101630', '2025-03-14'),
('70', '9780131101630', '2025-03-13')

-- 11. Insert into rent

INSERT INTO Rent (ISBN,readerGuid, rentDate, expirationDate, hasReturned, librarianId)
VALUES 
-- Reader 11'in sepetinden onaylananlar
('9781234567890', '10','2025-03-19', '2025-04-02', 0, 1),
-- Reader 12'nin sepetinden onaylananlar
('9782345678901', '20','2025-03-18', '2025-04-01', 0, 2),
-- Reader 13'ün sepetinden onaylananlar
('9783456789012', '40','2025-03-17', '2025-03-31', 0, 2),

-- Ayný kitaplarý alanlar
('9780131101630', '30','2025-03-15', '2025-03-29', 0, 1),
('9780131101630', '60','2025-03-14', '2025-03-28', 0, 2),
('9780131101630', '70','2025-03-13', '2025-03-27', 0, 2);

UPDATE BookDetail 
SET copiesAvailable = 0
WHERE ISBN IN ('9780131101630', '9780596009205', '9781491950357');