package tr.edu.duzce.mf.bm.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.edu.duzce.mf.bm.entity.Admin;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.entity.Rental;
import tr.edu.duzce.mf.bm.service.AdminService;
import tr.edu.duzce.mf.bm.service.PersonService;
import tr.edu.duzce.mf.bm.service.RentalService;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    MessageSource messageSource;
    @Autowired
    private AdminService adminService;
    @Autowired
    private PersonService personService;
    @Autowired
    private RentalService rentalService;

    @GetMapping(value = "/LibrarianProfile")
    public String getLibrarianProfileController(Model model, Locale locale){
        model.addAttribute("body", "/WEB-INF/view/AdminPanelViews/LibrarianProfile.jsp");
        return "_Shared/_adminLayout";}

    @GetMapping(value = "/LibrarianProfile-edit")
    public String getEditLibrarianProfileController(Model model){
        model.addAttribute("body", "/WEB-INF/view/AdminPanelViews/Edit/Edit_LibrarianProfile.jsp");
        return "_Shared/_adminLayout";}

    @GetMapping(value = "/BookInfo")
    public String getBookInfoController(Model model, Locale locale, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/admin/adminlogin";
        }
        List<Rental> rentals = rentalService.getAllRentals();
        model.addAttribute("rentals", rentals);
        model.addAttribute("body", "/WEB-INF/view/AdminPanelViews/BookInfo.jsp");
        logger.info("getBookInfoController() çağırıldı. Kiralama kayıtları yüklendi, kayıt sayısı: {}", rentals.size());
        return "_Shared/_adminLayout";
    }

    @GetMapping(value = "/PersonInfo")
    public String getPersonInfoController(Model model, Locale locale, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/admin/adminlogin";
        }
        List<Person> personList = personService.getAllPersons();
        model.addAttribute("persons", personList);
        model.addAttribute("body", "/WEB-INF/view/AdminPanelViews/PersonInfo.jsp");
        return "_Shared/_adminLayout";
    }

    @GetMapping(value = "/BookDetail")
    public String getBookDetailController(Model model){
        model.addAttribute("body", "/WEB-INF/view/AdminPanelViews/BookDetail.jsp");
        return "_Shared/_adminLayout";}

    @GetMapping(value = "/LibrarianUser")
    public String getLibUserController(Model model, Locale locale, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/admin/adminlogin";
        }
        List<Person> users = personService.getAllPersons();
        model.addAttribute("users", users);
        model.addAttribute("body", "/WEB-INF/view/AdminPanelViews/LibrarianUser.jsp");
        return "_Shared/_adminLayout";
    }

    @PostMapping(value = "/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestParam("tckn") String tckn, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (tckn == null || tckn.trim().isEmpty()) {
            logger.warn("deleteUser() çağrıldı. Geçersiz TCKN: null veya boş");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            personService.deleteUser(tckn);
            logger.info("deleteUser() çağrıldı. Üye silindi: tckn={}", tckn);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("deleteUser() çağrıldı. Geçersiz TCKN: tckn={}, hata={}", tckn, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            logger.error("deleteUser() çağrıldı. Üye silinirken hata: tckn={}, hata={}", tckn, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("deleteUser() çağrıldı. Beklenmeyen hata: tckn={}, hata={}", tckn, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/blockUser")
    public String blockUser(@RequestParam("tckn") String tckn,
                            HttpSession session,
                            RedirectAttributes redirectAttributes,
                            @RequestHeader(value = "Referer", required = false) String referer) {

        if (session.getAttribute("loggedInUser") == null) {
            redirectAttributes.addFlashAttribute("error", "Oturum bulunamadı.");
            return "redirect:/login";
        }

        try {
            personService.banUser(tckn);
            logger.info("blockUser() çağrıldı. Üye başarıyla engellendi: tckn={}", tckn);
            redirectAttributes.addFlashAttribute("success", "Kullanıcı başarıyla engellendi.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Hata oluştu: " + e.getMessage());
        }

        // Kullanıcı geldiği sayfaya geri dönsün
        return "redirect:" + (referer != null ? referer : "/");
    }

    @GetMapping(value = "/adminlogin")
    public String getAccountController(Model model, Locale locale) {
        model.addAttribute("body", "/WEB-INF/view/AdminPanelViews/AdminLogin.jsp");
        return "AdminPanelViews/AdminLogin";
    }

    @PostMapping(value = "/adminlogin")
    public String login(@RequestParam("tckn") String tckn,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {

        Admin admin = adminService.login(tckn, password);

        if (admin != null) {
            session.setAttribute("loggedInUser", admin);
            Admin sessionUser = (Admin) session.getAttribute("loggedInUser");
            String user = "Admin";
            String successMessage = messageSource.getMessage("login.success", null, LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/admin/BookInfo";

        } else {
            String errorMessage = messageSource.getMessage("login.error", null, LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/admin/adminlogin";
        }
    }

    @GetMapping(value = "/adminHome")
    public String getAdminHomeController(Model model, Locale locale, HttpSession session) {
        model.addAttribute("body", "/WEB-INF/view/AdminPanelViews/BookInfo.jsp");
        return "_Shared/_adminLayout";
    }

}