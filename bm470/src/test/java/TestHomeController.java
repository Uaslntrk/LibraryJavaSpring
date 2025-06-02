import jakarta.servlet.http.HttpSession;
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
import tr.edu.duzce.mf.bm.controller.HomeController;
import tr.edu.duzce.mf.bm.dao.PersonDAO;
import tr.edu.duzce.mf.bm.entity.Person;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebAppInitializer.class, WebConfig.class, AppConfig.class})
@WebAppConfiguration
@Transactional
public class TestHomeController {

    @Autowired
    private HomeController homeController;

    @Autowired
    private PersonDAO personDAO;

    private Model model;
    private HttpSession session;
    private Locale locale;

    @BeforeEach
    void setUp() {
        model = new ConcurrentModel();
        session = new MockHttpSession();
        locale = new Locale("tr", "TR");
    }

    @Test
    public void testGetHomeController_ReturnsHomeView() {

        String viewName = homeController.getHomeController(model, locale);


        assertEquals("_Shared/_Layout", viewName, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/HomePage.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
    }



    @Test
    public void testGetContactController_ReturnsContactView() {

        String viewName = homeController.getContactController(model);

        assertEquals("_Shared/_Layout", viewName, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/Contact.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
    }



    @Test
    public void testGetBookListController_ReturnsBookListView() {

        String viewName = homeController.getBookListController(model);

        assertEquals("_Shared/_Layout", viewName, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/DetailBook.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
    }

    @Test
    public void testGetMissionController_ReturnsMissionView() {

        String viewName = homeController.getMissionController(model, locale);

        assertEquals("_Shared/_Layout", viewName, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/Mission.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
    }

    @Test
    public void testGetVisionController_ReturnsVisionView() {

        String viewName = homeController.getVisionController(model, locale);


        assertEquals("_Shared/_Layout", viewName, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/Vision.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
    }

    @Test
    public void testGetHistoryController_ReturnsHistoryView() {

        String viewName = homeController.getHistoryController(model, locale);

        assertEquals("_Shared/_Layout", viewName, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/History.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
    }

    @Test
    public void testShowProfile_LoggedInUser_ReturnsProfileView() {

        Person user = new Person();
        user.setTckn("12345678901");
        user.setFirstName("Ali");
        user.setLastName("Yılmaz");
        LocalDate localDate = LocalDate.of(1990, 1, 1);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        user.setBirthday(date);
        user.setPassword("password123");
        user.setEmail("ali.yilmaz@example.com");
        user.setGender("Male");
        user.setBanned(false);
        personDAO.saveOrUpdatePerson(user);
        session.setAttribute("loggedInUser", user);

        String viewName = homeController.showProfile(session, model);


        assertEquals("_Shared/_Layout", viewName, "Görünüm adı '_Shared/_Layout' olmalı");
        assertTrue(model.containsAttribute("user"), "Model 'user' özniteliğini içermeli");
        assertTrue(model.containsAttribute("body"), "Model 'body' özniteliğini içermeli");
        assertEquals("/WEB-INF/view/HomeViews/UserProfile.jsp", model.getAttribute("body"), "Body özniteliği doğru JSP dosyası olmalı");
        assertEquals(user, model.getAttribute("user"), "Modeldeki user, oturumdaki kullanıcıyla eşleşmeli");
    }

    @Test
    public void testShowProfile_NoLoggedInUser_RedirectsToLogin() {

        session.setAttribute("loggedInUser", null);


        String viewName = homeController.showProfile(session, model);

        assertEquals("redirect:/login", viewName, "Giriş yapmamışsa login sayfasına yönlendirmeli");
        assertFalse(model.containsAttribute("user"), "Model 'user' özniteliğini içermemeli");
        assertFalse(model.containsAttribute("body"), "Model 'body' özniteliğini içermemeli");
    }
}