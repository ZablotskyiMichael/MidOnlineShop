package com.websystique.springboot.repositories;

import com.websystique.springboot.model.Product;
import com.websystique.springboot.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage,Long> {
    Storage findById (Long id);
    Storage findByProduct (Product product);
}
