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

import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.service.AxisConfigService;
import org.jboss.community.trts.service.AxisService;
import org.jboss.community.trts.service.ProductVersionService;

@RequestScoped
@RolesAllowed({"Tester, Admin"})
@Path("/axis/{aid:[0-9][0-9]*}/version/{vid:[0-9][0-9]*}/configurations")
public class AxisConfigurationREST {

	@Inject
	private AxisService service;

	@Inject
	private AxisConfigService configService;

	@Inject
	private ProductVersionService versionService;

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void editConfiguration(@PathParam("aid") Long aid,
			@PathParam("vid") Long vid, @PathParam("id") Long id, AxisConfig ac) {
		configService.update(ac);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addAxisConfig(@PathParam("aid") Long aid,
			@PathParam("vid") Long vid, AxisConfig c) {

		c.setProductVersion(versionService.getById(vid));
		configService.persist(c);
	}

	@GET
	public List<AxisConfig> listConfigurations(@PathParam("aid") Long aid,
			@PathParam("vid") Long vid) {
		return configService.getAxisConfigs(service.getById(aid),
				versionService.getById(vid));
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public AxisConfig getById(@PathParam("aid") Long aid,
			@PathParam("vid") Long vid, @PathParam("id") Long id) {
		return configService.getById(id);
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public void removeAxisConfig(@PathParam("aid") Long aid,
			@PathParam("vid") Long vid, @PathParam("id") Long id) {
		configService.delete(configService.getById(id));
	}
}