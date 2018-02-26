package com.websystique.springboot.service;

import com.websystique.springboot.model.Image;

import java.util.List;

public interface ImageService {
    Image findByName(String name);

    Image findById(Long id);

    void saveImage(Image image);

    List<Image> findAllImages();

    boolean isImageExist(Image image);
}
