package com.websystique.springboot.controller;

import com.websystique.springboot.model.Properties;
import com.websystique.springboot.service.PropertiesService;
import com.websystique.springboot.service.UserService;
import com.websystique.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class PropertiesController {
    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
@Autowired
    UserService userService;
    @Autowired
    PropertiesService propertiesService;
    @RequestMapping(value = "/properties/", method = RequestMethod.GET)
    public ResponseEntity<List<Properties>> listAllStorages() {
        List<Properties> properties = propertiesService.findAllProperties();
        if (properties.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Properties>>(properties, HttpStatus.OK);
    }
    @RequestMapping(value = "/discount/", method = RequestMethod.GET)
    public ResponseEntity<Properties> currentDiscount() {
       int discount = propertiesService.getCurrentDiscount(userService.getCurrentUser());
       Properties properties = new Properties();
       properties.setValue(discount);
       properties.setName("currentDiscount");
        return new ResponseEntity<Properties>(properties, HttpStatus.OK);
    }
    @RequestMapping(value = "/rdiscount/", method = RequestMethod.GET)
    public ResponseEntity<Properties> relatedDiscount() {
        Properties relatedDiscount = propertiesService.getRelatedDiscount();
        return new ResponseEntity<Properties>(relatedDiscount, HttpStatus.OK);
    }
    @RequestMapping(value = "/properties/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProperty(@PathVariable("id") long id) {
        logger.info("Fetching Properties with id {}", id);
        Properties property = propertiesService.findById(id);
        if (property == null) {
            logger.error("Property with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Property with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Properties>(property, HttpStatus.OK);
    }
    @RequestMapping(value = "/properties/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProperty(@PathVariable("id") long id, @RequestBody Properties property) {
        logger.info("Updating Product with id {}", id);

        Properties currentProperty = propertiesService.findById(id);

        if (currentProperty == null) {
            logger.error("Unable to update. Property with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Property with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        currentProperty.setValue(property.getValue());
        propertiesService.updateProperty(currentProperty);
        return new ResponseEntity<Properties>(currentProperty, HttpStatus.OK);
    }
}
