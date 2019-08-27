package com.khobu.checkn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/test")
public class TestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);


    @GetMapping("/up")
	public Boolean up() {
        LOGGER.info("Getting up status");
		return true;
	}

}