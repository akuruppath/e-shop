package com.kpn.eshop.rest.resource.processors.paged;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import com.kpn.eshop.model.Customer;
import com.kpn.eshop.rest.resource.processors.CustomerResourceProcessor;

@Component
public class CustomerResourcesProcessor implements ResourceProcessor<Resources<Resource<Customer>>> {

	private final CustomerResourceProcessor customerResourceProcessor;

	@Autowired
	public CustomerResourcesProcessor(CustomerResourceProcessor customerResourceProcessor) {
		this.customerResourceProcessor = customerResourceProcessor;
	}

	@Override
	public Resources<Resource<Customer>> process(Resources<Resource<Customer>> resource) {
		resource.forEach(customerResource -> customerResourceProcessor.process(customerResource));
		return resource;
	}

}
