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
import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.service.ProductService;
import org.jboss.community.trts.service.ProductVersionService;

@RequestScoped
@Path("/products/{pid:[0-9][0-9]*}/versions")
public class ProductVersionREST {

	@Inject
	private ProductVersionService versionService;

	@Inject
	private ProductService productService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductVersion> getProductVersionsOfProduct(
			@PathParam("pid") Long id) {

		Product p = productService.getProductById(id);

		if (p != null) {
			return versionService.getProductVersionByProduct(p);
		} else {
			return null;
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addProductVersionsOfProduct(
			@PathParam("pid") Long id, ProductVersion pv) {

		Product p = productService.getProductById(id);
		pv.setProduct(p);

		versionService.persist(pv);
	}

}
