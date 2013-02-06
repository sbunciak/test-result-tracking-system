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

@RequestScoped
@Path("/test/runs/cases")
public class TestRunCaseREST {

	@Inject
	private TestRunCaseService service;

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public TestRunCase getTestRunCase(@PathParam("id") Long id) {
		return service.getTestRunCaseById(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTestRunCase(TestRunCase rCase) {
		service.update(rCase);
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestRunCase> getTestRunCasessOfTestRun(TestRun run) {
		return service.getTestRunCasesByTestRun(run);
	}
}
