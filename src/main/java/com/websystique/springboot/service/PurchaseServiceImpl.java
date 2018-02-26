package com.websystique.springboot.service;

import com.websystique.springboot.model.Dictionary;
import com.websystique.springboot.model.Purchase;
import com.websystique.springboot.model.User;
import com.websystique.springboot.repositories.PurchaseRepository;
import com.websystique.springboot.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service("purchaseService")
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private int STATUS_NOT_ENOUGH_MONEY = 1;
    private int STATUS_NOT_ITEMS_IN_STOCK = 2;
    private int STATUS_SUCCESS = 3;

    @Autowired
    UserService userService;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    DictionaryService dictionaryService;
    @Autowired
    StorageService storageService;
    @Autowired
    PropertiesService propertiesService;
    @Override
    public Purchase findById(Long id) {
        return purchaseRepository.findOne(id);
    }

    @Override
    public void savePurchase(Purchase purchase) {
    purchaseRepository.save(purchase);
    }

    @Override
    public void updatePurchase(Purchase purchase) {
        savePurchase(purchase);
    }

    @Override
    public void deletePurchaseById(Long id) {
    purchaseRepository.delete(id);
    }

    @Override
    public void deleteAllPurchase() {
    purchaseRepository.deleteAll();
    }

    @Override
    public int buyBasked() {
       List<Purchase> basked = findPurchaseByUser("BASKED");
        for (Purchase purchase:basked) {
            int status = buyProduct(purchase);
            if (status==STATUS_NOT_ENOUGH_MONEY){
                return STATUS_NOT_ENOUGH_MONEY;
            }
            if (status==STATUS_NOT_ITEMS_IN_STOCK){
                return STATUS_NOT_ITEMS_IN_STOCK;
            }
        }
        return STATUS_SUCCESS;
    }

    private int buyProduct(Purchase purchase) {
        int discount = propertiesService.getCurrentDiscount(userService.getCurrentUser());
        Dictionary status =  dictionaryService.findByName("BOUGHT");
        if (storageService.findByProduct(purchase.getProduct()).getCount()<purchase.getCount()){
            return STATUS_NOT_ITEMS_IN_STOCK;
        }
        if (userService.getCurrentUser().getAccount()<purchase.getTotal_price()){
            return STATUS_NOT_ENOUGH_MONEY;
        }
            storageService.changeCount (storageService.findByProduct(purchase.getProduct()),purchase.getCount());
            userService.takeMoneyFromAccount (userService.findById(purchase.getUser().getId()),purchase.getTotal_price(),discount);
            purchase.setDictionary(status);
            purchaseRepository.save(purchase);
        return STATUS_SUCCESS;
       // storageService.changeCount (storageService.findByProduct(purchase.getProduct()),purchase.getCount());
       // userService.takeMoneyFromAccount (userService.findById(purchase.getUser().getId()),purchase.getTotal_price(),discount);
    }

    @Override
    public List<Purchase> findAllPurchase() {
        return purchaseRepository.findAll();
    }

    @Override
    public List<Purchase> findAllPurchaseByStatus(String status) {
        List<Purchase> allPurchases = findAllPurchase();
        List<Purchase> purchaseByStatus = new ArrayList<>();
        for (Purchase purchase:allPurchases) {
            if (purchase.getDictionary().getName().equals(status)){
                purchaseByStatus.add(purchase);
            }
        }
        return purchaseByStatus;
    }

    @Override
    public boolean isPurchaseExist(Purchase purchase) {
        return false;
    }

    @Override
    public List<Purchase> findPurchaseByUser(String status) {
     User  currentUser =  userService.getCurrentUser();
        List<Purchase> allPurchases = findAllPurchase();
        List<Purchase> purchaseByUser = new ArrayList<>();
        for (int i = 0; i < allPurchases.size(); i++) {
            if (allPurchases.get(i).getUser().equals(currentUser) && allPurchases.get(i).getDictionary().getName().equals(status)){
                purchaseByUser.add(allPurchases.get(i));
            }
        }
        return purchaseByUser;
    }

    /*@Override
    public User getCurrentUser (){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.findByName(name);
        return user;
    }
*/
}
