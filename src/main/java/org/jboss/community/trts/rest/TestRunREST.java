package org.jboss.community.trts.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.service.TestPlanService;
import org.jboss.community.trts.service.TestRunService;

@RequestScoped
@Path("products/{pid:[0-9][0-9]*}/versions/{vid:[0-9][0-9]*}/testplans/{tid:[0-9][0-9]*}/runs")
public class TestRunREST {

	@Inject
	private TestRunService runService;

	@Inject
	private TestPlanService planService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestRun> getTestRuns(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid) {

		TestPlan plan = planService.getTestPlanById(tid);

		if (plan != null) {
			return runService.getTestRunsOfTestPlan(plan);
		} else {
			return null;
		}
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public TestRun getTestRun(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("id") Long id) {
		return runService.getTestRunById(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTestRun(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid, TestRun run) {
		runService.update(run);
	}
}
