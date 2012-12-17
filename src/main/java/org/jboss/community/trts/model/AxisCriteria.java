package org.jboss.community.trts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries({
		@NamedQuery(name = "AxisCriteria.findAll", query = "SELECT p FROM AxisCriteria p"),
		@NamedQuery(name = "AxisCriteria.findByProductVersion", query = "SELECT p FROM AxisCriteria p WHERE p.productVersion = :productVersion"),
		@NamedQuery(name = "AxisCriteria.findByAxis", query = "SELECT p FROM AxisCriteria p WHERE p.axis = :axis"),
		@NamedQuery(name = "AxisCriteria.findByAxisAndProductVersion", query = "SELECT p FROM AxisCriteria p WHERE p.axis = :axis AND p.productVersion = :productVersion") })
public class AxisCriteria extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = 4684947539422770667L;

	@ManyToOne
	@NotNull
	private Axis axis;

	@ManyToOne
	private ProductVersion productVersion;

	@Column
	@NotEmpty
	private String value;

	@Column
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private AxisPriority priority;

	public Axis getAxis() {
		return axis;
	}

	public void setAxis(Axis axis) {
		this.axis = axis;
	}

	public ProductVersion getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(ProductVersion productVersion) {
		this.productVersion = productVersion;
	}

	public AxisPriority getPriority() {
		return this.priority;
	}

	public void setPriority(final AxisPriority priority) {
		this.priority = priority;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public String toString() {
		String result = "";
		if (value != null && !value.trim().isEmpty())
			result += value;
		if (priority != null && !priority.toString().isEmpty())
			result += " " + priority;
		return result;
	}
}