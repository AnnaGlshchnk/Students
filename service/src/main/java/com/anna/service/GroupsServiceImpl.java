package com.anna.service;

import com.anna.dao.GroupsDao;
import com.anna.exception.OperationFailedException;
import com.anna.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    GroupsDao groupsDao;

    public void setGroupsDao(GroupsDao groupsDao) {
        this.groupsDao = groupsDao;
    }


    @Override
    public List<Group> getGroups(String start, String finish) throws OperationFailedException {
        return groupsDao.getGroups(start, finish);
    }

    @Override
    public Group getGroupById(Integer groupId) throws OperationFailedException {
        if (groupId == null) {
            throw new OperationFailedException("groupId shouldn't be null");
        }

        return groupsDao.getGroupById(groupId);
    }

    @Override
    public Integer addGroup(Group group) throws OperationFailedException {

        validate(group);

        return groupsDao.addGroup(group);
    }

    @Override
    public Integer updateGroup(Group group) throws OperationFailedException{

        if (group.getGroupId() <= 0) {
            throw new OperationFailedException("groupId should be more than 0");
        }
        validate(group);

        return groupsDao.updateGroup(group);
    }

    private void validate(Group group) {
        if (group == null) {
            throw new OperationFailedException("group shouldn't be null");
        }
        if (group.getName() == null) {
            throw new OperationFailedException("group should have name");
        }
        if (group.getCreateDate() == null) {
            throw new OperationFailedException("group should have creation date");
        }
        if (group.getFinishDate() == null) {
            throw new OperationFailedException("group should have graduation date");
        }
        if (group.getCountOfStudent() < 0) {
            throw new OperationFailedException("group should have students");
        }
    }

    @Override
    public Integer deleteGroup(Integer groupId) throws OperationFailedException{
        if (groupId == null) {
            throw new OperationFailedException("groupId shouldn't be null");
        }

        return groupsDao.deleteGroup(groupId);
    }
}
