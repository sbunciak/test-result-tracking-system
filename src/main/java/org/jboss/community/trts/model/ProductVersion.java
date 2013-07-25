package org.jboss.community.trts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries({
		@NamedQuery(name = "ProductVersion.findAll", query = "SELECT p FROM ProductVersion p"),
		@NamedQuery(name = "ProductVersion.findByProduct", query = "SELECT p FROM ProductVersion p WHERE p.product = :product") })
public class ProductVersion extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = -8400036998565648294L;

	@ManyToOne
	@NotNull
	private Product product;

	@Column
	@NotEmpty
	@Pattern(regexp = "\\d+(\\.\\d+)*", message = "wrong version format")
	private String productVersion;

	@Column
	private String description;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String desc) {
		this.description = desc;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public String toString() {
		String result = "";
		result += product.getName() + " ";
		result += productVersion;
		return result;
	}
	
	@Override
	public Object clone() {
		ProductVersion clone = new ProductVersion();
		clone.setDescription(getDescription());
		clone.setProductVersion(getProductVersion());
		clone.setProduct(getProduct());
		return clone;
	}
}