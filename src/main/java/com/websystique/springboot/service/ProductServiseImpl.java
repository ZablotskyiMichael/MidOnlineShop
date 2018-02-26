package com.websystique.springboot.service;

import com.websystique.springboot.model.Product;
import com.websystique.springboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service("productService")
@Transactional
public class ProductServiseImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product findById(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public void saveProduct(Product product) {
    productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
    saveProduct(product);
    }

    @Override
    public void deleteProductById(Long id) {
    productRepository.delete(id);
    }

    @Override
    public void deleteAllProducts() {
    productRepository.deleteAll();
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean isProductExist(Product product) {
        return ((productRepository.findOne(product.getId()))!=null);
    }
}
