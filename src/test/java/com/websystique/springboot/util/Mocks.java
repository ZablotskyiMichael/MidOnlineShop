package com.websystique.springboot.util;

import com.websystique.springboot.model.*;

public class Mocks {

    public Purchase mockPurchase(String shoudUpdate,int number) {
        Purchase purchase = new Purchase();
        purchase.setId(1L);
        purchase.setTotal_price(number);
        purchase.setCount(number);
        purchase.setUser(mockUser("update",1));
        purchase.setProduct(mockProduct("update",100));
        return purchase;
    }

    public Storage mockStorage(int count) {
        Storage s = new Storage();
        s.setCount(count);
        s.setProduct(mockProduct("update",100));
        return s;
    }
    public Product mockProduct(String prefix , int number) {
        Product product = new Product();
        product.setId(1L);
        product.setName(prefix+"_product");
        product.setPrice(number);
        product.setCategory(mockCategory("mock"));
        return product;
    }

    public Category mockCategory(String prefix) {
        Category category = new Category ();
        category.setId(1L);
        category.setName(prefix+"_category");
        return category;
    }
    public User mockUser(String prefix , int number) {
        User user = new User();
        user.setPassword(String.valueOf(number));
        user.setName(prefix + "_user");
        user.setEnabled(number);
        user.setRole(mockRole(prefix));
        user.setId(1L);
        return user;
    }

    public Dictionary mockRole(String prefix) {
        Dictionary role = new Dictionary();
        role.setDictionary_id(1L);
        role.setName(prefix+"_dictionary");
        return role;
    }
}
