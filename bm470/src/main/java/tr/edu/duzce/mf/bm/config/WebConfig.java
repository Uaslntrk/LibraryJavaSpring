package tr.edu.duzce.mf.bm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import tr.edu.duzce.mf.bm.interceptor.*;

import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "tr.edu.duzce.mf.bm")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setContentType("text/html;charset=UTF-8");
        return viewResolver;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

    }
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setFallbackToSystemLocale(true);
        return messageSource;
    }

    @Bean
    public CookieLocaleResolver CookieResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.forLanguageTag("tr-TR"));
        localeResolver.setCookieName("bm470-locale-cookie");
        localeResolver.setCookieMaxAge(3600); // saniye
        return localeResolver;
    }

    @Bean
    public SessionLocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("tr", "TR"));
        localeResolver.setDefaultLocale(Locale.forLanguageTag("tr"));
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Autowired
    RentalInterceptor rentalInterceptor;
    @Autowired
    BookDetailInterceptor bookDetailInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor()).addPathPatterns("/*");
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns(new String[] {"/login", "/admin/adminLogin"});
        registry.addInterceptor(new LogoutInterceptor()).addPathPatterns("/logout");
        registry.addInterceptor(new ForwardInterceptor()).
                addPathPatterns(new String[] { "/contact", "/mission", "/vision", "/history",
                        "/BookList", "/profile", "/update", "register", "/RentalsList","/Rentals",
                        "/returned","/deleteRental","/Cart"});
        registry.addInterceptor(rentalInterceptor).addPathPatterns("/allRentals", "/returned", "/RentalsList");
        registry.addInterceptor(new BookInterceptor()).addPathPatterns("/Books","/searchTitle");
        registry.addInterceptor(bookDetailInterceptor).addPathPatterns("/book-detail");
    }


}
