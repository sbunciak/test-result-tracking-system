package org.jboss.tools.trts.test.rules;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisCriteria;
import org.jboss.community.trts.model.AxisPriority;
import org.jboss.community.trts.model.TestCase;
import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.model.TestRunCase;
import org.jboss.community.trts.model.TestType;
import org.jboss.community.trts.util.RulesProcessor;
import org.jboss.community.trts.util.TestRunHelper;
import org.junit.Test;

import com.google.common.collect.Sets;

public class TestRunCaseServiceTest {

	private RulesProcessor rulesProc = new RulesProcessor();

	@Test
	public void canGenerateTestRunCase() {

		// Custom DSL
		String rules = 
				"rule 'Filter java 1.6'" + "\n"+
				"when" + "\n"+
					"There is a TestPlan" + "\n" + 
					"There is a TestRunCase with" + "\n" +
					"	- title 'Manual Tests'" + "\n" +
					"There is an Axis with" + "\n" +
					"	- category 'Java'"+ "\n" +
					"There is an AxisCriteria with"+ "\n" +
					"	- appropriate axis"+ "\n" +
					"	- value '1.6'"+ "\n" +
					"The AxisCriteria has been used"+ "\n" +
				"then" + "\n"+
					"filter this TestRunCase" + "\n" +
				"end";
		
		TestPlan plan = new TestPlan();
		plan.setDescription("Integration tests for SOA 6.");
		plan.setName("SOA 6 Integration tests");
		plan.setType(TestType.INTEGRATION);

		// create test cases
		TestCase case1 = new TestCase();
		case1.setTestPlan(plan);
		case1.setDefaultTester("sbunciak");
		case1.setTitle("Manual Tests");

		// these are not going to be run on Java 7
		TestCase case2 = new TestCase();
		case2.setTestPlan(plan);
		case2.setDefaultTester("sbunciak");
		case2.setTitle("Automated Tests");

		// create java axis
		Axis axis = new Axis();
		axis.setCategory("Java");
		axis.setPlan(plan);
		axis.setDescription("Test axis to meet Java requirements.");

		AxisCriteria criteria1 = new AxisCriteria();
		criteria1.setAxis(axis);
		criteria1.setPriority(AxisPriority.P1);
		criteria1.setValue("1.6");

		AxisCriteria criteria2 = new AxisCriteria();
		criteria2.setAxis(axis);
		criteria2.setPriority(AxisPriority.P1);
		criteria2.setValue("1.7");

		Axis axis2 = new Axis();
		axis2.setPlan(plan);
		axis2.setCategory("OS");
		axis2.setDescription("Test axis to meet OS requirements.");

		AxisCriteria criteria3 = new AxisCriteria();
		criteria3.setAxis(axis2);
		criteria3.setPriority(AxisPriority.P2);
		criteria3.setValue("RHEL");

		AxisCriteria criteria4 = new AxisCriteria();
		criteria4.setAxis(axis2);
		criteria4.setPriority(AxisPriority.P2);
		criteria4.setValue("Windows");

		AxisCriteria criteria5 = new AxisCriteria();
		criteria5.setAxis(axis2);
		criteria5.setPriority(AxisPriority.P2);
		criteria5.setValue("MacOS");
		
		TestRun run = new TestRun();
		run.setName("Test Run for " + plan.getName());
		run.setTestPlan(plan);

		plan.setRules("");

		// Create axisMap for cartesian product
		Map<Axis, Set<AxisCriteria>> axisMap = new HashMap<Axis, Set<AxisCriteria>>();
		axisMap.put(axis, Sets.newHashSet(criteria1, criteria2));
		axisMap.put(axis2, Sets.newHashSet(criteria3, criteria4, criteria5));

		List<TestRunCase> generatedCases = TestRunHelper.generateTestRunCases(
				run, Arrays.asList(case1, case2), axisMap);

		// Should prevent from processing empty filter rules in prod
		List<TestRunCase> resultCases = rulesProc.filterTestRunCases(plan,
				generatedCases, axisMap);

		//System.out.println(resultCases);

		assertTrue(resultCases.size() == 12);

		plan.setRules(rules);

		resultCases = rulesProc.filterTestRunCases(plan, generatedCases, axisMap);

		//System.out.println(resultCases);
		
		assertTrue(generatedCases.size() == 9);
	}
}
