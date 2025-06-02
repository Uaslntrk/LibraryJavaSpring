package tr.edu.duzce.mf.bm.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tr.edu.duzce.mf.bm.entity.Person;
import tr.edu.duzce.mf.bm.entity.Rental;
import tr.edu.duzce.mf.bm.service.RentalService;

import java.lang.reflect.Method;
import java.util.List;

@Component
public class RentalInterceptor implements HandlerInterceptor {

    @Autowired
    private RentalService rentalService;

    private static final Logger logger = LoggerFactory.getLogger(RentalInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        StringBuilder builder = new StringBuilder();
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("loggedInUser");
        String loggedInUser = builder.append(person.getFirstName()).append(" ").append(person.getLastName()).toString();
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        List<Rental> rentals = rentalService.getAllRentals();
        if(rentals.isEmpty()){
            logger.error(method.getName() + " çağırıldı. Hata: Kiralama kaydı bulunamadı->" + rentals.size() + "URI:" + request.getRequestURI());
        }else{
            logger.info(method.getName() + " çağırıldı. Kiralama kayıtları bulundu: " + rentals.size() + "URI:" + request.getRequestURI());
        }
    }
}
