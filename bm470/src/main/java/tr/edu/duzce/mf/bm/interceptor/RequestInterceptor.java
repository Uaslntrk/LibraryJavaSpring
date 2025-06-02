package tr.edu.duzce.mf.bm.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tr.edu.duzce.mf.bm.entity.Person;
import java.lang.reflect.Method;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        logger.info(method.getName() + " Login'e yönlendirildi. Kullanıcı: " + user+ "URI:" + request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        if (user != null && user instanceof Person person) {
            logger.info(method.getName() + " çağırıldı. Oturum oluşturuldu. Kullanıcı: " + person.getTckn() + " URI: " + request.getRequestURI());
        }else{
            logger.error(method.getName() + " çağırıldı. Oturum oluşturulamadı! Kullanıcı: " + user + " URI: " + request.getRequestURI());
        }
    }

}
