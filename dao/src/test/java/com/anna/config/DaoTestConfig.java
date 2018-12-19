package com.anna.config;

import com.anna.dao.StudentsDao;
import com.anna.dao.StudentsDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
@Component
@PropertySource("classpath:sql.properties")
public class DaoTestConfig {

    @Bean
    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return new NamedParameterJdbcTemplate(getDataSource());
    }

    @Bean
    public DataSource getDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("testDB.sql")
                .addScript("testData.sql")
                .build();

        return db;
    }

    @Bean
    public StudentsDao getStudentDao(){
        return new StudentsDaoImpl(getJdbcTemplate());
    }
}
