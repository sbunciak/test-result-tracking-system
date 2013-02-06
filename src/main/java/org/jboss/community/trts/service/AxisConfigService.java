package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.model.ProductVersion;

@Stateless
@RequestScoped
public class AxisConfigService extends BaseEntityService<AxisConfig> {

	public AxisConfig getAxisConfigById(Long id) {
		return em.find(AxisConfig.class, id);
	}

	public List<AxisConfig> getAxisConfigs(Axis axis) {
		TypedQuery<AxisConfig> query = em.createNamedQuery("AxisConfig.findByAxis",
				AxisConfig.class);
		query.setParameter("axis", axis);
		return query.getResultList();
	}

	public List<AxisConfig> getAxisConfigs(ProductVersion version) {
		TypedQuery<AxisConfig> query = em.createNamedQuery("AxisConfig.findByProductVersion",
				AxisConfig.class);
		query.setParameter("productVersion", version);
		return query.getResultList();
	}
}
