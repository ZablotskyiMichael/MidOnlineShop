package com.websystique.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springboot.model.Dictionary;
import com.websystique.springboot.model.User;
import com.websystique.springboot.service.DictionaryService;
import com.websystique.springboot.service.UserService;
import com.websystique.springboot.util.Mocks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RestApiController.class)
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class RestApiControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;
    @MockBean
    RestApiController restApiController;
    @MockBean
    UserService userService;
    @MockBean
    DictionaryService dictionaryService;
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void listAllUsers() throws Exception {
        mvc.perform(get("/admin/user/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void listAllRoles() throws Exception {
        mvc.perform(get("/admin/role/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser() throws Exception {
        mvc.perform(get("/admin/user/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getRole() throws Exception {
        mvc.perform(get("/admin/role/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUser() throws Exception {
        Mocks mocks = new Mocks();
        User user = mocks.mockUser("shoudUpdate", 1);
        byte[] userJson = toJson(user);

        mvc.perform(put("/admin/user/"+user.getId())
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateRole() throws Exception {
        Mocks mocks = new Mocks();
        Dictionary role = mocks.mockRole("shoudUpdate");
        byte[] roleJson = toJson(role);

        mvc.perform(put("/admin/role/"+role.getName())
                .content(roleJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        mvc.perform(get("/admin/user/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRole() throws Exception {
        mvc.perform(get("/admin/role/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}