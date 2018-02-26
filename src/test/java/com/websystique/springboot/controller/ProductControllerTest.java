package com.websystique.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springboot.model.*;
import com.websystique.springboot.service.ProductService;
import com.websystique.springboot.service.PurchaseService;
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
@WebMvcTest(ProductController.class)
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class ProductControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;
    @MockBean
    ProductController productController;
    @MockBean
    PurchaseService purchaseService;
    @MockBean
    ProductService productService;
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void listAllProducts() throws Exception {
        mvc.perform(get("/user/product/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void listAllPurchase() throws Exception {
        mvc.perform(get("/user/purchase/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getProduct() throws Exception {
        mvc.perform(get("/user/product/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPurchase() throws Exception {
        mvc.perform(get("/user/purchase/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void updateProduct() throws Exception {
        Mocks mocks = new Mocks();
        Product product = mocks.mockProduct("shoudUpdate", 1);
        byte[] productJson = toJson(product);

        mvc.perform(put("/user/product/"+product.getId())
                .content(productJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePurchase() throws Exception {
        Mocks mocks = new Mocks();
        Purchase purchase = mocks.mockPurchase("Update",100);
        byte[] purchaseJson = toJson(purchase);

        mvc.perform(put("/user/purchase/"+purchase.getId())
                .content(purchaseJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteProduct() throws Exception {
        mvc.perform(get("/user/product/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePurchase() throws Exception {
        mvc.perform(get("/user/purchase/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}