package com.websystique.springboot.controller;

import com.websystique.springboot.model.Dictionary;
import com.websystique.springboot.model.User;
import com.websystique.springboot.service.DictionaryService;
import com.websystique.springboot.service.UserService;
import com.websystique.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	UserService userService; //Service which will do all data retrieval/manipulation work
	@Autowired
	DictionaryService dictionaryService;

	// -------------------Retrieve All Users---------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		/*if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}*/
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	@RequestMapping(value = "/role/", method = RequestMethod.GET)
	public ResponseEntity<List<Dictionary>> listAllRoles() {
		List<Dictionary> roles = dictionaryService.findAllDictionariesByDiscriminator("ROLES");
		/*if (roles.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}*/
		return new ResponseEntity<List<Dictionary>>(roles, HttpStatus.OK);
	}

	// -------------------Retrieve Single User------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		/*if (user == null) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}*/
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getRole(@PathVariable("id") long id) {
		logger.info("Fetching Dictionary with id {}", id);
		Dictionary role = dictionaryService.findById(id, "ROLES");
		/*if (role == null) {
			logger.error("Dictionary with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id
					+ " not found"), HttpStatus.NOT_FOUND);
		}*/
		return new ResponseEntity<Dictionary>(role, HttpStatus.OK);
	}


	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);

		if (userService.isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			user.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		user.setId(null);
		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/role/", method = RequestMethod.POST)
	public ResponseEntity<?> createRole(@RequestBody Dictionary role, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Dictionary : {}", role);

		if (dictionaryService.isDictionaryExist((role))) {
			logger.error("Unable to create. A Dictionary with name {} already exist", role.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
					role.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		role.setDiscriminator("ROLES");
		dictionaryService.saveDictionary(role);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/role/{id}").buildAndExpand(role.getDictionary_id()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User ------------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Updating User with id {}", id);

		User currentUser = userService.findById(id);

		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentUser.setName(user.getName());
		currentUser.setPassword(user.getPassword());
		currentUser.setEnabled(user.getEnabled());
		currentUser.setRole(user.getRole());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}
	@RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateRole(@PathVariable("id") long id, @RequestBody Dictionary role) {
		logger.info("Updating Dictionary with id {}", id);

		Dictionary currentRole = dictionaryService.findById(id,"ROLES");

		if (currentRole == null) {
			logger.error("Unable to update. Dictionary with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to update. Dictionary with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentRole.setDictionary_id(role.getDictionary_id());
		currentRole.setName(role.getName());
		currentRole.setDiscriminator("ROLES");

		dictionaryService.updateDictionary(currentRole);
		return new ResponseEntity<Dictionary>(currentRole, HttpStatus.OK);
	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		User user = userService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRole(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Dictionary with id {}", id);

		Dictionary role = dictionaryService.findById(id,"ROLES");
		if (role == null) {
			logger.error("Unable to delete. Dictionary with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Dictionary with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		dictionaryService.deleteDictionaryById(id);
		return new ResponseEntity<Dictionary>(HttpStatus.NO_CONTENT);
	}
	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		logger.info("Deleting All Users");

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	@RequestMapping(value = "/role/", method = RequestMethod.DELETE)
	public ResponseEntity<Dictionary> deleteAllRoles() {
		logger.info("Deleting All Roles");

		dictionaryService.deleteAllDictionaries();
		return new ResponseEntity<Dictionary>(HttpStatus.NO_CONTENT);
	}

}