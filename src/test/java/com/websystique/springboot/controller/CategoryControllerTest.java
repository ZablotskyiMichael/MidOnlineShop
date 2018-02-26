package com.websystique.springboot.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websystique.springboot.repositories.CategoryRepository;
import com.websystique.springboot.service.CategoryService;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.websystique.springboot.model.Category;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(CategoryController.class)
@WithMockUser(username="admin",roles={"USER","ADMIN"})
public class CategoryControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private  CategoryController categoryController;
    @MockBean
    private CategoryService categoryService;
   @Before
   public void setup() {
       mvc = MockMvcBuilders
               .webAppContextSetup(context)
               .apply(springSecurity())
               .build();
   }
   @Test
   public void CategoryControllerTestGetAll()
           throws Exception {
       mvc.perform(get("/user/category/")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
   }
    @Test
    public void CategoryControllerTestGetOne()
            throws Exception {

        Category category = new Category();
        category.setName("Smart");
        category.setId(1);

        given(categoryService.findById(1L)).willReturn(category);

        mvc.perform(get("/user/category/"+category.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void CategoryControllerTestDelete()
            throws Exception {



        mvc.perform(get("/user/category/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void CategoryControllerTestCreate() throws Exception {
        Mocks mocks = new Mocks();
        Category category = mocks.mockCategory("shouldCreate");
        byte[] categoryJson = toJson(category);
         mvc.perform(post("/user/category/")
                .content(categoryJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

}
    @Test
    public void CategoryControllerTestUpdate() throws Exception{
        Mocks mocks = new Mocks();
       Category category = mocks.mockCategory("shoudApdate");
       byte[] categoryJson = toJson(category);

       mvc.perform(put("/user/category/"+category.getId())
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