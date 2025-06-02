package tr.edu.duzce.mf.bm.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tr.edu.duzce.mf.bm.entity.Person;

import java.lang.reflect.Method;

public class ForwardInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ForwardInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        StringBuilder builder = new StringBuilder();
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("loggedInUser");
        String loggedInUser = builder.append(person.getFirstName()).append(" ").append(person.getLastName()).toString();
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        if(person != null){
            logger.info(method.getName() + " çağırıldı. Kullanıcı: " + loggedInUser + " yönlendiriliyor. URI: " + request.getRequestURI());

        }else{
            logger.debug(method.getName() + " çağırıldı. Kullanıcı bulunumadı. URI: " + request.getRequestURI());
        }
    }
}
