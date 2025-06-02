import jakarta.transaction.Transactional;
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
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import tr.edu.duzce.mf.bm.config.AppConfig;
import tr.edu.duzce.mf.bm.config.WebAppInitializer;
import tr.edu.duzce.mf.bm.config.WebConfig;
import tr.edu.duzce.mf.bm.controller.CartController;
import tr.edu.duzce.mf.bm.dao.PersonDAO;
import tr.edu.duzce.mf.bm.entity.Person;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebAppInitializer.class, WebConfig.class, AppConfig.class})
@WebAppConfiguration
@Transactional
public class TestCartController {

    @Autowired
    private CartController cartController;

    @Autowired
    private PersonDAO personDao;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private Person loggedInUser;
    private Model model;
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();
        model = new ConcurrentModel();
        redirectAttributes = new RedirectAttributesModelMap();

        // Test kullanıcısı oluştur
        loggedInUser = new Person();
        loggedInUser.setTckn("12345678901");
        loggedInUser.setFirstName("Ali");
        loggedInUser.setLastName("Yılmaz");
        LocalDate localDate = LocalDate.of(1990, 1, 1);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        loggedInUser.setBirthday(date);
        loggedInUser.setPassword("password123");
        loggedInUser.setEmail("ali.yilmaz@example.com");
        loggedInUser.setGender("Male");
        loggedInUser.setBanned(false);
        personDao.saveOrUpdatePerson(loggedInUser);
    }

    @Test
    public void testShowCart_LoggedInUser_ReturnsCartView() {

        session.setAttribute("loggedInUser", loggedInUser);


        String viewName = cartController.showCart(session, model);

        assertEquals("_Shared/_Layout", viewName, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("cartItems"), "Model 'cartItems' özniteliğini içermeli");
        assertTrue(model.containsAttribute("matchedBooks"), "Model 'matchedBooks' özniteliğini içermeli");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/Cart.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
        assertNotNull(model.getAttribute("cartItems"), "cartItems özniteliği null olmamalı");
        assertNotNull(model.getAttribute("matchedBooks"), "matchedBooks özniteliği null olmamalı");
    }

    @Test
    public void testShowCart_NoLoggedInUser_ReturnsEmptyCart() {

        session.setAttribute("loggedInUser", null);

        String viewName = cartController.showCart(session, model);

        assertEquals("_Shared/_Layout", viewName, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("cartItems"), "Model 'cartItems' özniteliğini içermeli");
        assertTrue(model.containsAttribute("matchedBooks"), "Model 'matchedBooks' özniteliğini içermeli");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/Cart.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
        assertEquals(0, ((java.util.List<?>) model.getAttribute("cartItems")).size(), "cartItems boş olmalı");
        assertEquals(0, ((java.util.List<?>) model.getAttribute("matchedBooks")).size(), "matchedBooks boş olmalı");
    }

    @Test
    public void testAddToCart_LoggedInUser_Success_RedirectsToReferer() {

        session.setAttribute("loggedInUser", loggedInUser);
        request.setParameter("title", "Test Kitap");
        request.setParameter("author", "Test Yazar");
        request.addHeader("Referer", "/previous-page");

        String result = cartController.addToCart("Test Kitap", "Test Yazar", request, session, model);

        assertEquals("redirect:/previous-page", result, "Referer URL'sine yönlendirmeli");
        assertTrue(model.containsAttribute("adderrormessage"), "Model 'adderrormessage' özniteliğini içermeli");
    }

    @Test
    public void testAddToCart_NoLoggedInUser_RedirectsToLogin() {

        session.setAttribute("loggedInUser", null);
        request.setParameter("title", "Test Kitap");
        request.setParameter("author", "Test Yazar");
        request.addHeader("Referer", "/previous-page");

        String result = cartController.addToCart("Test Kitap", "Test Yazar", request, session, model);

        assertEquals("redirect:/login", result, "Giriş yapmamışsa login sayfasına yönlendirmeli");
        assertTrue(model.containsAttribute("errorMessage"), "Model 'errorMessage' özniteliğini içermeli");
        assertEquals("Lütfen önce giriş yapınız.", model.getAttribute("errorMessage"), "Hata mesajı doğru olmalı");
    }

    @Test
    public void testAddToCart_NoReferer_RedirectsToCart() {

        session.setAttribute("loggedInUser", loggedInUser);
        request.setParameter("title", "Test Kitap");
        request.setParameter("author", "Test Yazar");

        String result = cartController.addToCart("Test Kitap", "Test Yazar", request, session, model);

        assertEquals("redirect:/Cart", result, "Referer yoksa Cart sayfasına yönlendirmeli");
        assertTrue(model.containsAttribute("adderrormessage"), "Model 'adderrormessage' özniteliğini içermeli");
    }

    @Test
    public void testRemoveFromCart_LoggedInUser_Success_RedirectsToReferer() {

        session.setAttribute("loggedInUser", loggedInUser);
        request.setParameter("title", "Test Kitap");
        request.setParameter("author", "Test Yazar");
        request.addHeader("Referer", "/previous-page");

        String result = cartController.removeFromCart("Test Kitap", "Test Yazar", request, session);

        assertEquals("redirect:/previous-page", result, "Referer URL'sine yönlendirmeli");
    }

    @Test
    public void testRemoveFromCart_NoLoggedInUser_RedirectsToLogin() {

        session.setAttribute("loggedInUser", null);
        request.setParameter("title", "Test Kitap");
        request.setParameter("author", "Test Yazar");
        request.addHeader("Referer", "/previous-page");

        String result = cartController.removeFromCart("Test Kitap", "Test Yazar", request, session);

        assertEquals("redirect:/login", result, "Giriş yapmamışsa login sayfasına yönlendirmeli");
    }

    @Test
    public void testRemoveFromCart_NoReferer_RedirectsToCart() {

        session.setAttribute("loggedInUser", loggedInUser);
        request.setParameter("title", "Test Kitap");
        request.setParameter("author", "Test Yazar");

        String result = cartController.removeFromCart("Test Kitap", "Test Yazar", request, session);

        assertEquals("redirect:/Cart", result, "Referer yoksa Cart sayfasına yönlendirmeli");
    }

    @Test
    public void testRentBooks_LoggedInUser_Success_RedirectsToCart() {

        session.setAttribute("loggedInUser", loggedInUser);

        String result = cartController.rentBooks(session, redirectAttributes);

        assertEquals("redirect:/Cart", result, "Cart sayfasına yönlendirmeli");
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("success"), "Flash 'success' özniteliği olmalı");
        assertEquals("Kitaplar başarıyla kiralandı.", redirectAttributes.getFlashAttributes().get("success"), "Başarı mesajı doğru olmalı");
    }

    @Test
    public void testRentBooks_NoLoggedInUser_RedirectsToLogin() {

        session.setAttribute("loggedInUser", null);

        String result = cartController.rentBooks(session, redirectAttributes);

        assertEquals("redirect:/login", result, "Giriş yapmamışsa login sayfasına yönlendirmeli");
        assertFalse(redirectAttributes.getFlashAttributes().containsKey("success"), "Flash 'success' özniteliği olmamalı");
        assertFalse(redirectAttributes.getFlashAttributes().containsKey("error"), "Flash 'error' özniteliği olmamalı");
    }

    @Test
    public void testRentBooks_RentalServiceThrowsException_RedirectsToCartWithError() {

        session.setAttribute("loggedInUser", loggedInUser);

        String result = cartController.rentBooks(session, redirectAttributes);

        assertEquals("redirect:/Cart", result, "Cart sayfasına yönlendirmeli");

    }
}