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

	public List<TestRunCase> getTestRunCasesByTestRun(TestRun run) {
		TypedQuery<TestRunCase> query = em.createNamedQuery("TestRunCase.findByTestRun",
				TestRunCase.class);
		query.setParameter("run", run);
		return query.getResultList();
	}
	
	public void persistAll(List<TestRunCase> cases) {
		for (TestRunCase testRunCase : cases) {
			this.persist(testRunCase);
		}
	}
}
