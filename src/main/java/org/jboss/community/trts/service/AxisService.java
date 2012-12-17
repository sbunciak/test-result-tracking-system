package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.Axis;

@Stateless
@RequestScoped
public class AxisService extends BaseEntityService<Axis>{

	public Axis getAxisById(Long id) {
		return em.find(Axis.class, id);
	}
	
	public List<Axis> getAllAxis() {
		TypedQuery<Axis> query = em.createNamedQuery("Axis.findAll",
				Axis.class);
		return query.getResultList();
	}
	
}
