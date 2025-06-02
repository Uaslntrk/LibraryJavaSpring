import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import tr.edu.duzce.mf.bm.config.AppConfig;
import tr.edu.duzce.mf.bm.config.WebAppInitializer;
import tr.edu.duzce.mf.bm.config.WebConfig;
import tr.edu.duzce.mf.bm.controller.RentalController;
import tr.edu.duzce.mf.bm.dao.PersonDAO;
import tr.edu.duzce.mf.bm.dao.RentalDao;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.entity.Rental;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebAppInitializer.class, WebConfig.class, AppConfig.class})
@WebAppConfiguration
@Transactional
public class TestRentalController {

    @Autowired
    private RentalController rentalController;

    @Autowired
    private RentalDao rentalDao;

    @Autowired
    private PersonDAO personDao;

    private MockHttpSession session;
    private Person loggedInUser;
    private Model model;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        model = new ConcurrentModel();

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
    public void testShowRentalsList_LoggedInUser_ReturnsRentalsListView() {

        session.setAttribute("loggedInUser", loggedInUser);


        Rental rental = new Rental();
        rental.setUser(loggedInUser);
        rental.setTitle("Test Book");
        rental.setAuthor("Test Author");
        rental.setReturned(false);
        rentalDao.saveOrUpdateRental(rental);

        String result = rentalController.showRentalsList(session, model);


        assertEquals("_Shared/_Layout", result, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("rentalRecords"), "Model 'rentalRecords' özniteliğini içermeli");
        assertTrue(model.containsAttribute("matchedRentals"), "Model 'matchedRentals' özniteliğini içermeli");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/Rentals.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
        assertFalse(((List<?>) model.getAttribute("rentalRecords")).isEmpty(), "rentalRecords boş olmamalı");
    }

    @Test
    public void testShowRentalsList_NoLoggedInUser_ReturnsEmptyRentalsList() {

        session.setAttribute("loggedInUser", null);


        String result = rentalController.showRentalsList(session, model);

        assertEquals("_Shared/_Layout", result, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("rentalRecords"), "Model 'rentalRecords' özniteliğini içermeli");
        assertTrue(model.containsAttribute("matchedRentals"), "Model 'matchedRentals' özniteliğini içermeli");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/Rentals.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
        assertTrue(((List<?>) model.getAttribute("rentalRecords")).isEmpty(), "rentalRecords boş olmalı");
        assertTrue(((List<?>) model.getAttribute("matchedRentals")).isEmpty(), "matchedRentals boş olmalı");
    }

    @Test
    public void testShowRentals_LoggedInUser_ReturnsRentalsView() {

        session.setAttribute("loggedInUser", loggedInUser);


        Rental rental = new Rental();
        rental.setUser(loggedInUser);
        rental.setTitle("Test Book");
        rental.setAuthor("Test Author");
        rental.setReturned(false);
        rentalDao.saveOrUpdateRental(rental);

        String result = rentalController.showRentals(session, model);

        assertEquals("_Shared/_Layout", result, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("rentalRecords"), "Model 'rentalRecords' özniteliğini içermeli");
        assertTrue(model.containsAttribute("matchedRentals"), "Model 'matchedRentals' özniteliğini içermeli");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/BookInfo.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
        assertFalse(((List<?>) model.getAttribute("rentalRecords")).isEmpty(), "rentalRecords boş olmamalı");
    }

    @Test
    public void testShowRentals_NoLoggedInUser_ReturnsEmptyRentals() {

        session.setAttribute("loggedInUser", null);


        String result = rentalController.showRentals(session, model);

        assertEquals("_Shared/_Layout", result, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("rentalRecords"), "Model 'rentalRecords' özniteliğini içermeli");
        assertTrue(model.containsAttribute("matchedRentals"), "Model 'matchedRentals' özniteliğini içermeli");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/BookInfo.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
        assertTrue(((List<?>) model.getAttribute("rentalRecords")).isEmpty(), "rentalRecords boş olmalı");
        assertTrue(((List<?>) model.getAttribute("matchedRentals")).isEmpty(), "matchedRentals boş olmalı");
    }

