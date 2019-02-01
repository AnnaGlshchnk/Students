package com.anna.integration_test.test;


import com.anna.model.Group;
import com.anna.model.SaveStudent;
import com.anna.model.Student;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class StudentsIntegrationTests {

    private RestTemplate restTemplate = new RestTemplate();

    private final String fooResourceUrl = "http://172.19.0.2:8080/students";

    @Test
    public void getStudentById() {

        ResponseEntity<Student> response = restTemplate.getForEntity(fooResourceUrl + "/1", Student.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getStudentId(), 1);
        assertEquals(response.getBody().getName(), "Ann");
        assertEquals(response.getBody().getSurname(), "Glush");
        assertEquals(response.getBody().getGroup().getGroupId(), 1);
    }

    @Test
    public void addStudent() throws ParseException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("1999-01-08");

        HttpEntity<SaveStudent> request = new HttpEntity<>(new SaveStudent("Kally", "Blanck", date1, new Group(2)));
        ResponseEntity<SaveStudent> response = restTemplate.postForEntity(fooResourceUrl, request, SaveStudent.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertNotNull(response);
    }

    @Test
    public void updateStudent() throws ParseException {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("1999-01-08");

        Student student = new Student(4, "Kally", "Blank", date1, new Group(2));
        String entityUrl = fooResourceUrl + "/" + student.getStudentId();

        restTemplate.put(entityUrl, student);

    }

    @Test
    public void deleteStudent() throws ParseException {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("1999-01-08");

        Student student = new Student(16, "Kally", "Blanck", date1, new Group(2));
        String entityUrl = fooResourceUrl + "/" + student.getStudentId();

        restTemplate.delete(entityUrl);
    }
}
