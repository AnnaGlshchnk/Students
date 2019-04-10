package com.anna.service;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

import com.anna.config.ServiceTestConfig;
import com.anna.model.Group;
import com.anna.model.SaveGroup;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestConfig.class)
@Transactional
public class GroupServiceTest {

  private static final Logger LOGGER = LogManager.getLogger(GroupServiceTest.class);

  @Autowired
  private GroupsService groupsService;

  @Test
  public void getGroups() {
    LOGGER.debug("service: getGroups");

    List<Group> groups = groupsService.getGroups(1, 3, null, null);
    Assert.assertEquals(3, groups.size());
  }

  @Test
  public void getGroupsWithParam() {
    LOGGER.debug("service: getGroups");

    List<Group> groups = groupsService.getGroups(1, 3, "2011-08-04", "2019-07-29");
    Assert.assertEquals(2, groups.size());
  }

  @Test
  public void getGroupById() {
    LOGGER.debug("service: getGroupById");

    Group group = groupsService.getGroupById(1);
    Assert.assertThat(group, allOf(hasProperty("groupId", equalTo(1)),
        hasProperty("name", equalTo("A"))));
  }

  @Test
  public void addGroup() throws ParseException {
    LOGGER.debug("service: addGroup");

    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Date date1 = simpleDateFormat.parse("2018-09-01");
    Date date2 = simpleDateFormat.parse("2022-06-29");

    SaveGroup group = new SaveGroup("D", date1, date2);
    Integer groupId = groupsService.addGroup(group);
    Group newGroup = groupsService.getGroupById(groupId);

    Assert.assertThat(newGroup, allOf(hasProperty("groupId", equalTo(4)),
        hasProperty("name", equalTo("D")),
        hasProperty("createDate", equalTo(date1)),
        hasProperty("finishDate", equalTo(date2))));
  }

  @Test
  public void updateGroup() throws ParseException {
    LOGGER.debug("service: updateGroup");

    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Date date1 = simpleDateFormat.parse("2018-09-01");
    Date date2 = simpleDateFormat.parse("2022-06-29");

    SaveGroup group = new SaveGroup("A", date1, date2);
    group.setName("newA");
    groupsService.updateGroup(1, group);
    Group newgroup = groupsService.getGroupById(1);

    Assert.assertEquals("newA", newgroup.getName());
  }

  @Test
  public void deleteGroup() {
    LOGGER.debug("service: deleteGroup");

    groupsService.deleteGroup(1);
    List<Group> groups = groupsService.getGroups(1, 3, null, null);
    Assert.assertEquals(2, groups.size());
  }
}
