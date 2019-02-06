package com.anna.config;


import com.anna.controller.GroupsController;
import com.anna.controller.StudentsController;
import com.anna.service.GroupsService;
import com.anna.service.GroupsServiceImpl;
import com.anna.service.StudentsService;
import com.anna.service.StudentsServiceImpl;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerMockTestConfig {

    @Bean
    public GroupsService mockGroupsService() {
        return EasyMock.createMock(GroupsServiceImpl.class);
    }

    @Bean
    public StudentsService mockStudentsService() {
        return EasyMock.createMock(StudentsServiceImpl.class);
    }

    @Bean
    public GroupsController groupsController() {
        return new GroupsController(mockGroupsService());
    }

    @Bean
    public StudentsController studentsController() {
        return new StudentsController(mockStudentsService());
    }
}
