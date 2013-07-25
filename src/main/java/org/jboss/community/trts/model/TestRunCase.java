package org.jboss.community.trts.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@NamedQueries({
		@NamedQuery(name = "TestRunCase.findAll", query = "SELECT p FROM TestRunCase p"),
		@NamedQuery(name = "TestRunCase.findByTestRun", query = "SELECT p FROM TestRunCase p WHERE p.testRun = :run") })
public class TestRunCase extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = 8023030016003008673L;

	@Column
	@NotEmpty
	private String title;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<AxisConfig> criterias;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private TestRun testRun;

	@Column
	@Enumerated(EnumType.STRING)
	private TestResult result;

	@Column
	@URL
	private String bugTrLink;

	@Column
	@URL
	private String ciLink;

	@Column
	private String assignee;

	public List<AxisConfig> getCriterias() {
		return criterias;
	}

	public void setCriterias(List<AxisConfig> criterias) {
		this.criterias = criterias;
	}

	public TestRun getTestRun() {
		return testRun;
	}

	public void setTestRun(TestRun testRun) {
		this.testRun = testRun;
	}

	/**
	 * just a short cut for rule processor
	 * 
	 * @return TestPlan of assigned TestRun
	 */
	/*
	 * @XmlTransient
	 * 
	 * @Transient private TestPlan getTestPlan() { return
	 * getTestRun().getTestPlan(); }
	 */

	public TestResult getResult() {
		return this.result;
	}

	public void setResult(final TestResult result) {
		this.result = result;
	}

	public String getBugTrLink() {
		return this.bugTrLink;
	}

	public void setBugTrLink(final String bugTrLink) {
		this.bugTrLink = bugTrLink;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(final String assignee) {
		this.assignee = assignee;
	}

	public String getCiLink() {
		return this.ciLink;
	}

	public void setCiLink(final String ciLink) {
		this.ciLink = ciLink;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "TestRunCase [id=" + getId() + ", testRun=" + testRun
				+ ", result=" + result + ", assignee=" + assignee + ", title="
				+ title + ", criterias=" + criterias + "]";
	}
}