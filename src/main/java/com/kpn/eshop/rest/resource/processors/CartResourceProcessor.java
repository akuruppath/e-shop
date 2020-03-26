package com.kpn.eshop.rest.resource.processors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Collection;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import com.kpn.eshop.constants.RelationConstants;
import com.kpn.eshop.model.Cart;
import com.kpn.eshop.model.Product;
import com.kpn.eshop.rest.controllers.CartController;
import com.kpn.eshop.rest.controllers.ProductController;

@Component
public class CartResourceProcessor implements ResourceProcessor<Resource<Cart>> {

	@Override
	public Resource<Cart> process(Resource<Cart> resource) {
		Cart cart = resource.getContent();
		Collection<Product> products = cart.getProducts();
		resource.add(linkTo(methodOn(CartController.class).getCart(cart.getId())).withSelfRel());
		products.forEach(product -> {
			resource.add(
					linkTo(methodOn(ProductController.class).getProduct(product.getCategory().getId(), product.getId()))
					.withRel(RelationConstants.REL_PRODUCT));
		});
		return resource;
	}

}
