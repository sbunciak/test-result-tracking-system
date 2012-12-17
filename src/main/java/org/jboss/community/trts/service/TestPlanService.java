package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.model.TestPlan;

@Stateless
@RequestScoped
public class TestPlanService extends BaseEntityService<TestPlan>{

	public TestPlan getTestPlanById(Long id) {
		return em.find(TestPlan.class, id);
	}

	public List<TestPlan> getAllTestPlans() {
		TypedQuery<TestPlan> query = em.createNamedQuery("TestPlan.findAll",
				TestPlan.class);
		return query.getResultList();
	}

	public List<TestPlan> getTestPlansByProductVersion(ProductVersion version) {
		TypedQuery<TestPlan> query = em.createNamedQuery("TestPlan.findByProductVersion",
				TestPlan.class);
		query.setParameter("productVersion", version);
		return query.getResultList();
	}
}
