package com.anna.controller;

import com.anna.config.ControllerMockTestConfig;
import com.anna.model.SaveStudent;
import com.anna.model.Student;
import com.anna.service.StudentsServiceImpl;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ControllerMockTestConfig.class)
@WebAppConfiguration
public class StudentsControllerMockTest {
    private static final Logger LOGGER = LogManager.getLogger(StudentsControllerMockTest.class);


    @InjectMocks
    private StudentsController studentsController;

    @Mock
    private StudentsServiceImpl mockStudentsService;

    private MockMvc mockMvc;

    public StudentsControllerMockTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(studentsController).build();
    }

    @After
    public void after() {
        Mockito.reset(mockStudentsService);
    }

    @Test
    public void getStudents() throws Exception {
        LOGGER.debug("service: getStudents");

        List<Student> students = new ArrayList<>();
        Mockito.when(mockStudentsService.getStudents(null, null)).thenReturn(students);

        mockMvc.perform(get("/students").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());

        Mockito.verify(mockStudentsService).getStudents(null, null);
    }

    @Test
    public void getStudentById() throws Exception {
        LOGGER.debug("service: getStudentById");

        Student student = new Student(1, "Anna", "Glush");
        Mockito.when(mockStudentsService.getStudentById(1)).thenReturn(student);

        mockMvc.perform(get("/students/1").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());

        Mockito.verify(mockStudentsService).getStudentById(1);
    }

    @Test
    public void addStudent() throws Exception {
        LOGGER.debug("service: addStudent");

        Mockito.when(mockStudentsService.addStudent(Mockito.any(SaveStudent.class))).thenReturn(7);

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

        Mockito.when(mockStudentsService.updateStudent(Mockito.any(Integer.class), Mockito.any(SaveStudent.class))).thenReturn(1);

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

        mockStudentsService.deleteStudent(Mockito.any(Integer.class));

        mockMvc.perform(delete("/students/1").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }
}
