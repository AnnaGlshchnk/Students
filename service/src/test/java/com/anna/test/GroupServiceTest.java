package com.anna.test;

import com.anna.model.Group;
import com.anna.service.GroupsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test-config.xml"})
@Transactional
public class GroupServiceTest {

    private static final Logger LOGGER = LogManager.getLogger(GroupServiceTest.class);

    @Autowired
    GroupsService groupsService;

    @Test
    public void getGroups() {
        LOGGER.debug("test: getGroups");

        List<Group> groups = groupsService.getGroups(null, null);
        Assert.assertEquals(3, groups.size());
    }

    @Test
    public void getGroupsWithParam() {
        LOGGER.debug("test: getGroups");

        List<Group> groups = groupsService.getGroups("2011-08-04", "2019-07-29");
        Assert.assertEquals(2, groups.size());
    }

    @Test
    public void getGroupById() {
        LOGGER.debug("test: getGroupById");

        Group group = groupsService.getGroupById(1);
        Assert.assertNotNull(group);
        Assert.assertNotEquals(0, group.getGroupId());
        Assert.assertEquals("A", group.getName());
    }

    @Test
    public void addGroup() throws ParseException {
        LOGGER.debug("test: addGroup");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("2018-09-01");
        Date date2 = simpleDateFormat.parse("2022-06-29");

        Group group = new Group("D", date1, date2);
        Integer groupId = groupsService.addGroup(group);

        group = groupsService.getGroupById(groupId);
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getName());
        Assert.assertNotNull(group.getCreateDate());
        Assert.assertNotNull(group.getFinishDate());
        Assert.assertEquals(group.getGroupId(), 4);
    }

    @Test
    public void updateGroup() {
        LOGGER.debug("test: updateGroup");

        Group group = groupsService.getGroupById(1);
        Assert.assertNotNull(group);
        Assert.assertNotEquals(0, group.getGroupId());
        Assert.assertNotNull(group.getName());
        Assert.assertNotNull(group.getCreateDate());
        Assert.assertNotNull(group.getFinishDate());
        group.setName("newA");

        groupsService.updateGroup(group);
        Group newgroup = groupsService.getGroupById(1);
        Assert.assertEquals("newA", newgroup.getName());
    }
    @Test
    public void deleteGroup() {
        LOGGER.debug("test: deleteGroup");

        groupsService.deleteGroup(1);
        List<Group> groups = groupsService.getGroups(null, null);
        Assert.assertEquals(2, groups.size());

    }
}
