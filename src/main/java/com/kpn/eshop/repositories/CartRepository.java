package com.kpn.eshop.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kpn.eshop.model.Cart;

public interface CartRepository extends MongoRepository<Cart, String> {

}
