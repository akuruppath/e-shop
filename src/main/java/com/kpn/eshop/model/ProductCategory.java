package com.kpn.eshop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "productCategories")
@Data
public class ProductCategory {

	@Id
	private String id;
}