    @Test
    public void testReturnBook_Success_RedirectsToRentalsList() {

        Rental rental = new Rental();
        rental.setUser(loggedInUser);
        rental.setTitle("Test Book");
        rental.setAuthor("Test Author");
        rental.setReturned(false);
        rentalDao.saveOrUpdateRental(rental);
        Long rentalId = rental.getId();

        String result = rentalController.returnBook(rentalId, model);

        Rental updatedRental = rentalDao.findById(rentalId).orElseThrow();
        assertTrue(updatedRental.isReturned(), "Kitap iade edilmiş olmalı");
        assertEquals("redirect:/RentalsList", result, "RentalsList sayfasına yönlendirmeli");
        assertTrue(model.containsAttribute("successMessage"), "Model 'successMessage' özniteliğini içermeli");
        assertEquals("Kitap başarıyla iade edildi.", model.getAttribute("successMessage"), "Başarı mesajı doğru olmalı");
    }


    @Test
    public void testDeleteRental_LoggedInUser_Success_RedirectsToRentals() {

        Rental rental = new Rental();
        rental.setUser(loggedInUser);
        rental.setTitle("Test Book for Delete");
        rental.setAuthor("Author Delete");
        rental.setReturned(false);
        rentalDao.saveOrUpdateRental(rental);
        Long rentalId = rental.getId();
        session.setAttribute("loggedInUser", loggedInUser);

        String result = rentalController.deleteRental(rentalId, session);

        boolean exists = rentalDao.findById(rentalId).isPresent();
        assertFalse(exists, "Kiralama kaydı silinmiş olmalı");
        assertEquals("redirect:/Rentals", result, "Rentals sayfasına yönlendirmeli");
    }

    @Test
    public void testDeleteRental_NoLoggedInUser_RedirectsToLogin() {

        Long rentalId = 1L;
        session.setAttribute("loggedInUser", null);


        String result = rentalController.deleteRental(rentalId, session);


        assertEquals("redirect:/login", result, "Giriş yapmamışsa login sayfasına yönlendirmeli");
    }

    @Test
    public void testShowHistory_LoggedInUser_ReturnsHistoryView() {

        Rental returnedRental = new Rental();
        returnedRental.setUser(loggedInUser);
        returnedRental.setTitle("Returned Book");
        returnedRental.setAuthor("Returned Author");
        returnedRental.setReturned(true);
        rentalDao.saveOrUpdateRental(returnedRental);
        session.setAttribute("loggedInUser", loggedInUser);


        String result = rentalController.showHistory(session, model);

        assertEquals("_Shared/_Layout", result, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("rentalRecords"), "Model 'rentalRecords' özniteliğini içermeli");
        assertTrue(model.containsAttribute("matchedRentals"), "Model 'matchedRentals' özniteliğini içermeli");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/Returned.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
        assertFalse(((List<?>) model.getAttribute("rentalRecords")).isEmpty(), "rentalRecords boş olmamalı");
    }

    @Test
    public void testShowHistory_NoLoggedInUser_ReturnsEmptyHistory() {

        session.setAttribute("loggedInUser", null);


        String result = rentalController.showHistory(session, model);


        assertEquals("_Shared/_Layout", result, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("rentalRecords"), "Model 'rentalRecords' özniteliğini içermeli");
        assertTrue(model.containsAttribute("matchedRentals"), "Model 'matchedRentals' özniteliğini içermeli");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/Returned.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
        assertTrue(((List<?>) model.getAttribute("rentalRecords")).isEmpty(), "rentalRecords boş olmalı");
        assertTrue(((List<?>) model.getAttribute("matchedRentals")).isEmpty(), "matchedRentals boş olmalı");
    }

    @Test
    public void testShowAllRentals_RentalsExist_ReturnsBookInfoView() {

        Rental rental = new Rental();
        rental.setUser(loggedInUser);
        rental.setTitle("Test Book");
        rental.setAuthor("Test Author");
        rental.setReturned(false);
        rentalDao.saveOrUpdateRental(rental);

        String result = rentalController.showAllRentals(model);

        assertEquals("BookInfo", result, "Görünüm adı 'BookInfo' olmalı");
        assertTrue(model.containsAttribute("rentals"), "Model 'rentals' özniteliğini içermeli");
        assertTrue(model.containsAttribute("success"), "Model 'success' özniteliğini içermeli");
        assertEquals("Kiralama kayıtları başarıyla yüklendi.", model.getAttribute("success"), "Başarı mesajı doğru olmalı");
        assertFalse(((List<?>) model.getAttribute("rentals")).isEmpty(), "rentals boş olmamalı");
    }


}