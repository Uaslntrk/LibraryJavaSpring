package tr.edu.duzce.mf.bm.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.edu.duzce.mf.bm.entity.BookAPI;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.entity.Rental;
import tr.edu.duzce.mf.bm.service.BookAPIService;
import tr.edu.duzce.mf.bm.service.CartItemService;
import tr.edu.duzce.mf.bm.service.RentalService;
import tr.edu.duzce.mf.bm.wrapper.BookSearchResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class RentalController {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

    private final BookAPIService bookService;
    private final CartItemService cartService;
    private final RentalService rentalService;

    public RentalController(BookAPIService bookService, CartItemService cartService, RentalService rentalService) {
        this.bookService = bookService;
        this.cartService = cartService;
        this.rentalService = rentalService;
    }

    @GetMapping("/RentalsList")
    public String showRentalsList(HttpSession session, Model model) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");

        List<Rental> rentalRecords = Collections.emptyList();
        List<BookAPI> matchedRentals = new ArrayList<>();

        if (loggedInUser != null) {
            rentalRecords = rentalService.findByPerson(loggedInUser).stream()
                    .filter(rental -> !rental.isReturned())
                    .toList();

            for (Rental record : rentalRecords) {
                BookSearchResponse response = bookService.searchBooksByTitle(record.getTitle());

                if (response != null && response.getBooks() != null) {
                    for (BookAPI book : response.getBooks()) {
                        if (book.getAuthors() != null) {
                            boolean authorMatch = false;
                            for (String author : book.getAuthors()) {
                                if (author.equalsIgnoreCase(record.getAuthor())) {
                                    authorMatch = true;
                                    break;
                                }
                            }
                            if (authorMatch) {
                                matchedRentals.add(book);
                                break;
                            }
                        }
                    }
                }
            }
        }

        model.addAttribute("rentalRecords", rentalRecords);
        model.addAttribute("matchedRentals", matchedRentals);
        model.addAttribute("body", "/WEB-INF/view/HomeViews/Rentals.jsp");
        return "_Shared/_Layout";
    }

    @GetMapping("/Rentals")
    public String showRentals(HttpSession session, Model model) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        List<Rental> rentalRecords = Collections.emptyList();
        List<BookAPI> matchedRentals = new ArrayList<>();

        if (loggedInUser != null) {
            rentalRecords = rentalService.findByPerson(loggedInUser).stream()
                    .filter(rental -> !rental.isReturned())
                    .toList();

            for (Rental record : rentalRecords) {
                BookSearchResponse response = bookService.searchBooksByTitle(record.getTitle());

                if (response != null && response.getBooks() != null) {
                    for (BookAPI book : response.getBooks()) {
                        if (book.getAuthors() != null) {
                            boolean authorMatch = false;
                            for (String author : book.getAuthors()) {
                                if (author.equalsIgnoreCase(record.getAuthor())) {
                                    authorMatch = true;
                                    break;
                                }
                            }

                            if (authorMatch) {
                                matchedRentals.add(book);
                                break;
                            }

                        }
                    }
                }
            }
        }

        model.addAttribute("rentalRecords", rentalRecords);
        model.addAttribute("matchedRentals", matchedRentals);
        model.addAttribute("body", "/WEB-INF/view/HomeViews/BookInfo.jsp");
        return "_Shared/_Layout";
    }

    @PostMapping("/return-book")
    public String returnBook(@RequestParam("rentalId") Long rentalId, Model model) {
        try {
            rentalService.markAsReturned(rentalId);
            model.addAttribute("successMessage", "Kitap başarıyla iade edildi.");
            logger.info("returnBook() çağırıldı. Kitap iadesi başarılı: " + rentalId);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "İade işlemi sırasında bir hata oluştu: " + e.getMessage());
            logger.error("returnBook() çağırıldı. İade hatası: " + rentalId);
        }
        return "redirect:/RentalsList";
    }

    @PostMapping("/delete-rental")
    public String deleteRental(@RequestParam("rentalId") Long rentalId, HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        rentalService.deleteRental(rentalId);
        return "redirect:/Rentals";
    }

    @GetMapping("/returned")
    public String showHistory(HttpSession session, Model model) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        List<Rental> returnedRentals = Collections.emptyList();
        List<BookAPI> matchedRentals = new ArrayList<>();

        if (loggedInUser != null) {

            returnedRentals = rentalService.findByPerson(loggedInUser).stream()
                    .filter(Rental::isReturned)
                    .toList();

            for (Rental rental : returnedRentals) {
                BookSearchResponse response = bookService.searchBooksByTitle(rental.getTitle());
                if (response != null && response.getBooks() != null) {
                    for (BookAPI book : response.getBooks()) {
                        if (book.getAuthors() != null) {
                            boolean authorMatch = false;
                            for (String author : book.getAuthors()) {
                                if (author.equalsIgnoreCase(rental.getAuthor())) {
                                    authorMatch = true;
                                    break;
                                }
                            }
                            if (authorMatch) {
                                matchedRentals.add(book);
                                break;
                            }
                        }
                    }
                }
            }
        }

        model.addAttribute("rentalRecords", returnedRentals);
        model.addAttribute("matchedRentals", matchedRentals);
        model.addAttribute("body", "/WEB-INF/view/HomeViews/Returned.jsp");
        return "_Shared/_Layout";}

    @GetMapping("/allRentals")
    public String showAllRentals(Model model) {
        List<Rental> rentals = rentalService.getAllRentals();
        if (rentals == null || rentals.isEmpty()) {
            model.addAttribute("error", "Kiralama kaydı bulunamadı.");
        } else {
            model.addAttribute("rentals", rentals);
            model.addAttribute("success", "Kiralama kayıtları başarıyla yüklendi.");
        }
        return "BookInfo";
    }
}
