package org.jboss.community.trts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

@Entity
@NamedQueries({ @NamedQuery(name = "TestCase.findAll", query = "SELECT c FROM TestCase c WHERE c.testPlan = :plan") })
public class TestCase extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = 4200795112916115715L;

	@NotNull
	@ManyToOne
	private TestPlan testPlan;

	@Column
	private String defaultTester;

	@Column
	@URL
	private String ciLink;

	@Column
	@NotEmpty
	private String title;

	public TestPlan getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	public String getDefaultTester() {
		return this.defaultTester;
	}

	public void setDefaultTester(final String defaultTester) {
		this.defaultTester = defaultTester;
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

	public String toString() {
		String result = "";
		if (defaultTester != null && !defaultTester.trim().isEmpty())
			result += " " + defaultTester;
		if (title != null && !title.trim().isEmpty())
			result += " " + title;
		return result;
	}

	@Override
	public Object clone() {
		TestCase clone = new TestCase();
		clone.setCiLink(getCiLink());
		clone.setDefaultTester(getDefaultTester());
		clone.setTitle(getTitle());
		clone.setTestPlan(getTestPlan());
		
		return clone;
	}
}