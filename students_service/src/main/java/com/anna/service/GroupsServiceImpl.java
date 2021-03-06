package com.anna.service;

import com.anna.dao.GroupsDao;
import com.anna.exception.OperationFailedException;
import com.anna.model.Group;
import com.anna.model.SaveGroup;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupsServiceImpl implements GroupsService {

  private GroupsDao groupsDao;

  @Autowired
  public GroupsServiceImpl(GroupsDao groupsDao) {
    this.groupsDao = groupsDao;
  }

  @Override
  public List<Group> getGroups(Integer page, Integer size, String start, String finish)
      throws OperationFailedException {

    if (page <= 0 || size <= 0) {
      throw new OperationFailedException("Parameters not to be equal or less then zero.");
    }

    List<Group> groups = groupsDao.getGroups(--page * size, size, start, finish);
    if (groups.size() == 0) {
      throw new EmptyResultDataAccessException("No found.", 0);
    }
    return groups;
  }

  @Override
  public Group getGroupById(Integer groupId) throws OperationFailedException {
    if (groupId == null) {
      throw new OperationFailedException("groupId shouldn't be null");
    }

    return groupsDao.getGroupById(groupId);
  }

  @Override
  public Integer addGroup(SaveGroup group) throws OperationFailedException {
    return groupsDao.addGroup(group);
  }

  @Override
  public Integer updateGroup(Integer groupId, SaveGroup group) throws OperationFailedException {
    return groupsDao.updateGroup(groupId, group);
  }

  @Override
  public Integer deleteGroup(Integer groupId) throws OperationFailedException {
    return groupsDao.deleteGroup(groupId);
  }
}
