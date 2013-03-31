package org.jboss.community.trts.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.service.AxisService;

@RequestScoped
@Path("/axis")
public class AxisREST {

	@Inject
	private AxisService service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Axis> listAllProducts() {
		return service.getAllAxis();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addProduct(Axis p) {
		service.persist(p);
	}
}
