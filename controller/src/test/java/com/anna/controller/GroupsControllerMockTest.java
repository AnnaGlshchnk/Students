package com.anna.controller;

import com.anna.model.Group;
import com.anna.model.SaveGroup;
import com.anna.service.GroupsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:controller-mock-test-config.xml"})
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
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
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

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("2019-08-04");
        Date date2 = simpleDateFormat.parse("2023-06-30");

        expect(mockGroupsService.addGroup(anyObject(SaveGroup.class))).andReturn(4);
        replay(mockGroupsService);

        String group= "{\"groupId\":0,\"name\":\"D\",\"createDate\":\"2019-08-04\",\"finishDate\":\"2023-06-30\",\"countOfStudent\":0,\"avgAge\":0.0}";

        mockMvc.perform(post("/groups").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(group)
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateGroup() throws Exception {
        LOGGER.debug("service: updateGroup");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("2019-08-04");
        Date date2 = simpleDateFormat.parse("2023-06-30");

        expect(mockGroupsService.updateGroup(anyObject(SaveGroup.class))).andReturn(1);
        replay(mockGroupsService);

        String str = "{\"groupId\":1,\"name\":\"A\",\"createDate\":\"2019-08-04\",\"finishDate\":\"2023-06-30\",\"countOfStudent\":2,\"avgAge\":19.5}";

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
