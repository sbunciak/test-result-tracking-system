package org.jboss.community.trts.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.model.ProductBuild;
import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.service.ProductBuildService;

@RequestScoped
@Path("/products/versions/builds")
public class ProductBuildREST {

	@Inject
	private ProductBuildService service;
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductBuild> getProductBuildsOfVersion(ProductVersion ver) {
		return service.getProductBuildsByProductVersion(ver);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addProductBuild(ProductBuild build) {
		service.persist(build);
	}
}
