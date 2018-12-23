package com.anna.test;

import com.anna.dao.GroupsDao;
import com.anna.dao.StudentsDao;
import com.anna.model.Group;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-config.xml")
public class GroupDaoTest {

    @Autowired
    GroupsDao groupsDao;

    @Test
    public void getGroups() {

        List<Group> groups = groupsDao.getGroups(null, null);
        Assert.assertEquals(3, groups.size());

    }

    @Test
    public void getGroupById() {
        Group group = groupsDao.getGroupById(1);
        Assert.assertEquals("A", group.getName());

    }

    @Test
    public void addGroups() {

        Group group = new Group("D");
        groupsDao.addGroup(group);

        Assert.assertEquals(group.getName(), "D");
    }


    @Test
    public void updateGroup() {

        Group group = groupsDao.getGroupById(1);
        group.setName("new A");

        groupsDao.updateGroup(group);
        group = groupsDao.getGroupById(1);
        Assert.assertEquals("new A", group.getName());
    }

    @Test
    public void deleteGroup() {

        groupsDao.deleteGroup(1);
        Group group = groupsDao.getGroupById(1);
        Assert.assertNull(group.getGroupId());

    }
}
