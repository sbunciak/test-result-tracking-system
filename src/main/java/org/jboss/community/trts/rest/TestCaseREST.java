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

import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.TestCase;
import org.jboss.community.trts.service.TestCaseService;

@RequestScoped
@Path("/test/cases/")
public class TestCaseREST {

	@Inject
	private TestCaseService service;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestCase> getTestCases(TestPlan plan) {
		return service.getTestCasesByTestPlan(plan);
	}

	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public TestCase getTestCase(@PathParam("id") Long id) {
		return service.getTestCaseById(id);
	}

}
