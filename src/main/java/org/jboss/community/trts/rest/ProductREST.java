package org.jboss.community.trts.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.service.BaseEntityService;
import org.jboss.community.trts.service.ProductService;

@RequestScoped
@Path("/products")
public class ProductREST extends BaseEntityREST<Product> {

	@Inject
	private ProductService service;
	
	@Override
	public BaseEntityService<Product> getService() {
		return service;
	}
}
