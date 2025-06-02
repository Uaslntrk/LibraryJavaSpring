package tr.edu.duzce.mf.bm.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
public class LogoutInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogoutInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        logger.info(method.getName() + " çağırıldı. Oturum kapatıldı. URI: " + request.getRequestURI());
        return true;
    }
}
