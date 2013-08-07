package org.jboss.community.trts.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.model.AxisPriority;
import org.jboss.community.trts.model.ProductBuild;
import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.model.TestRunCase;
import org.jboss.community.trts.util.RulesProcessor;
import org.jboss.community.trts.util.TestRunHelper;

import com.google.common.collect.Sets;

@Stateless
@RequestScoped
public class TestRunService extends BaseEntityService<TestRun> {
	
	@Inject
	private TestPlanService planService;
	
	@Inject
	private AxisConfigService configService;
	
	@Inject
	private TestCaseService caseService;
	
	@Inject
	private TestRunCaseService runCaseService;
	
	@Inject
	private ProductBuildService buildService;
	
	public List<TestRun> getTestRunsOfTestPlan(TestPlan plan) {
		TypedQuery<TestRun> query = em.createNamedQuery("TestRun.findAll",
				TestRun.class);
		query.setParameter("plan", plan);
		return query.getResultList();
	}

	public List<TestRun> getTestRunsOfProductBuild(ProductBuild build) {
		TypedQuery<TestRun> query = em.createNamedQuery(
				"TestRun.findAllByBuild", TestRun.class);
		query.setParameter("build", build);
		return query.getResultList();
	}
	
	/**
	 * Creates TestRun along with TestRunCase-s, subsequently processes associated TestPlan rules and applies them to the result (TestRunCase-s).
	 * 
	 * @param tid - TestPlan id
	 * @param bid - ProductBuild id
	 * @param run - TestRun
	 */
	public void createTestRun(Long tid, Long bid, TestRun run) {
		TestPlan plan = planService.getById(tid);
		ProductBuild build = buildService.getById(bid);
		
		run.setTestPlan(plan);
		run.setProductBuild(build);
		this.persist(run);

		RulesProcessor rulesProc = new RulesProcessor();

		List<AxisConfig> configs = new ArrayList<AxisConfig>();
		// remove all Axis Configurations with priority DISABLED
		for (AxisConfig config : configService.getAxisConfigs(build.getProductVersion())) {
			if (!config.getPriority().equals(AxisPriority.DISABLED)) configs.add(config);
		}
				
		HashMap<Axis, Set<AxisConfig>> axisMap = new HashMap<Axis, Set<AxisConfig>>();
		
		for (AxisConfig axisConfig : configs) {
			
			Axis axis = axisConfig.getAxisValue().getAxis();
			
			// enable only axiss which are configured with the test plan
			if (plan.getAxiss().contains(axis)) {
				// "group axis configs by axis" 
				if (axisMap.keySet().contains(axis)) {
					Set<AxisConfig> setOfConfigs = axisMap.get(axis);
					setOfConfigs.add(axisConfig);
				} else {
					axisMap.put(axis, Sets.newHashSet(axisConfig));
				}
			}
						
		}
		
		List<TestRunCase> generatedCases = TestRunHelper.generateTestRunCases(
				run, caseService.getTestCasesByTestPlan(plan), axisMap);

		List<TestRunCase> resultCases = rulesProc.filterTestRunCases(plan,
				generatedCases, axisMap);

		runCaseService.persistAll(resultCases);
	}
}
