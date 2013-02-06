package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisValue;

@Stateless
@RequestScoped
public class AxisValueService extends BaseEntityService<AxisValue> {

	public AxisValue getAxisValueById(Long id) {
		return em.find(AxisValue.class, id);
	}

	public List<AxisValue> getAxisValues(Axis a) {
		TypedQuery<AxisValue> query = em.createNamedQuery(
				"AxisValue.findByAxis", AxisValue.class);
		query.setParameter(0, a);
		return query.getResultList();
	}
}
