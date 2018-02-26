package com.websystique.springboot.service;

import com.websystique.springboot.model.Properties;
import com.websystique.springboot.model.User;

import java.util.List;

public interface PropertiesService {
    Properties findByName(String name);
    Properties findById(Long id);
    List<Properties> findAllProperties();
    void updateProperty (Properties property);
    void save (Properties property);
    int getCurrentDiscount(User user);
    Properties getRelatedDiscount();
}
