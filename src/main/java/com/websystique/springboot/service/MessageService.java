package com.websystique.springboot.service;

import com.websystique.springboot.model.Message;

import java.util.List;

public interface MessageService {

    void saveMessage (Message message);

    List<Message> findAllParentMessage ();

    List<Message> findByParent (Long parent_id);

    List<Message> findAllMessageByProduct(Long id);

}
