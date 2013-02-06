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
import org.jboss.community.trts.service.TestRunService;

@RequestScoped
@Path("/test/runs/")
public class TestRunREST {

	@Inject
	private TestRunService service;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestRun> getTestRuns(TestPlan plan) {
		return service.getTestRunsOfTestPlan(plan);
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public TestRun getTestRun(@PathParam("id") Long id) {
		return service.getTestRunById(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTestRun(TestRun run) {
		service.update(run);
	}
}
