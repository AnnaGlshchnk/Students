package com.anna.dao;

import com.anna.model.Group;
import com.anna.model.SaveGroup;
import java.util.List;

public interface GroupsDao {

  List<Group> getGroups(String start, String finish);

  Group getGroupById(Integer groupId);

  Integer addGroup(SaveGroup group);

  Integer updateGroup(Integer groupId, SaveGroup group);

  Integer deleteGroup(Integer groupId);
}
