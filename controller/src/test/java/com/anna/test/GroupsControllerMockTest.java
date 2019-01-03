package com.anna.test;

import com.anna.controller.GroupsController;
import com.anna.model.Group;
import com.anna.service.GroupsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.engine.User;
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
import org.springframework.transaction.annotation.Transactional;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:controller-mock-test-config.xml"})
public class GroupsControllerMockTest {
    private static final Logger LOGGER = LogManager.getLogger(GroupsControllerMockTest.class);

    @Resource
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
        LOGGER.debug("test: getGroups");

        List<Group> groups = new ArrayList<>();
        expect(mockGroupsService.getGroups(null, null)).andReturn(groups);
        replay(mockGroupsService);

        mockMvc.perform(get("/groups").accept(MediaType.APPLICATION_JSON)
                ).andDo(print()).andExpect(status().isOk());
    }
}
