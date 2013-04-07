package org.jboss.community.trts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p") })
public class Product extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = -3525123932293599086L;

	@Column
	@NotEmpty
	private String name;

	@Column
	private String description;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String toString() {
		String result = "";
		if (name != null && !name.trim().isEmpty())
			result += " " + name;
		if (description != null && !description.trim().isEmpty())
			result += " " + description;
		return result;
	}
}