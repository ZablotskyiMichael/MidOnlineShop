package com.websystique.springboot.controller;

import com.websystique.springboot.model.Storage;
import com.websystique.springboot.service.StorageService;
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
public class StorageController {
    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    StorageService storageService;

    // -------------------Retrieve All Users---------------------------------------------

    @RequestMapping(value = "/storage/", method = RequestMethod.GET)
    public ResponseEntity<List<Storage>> listAllStorages() {
        List<Storage> storages = storageService.findAllStorages();
        if (storages.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Storage>>(storages, HttpStatus.OK);
    }

    @RequestMapping(value = "/storage/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStorage(@PathVariable("id") long id) {
        logger.info("Fetching Storage with id {}", id);
        Storage storage = storageService.findById(id);
        if (storage == null) {
            logger.error("Storage with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Storage>(storage, HttpStatus.OK);
    }
    @RequestMapping(value = "/storage/", method = RequestMethod.POST)
    public ResponseEntity<?> createStorage(@RequestBody Storage storage, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Storage : {}", storage);

        if (storageService.isStorageExist(storage)) {
            logger.error("Unable to create. A Storage with id {} already exist", storage.getId());
            return new ResponseEntity(new CustomErrorType("Unable to create. A Storage with id " +
                    storage.getId() + " already exist."),HttpStatus.CONFLICT);
        }
        storageService.saveStorage(storage);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/storage/{id}").buildAndExpand(storage.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/storage/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStorage(@PathVariable("id") long id, @RequestBody Storage storage) {
        logger.info("Updating Storage with id {}", id);

        Storage currentStorage = storageService.findById(id);

        if (currentStorage == null) {
            logger.error("Unable to update. Storage with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        /*currentStorage.setProduct(storage.getProduct());*/
        currentStorage.setCount(currentStorage.getCount()+storage.getCount());

        storageService.updateStorage(currentStorage);
        return new ResponseEntity<Storage>(currentStorage, HttpStatus.OK);
    }

    @RequestMapping(value = "/storage/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStorage(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Storage with id {}", id);

        Storage storage = storageService.findById(id);
        if (storage == null) {
            logger.error("Unable to delete. Storage with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Storage with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        storageService.deleteStorageById(id);

        return new ResponseEntity<Storage>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/storage/", method = RequestMethod.DELETE)
    public ResponseEntity<Storage> deleteAllStorage() {
        logger.info("Deleting All Storage");

        storageService.deleteAllStorages();
        return new ResponseEntity<Storage>(HttpStatus.NO_CONTENT);
    }

}
