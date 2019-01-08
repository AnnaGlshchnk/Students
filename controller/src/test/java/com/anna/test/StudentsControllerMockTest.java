package com.anna.test;

import com.anna.controller.StudentsController;
import com.anna.model.Student;
import com.anna.service.StudentsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:controller-mock-test-config.xml"})
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
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @After
    public void tearDown() {
        verify(mockStudentsService);
        reset(mockStudentsService);
    }

    @Test
    public void getStudents() throws Exception {
        LOGGER.debug("test: getStudents");

        List<Student> students = new ArrayList<>();
        expect(mockStudentsService.getStudents(null, null)).andReturn(students);
        replay(mockStudentsService);

        mockMvc.perform(get("/students").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getStudentById() throws Exception {
        LOGGER.debug("test: getStudentById");

        Student student = new Student(1, "Anna", "Glush");
        expect(mockStudentsService.getStudentById(anyInt())).andReturn(student);
        replay(mockStudentsService);

        mockMvc.perform(get("/students/1").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void addStudent() throws Exception {
        LOGGER.debug("test: addStudent");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("2019-08-04");
        Date date2 = simpleDateFormat.parse("2023-06-30");

        expect(mockStudentsService.addStudent(anyObject(Student.class))).andReturn(7);
        replay(mockStudentsService);

        String student= "{\"studentId\":0,\"name\":\"Ada\",\"surname\":\"Mluh\",\"birthDate\":\"1998-06-30\"}";

        mockMvc.perform(post("/students").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(student)
        ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateStudent() throws Exception {
        LOGGER.debug("test: updateStudent");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("2019-08-04");
        Date date2 = simpleDateFormat.parse("2023-06-30");

        expect(mockStudentsService.updateStudent(anyObject(Student.class))).andReturn(1);
        replay(mockStudentsService);

        String str = "{\"studentId\":1,\"name\":\"Anna\",\"surname\":\"Glush\",\"birthDate\":\"1998-06-30\"}";

        mockMvc.perform(put("/students/1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(str)
        ).andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteStudent() throws Exception {
        LOGGER.debug("test: deleteStudent");

        expect(mockStudentsService.deleteStudent(anyInt())).andReturn(0);
        replay(mockStudentsService);

        mockMvc.perform(delete("/students/1").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }
}