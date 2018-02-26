package com.websystique.springboot.repositories;

import com.websystique.springboot.model.Properties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesRepository extends JpaRepository<Properties,Long> {
    Properties findByName(String name) ;
    Properties findById(Long id);
}
