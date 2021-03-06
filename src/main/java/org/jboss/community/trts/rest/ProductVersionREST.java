package org.jboss.community.trts.rest;

import java.util.Arrays;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@RolesAllowed({ "Manager" })
@Path("/products/{pid:[0-9][0-9]*}/versions")
public class ProductVersionREST {

	@Inject
	private ProductVersionService versionService;

	@Inject
	private ProductService productService;

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@RolesAllowed({ "Tester, Manager" })
	@Produces(MediaType.APPLICATION_JSON)
	public ProductVersion getById(@PathParam("pid") Long pid,
			@PathParam("id") Long id) {
		return versionService.getById(id);
	}

	@GET
	@RolesAllowed({ "Tester, Manager" })
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductVersion> getProductVersionsOfProduct(
			@PathParam("pid") Long id) {

		Product p = productService.getById(id);

		if (p != null) {
			return versionService.getProductVersionByProduct(p);
		} else {
			return null;
		}
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("pid") Long pid, @PathParam("id") Long id,
			ProductVersion pv) {
		versionService.update(pv);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addProductVersionsOfProduct(@PathParam("pid") Long id,
			ProductVersion pv) {

		Product p = productService.getById(id);
		pv.setProduct(p);

		versionService.persist(pv);
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void delete(@PathParam("pid") Long pid, @PathParam("id") Long id) {
		this.versionService.delete(this.versionService.getById(id));
	}

	@POST
	@Path("/{id:[0-9][0-9]*}/clone")
	@Consumes(MediaType.APPLICATION_JSON)
	public void cloneCascade(@PathParam("pid") Long pid,
			@PathParam("id") Long id, CloningOptions options) {
		
		ProductVersion pv = options.getClonedVersion();
		versionService.persist(pv);
		versionService.cloneProductVersionCascade(versionService.getById(id),
				pv, Arrays.asList(options.getClone()));
	}
}

class CloningOptions {
	private String[] clone;
	private ProductVersion clonedVersion;

	public ProductVersion getClonedVersion() {
		return clonedVersion;
	}

	public void setClonedVersion(ProductVersion clonedVersion) {
		this.clonedVersion = clonedVersion;
	}

	public String[] getClone() {
		return clone;
	}

	public void setClone(String[] clone) {
		this.clone = clone;
	}
}
