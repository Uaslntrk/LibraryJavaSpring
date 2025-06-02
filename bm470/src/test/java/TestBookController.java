import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import tr.edu.duzce.mf.bm.config.AppConfig;
import tr.edu.duzce.mf.bm.config.WebAppInitializer;
import tr.edu.duzce.mf.bm.config.WebConfig;
import tr.edu.duzce.mf.bm.controller.BookController;
import tr.edu.duzce.mf.bm.dao.PersonDAO;
import tr.edu.duzce.mf.bm.entity.BookAPI;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.wrapper.BookSearchResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebAppInitializer.class, WebConfig.class, AppConfig.class})
@WebAppConfiguration
@Transactional
public class TestBookController {

    @Autowired
    private BookController bookController;
    @Autowired
    private PersonDAO personDAO;

    Model model = new ConcurrentModel();
    HttpSession session = new MockHttpSession();


    @Test
    public void testSearchBooks_withoutQuery_shouldUseDefaultAndReturnLayout() {

        String query = null;
        int page = 1;
        int pageSize = 10;


        String viewName = bookController.searchBooks(query, page, pageSize, model, session);

        assertEquals("_Shared/_Layout", viewName);
        assertTrue(model.containsAttribute("books"));
        assertTrue(model.containsAttribute("currentPage"));
        assertTrue(model.containsAttribute("totalPages"));
        assertTrue(model.containsAttribute("query"));
        assertEquals("java", model.getAttribute("query")); // default değer
    }
    @Test
    public void testSearchBooks_withLoggedInUser_shouldAddCartAndFavItems() throws ParseException {
        Person person = new Person();
        person.setTckn("12345678901"); // Geçerli 11 haneli bir TCKN
        person.setFirstName("Ali");
        person.setLastName("Veli");
        person.setGender("Male");
        person.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));
        person.setEmail("ali@example.com");
        person.setPassword("1234");
        person.setBanned(false);

        // Veritabanına kaydet — burada personService varsa onu kullan
        personDAO.save(person); // Veya: personService.save(person);
        // Session’a kaydedilmiş bir kullanıcı olarak ekle
        session.setAttribute("loggedInUser", person);

        // Act
        String viewName = bookController.searchBooks("spring", 1, 10, model, session);

        // Assert
        assertEquals("_Shared/_Layout", viewName);
        assertTrue(model.containsAttribute("cartItems"));
        assertTrue(model.containsAttribute("favItems"));
        assertEquals("spring", model.getAttribute("query"));
    }
    @Test
    public void testSearchBooksByTitle_GecerliBaslikIle_KitapListesiGoruntusunuDondurur() {
        String title = "Test Kitap";

        String viewName = bookController.searchBooksByTitle(title, model);

        assertEquals("book-list-title", viewName, "Görünüm adı 'book-list-title' olmalı");
        assertTrue(model.containsAttribute("books"), "Model 'books' özniteliğini içermeli");
        assertNotNull(model.getAttribute("books"), "Books özniteliği null olmamalı");
    }
    @Test
    public void testSearchBooksByTitle_BaslikBosIse_BosKitapListesiDondurur() {
        String title = "";

        String viewName = bookController.searchBooksByTitle(title, model);

        assertEquals("book-list-title", viewName, "Görünüm adı 'book-list-title' olmalı");
        assertTrue(model.containsAttribute("books"), "Model 'books' özniteliğini içermeli");
        assertArrayEquals(new BookAPI[0], (BookAPI[]) model.getAttribute("books"), "Books özniteliği boş bir dizi olmalı");
    }
    @Test
    public void testSearchBooksByTitle_BaslikNullIse_BosKitapListesiDondurur() {
        String title = null;

        String viewName = bookController.searchBooksByTitle(title, model);

        assertEquals("book-list-title", viewName, "Görünüm adı 'book-list-title' olmalı");
        assertTrue(model.containsAttribute("books"), "Model 'books' özniteliğini içermeli");
        assertArrayEquals(new BookAPI[0], (BookAPI[]) model.getAttribute("books"), "Books özniteliği boş bir dizi olmalı");
    }


}
