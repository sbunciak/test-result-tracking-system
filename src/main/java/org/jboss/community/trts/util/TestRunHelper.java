package org.jboss.community.trts.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.model.TestCase;
import org.jboss.community.trts.model.TestResult;
import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.model.TestRunCase;

import com.google.common.collect.Sets;

/**
 * Helper class for easy generating of TestRunCase-s  
 * 
 * @author sbunciak
 *
 */
public class TestRunHelper {

	/**
	 * Generates TestRunCase-s from TestCase-s by producing cartesian product of TestCase and AxisConfigs
	 * 
	 * @param TestRun
	 * @param TestCases
	 * @param AxisConfigurations
	 * @return List of TestRunCase-s
	 */
	public static List<TestRunCase> generateTestRunCases(TestRun run,
			List<TestCase> cases, Map<Axis, Set<AxisConfig>> axisMap) {
		
		List<TestRunCase> resultCases = new ArrayList<TestRunCase>();

		List<Set<AxisConfig>> criteriasSets = new ArrayList<Set<AxisConfig>>(
				axisMap.values());

		for (TestCase tCase : cases) {

			for (List<AxisConfig> crits : Sets.cartesianProduct(criteriasSets)) {

				TestRunCase rCase = createTestRunCase(run, tCase);
				rCase.setCriterias(crits);

				resultCases.add(rCase);
			}

		}

		return resultCases;
	}

	/**
	 * Clone TestCase to TestRunCase and set default values
	 * 
	 * @param TestRun
	 * @param TestCase
	 * @return TestRunCase
	 */
	public static TestRunCase createTestRunCase(TestRun run, TestCase testCase) {
		TestRunCase rCase = new TestRunCase();

		rCase.setAssignee(testCase.getDefaultTester());
		rCase.setResult(TestResult.RUNNING);
		rCase.setTitle(testCase.getTitle());
		rCase.setCiLink(testCase.getCiLink());
		rCase.setTestRun(run);
		return rCase;
	}
	

	public static boolean axisHasBeenUsed(TestRunCase runCase, Axis axis) {
		boolean result = false;

		for (AxisConfig ac : runCase.getCriterias()) {
			if (ac.getAxisValue().getAxis().equals(axis))
				result = true;
		}

		return result;
	}
}
