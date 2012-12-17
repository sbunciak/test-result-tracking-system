package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisCriteria;
import org.jboss.community.trts.model.ProductVersion;

@Stateless
@RequestScoped
public class AxisCriteriaService extends BaseEntityService<AxisCriteria> {

	public AxisCriteria getAxisCriteriaById(Long id) {
		return em.find(AxisCriteria.class, id);
	}

	public List<AxisCriteria> getAxisCriterias(Axis axis) {
		TypedQuery<AxisCriteria> query = em.createNamedQuery("AxisCriteria.findByAxis",
				AxisCriteria.class);
		query.setParameter("axis", axis);
		return query.getResultList();
	}

	public List<AxisCriteria> getAxisCriterias(Axis axis, ProductVersion version) {
		TypedQuery<AxisCriteria> query = em.createNamedQuery("AxisCriteria.findByAxisAndProductVersion",
				AxisCriteria.class);
		query.setParameter("axis", axis);
		query.setParameter("productVersion", version);
		return query.getResultList();
	}

	public List<AxisCriteria> getAxisCriterias(ProductVersion version) {
		TypedQuery<AxisCriteria> query = em.createNamedQuery("AxisCriteria.findByProductVersion",
				AxisCriteria.class);
		query.setParameter("productVersion", version);
		return query.getResultList();
	}
}
