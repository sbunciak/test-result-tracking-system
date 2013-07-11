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

	public List<AxisConfig> getAxisConfigs(ProductVersion version) {
		TypedQuery<AxisConfig> query = em.createNamedQuery("AxisConfig.findByProductVersion",
				AxisConfig.class);
		query.setParameter("productVersion", version);
		return query.getResultList();
	}
	
	public List<AxisConfig> getAxisConfigs(Axis axis, ProductVersion version) {
		TypedQuery<AxisConfig> query = em.createNamedQuery("AxisConfig.findByAxisAndProductVersion",
				AxisConfig.class);
		query.setParameter("axis", axis);
		query.setParameter("productVersion", version);
		return query.getResultList();
	}
}
