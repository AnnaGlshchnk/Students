package com.anna.controller;

import com.anna.controller.wrappers.IdWrapper;
import com.anna.model.Group;
import com.anna.model.json.View;
import com.anna.service.GroupsService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class GroupsController {

    private final GroupsService groupService;

    @Autowired
    public GroupsController(GroupsService groupService) {
        this.groupService = groupService;
    }

    @JsonView(View.Group.class)
    @GetMapping("/groups")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Group> getGroups(@RequestParam(value = "start", required = false) String  start,
                                    @RequestParam(value = "finish", required = false) String  finish) {
        return groupService.getGroups(start, finish);
    }

    @JsonView(View.GroupWithStudents.class)
    @GetMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Group getGroupById(@PathVariable(value = "groupId") Integer groupId) {
        return groupService.getGroupById(groupId);
    }

    @PostMapping("/groups")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public IdWrapper addGroup(@RequestBody Group group) {
        return IdWrapper.wrap(groupService.addGroup(group));
    }

    @PutMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void updateGroup(@RequestBody Group group, @PathVariable("groupId") Integer groupId) {
        group.setGroupId(groupId);

        groupService.updateGroup(group);
    }

    @DeleteMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable(value = "groupId") Integer groupId) {
        groupService.deleteGroup(groupId);
    }
}
