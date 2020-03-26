package com.kpn.eshop.rest.resource.processors.paged;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

import com.kpn.eshop.model.Product;
import com.kpn.eshop.rest.resource.processors.ProductResourceProcessor;

@Component
public class ProductResourcesProcessor implements ResourceProcessor<Resources<Resource<Product>>> {

	private final ProductResourceProcessor productResourceProcessor;

	@Autowired
	public ProductResourcesProcessor(ProductResourceProcessor productResourceProcessor) {
		this.productResourceProcessor = productResourceProcessor;
	}

	@Override
	public Resources<Resource<Product>> process(Resources<Resource<Product>> resource) {
		resource.forEach(productResource -> productResourceProcessor.process(productResource));
		return resource;
	}

}
