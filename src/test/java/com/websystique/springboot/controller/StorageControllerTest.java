package com.websystique.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springboot.model.Category;
import com.websystique.springboot.model.Product;
import com.websystique.springboot.model.Storage;
import com.websystique.springboot.service.CategoryService;
import com.websystique.springboot.service.StorageService;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(StorageController.class)
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class StorageControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;
   @MockBean
   StorageController storageController;
   @MockBean
    StorageService storageService;
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void storageControllerTestGetAll()
            throws Exception {
        mvc.perform(get("/user/storage/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void storageControllerTestGetOne()
            throws Exception {
        mvc.perform(get("/user/storage/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void storageControllerTestDelete()
            throws Exception {
        mvc.perform(get("/user/storage/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void storageControllerTestUpdate() throws Exception{
        Mocks mocks = new Mocks();
        Storage storage = mocks.mockStorage(100);
        byte[] categoryJson = toJson(storage);

        mvc.perform(put("/user/storage/"+storage.getId())
                .content(categoryJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}