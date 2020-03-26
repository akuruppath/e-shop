package com.kpn.eshop.rest.resource.processors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import com.kpn.eshop.constants.RelationConstants;
import com.kpn.eshop.model.Home;
import com.kpn.eshop.rest.controllers.HomeController;
import com.kpn.eshop.rest.controllers.ProductCategoryController;

@Component
public class HomeResourceProcessor implements ResourceProcessor<Resource<Home>> {

	@Override
	public Resource<Home> process(Resource<Home> resource) {
		resource.add(linkTo(methodOn(HomeController.class).landHome()).withSelfRel());
		resource.add(linkTo(methodOn(ProductCategoryController.class).getAllProductCategories())
				.withRel(RelationConstants.REL_PRODUCT_CATEGORIES));
		return resource;
	}

}
