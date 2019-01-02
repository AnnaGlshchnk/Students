package com.anna.controller;

import com.anna.model.Group;
import com.anna.service.GroupsService;
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

    //@JsonView(View.CompanySummary.class)
    @GetMapping("/groups")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Group> getGroups(@RequestParam(value = "start", required = false) String  start,
                                    @RequestParam(value = "finish", required = false) String  finish) {
        return groupService.getGroups(start, finish);
    }

   // @JsonView(View.CompanyDetails.class)
    @GetMapping("/groups/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Group getGroupById(@PathVariable(value = "groupId") Integer groupId) {
        return groupService.getGroupById(groupId);
    }

    @DeleteMapping("/groups/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable(value = "groupId") Integer groupId) {
        groupService.deleteGroup(groupId);
    }
}
