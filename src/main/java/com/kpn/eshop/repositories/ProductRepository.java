package com.kpn.eshop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kpn.eshop.model.Product;
import com.kpn.eshop.model.ProductCategory;

public interface ProductRepository extends MongoRepository<Product, String> {

	List<Product> findAllByCategory(ProductCategory category);
    
	Optional<Product> findByCategoryAndId(ProductCategory category, String id);
}
