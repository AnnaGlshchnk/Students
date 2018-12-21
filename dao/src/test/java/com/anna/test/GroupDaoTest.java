//package com.anna.test;
//
//import com.anna.model.Group;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.List;
//
//public class GroupDaoTest {
//    @Test
//    public void getGroups(){
//
//        List<Group> groups = studentsDao.getGroups(null, null);
//        Assert.assertEquals(3, groups.size());
//
//        groups = studentsDao.getGroups(1, 2);
//        Assert.assertEquals(2, groups.size());
//    }
//
//    @Test
//    public void  getGroupById(){
//        Group group = studentsDao.getGroupById(1);
//        Assert.assertEquals("A", group.getName());
//
//    }
//
//    @Test
//    public void addGroups(){
//
//        Group group = new Group("D");
//        studentsDao.addGroup(group);
//
//        Assert.assertEquals(group.getName(), "D");
//    }
//
//
//    @Test
//    public void updateGroup(){
//
//        Group group = studentsDao.getGroupById(1);
//        group.setName("new A");
//
//        studentsDao.updateGroup(group);
//        group = studentsDao.getGroupById(1);
//        Assert.assertEquals("new A", group.getName());
//    }
//
//    @Test
//    public  void deleteGroup(){
//
//        studentsDao.deleteGroup(1);
//        Group group = studentsDao.getGroupById(1);
//        Assert.assertNull(group.getGroupId());
//
//    }
//}
