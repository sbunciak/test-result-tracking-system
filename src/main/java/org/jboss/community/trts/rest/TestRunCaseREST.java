package org.jboss.community.trts.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.model.TestRunCase;
import org.jboss.community.trts.service.TestRunCaseService;
import org.jboss.community.trts.service.TestRunService;

@RequestScoped
@RolesAllowed({"Tester, Admin"})
@Path("products/{pid:[0-9][0-9]*}/versions/{vid:[0-9][0-9]*}/builds/{bid:[0-9][0-9]*}/testplans/{tid:[0-9][0-9]*}/runs/{rid:[0-9][0-9]*}/cases")
public class TestRunCaseREST {

	@Inject
	private TestRunCaseService runCaseService;

	@Inject
	private TestRunService runService;

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public TestRunCase getTestRunCase(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("bid") Long bid,
			@PathParam("tid") Long tid, @PathParam("rid") Long rid,
			@PathParam("id") Long id) {
		return runCaseService.getById(id);
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTestRunCase(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("bid") Long bid,
			@PathParam("tid") Long tid, @PathParam("rid") Long rid,
			@PathParam("id") Long id, TestRunCase rCase) {
		runCaseService.update(rCase);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestRunCase> getTestRunCasesOfTestRun(
			@PathParam("pid") Long pid, @PathParam("vid") Long vid,
			@PathParam("bid") Long bid, @PathParam("tid") Long tid,
			@PathParam("rid") Long rid) {

		TestRun run = runService.getById(rid);
		return runCaseService.getTestRunCasesByTestRun(run);
	}
	
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void removeTestRunCase(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("bid") Long bid,
			@PathParam("tid") Long tid, @PathParam("rid") Long rid,
			@PathParam("id") Long id) {
		runCaseService.delete(runCaseService.getById(id));
	}

}
