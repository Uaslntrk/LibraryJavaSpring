package tr.edu.duzce.mf.bm.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.edu.duzce.mf.bm.entity.BookAPI;
import tr.edu.duzce.mf.bm.entity.CartItem;
import tr.edu.duzce.mf.bm.entity.Favorite;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.service.BookAPIService;
import tr.edu.duzce.mf.bm.service.CartItemService;
import tr.edu.duzce.mf.bm.service.FavoriteService;
import tr.edu.duzce.mf.bm.wrapper.BookSearchResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookAPIService bookService;
    private final CartItemService cartService;
    private final FavoriteService favoriteService;

    @Autowired
    public BookController(BookAPIService bookService, CartItemService cartService, FavoriteService favoriteService) {
        this.bookService = bookService;
        this.cartService = cartService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/Books")
    public String searchBooks(
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            Model model,HttpSession session) {

        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            List<CartItem> cartItems = cartService.findByPerson(loggedInUser);
            model.addAttribute("cartItems", cartItems);
            List<Favorite> favItems=favoriteService.findByPerson(loggedInUser);
            model.addAttribute("favItems", favItems);

        } else {
            model.addAttribute("cartItems", Collections.emptyList());
            model.addAttribute("favItems", Collections.emptyList());
        }
        if (query == null || query.trim().isEmpty()) {
            query = "java";
        }

        int apiPage = ((page - 1) * pageSize) / 100 + 1;

        BookSearchResponse response = bookService.searchBooks(query, apiPage);
        List<BookAPI> books = Arrays.asList(response.getBooks());

        int startIndex = ((page - 1) * pageSize) % 100;
        int endIndex = Math.min(startIndex + pageSize, books.size());

        List<BookAPI> pageBooks = books.subList(startIndex, endIndex);

        int totalResults = response.getNumFound();
        int totalPages = (int) Math.ceil((double) totalResults / pageSize);

        model.addAttribute("books", pageBooks);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("query", query);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("body","/WEB-INF/view/HomeViews/book-list.jsp");

        return "_Shared/_Layout";
    }

    @GetMapping("/searchTitle")
    public String searchBooksByTitle(@RequestParam(value = "title", required = false) String title, Model model) {
        if (title != null && !title.isEmpty()) {
            BookSearchResponse response = bookService.searchBooksByTitle(title);
            model.addAttribute("books", response.getBooks());
        } else {
            model.addAttribute("books", new BookAPI[0]);
        }
        return "book-list-title";
    }
    @GetMapping("/book-detail")
    public String showBookDetail(@RequestParam("title") String title,
                                 @RequestParam("author") String author,
                                 Model model) {

        BookSearchResponse response = bookService.searchBooksByTitle(title);

        if (response != null && response.getBooks().length > 0) {
            BookAPI matchedBook = null;
            for (BookAPI book : response.getBooks()) {
                if (book.getAuthors() != null) {
                    for (String a : book.getAuthors()) {
                        if (a.equalsIgnoreCase(author.trim())) {
                            matchedBook = book;
                            break;
                        }
                    }
                }
                if (matchedBook != null) break;
            }

            if (matchedBook != null) {
                model.addAttribute("book", matchedBook);
                model.addAttribute("body", "/WEB-INF/view/HomeViews/book-detail.jsp");
                return "_Shared/_Layout";
            }
        }
        return "redirect:/Books?q=";
    }
}
