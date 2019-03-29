package com.anna.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource(value = "classpath:conf.environment/${database.sql}/sql.properties")
public class DaoConfig {

  @Value("${user.driverClassName}")
  private String driverClassName;
  @Value("${user.url}")
  private String url;
  @Value("${username.name}")
  private String name;
  @Value("${user.password}")
  private String password;
  @Value("${db.sql}")
  private String db;
  @Value("${text.sql}")
  private String text;

  /**
   * Bean for configuration MYSQL properties.
   */
  @Profile("MYSQL")
  @Bean
  public static PropertySourcesPlaceholderConfigurer app() {
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
        new PropertySourcesPlaceholderConfigurer();
    propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource(
        "conf.environment/MYSQL/app.properties"));
    return propertySourcesPlaceholderConfigurer;
  }

  /**
   * Bean for configuration POSTGRESQL properties.
   */
  @Profile("POSTGRESQL")
  @Bean
  public static PropertySourcesPlaceholderConfigurer appPostgres() {
    PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
        new PropertySourcesPlaceholderConfigurer();
    propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource(
        "conf.environment/POSTGRESQL/app.properties"));
    return propertySourcesPlaceholderConfigurer;
  }

  /**
   * Bean for configuration docker properties.
   */
  @Profile("docker")
  @Bean
  public static PropertyPlaceholderConfigurer appDocker() {
    PropertyPlaceholderConfigurer ppc
        = new PropertyPlaceholderConfigurer();
    Resource[] resources = new ClassPathResource[]
        {new ClassPathResource("conf.environment/app-docker.properties")};
    ppc.setLocations(resources);
    ppc.setIgnoreUnresolvablePlaceholders(true);
    return ppc;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  /**
   * Bean for configuration dataSource.
   */
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(name);
    dataSource.setPassword(password);
    Resource initSchema = new ClassPathResource(db);
    Resource textSchema = new ClassPathResource(text);
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
    databasePopulator.addScripts(initSchema, textSchema);
    DatabasePopulatorUtils.execute(databasePopulator, dataSource);

    return dataSource;
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
    return new NamedParameterJdbcTemplate(dataSource());
  }

  @Bean
  public PlatformTransactionManager txManager() {
    return new DataSourceTransactionManager(dataSource());
  }
}