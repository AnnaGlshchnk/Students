package com.anna.config;

import com.anna.service.GroupsServiceImpl;
import com.anna.service.StudentsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(value = DaoConfig.class)
@EnableTransactionManagement
public class ServiceConfig {

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean
    public GroupsServiceImpl getGroupsService(){
        return new GroupsServiceImpl(getGroupsDao());
    }

    @Bean
    public StudentsServiceImpl getStudentsService(){
        return new StudentsServiceImpl(getStudentsDao());
    }

}
