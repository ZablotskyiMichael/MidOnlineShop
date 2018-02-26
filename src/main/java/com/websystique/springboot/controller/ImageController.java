package com.websystique.springboot.controller;

import com.websystique.springboot.model.Image;
import com.websystique.springboot.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class ImageController {

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/image/", method = RequestMethod.GET)
    public ResponseEntity<List<Image>> listAllImages() {
        List<Image> images = imageService.findAllImages();
        if (images.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Image>>(images, HttpStatus.OK);
    }


}
