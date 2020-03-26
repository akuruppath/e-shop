package com.kpn.eshop.rest.controllers;

import static com.kpn.eshop.constants.URLConstants.CATEGORY_ID;
import static com.kpn.eshop.constants.URLConstants.PRODUCT_ID;
import static com.kpn.eshop.constants.URLConstants.URL_PRODUCT;
import static com.kpn.eshop.constants.URLConstants.URL_PRODUCTS;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kpn.eshop.model.Customer;
import com.kpn.eshop.model.Product;
import com.kpn.eshop.model.ProductCategory;
import com.kpn.eshop.repositories.CustomerRepository;
import com.kpn.eshop.repositories.ProductCategoryRepository;
import com.kpn.eshop.repositories.ProductRepository;
import com.kpn.eshop.rest.resource.processors.ProductResourceProcessor;
import com.kpn.eshop.rest.resource.processors.paged.ProductResourcesProcessor;

@RestController
public class ProductController {

	private final ProductCategoryRepository productCategoryRepository;
	private final ProductRepository productRepository;
	private final ProductResourceProcessor productResourceProcessor;
	private final ProductResourcesProcessor productResourcesProcessor;
	private final CustomerRepository customerRepository;

	@Autowired
	public ProductController(ProductCategoryRepository productCategoryRepository, ProductRepository productRepository,
			ProductResourceProcessor productResourceProcessor, ProductResourcesProcessor productResourcesProcessor,
			CustomerRepository customerRepository) {
		this.productCategoryRepository = productCategoryRepository;
		this.productRepository = productRepository;
		this.productResourceProcessor = productResourceProcessor;
		this.productResourcesProcessor = productResourcesProcessor;
		this.customerRepository = customerRepository;
	}

	@RequestMapping(value = URL_PRODUCTS, method = RequestMethod.GET, produces = "application/hal+json")
	public HttpEntity<Resources<Resource<Product>>> getAllProducts(@PathVariable(CATEGORY_ID) String categoryId) {
		Optional<ProductCategory> category = productCategoryRepository.findById(categoryId);
		if (category.isPresent()) {
			Resources<Resource<Product>> productResources = Resources
					.wrap(productRepository.findAllByCategory(category.get()));
			return new ResponseEntity<>(productResourcesProcessor.process(productResources), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = URL_PRODUCT, method = RequestMethod.GET, produces = "application/hal+json")
	public HttpEntity<Resource<Product>> getProduct(@PathVariable(CATEGORY_ID) String categoryId,
			@PathVariable(PRODUCT_ID) String productId) {
		Optional<ProductCategory> category = productCategoryRepository.findById(categoryId);
		if (category.isPresent()) {
			Optional<Product> product = productRepository.findByCategoryAndId(category.get(), productId);
			if (product.isPresent()) {
				Customer existingCustomer = getExistingCustomer();
				if (existingCustomer != null) {
					return new ResponseEntity<>(
							productResourceProcessor.process(new Resource<>(product.get()), existingCustomer),
							HttpStatus.OK);
				}
				return new ResponseEntity<>(productResourceProcessor.process(new Resource<>(product.get())),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	private String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	private Customer getExistingCustomer() {
		String userName = getUserName();
		return userName == null ? null : customerRepository.findByUserName(userName);
	}
}
