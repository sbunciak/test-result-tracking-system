package org.jboss.community.trts.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.model.ProductBuild;
import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.model.TestRunCase;
import org.jboss.community.trts.service.AxisConfigService;
import org.jboss.community.trts.service.ProductBuildService;
import org.jboss.community.trts.service.TestCaseService;
import org.jboss.community.trts.service.TestPlanService;
import org.jboss.community.trts.service.TestRunCaseService;
import org.jboss.community.trts.service.TestRunService;
import org.jboss.community.trts.util.RulesProcessor;
import org.jboss.community.trts.util.TestRunHelper;

import com.google.common.collect.Sets;

@RequestScoped
@RolesAllowed({"Tester, Admin"})
@Path("products/{pid:[0-9][0-9]*}/versions/{vid:[0-9][0-9]*}/builds/{bid:[0-9][0-9]*}/testplans/{tid:[0-9][0-9]*}/runs")
public class TestRunREST {

	@Inject
	private TestRunService runService;

	@Inject
	private TestCaseService caseService;

	@Inject
	private TestRunCaseService runCaseService;

	@Inject
	private TestPlanService planService;

	@Inject
	private ProductBuildService buildService;

	@Inject
	private AxisConfigService configService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestRun> getTestRuns(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("bid") Long bid) {

		TestPlan plan = planService.getById(tid);

		return runService.getTestRunsOfTestPlan(plan);
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public TestRun getTestRun(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("bid") Long bid, @PathParam("id") Long id) {
		return runService.getById(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createTestRun(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("bid") Long bid, TestRun run) {

		TestPlan plan = planService.getById(tid);
		ProductBuild build = buildService.getById(bid);
		
		run.setTestPlan(plan);
		run.setProductBuild(build);
		runService.persist(run);

		RulesProcessor rulesProc = new RulesProcessor();

		List<AxisConfig> configs = configService.getAxisConfigs(build.getProductVersion());
		
		HashMap<Axis, Set<AxisConfig>> axisMap = new HashMap<Axis, Set<AxisConfig>>();
		
		for (AxisConfig axisConfig : configs) {
			Axis axis = axisConfig.getAxisValue().getAxis();
			 
			if (axisMap.keySet().contains(axis)) {
				Set<AxisConfig> setOfConfigs = axisMap.get(axis);
				setOfConfigs.add(axisConfig);
			} else {
				axisMap.put(axis, Sets.newHashSet(axisConfig));
			}			
		}
		
		List<TestRunCase> generatedCases = TestRunHelper.generateTestRunCases(
				run, caseService.getTestCasesByTestPlan(plan), axisMap);

		List<TestRunCase> resultCases = rulesProc.filterTestRunCases(plan,
				generatedCases, axisMap);

		runCaseService.persistAll(resultCases);
	}
	
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTestRun(@PathParam("pid") Long pid,
			@PathParam("vid") Long vid, @PathParam("tid") Long tid,
			@PathParam("bid") Long bid, @PathParam("id") Long id, TestRun run) {
		runService.update(run);
	}
}
