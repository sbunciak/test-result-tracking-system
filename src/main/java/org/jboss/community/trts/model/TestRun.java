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
@NamedQueries({
		@NamedQuery(name = "TestRun.findAll", query = "SELECT r FROM TestRun r WHERE r.testPlan = :plan"),
		@NamedQuery(name = "TestRun.findAllByBuild", query = "SELECT r FROM TestRun r WHERE r.productBuild = :build") })
public class TestRun extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = 1516057483386392228L;

	@ManyToOne
	@NotNull
	private TestPlan testPlan;

	@ManyToOne
	@NotNull
	private ProductBuild productBuild;

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

	public ProductBuild getProductBuild() {
		return productBuild;
	}

	public void setProductBuild(ProductBuild productBuild) {
		this.productBuild = productBuild;
	}

	@Override
	public String toString() {
		String result = "";
		if (name != null && !name.trim().isEmpty())
			result += name;

		return result;
	}
}