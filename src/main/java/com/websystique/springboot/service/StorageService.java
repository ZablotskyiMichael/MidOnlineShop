package com.websystique.springboot.service;


import com.websystique.springboot.model.Product;
import com.websystique.springboot.model.Storage;

import java.util.List;

public interface StorageService {

    Storage findById(Long id);

    Storage findByProduct (Product product);

    void saveStorage(Storage storage);

    void updateStorage(Storage storage);

    void deleteStorageById(Long id);

    void deleteAllStorages();

    List<Storage> findAllStorages();

    boolean isStorageExist(Storage storage);

    void changeCount(Storage storage, int count);
}
