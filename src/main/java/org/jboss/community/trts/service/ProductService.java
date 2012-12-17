package org.jboss.community.trts.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.TypedQuery;

import org.jboss.community.trts.model.Product;

@Stateless
@RequestScoped
public class ProductService extends BaseEntityService<Product>{

	public Product getProductById(Long id) {
		return em.find(Product.class, id);
	}

	public List<Product> getAllProducts() {
		TypedQuery<Product> query = em.createNamedQuery("Product.findAll",
				Product.class);
		return query.getResultList();
	}

}
