package org.jboss.community.trts.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.service.TestPlanService;

@RequestScoped
@Path("/test/plans")
public class TestPlanREST {

	@Inject
	private TestPlanService service;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestPlan> getTestPlansByProduct(ProductVersion version) {
		return service.getTestPlansByProductVersion(version);
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public TestPlan getTestPlan(@PathParam("id") Long id) {
		return service.getTestPlanById(id);
	}
}
