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

import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.model.TestRunCase;
import org.jboss.community.trts.service.TestRunCaseService;
import org.jboss.community.trts.service.TestRunService;

@RequestScoped
@Path("products/{pid:[0-9][0-9]*}/versions/{vid:[0-9][0-9]*}/testplans/{tid:[0-9][0-9]*}/runs/{rid:[0-9][0-9]*}/cases")
public class TestRunCaseREST {

	@Inject
	private TestRunCaseService runCaseService;

	@Inject
	private TestRunService runService;

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public TestRunCase getTestRunCase(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("rid") Long rid, @PathParam("id") Long id) {
		return runCaseService.getTestRunCaseById(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTestRunCase(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("rid") Long rid, TestRunCase rCase) {
		runCaseService.update(rCase);
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestRunCase> getTestRunCasesOfTestRun(
			@PathParam("pid") Long pid, @PathParam("vid") Long vid,
			@PathParam("tid") Long tid, @PathParam("rid") Long rid) {

		TestRun run = runService.getTestRunById(rid);

		if (run != null) {
			return runCaseService.getTestRunCasesByTestRun(run);
		} else {
			return null;
		}
	}
}
