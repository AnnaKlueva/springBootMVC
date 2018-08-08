package anna.klueva.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages={"anna.klueva"},
        excludeFilters={
                @ComponentScan.Filter(type= FilterType.ANNOTATION, value=EnableWebMvc.class)
        })
@EnableTransactionManagement
@EnableJpaRepositories("anna.klueva")
public class RootConfig {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource, @Autowired @Qualifier("propertiesContainer") Properties propertiesContainer) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan("anna.klueva");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(propertiesContainer);
        em.setDataSource(dataSource);
        return em;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(databaseProperties.getUrl());
        dataSource.setDriverClassName(databaseProperties.getDBDriver());
        dataSource.setUsername(databaseProperties.getUsername());
        dataSource.setPassword(databaseProperties.getPassword());
        return dataSource;
    }

    @Bean
    @Qualifier("propertiesContainer")
    public Properties additionalProperties() throws IOException {
        return PropertiesLoaderUtils.loadProperties(new ClassPathResource(databaseProperties.getAdditionalPropertiesFilename()));
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /*@Configuration
    @Profile("dev")
    @PropertySource("classpath:inmemDb.properties")
    static class InmemoryProperties
    {}

    @Configuration
    @Profile("test")
    @PropertySource("classpath:embeddedDb.properties")
    static class EmbeddedProperties
    {}

    @Configuration
    @Profile("production")
    @PropertySource("classpath:remotePostgresDb.properties")
    static class RemoteProperties
    {}*/
}
