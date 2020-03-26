package com.kpn.eshop.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Cart {

	@Id
	private String id;
	private Collection<Product> products = new ArrayList<>();
}
