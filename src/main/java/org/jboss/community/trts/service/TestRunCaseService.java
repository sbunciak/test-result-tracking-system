package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.TestRun;
import org.jboss.community.trts.model.TestRunCase;

@Stateless
@RequestScoped
public class TestRunCaseService extends BaseEntityService<TestRunCase>{

	public TestRunCase getTestRunCaseById(Long id) {
		return em.find(TestRunCase.class, id);
	}
	
	public List<TestRunCase> getTestRunCasesByTestRun(TestRun run) {
		TypedQuery<TestRunCase> query = em.createNamedQuery("TestRunCase.findByTestRun",
				TestRunCase.class);
		query.setParameter("run", run);
		return query.getResultList();
	}
	
}
