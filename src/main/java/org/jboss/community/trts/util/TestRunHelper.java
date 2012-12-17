package org.jboss.community.trts.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisCriteria;
import org.jboss.community.trts.model.TestCase;
import org.jboss.community.trts.model.TestResult;
import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.model.TestRunCase;

import com.google.common.collect.Sets;

public class TestRunHelper {

	public static List<TestRunCase> generateTestRunCases(TestRun run,
			List<TestCase> cases, Map<Axis, Set<AxisCriteria>> axisMap) {

		List<TestRunCase> resultCases = new ArrayList<TestRunCase>();

		List<Set<AxisCriteria>> criteriasSets = new ArrayList<Set<AxisCriteria>>(
				axisMap.values());

		for (TestCase tCase : cases) {

			for (List<AxisCriteria> crits : Sets
					.cartesianProduct(criteriasSets)) {

				TestRunCase rCase = createTestRunCase(run, tCase);
				rCase.setCriterias(crits);

				resultCases.add(rCase);
			}

		}

		return resultCases;
	}

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

		for (AxisCriteria ac : runCase.getCriterias()) {
			if (ac.getAxis().equals(axis))
				result = true;
		}

		return result;
	}
}
