package org.jboss.community.trts.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.service.ProductService;

@RequestScoped
@Path("/products")
public class ProductREST {

	@Inject
	private ProductService service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> listAllProducts() {
		return service.getAllProducts();
	}
	
	@GET
	@Path("/{pid:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProduct(@PathParam("pid") Long id) {
		return service.getProductById(id);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addProduct(Product p) {
		service.persist(p);
	}
	
}
