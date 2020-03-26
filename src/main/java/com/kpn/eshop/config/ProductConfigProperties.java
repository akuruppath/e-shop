package com.kpn.eshop.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@EnableConfigurationProperties
@ConfigurationProperties("productconfig")
@Data
public class ProductConfigProperties {

	private List<Category> categories = new ArrayList<>();
	
	@Data
	public static class Category {
		private String id;
		private List<String> products;
	}
	
}
