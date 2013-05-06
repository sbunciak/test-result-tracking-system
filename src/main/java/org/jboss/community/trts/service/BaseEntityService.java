package org.jboss.community.trts.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseEntityService() {
		Class<T> attempt = null;

		Type genericSuperClass = this.getClass().getGenericSuperclass();
		if (genericSuperClass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) genericSuperClass;

			for (Type actual : parameterizedType.getActualTypeArguments()) {
				if (actual instanceof Class<?>) {
					attempt = (Class<T>) actual;
					break;
				}
			}
		}
		
		entityClass = (Class<T>) attempt;
	}

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

	public T getById(Long id) {
		return em.find(entityClass, id);
	}

	public List<T> getAll() {
		// TODO: Check
		final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		final CriteriaQuery<T> criteriaQuery = criteriaBuilder
				.createQuery(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);

		criteriaQuery.select(criteriaQuery.getSelection());
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));

		TypedQuery<T> query = em.createQuery(criteriaQuery);

		return query.getResultList();
	}
}
