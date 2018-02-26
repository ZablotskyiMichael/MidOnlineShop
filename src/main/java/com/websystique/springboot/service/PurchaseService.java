package com.websystique.springboot.service;

import com.websystique.springboot.model.Purchase;
import com.websystique.springboot.model.User;

import java.util.List;

public interface PurchaseService {
    Purchase findById(Long id);

    void savePurchase(Purchase purchase);

    void updatePurchase(Purchase purchase);

    void deletePurchaseById(Long id);

    void deleteAllPurchase();

    int buyBasked();

    List<Purchase> findAllPurchase();

    List<Purchase> findAllPurchaseByStatus(String status);
/*
    List<Purchase> findAllPurchase(Long id);*/

    boolean isPurchaseExist(Purchase purchase);

    List<Purchase> findPurchaseByUser (String status);

//    User getCurrentUser();


}