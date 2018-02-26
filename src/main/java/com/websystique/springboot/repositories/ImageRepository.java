package com.websystique.springboot.repositories;

import com.websystique.springboot.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findByName (String name);
}
