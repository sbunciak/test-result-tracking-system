package org.jboss.community.trts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries({ @NamedQuery(name = "TestRun.findAll", query = "SELECT r FROM TestRun r WHERE r.testPlan = :plan") })
public class TestRun extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = 1516057483386392228L;

	@ManyToOne
	@NotNull
	private TestPlan testPlan;

	@Column
	@NotEmpty
	private String name;

	public TestPlan getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		String result = "";
		if (name != null && !name.trim().isEmpty())
			result += name;

		return result;
	}
}