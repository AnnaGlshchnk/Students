package com.anna.controller;

import com.anna.config.ControllerMockTestConfig;
import com.anna.model.Student;
import com.anna.service.StudentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ControllerMockTestConfig.class)
@WebAppConfiguration
public class StudentsControllerMockTest {

    private static final Logger LOGGER = LogManager.getLogger(StudentsControllerMockTest.class);

    @Autowired
    private StudentsController studentsController;

    private MockMvc mockMvc;

    @Autowired
    private StudentsService mockStudentsService;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(studentsController)
                .build();
    }

    @After
    public void tearDown() {
        verify(mockStudentsService);
        reset(mockStudentsService);
    }

    @Test
    public void getStudents() throws Exception {
        LOGGER.debug("service: getStudents");

        List<Student> students = new ArrayList<>();
        expect(mockStudentsService.getStudents(null, null)).andReturn(students);
        replay(mockStudentsService);

        mockMvc.perform(get("/students").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getStudentById() throws Exception {
        LOGGER.debug("service: getStudentById");

        Student student = new Student(1, "Anna", "Glush");
        expect(mockStudentsService.getStudentById(anyInt())).andReturn(student);
        replay(mockStudentsService);

        mockMvc.perform(get("/students/1").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void addStudent() throws Exception {
        LOGGER.debug("service: addStudent");

        expect(mockStudentsService.addStudent(anyObject(Student.class))).andReturn(7);
        replay(mockStudentsService);

        Resource resource = new ClassPathResource("requests/add_student.json");
        InputStream resourceInputStream = resource.getInputStream();

        String student = IOUtils.toString(resourceInputStream, "UTF-8");

        mockMvc.perform(post("/students").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(student)
        ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateStudent() throws Exception {
        LOGGER.debug("service: updateStudent");

        mockStudentsService.updateStudent(anyObject(Student.class));
        expectLastCall().once();
        replay(mockStudentsService);
        Resource resource = new ClassPathResource("requests/update_student.json");
        InputStream resourceInputStream = resource.getInputStream();
        String str = IOUtils.toString(resourceInputStream, "UTF-8");

        mockMvc.perform(put("/students/1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(str)
        ).andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteStudent() throws Exception {
        LOGGER.debug("service: deleteStudent");

        mockStudentsService.deleteStudent(anyInt());
        expectLastCall().once();
        replay(mockStudentsService);

        mockMvc.perform(delete("/students/1").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }
}
