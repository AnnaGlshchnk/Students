package com.anna.test;

import com.anna.dao.GroupsDao;
import com.anna.model.Group;
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
@ContextConfiguration("classpath*:dao-test-config.xml")
@Transactional
public class GroupDaoTest {

    private static final Logger LOGGER = LogManager.getLogger(GroupDaoTest.class);

    @Autowired
    GroupsDao groupsDao;

    @Test
    public void getGroups() {
        LOGGER.debug("test: getGroups");

        List<Group> groups = groupsDao.getGroups(null, null);
        Assert.assertEquals(3, groups.size());
    }

    @Test
    public void getGroupsWithParam() {
        LOGGER.debug("test: getGroups");

        List<Group> groups = groupsDao.getGroups("2011-08-04", "2019-07-29");
        Assert.assertEquals(2, groups.size());
    }

    @Test
    public void getGroupById() {
        LOGGER.debug("test: getGroupById");

        Group group = groupsDao.getGroupById(1);
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
        Integer groupId = groupsDao.addGroup(group);

        group = groupsDao.getGroupById(groupId);
        Assert.assertEquals(group.getGroupId(), 4);
    }

    @Test
    public void updateGroup() {
        LOGGER.debug("test: updateGroup");

        Group group = groupsDao.getGroupById(1);
        group.setName("newA");

        groupsDao.updateGroup(group);
        Group newgroup = groupsDao.getGroupById(1);
        Assert.assertEquals("newA", newgroup.getName());
    }

    @Test
    public void deleteGroup() {
        LOGGER.debug("test: deleteGroup");

        groupsDao.deleteGroup(1);
        List<Group> groups = groupsDao.getGroups(null, null);
        Assert.assertEquals(2, groups.size());

    }
}
