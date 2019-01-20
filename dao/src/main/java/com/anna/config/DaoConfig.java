package com.anna.config;

import com.anna.dao.GroupsDao;
import com.anna.dao.GroupsDaoImpl;
import com.anna.dao.StudentsDao;
import com.anna.dao.StudentsDaoImpl;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySources({
        @PropertySource("classpath:sql.properties"),
        @PropertySource("classpath:app.properties")
})
public class DaoConfig {

    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("user.driverClassName");
        dataSource.setUrl("user.url");
        dataSource.setUsername("username.name");
        dataSource.setPassword("user.password");
        return dataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(){
        return new NamedParameterJdbcTemplate(getDataSource());
    }

    @Bean
    public GroupsDaoImpl getGroupsDao(){
        return new GroupsDaoImpl(getNamedParameterJdbcTemplate());
    }

    @Bean
    public StudentsDaoImpl getStudentsDao(){
        return new StudentsDaoImpl(getNamedParameterJdbcTemplate());
    }
}
