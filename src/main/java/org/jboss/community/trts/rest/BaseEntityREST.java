package org.jboss.community.trts.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.service.BaseEntityService;

/**
 * Basic REST CRUD methods implementation common for all entities.
 * This approach ensures unified calls among the entities REST calls from UI.
 * 
 * @author sbunciak
 * 
 * @param <T>
 *            entity
 */
@RolesAllowed({"Tester, Admin"})
public abstract class BaseEntityREST<T> {

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void delete(@PathParam("id") Long id) {
		this.getService().delete(this.getService().getById(id));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public T getById(@PathParam("id") Long id) {
		return this.getService().getById(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<T> getAll() {
		return this.getService().getAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(T entity) {
		this.getService().persist(entity);
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") Long id, T entity) {
		this.getService().update(entity);
	}

	/**
	 * Each REST service has to provide.
	 * 
	 * @return Appropriate BaseEntityService Implementation
	 */
	public abstract BaseEntityService<T> getService();
}
