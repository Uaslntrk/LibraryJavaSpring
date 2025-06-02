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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import tr.edu.duzce.mf.bm.config.AppConfig;
import tr.edu.duzce.mf.bm.config.WebAppInitializer;
import tr.edu.duzce.mf.bm.config.WebConfig;
import tr.edu.duzce.mf.bm.controller.AccountController;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.service.PersonService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebAppInitializer.class, WebConfig.class, AppConfig.class})
@Transactional
@WebAppConfiguration
public class TestAccountController {

    @Autowired
    private AccountController accountController;

    @Autowired
    private PersonService personService;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;
    private Person testPerson;


    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        session = new MockHttpSession();

        testPerson = new Person();
        testPerson.setTckn("12345678901");
        testPerson.setFirstName("Mehmet");
        testPerson.setLastName("Demir");
        testPerson.setEmail("mehmet@example.com");
        testPerson.setPassword("test123");
        testPerson.setGender("Male");

        LocalDate localDate = LocalDate.of(1990, 1, 1);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        testPerson.setBirthday(date);

        // Veritabanına kayıt
        personService.savePerson(testPerson);
    }

    @Test
    public void testLoginSuccess() {
        String viewName = accountController.login(
                "12345678901",
                "test123",
                new RedirectAttributesModelMap(),
                session
        );

        assertEquals("redirect:/", viewName);

        Person sessionUser = (Person) session.getAttribute("loggedInUser");
        assertNotNull(sessionUser);
        assertEquals("Mehmet", sessionUser.getFirstName());
    }

    @Test
    public void testLoginFail() {
        String viewName = accountController.login(
                "12345678901",
                "wrongpassword",
                new RedirectAttributesModelMap(),
                session
        );

        assertEquals("redirect:/login", viewName);
        assertNull(session.getAttribute("loggedInUser"));
    }

    @Test
    public void testLogout() {

        MockHttpSession session = new MockHttpSession();
        Person user = new Person();
        user.setTckn("12345678901");
        session.setAttribute("loggedInUser", user);


        String viewName = accountController.logout(session);


        assertEquals("redirect:/", viewName);

    }

    @Test
    public void testUpdateSuccess(){
        MockHttpSession session = new MockHttpSession();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        BindingResult result = new BeanPropertyBindingResult(new Person(), "person");
        Model model = new ExtendedModelMap();

        String viewName=accountController.updatePerson(
                new Person(), // boş Person (formdan gelen)
                result,
                session,
                redirectAttributes,
                model
        );

        assertEquals("redirect:/login",viewName);

    }
    @Test
    public void testUpdateFail(){
        MockHttpSession session = new MockHttpSession();
        Person loggedInUser = new Person();
        loggedInUser.setTckn("12345678901");
        session.setAttribute("loggedInUser", loggedInUser);

        Person formPerson = new Person();
        formPerson.setTckn("12345678901");
        BindingResult bindingResult = new BeanPropertyBindingResult(formPerson, "person");
        bindingResult.rejectValue("email", "error.email", "Email geçersiz");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        Model model = new ExtendedModelMap();

        // Metodu çağır
        String viewName = accountController.updatePerson(
                formPerson,
                bindingResult,
                session,
                redirectAttributes,
                model
        );

        // Beklenen: doğrulama hatası nedeniyle form sayfasına geri dönülmeli
        assertEquals("HomeViews/UserProfile", viewName);

        // Ayrıca model'de hataların olduğunu kontrol edebilirsiniz
        assertTrue(model.containsAttribute("validationErrors"));
    }

    @Test
    public void testGetRegisterSuccess(){
        Model model = new ExtendedModelMap();

        String viewName = accountController.getRegisterController(model, Locale.ENGLISH);

        assertEquals("AccountViews/Register", viewName);
        assertTrue(model.containsAttribute("person"));
    }

    @Test
    public void testRegisterPersonSuccess() {
        Person person = new Person();
        person.setTckn("98765432109");
        person.setPassword("test123");

        BindingResult bindingResult = new BeanPropertyBindingResult(person, "person");

        String telNumber = "5554443322";
        String rePass = "test123";
        String agreeTerm = "on";

        Model model = new ExtendedModelMap();


        String result = accountController.registerPerson(person, bindingResult, telNumber, rePass, agreeTerm, model);

        assertEquals("redirect:/login?success=true", result);
    }



}







