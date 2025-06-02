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
import tr.edu.duzce.mf.bm.entity.BookAPI;
import tr.edu.duzce.mf.bm.service.BookAPIService;
import tr.edu.duzce.mf.bm.wrapper.BookSearchResponse;

import java.lang.reflect.Method;

@Component
public class BookDetailInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BookDetailInterceptor.class);

    BookSearchResponse bookSearchResponse;

    @Autowired
    BookAPIService bookAPIService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        String title = request.getParameter("title");
        String author = request.getParameter("author");

        bookSearchResponse = bookAPIService.searchBooksByTitle(title);
        if (bookSearchResponse != null && bookSearchResponse.getBooks().length > 0) {
            logger.info(method.getName() + " çağırıldı. Kitap başlığıyla arama sonucu geldi: " + title + " | URI: " + request.getRequestURI());

            BookAPI matchedBook = null;
            for (BookAPI book : bookSearchResponse.getBooks()) {
                if (book.getAuthors() != null) {
                    for (String a : book.getAuthors()) {
                        if (a.equalsIgnoreCase(author.trim())) {
                            matchedBook = book;
                            break;
                        }
                    }
                }
                if (matchedBook != null) break;
            }

            if (matchedBook != null) {
                logger.info(method.getName() + " çağırıldı. Kitap ve yazar eşleşti: " + matchedBook.getTitle() + " - " + author);
            } else {
                logger.warn(method.getName() + " çağırıldı. Kitap bulundu ama yazar eşleşmedi. Yazar: " + author);
            }

        } else {
            logger.error(method.getName() + " çağırıldı. Kitap bulunamadı. Başlık: " + title + " | URI: " + request.getRequestURI());
        }
    }
}
