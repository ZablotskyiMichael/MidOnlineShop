package com.websystique.springboot.controller;


import com.websystique.springboot.model.Message;
import com.websystique.springboot.service.MessageService;
import com.websystique.springboot.service.UserService;
import com.websystique.springboot.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/user")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> listAllMessages(@PathVariable("id") long id) {
        List<Message> messages = messageService.findAllMessageByProduct(id);
        if (messages.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }

    @RequestMapping(value = "/message/parent/", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> listParentMessages(){
        List<Message> parentMessages = messageService.findAllParentMessage();
        if(parentMessages.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Message>>(parentMessages,HttpStatus.OK);
    }

    @RequestMapping(value = "/message/comments/{id}",method = RequestMethod.GET)
    public ResponseEntity<List<Message>> listCommentsForMessage (@PathVariable("id") long id){
        List<Message> comments  = messageService.findByParent(id);
        if(comments.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Message>>(comments,HttpStatus.OK);
    }

    @RequestMapping(value = "/message/", method = RequestMethod.POST)
    public ResponseEntity<?> createStorage(@RequestBody Message message, UriComponentsBuilder ucBuilder) {
        java.util.Date javaDate = new java.util.Date();
        long javaTime = javaDate.getTime();

        message.setTime(new java.sql.Date(javaTime).toString() +" " +  new java.sql.Time(javaTime).toString());
        message.setUser(userService.getCurrentUser());
        messageService.saveMessage(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/message/{id}").buildAndExpand(message.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}
