package org.jboss.community.trts.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisValue;
import org.jboss.community.trts.service.AxisService;
import org.jboss.community.trts.service.AxisValueService;

@RequestScoped
@RolesAllowed({"Tester, Admin"})
@Path("/axis/{aid:[0-9][0-9]*}/values")
public class AxisValueREST {

	@Inject
	private AxisService service;

	@Inject
	private AxisValueService valueService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AxisValue> getAxisValuesOfAxis(@PathParam("aid") Long aid) {
		Axis a = service.getById(aid);

		if (a != null) {
			return valueService.getAxisValues(a);
		} else {
			return null;
		}
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public AxisValue getById(@PathParam("aid") Long aid,
			@PathParam("id") Long id) {
		return valueService.getById(id);
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void editAxisValue(@PathParam("aid") Long aid,
			@PathParam("id") Long id, AxisValue value) {
		valueService.update(value);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addProductBuildAxisValue(@PathParam("aid") Long aid,
			AxisValue value) {
		value.setAxis(service.getById(aid));

		valueService.persist(value);
	}
	
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void deleteAxisValue(@PathParam("aid") Long aid,
			@PathParam("id") Long id) {
		valueService.delete(valueService.getById(id));
	}
}
