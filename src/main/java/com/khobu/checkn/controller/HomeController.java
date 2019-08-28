package com.khobu.checkn.controller;


import com.khobu.checkn.domain.HelloResponse;
import com.khobu.checkn.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class HomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserService userService;

    @GetMapping("/test")
	String test() {
		LOGGER.info("Getting test");
    	return "The controller is working";
	}

	@GetMapping(path = "/hello-world", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HelloResponse> helloWorld(){
    	LOGGER.info("running hello world");
		return new ResponseEntity<HelloResponse>(new HelloResponse("Hello World"), HttpStatus.OK);
	}

}
