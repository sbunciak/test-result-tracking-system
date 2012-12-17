package org.jboss.community.trts.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.service.ProductService;

@RequestScoped
@Path("/products")
public class ProductRESTService {

	@Inject
	private ProductService service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> listAllProducts() {
		return service.getAllProducts();
	}
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProduct(@PathParam("id") Long id) {
		return service.getProductById(id);
	}
	
}
