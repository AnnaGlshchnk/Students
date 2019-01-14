package com.anna.controller;

import com.anna.model.Group;
import com.anna.model.SaveGroup;
import com.anna.model.json.View;
import com.anna.service.GroupsService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class GroupsController {

    private GroupsService groupService;

    public GroupsController(GroupsService groupService) {
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
    public ResponseEntity<Void> addGroup(@Valid @RequestBody  SaveGroup group, UriComponentsBuilder builder) {
        Integer createdId = groupService.addGroup(group);
        UriComponents uriComponents = builder.path("/groups/{groupId}").buildAndExpand(createdId);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGroup(@Valid @RequestBody   SaveGroup group, @PathVariable("groupId") Integer groupId) {
        groupService.updateGroup(groupId, group);
    }

    @DeleteMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable(value = "groupId") Integer groupId) {
        groupService.deleteGroup(groupId);
    }


}
