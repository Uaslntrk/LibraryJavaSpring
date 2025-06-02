package tr.edu.duzce.mf.bm.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tr.edu.duzce.mf.bm.entity.Person;

import java.util.Locale;

@Controller
@RequestMapping(value = "/*")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private MessageSource messageSource;


    @GetMapping(value = "/")
    public String getHomeController(Model model, Locale locale){
        logger.info("getHomeController() çağırıldı. Sayfa yönlendirmesi: HomePage.jsp");
        model.addAttribute("body", "/WEB-INF/view/HomeViews/HomePage.jsp");
        return "_Shared/_Layout";
    }

    @GetMapping(value = "/contact")
    public String getContactController(Model model) {
        model.addAttribute("body", "/WEB-INF/view/HomeViews/Contact.jsp");
        return "_Shared/_Layout";
    }

    @GetMapping(value = "/BookList")
    public String getBookListController(Model model){
        model.addAttribute("body", "/WEB-INF/view/DetailBook.jsp");
        return "_Shared/_Layout";
    }
    @GetMapping(value = "/mission")
    public String getMissionController(Model model, Locale locale){
        model.addAttribute("body", "/WEB-INF/view/HomeViews/Mission.jsp");
        return "_Shared/_Layout";
    }
    @GetMapping(value = "/vision")
    public String getVisionController(Model model, Locale locale){
        model.addAttribute("body", "/WEB-INF/view/HomeViews/Vision.jsp");
        return "_Shared/_Layout";
    }
    @GetMapping(value = "/history")
    public String getHistoryController(Model model, Locale locale) {
        model.addAttribute("body", "/WEB-INF/view/HomeViews/History.jsp");
        return "_Shared/_Layout";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model ) {
        Person user = (Person) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("body","/WEB-INF/view/HomeViews/UserProfile.jsp");
        return "_Shared/_Layout";
    }
}
