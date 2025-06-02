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
import java.lang.reflect.Method;

@Component
public class BookInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BookInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HttpSession session = request.getSession();
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        String query = request.getParameter("q");

        if(query != null) {
            logger.info(method.getName() +" çağırıldı. Kitap aranıyor. Sorgu: " + query + "... URI: " + request.getRequestURI());
        }else{
            logger.info(method.getName() + " çağırıldı. Kitaplar listelendi. URI: " + request.getRequestURI());
        }

    }
}
