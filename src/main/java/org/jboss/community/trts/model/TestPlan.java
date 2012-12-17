package org.jboss.community.trts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries({
		@NamedQuery(name = "TestPlan.findAll", query = "SELECT p FROM TestPlan p"),
		@NamedQuery(name = "TestPlan.findByProductVersion", query = "SELECT p FROM TestPlan p WHERE p.productVersion = :productVersion") })
public class TestPlan extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = 4000467497295107810L;

	@Column
	@NotEmpty
	private String name;

	@ManyToOne
	private ProductVersion productVersion;

	@Column
	@NotNull
	@Enumerated(EnumType.STRING)
	private TestType type;

	@Lob
	@Column
	private String rules;

	@Column
	private String description;

	public ProductVersion getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(ProductVersion productVersion) {
		this.productVersion = productVersion;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String desc) {
		this.description = desc;
	}

	public TestType getType() {
		return type;
	}

	public void setType(TestType type) {
		this.type = type;
	}

	public String toString() {
		String result = "";
		if (name != null && !name.trim().isEmpty())
			result += name;
		if (description != null && !description.trim().isEmpty())
			result += " " + description;
		return result;
	}
}