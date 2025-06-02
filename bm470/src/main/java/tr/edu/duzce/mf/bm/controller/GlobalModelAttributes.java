package tr.edu.duzce.mf.bm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;

@ControllerAdvice
public class GlobalModelAttributes {
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute
    public void addGlobalAttributes(Model model, Locale locale) {
        //Basic Translate
        model.addAttribute("close", messageSource.getMessage("close", null, locale));

        // Login Bölümü
        model.addAttribute("loginCreateAccount", messageSource.getMessage("login.create.account", null, locale));
        model.addAttribute("loginLogin", messageSource.getMessage("login.login", null, locale));
        model.addAttribute("loginPassword", messageSource.getMessage("login.password", null, locale));
        model.addAttribute("loginTckn", messageSource.getMessage("login.tokens", null, locale));
        model.addAttribute("loginAdminPage", messageSource.getMessage("login.admin", null, locale));
        model.addAttribute("loginAdminPageRedirected", messageSource.getMessage("login.admin.redirected", null, locale));


        // Register Bölümü
        model.addAttribute("registerSignup", messageSource.getMessage("register.sign.up", null, locale));
        model.addAttribute("registerLogin", messageSource.getMessage("register.login", null, locale));
        model.addAttribute("registerFirstname", messageSource.getMessage("register.firstname", null, locale));
        model.addAttribute("registerLastname", messageSource.getMessage("register.lastname", null, locale));
        model.addAttribute("registerTCKN", messageSource.getMessage("register.tokens", null, locale));
        model.addAttribute("registerBirthday", messageSource.getMessage("register.birthday", null, locale));
        model.addAttribute("registerGender", messageSource.getMessage("register.gender", null, locale));
        model.addAttribute("registerGenderWomen", messageSource.getMessage("register.gender.women", null, locale));
        model.addAttribute("registerGenderMan", messageSource.getMessage("register.gender.man", null, locale));
        model.addAttribute("registerEmail", messageSource.getMessage("register.email", null, locale));
        model.addAttribute("registerPassword", messageSource.getMessage("register.password", null, locale));
        model.addAttribute("registerPasswordAgain", messageSource.getMessage("register.password.again", null, locale));
        model.addAttribute("registerAlreadyAccount", messageSource.getMessage("register.already.account", null, locale));
        //model.addAttribute("registerError", messageSource.getMessage("register.sign.up", null, locale));

        model.addAttribute("registerPhoneNumber", messageSource.getMessage("register.phonumber", null, locale));
        // Register Contract
        model.addAttribute("registerContract", messageSource.getMessage("register.contract", null, locale));
        model.addAttribute("registerUserContract", messageSource.getMessage("register.user.contract", null, locale));
        model.addAttribute("registerContractOne", messageSource.getMessage("register.contract.one", null, locale));
        model.addAttribute("registerContractOneOne", messageSource.getMessage("register.contract.one.one", null, locale));
        model.addAttribute("registerContractTwo", messageSource.getMessage("register.contract.two", null, locale));
        model.addAttribute("registerContractTwoOne", messageSource.getMessage("register.contract.two.one", null, locale));
        model.addAttribute("registerContractTwoTwo", messageSource.getMessage("register.contract.two.two", null, locale));
        model.addAttribute("registerContractTwoThree", messageSource.getMessage("register.contract.two.three", null, locale));
        model.addAttribute("registerContractThree", messageSource.getMessage("register.contract.three", null, locale));
        model.addAttribute("registerContractThreeOne", messageSource.getMessage("register.contract.three.one", null, locale));
        model.addAttribute("registerContractThreeTwo", messageSource.getMessage("register.contract.three.two", null, locale));
        model.addAttribute("registerContractThreeThree", messageSource.getMessage("register.contract.three.three", null, locale));
        model.addAttribute("registerContractFour", messageSource.getMessage("register.contract.four", null, locale));
        model.addAttribute("registerContractFourOne", messageSource.getMessage("register.contract.four.one", null, locale));
        model.addAttribute("registerContractFive", messageSource.getMessage("register.contract.five", null, locale));
        model.addAttribute("registerContractFiveOne", messageSource.getMessage("register.contract.five.one", null, locale));
        model.addAttribute("registerContractAccept", messageSource.getMessage("register.contract.accept", null, locale));
        model.addAttribute("registerTCKNError", messageSource.getMessage("register.tckn.error", null, locale));
        model.addAttribute("registerFirstnameError", messageSource.getMessage("register.firstname.error", null, locale));
        model.addAttribute("registerLastnameError", messageSource.getMessage("register.lastname.error", null, locale));
        model.addAttribute("registerPhonenumberError", messageSource.getMessage("register.phoneNumber.error", null, locale));
        model.addAttribute("registerPasswordError", messageSource.getMessage("register.password.error", null, locale));


        // Librarian Profile
        model.addAttribute("librarianProfile",messageSource.getMessage("librarian.profile",null,locale));
        model.addAttribute("librarianTitle",messageSource.getMessage("librarian.title",null,locale));
        model.addAttribute("librarianTCKN",messageSource.getMessage("librarian.tckn",null,locale));
        model.addAttribute("librarianName",messageSource.getMessage("librarian.name",null,locale));
        model.addAttribute("librarianGender",messageSource.getMessage("librarian.gender",null,locale));
        model.addAttribute("librarianChange",messageSource.getMessage("librarian.change",null,locale));
        model.addAttribute("librarianID",messageSource.getMessage("librarian.id",null,locale));
        model.addAttribute("librarianEmail",messageSource.getMessage("librarian.email",null,locale));
        model.addAttribute("librarianNumber",messageSource.getMessage("librarian.number",null,locale));
        model.addAttribute("librarianPassword",messageSource.getMessage("librarian.password",null,locale));

        // Admin Books Bölümü
        model.addAttribute("bookTransaction",messageSource.getMessage("book.info.book.transactions",null,locale));
        model.addAttribute("bookDetail",messageSource.getMessage("book.info.book.detail",null,locale));
        model.addAttribute("bookBookName",messageSource.getMessage("book.info.book.name",null,locale));
        model.addAttribute("bookBookAuthor",messageSource.getMessage("book.info.book.author",null,locale));
        model.addAttribute("bookBookPageNumber",messageSource.getMessage("book.info.book.page.number",null,locale));
        model.addAttribute("bookBookPublicationDate",messageSource.getMessage("book.info.book.publication.date",null,locale));
        model.addAttribute("booksSearch",messageSource.getMessage("book.reseach",null,locale));
        model.addAttribute("booksBookSearch",messageSource.getMessage("book.book.reseach",null,locale));
        model.addAttribute("booksUnknow",messageSource.getMessage("book.unknow",null,locale));
        model.addAttribute("booksCartAdd",messageSource.getMessage("book.cart.add",null,locale));
        model.addAttribute("booksCartDelete",messageSource.getMessage("book.cart.delete",null,locale));
        model.addAttribute("booksFavoriteAdd",messageSource.getMessage("book.favorite.add",null,locale));
        model.addAttribute("booksFavoriteDelete",messageSource.getMessage("book.favorite.delete",null,locale));
        model.addAttribute("booksPrevius",messageSource.getMessage("book.previus",null,locale));
        model.addAttribute("booksNext",messageSource.getMessage("book.next",null,locale));
        model.addAttribute("booksBookPage",messageSource.getMessage("book.page",null,locale));


        // Admin Users Bölümü

        model.addAttribute("librarianUserDelete",messageSource.getMessage("librarian.user.delete",null,locale));
        model.addAttribute("librarianAll",messageSource.getMessage("labrarian.all",null,locale));
        model.addAttribute("librarianInfo",messageSource.getMessage("librarian.info",null,locale));
        model.addAttribute("librarianBanned",messageSource.getMessage("librarian.banned",null,locale));
        model.addAttribute("librarianAktive",messageSource.getMessage("librarian.aktive",null,locale));
        model.addAttribute("librarianDoBanned",messageSource.getMessage("librarian.do.banned",null,locale));


        model.addAttribute("bookProcess",messageSource.getMessage("book.info.book.transactions",null,locale));
        model.addAttribute("memberTransactions",messageSource.getMessage("book.info.person.transactions",null,locale));

        // Layout Bölümü
        model.addAttribute("layoutName",messageSource.getMessage("layout.namess",null,locale));
        model.addAttribute("layoutAbout",messageSource.getMessage("layout.about",null,locale));
        model.addAttribute("layoutHistory",messageSource.getMessage("layout.history",null,locale));
        model.addAttribute("layoutMission",messageSource.getMessage("layout.mission",null,locale));
        model.addAttribute("layoutVision",messageSource.getMessage("layout.vision",null,locale));
        model.addAttribute("layoutCollection",messageSource.getMessage("layout.collectionn",null,locale));
        model.addAttribute("layoutLogin",messageSource.getMessage("layout.login",null,locale));
        model.addAttribute("layoutUniversity",messageSource.getMessage("layout.university",null,locale));
        model.addAttribute("layoutLocation",messageSource.getMessage("layout.location",null,locale));
        model.addAttribute("layoutNumber",messageSource.getMessage("layout.numberr",null,locale));
        model.addAttribute("layoutEmail",messageSource.getMessage("layout.emaill",null,locale));
        model.addAttribute("layoutEmailAnswer",messageSource.getMessage("layout.email.answer",null,locale));
        model.addAttribute("layoutRights",messageSource.getMessage("layout.rights",null,locale));
        model.addAttribute("layoutAllBooks",messageSource.getMessage("layout.all.book",null,locale));
        model.addAttribute("layoutContact",messageSource.getMessage("layout.contact",null,locale));
        model.addAttribute("layoutProfile",messageSource.getMessage("layout.profile",null,locale));
        model.addAttribute("layoutFavorite",messageSource.getMessage("layout.favorite",null,locale));
        model.addAttribute("layoutSignOut",messageSource.getMessage("layout.sign.out",null,locale));
        model.addAttribute("layoutRental",messageSource.getMessage("layout.book.rental",null,locale));
        model.addAttribute("layoutBookPast",messageSource.getMessage("layout.book.past",null,locale));

        // Hero bölümü
        model.addAttribute("heroBadge", messageSource.getMessage("hero.badge", null, locale));
        model.addAttribute("heroTitle1", messageSource.getMessage("hero.title1", null, locale));
        model.addAttribute("heroTitle2", messageSource.getMessage("hero.title2", null, locale));
        model.addAttribute("heroTitle3", messageSource.getMessage("hero.title3", null, locale));
        model.addAttribute("heroDescription", messageSource.getMessage("hero.description", null, locale));

        // Stats kısmı
        model.addAttribute("statsBooks", messageSource.getMessage("stats.books", null, locale));
        model.addAttribute("statsBooksDesc", messageSource.getMessage("stats.books.desc", null, locale));
        model.addAttribute("statsaAuthors", messageSource.getMessage("stats.authors", null, locale));
        model.addAttribute("statsAuthorsDesc", messageSource.getMessage("stats.authors.desc", null, locale));
        model.addAttribute("statsCatagories", messageSource.getMessage("stats.categories", null, locale));
        model.addAttribute("statsCatagoriesDesc", messageSource.getMessage("stats.categories.desc", null, locale));


        // Services kısmı
        model.addAttribute("serviesTitle", messageSource.getMessage("services.title", null, locale));
        model.addAttribute("servicesTab1", messageSource.getMessage("services.tab1", null, locale));
        model.addAttribute("servicesTab2", messageSource.getMessage("services.tab2", null, locale));
        model.addAttribute("servicesTab3", messageSource.getMessage("services.tab3", null, locale));

        //Tab1 Content
        model.addAttribute("tab1Title", messageSource.getMessage("tab1.title", null, locale));
        model.addAttribute("tab1Description", messageSource.getMessage("tab1.description", null, locale));
        model.addAttribute("tab1Point1", messageSource.getMessage("tab1.point1", null, locale));
        model.addAttribute("tab1Point2",messageSource.getMessage("tab1.point2",null,locale));
        model.addAttribute("tab1Point3",messageSource.getMessage("tab1.point3",null,locale));

        //Tab2 Content
        model.addAttribute("tab2Title", messageSource.getMessage("tab2.title", null, locale));
        model.addAttribute("tab2Description", messageSource.getMessage("tab2.description", null, locale));
        model.addAttribute("tab2Point1", messageSource.getMessage("tab2.point1", null, locale));
        model.addAttribute("tab2Point2",messageSource.getMessage("tab2.point2",null,locale));

        //Tab3 Content
        model.addAttribute("tab3Title", messageSource.getMessage("tab3.title", null, locale));
        model.addAttribute("tab3Description", messageSource.getMessage("tab3.description", null, locale));
        model.addAttribute("tab3Point1", messageSource.getMessage("tab3.point1", null, locale));
        model.addAttribute("tab3Point2",messageSource.getMessage("tab3.point2",null,locale));

        //Bottum Stats kısmı

        model.addAttribute("statsEncyclopedia", messageSource.getMessage("stats.encyclopedia", null, locale));
        model.addAttribute("statsNovels",messageSource.getMessage("stats.novels",null,locale));
        model.addAttribute("statsScifi",messageSource.getMessage("stats.scifi",null,locale));
        model.addAttribute("statsHorror",messageSource.getMessage("stats.horror",null,locale));

        //History Bölümü

        model.addAttribute("layoutAllBooks",messageSource.getMessage("layout.all.book",null,locale));

        model.addAttribute("layoutContact",messageSource.getMessage("layout.contact",null,locale));
        model.addAttribute("historyTitle", messageSource.getMessage("history.title", null, locale));
        model.addAttribute("historyTab1Title", messageSource.getMessage("history.tab1.title", null, locale));
        model.addAttribute("historyTab1Content", messageSource.getMessage("history.tab1.content", null, locale));

        //Mission Bölümü

        model.addAttribute("missionTitle", messageSource.getMessage("mission.title", null, locale));
        model.addAttribute("missionTab1Title", messageSource.getMessage("mission.tab1.title", null, locale));
        model.addAttribute("missionTab1Content", messageSource.getMessage("mission.tab1.content", null, locale));

        //Vision Bölümü

        model.addAttribute("visionTitle", messageSource.getMessage("vision.title", null, locale));
        model.addAttribute("visionTab1Title", messageSource.getMessage("vision.tab1.title", null, locale));
        model.addAttribute("visionTab1Content", messageSource.getMessage("vision.tab1.content", null, locale));

        //Cart  bookInfo Bölümü
        model.addAttribute("cartTitle", messageSource.getMessage("cart.title", null, locale));
        model.addAttribute("cartInfo", messageSource.getMessage("cart.info", null, locale));
        model.addAttribute("cartRental", messageSource.getMessage("cart.rental", null, locale));
        model.addAttribute("cartStartLook", messageSource.getMessage("cart.look.start", null, locale));
        model.addAttribute("cartBookTitle", messageSource.getMessage("cart.book.title", null, locale));
        model.addAttribute("cartBookAll", messageSource.getMessage("cart.book.all", null, locale));
        model.addAttribute("cartDelivered", messageSource.getMessage("cart.delivered", null, locale));
        model.addAttribute("cartReturned", messageSource.getMessage("cart.returned", null, locale));
        model.addAttribute("cartnotReturned", messageSource.getMessage("cart.notreturned", null, locale));
        model.addAttribute("cartRental", messageSource.getMessage("cart.retal", null, locale));
        model.addAttribute("cartRentalSize", messageSource.getMessage("cart.retal.size", null, locale));
        model.addAttribute("cartRentalDate", messageSource.getMessage("cart.retal.date", null, locale));
        model.addAttribute("cartRentalStatus", messageSource.getMessage("cart.retal.status", null, locale));






        //Cart Bölümü
        model.addAttribute("favoriteTitle", messageSource.getMessage("favorite.title", null, locale));
        model.addAttribute("favoriteInfo", messageSource.getMessage("favorite.info", null, locale));
        model.addAttribute("favoriteRental", messageSource.getMessage("favorite.rental", null, locale));

        //Rental Bölümü
        model.addAttribute("rentalTitle", messageSource.getMessage("rental.title", null, locale));
        model.addAttribute("rentalRental", messageSource.getMessage("rental.info", null, locale));
        model.addAttribute("rentalBack", messageSource.getMessage("rental.comeback", null, locale));
        model.addAttribute("rentalInfo2", messageSource.getMessage("rental.info2", null, locale));

        //Returned Bölümü
        model.addAttribute("returnedTitle", messageSource.getMessage("returned.title", null, locale));
        model.addAttribute("returnedInfo", messageSource.getMessage("returned.info", null, locale));
        model.addAttribute("returnedDate", messageSource.getMessage("returned.date", null, locale));
        model.addAttribute("returnedReturned", messageSource.getMessage("returned.date.accept", null, locale));
        model.addAttribute("returnedInfo2", messageSource.getMessage("returned.info2", null, locale));

        //Admin Bölümü
        model.addAttribute("adminPanel", messageSource.getMessage("admin.panel", null, locale));
        model.addAttribute("returnedInfo", messageSource.getMessage("returned.info", null, locale));
        model.addAttribute("returnedDate", messageSource.getMessage("returned.date", null, locale));
        model.addAttribute("returnedReturned", messageSource.getMessage("returned.date.accept", null, locale));
        model.addAttribute("returnedInfo2", messageSource.getMessage("returned.info2", null, locale));



    }
}
