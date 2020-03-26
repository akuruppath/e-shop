package com.kpn.eshop.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kpn.eshop.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{

	Customer findByFirstNameAndLastName(String firstName, String lastName);

	Customer findByUserName(String userName);
}
