package com.anna.config;

import com.anna.dao.GroupsDao;
import com.anna.dao.GroupsDaoImpl;
import com.anna.dao.StudentsDao;
import com.anna.dao.StudentsDaoImpl;
import com.anna.service.GroupsService;
import com.anna.service.GroupsServiceImpl;
import com.anna.service.StudentsService;
import com.anna.service.StudentsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:sql.properties")
@EnableTransactionManagement
public class ServiceTestConfig {

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

        return builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("testDB.sql")
                .addScript("testData.sql")
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public GroupsDao groupsDao(){
        return new GroupsDaoImpl(namedParameterJdbcTemplate());
    }

    @Bean
    public StudentsDao studentsDao(){
        return new StudentsDaoImpl(namedParameterJdbcTemplate());
    }

    @Bean
    public GroupsService groupsService(){
        return new GroupsServiceImpl(groupsDao());
    }

    @Bean
    public StudentsService studentsService(){
        return new StudentsServiceImpl(studentsDao());
    }
}
