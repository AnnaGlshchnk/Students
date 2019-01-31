package com.anna.integration_test.test;

import com.anna.model.Group;
import com.anna.model.SaveGroup;
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
import static org.junit.Assert.assertNotNull;

public class GroupsIntegrationTests {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getGroupById() {

        String fooResourceUrl = "http://172.23.0.3:8080/groups";

        ResponseEntity<Group> response = restTemplate.getForEntity(fooResourceUrl + "/1", Group.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getGroupId(), 1);
        assertEquals(response.getBody().getName(), "A");
    }

    @Test
    public void addStudent() throws ParseException {
        String fooResourceUrl = "http://172.23.0.3:8080/groups";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("2018-09-01");
        Date date2 = simpleDateFormat.parse("2022-06-29");

        HttpEntity<SaveGroup> request = new HttpEntity<>(new SaveGroup("D", date1, date2));
        ResponseEntity<SaveGroup> response = restTemplate.postForEntity(fooResourceUrl, request, SaveGroup.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
        assertNotNull(response);
    }

    @Test
    public void updateStudent() throws ParseException {
        String fooResourceUrl = "http://172.23.0.3:8080/groups";

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("2018-09-01");
        Date date2 = simpleDateFormat.parse("2022-06-29");

        Group group = new Group(4, "Dnew", date1, date2);
        String entityUrl = fooResourceUrl + "/" + group.getGroupId();

        restTemplate.put(entityUrl, group);
    }

    @Test
    public void deleteStudent() throws ParseException {
        String fooResourceUrl = "http://172.23.0.3:8080/groups";

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("2018-09-01");
        Date date2 = simpleDateFormat.parse("2022-06-29");

        Group group = new Group(4, "Dnew", date1, date2);
        String entityUrl = fooResourceUrl + "/" + group.getGroupId();

        restTemplate.delete(entityUrl);
    }
}
