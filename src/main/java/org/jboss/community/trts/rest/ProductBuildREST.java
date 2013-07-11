package org.jboss.community.trts.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.model.ProductBuild;
import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.service.ProductBuildService;
import org.jboss.community.trts.service.ProductVersionService;

@RequestScoped
@RolesAllowed({"Tester, Admin"})
@Path("/products/{pid:[0-9][0-9]*}/versions/{vid:[0-9][0-9]*}/builds")
public class ProductBuildREST {

	@Inject
	private ProductBuildService buildService;

	@Inject
	private ProductVersionService versionService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductBuild> getProductBuildsOfVersion(
			@PathParam("pid") Long pid, @PathParam("vid") Long vid) {

		ProductVersion ver = versionService.getById(vid);

		if (ver != null) {
			return buildService.getProductBuildsByProductVersion(ver);
		} else {
			return null;
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addProductBuild(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, ProductBuild build) {
		build.setProductVersion(versionService.getById(vid));

		buildService.persist(build);
	}

	@PUT
	@Path("/{bid:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void editProductBuild(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("bid") Long id,
			ProductBuild build) {
		build.setProductVersion(versionService.getById(vid));

		buildService.update(build);
	}

	@GET
	@Path("/{bid:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductBuild getProductBuildById(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("bid") Long id) {
		return buildService.getById(id);
	}
}
