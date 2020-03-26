package com.kpn.eshop.rest.controllers;

import static com.kpn.eshop.constants.URLConstants.URL_HOME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kpn.eshop.model.Home;
import com.kpn.eshop.rest.resource.processors.HomeResourceProcessor;

@RestController
public class HomeController {

	private final HomeResourceProcessor homeResourceProcessor;

	@Autowired
	public HomeController(HomeResourceProcessor homeResourceProcessor) {
		this.homeResourceProcessor = homeResourceProcessor;
	}

	@RequestMapping(value = URL_HOME, method = RequestMethod.GET, produces = "application/hal+json")
	public HttpEntity<Resource<Home>> landHome() {
		Home home = new Home();
		Resource<Home> homeResource = new Resource<>(home);
		return new ResponseEntity<>(homeResourceProcessor.process(homeResource),
				HttpStatus.OK);
	}
}
