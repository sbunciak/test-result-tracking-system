package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.TestRun;

@Stateless
@RequestScoped
public class TestRunService extends BaseEntityService<TestRun> {

	public List<TestRun> getTestRunsOfTestPlan(TestPlan plan) {
		TypedQuery<TestRun> query = em.createNamedQuery("TestRun.findAll",
				TestRun.class);
		query.setParameter("plan", plan);
		return query.getResultList();
	}
}
