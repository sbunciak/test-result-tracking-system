package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.TestCase;
import org.jboss.community.trts.model.TestPlan;

@Stateless
@RequestScoped
public class TestCaseService extends BaseEntityService<TestCase>{

	public List<TestCase> getTestCasesByTestPlan(TestPlan plan) {
		TypedQuery<TestCase> query = em.createNamedQuery("TestCase.findAll",
				TestCase.class);
		query.setParameter("plan", plan);
		return query.getResultList();
	}
	
}
