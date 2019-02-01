package com.anna.service;

import com.anna.config.ServiceMockTestConfig;
import com.anna.dao.GroupsDao;
import com.anna.model.Group;
import com.anna.model.SaveGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceMockTestConfig.class)
public class GroupServiceMockTest {

    private static final Logger LOGGER = LogManager.getLogger(GroupServiceMockTest.class);

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private GroupsDao mockGroupsDao;

    @After
    public void clean() {
        verify(mockGroupsDao);
        reset(mockGroupsDao);
    }

    @Test
    public void getGroups() {
        LOGGER.debug("service: getGroups");

        List<Group> groups = new ArrayList<>();
        expect(mockGroupsDao.getGroups(null, null)).andReturn(groups);

        replay(mockGroupsDao);

        groups = groupsService.getGroups(null, null);
        Assert.assertEquals(0, groups.size());
    }

    @Test
    public void getGroupsWithParam() {
        LOGGER.debug("service: getGroups");

        List<Group> groups = new ArrayList<>();
        expect(mockGroupsDao.getGroups("2011-08-04", "2019-07-29")).andReturn(groups);
        replay(mockGroupsDao);

        groups = groupsService.getGroups("2011-08-04", "2019-07-29");
        Assert.assertEquals(0, groups.size());
    }

    @Test
    public void getGroupById() {
        LOGGER.debug("service: getGroupById");

        expect(mockGroupsDao.getGroupById(anyInt())).andReturn(new Group(1));
        replay(mockGroupsDao);

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

        expect(mockGroupsDao.addGroup(group)).andReturn(4);
        replay(mockGroupsDao);

        Integer groupId = groupsService.addGroup(group);
        assertEquals(groupId, (Integer) 4);
    }

    @Test
    public void updateGroup() throws ParseException {
        LOGGER.debug("service: updateGroup");
        expect(mockGroupsDao.updateGroup(anyObject(), anyObject())).andReturn(1);
        replay(mockGroupsDao);

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

        expect(mockGroupsDao.deleteGroup(anyInt())).andReturn(1);
        replay(mockGroupsDao);

        Integer id = groupsService.deleteGroup(1);
        Assert.assertEquals(id, Integer.valueOf(1));

    }
}
