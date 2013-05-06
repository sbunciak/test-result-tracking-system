package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.ProductBuild;
import org.jboss.community.trts.model.ProductVersion;

@Stateless
@RequestScoped
public class ProductBuildService extends BaseEntityService<ProductBuild> {

	public List<ProductBuild> getProductBuildsByProductVersion(ProductVersion version) {
		TypedQuery<ProductBuild> query = em.createNamedQuery(
				"ProductBuild.findByProductVersion", ProductBuild.class);
		query.setParameter("productVersion", version);
		return query.getResultList();
	}
}
