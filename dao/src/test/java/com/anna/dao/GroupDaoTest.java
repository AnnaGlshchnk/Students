package com.anna.dao;

import com.anna.config.DaoTestConfig;
import com.anna.model.Group;
import com.anna.model.SaveGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class)
@Transactional
public class GroupDaoTest {

    private static final Logger LOGGER = LogManager.getLogger(GroupDaoTest.class);

    @Autowired
    private GroupsDao groupsDao;

    @Test
    public void getGroups() {
        LOGGER.debug("service: getGroups");

        List<Group> groups = groupsDao.getGroups(null, null);
        Assert.assertEquals(3, groups.size());
    }

    @Test
    public void getGroupsWithParam() {
        LOGGER.debug("service: getGroups");

        List<Group> groups = groupsDao.getGroups("2011-08-04", "2019-07-29");
        Assert.assertEquals(2, groups.size());
    }

    @Test
    public void getGroupById() {
        LOGGER.debug("service: getGroupById");

        Group group = groupsDao.getGroupById(1);
        Assert.assertEquals("A", group.getName());
    }

    @Test
    public void addGroup() throws ParseException {
        LOGGER.debug("service: addGroup");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("2018-09-01");
        Date date2 = simpleDateFormat.parse("2022-06-29");

        SaveGroup group = new SaveGroup("D", date1, date2);
        Integer groupId = groupsDao.addGroup(group);

        Group newGroup = groupsDao.getGroupById(groupId);
        Assert.assertEquals(newGroup.getGroupId(), 4);
    }

    @Test
    public void updateGroup() throws ParseException {
        LOGGER.debug("service: updateGroup");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("2018-09-01");
        Date date2 = simpleDateFormat.parse("2022-06-29");

        SaveGroup saveGroup = new SaveGroup( "A", date1, date2);
        saveGroup.setName("newA");
        groupsDao.updateGroup(1, saveGroup);
        Group newgroup = groupsDao.getGroupById(1);
        Assert.assertEquals("newA", newgroup.getName());
    }

    @Test
    public void deleteGroup() {
        LOGGER.debug("service: deleteGroup");

        groupsDao.deleteGroup(1);
        List<Group> groups = groupsDao.getGroups(null, null);
        Assert.assertEquals(2, groups.size());

    }
}
