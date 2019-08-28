package com.khobu.checkn.controller;


import com.khobu.checkn.annotation.IsAdmin;
import com.khobu.checkn.domain.Credential;
import com.khobu.checkn.domain.User;
import com.khobu.checkn.service.CredentialService;
import com.khobu.checkn.service.UserService;
import com.khobu.checkn.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CredentialService credentialService;

	@Autowired
    private SessionService sessionService;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		LOGGER.info("getting all users");
    	List<User> results = userService.retrieveAllUsers();
    	return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		LOGGER.info("creating user");
    	User result = userService.saveUser(user);
    	LOGGER.info("creating temporary password for user");
		Credential credential = new Credential();
		credential.setUsername(result.getUsername());
		credential.setPassword(credentialService.generateRandomPassword());
		credential.setActive(true);
		credentialService.saveCredential(credential);
		LOGGER.info("creating username {} and password {}",credential.getUsername(), credential.getPassword());
    	return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> findUser(@PathVariable Long id) {
		LOGGER.info("finding user");
		User result = userService.findUserById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@IsAdmin
	@PutMapping("/users/{id}")
	public ResponseEntity<User> replaceUser(@RequestBody User newUser, @PathVariable Long id) {
		LOGGER.info("replacing user");
    	User result = userService.updateUser(newUser, id);
    	return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@IsAdmin
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
		LOGGER.info("deleting user");
		boolean result = userService.deleteUser(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
