package com.websystique.springboot.service;

import com.websystique.springboot.model.Message;
import com.websystique.springboot.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public List<Message> findAllParentMessage() {
        List<Message> allMessage = messageRepository.findAll();
        List<Message> parentMessage = new ArrayList<>();
        for (Message massege :
                allMessage) {
            if (massege.getParentId() == 0) {
                parentMessage.add(massege);
            }
        }
        return parentMessage;
    }

    @Override
    public List<Message> findByParent(Long parentId) {
        return messageRepository.findByParentId(parentId);
    }

    @Override
    public List<Message> findAllMessageByProduct(Long id) {
        List<Message> parentMessage = findAllParentMessage();
        List<Message> sortAllMessage = new ArrayList<>();
        for (Message message : parentMessage) {
            if (message.getProduct().getId()==id){
            sortAllMessage.add(message);
            List<Message> comments = messageRepository.findByParentId(message.getId());
            sortAllMessage.addAll(comments);}
        }
        return sortAllMessage;
    }

}
