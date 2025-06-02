-- CREATE FUNCTIONS TO CREATE TABLES --

CREATE TABLE Roles(
	roleId int Primary Key,
	roleName varchar (10) Not Null 
		constraint ck_rolename check (roleName in ('ADMIN','USER'))
)

CREATE TABLE Person(
	TCKN char (11) Primary Key
		constraint ck_tckn check (tckn like '%[0-9]%'),
	firstName varchar (50) Not Null
		constraint ck_firstName check (firstName like '%[a-zA-Z]%'),
	lastName varchar (50) Not Null
		constraint ck_lastname check (lastname like '%[a-zA-Z]%'),
	gender varchar (10) Not Null
		constraint ck_gender check(gender in ('Kadýn','Erkek','Tercihsiz')),
	email varchar (100) Not Null
		constraint ck_email check (email like '%@gmail.com'),
	phoneNumber char (12)
		constraint ck_phonenumber check (phonenumber like '90%')
)

CREATE TABLE Reader(
	readerGuid varchar(255) Primary Key,
	userName varchar (20) Unique Not Null
		constraint ck_username check (LEN(username) > 3),
	userPassword varchar (20) Not Null
		constraint ck_userpassword check (LEN(userpassword) > 5),
	TCKN char (11) Foreign Key References Person (TCKN) ON DELETE CASCADE Not Null,
	roleId int Foreign Key References Roles (roleId) ON UPDATE CASCADE Not Null,
	subDate date Not Null
		default(getDate())
		constraint ck_subdate check (subDate <= getDate())
)

CREATE TABLE Librarian (
	librarianId int Primary Key,
	libPassword varchar (20) Not Null
		constraint ck_libpassword check (LEN(libpassword) > 5),
	TCKN char (11) Foreign Key References Person(TCKN) ON DELETE CASCADE Not Null,
	roleId int Foreign Key References Roles(roleId) ON UPDATE CASCADE Not Null
)

CREATE TABLE Book (
	ISBN char (13) Primary Key,
	title varchar(50) Not Null,
	author varchar (100) Not Null
		constraint ck_author check (author like '%[a-zA-Z]%'),
	pageNumber int Not Null,
		constraint ck_pageNumber check (pagenumber > 0),
	publicationDate date Not Null
		default (getDate()),
	img varbinary(max)
)

CREATE TABLE BookDetail (
	detailId int identity (1,1) Primary Key,
	ISBN char(13) Foreign Key References Book(ISBN) ON DELETE CASCADE Not Null,
	bookDescription varchar(max) Not Null,
	copiesAvailable int Not Null
		constraint ck_copiesavailable check (copiesavailable >= 0),
	bookCover varchar (20) Not Null
		constraint ck_bookcover check (bookcover in ('Sert','Ciltli','Ciltsiz')),
)

CREATE TABLE Genre (
	genreId int identity (1,1) Primary Key,
	genreName varchar (100) Not Null
		constraint ck_genrename check (genrename like '%[a-zA-Z]%')
)

CREATE TABLE Category (
	categoryId int identity (1,1) Primary Key,
	ISBN char (13) Foreign Key References Book (ISBN) ON UPDATE CASCADE Not Null,
	genreId int Foreign Key References Genre (genreId) ON UPDATE CASCADE Not Null
)


CREATE TABLE Bookshelf (
	shelfId int identity (1,1) Primary Key,
	shelfNumber int Not Null,
	genreId int Foreign Key References Genre (genreId) ON UPDATE CASCADE Not Null
)

CREATE TABLE Place (
	placeId int Primary Key,
	ISBN char (13) Foreign Key References Book (ISBN) ON DELETE CASCADE Not Null,
	shelfId int Foreign Key References BookShelf (shelfId) ON DELETE CASCADE Not Null,
	librarianId int Foreign Key References Librarian (librarianId) ON UPDATE CASCADE,
	isOnShelf binary Not Null
)

CREATE TABLE Donation (
	donationId int identity (1,1) Primary Key,
	TCKN char(11) Foreign Key References Person (TCKN) ON UPDATE CASCADE Not Null,
	ISBN char (13) Foreign Key References Book (ISBN) ON UPDATE CASCADE Not Null,
	donationDate date Not Null
		default (getDate()),
	bookStatus varchar (30) Not Null
		constraint ck_bookstatus check (bookstatus in 
		('Ýyi','AzHasarlý','Yýpranmýþ','ÇokHasarlý','Kötü','EksikSayfa')),
	librarianId int Foreign Key References Librarian (librarianId) ON UPDATE CASCADE Not Null
)

CREATE TABLE Rent (
	rentId int identity (1,1) Primary Key,
	readerGuid varchar (255) Foreign Key References Reader (readerGuid) Not Null,
	ISBN char (13) Foreign Key References Book (ISBN) ON UPDATE CASCADE Not Null, 
	rentDate date Not Null
		default (getDate()),
	expirationDate date Not Null,
	hasReturned binary Not Null,
	librarianId int Foreign Key References Librarian (librarianId) ON UPDATE CASCADE Not Null
)

CREATE TABLE Cart (
	cartId int identity (1,1) Primary Key,
	readerGuid varchar (255) Foreign Key References Reader (readerGuid) Not Null,
	ISBN char (13) Foreign Key References Book (ISBN) ON UPDATE CASCADE Not Null, 
	createDate date Not Null
		default (getDate())
)

