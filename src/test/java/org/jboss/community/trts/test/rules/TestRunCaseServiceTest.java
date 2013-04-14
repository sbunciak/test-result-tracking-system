package org.jboss.community.trts.test.rules;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.model.AxisPriority;
import org.jboss.community.trts.model.AxisValue;
import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.model.ProductVersion;
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
					"There is an AxisConfig with"+ "\n" +
					"	- appropriate axis"+ "\n" +
					"	- value '1.6'"+ "\n" +
					"The AxisConfig has been used"+ "\n" +
				"then" + "\n"+
					"filter this TestRunCase" + "\n" +
				"end";
		
		Product product = new Product();
		product.setName("SOA");
		
		ProductVersion version = new ProductVersion();
		version.setProduct(product);
		version.setProductVersion("6.0.0");
		
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
		axis.setDescription("Test axis to meet Java requirements.");

		AxisValue value1 = new AxisValue();
		value1.setAxis(axis);
		value1.setValue("1.6");
		
		AxisValue value2 = new AxisValue();
		value2.setAxis(axis);
		value2.setValue("1.7");
		
		AxisConfig config1 = new AxisConfig();
		config1.setAxisValue(value1);
		config1.setPriority(AxisPriority.P1);
		config1.setProductVersion(version);

		AxisConfig config2 = new AxisConfig();
		config2.setAxisValue(value2);
		config2.setPriority(AxisPriority.P1);
		config2.setProductVersion(version);

		Axis axis2 = new Axis();
		axis2.setCategory("OS");
		axis2.setDescription("Test axis to meet OS requirements.");

		AxisValue value3 = new AxisValue();
		value3.setAxis(axis2);
		value3.setValue("RHEL");
		
		AxisValue value4 = new AxisValue();
		value4.setAxis(axis2);
		value4.setValue("Windows");
		
		AxisValue value5 = new AxisValue();
		value5.setAxis(axis2);
		value5.setValue("MacOS");
		
		AxisConfig config3 = new AxisConfig();
		config3.setAxisValue(value3);
		config3.setPriority(AxisPriority.P2);
		config3.setProductVersion(version);

		AxisConfig config4 = new AxisConfig();
		config4.setAxisValue(value4);
		config4.setPriority(AxisPriority.P2);
		config4.setProductVersion(version);

		AxisConfig config5 = new AxisConfig();
		config5.setAxisValue(value5);
		config5.setPriority(AxisPriority.P2);
		config5.setProductVersion(version);
		
		TestRun run = new TestRun();
		run.setName("Test Run for " + plan.getName());
		run.setTestPlan(plan);

		plan.setRules("");

		// Create axisMap for cartesian product
		Map<Axis, Set<AxisConfig>> axisMap = new HashMap<Axis, Set<AxisConfig>>();
		
		List<TestRunCase> generatedCases = TestRunHelper.generateTestRunCases(
				run, Arrays.asList(case1, case2), axisMap);
		
		// Should prevent from processing empty filter rules in prod
		List<TestRunCase> resultCases = rulesProc.filterTestRunCases(plan,
				generatedCases, axisMap);

		// without filtering and axis configs
		assertTrue(resultCases.size() == 2);

		// Add Axis Configs
		axisMap.put(axis, Sets.newHashSet(config1, config2));
		axisMap.put(axis2, Sets.newHashSet(config3, config4, config5));
		
		// Add rules
		plan.setRules(rules);

		generatedCases = TestRunHelper.generateTestRunCases(
				run, Arrays.asList(case1, case2), axisMap);
		resultCases = rulesProc.filterTestRunCases(plan, generatedCases, axisMap);

		assertTrue(generatedCases.size() == 9);
	}
}
