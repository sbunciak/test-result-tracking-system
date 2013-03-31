package org.jboss.community.trts.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.service.AxisConfigService;
import org.jboss.community.trts.service.AxisService;
import org.jboss.community.trts.service.AxisValueService;

@RequestScoped
@Path("/axis/{aid:[0-9][0-9]*}/configurations")
public class AxisConfigurationREST {
	
	@Inject
	private AxisService service;
	
	@Inject
	private AxisValueService valueService;
	
	@Inject
	private AxisConfigService configService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AxisConfig> getAxisConfigurations(@PathParam("aid") Long aid) {
		Axis a = service.getAxisById(aid);
		
		if (a != null) {
			return configService.getAxisConfigs(a);
		} else {
			return null;
		}		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addAxisConfig(AxisConfig config) {
		//config.setAxisValue(axisValue)
		//config.setProductVersion(productVersion)
		
		configService.persist(config);
	}
	
}
