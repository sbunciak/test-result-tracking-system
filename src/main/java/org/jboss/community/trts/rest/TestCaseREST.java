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

import org.jboss.community.trts.model.TestCase;
import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.service.TestCaseService;
import org.jboss.community.trts.service.TestPlanService;

@RequestScoped
@Path("/products/{pid:[0-9][0-9]*}/versions/{vid:[0-9][0-9]*}/testplans/{tid:[0-9][0-9]*}/cases")
public class TestCaseREST {

	@Inject
	private TestCaseService caseService;

	@Inject
	private TestPlanService planService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestCase> getTestCases(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid) {

		TestPlan plan = planService.getById(tid);

		if (plan != null) {
			return caseService.getTestCasesByTestPlan(plan);
		} else {
			return null;
		}

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addTestCase(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			TestCase tcase) {

		TestPlan plan = planService.getById(tid);
		tcase.setTestPlan(plan);
		caseService.persist(tcase);

	}

	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public TestCase getTestCase(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("id") Long id) {
		return caseService.getById(id);
	}

}
