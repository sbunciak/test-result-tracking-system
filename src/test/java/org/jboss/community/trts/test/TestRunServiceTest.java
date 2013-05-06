package org.jboss.community.trts.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.model.ProductBuild;
import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.model.TestType;
import org.jboss.community.trts.service.ProductBuildService;
import org.jboss.community.trts.service.ProductService;
import org.jboss.community.trts.service.ProductVersionService;
import org.jboss.community.trts.service.TestPlanService;
import org.jboss.community.trts.service.TestRunService;
import org.jboss.community.trts.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TestRunServiceTest {

	@Inject
	private TestPlanService planService;

	@Inject
	private TestRunService runService;

	@Inject
	private ProductService productService;

	@Inject
	private ProductVersionService versionService;
	
	@Inject
	private ProductBuildService buildService;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage(TestRun.class.getPackage())
				.addPackage(TestRunService.class.getPackage())
				.addClass(Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}

	@Test
	public void canCreateUpdateDeleteTestRun() {
		Product product = new Product();
		product.setName("JBoss Developer Studio");

		productService.persist(product);

		ProductVersion version = new ProductVersion();
		version.setProduct(product);
		version.setProductVersion("6.0.0");

		versionService.persist(version);

		ProductBuild build = new ProductBuild();
		build.setLabel("CR1");
		build.setProductVersion(version);

		buildService.persist(build);
		
		TestPlan plan = new TestPlan();
		plan.setDescription("Integration tests for SOA 6.");
		plan.setName("SOA 6 Integration tests");
		plan.setType(TestType.INTEGRATION);
		plan.setProductVersion(version);

		planService.persist(plan);
		
		TestRun run = new TestRun();
		run.setName("Integration test run for SOA");
		run.setTestPlan(plan);
		run.setProductBuild(build);

		runService.persist(run);

		assertNotNull(run.getId());

		run.setName("Changed name");
		runService.update(run);

		run = runService.getById(run.getId());

		assertEquals(run.getName(), "Changed name");

		assertTrue(runService.getTestRunsOfTestPlan(plan).size() == 1);

		runService.delete(run);

		assertTrue(runService.getTestRunsOfTestPlan(plan).size() == 0);

		planService.delete(plan);
	}

}
