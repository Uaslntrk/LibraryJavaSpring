
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import tr.edu.duzce.mf.bm.config.AppConfig;
import tr.edu.duzce.mf.bm.config.WebAppInitializer;
import tr.edu.duzce.mf.bm.config.WebConfig;
import tr.edu.duzce.mf.bm.controller.FavoriteController;
import tr.edu.duzce.mf.bm.dao.FavoriteDao;
import tr.edu.duzce.mf.bm.dao.PersonDAO;
import tr.edu.duzce.mf.bm.entity.BookAPI;
import tr.edu.duzce.mf.bm.entity.Favorite;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.service.CartItemService;
import tr.edu.duzce.mf.bm.service.FavoriteService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, WebAppInitializer.class, AppConfig.class})
@Transactional
@WebAppConfiguration
public class TestFavoriteController {

    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();
    private MockHttpSession session = new MockHttpSession();
    Model model = new ExtendedModelMap();

    @Autowired
    private FavoriteDao favoriteDao;
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private FavoriteController favoriteController;

    @Autowired
    private CartItemService cartItemService;
    Person testPerson = new Person();
    Favorite favorite = new Favorite();

    @BeforeEach
    public void setUpPerson(){

        favoriteService.deleteAllFavoritesForUser(testPerson);
        testPerson.setTckn("12345678901");
        testPerson.setFirstName("TestUser");
        testPerson.setLastName("TestUser");
        testPerson.setBanned(false);
        testPerson.setEmail("testperson@gmail.com");
        testPerson.setPhoneNumber("01111111111");
        testPerson.setGender("Male");
        testPerson.setPassword("123456");
        LocalDate localDate = LocalDate.of(1990, 1, 1);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        testPerson.setBirthday(date);
        personDAO.saveOrUpdatePerson(testPerson);
    }

    @Test
    public void testShowCart_WithLoggedInUser_ShouldRedirectToCart(){
        String viewName = favoriteController.showCart(session, model);

        assertTrue(((List<?>) model.getAttribute("favItems")).isEmpty());
        assertTrue(((List<?>) model.getAttribute("cartItems")).isEmpty());
        assertTrue(((List<?>) model.getAttribute("matchedBooks")).isEmpty());

        assertEquals("/WEB-INF/view/HomeViews/Favorites.jsp", model.getAttribute("body"));
    }

    @Test
    public void testShowCart_WithLoggedInUser_ShouldReturnFavoritesAndMatchedBooks(){
        session.setAttribute("loggedInUser", testPerson);

        String title = "TestTitle";
        String author = "TestAuthor";
        favoriteService.addFavorite(testPerson,title,author);

        String viewName = favoriteController.showCart(session, model);

        assertEquals("_Shared/_Layout", viewName);

        List<Favorite> favItems = (List<Favorite>) model.getAttribute("favItems");
        assertFalse(favItems.isEmpty());

        List<BookAPI> matchedBooks = (List<BookAPI>) model.getAttribute("matchedBooks");

        assertNotNull(matchedBooks, "matchedBooks null dönmemeli. İçeriği dış API'ye bağlı olduğu için kontrol edilmiyor.");


        assertEquals("/WEB-INF/view/HomeViews/Favorites.jsp", model.getAttribute("body"));
    }

    @Test
    @Transactional
    public void testAddToFavorites_WithoutLogedInUser_ShouldRedirectToLogin() {
        String title = "TestTitle";
        String author ="TestAuthor";

        request.addParameter("title", title);
        request.addParameter("author", author);
        request.setSession(session);

        String result = favoriteController.addToFavorites(title,author,request,session,model);
        assert result.equals("redirect:/login");
    }

    @Test
    @Transactional
    public void testRemoveFromFavorites_WithLoggedInUser_ShouldRedirectToReferer() {
        String title = "TestTitle";
        String author ="TestAuthor";
        String referer ="/favorites/list";

        favorite.setTitle(title);
        favorite.setAuthor(author);
        favorite.setUser(testPerson);

        request.addParameter("title", title);
        request.addParameter("author", author);
        request.addHeader("referer", referer);

        session.setAttribute("loggedInUser", testPerson);
        request.setSession(session);

        String result = favoriteController.removeFromFavorites(title,author,request,session);

        assert result.equals("redirect:" + referer);
    }

    @Test
    @Transactional
    public void testRemoveFromFavorites_WithoutLogedInUser_ShouldRedirectToLogin() {
        String title = "TestTitle";
        String author ="TestAuthor";

        request.addParameter("title", title);
        request.addParameter("author", author);

        request.setSession(session);

        String result = favoriteController.removeFromFavorites(title,author,request,session);

        assert result.equals("redirect:/login");
    }

}
