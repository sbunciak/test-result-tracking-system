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
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.model.AxisPriority;
import org.jboss.community.trts.service.AxisConfigService;
import org.jboss.community.trts.service.AxisService;
import org.jboss.community.trts.service.AxisValueService;
import org.jboss.community.trts.service.ProductVersionService;

@RequestScoped
@Path("/axis/{aid:[0-9][0-9]*}/configurations")
public class AxisConfigurationREST {
	
	@Inject
	private AxisService service;
	
	@Inject
	private AxisValueService valueService;
	
	@Inject
	private AxisConfigService configService;
	
	@Inject
	private ProductVersionService versionService;
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addAxisConfig(@PathParam("aid") Long aid, AxisConfigDataHolder[] config) {
		
		for (AxisConfigDataHolder c : config) {
			AxisConfig ac = new AxisConfig();
			ac.setAxisValue(valueService.getById(c.getAxisValueId()));
			ac.setProductVersion(versionService.getById(c.getProductVersionId()));
			ac.setPriority(AxisPriority.valueOf(c.getPriority()));
			
			configService.persist(ac);
		}
		
	}
	
}

/**
 * 
 * @author sbunciak
 *
 */
@XmlRootElement
class AxisConfigDataHolder {
	private Long axisValueId;
	private Long productVersionId;
	private String priority;
	
	public AxisConfigDataHolder() {
		// Auto-generated constructor stub
	}

	public Long getAxisValueId() {
		return axisValueId;
	}

	public void setAxisValueId(Long axisValueId) {
		this.axisValueId = axisValueId;
	}

	public Long getProductVersionId() {
		return productVersionId;
	}

	public void setProductVersionId(Long productVersionId) {
		this.productVersionId = productVersionId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}	
}