package com.anna.service;

import com.anna.model.Group;

import java.util.List;

public interface GroupsService {

    List<Group> getGroups(String start, String finish);

    Group getGroupById(Integer groupId);

    Integer addGroup(Group group);

    Integer updateGroup(Group group);

    Integer deleteGroup(Integer groupId);
}
