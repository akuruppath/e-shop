package com.kpn.eshop.rest.resource.processors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import com.kpn.eshop.constants.RelationConstants;
import com.kpn.eshop.model.ProductCategory;
import com.kpn.eshop.rest.controllers.ProductCategoryController;
import com.kpn.eshop.rest.controllers.ProductController;

@Component
public class ProductCategoryResourceProcessor implements ResourceProcessor<Resource<ProductCategory>> {

	@Override
	public Resource<ProductCategory> process(Resource<ProductCategory> resource) {
		ProductCategory category = resource.getContent();
		String categoryId = category.getId();
		resource.add(
				linkTo(methodOn(ProductCategoryController.class).getProductCategoryById(categoryId)).withSelfRel());
		resource.add(linkTo(methodOn(ProductController.class).getAllProducts(categoryId))
				.withRel(RelationConstants.REL_PRODUCTS));
		return resource;
	}

}
