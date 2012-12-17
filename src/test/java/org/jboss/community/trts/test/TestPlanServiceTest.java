package org.jboss.community.trts.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.TestCase;
import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.TestType;
import org.jboss.community.trts.service.AxisService;
import org.jboss.community.trts.service.TestCaseService;
import org.jboss.community.trts.service.TestPlanService;
import org.jboss.community.trts.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/*
 * Integration tests using Arquillian according to the current Use Case diagram
 */
@RunWith(Arquillian.class)
public class TestPlanServiceTest {

	@Inject
	private TestPlanService planService;

	@Inject
	private TestCaseService caseService;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage(Axis.class.getPackage())
				.addPackage(AxisService.class.getPackage())
				.addClass(Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}

	@Test
	public void canCreateUpdateDeleteTestPlan() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("package org.jboss.community.trts.util" + "\n");
		strBuilder.append("import org.jboss.community.trts.model.TestCase;"
				+ "\n");
		strBuilder.append("rule 'Is valid'" + "\n");
		strBuilder.append("when" + "\n");
		strBuilder.append("TestCase( title == \"Manual Tests\" )" + "\n");
		strBuilder.append("$a : TestCase()  " + "\n");
		strBuilder.append("then" + "\n");
		strBuilder.append("$a.setValid( false ); " + "\n");
		strBuilder.append("end" + "\n");

		TestPlan plan = new TestPlan();
		plan.setDescription("Integration tests for SOA 6.");
		plan.setName("SOA 6 Integration tests");
		plan.setType(TestType.INTEGRATION);
		plan.setRules(strBuilder.toString());

		planService.persist(plan);

		assertNotNull(plan.getId());

		plan.setType(TestType.ACCEPTANCE);

		planService.update(plan);

		TestPlan dbPlan = planService.getTestPlanById(plan.getId());

		assertEquals(dbPlan.getType(), plan.getType());

		planService.delete(dbPlan);

		assertTrue(planService.getAllTestPlans().size() == 0);
	}

	@Test
	public void canCreateUpdateDeleteTestCase() {

		TestPlan plan = new TestPlan();
		plan.setDescription("Integration tests for SOA 6.");
		plan.setName("SOA 6 Integration tests");
		plan.setType(TestType.INTEGRATION);

		planService.persist(plan);
		
		// create test cases
		TestCase case1 = new TestCase();
		case1.setTestPlan(plan);
		case1.setDefaultTester("sbunciak");
		case1.setTitle("Manual Tests");

		caseService.persist(case1);

		TestCase case2 = new TestCase();
		case2.setTestPlan(plan);
		case2.setDefaultTester("sbunciak");
		case2.setTitle("Automated Tests");

		caseService.persist(case2);

		assertTrue(caseService.getTestCasesByTestPlan(plan).size() == 2);
		
		case1.setTitle("Smoke Tests");
		caseService.update(case1);
		
		case1 = caseService.getTestCaseById(case1.getId());
		
		assertTrue(case1.getTitle().equals("Smoke Tests"));
		
		caseService.delete(case1);
		caseService.delete(case2);
		
		assertTrue(caseService.getTestCasesByTestPlan(plan).size() == 0);
		
		planService.delete(plan);
	}

}
