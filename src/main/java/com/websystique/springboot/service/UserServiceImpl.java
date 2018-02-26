package com.websystique.springboot.service;

import com.websystique.springboot.model.Dictionary;
import com.websystique.springboot.model.User;
import com.websystique.springboot.repositories.DictionaryRepository;
import com.websystique.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DictionaryRepository dictionaryRepository;

	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public void updateUser(User user){
		saveUser(user);
	}

	public void deleteUserById(Long id){
		userRepository.delete(id);
	}

	public void deleteAllUsers(){
		userRepository.deleteAll();
	}

	public List<User> findAllUsers(){
		return userRepository.findAll();
	}
	public void sayHello (){
		System.out.println("hello");
	}

	public boolean isUserExist(User user) {
		return findByName(user.getName()) != null;
	}

	public User registerNewUserAccount(UserDto accountDto){
		/*if (nameExist(accountDto.getName())) {
			throw new IllegalArgumentException();
		}*/
		User user = new User();
		user.setName(accountDto.getName());
		user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		user.setEnabled(1);
		if (accountDto.getParent_name()!=null){
			user.setParent_id(userRepository.findByName(accountDto.getParent_name()).getId());
		}
		Dictionary role = dictionaryRepository.findByName("ROLE_USER");
		if (role == null){
			role = new Dictionary() ;
			role.setName("ROLE_USER");
			dictionaryRepository.save(role);
		}
		user.setRole(role);

		return userRepository.save(user);
	}

	public boolean nameExist(String name) {
		User user = userRepository.findByName(name);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public void takeMoneyFromAccount(User user, double total_price,int discount) {
		double discountValue = total_price*discount/100;
		double userAccount = user.getAccount()-total_price+discountValue;
		String formattedDouble =  new DecimalFormat("#0.00").format(userAccount);

		user.setAccount(Double.valueOf(formattedDouble.replace(',','.')));
		userRepository.save(user);
	}

	@Override
	public User getCurrentUser (){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		User user = findByName(name);
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = userRepository.findByName(name);
		if (user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getName(),
				user.getPassword(),
				mapRolesToAuthorities(getRoles(user)));
	}
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Dictionary> roles){
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}

	private Collection<Dictionary> getRoles (User user){
		Collection<Dictionary> roles = new HashSet<>();
		roles.add(user.getRole());
		return roles;
	}

}
