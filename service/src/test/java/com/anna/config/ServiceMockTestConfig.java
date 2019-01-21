package com.anna.config;


import com.anna.dao.GroupsDao;
import com.anna.dao.GroupsDaoImpl;
import com.anna.dao.StudentsDao;
import com.anna.dao.StudentsDaoImpl;
import com.anna.service.GroupsService;
import com.anna.service.GroupsServiceImpl;
import com.anna.service.StudentsService;
import com.anna.service.StudentsServiceImpl;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceMockTestConfig {

    @Bean
    public GroupsDao groupsDao(){
        return EasyMock.createMock(GroupsDaoImpl.class);
    }

    @Bean
    public StudentsDao studentsDao(){
        return EasyMock.createMock(StudentsDaoImpl.class);
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
