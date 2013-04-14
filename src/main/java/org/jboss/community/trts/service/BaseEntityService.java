package org.jboss.community.trts.service;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Base service class to perform basic CRUD operations on persistance layer
 * 
 * @author sbunciak
 *
 * @param <T>
 */
public abstract class BaseEntityService<T> {

	@Inject
	protected EntityManager em;

	@Inject
	protected Logger log;

	public void persist(T entity) {
		em.persist(entity);
		log.info(this.getClass().getSimpleName() + ": " + entity
				+ " persisted.");
	}

	public void delete(T entity) {
		em.remove(em.merge(entity));
		log.info(this.getClass().getSimpleName() + ": " + entity + " deleted.");
	}

	public void update(T entity) {
		em.merge(entity);
		log.info(this.getClass().getSimpleName() + ": " + entity + " updated.");
	}

}
