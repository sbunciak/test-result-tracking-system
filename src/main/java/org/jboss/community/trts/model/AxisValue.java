package org.jboss.community.trts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@NamedQueries({ @NamedQuery(name = "AxisValue.findByAxis", query = "SELECT p FROM AxisValue p WHERE p.axis = :axis") })
public class AxisValue extends TestSystemEntity {

	private static final long serialVersionUID = -6149008764429851962L;

	@ManyToOne
	@NotNull
	private Axis axis;

	@Column
	@NotEmpty
	private String value;

	// TODO: Some Additional parameters, Manufacturer (Java 6 + SUN, Java 6 +
	// OpenJDK, etc.) ?

	public Axis getAxis() {
		return axis;
	}

	public void setAxis(Axis axis) {
		this.axis = axis;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AxisValue [axis=" + axis + ", value=" + value + "]";
	}

}
