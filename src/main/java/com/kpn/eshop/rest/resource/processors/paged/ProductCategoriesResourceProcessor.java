package com.kpn.eshop.rest.resource.processors.paged;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import com.kpn.eshop.model.ProductCategory;
import com.kpn.eshop.rest.resource.processors.ProductCategoryResourceProcessor;

@Component
public class ProductCategoriesResourceProcessor implements ResourceProcessor<Resources<Resource<ProductCategory>>> {

	private final ProductCategoryResourceProcessor productCategoryResourceProcessor;

	@Autowired
	public ProductCategoriesResourceProcessor(ProductCategoryResourceProcessor productCategoryResourceProcessor) {
		this.productCategoryResourceProcessor = productCategoryResourceProcessor;
	}

	@Override
	public Resources<Resource<ProductCategory>> process(Resources<Resource<ProductCategory>> resource) {
		resource.forEach(productCategoryResource -> productCategoryResourceProcessor.process(productCategoryResource));
		return resource;
	}
}
