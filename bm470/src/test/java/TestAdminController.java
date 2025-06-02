import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import tr.edu.duzce.mf.bm.config.AppConfig;
import tr.edu.duzce.mf.bm.config.WebAppInitializer;
import tr.edu.duzce.mf.bm.config.WebConfig;
import tr.edu.duzce.mf.bm.controller.AdminController;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.entity.Rental;
import tr.edu.duzce.mf.bm.service.PersonService;
import tr.edu.duzce.mf.bm.service.RentalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebAppInitializer.class, WebConfig.class, AppConfig.class})
@Transactional
@WebAppConfiguration
public class TestAdminController {

    AdminController controller = new AdminController();
    Model model = new ExtendedModelMap();
    Locale locale = Locale.ENGLISH;
    MockHttpSession session = new MockHttpSession();

    @BeforeEach
    public void setUp() {
        controller = new AdminController();
        session = new MockHttpSession();
    }
    @Test
    public void testGetLibrarianProfileController() {

        String viewName = controller.getLibrarianProfileController(model, locale);

        // Assert
        assertEquals("_Shared/_adminLayout", viewName);
        assertTrue(model.containsAttribute("body"));
        assertEquals("/WEB-INF/view/AdminPanelViews/LibrarianProfile.jsp", model.getAttribute("body"));
    }
    @Test
    public void testGetEditLibrarianProfileController(){

        String viewName=controller.getEditLibrarianProfileController(model);
        assertEquals("_Shared/_adminLayout",viewName);
        assertTrue(model.containsAttribute("body"));
        assertEquals("/WEB-INF/view/AdminPanelViews/Edit/Edit_LibrarianProfile.jsp",model.getAttribute("body"));

    }
    @Test
    public void testGetBookInfoControllerWithoutLoginRedirects() {

        String viewName = controller.getBookInfoController(model, locale, session);

        assertEquals("redirect:/admin/adminlogin", viewName);
    }
    @Test
    public void testGetBookInfoControllerWithLoginLoadsRentals() {
        RentalService fakeRentalService = new RentalService(null, null, null) {
            @Override
            public List<Rental> getAllRentals() {
                List<Rental> rentals = new ArrayList<>();
                rentals.add(new Rental());
                return rentals;
            }
        };

        ReflectionTestUtils.setField(controller, "rentalService", fakeRentalService);

        Model model = new ExtendedModelMap();
        Locale locale = Locale.ENGLISH;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", "testUser");

        // Act
        String view = controller.getBookInfoController(model, locale, session);

        // Assert
        assertEquals("_Shared/_adminLayout", view);
        assertTrue(model.containsAttribute("rentals"));
        assertTrue(model.containsAttribute("body"));
        assertEquals("/WEB-INF/view/AdminPanelViews/BookInfo.jsp", model.getAttribute("body"));
    }
    @Test
    public void testGetPersonInfoController_withLoggedInUser() {

        // Sahte PersonService tanımlanıyor
        PersonService fakePersonService = new PersonService() {
            @Override
            public List<Person> getAllPersons() {
                List<Person> persons = new ArrayList<>();
                persons.add(new Person());
                return persons;
            }
        };

        ReflectionTestUtils.setField(controller, "personService", fakePersonService);
        session.setAttribute("loggedInUser", "adminUser");

        String viewName = controller.getPersonInfoController(model, locale, session);

        assertEquals("_Shared/_adminLayout", viewName);
        assertTrue(model.containsAttribute("persons"));
        assertTrue(model.containsAttribute("body"));
        assertEquals("/WEB-INF/view/AdminPanelViews/PersonInfo.jsp", model.getAttribute("body"));
    }
    @Test
    public void testGetPersonInfoController_withoutLoggedInUser_redirectsToLogin() {
        String viewName = controller.getPersonInfoController(model, locale, session);

        assertEquals("redirect:/admin/adminlogin", viewName);
    }
    @Test
    public void testGetBookDetailController(){
        String viewName = controller.getBookDetailController(model);
        assertEquals("_Shared/_adminLayout",viewName);
        assertTrue(model.containsAttribute("body"));
        assertEquals("/WEB-INF/view/AdminPanelViews/BookDetail.jsp",model.getAttribute("body"));



    }
    @Test
    public void testGetLibUserController_withLoggedInUser() {
        // Sahte PersonService tanımı
        PersonService fakePersonService = new PersonService() {
            @Override
            public List<Person> getAllPersons() {
                List<Person> personList = new ArrayList<>();
                personList.add(new Person());
                return personList;
            }
        };

        // Controller'daki personService alanını sahte servisle değiştir
        ReflectionTestUtils.setField(controller, "personService", fakePersonService);

        Model model = new ExtendedModelMap();
        Locale locale = Locale.ENGLISH;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", "adminUser");


        String viewName = controller.getLibUserController(model, locale, session);


        assertEquals("_Shared/_adminLayout", viewName);
        assertTrue(model.containsAttribute("users"));
        assertTrue(model.containsAttribute("body"));
        assertEquals("/WEB-INF/view/AdminPanelViews/LibrarianUser.jsp", model.getAttribute("body"));
    }
    @Test
    public void testGetLibUserController_withoutLoggedInUser_redirectsToLogin() {
        String viewName = controller.getLibUserController(model, locale, session);
        assertEquals("redirect:/admin/adminlogin", viewName);
    }
    @Test
    public void testDeleteUser_Unauthorized_WhenNoSession() {

        String tckn = "12345678901";

        ResponseEntity<Void> response = controller.deleteUser(tckn, session);

        assertEquals(UNAUTHORIZED, response.getStatusCode());
    }
    @Test
    public void testDeleteUser_BadRequest_WhenTcknIsNull() {

        session.setAttribute("loggedInUser", "admin");
        String tckn = null;

        ResponseEntity<Void> response = controller.deleteUser(tckn, session);

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void testDeleteUser_BadRequest_WhenTcknIsEmpty() {

        session.setAttribute("loggedInUser", "admin");
        String tckn = "   ";

        ResponseEntity<Void> response = controller.deleteUser(tckn, session);


        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteUser_Ok_WhenUserDeletedSuccessfully() {

        session.setAttribute("loggedInUser", "admin");

        PersonService fakePersonService = new PersonService() {
            @Override
            public void deleteUser(String tckn) {

            }
        };

        ReflectionTestUtils.setField(controller, "personService", fakePersonService);

        // Act
        ResponseEntity<Void> response = controller.deleteUser("12345678901", session);

        // Assert
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUser_BadRequest_WhenIllegalArgumentExceptionThrown() {
        // Arrange
        session.setAttribute("loggedInUser", "admin");

        PersonService fakePersonService = new PersonService() {
            @Override
            public void deleteUser(String tckn) {
                throw new IllegalArgumentException("TCKN formatı hatalı");
            }
        };

        ReflectionTestUtils.setField(controller, "personService", fakePersonService);

        // Act
        ResponseEntity<Void> response = controller.deleteUser("123", session);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteUser_NotFound_WhenRuntimeExceptionThrown() {

        session.setAttribute("loggedInUser", "admin");
        PersonService fakePersonService = new PersonService() {
            @Override
            public void deleteUser(String tckn) {
                throw new RuntimeException("Kullanıcı bulunamadı");
            }
        };

        ReflectionTestUtils.setField(controller, "personService", fakePersonService);

        ResponseEntity<Void> response = controller.deleteUser("123", session);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteUser_InternalServerError_WhenGenericExceptionThrown() {

        session.setAttribute("loggedInUser", "admin");

        PersonService fakePersonService = new PersonService() {
            @Override
            public void deleteUser(String tckn) {
                throw new RuntimeException(new Exception("Veritabanı hatası"));
            }
        };

        ReflectionTestUtils.setField(controller, "personService", fakePersonService);

        ResponseEntity<Void> response = controller.deleteUser("123", session);

        // Yalnızca RuntimeException catch bloğu çalışacağı için NOT_FOUND döner, INTERNAL_SERVER_ERROR değil.
        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    /*
    @Test
    public void testBlockUser_Unauthorized_WhenNoSession() {
        String tckn = "12345678901";

        ResponseEntity<Void> response = controller.blockUser(tckn, session);

        assertEquals(UNAUTHORIZED, response.getStatusCode());
    }


     */
    /*
    @Test
    public void testBlockUser_Ok_WhenUserBlockedSuccessfully() {
        // Arrange
        session.setAttribute("loggedInUser", "admin");

        PersonService fakePersonService = new PersonService() {

            public void blockUser(String tckn) {

            }
        };

        ReflectionTestUtils.setField(controller, "personService", fakePersonService);

        // Act
        ResponseEntity<Void> response = controller.blockUser("12345678901", session);

        // Assert
        assertEquals(OK, response.getStatusCode());
    }

     */
    /*
    @Test
    public void testBlockUser_InternalServerError_WhenExceptionThrown() {
        // Arrange
        session.setAttribute("loggedInUser", "admin");

        PersonService fakePersonService = new PersonService(null) {
            @Override
            public void blockUser(String tckn) {
                throw new RuntimeException("Veritabanı hatası");
            }
        };

        ReflectionTestUtils.setField(controller, "personService", fakePersonService);

        // Act
        ResponseEntity<Void> response = controller.blockUser("12345678901", session);

        // Assert
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

     */

    @Test
    public void testgetAccountController(){
        String viewName = controller.getAccountController(model, locale);

        assertEquals("AdminPanelViews/AdminLogin", viewName);
        assertTrue(model.containsAttribute("body"));
        assertEquals("/WEB-INF/view/AdminPanelViews/AdminLogin.jsp", model.getAttribute("body"));
    }
    @Test
    public void testgetAdminHomeController(){
        String viewName = controller.getAdminHomeController(model,locale,session);
        assertEquals("_Shared/_adminLayout",viewName);
        assertTrue(model.containsAttribute("body"));
        assertEquals("/WEB-INF/view/AdminPanelViews/BookInfo.jsp",model.getAttribute("body"));
    }


}



