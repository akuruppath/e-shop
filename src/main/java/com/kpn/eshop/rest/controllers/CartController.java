package com.kpn.eshop.rest.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kpn.eshop.constants.URLConstants;
import com.kpn.eshop.model.Cart;
import com.kpn.eshop.model.Customer;
import com.kpn.eshop.model.Product;
import com.kpn.eshop.repositories.CartRepository;
import com.kpn.eshop.repositories.CustomerRepository;
import com.kpn.eshop.repositories.ProductRepository;
import com.kpn.eshop.rest.resource.processors.CartResourceProcessor;

@RestController
public class CartController {

	private final CartRepository cartRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	private final CartResourceProcessor cartResourceProcessor;

	@Autowired
	public CartController(CartRepository cartRepository, CustomerRepository customerRepository,
			ProductRepository productRepository, CartResourceProcessor cartResourceProcessor) {
		this.cartRepository = cartRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.cartResourceProcessor = cartResourceProcessor;
	}

	@RequestMapping(value = URLConstants.URL_CART, method = RequestMethod.GET, produces = "application/hal+json")
	public HttpEntity<Resource<Cart>> getCart(@PathVariable(URLConstants.CART_ID) String cartId) {
		Optional<Cart> cart = cartRepository.findById(cartId);
		if (cart.isPresent()) {
			return new ResponseEntity<>(cartResourceProcessor.process(new Resource<>(cart.get())), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = URLConstants.URL_CUSTOMER_CART, method = RequestMethod.GET, produces = "application/hal+json")
	public HttpEntity<Resource<Cart>> getProductsInCart(@PathVariable(URLConstants.CUSTOMER_ID) String customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (customer.isPresent()) {
			return new ResponseEntity<>(cartResourceProcessor.process(new Resource<>(getCart(customer.get()))),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = URLConstants.URL_ADD_PRODUCT_TO_CUSTOMER_CART, method = RequestMethod.PUT, produces = "application/hal+json")
	public HttpEntity<Resource<Cart>> addProductToCart(@PathVariable(URLConstants.CUSTOMER_ID) String customerId,
			@PathVariable(URLConstants.PRODUCT_ID) String productId) {
		Optional<Customer> optionCustomer = customerRepository.findById(customerId);
		if (optionCustomer.isPresent()) {
			Customer customer = optionCustomer.get();
			Optional<Product> product = productRepository.findById(productId);
			if (product.isPresent()) {
				Cart cart = getCart(customer);
				cart.getProducts().add(product.get());
				Cart savedCart = cartRepository.save(cart);
				customer.setCart(savedCart);
				customerRepository.save(customer);
				return new ResponseEntity<>(cartResourceProcessor.process(new Resource<>(savedCart)), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	private Cart getCart(Customer customer) {
		Cart cart = customer.getCart();
		if (cart == null) {
			cart = new Cart();
			customer.setCart(cart);
			return cartRepository.save(cart);
		}
		return cart;
	}

}
