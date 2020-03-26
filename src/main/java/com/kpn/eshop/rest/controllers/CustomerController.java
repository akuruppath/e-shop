package com.kpn.eshop.rest.controllers;

import static com.kpn.eshop.constants.URLConstants.CUSTOMER_ID;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kpn.eshop.constants.URLConstants;
import com.kpn.eshop.model.Customer;
import com.kpn.eshop.repositories.CustomerRepository;
import com.kpn.eshop.rest.resource.processors.CustomerResourceProcessor;
import com.kpn.eshop.rest.resource.processors.paged.CustomerResourcesProcessor;

@RestController
public class CustomerController {

	private final CustomerRepository customerRepository;
	private final CustomerResourceProcessor customerResourceProcessor;
	private final CustomerResourcesProcessor customerResourcesProcessor;

	@Autowired
	public CustomerController(CustomerRepository customerRepository,
			CustomerResourceProcessor customerResourceProcessor,
			CustomerResourcesProcessor customerResourcesProcessor) {
		this.customerRepository = customerRepository;
		this.customerResourceProcessor = customerResourceProcessor;
		this.customerResourcesProcessor = customerResourcesProcessor;
	}

	@RequestMapping(value = URLConstants.URL_CUSTOMERS, method = RequestMethod.GET, produces = "application/hal+json")
	public HttpEntity<Resources<Resource<Customer>>> getCustomers() {
		return new ResponseEntity<>(customerResourcesProcessor.process(Resources.wrap(customerRepository.findAll())),
				HttpStatus.OK);
	}

	@RequestMapping(value = URLConstants.URL_CUSTOMER, method = RequestMethod.GET, produces = "application/hal+json")
	public HttpEntity<Resource<Customer>> getCustomer(@PathVariable(CUSTOMER_ID) String customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (customer.isPresent()) {
			return new ResponseEntity<>(customerResourceProcessor.process(new Resource<>(customer.get())),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
