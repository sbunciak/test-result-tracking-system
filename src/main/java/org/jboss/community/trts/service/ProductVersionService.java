package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.Product;
import org.jboss.community.trts.model.ProductVersion;

@Stateless
@RequestScoped
public class ProductVersionService extends BaseEntityService<ProductVersion>{

	public ProductVersion getProductVersionById(Long id) {
		return em.find(ProductVersion.class, id);
	}

	public List<ProductVersion> getProductVersionByProduct(Product product) {
		TypedQuery<ProductVersion> query = em.createNamedQuery("ProductVersion.findByProduct",
				ProductVersion.class);
		query.setParameter("product", product);
		return query.getResultList();
	}
	
}
