package com.kpn.eshop.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.kpn.eshop.config.ProductConfigProperties.Category;
import com.kpn.eshop.constants.CustomerConstants;
import com.kpn.eshop.model.Customer;
import com.kpn.eshop.model.Product;
import com.kpn.eshop.model.ProductCategory;
import com.kpn.eshop.repositories.CustomerRepository;
import com.kpn.eshop.repositories.ProductCategoryRepository;
import com.kpn.eshop.repositories.ProductRepository;

@Component
public class EShopInitialiser implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	private final ProductConfigProperties productCategoryConfig;
	private final ProductCategoryRepository productCategoryRepository;
	private final ProductRepository productRepository;
	private final CustomerRepository customerRepository;

	@Autowired
	public EShopInitialiser(ApplicationContext applicationContext, ProductConfigProperties productCategoryConfig,
			ProductCategoryRepository productCategoryRepository, ProductRepository productRepository,
			CustomerRepository customerRepository) {
		this.applicationContext = applicationContext;
		this.productCategoryConfig = productCategoryConfig;
		this.productCategoryRepository = productCategoryRepository;
		this.productRepository = productRepository;
		this.customerRepository = customerRepository;
		initialiseProductCategories();
		initialiseCustomer();
	}

	private void initialiseCustomer() {
		if (customerRepository.findByFirstNameAndLastName(CustomerConstants.USER_FIRST_NAME,
				CustomerConstants.USER_LAST_NAME) == null) {
			Customer customer = new Customer();
			customer.setFirstName(CustomerConstants.USER_FIRST_NAME);
			customer.setLastName(CustomerConstants.USER_LAST_NAME);
			customer.setUserName(CustomerConstants.USER_NAME);
			customer.setPassword(CustomerConstants.PASSWORD);
			customerRepository.save(customer);
		}
	}

	private void initialiseProductCategories() {
		List<Category> productCategories = productCategoryConfig.getCategories();
		Assert.notEmpty(productCategories, "ProductCategories cannot be null.");
		productCategories.forEach(category -> {
			String id = category.getId();
			List<String> categoryProducts = category.getProducts();
			if (!productCategoryRepository.findById(id).isPresent()) {
				ProductCategory savedCategory = saveCategory(id);
				saveProducts(categoryProducts, savedCategory);
			}
		});
	}

	private ProductCategory saveCategory(String id) {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setId(id);
		ProductCategory savedCategory = productCategoryRepository.save(productCategory);
		return savedCategory;
	}

	private void saveProducts(List<String> categoryProducts, ProductCategory savedCategory) {
		categoryProducts.forEach(categoryProduct -> {
			Product product = new Product();
			product.setId(categoryProduct);
			product.setCategory(savedCategory);
			product.setPrice(new BigDecimal("100.0"));
			productRepository.save(product);
		});
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
