package org.jboss.community.trts.rest;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@RequestScoped
@Path("/user")
@RolesAllowed({ "Tester, Manager" })
public class SystemUserREST {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getCurrentlyLoggedUser(@Context SecurityContext sec) {
		return sec.getUserPrincipal().getName();
	}

	@POST
	@Path("/role")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String inRole(@Context SecurityContext sec, RoleMap roleMap) {
		return String.valueOf(sec.isUserInRole(roleMap.getRole()));
	}

}

class RoleMap {
	private String role;
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}