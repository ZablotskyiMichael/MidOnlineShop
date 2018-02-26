package com.websystique.springboot.service;

import com.websystique.springboot.model.Product;
import com.websystique.springboot.model.Storage;
import com.websystique.springboot.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("storageService")
@Transactional
public class StorageServiceImpl implements StorageService {

    @Autowired
    StorageRepository storageRepository;

    @Override
    public Storage findById(Long id) {
        return storageRepository.findById(id);
    }

    @Override
    public Storage findByProduct(Product product) {
        return storageRepository.findByProduct(product);
    }

    @Override
    public void saveStorage(Storage storage) {
    storageRepository.save(storage);
    }

    @Override
    public void updateStorage(Storage storage) {
        saveStorage(storage);
    }

    @Override
    public void deleteStorageById(Long id) {
    storageRepository.delete(id);
    }

    @Override
    public void deleteAllStorages() {
    storageRepository.deleteAll();
    }

    @Override
    public List<Storage> findAllStorages() {
        return storageRepository.findAll();
    }

    @Override
    public boolean isStorageExist(Storage storage) {
        return (storageRepository.findOne(storage.getId())!=null);
    }

    @Override
    public void changeCount(Storage storage, int count) {
        storage.setCount(storage.getCount()-count);
        updateStorage(storage);
    }
}
