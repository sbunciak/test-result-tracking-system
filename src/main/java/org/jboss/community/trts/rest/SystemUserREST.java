package org.jboss.community.trts.rest;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@RequestScoped
@Path("/user")
@RolesAllowed({ "Tester, Admin" })
public class SystemUserREST {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getCurrentlyLoggedUser(@Context SecurityContext sec) {
		return sec.getUserPrincipal().getName();
	}

}
