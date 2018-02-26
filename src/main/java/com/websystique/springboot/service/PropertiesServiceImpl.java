package com.websystique.springboot.service;

import com.websystique.springboot.model.Properties;
import com.websystique.springboot.model.User;
import com.websystique.springboot.repositories.PropertiesRepository;
import com.websystique.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("propertiesService")
@Transactional
public class PropertiesServiceImpl implements PropertiesService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PropertiesRepository propertiesRepository;
    @Override
    public Properties findByName(String name) {
        return propertiesRepository.findByName(name);
    }

    @Override
    public Properties findById(Long id) {
        return propertiesRepository.findById(id);
    }

    @Override
    public List<Properties> findAllProperties() {
        return propertiesRepository.findAll();
    }

    @Override
    public void updateProperty(Properties property) {
        save(property);
    }

    @Override
    public void save(Properties property) {
        propertiesRepository.save(property);
    }

    @Override
    public int getCurrentDiscount(User user) {
        int maxDepth = getDepthOfInvitedUsers(0,0,user);
        return findByName("discount").getValue()*maxDepth;
    }

    @Override
    public Properties getRelatedDiscount() {
        return findByName("relatedDiscount");
    }

    private int getDepthOfInvitedUsers(int count , int max , User user ) {
       List<User> users =  userRepository.findByParentId(user.getId());
       if(users!=null && users.size()>0){
           if (++count>max){
               max++;
           }
        for (int i = 0; i < users.size(); i++) {
           int depth =  getDepthOfInvitedUsers(count , max , users.get(i));
           if (max<depth){
               max=depth;
           }
        }
       }
       return max;
    }
}
