package tr.edu.duzce.mf.bm.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller

public class FavoriteController {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    private final BookAPIService bookService;
    private final FavoriteService favoriteService;
    private final CartItemService cartService;

    public FavoriteController(BookAPIService bookService, FavoriteService favoriteService, CartItemService cartService) {
        this.bookService = bookService;
        this.favoriteService = favoriteService;
        this.cartService = cartService;
    }
    @GetMapping("/Favorites")
    public String showCart(HttpSession session, Model model) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        logger.info("showCart() çağırıldı. Oturumdaki kullanıcı: " +loggedInUser);

        List<Favorite> favItems = Collections.emptyList();
        List<BookAPI> matchedBooks = new ArrayList<>();
        List<CartItem> cartItems=Collections.emptyList();

        if (loggedInUser != null) {
            favItems = favoriteService.findByPerson(loggedInUser);
            cartItems = cartService.findByPerson(loggedInUser);

            for (Favorite favItem : favItems) {
                BookSearchResponse response = bookService.searchBooksByTitle(favItem.getTitle());

                if (response != null && response.getBooks() != null) {
                    for (BookAPI book : response.getBooks()) {
                        if (book.getAuthors() != null) {

                            boolean authorMatch = false;
                            for (String author : book.getAuthors()) {
                                if (author.equalsIgnoreCase(favItem.getAuthor())) {
                                    logger.info("showCart() çağırıldı. Kitap ve yazar eşleşmesi.");
                                    authorMatch = true;
                                    break;
                                }
                            }

                            if (authorMatch) {
                                matchedBooks.add(book);
                                logger.info("showCart çağırıldı. Yazar eşleşmesi bulundu: " + book);
                                break;
                            }
                        }
                    }
                }
            }
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("favItems", favItems);
        model.addAttribute("matchedBooks", matchedBooks);
        model.addAttribute("body","/WEB-INF/view/HomeViews/Favorites.jsp");

        return "_Shared/_Layout";
    }

    @PostMapping("/add-to-favorites")
    public String addToFavorites(@RequestParam("title") String title,
                                 @RequestParam("author") String author,
                                 HttpServletRequest request,
                                 HttpSession session, Model model) {
        Person user = (Person) session.getAttribute("loggedInUser");
        logger.info("addToFavorites() çağırıldı. Oturumdaki kullanıcı: " + user);
        if (user == null) {
            logger.debug("addToFavorites() çağırıldı. Oturumda kullanıcı bulunamadı. Sayfa yönlendirmesi: Login" );
            model.addAttribute("errorMessage", "Lütfen önce giriş yapınız.");
            return "redirect:/login";
        }

        favoriteService.addFavorite(user,title,author);
        String referer = request.getHeader("Referer");
        if (referer != null) {
            return "redirect:" + referer;
        }else{
            logger.debug("addToFavorites() çağırıldı. Header bulunamadı: " + referer);
            return "redirect:/Favorites";
        }
    }
    @PostMapping("/remove-from-favorites")
    public String removeFromFavorites(@RequestParam("title") String title,
                                      @RequestParam("author") String author,
                                      HttpServletRequest request,
                                      HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        logger.info("removeFromFavorites() çağırıldı. Oturumdaki kullanıcı: " + loggedInUser);
        if (loggedInUser == null) {
            logger.debug("removeFromFavorites() çağırıldı. Sayfa yönlendirmesi: Login" );
            return "redirect:/login";
        }

        List<Favorite> favItems = favoriteService.findByPerson(loggedInUser);
        logger.info("removeFromFavorites() çağırıldı. Favori kitaplar çekildi: [" + favItems +"]");
        for (Favorite item : favItems) {
            if (item.getTitle().equals(title) && item.getAuthor().equals(author)) {
                logger.info("removeFromFavorites(). Favorilerden silindi: " + item);
                favoriteService.delete(item);
                break;
            }
        }

        String referer = request.getHeader("Referer");
        if (referer != null) {
            return "redirect:" + referer;
        } else {
            logger.debug("removeFromFavorites() çağırıldı. Header bulunamadı: " + referer);
            return "redirect:/Favorites";
        }
    }
}
