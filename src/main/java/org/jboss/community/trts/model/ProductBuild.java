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
		@NamedQuery(name = "ProductBuild.findAll", query = "SELECT p FROM ProductBuild p"),
		@NamedQuery(name = "ProductBuild.findByProductVersion", query = "SELECT p FROM ProductBuild p WHERE p.productVersion = :productVersion") })
public class ProductBuild extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = -4238302133793644675L;

	@ManyToOne
	@NotNull
	private ProductVersion productVersion;

	@Column
	@NotEmpty
	private String label;

	public ProductVersion getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(ProductVersion productVersion) {
		this.productVersion = productVersion;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public String toString() {
		String result = "";
		if (label != null && !label.trim().isEmpty())
			result += label;
		return result;
	}
}