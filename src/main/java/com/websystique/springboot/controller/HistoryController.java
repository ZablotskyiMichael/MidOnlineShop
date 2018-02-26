package com.websystique.springboot.controller;

import com.websystique.springboot.model.HistoryOfVisits;
import com.websystique.springboot.model.User;
import com.websystique.springboot.repositories.UserRepository;
import com.websystique.springboot.service.HistoryService;
import com.websystique.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class HistoryController {
    @Autowired
    HistoryService historyService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/history/",method = RequestMethod.GET)
    public ResponseEntity<List<HistoryOfVisits>> getRecommendedHistoryByUser(){
        User currentUser = userService.getCurrentUser();
        if (currentUser==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<HistoryOfVisits> history = historyService.getRecommendedHistoryByUser(currentUser);
        return new ResponseEntity<List<HistoryOfVisits>>(history,HttpStatus.OK);
    }
}
