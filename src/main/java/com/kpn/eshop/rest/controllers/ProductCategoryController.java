package com.kpn.eshop.rest.controllers;

import static com.kpn.eshop.constants.URLConstants.CATEGORY_ID;
import static com.kpn.eshop.constants.URLConstants.URL_PRODUCT_CATEGORIES;
import static com.kpn.eshop.constants.URLConstants.URL_PRODUCT_CATEGORY;

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

import com.kpn.eshop.model.ProductCategory;
import com.kpn.eshop.repositories.ProductCategoryRepository;
import com.kpn.eshop.rest.resource.processors.ProductCategoryResourceProcessor;
import com.kpn.eshop.rest.resource.processors.paged.ProductCategoriesResourceProcessor;

@RestController
public class ProductCategoryController {

	private final ProductCategoryRepository productCategoryRepository;
	private final ProductCategoryResourceProcessor productCategoryResourceProcessor;
	private final ProductCategoriesResourceProcessor productCategoriesResourceProcessor;

	@Autowired
	public ProductCategoryController(ProductCategoryRepository productCategoryRepository,
			ProductCategoryResourceProcessor productCategoryResourceProcessor,
			ProductCategoriesResourceProcessor productCategoriesResourceProcessor) {
		this.productCategoryRepository = productCategoryRepository;
		this.productCategoryResourceProcessor = productCategoryResourceProcessor;
		this.productCategoriesResourceProcessor = productCategoriesResourceProcessor;
	}

	@RequestMapping(value = URL_PRODUCT_CATEGORIES, method = RequestMethod.GET, produces = "application/hal+json")
	public HttpEntity<Resources<Resource<ProductCategory>>> getAllProductCategories() {
		Resources<Resource<ProductCategory>> productCategoryResources = Resources
				.wrap(productCategoryRepository.findAll());
		return new ResponseEntity<>(productCategoriesResourceProcessor.process(productCategoryResources),
				HttpStatus.OK);
	}

	@RequestMapping(value = URL_PRODUCT_CATEGORY, method = RequestMethod.GET, produces = "application/hal+json")
	public HttpEntity<Resource<ProductCategory>> getProductCategoryById(@PathVariable(CATEGORY_ID) String categoryId) {
		Optional<ProductCategory> productCategory = productCategoryRepository.findById(categoryId);
		if (productCategory.isPresent()) {
			return new ResponseEntity<>(productCategoryResourceProcessor.process(new Resource<>(productCategory.get())),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
