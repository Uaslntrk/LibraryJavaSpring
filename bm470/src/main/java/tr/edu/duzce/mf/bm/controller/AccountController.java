package tr.edu.duzce.mf.bm.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.service.PersonService;

import java.util.Locale;

@Controller
@RequestMapping(value = "/*")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/login")
    public String getAccountController(Model model, Locale locale) {

        return "AccountViews/Login";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("tckn") String tckn,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {

        Person person = personService.login(tckn, password);

        if (person != null) {
            session.setAttribute("loggedInUser", person);

            Person sessionUser = (Person) session.getAttribute("loggedInUser");
            StringBuilder builder = new StringBuilder();
            String user = String.valueOf(builder.append(sessionUser.getFirstName()).append(" ").append(sessionUser.getLastName()));

            String successMessage = messageSource.getMessage("login.success", null, LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("success", successMessage);

            return "redirect:/";
        } else {
            String errorMessage = messageSource.getMessage("login.error", null, LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updatePerson(
            @Valid @ModelAttribute("person") Person person,
            BindingResult result,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {
        Person loggedInUser = (Person) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            redirectAttributes.addFlashAttribute("error", "Oturum süresi doldu. Lütfen tekrar giriş yapın.");
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            logger.warn("updatePerson() çağırıldı. Doğrulama hataları: TCKN={}, Hatalar={}", person.getTckn(), result.getAllErrors());
            model.addAttribute("validationErrors", result.getAllErrors());
            model.addAttribute("user", person); // Formdaki verileri korumak için
            return "HomeViews/UserProfile";
        }

        try {
            Person existing = personService.getPerson(loggedInUser.getTckn());
            if (existing == null) {
                redirectAttributes.addFlashAttribute("error", "Kullanıcı bulunamadı.");
                return "redirect:/profile";
            }

            existing.setFirstName(person.getFirstName());
            existing.setLastName(person.getLastName());
            existing.setEmail(person.getEmail());
            existing.setPassword(person.getPassword());
            existing.setPhoneNumber(person.getPhoneNumber());
            existing.setBirthday(existing.getBirthday()); //
            existing.setGender(person.getGender());

            personService.savePerson(existing);

            // Session'daki kullanıcıyı güncelle
            session.setAttribute("loggedInUser", existing);

            redirectAttributes.addFlashAttribute("success", "Bilgiler başarıyla güncellendi.");
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Bir hata oluştu: " + e.getMessage());
            return "redirect:/profile";
        }
    }

    @GetMapping(value = "/register")
    public String getRegisterController(Model model, Locale locale) {
        model.addAttribute("person", new Person());
        return "AccountViews/Register";
    }

    @PostMapping("/register")
    public String registerPerson(
            @Valid @ModelAttribute("person") Person person,
            BindingResult result,
            @RequestParam("telnumber") String telnumber,
            @RequestParam("re_pass") String rePass,
            @RequestParam(value = "agree-term", required = false) String agreeTerm,
            Model model){

        if (result.hasErrors()) {
            logger.warn("registerPerson() çağırıldı. Doğrulama hatası: TCKN={}, Hatalar={}", person.getTckn(), result.getAllErrors());
            model.addAttribute("validationErrors", result.getAllErrors());
            return "AccountViews/Register";
        }

        if (agreeTerm == null || !agreeTerm.equals("on")) {
            logger.warn("registerPerson() çağırıldı. Sözleşme ret hatası: TCKN={}", person.getTckn());
            model.addAttribute("agreeTermError", messageSource.getMessage("register.agree.term.error", null, LocaleContextHolder.getLocale()));
            return "AccountViews/Register";
        }

        if (!person.getPassword().equals(rePass)) {
            logger.warn("registerPerson() çağırıldı. Şifre eşleşme hatası.: TCKN={}", person.getTckn());
            model.addAttribute("passwordError", messageSource.getMessage("register.password.mismatch", null, LocaleContextHolder.getLocale()));
            return "AccountViews/Register";
        }
        person.setPhoneNumber(telnumber);
        try {
            personService.savePerson(person);
            return "redirect:/login?success=true";
        }
        catch (Exception e) {
            return "AccountViews/Register";
        }
    }
}

