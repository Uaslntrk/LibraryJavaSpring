package tr.edu.duzce.mf.bm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import tr.edu.duzce.mf.bm.entity.*;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:hibernate.properties", encoding = "UTF-8")
@EnableTransactionManagement
@ComponentScan(basePackages = "tr.edu.duzce.mf.bm")

public class AppConfig implements AvailableSettings {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(environment.getProperty("mysql.driver"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Veritabanı sürücüsü ayarlanamadı: " + e.getMessage());
        }
        dataSource.setJdbcUrl(environment.getProperty("mysql.url"));
        dataSource.setUser(environment.getProperty("mysql.user"));
        dataSource.setPassword(environment.getProperty("mysql.password"));
        dataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("hibernate.c3p0.min_size")));
        dataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("hibernate.c3p0.max_size")));
        dataSource.setAcquireIncrement(Integer.parseInt(environment.getProperty("hibernate.c3p0.acquire_increment")));
        dataSource.setMaxIdleTime(Integer.parseInt(environment.getProperty("hibernate.c3p0.timeout")));

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        //sessionFactoryBean.setPackagesToScan("tr.edu.duzce.mf.bm.entity");
        sessionFactoryBean.setAnnotatedClasses(Person.class, CartItem.class, Favorite.class, Rental.class, Admin.class);


        Properties hibernateProperties = new Properties();
        hibernateProperties.put(DIALECT, environment.getProperty("hibernate.dialect"));
        hibernateProperties.put(SHOW_SQL, environment.getProperty("hibernate.show_sql"));
        hibernateProperties.put(HBM2DDL_AUTO, environment.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.put(DEFAULT_SCHEMA, environment.getProperty("hibernate.default_schema", ""));
        hibernateProperties.put(C3P0_MIN_SIZE, environment.getProperty("hibernate.c3p0.min_size"));
        hibernateProperties.put(C3P0_MAX_SIZE, environment.getProperty("hibernate.c3p0.max_size"));
        hibernateProperties.put(C3P0_ACQUIRE_INCREMENT, environment.getProperty("hibernate.c3p0.acquire_increment"));
        hibernateProperties.put(C3P0_TIMEOUT, environment.getProperty("hibernate.c3p0.timeout"));
        hibernateProperties.put(C3P0_MAX_STATEMENTS, environment.getProperty("hibernate.c3p0.max_statements"));

        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public RestTemplate restTemplate() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        return new RestTemplate(converters);
    }
}