package com.anna.controller;

import com.anna.config.ControllerMockTestConfig;
import com.anna.model.Group;
import com.anna.model.SaveGroup;
import com.anna.service.GroupsServiceImpl;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ControllerMockTestConfig.class)
@WebAppConfiguration
public class GroupsControllerMockTest {
    private static final Logger LOGGER = LogManager.getLogger(GroupsControllerMockTest.class);

    @InjectMocks
    private GroupsController groupsController;

    @Mock
    private GroupsServiceImpl mockGroupsService;

    private MockMvc mockMvc;

    public GroupsControllerMockTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(groupsController).build();
    }

    @After
    public void after() {
        Mockito.reset(mockGroupsService);
    }

    @Test
    public void getGroups() throws Exception {
        LOGGER.debug("service: getGroups");

        List<Group> groups = new ArrayList<>();
        Mockito.when(mockGroupsService.getGroups(null, null)).thenReturn(groups);

        mockMvc.perform(get("/groups").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());

        Mockito.verify(mockGroupsService).getGroups(null, null);
    }

    @Test
    public void getGroupById() throws Exception {
        LOGGER.debug("service: getGroupById");

        Group group = new Group(1, "A");
        Mockito.when(mockGroupsService.getGroupById(1)).thenReturn(group);

        mockMvc.perform(get("/groups/1").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());

        Mockito.verify(mockGroupsService).getGroupById(1);
    }

    @Test
    public void addGroup() throws Exception {
        LOGGER.debug("service: addGroup");

        Mockito.when(mockGroupsService.addGroup(Mockito.any(SaveGroup.class))).thenReturn(4);

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

        Mockito.when(mockGroupsService.updateGroup(Mockito.any(Integer.class), Mockito.any(SaveGroup.class))).thenReturn(1);

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

        Mockito.when(mockGroupsService.deleteGroup(Mockito.any(Integer.class))).thenReturn(0);

        mockMvc.perform(delete("/groups/1").accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }
}
