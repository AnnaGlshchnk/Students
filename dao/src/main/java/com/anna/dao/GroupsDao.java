package com.anna.dao;

import com.anna.model.Group;

import java.util.Date;
import java.util.List;

public interface GroupsDao {

    List<Group> getGroups(String start, String finish);

    Group getGroupById(Integer groupId);

    Integer addGroup(Group group);

    Integer updateGroup(Group group);

    Integer deleteGroup(Integer groupId);
}
