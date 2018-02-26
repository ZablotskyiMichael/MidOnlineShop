package com.websystique.springboot.service;

import com.websystique.springboot.model.Image;
import com.websystique.springboot.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("imageService")
@Transactional
public class ImageServiceImpl implements ImageService{
    @Autowired
    ImageRepository imageRepository;
    @Override
    public Image findByName(String name) {
        return imageRepository.findByName(name);
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findOne(id);
    }

    @Override
    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    @Override
    public List<Image> findAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public boolean isImageExist(Image image) {
        return (imageRepository.findOne(image.getId())!=null);
    }
}
