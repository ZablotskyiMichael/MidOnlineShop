package com.websystique.springboot.repositories;

import com.websystique.springboot.model.HistoryOfVisits;
import com.websystique.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryOfVisits,Long> {
    List<HistoryOfVisits> getByUserId(User user);
}
