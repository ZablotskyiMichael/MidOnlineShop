package com.websystique.springboot.service;

import com.websystique.springboot.model.HistoryOfVisits;
import com.websystique.springboot.model.Product;
import com.websystique.springboot.model.Properties;
import com.websystique.springboot.model.User;
import com.websystique.springboot.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("historyService")
@Transactional
public class HistoryServiceImpl implements HistoryService{

    private int MINUTE = 60;
    private int HOUR = MINUTE*60;
    private int DAY = HOUR*24;
    private int MONTH = DAY*31;
    private int YEAR = MONTH*12;
    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    PropertiesService propertiesService;
    private HistoryOfVisits lastVisit;

    @Override
    public List<HistoryOfVisits> getRecommendedHistoryByUser(User user) {
       Properties property =  propertiesService.findByName("countOfVisitsToRecommended");
        List<HistoryOfVisits> historyByUser = historyRepository.getByUserId(user);
        List<HistoryOfVisits> checkedHistories = new ArrayList<>();
        int maxCountFirst = 0;
        int maxCountSecond = 0;
        int maxCountThird = 0;
        int currentCount=0;
        HistoryOfVisits historyWithMaxVisitsFirst = new HistoryOfVisits();
        HistoryOfVisits historyWithMaxVisitsSecond = new HistoryOfVisits();
        HistoryOfVisits historyWithMaxVisitsThird = new HistoryOfVisits();
        for (int i = 0; i < historyByUser.size(); i++) {
            HistoryOfVisits history = historyByUser.get(i);
            if (!checkedHistories(checkedHistories,history)){
                checkedHistories.add(history);
                for(HistoryOfVisits currentHistory : historyByUser){

                    if (currentHistory.getProduct().getName().equals(history.getProduct().getName())){
                        currentCount++;
                    }
                }
                if (currentCount>maxCountFirst){
                    maxCountThird=maxCountSecond;
                    historyWithMaxVisitsThird=historyWithMaxVisitsSecond;
                    maxCountSecond=maxCountFirst;
                    historyWithMaxVisitsSecond=historyWithMaxVisitsFirst;
                    maxCountFirst=currentCount;
                    historyWithMaxVisitsFirst=history;
                }else if (currentCount>maxCountSecond){
                    maxCountThird=maxCountSecond;
                    historyWithMaxVisitsThird=historyWithMaxVisitsSecond;
                    maxCountSecond=currentCount;
                    historyWithMaxVisitsSecond=history;
                }else if(currentCount>maxCountThird){
                    maxCountThird=currentCount;
                    historyWithMaxVisitsThird=history;
             }
            }
            currentCount=0;
        }
        List<HistoryOfVisits> mostPopular = new ArrayList<>();
        if(maxCountFirst>=propertiesService.findByName("countOfVisitsToRecommended").getValue()){
                mostPopular.add(historyWithMaxVisitsFirst);
        }
        if(maxCountSecond>=propertiesService.findByName("countOfVisitsToRecommended").getValue()){
            mostPopular.add(historyWithMaxVisitsSecond);
        }
        if(maxCountThird>=propertiesService.findByName("countOfVisitsToRecommended").getValue()){
            mostPopular.add(historyWithMaxVisitsThird);
        }
        return mostPopular;
    }

    private boolean checkedHistories(List<HistoryOfVisits> checkedHistories , HistoryOfVisits current){
        if (checkedHistories.size()==0){
            return false;
        }
        for(HistoryOfVisits history:checkedHistories){
            if(history.getProduct().getName().equals(current.getProduct().getName())){
                return true;
            }
        }
        return false;
    }
    @Override
    public List<HistoryOfVisits> getHistoryByUser(User user) {
        return historyRepository.getByUserId(user);
    }

    @Override
    public void saveHistory(HistoryOfVisits history) {
        historyRepository.save(history);
    }

    @Override
    public void createHistory(Long product) {
        HistoryOfVisits historyOfVisits = new HistoryOfVisits();
        historyOfVisits.setTime(getCurrentTime());
        historyOfVisits.setProduct(productService.findById(product));
        historyOfVisits.setUserId(userService.getCurrentUser());
        saveHistory(historyOfVisits);

    }

    @Override
    public boolean isNewVisit(String currentTime) {
        List<HistoryOfVisits> history = historyRepository.findAll();
        if (history.size() == 0) {
            return true;
        }
        HistoryOfVisits lastVisit = getLastVisit(history);
        if (lastVisit==null){
            return true;
        }
        System.out.println("" + (getSeconds(lastVisit.getTime()) - getSeconds(currentTime)));
        if (Math.abs(getSeconds(lastVisit.getTime()) - getSeconds(currentTime)) > 3) {
            return true;
        }
        return false;
    }

    private String getCurrentTime(){
        java.util.Date javaDate = new java.util.Date();
        long javaTime = javaDate.getTime();

        return  new java.sql.Date(javaTime).toString() +" " +  new java.sql.Time(javaTime).toString();
    }

    private int getSeconds (String time){
        int second = Integer.valueOf(time.substring(time.length()-2,time.length()));
        int minute = Integer.valueOf(time.substring(time.length()-5,time.length()-3));
        int hour = Integer.valueOf(time.substring(time.length()-8,time.length()-6));
        int day = Integer.valueOf(time.substring(time.length()-11,time.length()-9));
        int month = Integer.valueOf(time.substring(time.length()-14,time.length()-12));
        int years = Integer.valueOf(time.substring(time.length()-19,time.length()-15));

        System.out.println(Integer.valueOf(time.substring(time.length()-2,time.length())));
       return  second+minute*MINUTE+hour*HOUR+day*DAY + month*MONTH +years*YEAR;
    }

    private HistoryOfVisits getLastVisit( List<HistoryOfVisits> history) {
        User user = userService.getCurrentUser();
        for (int i = history.size()-1; i >= 0 ; i--) {
            if (history.get(i).getUserId().getName().equals(user.getName())){
                return history.get(i);
            }
        }
        return null;
    }
}
