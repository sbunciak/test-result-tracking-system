package org.jboss.community.trts.rest;

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

import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.service.ProductVersionService;
import org.jboss.community.trts.service.TestPlanService;

@RequestScoped
@RolesAllowed({"Manager"})
@Path("/products/{pid:[0-9][0-9]*}/versions/{vid:[0-9][0-9]*}/testplans")
public class TestPlanREST {

	@Inject
	private TestPlanService service;

	@Inject
	private ProductVersionService versionService;

	@GET
	@RolesAllowed({"Tester, Manager"})
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestPlan> getTestPlansByProduct(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid) {
		ProductVersion ver = versionService.getById(vid);

		if (ver != null) {
			return service.getTestPlansByProductVersion(ver);
		} else {
			return null;
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addTestPlan(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, TestPlan p) {
		ProductVersion ver = versionService.getById(vid);

		p.setProductVersion(ver);
		service.persist(p);
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@RolesAllowed({"Tester, Manager"})
	@Produces(MediaType.APPLICATION_JSON)
	public TestPlan getTestPlan(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("id") Long id) {
		return service.getById(id);
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void editTestPlan(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("id") Long id, TestPlan p) {
		service.update(p);
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void removeTestPlan(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("id") Long id) {
		service.delete(service.getById(id));
	}
}
