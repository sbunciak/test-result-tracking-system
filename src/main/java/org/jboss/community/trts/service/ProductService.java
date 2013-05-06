package org.jboss.community.trts.service;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;

import org.jboss.community.trts.model.Product;

@Stateless
@RequestScoped
public class ProductService extends BaseEntityService<Product> {
	// All necessary implementation is inherited
}
