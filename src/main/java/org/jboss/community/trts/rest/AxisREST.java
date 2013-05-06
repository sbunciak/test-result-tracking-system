package org.jboss.community.trts.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.service.AxisService;
import org.jboss.community.trts.service.BaseEntityService;

@RequestScoped
@Path("/axis")
public class AxisREST extends BaseEntityREST<Axis> {

	@Inject
	private AxisService service;
	
	@Override
	public BaseEntityService<Axis> getService() {
		return service;
	}
}
