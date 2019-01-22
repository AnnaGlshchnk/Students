package com.anna.controller;

import com.anna.config.ControllerMockTestConfig;
import com.anna.model.Group;
import com.anna.model.SaveGroup;
import com.anna.service.GroupsService;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ControllerMockTestConfig.class)
@WebAppConfiguration
public class GroupsControllerMockTest {
    private static final Logger LOGGER = LogManager.getLogger(GroupsControllerMockTest.class);

    @Autowired
    private GroupsController groupsController;

    private MockMvc mockMvc;

    @Autowired
    private GroupsService mockGroupsService;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(groupsController)
                .build();
    }

    @After
    public void tearDown() {
        verify(mockGroupsService);
        reset(mockGroupsService);
    }

    @Test
    public void getGroups() throws Exception {
        LOGGER.debug("service: getGroups");

        List<Group> groups = new ArrayList<>();
        expect(mockGroupsService.getGroups(null, null)).andReturn(groups);
        replay(mockGroupsService);

        mockMvc.perform(get("/groups").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getGroupById() throws Exception {
        LOGGER.debug("service: getGroupById");

        Group group = new Group(1, "A");
        expect(mockGroupsService.getGroupById(anyInt())).andReturn(group);
        replay(mockGroupsService);

        mockMvc.perform(get("/groups/1").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void addGroup() throws Exception {
        LOGGER.debug("service: addGroup");

        expect(mockGroupsService.addGroup(anyObject(SaveGroup.class))).andReturn(4);
        replay(mockGroupsService);

        Resource resource = new ClassPathResource("requests/add_group.json");
        InputStream resourceInputStream = resource.getInputStream();
        String group = IOUtils.toString(resourceInputStream, "UTF-8");

        mockMvc.perform(post("/groups").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(group)
        ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateGroup() throws Exception {
        LOGGER.debug("service: updateGroup");

        expect(mockGroupsService.updateGroup(anyObject(Integer.class), anyObject(SaveGroup.class))).andReturn(1);
        replay(mockGroupsService);

        Resource resource = new ClassPathResource("requests/update_group.json");
        InputStream resourceInputStream = resource.getInputStream();
        String str = IOUtils.toString(resourceInputStream, "UTF-8");

        mockMvc.perform(put("/groups/1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(str)
        ).andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteGroup() throws Exception {
        LOGGER.debug("service: deleteGroup");

        expect(mockGroupsService.deleteGroup(anyInt())).andReturn(0);
        replay(mockGroupsService);

        mockMvc.perform(delete("/groups/1").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }
}
