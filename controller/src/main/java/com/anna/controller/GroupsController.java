package com.anna.controller;

import com.anna.model.Group;
import com.anna.model.json.View;
import com.anna.service.GroupsService;
import com.anna.service.GroupsServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin
public class GroupsController {


    private GroupsServiceImpl groupService;

    public GroupsController(GroupsServiceImpl groupService) {
        this.groupService = groupService;
    }

    @JsonView(View.Group.class)
    @GetMapping("/groups")
    @ResponseStatus(HttpStatus.OK)
    public List<Group> getGroups(@RequestParam(value = "start", required = false) String  start,
                                    @RequestParam(value = "finish", required = false) String  finish) {
        return groupService.getGroups(start, finish);
    }

    @JsonView(View.GroupWithStudents.class)
    @GetMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public Group getGroupById(@PathVariable(value = "groupId") Integer groupId) {
        return groupService.getGroupById(groupId);
    }

    @PostMapping("/groups")
    public ResponseEntity<Void> addGroup(@RequestBody Group group, UriComponentsBuilder builder) {
        UriComponents uriComponents = builder.path("/groups/{groupId}").buildAndExpand(group.getGroupId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
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
