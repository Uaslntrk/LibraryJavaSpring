package tr.edu.duzce.mf.bm.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.edu.duzce.mf.bm.entity.BookAPI;
import tr.edu.duzce.mf.bm.entity.CartItem;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.entity.Rental;
import tr.edu.duzce.mf.bm.service.BookAPIService;
import tr.edu.duzce.mf.bm.service.CartItemService;
import tr.edu.duzce.mf.bm.service.RentalService;
import tr.edu.duzce.mf.bm.wrapper.BookSearchResponse;

import java.util.*;

@Controller
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final BookAPIService bookService;
    private final CartItemService cartService;
    private final RentalService rentalService;

    public CartController(BookAPIService bookService, CartItemService cartService, RentalService rentalService) {
        this.bookService = bookService;
        this.cartService = cartService;
        this.rentalService = rentalService;}

    @GetMapping("/Cart")
    public String showCart(HttpSession session, Model model) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        List<CartItem> cartItems = Collections.emptyList();
        List<BookAPI> matchedBooks = new ArrayList<>();

        if (loggedInUser != null) {
            cartItems = cartService.findByPerson(loggedInUser);

            for (CartItem cartItem : cartItems) {
                BookSearchResponse response = bookService.searchBooksByTitle(cartItem.getTitle());

                if (response != null && response.getBooks() != null) {
                    for (BookAPI book : response.getBooks()) {
                        if (book.getAuthors() != null) {
                            boolean authorMatch = false;
                            for (String author : book.getAuthors()) {
                                if (author.equalsIgnoreCase(cartItem.getAuthor())) {
                                    logger.info("showCart() çağırıldı. Kitap ve yazar eşleşmesi.");
                                    authorMatch = true;
                                    break;
                                }
                            }

                            if (authorMatch) {
                                matchedBooks.add(book);
                                break;
                            }
                        }
                    }
                }
            }
        }else{
            logger.debug("showCart() çağırıldı. Kullanıcı bulunamadı: " +loggedInUser);
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("matchedBooks", matchedBooks);
        model.addAttribute("body","/WEB-INF/view/HomeViews/Cart.jsp");
        logger.info("showCart() çağırıldı. Sayfa yönlendirmesi: Cart.jsp");
        return "_Shared/_Layout";}

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("title") String title,
                            @RequestParam("author") String author,
                            HttpServletRequest request,
                            HttpSession session, Model model) {
        logger.info("addToCart() çağırıldı. Sepete eklenecek kitap: " + title + " " + author);
        Person user = (Person) session.getAttribute("loggedInUser");
        logger.info("addToCart() çağırıldı. Oturumdaki kullanıcı: " + user);
        if (user == null) {
            logger.debug("addToCart() çağırıldı. Kullanıcı oturumu bulunamadı. Sayfa yönlendirmesi: Login.jsp");
            model.addAttribute("errorMessage", "Lütfen önce giriş yapınız.");
            return "redirect:/login";
        }

        StringBuilder builder = new StringBuilder();
        String users = builder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();

        String message = cartService.addBookToCart(user, title, author);
        model.addAttribute("adderrormessage", message);

        String referer = request.getHeader("Referer");
        if (referer != null) {
            logger.debug("addToCart() çağırıldı. Header hatası.");
            return "redirect:" + referer;
        } else {
            return "redirect:/Cart";
        }}

    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam("title") String title,
                                 @RequestParam("author") String author,
                                 HttpServletRequest request,
                                 HttpSession session) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        logger.info("removeFromCart() çağırıldı. Oturumdaki kullanıcı: " + loggedInUser);
        if (loggedInUser == null) {
            logger.debug("removeFromCar() çağırıldı. Oturumda kullanıcı bulunamadı. Sayfa yönlendirmesi: Login.jsp");
            return "redirect:/login";
        }

        List<CartItem> cartItems = cartService.findByPerson(loggedInUser);
        logger.info("removeFromCart) çağırıldı. Sepette bulunan ürünler: [ " + cartItems + "]");

        for (CartItem item : cartItems) {
            if (item.getTitle().equals(title) && item.getAuthor().equals(author)) {
                cartService.delete(item);
                logger.info("removeFromCart() çağırıldı. Sepetten ürün silindi: "+ item);
                break;
            }
        }

        String referer = request.getHeader("Referer");
        if (referer != null) {
            return "redirect:" + referer;
        } else {
            logger.debug("removeFromCart() çağırıldı. Varsayılan sayfa: Cart");
            return "redirect:/Cart";
        }
    }

    @PostMapping("/rent-books")
    public String rentBooks(HttpSession session, RedirectAttributes redirectAttributes) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        logger.info("rentBooks() çağırıldı. Oturumdaki kullanıcı: " + loggedInUser);
        if (loggedInUser == null) {
            logger.debug("rentBooks() çağırıldı. Oturumda kullanıcı bulunamadı: Sayfa yönlendirmesi: Login.jsp");
            return "redirect:/login";
        }

        try {
            cartService.rentBooks(loggedInUser);
            redirectAttributes.addFlashAttribute("success", "Kitaplar başarıyla kiralandı.");
            logger.info("rentBooks() çağırıldı. Kitap kiralandı.");
        } catch (Exception e) {
            logger.error("rentBooks() çağırıldı. Kiralama hatası.");
            redirectAttributes.addFlashAttribute("error", "Kiralama sırasında hata: " + e.getMessage());
        }
        return "redirect:/Cart";
    }

}