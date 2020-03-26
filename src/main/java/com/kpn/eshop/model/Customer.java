package com.kpn.eshop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document(collection="customers")
@Data
public class Customer {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String userName;
	
	@JsonIgnore
	private char[] password;
	
	@JsonIgnore
	@DBRef
	private Cart cart;
}
