package com.kpn.eshop.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class URLConstants {

	public static final String CART_ID = "cartId";
	public static final String CART_TEMPLATE = "{" + CART_ID + "}";

	public static final String CATEGORY_ID = "categoryId";
	public static final String CATEGORY_TEMPLATE = "{" + CATEGORY_ID + "}";

	public static final String PRODUCT_ID = "productId";
	public static final String PRODUCT_TEMPLATE = "{" + PRODUCT_ID + "}";

	public static final String CUSTOMER_ID = "customerId";
	public static final String CUSTOMER_TEMPLATE = "{" + CUSTOMER_ID + "}";

	public static final String URL_PRODUCT_CATEGORIES = "/" + Constants.PRODUCT_CATEGORIES;
	public static final String URL_PRODUCT_CATEGORY = URL_PRODUCT_CATEGORIES + "/" + CATEGORY_TEMPLATE;

	public static final String URL_PRODUCTS = URL_PRODUCT_CATEGORY + "/" + Constants.PRODUCTS;
	public static final String URL_PRODUCT = URL_PRODUCTS + "/" + PRODUCT_TEMPLATE;

	public static final String URL_CUSTOMERS = "/" + Constants.CUSTOMERS;
	public static final String URL_CUSTOMER = URL_CUSTOMERS + "/" + CUSTOMER_TEMPLATE;

	public static final String URL_CART = "/" + Constants.CART + "/" + CART_TEMPLATE;
	public static final String URL_CUSTOMER_CART = URL_CUSTOMER + "/" + Constants.CART;
	public static final String URL_ADD_PRODUCT_TO_CUSTOMER_CART = URL_CUSTOMER_CART + "/" + PRODUCT_TEMPLATE;
	public static final String URL_CUSTOMER_CART_PRODUCTS = URL_CUSTOMER_CART + "/" + Constants.PRODUCTS;

	public static final String URL_HOME = "/" + Constants.HOME;
}
