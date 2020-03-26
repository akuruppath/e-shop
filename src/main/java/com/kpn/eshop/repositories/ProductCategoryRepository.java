package com.kpn.eshop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kpn.eshop.model.Product;
import com.kpn.eshop.model.ProductCategory;

public interface ProductCategoryRepository extends MongoRepository<ProductCategory, String> {

	List<ProductCategory> findAll();
	
	List<Product> findAllProductsById(String id);
	
	Optional<ProductCategory> findById(String id);
}
