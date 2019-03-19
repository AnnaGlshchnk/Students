package com.anna.service;

import static org.junit.Assert.assertEquals;

import com.anna.config.ServiceTestConfig;
import com.anna.dao.GroupsDao;
import com.anna.model.Group;
import com.anna.model.SaveGroup;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestConfig.class)
public class GroupServiceMockTest {

  private static final Logger LOGGER = LogManager.getLogger(GroupServiceMockTest.class);

  @InjectMocks
  private GroupsServiceImpl groupsService;

  @Mock
  private GroupsDao mockGroupsDao;

  public GroupServiceMockTest() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void clean() {
    Mockito.reset(mockGroupsDao);
  }

  @Test
  public void getGroups() {
    LOGGER.debug("service: getGroups");

    List<Group> groups = new ArrayList<>();
    Mockito.when(mockGroupsDao.getGroups(null, null)).thenReturn(groups);

    groups = groupsService.getGroups(null, null);
    Assert.assertEquals(0, groups.size());
  }

  @Test
  public void getGroupsWithParam() {
    LOGGER.debug("service: getGroups");

    List<Group> groups = new ArrayList<>();
    Mockito.when(mockGroupsDao.getGroups("2011-08-04", "2019-07-29")).thenReturn(groups);

    groups = groupsService.getGroups("2011-08-04", "2019-07-29");
    Assert.assertEquals(0, groups.size());
  }

  @Test
  public void getGroupById() {
    LOGGER.debug("service: getGroupById");

    Mockito.when(mockGroupsDao.getGroupById(Mockito.any(Integer.class))).thenReturn(new Group(1));

    Group group = groupsService.getGroupById(1);
    Assert.assertEquals(1, group.getGroupId());
  }

  @Test
  public void addGroup() throws ParseException {
    LOGGER.debug("service: addGroup");

    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Date date1 = simpleDateFormat.parse("2019-08-04");
    Date date2 = simpleDateFormat.parse("2023-06-30");

    SaveGroup group = new SaveGroup("D", date1, date2);

    Mockito.when(mockGroupsDao.addGroup(group)).thenReturn(4);

    Integer groupId = groupsService.addGroup(group);
    assertEquals(groupId, (Integer) 4);
  }

  @Test
  public void updateGroup() throws ParseException {
    LOGGER.debug("service: updateGroup");

    Mockito
        .when(mockGroupsDao.updateGroup(Mockito.any(Integer.class), Mockito.any(SaveGroup.class)))
        .thenReturn(1);

    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Date date1 = simpleDateFormat.parse("2019-08-04");
    Date date2 = simpleDateFormat.parse("2023-06-30");

    Integer id = groupsService.updateGroup(1, new SaveGroup("A", date1, date2));
    Assert.assertEquals(id, Integer.valueOf(1));

  }

  @Test
  public void deleteGroup() {
    LOGGER.debug("service: deleteGroup");

    Mockito.when(mockGroupsDao.deleteGroup(Mockito.any(Integer.class))).thenReturn(1);

    Integer id = groupsService.deleteGroup(1);
    Assert.assertEquals(id, Integer.valueOf(1));

  }
}
