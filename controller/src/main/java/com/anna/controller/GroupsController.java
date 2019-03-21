package com.anna.controller;

import com.anna.model.Group;
import com.anna.model.SaveGroup;
import com.anna.model.json.View;
import com.anna.service.GroupsService;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin
@RestController
public class GroupsController {

  private GroupsService groupService;

  @Autowired
  public GroupsController(GroupsService groupService) {
    this.groupService = groupService;
  }

  @JsonView(View.Group.class)
  @GetMapping("/groups")
  @ResponseStatus(HttpStatus.OK)
  public List<Group> getGroups(
      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
      @RequestParam(value = "start", required = false) String start,
      @RequestParam(value = "finish", required = false) String finish) {
    return groupService.getGroups(page, size,start, finish);
  }

  @JsonView(View.GroupWithStudents.class)
  @GetMapping("/groups/{groupId}")
  @ResponseStatus(HttpStatus.OK)
  public Group getGroupById(@PathVariable(value = "groupId") Integer groupId) {
    return groupService.getGroupById(groupId);
  }

  /**
   * Add group.
   *
   * @param group the group
   * @param builder builder for UriComponents
   * @return url
   */
  @PostMapping("/groups")
  public ResponseEntity<Void> addGroup(@Valid @RequestBody SaveGroup group,
      UriComponentsBuilder builder) {
    Integer createdId = groupService.addGroup(group);
    UriComponents uriComponents = builder.path("/groups/{groupId}").buildAndExpand(createdId);
    return ResponseEntity.created(uriComponents.toUri()).build();
  }

  @PutMapping("/groups/{groupId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void updateGroup(@Valid @RequestBody SaveGroup group,
      @PathVariable("groupId") Integer groupId) {
    groupService.updateGroup(groupId, group);
  }

  @DeleteMapping("/groups/{groupId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteGroup(@PathVariable(value = "groupId") Integer groupId) {
    groupService.deleteGroup(groupId);
  }

}
