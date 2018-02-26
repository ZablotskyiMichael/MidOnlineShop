package com.websystique.springboot.service;


import com.websystique.springboot.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService{
	
	User findById(Long id);

	User findByName(String name);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserById(Long id);

	void deleteAllUsers();

	List<User> findAllUsers();

	boolean isUserExist(User user);

	User registerNewUserAccount(UserDto accountDto) ;

	 boolean nameExist(String name);
	void sayHello();
	 User getCurrentUser();
	void takeMoneyFromAccount(User user, double total_price,int discount);
}