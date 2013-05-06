package org.jboss.community.trts.service;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;

import org.jboss.community.trts.model.Axis;

@Stateless
@RequestScoped
public class AxisService extends BaseEntityService<Axis> {
	// All necessary implementation is inherited
}
