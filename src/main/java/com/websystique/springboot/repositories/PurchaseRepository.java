package com.websystique.springboot.repositories;

import com.websystique.springboot.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    Purchase findById (Long id);
}
