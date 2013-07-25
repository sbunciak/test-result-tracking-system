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
import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.service.ProductBuildService;
import org.jboss.community.trts.service.TestPlanService;
import org.jboss.community.trts.service.TestRunService;

@RequestScoped
@RolesAllowed({"Manager"})
@Path("products/{pid:[0-9][0-9]*}/versions/{vid:[0-9][0-9]*}/builds/{bid:[0-9][0-9]*}")
public class TestRunREST {

	@Inject
	private TestRunService runService;

	@Inject
	private TestPlanService planService;

	@Inject
	private ProductBuildService buildService;

	@GET
	@Path("/testplans/{tid:[0-9][0-9]*}/runs")
	@RolesAllowed({"Tester, Manager"})
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestRun> getTestRuns(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("bid") Long bid) {

		TestPlan plan = planService.getById(tid);

		return runService.getTestRunsOfTestPlan(plan);
	}

	/*
	 * Due to test reporting, a workaround for getting all test runs of a build
	 */
	@GET
	@Path("/runs")
	@RolesAllowed({"Tester, Manager"})
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestRun> getTestRuns(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("bid") Long bid) {

		ProductBuild build  = buildService.getById(bid);

		return runService.getTestRunsOfProductBuild(build);
	}

	
	@GET
	@Path("/testplans/{tid:[0-9][0-9]*}/runs/{id:[0-9][0-9]*}")
	@RolesAllowed({"Tester, Manager"})
	@Produces(MediaType.APPLICATION_JSON)
	public TestRun getTestRun(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("bid") Long bid, @PathParam("id") Long id) {
		return runService.getById(id);
	}

	@POST
	@Path("/testplans/{tid:[0-9][0-9]*}/runs")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createTestRun(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("bid") Long bid, TestRun run) {

		runService.createTestRun(tid, bid, run);
	}

	
	
	@PUT
	@Path("/testplans/{tid:[0-9][0-9]*}/runs/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTestRun(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("bid") Long bid, @PathParam("id") Long id, TestRun run) {
		runService.update(run);
	}
}
