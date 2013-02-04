package org.jboss.community.trts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries({ @NamedQuery(name = "Axis.findAll", query = "SELECT p FROM Axis p") })
public class Axis extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = -508142527954177187L;

	@Column
	@NotEmpty
	private String category;

	@Column
	private String description;

	public String getCategory() {
		return this.category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String desc) {
		this.description = desc;
	}

	public String toString() {
		String result = "";
		if (category != null && !category.trim().isEmpty())
			result += category;
		return result;
	}
}