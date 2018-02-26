package com.websystique.springboot.service;

import com.websystique.springboot.model.HistoryOfVisits;
import com.websystique.springboot.model.Product;
import com.websystique.springboot.model.User;

import java.util.List;

public interface HistoryService {

    List<HistoryOfVisits> getRecommendedHistoryByUser(User user);

    List<HistoryOfVisits> getHistoryByUser(User user);

    void saveHistory (HistoryOfVisits history);

    void createHistory(Long product);

    boolean isNewVisit(String currentTime);
}
