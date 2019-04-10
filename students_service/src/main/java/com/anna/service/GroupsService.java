package com.anna.service;

import com.anna.exception.OperationFailedException;
import com.anna.model.Group;
import com.anna.model.SaveGroup;
import java.util.List;

public interface GroupsService {

  List<Group> getGroups(Integer page, Integer size, String start, String finish)
      throws OperationFailedException;

  Group getGroupById(Integer groupId) throws OperationFailedException;

  Integer addGroup(SaveGroup group) throws OperationFailedException;

  Integer updateGroup(Integer groupId, SaveGroup group) throws OperationFailedException;

  Integer deleteGroup(Integer groupId) throws OperationFailedException;
}
