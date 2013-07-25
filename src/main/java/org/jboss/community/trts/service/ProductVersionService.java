package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.model.TestCase;
import org.jboss.community.trts.model.TestPlan;

@Stateless
@RequestScoped
public class ProductVersionService extends BaseEntityService<ProductVersion> {

	@Inject
	private TestPlanService planService;

	@Inject
	private TestCaseService caseService;

	@Inject
	private AxisConfigService configService;

	public List<ProductVersion> getProductVersionByProduct(Product product) {
		TypedQuery<ProductVersion> query = em.createNamedQuery(
				"ProductVersion.findByProduct", ProductVersion.class);
		query.setParameter("product", product);
		return query.getResultList();
	}

	/**
	 * Clones related entities to ProductVersion given as origin to ProductVersion given as clone
	 * 
	 * @param origin - ProductVersion to be cloned from
	 * @param clone - ProductVersion to be cloned to
	 * @param options - List of what to be cloned in cascade [configs | plans]
	 */
	public void cloneProductVersionCascade(ProductVersion origin, ProductVersion clone, List<String> options) {

		clone = this.getById(clone.getId());
		
		if (options.contains("configs")) {
			// axis config
			List<AxisConfig> configs = configService.getAxisConfigs(origin);
			for (AxisConfig axisConfig : configs) {
				// clone axisConfigs
				AxisConfig clonedConfig = (AxisConfig)axisConfig.clone();
				clonedConfig.setProductVersion(clone);
				
				configService.persist(clonedConfig);
			}

		}

		if (options.contains("plans")) {
			// test plan
			final List<TestPlan> testPlans = planService.getTestPlansByProductVersion(origin);
			for (TestPlan testPlan : testPlans) {
				// clone plan
				TestPlan clonedPlan = (TestPlan)testPlan.clone();
				clonedPlan.setProductVersion(clone);
				planService.persist(clonedPlan);

				List<TestCase> testCases = caseService.getTestCasesByTestPlan(testPlan);
				
				for (TestCase testCase : testCases) {
					// clone case
					TestCase clonedCase = (TestCase)testCase.clone();
					clonedCase.setTestPlan(clonedPlan);
					caseService.persist(clonedCase);
				}
			}
		}
		
	}

}
