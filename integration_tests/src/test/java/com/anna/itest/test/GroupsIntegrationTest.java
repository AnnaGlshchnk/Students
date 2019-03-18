package com.anna.itest.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.anna.model.Group;
import com.anna.model.SaveGroup;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class GroupsIntegrationTest {

  private final String fooResourceUrl = "http://localhost:8080/groups";
  private RestTemplate restTemplate = new RestTemplate();

  @Test
  public void getGroupById() {
    ResponseEntity<Group> response = restTemplate.getForEntity(fooResourceUrl + "/1", Group.class);
    assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(response.getBody().getGroupId(), 1);
    assertEquals(response.getBody().getName(), "A");
  }

  @Test
  public void addGroup() throws ParseException {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Date date1 = simpleDateFormat.parse("2018-09-01");
    Date date2 = simpleDateFormat.parse("2022-06-29");

    HttpEntity<SaveGroup> request = new HttpEntity<>(new SaveGroup("D", date1, date2));
    ResponseEntity<SaveGroup> response = restTemplate
        .postForEntity(fooResourceUrl, request, SaveGroup.class);
    assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    assertNotNull(response);
  }

  @Test
  public void updateGroup() throws ParseException {

    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Date date1 = simpleDateFormat.parse("2018-09-01");
    Date date2 = simpleDateFormat.parse("2022-06-29");

    Group group = new Group(4, "Dnew", date1, date2);
    String entityUrl = fooResourceUrl + "/" + group.getGroupId();

    restTemplate.put(entityUrl, group);
  }

  @Test
  public void deleteGroup() throws ParseException {
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Date date1 = simpleDateFormat.parse("2018-09-01");
    Date date2 = simpleDateFormat.parse("2022-06-29");

    HttpEntity<SaveGroup> request = new HttpEntity<>(new SaveGroup("Dnew", date1, date2));
    try {
      ResponseEntity<SaveGroup> response = restTemplate
          .postForEntity(fooResourceUrl, request, SaveGroup.class);
      restTemplate.delete(response.getHeaders().getLocation());
    } catch (HttpServerErrorException ex) {
      System.out.println(ex);
    }

  }
}
