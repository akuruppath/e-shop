package com.kpn.eshop.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "products")
@Data
public class Product {

	@Id
	private String id;
	private ProductCategory category;
	private BigDecimal price;
}
