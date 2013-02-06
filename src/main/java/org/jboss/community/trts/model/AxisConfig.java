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

@Entity
@NamedQueries({
		@NamedQuery(name = "AxisConfig.findAll", query = "SELECT p FROM AxisConfig p"),
		@NamedQuery(name = "AxisConfig.findByProductVersion", query = "SELECT p FROM AxisConfig p WHERE p.productVersion = :productVersion") })
public class AxisConfig extends TestSystemEntity {

	@Transient
	private static final long serialVersionUID = 4684947539422770667L;

	@ManyToOne
	@NotNull
	private ProductVersion productVersion;

	@NotNull
	@ManyToOne
	private AxisValue axisValue;

	@Column
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private AxisPriority priority;

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

	public AxisValue getAxisValue() {
		return axisValue;
	}

	public void setAxisValue(AxisValue axisValue) {
		this.axisValue = axisValue;
	}

	@Override
	public String toString() {
		return "AxisConfig [productVersion=" + productVersion + ", axisValue="
				+ axisValue + ", priority=" + priority + "]";
	}

}