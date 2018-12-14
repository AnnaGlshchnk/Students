package com.anna.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@PropertySource("classpath:sql.properties")
@ComponentScan(basePackages = {"com.anna.dao"})
public class DaoConfig extends WebMvcConfigurerAdapter {

    @Bean
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(getDataSource());
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/students");
        dataSource.setUsername("user");
        dataSource.setPassword("password");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
}
