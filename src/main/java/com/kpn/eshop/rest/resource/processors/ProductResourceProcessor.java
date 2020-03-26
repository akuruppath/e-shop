package com.kpn.eshop.rest.resource.processors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import com.kpn.eshop.constants.RelationConstants;
import com.kpn.eshop.model.Customer;
import com.kpn.eshop.model.Product;
import com.kpn.eshop.rest.controllers.CartController;
import com.kpn.eshop.rest.controllers.ProductCategoryController;
import com.kpn.eshop.rest.controllers.ProductController;

@Component
public class ProductResourceProcessor implements ResourceProcessor<Resource<Product>> {

	@Override
	public Resource<Product> process(Resource<Product> resource) {
		Product product = resource.getContent();
		String categoryId = product.getCategory().getId();
		resource.add(linkTo(methodOn(ProductController.class).getProduct(categoryId, product.getId())).withSelfRel());
		resource.add(linkTo(methodOn(ProductCategoryController.class).getProductCategoryById(categoryId))
				.withRel(RelationConstants.REL_CATEGORY));
		return resource;
	}

	public Resource<Product> process(Resource<Product> resource, Customer customer) {
		Resource<Product> productResource = process(resource);
		resource.add(linkTo(
				methodOn(CartController.class).addProductToCart(customer.getId(), productResource.getContent().getId()))
				.withRel(RelationConstants.REL_ADD));
		return resource;
	}

}
