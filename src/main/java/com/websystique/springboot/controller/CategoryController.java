package com.websystique.springboot.controller;

import com.websystique.springboot.model.Category;
import com.websystique.springboot.service.CategoryService;
import com.websystique.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/user")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @RequestMapping(value = "/category/", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> listAllCategory() {
        List<Category> category = categoryService.findAllCategory();
        /*if (category.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }*/
        return new ResponseEntity<List<Category>>(category, HttpStatus.OK);
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCategory(@PathVariable("id") long id) {
        logger.info("Fetching Category with id {}", id);
        Category category = categoryService.findById(id);
        if (category == null) {
            logger.error("Category with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Category with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }
    @RequestMapping(value = "/category/", method = RequestMethod.POST)
    public ResponseEntity<?> createCategory(@RequestBody Category category, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Category : {}", category);

        if (categoryService.isCategorytExist(category)) {
            logger.error("Unable to create. A category with id {} already exist", category.getId());
            return new ResponseEntity(new CustomErrorType("Unable to create. A Storage with id " +
                    category.getId() + " already exist."),HttpStatus.CONFLICT);
        }
        categoryService.saveCategory(category);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/category/{id}").buildAndExpand(category.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategory(@PathVariable("id") long id, @RequestBody Category category) {
        logger.info("Updating Category with id {}", id);

        Category currentCategory = categoryService.findById(id);

        if (currentCategory == null) {
            logger.error("Unable to update. Category with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Category with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentCategory.setName(category.getName());

        categoryService.updateCategory(currentCategory);
        return new ResponseEntity<Category>(currentCategory, HttpStatus.OK);
    }


    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCategory(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Category with id {}", id);

        Category category = categoryService.findById(id);
        if (category == null) {
            logger.error("Unable to delete. category with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. category with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        categoryService.deleteCategoryById(id);

        return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
    }


}



