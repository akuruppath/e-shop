package com.kpn.eshop.rest.resource.processors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import com.kpn.eshop.constants.RelationConstants;
import com.kpn.eshop.model.Customer;
import com.kpn.eshop.rest.controllers.CartController;
import com.kpn.eshop.rest.controllers.CustomerController;

@Component
public class CustomerResourceProcessor implements ResourceProcessor<Resource<Customer>> {

	@Override
	public Resource<Customer> process(Resource<Customer> resource) {
		Customer customer = resource.getContent();
		String customerId = customer.getId();
		resource.add(linkTo(methodOn(CustomerController.class).getCustomer(customerId)).withSelfRel());
		resource.add(linkTo(methodOn(CartController.class).getProductsInCart(customerId))
				.withRel(RelationConstants.REL_CART));
		return resource;
	}

}
